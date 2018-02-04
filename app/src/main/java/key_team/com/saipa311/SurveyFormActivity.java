package key_team.com.saipa311;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExistsParams;
import key_team.com.saipa311.Sale_services.NewCarRequestActivity;
import key_team.com.saipa311.Services.JsonSchema.Surveys.CauseOfReferral;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SurveyForm;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SurveyFormRequestParams;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SurveyQuestion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class SurveyFormActivity extends AppCompatActivity {
    private int surveyFormId;
    private SurveyForm surveyForm;
    private LinearLayout causeOfReferralItems;
    private LinearLayout questionForm;
    private List<CauseOfReferral> causeOfReferrals;
    private MyProgressDialog myProgressDialog;
    private Spinner education;
    private Spinner fuelType;
    private TextView repInfo;
    private TextView serviceSubject;
    private List<SurveyQuestion> surveyQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_form);
        MyCustomApplication.appCreate();
        this.createActionBar();
        this.getSurveyFormId();
        this.init();
        this.fetchSurveyForm();
    }

    private void init()
    {
        causeOfReferralItems = (LinearLayout)findViewById(R.id.input_causeOfReferralItems);
        myProgressDialog = new MyProgressDialog(SurveyFormActivity.this);
        education = (Spinner)findViewById(R.id.input_education);
        fuelType = (Spinner)findViewById(R.id.input_fuelType);
        repInfo = (TextView)findViewById(R.id._repInfo);
        serviceSubject = (TextView)findViewById(R.id._serviceSubject);
        questionForm = (LinearLayout)findViewById(R.id.questionForm);
        loadEducations();
        loadFuelTypes();

    }

    private void loadEducations()
    {
        String[] items = new String[7];
        items[0] = "";
        items[1] = "دکتری";
        items[2] = "فوق لیسانس";
        items[3] = "لیسانس";
        items[4] = "فوق دیپلم";
        items[5] = "دیپلم";
        items[6] = "زیر دیپلم";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        education.setAdapter(adapter);
    }

    private void loadFuelTypes()
    {
        String[] items = new String[4];
        items[0] = "";
        items[1] = " بنزین سوز";
        items[2] = "دوگانه سوز";
        items[3] = " گازوئیل سو";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        fuelType.setAdapter(adapter);
    }

    private void fetchSurveyForm()
    {
        myProgressDialog.start();
        SurveyFormRequestParams params = new SurveyFormRequestParams();
        params.setId(surveyFormId);
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<SurveyForm> form = client.fetchSurveyForm(params);
        form.enqueue(new Callback<SurveyForm>() {
            @Override
            public void onResponse(Call<SurveyForm> call, Response<SurveyForm> response) {
                if (response.code() == 200) {
                    surveyForm = response.body();
                    repInfo.setText("نمایندگی " + surveyForm.getRepresentation().getRCode() + " " + surveyForm.getRepresentation().getRName());
                    serviceSubject.setText(surveyForm.getSfSubject());
                    fetchCauseOfReferrals();
                    fetchQuestions();
                } else {
                    myProgressDialog.stop();
                }
            }

            @Override
            public void onFailure(Call<SurveyForm> call, Throwable t) {
                myProgressDialog.stop();
            }
        });
    }

    private void fetchCauseOfReferrals()
    {
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<CauseOfReferral>> request = client.fetchAllCauseOfReferrals();
        request.enqueue(new Callback<List<CauseOfReferral>>() {
            @Override
            public void onResponse(Call<List<CauseOfReferral>> call, Response<List<CauseOfReferral>> response) {
                if (response.code() == 200)
                {
                    causeOfReferrals = response.body();
                    loadCauseOfReferralItems();
                    myProgressDialog.stop();
                }else{
                    myProgressDialog.stop();
                }
            }

            @Override
            public void onFailure(Call<List<CauseOfReferral>> call, Throwable t) {
                myProgressDialog.stop();
            }
        });
    }

    private void fetchQuestions()
    {
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<SurveyQuestion>> questions = client.fetchAllSurveyQuestions();
        questions.enqueue(new Callback<List<SurveyQuestion>>() {
            @Override
            public void onResponse(Call<List<SurveyQuestion>> call, Response<List<SurveyQuestion>> response) {
                if (response.code() == 200)
                {
                    surveyQuestions = response.body();
                    loadQuestions();
                }else{
                    myProgressDialog.stop();
                }
            }

            @Override
            public void onFailure(Call<List<SurveyQuestion>> call, Throwable t) {
                myProgressDialog.stop();
            }
        });
    }

    private void loadQuestions()
    {
        for (int i=0;i<surveyQuestions.size();i++)
        {
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView title = new TextView(this);
            title.setText(surveyQuestions.get(i).getSqSubject());
            title.setGravity(Gravity.RIGHT);
            questionForm.addView(title , titleParams);

            RadioGroup rg = new RadioGroup(this);
            rg.setOrientation(RadioGroup.HORIZONTAL);
            rg.setGravity(Gravity.RIGHT);
            for (int j=0 ; j<surveyQuestions.get(i).getSurveyQuestionForm().size() ; j++)
            {
                RadioButton rb  = new RadioButton(this);
                rb.setText(surveyQuestions.get(i).getSurveyQuestionForm().get(j).getSurveyAnswer().getSaSubject());
                rg.addView(rb);
            }

            questionForm.addView(rg);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadCauseOfReferralItems()
    {
        for (int i=0; i<causeOfReferrals.size();i++)
        {
            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkParams.gravity = Gravity.RIGHT;
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            checkBox.setText(causeOfReferrals.get(i).getCorSubject());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            causeOfReferralItems.addView(checkBox , checkParams);
        }
    }

    private void getSurveyFormId()
    {
        Intent intent = getIntent();
        surveyFormId = intent.getIntExtra("surveyFormId", 0);
        customToast.show(getLayoutInflater() , this , "sId:" + surveyFormId);
    }

    @Override
    protected void onDestroy() {
        MyCustomApplication.appDestroy();
        super.onDestroy();
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
