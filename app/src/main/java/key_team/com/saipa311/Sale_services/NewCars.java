package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.ServiceGenerator;
import key_team.com.saipa311.Sale_services.JsonSchema.StoreClient;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ss_new_car_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.new_car_recycler_view);
        newCarList = new ArrayList<>();
        newCarAdapter = new NewCarsAdapter(newCarList);
        recyclerView.setAdapter(newCarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.fetchAllNewCars();
        return view;
    }

    private void fetchAllNewCars()
    {
        NewCarRequestParams params = new NewCarRequestParams();
        params.setRepId(1);
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<List<NewCar>> newCars = client.fetchNewCars(params);
        newCars.enqueue(new Callback<List<NewCar>>() {
            @Override
            public void onResponse(Call<List<NewCar>> call, Response<List<NewCar>> response) {
                newCarData = response.body();
                Log.d("my log", "..............." + newCarData.get(0).getNcSubject());
                prepareAlbums();
            }

            @Override
            public void onFailure(Call<List<NewCar>> call, Throwable t) {
                Log.d("my log", "............... error" + t.getMessage());
            }
        });
    }
    private void prepareAlbums() {
        for (int i=0 ; i < newCarData.size() ; i++) {
            newCar a = new newCar(newCarData.get(i).getNcSubject(),
                    newCarData.get(i).getChassis().getChChassis(),
                    newCarData.get(i).getNcConditions(),
                    newCarData.get(i).getNcPrice(),
                    newCarData.get(i).getNcDescription() ,
                    newCarData.get(i).getNewCarImage().get(0).getNciPatch());
            newCarAdapter.addItem(a);
        }
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
            public ImageView thumbnail;
            public ProgressBar progress;

            public NewCarViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                chassis = (TextView) view.findViewById(R.id.chassis);
                isConditions = (TextView) view.findViewById(R.id.isConditions);
                price = (TextView) view.findViewById(R.id.price);
                description = (TextView) view.findViewById(R.id.description);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                progress = (ProgressBar) view.findViewById(R.id.progressBar);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(getActivity(), NewCarInfoActivity.class);
                        String arrayAsString = new Gson().toJson(newCarData.get(getAdapterPosition()));
                        intent.putExtra("newCarInfo", arrayAsString);
                        startActivity(intent);
                    }
                });
            }
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
            Picasso.with(getActivity())
                    .load(PublicParams.BASE_URL + "pic/cars/" + dataSet.get(listPosition).getImage())
                    .placeholder(R.drawable.place_holder)
                    .error(R.mipmap.ic_launcher)
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
