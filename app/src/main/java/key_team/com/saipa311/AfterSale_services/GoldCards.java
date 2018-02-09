package key_team.com.saipa311.AfterSale_services;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestParams;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestParams;
import key_team.com.saipa311.Sale_services.NewCarInfoActivity;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.SquareImageView;
import key_team.com.saipa311.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 1/10/18.
 */
public class GoldCards extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private List<GoldCard> goldCardsData;
    private ArrayList<goldCard> goldCardList;
    private GoldCardsAdapter goldCardsAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ass_gold_cars_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.gold_cars_recycler_view);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllGoldCards();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        goldCardList = new ArrayList<>();
        goldCardsAdapter = new GoldCardsAdapter(goldCardList);
        recyclerView.setAdapter(goldCardsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.fetchAllGoldCards();
        return view;
    }

    private void fetchAllGoldCards()
    {
        swipeContainer.setRefreshing(true);
        GoldCardRequestParams params = new GoldCardRequestParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<List<GoldCard>> goldCards = client.fetchGoldCards(params);
        goldCards.enqueue(new Callback<List<GoldCard>>() {
            @Override
            public void onResponse(Call<List<GoldCard>> call, Response<List<GoldCard>> response) {
                goldCardsData = response.body();
                prepareAlbums();
            }

            @Override
            public void onFailure(Call<List<GoldCard>> call, Throwable t) {

            }
        });
    }

    private void prepareAlbums() {
        goldCardsAdapter.clearData();
        for (int i=0 ; i < goldCardsData.size() ; i++) {
            goldCard a = new goldCard(goldCardsData.get(i).getGcSubject(),
                    goldCardsData.get(i).getGcPrice(),
                    goldCardsData.get(i).getGcDescription(),
                    goldCardsData.get(i).getGcImgPath());
            goldCardsAdapter.addItem(a);
        }
        swipeContainer.setRefreshing(false);
    }

    public class goldCard {
        String subject;
        String price;
        String description;
        String imageUrl;
        public goldCard(String subject, String price , String desc, String imageUrl) {
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

    public class GoldCardsAdapter extends RecyclerView.Adapter<GoldCardsAdapter.GoldCardsViewHolder> {

        private ArrayList<goldCard> dataSet;
        public class GoldCardsViewHolder extends RecyclerView.ViewHolder {
            public TextView subject;
            public TextView price;
            public TextView description;
            public ImageView thumbnail;
            public ProgressBar progress;
            public ImageView shareGoldCard;

            public GoldCardsViewHolder(View view) {
                super(view);
                subject = (TextView) view.findViewById(R.id.subject);
                price = (TextView) view.findViewById(R.id.price);
                description = (TextView) view.findViewById(R.id.description);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                progress = (ProgressBar) view.findViewById(R.id.progressBar);
                shareGoldCard = (ImageView) view.findViewById(R.id.shareGoldCard);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(getActivity(), GoldCardInfoActivity.class);
                        String arrayAsString = new Gson().toJson(goldCardsData.get(getAdapterPosition()));
                        intent.putExtra("goldCardInfo", arrayAsString);
                        getActivity().startActivityForResult(intent , 100);
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            goldCardsAdapter.notifyDataSetChanged();
        }
        public GoldCardsAdapter(ArrayList<goldCard> data) {
            this.dataSet = data;
        }

        @Override
        public GoldCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gold_card_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            GoldCardsViewHolder myViewHolder = new GoldCardsViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final GoldCardsViewHolder holder, final int listPosition) {
            holder.subject.setText(dataSet.get(listPosition).getSubject());
            holder.price.setText(dataSet.get(listPosition).getPrice());
            holder.price.setTypeface(PublicParams.BYekan(getContext()));
            holder.description.setText(dataSet.get(listPosition).getDescription());
            holder.shareGoldCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody =
                            "کارت طلایی:"
                            + "\n" + dataSet.get(listPosition).getSubject()
                            + "\n" + dataSet.get(listPosition).getDescription()
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
                    .placeholder(R.drawable.gold_card_place_holder)
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

        public void addItem(goldCard dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
