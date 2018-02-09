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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestParams;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.SquareImageView;
import key_team.com.saipa311.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class NewCars extends Fragment {
    private RecyclerView recyclerView;
    private NewCarsAdapter newCarAdapter;
    private ArrayList<newCar> newCarList;
    private List<NewCar> newCarData;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ss_new_car_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.new_car_recycler_view);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllNewCars();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        newCarList = new ArrayList<>();
        newCarAdapter = new NewCarsAdapter(newCarList);
        recyclerView.setAdapter(newCarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.fetchAllNewCars();
        return view;
    }

    private void fetchAllNewCars()
    {
        swipeContainer.setRefreshing(true);
        NewCarRequestParams params = new NewCarRequestParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<List<NewCar>> newCars = client.fetchNewCars(params);
        newCars.enqueue(new Callback<List<NewCar>>() {
            @Override
            public void onResponse(Call<List<NewCar>> call, Response<List<NewCar>> response) {
                newCarData = response.body();
                prepareAlbums();
            }

            @Override
            public void onFailure(Call<List<NewCar>> call, Throwable t) {
            }
        });
    }

    private void prepareAlbums() {
        newCarAdapter.clearData();
        for (int i=0 ; i < newCarData.size() ; i++) {
            newCar a = new newCar(newCarData.get(i).getProduct().getPrSubject(),
                    newCarData.get(i).getChassis().getChChassis(),
                    newCarData.get(i).getNcConditions(),
                    newCarData.get(i).getNcPrice(),
                    newCarData.get(i).getNcDescription() ,
                    newCarData.get(i).getNewCarImage().size() > 0 ? newCarData.get(i).getNewCarImage().get(0).getNciPath() : "");
            newCarAdapter.addItem(a);
        }
        swipeContainer.setRefreshing(false);
    }

    public class newCar {
        String title;
        String chassis;
        int isConditions;
        String price;
        String description;
        String imageUrl;
        public newCar(String name, String chassis , int isConditions , String price , String desc, String imageUrl) {
            this.title = name;
            this.chassis = chassis;
            this.isConditions = isConditions;
            this.price = price;
            this.description = desc;
            this.imageUrl=imageUrl;
        }

        public String getTitle() {
            return this.title;
        }

        public String getChassis() {
            return this.chassis;
        }

        public int getIsConditions() {
            return this.isConditions;
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

    public class NewCarsAdapter extends RecyclerView.Adapter<NewCarsAdapter.NewCarViewHolder> {

        private ArrayList<newCar> dataSet;
        public class NewCarViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public TextView chassis;
            public TextView isConditions;
            public TextView price;
            public TextView description;
            public SquareImageView thumbnail;
            public ProgressBar progress;
            public ImageView shareNewCar;

            public NewCarViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                chassis = (TextView) view.findViewById(R.id.chassis);
                isConditions = (TextView) view.findViewById(R.id.isConditions);
                price = (TextView) view.findViewById(R.id.price);
                description = (TextView) view.findViewById(R.id.description);
                thumbnail = (SquareImageView) view.findViewById(R.id.thumbnail);
                progress = (ProgressBar) view.findViewById(R.id.progressBar);
                shareNewCar = (ImageView) view.findViewById(R.id.shareNewCar);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(getActivity(), NewCarInfoActivity.class);
                        String arrayAsString = new Gson().toJson(newCarData.get(getAdapterPosition()));
                        intent.putExtra("newCarInfo", arrayAsString);
                        getActivity().startActivityForResult(intent , 100);
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            newCarAdapter.notifyDataSetChanged();
        }
        public NewCarsAdapter(ArrayList<newCar> data) {
            this.dataSet = data;
        }

        @Override
        public NewCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_car_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            NewCarViewHolder myViewHolder = new NewCarViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final NewCarViewHolder holder, final int listPosition) {
            holder.title.setText(dataSet.get(listPosition).getTitle());
            holder.chassis.setText(dataSet.get(listPosition).getChassis());
            holder.isConditions.setVisibility(dataSet.get(listPosition).getIsConditions() == 0 ? View.GONE : View.VISIBLE);
            holder.price.setText(dataSet.get(listPosition).getPrice());
            holder.price.setTypeface(PublicParams.BYekan(getContext()));
            holder.description.setText(dataSet.get(listPosition).getDescription());
            holder.shareNewCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = dataSet.get(listPosition).getTitle()
                            + "\n" + dataSet.get(listPosition).getDescription()
                            + "\n" + "شاسی : " + dataSet.get(listPosition).getChassis()
                            + "\n" + "قیمت : " + dataSet.get(listPosition).getPrice()
                            + "\n" + "نمایندگی " + ActiveRepresentation.getActiveRepresentationInfo().name + " کد" + ActiveRepresentation.getActiveRepresentationInfo().code;
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "سایپا");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    //sharingIntent.setType("*/*");
                    //sharingIntent.putExtra(Intent.EXTRA_STREAM , PublicParams.BASE_URL + dataSet.get(listPosition).getImage());
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
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

        public void addItem(newCar dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
