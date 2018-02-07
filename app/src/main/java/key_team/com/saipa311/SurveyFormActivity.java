package key_team.com.saipa311;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.SelectedOption;
import key_team.com.saipa311.Sale_services.NewCarRequestActivity;
import key_team.com.saipa311.Services.JsonSchema.Surveys.CauseOfReferral;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SelectedCauseOfReferral;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SelectedUserSurveyAnswer;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SurveyForm;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SurveyFormRequestParams;
import key_team.com.saipa311.Services.JsonSchema.Surveys.SurveyQuestion;
import key_team.com.saipa311.Services.JsonSchema.Surveys.UserSurveyAnswerRegisterRequestParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class SurveyFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private int surveyFormId;
    private EditText birthDate;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText mobile;
    private EditText address;
    private EditText description;
    private SurveyForm surveyForm;
    private LinearLayout causeOfReferralItems;
    private LinearLayout questionForm;
    private List<CauseOfReferral> causeOfReferrals;
    private MyProgressDialog myProgressDialog;
    private Spinner education;
    private Spinner fuelType;
    private TextView repInfo;
    private TextView serviceSubject;
    private RadioGroup secondVisit;
    private String selectedSecondVisit = "";
    private RadioGroup sex;
    private String selectedSex = "";
    private MyProgressDialog progressDialog;
    private List<SurveyQuestion> surveyQuestions;
    private List<SelectedCauseOfReferral> selectedCauseOfReferrals = new ArrayList<SelectedCauseOfReferral>();
    private List<SelectedUserSurveyAnswer> selectedUserSurveyAnswers = new ArrayList<SelectedUserSurveyAnswer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_form);
        MyCustomApplication.appCreate();
        this.createActionBar();
        this.getSurveyFormId();
        this.init();
    }

    private void init()
    {
        UserInfo userInfo = UserInfo.getUserInfo();

        birthDate = (EditText)findViewById(R.id.btn_birthDate);
        name = (EditText)findViewById(R.id.input_name);
        fatherName = (EditText)findViewById(R.id.input_fatherName);
        idNumber = (EditText)findViewById(R.id.input_idNumber);
        nationalCode = (EditText)findViewById(R.id.input_nationalCode);
        address = (EditText)findViewById(R.id.input_address);
        description = (EditText)findViewById(R.id.input_description);
        mobile = (EditText)findViewById(R.id.input_mobile);

        name.setText(userInfo.name);
        fatherName.setText(userInfo.fatherName);
        idNumber.setText(userInfo.idNumber);
        nationalCode.setText(userInfo.nationalCode);
        mobile.setText(userInfo.mobile);
        address.setText(userInfo.address);
        birthDate.setText(userInfo.birthDate == null ? "" : userInfo.birthDate);
        birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PersianCalendar now = new PersianCalendar();
                    now.setPersianDate(1370, 5, 5);
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            SurveyFormActivity.this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.setThemeDark(false);
                    dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
                    dpd.show(getFragmentManager(), "tpd");
                }
            }
        });
        causeOfReferralItems = (LinearLayout)findViewById(R.id.input_causeOfReferralItems);
        myProgressDialog = new MyProgressDialog(SurveyFormActivity.this);
        education = (Spinner)findViewById(R.id.input_education);
        fuelType = (Spinner)findViewById(R.id.input_fuelType);
        repInfo = (TextView)findViewById(R.id._repInfo);
        serviceSubject = (TextView)findViewById(R.id._serviceSubject);
        questionForm = (LinearLayout)findViewById(R.id.questionForm);
        secondVisit = (RadioGroup)findViewById(R.id.input_second_visit);
        secondVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedSecondVisit = ((RadioButton) findViewById(checkedId)).getText().toString();
            }
        });
        sex = (RadioGroup)findViewById(R.id.input_sex);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedSex = ((RadioButton) findViewById(checkedId)).getText().toString();
            }
        });
        loadEducations();
        loadFuelTypes();
        progressDialog = new MyProgressDialog(SurveyFormActivity.this);

        this.fetchSurveyForm();

        if (!PublicParams.getConnectionState(this))
        {
            displayNoInternetConnectionError();
        }

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
                init();
                dTemp.dismiss();
            }
        });
    }

    public void openDatePicker(View  view)
    {
        PersianCalendar now = new PersianCalendar();
        now.setPersianDate(1370, 5, 5);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SurveyFormActivity.this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(false);
        dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
        dpd.show(getFragmentManager(), "tpd");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        birthDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        //Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
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
                    repInfo.setText("نمایندگی " + surveyForm.getRepresentation().getRCode() + " - " + surveyForm.getRepresentation().getRName());
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
                if (response.code() == 200) {
                    causeOfReferrals = response.body();
                    loadCauseOfReferralItems();
                    myProgressDialog.stop();
                } else {
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

    private void register()
    {
        if (validate())
        {
            progressDialog.start();
            UserSurveyAnswerRegisterRequestParams params = new UserSurveyAnswerRegisterRequestParams();
            params.setName(name.getText().toString());
            params.setId(surveyFormId);
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(birthDate.getText().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setMobile(mobile.getText().toString());
            params.setAddress(address.getText().toString());
            params.setDescription(description.getText().toString());
            params.setEducation(education.getSelectedItem().toString());
            params.setSex(selectedSex);
            params.setFuelType(fuelType.getSelectedItem().toString());
            params.setSecondVisit(selectedSecondVisit);
            params.setSelectedCauseOfReferrals(selectedCauseOfReferrals);
            params.setSelectedUserSurveyAnswers(selectedUserSurveyAnswers);
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call request = client.registerUserSurveyAnswer(params);
            request.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    progressDialog.stop();
                    if (response.code() == 200)
                    {
                        updateUserInfo();
                        showDialog();
                    }else
                    {
                        customToast.show(getLayoutInflater(), SurveyFormActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                    }

                    Log.d("my log" , "..................." + response.code() + " = " + response.message());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressDialog.stop();
                    customToast.show(getLayoutInflater(), SurveyFormActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            });
        }
    }

    private void updateUserInfo()
    {
        //Log.d("my log" , "..................... name : " + name.getText().toString());
        UserInfo userInfo = UserInfo.getUserInfo();
        userInfo.address = address.getText().toString();
        userInfo.nationalCode = nationalCode.getText().toString();
        userInfo.idNumber = idNumber.getText().toString();
        userInfo.fatherName = fatherName.getText().toString();
        userInfo.birthDate = birthDate.getText().toString();
        userInfo.mobile = mobile.getText().toString();
        userInfo.name = name.getText().toString();
        userInfo.save();
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setPadding(0, 30, 45, 10);
        title.setGravity(Gravity.RIGHT);
        //title.setTextSize((int) getResources().getDimension(R.dimen.textSizeXSmaller));
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setText("مشتری گرامی");
        builder.setCustomTitle(title);
        builder.setCancelable(false);
        builder.setMessage(R.string.registerUserSurveyAnswer_pm);
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    public boolean validate() {
        boolean valid = true;
        String _birthDate = birthDate.getText().toString();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();

        if (selectedSecondVisit.isEmpty()){
            valid = false;
        }

        if (education.getSelectedItemPosition() == 0){
            valid = false;
        }

        if (selectedSex.isEmpty()){
            valid = false;
        }

        if (fuelType.getSelectedItemPosition() == 0){
            valid = false;
        }

        if (selectedCauseOfReferrals.size() == 0){
            valid = false;
        }

        if (selectedUserSurveyAnswers.size() < surveyQuestions.size()){
            valid = false;
        }

        if (_birthDate.isEmpty()){
            valid = false;
        }

        if (_name.isEmpty()) {
            valid = false;
        }

        if (_fatherName.isEmpty()) {
            valid = false;
        }

        if (_idNumber.isEmpty()) {
            valid = false;
        }

        if (_nationalCode.isEmpty()) {
            valid = false;
        }

        if (_mobile.isEmpty()) {
            valid = false;
        }

        if (_address.isEmpty()) {
            valid = false;
        }

        if (valid == false)
        {
            customToast.show(getLayoutInflater() , this , "لطفا فرم را به طور کامل تکمیل کنید!");
        }
        return valid;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadQuestions()
    {
        for (int i=0;i<surveyQuestions.size();i++)
        {
            final int curent_i = i;
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView title = new TextView(this);
            title.setText(surveyQuestions.get(i).getSqSubject());
            title.setTextColor(getResources().getColor(android.R.color.black));
            title.setGravity(Gravity.RIGHT);
            questionForm.addView(title, titleParams);

            RadioGroup rg = new RadioGroup(this);
            rg.setOrientation(RadioGroup.VERTICAL);
            rg.setGravity(Gravity.RIGHT);
            for (int j=0 ; j<surveyQuestions.get(i).getSurveyQuestionForm().size() ; j++)
            {
                final int curent_j = j;
                RadioButton rb  = new RadioButton(this);
                rb.setText(surveyQuestions.get(i).getSurveyQuestionForm().get(j).getSurveyAnswer().getSaSubject());
                rb.setId(surveyQuestions.get(i).getSurveyQuestionForm().get(j).getId());
                rb.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.d("my log" , "............... radio id:" + buttonView.getId());
                        SelectedUserSurveyAnswer SUSA = new SelectedUserSurveyAnswer();
                        if (isChecked) {
                            SUSA.setId(surveyQuestions.get(curent_i).getSurveyQuestionForm().get(curent_j).getId());
                            Log.d("my log", "................... add getSurveyQuestionForm in list" + curent_j);
                            selectedUserSurveyAnswers.add(SUSA);
                        } else {
                            for (int i = 0; i < selectedUserSurveyAnswers.size(); i++) {
                                SelectedUserSurveyAnswer temp = selectedUserSurveyAnswers.get(i);
                                if (temp.getId() == surveyQuestions.get(curent_i).getSurveyQuestionForm().get(curent_j).getId()) {
                                    selectedUserSurveyAnswers.remove(i);
                                    Log.d("my log", "................... remove getSurveyQuestionForm in list" + selectedUserSurveyAnswers.size());
                                    break;
                                }
                            }
                        }
                    }
                });
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
            final int curent_i = i;
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
                    SelectedCauseOfReferral SCOR = new SelectedCauseOfReferral();
                    if (isChecked) {
                        SCOR.setId(causeOfReferrals.get(curent_i).getId());
                        Log.d("my log", "................... add causeOfReferral in list" + curent_i);
                        selectedCauseOfReferrals.add(SCOR);
                    } else {
                        for (int i = 0; i < selectedCauseOfReferrals.size(); i++) {
                            SelectedCauseOfReferral temp = selectedCauseOfReferrals.get(i);
                            if (temp.getId() == causeOfReferrals.get(curent_i).getId()) {
                                selectedCauseOfReferrals.remove(i);
                                Log.d("my log", "................... remove causeOfReferral in list" + selectedCauseOfReferrals.size());
                                break;
                            }
                        }
                    }
                }
            });
            causeOfReferralItems.addView(checkBox , checkParams);
        }
    }

    private void getSurveyFormId()
    {
        Intent intent = getIntent();
        surveyFormId = intent.getIntExtra("surveyFormId", 0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_menu, menu);
        MenuItem actionInsert = menu.findItem(R.id.action_insert);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_insert:
                register();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
