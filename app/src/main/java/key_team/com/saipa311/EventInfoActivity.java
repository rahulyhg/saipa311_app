package key_team.com.saipa311;

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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestExistsParams;
import key_team.com.saipa311.Sale_services.OldCarRequestActivity;
import key_team.com.saipa311.Services.JsonSchema.Events.ChangeViewStateRequestParams;
import key_team.com.saipa311.Services.JsonSchema.Events.Event;
import key_team.com.saipa311.Services.JsonSchema.Events.EventRequestParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class EventInfoActivity extends AppCompatActivity {
    private Event eventInfo;
    private TextView title;
    private TextView repInfo;
    private TextView description;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
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

    private void changeViewState()
    {
        ChangeViewStateRequestParams params = new ChangeViewStateRequestParams();
        params.setId(eventInfo.getViewed().get(0).getId());
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call request = client.changeEventViewState(params);
        request.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    private void getData()
    {
        String eventInfo_string = getIntent().getExtras().getString("eventInfo");
        eventInfo = new Gson().fromJson(eventInfo_string, Event.class);
        if (PublicParams.getConnectionState(this)) {
            title = (TextView) findViewById(R.id.title);
            repInfo = (TextView) findViewById(R.id.repInto);
            description = (TextView) findViewById(R.id.description);

            title.setText(eventInfo.getESubject());
            repInfo.setText("نمایندگی " + eventInfo.getRepresentation().getRCode() + " " + eventInfo.getRepresentation().getRName());

            description.setText(eventInfo.getEDescription());
            this.initSlider();
            this.changeViewState();
        }else{
            this.initSlider();
            displayNoInternetConnectionError();
        }
    }

    private void initSlider()
    {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.removeAllSliders();
        HashMap<String,String> file_maps = new HashMap<String, String>();
        for (int i = 0 ; i < eventInfo.getEventImg().size() ; i++ )
        {
            file_maps.put(i + "", PublicParams.BASE_URL + eventInfo.getEventImg().get(i).getEiPath());
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
