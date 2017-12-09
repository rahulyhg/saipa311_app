package key_team.com.saipa311.Sale_services;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.IOException;
import java.util.HashMap;

import key_team.com.saipa311.MainActivity;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.ServiceGenerator;
import key_team.com.saipa311.Sale_services.JsonSchema.StoreClient;
import key_team.com.saipa311.Sale_services.JsonSchema.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class NewCarInfoActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_info);
        this.createActionBar();
        this.initSlider();
        Test test = new Test();
        test.setName("shondol");
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<Test> myTest = client.myTest(test);
/*        try {
            Test test = myTest.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        myTest.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                Test myItem = response.body();
                Log.d("my log" , "..............." + myItem.getName());
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                //Handle failure
            }
        });

    }

    private void initSlider()
    {
        SliderLayout mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("۱",R.drawable.new_car1);
        file_maps.put("۲",R.drawable.new_car2);
        file_maps.put("۳", R.drawable.new_car3);
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
