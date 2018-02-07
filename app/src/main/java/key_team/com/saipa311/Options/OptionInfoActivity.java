package key_team.com.saipa311.Options;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import key_team.com.saipa311.AfterSale_services.GoldCardRequestActivity;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestExists;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestExistsParams;
import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyCustomApplication;
import key_team.com.saipa311.Options.JsonSchema.CarOption;
import key_team.com.saipa311.Options.JsonSchema.CarOptionRequestExists;
import key_team.com.saipa311.Options.JsonSchema.CarOptionRequestExistsParams;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class OptionInfoActivity extends AppCompatActivity {
    private CarOption carOptionInfo;
    private TextView subject;
    private TextView price;
    private TextView description;
    private ImageView cardImg;
    private ViewFlipper onTrackPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_option_info);
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
        String carOptionInfo_string = getIntent().getExtras().getString("carOptionInfo");
        carOptionInfo = new Gson().fromJson(carOptionInfo_string, CarOption.class);

        if (PublicParams.getConnectionState(this)) {
            if (UserInfo.isLoggedIn()) {
                existNotTrackedRequest();
            } else {
                onTrackPm.setDisplayedChild(1);
            }

            subject = (TextView) findViewById(R.id.subject);
            price = (TextView) findViewById(R.id.price);
            description = (TextView) findViewById(R.id.description);

            subject.setText(carOptionInfo.getOption().getOName() + " - " + carOptionInfo.getProduct().getPrSubject());
            price.setText(carOptionInfo.getCoPrice());
            price.setTypeface(PublicParams.BYekan(this));
            description.setText(carOptionInfo.getCoDescription());
            cardImg = (ImageView) findViewById(R.id.optionImg);
            Picasso.with(OptionInfoActivity.this)
                    .load(PublicParams.BASE_URL + carOptionInfo.getCoImgPath())
                    .placeholder(R.drawable.place_holder)
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

        CarOptionRequestExistsParams params = new CarOptionRequestExistsParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        params.setCoId(carOptionInfo.getId());
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<CarOptionRequestExists> request = client.isNotTrackedCarOptionRequestExist(params);
        request.enqueue(new Callback<CarOptionRequestExists>() {
            @Override
            public void onResponse(Call<CarOptionRequestExists> call, Response<CarOptionRequestExists> response) {
                if (response.code() == 200)
                {
                    CarOptionRequestExists corExist;
                    corExist = response.body();
                    if (corExist.getExist() == true)
                    {
                        onTrackPm.setDisplayedChild(2);
                    }
                    else{
                        onTrackPm.setDisplayedChild(1);
                    }
                }else
                {
                    customToast.show(getLayoutInflater(), OptionInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<CarOptionRequestExists> call, Throwable t) {
                customToast.show(getLayoutInflater(), OptionInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
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
            Intent intent = new Intent(OptionInfoActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        else{
            Intent intent = new Intent(OptionInfoActivity.this, OptionRequestActivity.class);
            String arrayAsString = new Gson().toJson(carOptionInfo);
            intent.putExtra("carOptionInfo", arrayAsString);
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
