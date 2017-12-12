package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;

import java.util.HashMap;
import key_team.com.saipa311.PhotoViewer;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;

/**
 * Created by ammorteza on 12/1/17.
 */
public class NewCarInfoActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NewCar newCarInfo;
    private TextView title;
    private TextView isConditions;
    private TextView price;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_info);
        this.createActionBar();
        this.getData();
        this.initSlider();
    }

    private void getData()
    {
        String newCarInfo_string = getIntent().getExtras().getString("newCarInfo");
        newCarInfo = new Gson().fromJson(newCarInfo_string, NewCar.class);

        title = (TextView)findViewById(R.id.title);
        isConditions = (TextView)findViewById(R.id.isConditions);
        price = (TextView)findViewById(R.id.price);
        description = (TextView)findViewById(R.id.description);

        title.setText(newCarInfo.getNcSubject());
        if (newCarInfo.getNcConditions() == 0)
            isConditions.setVisibility(View.GONE);
        else
            isConditions.setVisibility(View.VISIBLE);

        price.setText(newCarInfo.getNcPrice());
        price.setTypeface(PublicParams.BYekan(this));

        description.setText(newCarInfo.getNcDescription());
    }

    public void openImageViewer(View view)
    {
        Intent intent = new Intent(this , PhotoViewer.class);
        startActivity(intent);
    }

    private void initSlider()
    {
        SliderLayout mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,String> file_maps = new HashMap<String, String>();
        for (int i = 0 ; i < newCarInfo.getNewCarImage().size() ; i++ )
        {
            file_maps.put(i + "", PublicParams.BASE_URL + "pic/cars/" + newCarInfo.getNewCarImage().get(i).getNciPatch());
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
