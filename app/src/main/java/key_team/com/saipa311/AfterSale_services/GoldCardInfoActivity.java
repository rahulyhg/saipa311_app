package key_team.com.saipa311.AfterSale_services;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestExists;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestExistsParams;
import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyCustomApplication;
import key_team.com.saipa311.PhotoViewer;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExistsParams;
import key_team.com.saipa311.Sale_services.NewCarRequestActivity;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class GoldCardInfoActivity extends AppCompatActivity {
    private GoldCard goldCardInfo;
    private TextView subject;
    private TextView price;
    private TextView description;
    private ImageView cardImg;
    private ViewFlipper onTrackPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_card_info);
        this.init();
        this.createActionBar();
        this.getData();
    }

    @Override
    protected void onPause() {
        MyCustomApplication.activityPaused();
        super.onPause();
    }

    @Override
    protected void onResume() {
        MyCustomApplication.activityResumed();
        super.onResume();
    }

    public void displayNoInternetConnectionError()
    {
        TextView reTry_btn;
        View alertLayout = getLayoutInflater().inflate(R.layout.no_internet_connection_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        reTry_btn = (TextView)alertLayout.findViewById(R.id.reTry);
        builder.setView(alertLayout);
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                System.exit(0);
            }
        });
        final AlertDialog dTemp = builder.show();
        reTry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                dTemp.dismiss();
            }
        });
    }

    private void init()
    {
        this.onTrackPm = (ViewFlipper)findViewById(R.id.onTrackPm);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                //Log.d("my log" , ".................... offset" + offset);
                Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back, null);
                if ((Math.abs(offset) + 30) >= appBarLayout.getTotalScrollRange()) {
                    upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                } else {
                    upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                }
            }
        });
    }

    private void getData()
    {
        String goldCardInfo_string = getIntent().getExtras().getString("goldCardInfo");
        goldCardInfo = new Gson().fromJson(goldCardInfo_string, GoldCard.class);

        if (PublicParams.getConnectionState(this)) {
            if (UserInfo.isLoggedIn()) {
                existNotTrackedRequest();
            } else {
                onTrackPm.setDisplayedChild(1);
            }

            subject = (TextView) findViewById(R.id.subject);
            price = (TextView) findViewById(R.id.price);
            description = (TextView) findViewById(R.id.description);

            subject.setText(goldCardInfo.getGcSubject());
            price.setText(goldCardInfo.getGcPrice());
            price.setTypeface(PublicParams.BYekan(this));
            description.setText(goldCardInfo.getGcDescription());
            cardImg = (ImageView) findViewById(R.id.goldCardImg);
            Picasso.with(GoldCardInfoActivity.this)
                    .load(PublicParams.BASE_URL + goldCardInfo.getGcImgPath())
                    .placeholder(R.drawable.gold_card_place_holder)
                    .error(R.drawable.oops)
                    .fit()
                    .centerInside()
                    .into(cardImg, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }else{
            displayNoInternetConnectionError();
        }
    }

    private void existNotTrackedRequest()
    {
        onTrackPm.setDisplayedChild(0);

        GoldCardRequestExistsParams params = new GoldCardRequestExistsParams();
        params.setGcId(goldCardInfo.getId());
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<GoldCardRequestExists> request = client.isNotTrackedGoldCardRequestExist(params);
        request.enqueue(new Callback<GoldCardRequestExists>() {
            @Override
            public void onResponse(Call<GoldCardRequestExists> call, Response<GoldCardRequestExists> response) {
                if (response.code() == 200)
                {
                    GoldCardRequestExists gcrExist;
                    gcrExist = response.body();
                    if (gcrExist.getExist() == true)
                    {
                        onTrackPm.setDisplayedChild(2);
                    }
                    else{
                        onTrackPm.setDisplayedChild(1);
                    }
                }else
                {
                    customToast.show(getLayoutInflater(), GoldCardInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<GoldCardRequestExists> call, Throwable t) {
                customToast.show(getLayoutInflater(), GoldCardInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
            }
        });
    }

    private void createActionBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void createRequest(View view)
    {
        if (UserInfo.isLoggedIn() == false) {
            Intent intent = new Intent(GoldCardInfoActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        else{
            Intent intent = new Intent(GoldCardInfoActivity.this, GoldCardRequestActivity.class);
            String arrayAsString = new Gson().toJson(goldCardInfo);
            intent.putExtra("goldCardInfo", arrayAsString);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("my log" , "....................... onActivityResult");
        if (UserInfo.isLoggedIn()) {
            existNotTrackedRequest();
        }
        else{
            onTrackPm.setDisplayedChild(1);
        }
    }
}
