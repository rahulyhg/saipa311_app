package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestParams;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.SquareImageView;
import key_team.com.saipa311.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class OldCars extends Fragment {
    private RecyclerView recyclerView;
    private OldCarsAdapter oldCarAdapter;
    private ArrayList<oldCar> oldCarList;
    private List<OldCar> oldCarData;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ss_old_car_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.old_car_recycler_view);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.oldCarSRLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllOldCars();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        oldCarList = new ArrayList<>();
        oldCarAdapter = new OldCarsAdapter(oldCarList);
        recyclerView.setAdapter(oldCarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.fetchAllOldCars();
        return view;
    }

    private void fetchAllOldCars()
    {
        swipeContainer.setRefreshing(true);
        OldCarRequestParams params = new OldCarRequestParams();
        params.setRepId(1);
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<List<OldCar>> newCars = client.fetchOldCars(params);
        newCars.enqueue(new Callback<List<OldCar>>() {
            @Override
            public void onResponse(Call<List<OldCar>> call, Response<List<OldCar>> response) {
                oldCarData = response.body();
                Log.d("my log" , "......................" + oldCarData.get(0).getProduct().getPrSubject());
                prepareAlbums();
            }

            @Override
            public void onFailure(Call<List<OldCar>> call, Throwable t) {
            }
        });
    }

    private void prepareAlbums() {
        oldCarAdapter.clearData();
        for (int i=0 ; i < oldCarData.size() ; i++) {
            oldCar a = new oldCar(oldCarData.get(i).getProduct().getPrSubject(),
                    oldCarData.get(i).getOcPrice(),
                    oldCarData.get(i).getOcBuildYear(),
                    oldCarData.get(i).getOcDescription() ,
                    oldCarData.get(i).getOldCarImage().size() > 0 ? oldCarData.get(i).getOldCarImage().get(0).getOciPath() : "");
            oldCarAdapter.addItem(a);
        }
        swipeContainer.setRefreshing(false);
    }

    public class oldCar {
        String title;
        String buildYear;
        String price;
        String description;
        String imageUrl;
        public oldCar(String name , String price , String buildYear , String desc , String imageUrl) {
            this.title = name;
            this.buildYear = buildYear;
            this.price = price;
            this.description = desc;
            this.imageUrl=imageUrl;
        }

        public String getTitle() {
            return this.title;
        }

        public String getBuildYear() {
            return this.buildYear;
        }

        public String getPrice() {
            return this.price;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return imageUrl;
        }
    }

    public class OldCarsAdapter extends RecyclerView.Adapter<OldCarsAdapter.OldCarViewHolder> {

        private ArrayList<oldCar> dataSet;
        public class OldCarViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public TextView buildYear;
            public TextView price;
            public TextView description;
            public SquareImageView thumbnail;
            public ProgressBar progress;

            public OldCarViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                buildYear = (TextView) view.findViewById(R.id.buildYear);
                price = (TextView) view.findViewById(R.id.price);
                description = (TextView) view.findViewById(R.id.description);
                thumbnail = (SquareImageView) view.findViewById(R.id.thumbnail);
                progress = (ProgressBar) view.findViewById(R.id.progressBar);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(getActivity(), OldCarInfoActivity.class);
                        String arrayAsString = new Gson().toJson(oldCarData.get(getAdapterPosition()));
                        intent.putExtra("oldCarInfo", arrayAsString);
                        startActivity(intent);
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            oldCarAdapter.notifyDataSetChanged();
        }
        public OldCarsAdapter(ArrayList<oldCar> data) {
            this.dataSet = data;
        }

        @Override
        public OldCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_car_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            OldCarViewHolder myViewHolder = new OldCarViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final OldCarViewHolder holder, final int listPosition) {
            holder.title.setText(dataSet.get(listPosition).getTitle());
            holder.buildYear.setText(dataSet.get(listPosition).getBuildYear());
            holder.buildYear.setTypeface(PublicParams.BYekan(getContext()));
            holder.price.setText(dataSet.get(listPosition).getPrice());
            holder.price.setTypeface(PublicParams.BYekan(getContext()));
            holder.description.setText(dataSet.get(listPosition).getDescription());
            Picasso.with(getActivity())
                    .load(PublicParams.BASE_URL + dataSet.get(listPosition).getImage())
                    .error(R.drawable.oops)
                    .fit()
                    .centerInside()
                    .into(holder.thumbnail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (holder.progress != null) {
                                holder.progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (holder.progress != null) {
                                holder.progress.setVisibility(View.GONE);
                            }
                        }
                    });
        }

        public void addItem(oldCar dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}