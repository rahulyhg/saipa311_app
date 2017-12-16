package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.ServiceGenerator;
import key_team.com.saipa311.Sale_services.JsonSchema.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class Deposite extends Fragment {
    private RecyclerView recyclerView;
    private DepositsAdapter depositAdapter;
    private ArrayList<deposit> depositList;
    private List<Deposit> depositData;
    private SwipeRefreshLayout swipeContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deposite_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.deposit_recycler_view);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.depositSwipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllDeposits();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        depositList = new ArrayList<>();
        depositAdapter = new DepositsAdapter(depositList);
        recyclerView.setAdapter(depositAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.fetchAllDeposits();
        return view;
    }

    private void fetchAllDeposits()
    {
        DepositRequestParams params = new DepositRequestParams();
        params.setRepId(1);
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<Deposit>> deposits = client.fetchDeposits(params);
        deposits.enqueue(new Callback<List<Deposit>>() {
            @Override
            public void onResponse(Call<List<Deposit>> call, Response<List<Deposit>> response) {
                depositData = response.body();
                //Log.d("my log", "......................" + oldCarData.get(0).getProduct().getPrSubject());
                prepareAlbums();
            }

            @Override
            public void onFailure(Call<List<Deposit>> call, Throwable t) {
            }
        });
    }

    private void prepareAlbums() {
        depositAdapter.clearData();
        for (int i=0 ; i < depositData.size() ; i++) {
            deposit a = new deposit(depositData.get(i).getDCar(),
                    depositData.get(i).getDNotificationId(),
                    depositData.get(i).getDDescription(),
                    depositData.get(i).getCompony().getCoIconPath());
            depositAdapter.addItem(a);
        }
        swipeContainer.setRefreshing(false);
    }

    public class deposit {
        String car;
        String notifId;
        String description;
        String companyLogo;
        public deposit(String car , String notifId , String description , String logoPath) {
            this.car = car;
            this.notifId = notifId;
            this.description = description;
            this.companyLogo = logoPath;
        }

        public String getCar() {
            return this.car;
        }

        public String getNotifId() {
            return this.notifId;
        }

        public String getDescription() {
            return description;
        }

        public String getComponyLogo() {
            return companyLogo;
        }
    }

    public class DepositsAdapter extends RecyclerView.Adapter<DepositsAdapter.DepositViewHolder> {

        private ArrayList<deposit> dataSet;
        public class DepositViewHolder extends RecyclerView.ViewHolder {
            public TextView car;
            public TextView notifId;
            public TextView description;
            public ImageView companyLogo;

            public DepositViewHolder(View view) {
                super(view);
                car = (TextView) view.findViewById(R.id.car);
                notifId = (TextView) view.findViewById(R.id.notifId);
                description = (TextView) view.findViewById(R.id.description);
                companyLogo = (ImageView) view.findViewById(R.id.company);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(getActivity(), DepositInfoActivity.class);
                        String arrayAsString = new Gson().toJson(depositData.get(getAdapterPosition()));
                        intent.putExtra("depositInfo", arrayAsString);
                        startActivity(intent);
                    }
                });

            }
        }

        public void clearData(){
            dataSet.clear();
            depositAdapter.notifyDataSetChanged();
        }
        public DepositsAdapter(ArrayList<deposit> data) {
            this.dataSet = data;
        }

        @Override
        public DepositViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_cardview, parent, false);
            DepositViewHolder myViewHolder = new DepositViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final DepositViewHolder holder, final int listPosition) {
            holder.car.setText(dataSet.get(listPosition).getCar());
            holder.notifId.setText(dataSet.get(listPosition).getNotifId());
            holder.notifId.setTypeface(PublicParams.BYekan(getContext()));
            holder.description.setText(dataSet.get(listPosition).getDescription());

            Picasso.with(getActivity())
                    .load(PublicParams.BASE_URL + "pic/company_icons/" + dataSet.get(listPosition).getComponyLogo())
                    .error(R.drawable.oops)
                    .fit()
                    .centerInside()
                    .into(holder.companyLogo);
        }

        public void addItem(deposit dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}