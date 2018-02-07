package key_team.com.saipa311.Sale_services;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

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
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlanRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlans;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarRequestExists;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class NewCarInfoActivity extends AppCompatActivity {
    private NewCar newCarInfo;
    private TextView title;
    private TextView isConditions;
    private TextView price;
    private TextView description;
    private ImageView conditionImg;
    private SliderLayout mDemoSlider;
    private CardView conditionImagePart;
    private ViewFlipper onTrackPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_info);
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
    }

    private void getData()
    {
        String newCarInfo_string = getIntent().getExtras().getString("newCarInfo");
        newCarInfo = new Gson().fromJson(newCarInfo_string, NewCar.class);

        if (UserInfo.isLoggedIn()) {
            existNotTrackedRequest();
        } else {
            onTrackPm.setDisplayedChild(1);
        }

        title = (TextView) findViewById(R.id.title);
        isConditions = (TextView) findViewById(R.id.isConditions);
        price = (TextView) findViewById(R.id.price);
        description = (TextView) findViewById(R.id.description);
        conditionImagePart = (CardView) findViewById(R.id.conditionImagePart);

        title.setText(newCarInfo.getProduct().getPrSubject());
        if (newCarInfo.getNcConditions() == 0) {
            isConditions.setVisibility(View.GONE);
            conditionImagePart.setVisibility(View.GONE);
        } else {
            isConditions.setVisibility(View.VISIBLE);
            conditionImagePart.setVisibility(View.VISIBLE);
        }

        price.setText(newCarInfo.getNcPrice());
        price.setTypeface(PublicParams.BYekan(this));

        description.setText(newCarInfo.getNcDescription());

        TableLayout ts = (TableLayout) findViewById(R.id.technicalSpecifications);
        TableLayout.LayoutParams tr_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView ncWheelbase = new TextView(this);
        ncWheelbase.setText(newCarInfo.getNcWheelbase());
        ncWheelbase.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncWheelbase.setGravity(Gravity.RIGHT);
        ncWheelbase.setTypeface(PublicParams.BYekan(this));
        ncWheelbase.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr.addView(ncWheelbase);

        TextView ncWheelbase_subject = new TextView(this);
        ncWheelbase_subject.setText("فاصله محور ها");
        ncWheelbase_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncWheelbase_subject.setGravity(Gravity.RIGHT);
        ncWheelbase_subject.setTextColor(Color.parseColor("#AEAEAE"));
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr.addView(ncWheelbase_subject);
        tr.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr.setPadding(8, 15, 8, 15);
        ts.addView(tr, tr_params);
        /////////////////////////////
        TableRow tr1 = new TableRow(this);
        TextView ncEngine = new TextView(this);
        ncEngine.setText(newCarInfo.getNcEngine());
        ncEngine.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncEngine.setGravity(Gravity.RIGHT);
        ncEngine.setTypeface(PublicParams.BYekan(this));
        ncEngine.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr1.addView(ncEngine);

        TextView ncEngine_subject = new TextView(this);
        ncEngine_subject.setText("حجم موتور");
        ncEngine_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncEngine_subject.setTextColor(Color.parseColor("#AEAEAE"));
        ncEngine_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr1.addView(ncEngine_subject);
        tr1.setPadding(8, 15, 8, 15);
        ts.addView(tr1, tr_params);
        /////////////////////////////
        TableRow tr2 = new TableRow(this);
        TextView maxSpeed = new TextView(this);
        maxSpeed.setText(newCarInfo.getNcMaxSpeed());
        maxSpeed.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxSpeed.setGravity(Gravity.RIGHT);
        maxSpeed.setTypeface(PublicParams.BYekan(this));
        maxSpeed.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr2.addView(maxSpeed);

        TextView maxSpeed_subject = new TextView(this);
        maxSpeed_subject.setText("حداکثر سرعت");
        maxSpeed_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxSpeed_subject.setTextColor(Color.parseColor("#AEAEAE"));
        maxSpeed_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr2.addView(maxSpeed_subject);
        tr2.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr2.setPadding(8, 15, 8, 15);
        ts.addView(tr2, tr_params);
        /////////////////////////////
        TableRow tr3 = new TableRow(this);
        TextView maxPower = new TextView(this);
        maxPower.setText(newCarInfo.getNcMaxPower());
        maxPower.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxPower.setGravity(Gravity.RIGHT);
        maxPower.setTypeface(PublicParams.BYekan(this));
        maxPower.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr3.addView(maxPower);

        TextView maxPower_subject = new TextView(this);
        maxPower_subject.setText("حداکثر توان");
        maxPower_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxPower_subject.setTextColor(Color.parseColor("#AEAEAE"));
        maxPower_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr3.addView(maxPower_subject);
        tr3.setPadding(8, 15, 8, 15);
        ts.addView(tr3, tr_params);
        /////////////////////////////
        TableRow tr4 = new TableRow(this);
        TextView brakeSystem = new TextView(this);
        brakeSystem.setText(newCarInfo.getNcBrakeSystem());
        brakeSystem.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        brakeSystem.setTextColor(Color.parseColor("#263938"));
        brakeSystem.setGravity(Gravity.RIGHT);
        brakeSystem.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr4.addView(brakeSystem);

        TextView brakeSystem_subject = new TextView(this);
        brakeSystem_subject.setText("سیستم ترمز");
        brakeSystem_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        brakeSystem_subject.setTextColor(Color.parseColor("#AEAEAE"));
        brakeSystem_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr4.addView(brakeSystem_subject);
        tr4.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr4.setPadding(8, 15, 8, 15);
        ts.addView(tr4, tr_params);
        /////////////////////////////
        TableRow tr5 = new TableRow(this);
        TextView tire = new TextView(this);
        tire.setText(newCarInfo.getNcTire());
        tire.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tire.setTextColor(Color.parseColor("#263938"));
        tire.setGravity(Gravity.RIGHT);
        tire.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr5.addView(tire);

        TextView tire_subject = new TextView(this);
        tire_subject.setText("سایز و نوع رینگ و لاستیک");
        tire_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tire_subject.setTextColor(Color.parseColor("#AEAEAE"));
        tire_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr5.addView(tire_subject);
        tr5.setPadding(8, 15, 8, 15);
        ts.addView(tr5, tr_params);
        /////////////////////////////
        TableRow tr6 = new TableRow(this);
        TextView gvm = new TextView(this);
        gvm.setText(newCarInfo.getNcGvm());
        gvm.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        gvm.setTextColor(Color.parseColor("#263938"));
        gvm.setGravity(Gravity.RIGHT);
        gvm.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr6.addView(gvm);

        TextView gvm_subject = new TextView(this);
        gvm_subject.setText("وزن خالص");
        gvm_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        gvm_subject.setTextColor(Color.parseColor("#AEAEAE"));
        gvm_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr6.addView(gvm_subject);
        tr6.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr6.setPadding(8, 15, 8, 15);
        ts.addView(tr6, tr_params);
        /////////////////////////////
        TableRow tr7 = new TableRow(this);
        TextView fuelTank = new TextView(this);
        fuelTank.setText(newCarInfo.getNcFuelTank());
        fuelTank.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        fuelTank.setTextColor(Color.parseColor("#263938"));
        fuelTank.setGravity(Gravity.RIGHT);
        fuelTank.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr7.addView(fuelTank);

        TextView fuelTank_subject = new TextView(this);
        fuelTank_subject.setText("ظرفیت باک");
        fuelTank_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        fuelTank_subject.setTextColor(Color.parseColor("#AEAEAE"));
        fuelTank_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr7.addView(fuelTank_subject);
        tr7.setPadding(8, 15, 8, 15);
        ts.addView(tr7, tr_params);
        /////////////////////////////
        TableRow tr8 = new TableRow(this);
        TextView maxTorque = new TextView(this);
        maxTorque.setText(newCarInfo.getNcMaxTorque());
        maxTorque.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxTorque.setTextColor(Color.parseColor("#263938"));
        maxTorque.setGravity(Gravity.RIGHT);
        maxTorque.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr8.addView(maxTorque);

        TextView maxTorque_subject = new TextView(this);
        maxTorque_subject.setText("حداکثر گشتاور");
        maxTorque_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxTorque_subject.setTextColor(Color.parseColor("#AEAEAE"));
        maxTorque_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr8.addView(maxTorque_subject);
        tr8.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr8.setPadding(8, 15, 8, 15);
        ts.addView(tr8, tr_params);
        /////////////////////////////
        TableRow tr9 = new TableRow(this);
        TextView fuelEfficiency = new TextView(this);
        fuelEfficiency.setText(newCarInfo.getNcFuelEfficiency());
        fuelEfficiency.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        fuelEfficiency.setTextColor(Color.parseColor("#263938"));
        fuelEfficiency.setGravity(Gravity.RIGHT);
        fuelEfficiency.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr9.addView(fuelEfficiency);

        TextView fuelEfficiency_subject = new TextView(this);
        fuelEfficiency_subject.setText("مصرف سوخت");
        fuelEfficiency_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        fuelEfficiency_subject.setTextColor(Color.parseColor("#AEAEAE"));
        fuelEfficiency_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr9.addView(fuelEfficiency_subject);
        tr9.setPadding(8, 15, 8, 15);
        ts.addView(tr9, tr_params);
        /////////////////////////////
        TableRow tr10 = new TableRow(this);
        TextView pollutionStandard = new TextView(this);
        pollutionStandard.setText(newCarInfo.getNcPollutionStandard());
        pollutionStandard.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        pollutionStandard.setTextColor(Color.parseColor("#263938"));
        pollutionStandard.setGravity(Gravity.RIGHT);
        pollutionStandard.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr10.addView(pollutionStandard);

        TextView pollutionStandard_subject = new TextView(this);
        pollutionStandard_subject.setText("استاندارد آلایندگی");
        pollutionStandard_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        pollutionStandard_subject.setTextColor(Color.parseColor("#AEAEAE"));
        pollutionStandard_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr10.addView(pollutionStandard_subject);
        tr10.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr10.setPadding(8, 15, 8, 15);
        ts.addView(tr10, tr_params);
        /////////////////////////////
        TableRow tr11 = new TableRow(this);
        TextView cylinder = new TextView(this);
        cylinder.setText(newCarInfo.getNcCylinder());
        cylinder.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        cylinder.setTextColor(Color.parseColor("#263938"));
        cylinder.setGravity(Gravity.RIGHT);
        cylinder.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr11.addView(cylinder);

        TextView cylinder_subject = new TextView(this);
        cylinder_subject.setText("تعداد سیلندر و سوپاپ");
        cylinder_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        cylinder_subject.setTextColor(Color.parseColor("#AEAEAE"));
        cylinder_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr11.addView(cylinder_subject);
        tr11.setPadding(8, 15, 8, 15);
        ts.addView(tr11, tr_params);
        /////////////////////////////
        TableRow tr12 = new TableRow(this);
        TextView transmissionType = new TextView(this);
        transmissionType.setText(newCarInfo.getNcTransmissionType());
        transmissionType.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        transmissionType.setTextColor(Color.parseColor("#263938"));
        transmissionType.setGravity(Gravity.RIGHT);
        transmissionType.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr12.addView(transmissionType);

        TextView transmissionType_subject = new TextView(this);
        transmissionType_subject.setText("حالت گیرباکس");
        transmissionType_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        transmissionType_subject.setTextColor(Color.parseColor("#AEAEAE"));
        transmissionType_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr12.addView(transmissionType_subject);
        tr12.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr12.setPadding(8, 15, 8, 15);
        ts.addView(tr12, tr_params);
        /////////////////////////////
        TableRow tr13 = new TableRow(this);
        TextView gearBox = new TextView(this);
        gearBox.setText(newCarInfo.getGearBox().getGbGearBox());
        gearBox.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        gearBox.setTextColor(Color.parseColor("#263938"));
        gearBox.setGravity(Gravity.RIGHT);
        gearBox.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr13.addView(gearBox);

        TextView gearBox_subject = new TextView(this);
        gearBox_subject.setText("نوع گیرباکس");
        gearBox_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        gearBox_subject.setTextColor(Color.parseColor("#AEAEAE"));
        gearBox_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr13.addView(gearBox_subject);
        tr13.setPadding(8, 15, 8, 15);
        ts.addView(tr13, tr_params);
        /////////////////////////////
        String colors = "";
        for (int i = 0; i < newCarInfo.getNewCarColor().size(); i++) {
            colors += newCarInfo.getNewCarColor().get(i).getColor().getCSubject();
            if (i < newCarInfo.getNewCarColor().size() - 1) {
                colors += " , ";
            }
        }
        TableRow tr14 = new TableRow(this);
        TextView color = new TextView(this);
        color.setText(colors);
        color.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        color.setTextColor(Color.parseColor("#263938"));
        color.setGravity(Gravity.RIGHT);
        color.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr14.addView(color);

        TextView color_subject = new TextView(this);
        color_subject.setText("رنگ");
        color_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        color_subject.setTextColor(Color.parseColor("#AEAEAE"));
        color_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr14.addView(color_subject);
        tr14.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr14.setPadding(8, 15, 8, 15);
        ts.addView(tr14, tr_params);
        /////////////////////////////
        TableRow tr15 = new TableRow(this);
        TextView length = new TextView(this);
        length.setText(newCarInfo.getNcLength());
        length.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        length.setTextColor(Color.parseColor("#263938"));
        length.setGravity(Gravity.RIGHT);
        length.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr15.addView(length);

        TextView length_subject = new TextView(this);
        length_subject.setText("طول");
        length_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        length_subject.setTextColor(Color.parseColor("#AEAEAE"));
        length_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr15.addView(length_subject);
        tr15.setPadding(8, 15, 8, 15);
        ts.addView(tr15, tr_params);
        /////////////////////////////
        TableRow tr16 = new TableRow(this);
        TextView width = new TextView(this);
        width.setText(newCarInfo.getNcWidth());
        width.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        width.setTextColor(Color.parseColor("#263938"));
        width.setGravity(Gravity.RIGHT);
        width.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr16.addView(width);

        TextView width_subject = new TextView(this);
        width_subject.setText("عرض");
        width_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        width_subject.setTextColor(Color.parseColor("#AEAEAE"));
        width_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr16.addView(width_subject);
        tr16.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr16.setPadding(8, 15, 8, 15);
        ts.addView(tr16, tr_params);
        /////////////////////////////
        TableRow tr17 = new TableRow(this);
        TextView height = new TextView(this);
        height.setText(newCarInfo.getNcHeight());
        height.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        height.setTextColor(Color.parseColor("#263938"));
        height.setGravity(Gravity.RIGHT);
        height.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr17.addView(height);

        TextView height_subject = new TextView(this);
        height_subject.setText("ارتفاع");
        height_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        height_subject.setTextColor(Color.parseColor("#AEAEAE"));
        height_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr17.addView(height_subject);
        tr17.setPadding(8, 15, 8, 15);
        ts.addView(tr17, tr_params);

        conditionImg = (ImageView) findViewById(R.id.condition);
        Log.d("............", PublicParams.BASE_URL + newCarInfo.getNcTermsOfSaleImg());
        Picasso.with(NewCarInfoActivity.this)
                .load(PublicParams.BASE_URL + newCarInfo.getNcTermsOfSaleImg())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.oops)
                .fit()
                .centerInside()
                .into(conditionImg, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {

                    }
                });
        this.initSlider();
        if (!PublicParams.getConnectionState(this)){
            displayNoInternetConnectionError();
        }
    }

    private void existNotTrackedRequest()
    {
        onTrackPm.setDisplayedChild(0);

        NewCarRequestExistsParams params = new NewCarRequestExistsParams();
        params.setNcId(newCarInfo.getId());
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<NewCarRequestExists> request = client.isNotTrackedRequestExist(params);
        request.enqueue(new Callback<NewCarRequestExists>() {
            @Override
            public void onResponse(Call<NewCarRequestExists> call, Response<NewCarRequestExists> response) {
                if (response.code() == 200)
                {
                    NewCarRequestExists ncrExist;
                    ncrExist = response.body();
                    if (ncrExist.getExist() == true)
                    {
                        onTrackPm.setDisplayedChild(2);
                    }
                    else{
                        onTrackPm.setDisplayedChild(1);
                    }
                }else
                {
                    customToast.show(getLayoutInflater(), NewCarInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<NewCarRequestExists> call, Throwable t) {
                customToast.show(getLayoutInflater(), NewCarInfoActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
            }
        });
    }

    public void openImageViewer(View view)
    {
        Intent intent = new Intent(this , PhotoViewer.class);
        intent.putExtra("imgUrl", PublicParams.BASE_URL + newCarInfo.getNcTermsOfSaleImg());
        startActivity(intent);
    }

    private void initSlider()
    {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.removeAllSliders();
        HashMap<String,String> file_maps = new HashMap<String, String>();
        for (int i = 0 ; i < newCarInfo.getNewCarImage().size() ; i++ )
        {
            file_maps.put(i + "", PublicParams.BASE_URL + newCarInfo.getNewCarImage().get(i).getNciPath());
        }
        for(String name : file_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
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
        final ViewTreeObserver observer= mDemoSlider.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mDemoSlider.setLayoutParams(new LinearLayout.LayoutParams(mDemoSlider.getWidth(), mDemoSlider.getWidth()));
                        //Log.d("Log", "Height: ............................................." + mDemoSlider.getWidth());
                    }
                });
        //mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(Color.parseColor("#F39C12"), Color.parseColor("#FFFFFF"));
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
            Intent intent = new Intent(NewCarInfoActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        else{
            Intent intent = new Intent(NewCarInfoActivity.this, NewCarRequestActivity.class);
            String arrayAsString = new Gson().toJson(newCarInfo);
            intent.putExtra("newCarInfo", arrayAsString);
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
