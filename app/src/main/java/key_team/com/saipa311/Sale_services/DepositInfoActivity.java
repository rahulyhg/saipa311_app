package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;

import java.util.HashMap;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestExistsParams;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class DepositInfoActivity extends AppCompatActivity {
    private Deposit depositInfo;
    private TextView car;
    private TextView notifId;
    private TextView prePaymentAmount;
    private TextView description;
    private ViewFlipper onTrackPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_info);
        this.init();
        this.createActionBar();
        this.getData();
    }

    private void init()
    {
        this.onTrackPm = (ViewFlipper)findViewById(R.id.onTrackPm);
    }

    private void createActionBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void getData() {
        String depositInfo_string = getIntent().getExtras().getString("depositInfo");
        depositInfo = new Gson().fromJson(depositInfo_string, Deposit.class);

        if (UserInfo.isLoggedIn()) {
            existNotTrackedRequest();
        }
        else{
            onTrackPm.setDisplayedChild(1);
        }

        car = (TextView) findViewById(R.id.car);
        notifId = (TextView) findViewById(R.id.notifId);
        description = (TextView) findViewById(R.id.description);
        prePaymentAmount = (TextView) findViewById(R.id.prePaymentAmount);

        car.setText(depositInfo.getDCar());
        notifId.setText(depositInfo.getDNotificationId());
        notifId.setTypeface(PublicParams.BYekan(this));
        prePaymentAmount.setText(depositInfo.getDPrePaymentAmount());
        prePaymentAmount.setTypeface(PublicParams.BYekan(this));

        description.setText(depositInfo.getDDescription());

        TableLayout ts = (TableLayout) findViewById(R.id.interest);
        TableLayout.LayoutParams tr_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView threeMonthProfit = new TextView(this);
        threeMonthProfit.setText(depositInfo.getDThreeMonthProfit());
        threeMonthProfit.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        threeMonthProfit.setGravity(Gravity.RIGHT);
        threeMonthProfit.setTypeface(PublicParams.BYekan(this));
        threeMonthProfit.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr.addView(threeMonthProfit);

        TextView threeMonthProfit_subject = new TextView(this);
        threeMonthProfit_subject.setText("ثبت نام سه ماهه");
        threeMonthProfit_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        threeMonthProfit_subject.setGravity(Gravity.RIGHT);
        threeMonthProfit_subject.setTextColor(Color.parseColor("#AEAEAE"));
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr.addView(threeMonthProfit_subject);
        tr.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr.setPadding(8 , 15 , 8 , 15);
        ts.addView(tr, tr_params);
        /////////////////////////////

        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView sixMonthProfit = new TextView(this);
        sixMonthProfit.setText(depositInfo.getDSixMonthProfit());
        sixMonthProfit.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        sixMonthProfit.setGravity(Gravity.RIGHT);
        sixMonthProfit.setTypeface(PublicParams.BYekan(this));
        sixMonthProfit.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr1.addView(sixMonthProfit);

        TextView sixMonthProfit_subject = new TextView(this);
        sixMonthProfit_subject.setText("ثبت نام شش ماهه");
        sixMonthProfit_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        sixMonthProfit_subject.setGravity(Gravity.RIGHT);
        sixMonthProfit_subject.setTextColor(Color.parseColor("#AEAEAE"));
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr1.addView(sixMonthProfit_subject);
        tr1.setPadding(8 , 15 , 8 , 15);
        ts.addView(tr1, tr_params);
        /////////////////////////////

        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView nineMonthProfit = new TextView(this);
        nineMonthProfit.setText(depositInfo.getDNineMonthProfit());
        nineMonthProfit.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        nineMonthProfit.setGravity(Gravity.RIGHT);
        nineMonthProfit.setTypeface(PublicParams.BYekan(this));
        nineMonthProfit.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr2.addView(nineMonthProfit);

        TextView nineMonthProfit_subject = new TextView(this);
        nineMonthProfit_subject.setText("ثبت نام نه ماهه");
        nineMonthProfit_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        nineMonthProfit_subject.setGravity(Gravity.RIGHT);
        nineMonthProfit_subject.setTextColor(Color.parseColor("#AEAEAE"));
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr2.addView(nineMonthProfit_subject);
        tr2.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr2.setPadding(8 , 15 , 8 , 15);
        ts.addView(tr2, tr_params);
        /////////////////////////////

        TableRow tr3 = new TableRow(this);
        tr3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView twelveMonthProfit = new TextView(this);
        twelveMonthProfit.setText(depositInfo.getDTwelveMonthProfit());
        twelveMonthProfit.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        twelveMonthProfit.setGravity(Gravity.RIGHT);
        twelveMonthProfit.setTypeface(PublicParams.BYekan(this));
        twelveMonthProfit.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr3.addView(twelveMonthProfit);

        TextView twelveMonthProfit_subject = new TextView(this);
        twelveMonthProfit_subject.setText("ثبت نامه یک ساله");
        twelveMonthProfit_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        twelveMonthProfit_subject.setGravity(Gravity.RIGHT);
        twelveMonthProfit_subject.setTextColor(Color.parseColor("#AEAEAE"));
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr3.addView(twelveMonthProfit_subject);
        tr3.setPadding(8 , 15 , 8 , 15);
        ts.addView(tr3, tr_params);
        /////////////////////////////
    }

    private void existNotTrackedRequest()
    {
        onTrackPm.setDisplayedChild(0);

        DepositRequestExistsParams params = new DepositRequestExistsParams();
        params.setDId(depositInfo.getId());
        params.setRepId(1);
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<DepositRequestExists> request = client.isNotTrackedDepositRequestExist(params);
        request.enqueue(new Callback<DepositRequestExists>() {
            @Override
            public void onResponse(Call<DepositRequestExists> call, Response<DepositRequestExists> response) {
                if (response.code() == 200)
                {
                    DepositRequestExists drExist;
                    drExist = response.body();
                    if (drExist.getExist() == true)
                    {
                        onTrackPm.setDisplayedChild(2);
                    }
                    else{
                        onTrackPm.setDisplayedChild(1);
                    }
                }else
                {
                    customToast.show(getLayoutInflater(), DepositInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<DepositRequestExists> call, Throwable t) {
                customToast.show(getLayoutInflater(), DepositInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
            }
        });
    }

    public void createRequest(View view)
    {
        if (UserInfo.isLoggedIn() == false) {
            Intent intent = new Intent(DepositInfoActivity.this, LoginActivity.class);
            startActivityForResult(intent , 1);
        }
        else{
            Intent intent = new Intent(DepositInfoActivity.this, DepositRequestActivity.class);
            String arrayAsString = new Gson().toJson(depositInfo);
            intent.putExtra("depositInfo", arrayAsString);
            startActivityForResult(intent , 2);
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
        Log.d("my log", "....................... onActivityResult");
        if (UserInfo.isLoggedIn()) {
            existNotTrackedRequest();
        }
        else{
            onTrackPm.setDisplayedChild(1);
        }
    }
}
