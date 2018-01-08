package key_team.com.saipa311.Sale_services;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.activeandroid.query.Delete;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import key_team.com.saipa311.Auth.JsonSchema.RegisterUserRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RegisterUserResult;
import key_team.com.saipa311.Auth.JsonSchema.TokenInfo;
import key_team.com.saipa311.Auth.JsonSchema.TokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.Auth.JsonSchema.UserActivationRequestParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.PublicParams;
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
public class NewCarRequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private NewCar newCarInfo;
    private Spinner carColor;
    private EditText birthDate;
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
    private boolean HIDE_INSERT_ACTION_MENU = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_request);
        createActionBar();
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
                LinearLayout options = (LinearLayout) findViewById(R.id.options);

                for (int i = 0; i < newCarOption.size(); i++) {
                    final int curent_i = i;
                    Switch sw = new Switch(NewCarRequestActivity.this);
                    sw.setText(newCarOption.get(i).getOption().getOName());
                    sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            SelectedOption so = new SelectedOption();

                            if (isChecked) {
                                so.setId(newCarOption.get(curent_i).getId());
                                Log.d("my log", "................... add option in list" + curent_i);
                                selectedOptions.add(so);
                            } else {
                                for (int i = 0; i < selectedOptions.size(); i++) {
                                    SelectedOption temp = selectedOptions.get(i);
                                    if (temp.getId() == newCarOption.get(curent_i).getId()) {
                                        selectedOptions.remove(i);
                                        Log.d("my log", "................... remove option in list" + selectedOptions.size());
                                        break;
                                    }
                                }
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

    public void openDatePicker(View  view)
    {
        PersianCalendar now = new PersianCalendar();
        now.setPersianDate(1370, 5, 5);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                NewCarRequestActivity.this,
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

    private void createActionBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void register()
    {
        if (validate())
        {
            UserInfo userInfo = UserInfo.getUserInfo();
            NewCarRequestRequestParams params = new NewCarRequestRequestParams();
            params.setUserId(userInfo.userId);
            //Log.d("my log", "................... user id" + userInfo.userId);
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(birthDate.getText().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setMobile(mobile.getText().toString());
            params.setNcId(newCarInfo.getId());
            params.setNcrHaveLicensePlate(haveLicensePlate.isChecked() == true ? 1 : 0);
            params.setNccId(newCarInfo.getNewCarColor().get(carColor.getSelectedItemPosition() - 1).getId());
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
                        HIDE_INSERT_ACTION_MENU = true;
                        invalidateOptionsMenu();
                        viewFlipper.setDisplayedChild(1);
                        showDialog();
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

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setPadding(0 , 30 , 45 , 10);
        title.setGravity(Gravity.RIGHT);
        //title.setTextSize((int) getResources().getDimension(R.dimen.textSizeXSmaller));
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface() , Typeface.BOLD);
        title.setText("مشتری گرامی");
        builder.setCustomTitle(title);
        builder.setCancelable(false);
        builder.setMessage(R.string.register_pm);
        builder.setPositiveButton("منتظر می مانم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void init()
    {
        String newCarInfo_string = getIntent().getExtras().getString("newCarInfo");
        newCarInfo = new Gson().fromJson(newCarInfo_string, NewCar.class);

        UserInfo userInfo = UserInfo.getUserInfo();
        viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        carColor = (Spinner)findViewById(R.id.input_color);
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
        haveLicensePlate = (Switch)findViewById(R.id.input_haveLicensePlate);
        address = (EditText)findViewById(R.id.input_address);
        address.setTypeface(PublicParams.BYekan(this));
        description = (EditText)findViewById(R.id.input_description);
        description.setTypeface(PublicParams.BYekan(this));
        mobile = (EditText)findViewById(R.id.input_mobile);
        mobile.setTypeface(PublicParams.BYekan(this));

        subject.setText(newCarInfo.getProduct().getPrSubject());
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
                            NewCarRequestActivity.this,
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

        String[] items = new String[this.newCarInfo.getNewCarColor().size() + 1];
        items[0] = "";
        for (int i = 1;i <= this.newCarInfo.getNewCarColor().size(); i++)
        {
            items[i] = this.newCarInfo.getNewCarColor().get(i - 1).getColor().getCSubject();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        carColor.setAdapter(adapter);
    }

    public boolean validate() {
        int color = carColor.getSelectedItemPosition();
        String _birthDate = birthDate.getText().toString();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();

        if (((String) carColor.getItemAtPosition(color)).isEmpty()) {
            setSpinnerError(carColor , "انتخاب رنگ الزامیست!");
            return false;
        }

        if (_birthDate.isEmpty()){
            birthDate.setError("تاریخ تولد الزامیست");
            return false;
        }else{
            birthDate.setError(null);
        }

        if (_name.isEmpty()) {
            name.setError("نام و نام خانوادگی الزامیست!");
            return false;
        } else {
            name.setError(null);
        }

        if (_fatherName.isEmpty()) {
            fatherName.setError("نام پدر الزامیست!");
            return false;
        } else {
            fatherName.setError(null);
        }

        if (_idNumber.isEmpty()) {
            idNumber.setError("شماره شناسنامه الزامیست!");
            return false;
        } else {
            idNumber.setError(null);
        }

        if (_nationalCode.isEmpty()) {
            nationalCode.setError("کد ملی الزامیست!");
            return false;
        } else {
            nationalCode.setError(null);
        }

        if (_address.isEmpty()) {
            address.setError("آدرس الزامیست!");
            return false;
        } else {
            address.setError(null);
        }

        if (_mobile.isEmpty()) {
            mobile.setError("شماره همراه الزامبست!");
            return false;
        } else {
            mobile.setError(null);
        }

        return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_menu, menu);
        MenuItem actionInsert = menu.findItem(R.id.action_insert);
        if (HIDE_INSERT_ACTION_MENU)
        {
            actionInsert.setVisible(false);
        }
        return true;
    }

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
            case R.id.action_insert:
                register();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
