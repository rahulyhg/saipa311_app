package key_team.com.saipa311.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import key_team.com.saipa311.AfterSale_services.GoldCardInfoActivity;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestParams;
import key_team.com.saipa311.ItemOffsetDecoration;
import key_team.com.saipa311.Options.JsonSchema.CarOption;
import key_team.com.saipa311.Options.JsonSchema.CarOptionsRequestParams;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 11/16/17.
 */
public class OptionsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private List<CarOption> carOptionsData;
    private CarOptionsAdapter carOptionsAdapter;
    private ArrayList<carOption> carOptionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.option_recycler_view);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllCarOptions();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        carOptionList = new ArrayList<>();
        carOptionsAdapter = new CarOptionsAdapter(carOptionList);
        recyclerView.setAdapter(carOptionsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.recycler_item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        this.fetchAllCarOptions();
        return view;
    }

    private void fetchAllCarOptions()
    {
        swipeContainer.setRefreshing(true);
        CarOptionsRequestParams params = new CarOptionsRequestParams();
        params.setRepId(1);
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<List<CarOption>> request = client.fetchAllCarOptions(params);
        request.enqueue(new Callback<List<CarOption>>() {
            @Override
            public void onResponse(Call<List<CarOption>> call, Response<List<CarOption>> response) {
                carOptionsData = response.body();
                prepareCarOptions();
            }

            @Override
            public void onFailure(Call<List<CarOption>> call, Throwable t) {

            }
        });
    }

    private void prepareCarOptions()
    {
        carOptionsAdapter.clearData();
        for (int i=0 ; i < carOptionsData.size() ; i++) {
            carOption a = new carOption(carOptionsData.get(i).getOption().getOName(),
                    carOptionsData.get(i).getCoPrice(),
                    carOptionsData.get(i).getCoDescription(),
                    carOptionsData.get(i).getCoImgPath());
            carOptionsAdapter.addItem(a);
        }
        swipeContainer.setRefreshing(false);
    }

    public class carOption {
        String subject;
        String price;
        String description;
        String imageUrl;
        public carOption(String subject, String price , String desc, String imageUrl) {
            this.subject = subject;
            this.price = price;
            this.description = desc;
            this.imageUrl=imageUrl;
        }

        public String getSubject() {
            return this.subject;
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

    public class CarOptionsAdapter extends RecyclerView.Adapter<CarOptionsAdapter.CarOptionsViewHolder> {

        private ArrayList<carOption> dataSet;
        public class CarOptionsViewHolder extends RecyclerView.ViewHolder {
            public TextView subject;
            public TextView price;
            public ImageView thumbnail;
            public ProgressBar progress;
            public ImageView shareGoldCard;

            public CarOptionsViewHolder(View view) {
                super(view);
                subject = (TextView) view.findViewById(R.id.subject);
                price = (TextView) view.findViewById(R.id.price);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                progress = (ProgressBar) view.findViewById(R.id.progressBar);
                shareGoldCard = (ImageView) view.findViewById(R.id.shareOption);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(getActivity(), OptionInfoActivity.class);
                        String arrayAsString = new Gson().toJson(carOptionsData.get(getAdapterPosition()));
                        intent.putExtra("carOptionInfo", arrayAsString);
                        startActivity(intent);
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            carOptionsAdapter.notifyDataSetChanged();
        }
        public CarOptionsAdapter(ArrayList<carOption> data) {
            this.dataSet = data;
        }

        @Override
        public CarOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            CarOptionsViewHolder myViewHolder = new CarOptionsViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final CarOptionsViewHolder holder, final int listPosition) {
            holder.subject.setText(dataSet.get(listPosition).getSubject());
            holder.price.setText(dataSet.get(listPosition).getPrice());
            holder.price.setTypeface(PublicParams.BYekan(getContext()));
            holder.shareGoldCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = dataSet.get(listPosition).getSubject()
                            + "\n" + dataSet.get(listPosition).getDescription()
                            + "\n" + "قیمت : " + dataSet.get(listPosition).getPrice()
                            + "\n" + "نماینده سایپا ۳۱۱ : کوچک عظیمی";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "سایپا");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    //sharingIntent.setType("*/*");
                    //sharingIntent.putExtra(Intent.EXTRA_STREAM , PublicParams.BASE_URL + dataSet.get(listPosition).getImage());
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
            Picasso.with(getActivity())
                    .load(PublicParams.BASE_URL + dataSet.get(listPosition).getImage())
                    .placeholder(R.drawable.place_holder)
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

        public void addItem(carOption dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
