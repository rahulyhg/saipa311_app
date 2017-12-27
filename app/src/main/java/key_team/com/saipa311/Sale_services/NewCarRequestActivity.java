package key_team.com.saipa311.Sale_services;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.activeandroid.query.Delete;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.Auth.JsonSchema.RegisterUserRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RegisterUserResult;
import key_team.com.saipa311.Auth.JsonSchema.TokenInfo;
import key_team.com.saipa311.Auth.JsonSchema.TokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.Auth.JsonSchema.UserActivationRequestParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarOption;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarOptionsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.SelectedOption;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class NewCarRequestActivity extends AppCompatActivity {
    private NewCar newCarInfo;
    private Spinner carColor;
    private Spinner bYear;
    private Spinner bMonth;
    private Spinner bDay;
    private EditText subject;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText mobile;
    private Switch haveLicensePlate;
    private EditText address;
    private EditText description;
    private ViewFlipper viewFlipper;
    private List<SelectedOption> selectedOptions = new ArrayList<SelectedOption>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_request);
        init();
        loadOptions();
    }

    private void loadOptions()
    {
        NewCarOptionsParams params = new NewCarOptionsParams();
        params.setRepId(1);
        params.setPId(newCarInfo.getProduct().getId());
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<NewCarOption>> request = client.fetchOptionWithRepAndPid(params);
        request.enqueue(new Callback<List<NewCarOption>>() {
            @Override
            public void onResponse(Call<List<NewCarOption>> call, Response<List<NewCarOption>> response) {
                final List<NewCarOption> newCarOption = response.body();
                LinearLayout options = (LinearLayout)findViewById(R.id.options);

                for (int i=0;i<newCarOption.size();i++)
                {
                    final int curent_i = i;
                    Switch sw = new Switch(NewCarRequestActivity.this);
                    sw.setText(newCarOption.get(i).getOption().getOName());
                    sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            SelectedOption so = new SelectedOption();
                            so.setId(newCarOption.get(curent_i).getId());
                            if (isChecked) {
                                selectedOptions.add(so);
                            }else{
                                selectedOptions.remove(so);
                            }
                        }
                    });
                    options.addView(sw);
                }
            }

            @Override
            public void onFailure(Call<List<NewCarOption>> call, Throwable t) {

            }
        });
    }

    public void register(View view)
    {
        if (validate())
        {
            UserInfo userInfo = UserInfo.getUserInfo();
            NewCarRequestRequestParams params = new NewCarRequestRequestParams();
            params.setUserId(userInfo.userId);
            //Log.d("my log", "................... user id" + userInfo.userId);
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(bYear.getSelectedItem().toString() + "/" + bMonth.getSelectedItem().toString() + "/" + bDay.getSelectedItem().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setNcId(newCarInfo.getId());
            params.setNcrHaveLicensePlate(haveLicensePlate.isChecked() == true ? 1 : 0);
            params.setNcrAddress(address.getText().toString());
            params.setNcrDescription(description.getText().toString());
            params.setSelectedOptions(selectedOptions);
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call request = client.registerNewCarRequest(params);
            request.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == 200)
                    {
                        viewFlipper.setDisplayedChild(1);
                    }else
                    {
                        customToast.show(getLayoutInflater(), NewCarRequestActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                    }

                    Log.d("my log" , "..................." + response.code() + " = " + response.message());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    customToast.show(getLayoutInflater(), NewCarRequestActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
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
        String newCarInfo_string = getIntent().getExtras().getString("newCarInfo");
        newCarInfo = new Gson().fromJson(newCarInfo_string, NewCar.class);

        UserInfo userInfo = UserInfo.getUserInfo();

        viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        carColor = (Spinner)findViewById(R.id.input_color);
        bYear = (Spinner)findViewById(R.id.input_year);
        bMonth = (Spinner)findViewById(R.id.input_month);
        bDay = (Spinner)findViewById(R.id.input_day);
        subject = (EditText)findViewById(R.id.input_subject);
        name = (EditText)findViewById(R.id.input_name);
        fatherName = (EditText)findViewById(R.id.input_fatherName);
        idNumber = (EditText)findViewById(R.id.input_idNumber);
        nationalCode = (EditText)findViewById(R.id.input_nationalCode);
        haveLicensePlate = (Switch)findViewById(R.id.input_haveLicensePlate);
        address = (EditText)findViewById(R.id.input_address);
        description = (EditText)findViewById(R.id.input_description);
        mobile = (EditText)findViewById(R.id.input_mobile);

        subject.setText(newCarInfo.getProduct().getPrSubject());
        name.setText(userInfo.name);
        fatherName.setText(userInfo.fatherName);
        idNumber.setText(userInfo.idNumber);
        nationalCode.setText(userInfo.nationalCode);
        mobile.setText(userInfo.mobile);


        String[] items = new String[]{"سفید", "آبی", "مشکی"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        carColor.setAdapter(adapter);

        String[] years = new String[100];
        for (int i = 1320 ; i<1420 ; i++)
            years[i - 1320] = i + "";
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        bYear.setAdapter(yearAdapter);

        String[] months = new String[12];
        for (int i = 0 ; i<12 ; i++)
            months[i] = (i + 1) + "";
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        bMonth.setAdapter(monthAdapter);

        String[] days = new String[31];
        for (int i = 0 ; i<31 ; i++)
            days[i] = (i + 1) + "";
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        bDay.setAdapter(dayAdapter);
    }

    public boolean validate() {
        boolean valid = true;

        int color = carColor.getSelectedItemPosition();
        int year = bYear.getSelectedItemPosition();
        int month = bMonth.getSelectedItemPosition();
        int day = bDay.getSelectedItemPosition();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();

        if (((String) carColor.getItemAtPosition(color)).isEmpty()) {
            setSpinnerError(carColor , "انتخاب رنگ الزامیست!");
            valid = false;
        }

        if (((String) bYear.getItemAtPosition(year)).isEmpty()) {
            setSpinnerError(bYear , "انتخاب سال الزامیست!");
            valid = false;
        }

        if (((String) bMonth.getItemAtPosition(month)).isEmpty()) {
            setSpinnerError(bMonth , "انتخاب ماه الزامی است!");
            valid = false;
        }

        if (((String) bDay.getItemAtPosition(day)).isEmpty()) {
            setSpinnerError(bDay , "انتخاب روز الزامیست!");
            valid = false;
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

    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }
}
