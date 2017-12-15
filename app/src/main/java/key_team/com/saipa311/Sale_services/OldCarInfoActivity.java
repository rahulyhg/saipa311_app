package key_team.com.saipa311.Sale_services;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import java.util.HashMap;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;

/**
 * Created by ammorteza on 12/1/17.
 */
public class OldCarInfoActivity extends AppCompatActivity {
    private OldCar oldCarInfo;
    private TextView title;
    private TextView price;
    public TextView buildYear;
    private TextView description;
    private SliderLayout mDemoSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_car_info);
        this.createActionBar();
        this.getData();
        this.initSlider();
    }

    private void getData()
    {
        String oldCarInfo_string = getIntent().getExtras().getString("oldCarInfo");
        oldCarInfo = new Gson().fromJson(oldCarInfo_string, OldCar.class);

        title = (TextView)findViewById(R.id.title);
        buildYear = (TextView)findViewById(R.id.buildYear);
        price = (TextView)findViewById(R.id.price);
        description = (TextView)findViewById(R.id.description);

        title.setText(oldCarInfo.getProduct().getPrSubject());
        buildYear.setText(oldCarInfo.getOcBuildYear());
        buildYear.setTypeface(PublicParams.BYekan(this));
        price.setText(oldCarInfo.getOcPrice());
        price.setTypeface(PublicParams.BYekan(this));

        description.setText(oldCarInfo.getOcDescription());

        TableLayout ts = (TableLayout) findViewById(R.id.technicalSpecifications);
        TableLayout.LayoutParams tr_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView ncWheelbase = new TextView(this);
        ncWheelbase.setText(oldCarInfo.getOcFuelType());
        ncWheelbase.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncWheelbase.setGravity(Gravity.RIGHT);
        ncWheelbase.setTypeface(PublicParams.BYekan(this));
        ncWheelbase.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr.addView(ncWheelbase);

        TextView ncWheelbase_subject = new TextView(this);
        ncWheelbase_subject.setText("نوع سوخت");
        ncWheelbase_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncWheelbase_subject.setGravity(Gravity.RIGHT);
        ncWheelbase_subject.setTextColor(Color.parseColor("#AEAEAE"));
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr.addView(ncWheelbase_subject);
        tr.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr.setPadding(8 , 15 , 8 , 15);
        ts.addView(tr, tr_params);
        /////////////////////////////
        TableRow tr1 = new TableRow(this);
        TextView ncEngine = new TextView(this);
        ncEngine.setText(oldCarInfo.getGearBox().getGbGearBox());
        ncEngine.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ncEngine.setGravity(Gravity.RIGHT);
        ncEngine.setTypeface(PublicParams.BYekan(this));
        ncEngine.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr1.addView(ncEngine);

        TextView ncEngine_subject = new TextView(this);
        ncEngine_subject.setText("'گیرباکس");
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
        maxSpeed.setText(oldCarInfo.getOcKmOfOperation());
        maxSpeed.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxSpeed.setGravity(Gravity.RIGHT);
        maxSpeed.setTypeface(PublicParams.BYekan(this));
        maxSpeed.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr2.addView(maxSpeed);

        TextView maxSpeed_subject = new TextView(this);
        maxSpeed_subject.setText("کارکرد");
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
        maxPower.setText(oldCarInfo.getOcBody());
        maxPower.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        maxPower.setGravity(Gravity.RIGHT);
        maxPower.setTypeface(PublicParams.BYekan(this));
        maxPower.setTextColor(Color.parseColor("#263938"));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr3.addView(maxPower);

        TextView maxPower_subject = new TextView(this);
        maxPower_subject.setText("بدنه");
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
        brakeSystem.setText(oldCarInfo.getOcColor());
        brakeSystem.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        brakeSystem.setTextColor(Color.parseColor("#263938"));
        brakeSystem.setGravity(Gravity.RIGHT);
        brakeSystem.setTypeface(PublicParams.BYekan(this));
        //value.setTextSize(R.dimen.textSizeSmall);
        tr4.addView(brakeSystem);

        TextView brakeSystem_subject = new TextView(this);
        brakeSystem_subject.setText("رنگ");
        brakeSystem_subject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        brakeSystem_subject.setTextColor(Color.parseColor("#AEAEAE"));
        brakeSystem_subject.setGravity(Gravity.RIGHT);
        //subject.setTextSize(R.dimen.textSizeSmall);
        tr4.addView(brakeSystem_subject);
        tr4.setBackgroundColor(getResources().getColor(R.color.background_color_light));
        tr4.setPadding(8, 15, 8, 15);
        ts.addView(tr4, tr_params);
    }

    private void initSlider()
    {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,String> file_maps = new HashMap<String, String>();
        for (int i = 0 ; i < oldCarInfo.getOldCarImage().size() ; i++ )
        {
            file_maps.put(i + "", PublicParams.BASE_URL + "pic/cars/" + oldCarInfo.getOldCarImage().get(i).getOciPath());
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
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
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

    public void callTest(View view)
    {

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
}