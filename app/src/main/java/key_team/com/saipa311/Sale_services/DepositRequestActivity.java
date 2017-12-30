package key_team.com.saipa311.Sale_services;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestRequestParams;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class DepositRequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Deposit depositInfo;
    private EditText birthDate;
    private EditText subject;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText mobile;
    private EditText address;
    private EditText description;
    private ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_car_request);
        createActionBar();
        init();
    }

    private void createActionBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void openDatePicker(View  view)
    {
        PersianCalendar now = new PersianCalendar();
        now.setPersianDate(1370, 5, 5);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                DepositRequestActivity.this,
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
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        birthDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        //Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
    }

    public void register(View view)
    {
        if (validate())
        {
            UserInfo userInfo = UserInfo.getUserInfo();
            DepositRequestRequestParams params = new DepositRequestRequestParams();
            params.setUserId(userInfo.userId);
            //Log.d("my log", "................... user id" + userInfo.userId);
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(birthDate.getText().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setMobile(mobile.getText().toString());
            params.setDId(depositInfo.getId());
            params.setDrAddress(address.getText().toString());
            params.setDrDescription(description.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call request = client.registerDepositRequest(params);
            request.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == 200)
                    {
                        viewFlipper.setDisplayedChild(1);
                    }else
                    {
                        customToast.show(getLayoutInflater(), DepositRequestActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                    }

                    Log.d("my log" , "..................." + response.code() + " = " + response.message());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    customToast.show(getLayoutInflater(), DepositRequestActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            });
        }
    }

    public void wait(View view)
    {
        finish();
    }

    private void init()
    {
        String depositInfo_string = getIntent().getExtras().getString("depositInfo");
        depositInfo = new Gson().fromJson(depositInfo_string, Deposit.class);

        UserInfo userInfo = UserInfo.getUserInfo();
        viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        birthDate = (EditText)findViewById(R.id.btn_birthDate);
        birthDate.setTypeface(PublicParams.BYekan(this));
        subject = (EditText)findViewById(R.id.input_subject);
        subject.setTypeface(PublicParams.BYekan(this));
        name = (EditText)findViewById(R.id.input_name);
        name.setTypeface(PublicParams.BYekan(this));
        fatherName = (EditText)findViewById(R.id.input_fatherName);
        fatherName.setTypeface(PublicParams.BYekan(this));
        idNumber = (EditText)findViewById(R.id.input_idNumber);
        idNumber.setTypeface(PublicParams.BYekan(this));
        nationalCode = (EditText)findViewById(R.id.input_nationalCode);
        nationalCode.setTypeface(PublicParams.BYekan(this));
        address = (EditText)findViewById(R.id.input_address);
        address.setTypeface(PublicParams.BYekan(this));
        description = (EditText)findViewById(R.id.input_description);
        description.setTypeface(PublicParams.BYekan(this));
        mobile = (EditText)findViewById(R.id.input_mobile);
        mobile.setTypeface(PublicParams.BYekan(this));

        subject.setText(depositInfo.getDCar());
        name.setText(userInfo.name);
        fatherName.setText(userInfo.fatherName);
        idNumber.setText(userInfo.idNumber);
        nationalCode.setText(userInfo.nationalCode);
        mobile.setText(userInfo.mobile);

        birthDate.setText(userInfo.birthDate == null ? "" : userInfo.birthDate);
        birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PersianCalendar now = new PersianCalendar();
                    now.setPersianDate(1370, 5, 5);
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DepositRequestActivity.this,
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

        if (_birthDate.isEmpty()){
            birthDate.setError("تاریخ تولد الزامیست");
        }else{
            birthDate.setError(null);
        }

        if (_name.isEmpty()) {
            name.setError("نام و نام خانوادگی الزامیست!");
            valid = false;
        } else {
            name.setError(null);
        }

        if (_fatherName.isEmpty()) {
            fatherName.setError("نام پدر الزامیست!");
            valid = false;
        } else {
            fatherName.setError(null);
        }

        if (_idNumber.isEmpty()) {
            idNumber.setError("شماره شناسنامه الزامیست!");
            valid = false;
        } else {
            idNumber.setError(null);
        }

        if (_nationalCode.isEmpty()) {
            nationalCode.setError("کد ملی الزامیست!");
            valid = false;
        } else {
            nationalCode.setError(null);
        }

        if (_address.isEmpty()) {
            address.setError("آدرس الزامیست!");
            valid = false;
        } else {
            address.setError(null);
        }

        if (_mobile.isEmpty()) {
            mobile.setError("شماره همراه الزامبست!");
            valid = false;
        } else {
            mobile.setError(null);
        }

        return valid;
    }

/*    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
