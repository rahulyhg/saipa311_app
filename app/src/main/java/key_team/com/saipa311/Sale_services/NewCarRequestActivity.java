package key_team.com.saipa311.Sale_services;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ViewFlipper;

import com.activeandroid.query.Delete;
import com.google.gson.Gson;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_request);
        init();
    }

    public void register(View view)
    {

    }

    private void init()
    {
        String newCarInfo_string = getIntent().getExtras().getString("newCarInfo");
        newCarInfo = new Gson().fromJson(newCarInfo_string, NewCar.class);

        UserInfo userInfo = UserInfo.getUserInfo();

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

    public void validate() {
/*        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty()) {
            emailText.setError("نام کاربری الزامیست!");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError("کلمه عبور الزامیست!");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;*/
    }
}
