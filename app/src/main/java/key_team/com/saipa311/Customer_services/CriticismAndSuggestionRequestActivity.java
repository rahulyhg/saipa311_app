package key_team.com.saipa311.Customer_services;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestRequestParams;
import key_team.com.saipa311.Customer_services.JsonSchema.CriticismAndSuggestionRequestParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class CriticismAndSuggestionRequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText birthDate;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText mobile;
    private EditText address;
    private EditText description;
    private MyProgressDialog progressDialog;
    private boolean HIDE_INSERT_ACTION_MENU = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_request);
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
                CriticismAndSuggestionRequestActivity.this,
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

    private void register()
    {
        if (validate())
        {
            progressDialog.start();
            CriticismAndSuggestionRequestParams params = new CriticismAndSuggestionRequestParams();
            params.setRepId(1);
            params.setDescription(description.getText().toString());
            params.setName(name.getText().toString());
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(birthDate.getText().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setMobile(mobile.getText().toString());
            params.setAddress(address.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call<Void> request = client.registerCriticismAndSuggestion(params);
            request.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    progressDialog.stop();
                    if (response.code() == 200)
                    {
                        HIDE_INSERT_ACTION_MENU = true;
                        invalidateOptionsMenu();
                        showDialog("از توجه شما سپاسگزاریم، امید است که نظرات سازنده شما ما را در بهبود نحوه خدمت رسانی به شما مشتریان گرامی همراهی نماید.");
                        updateUserInfo();
                    }else{
                        customToast.show(getLayoutInflater(), CriticismAndSuggestionRequestActivity.this, "خطایی رخ داده است، دوباره تلاش کنید");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    progressDialog.stop();
                    customToast.show(getLayoutInflater(), CriticismAndSuggestionRequestActivity.this, "خطایی رخ داده است، دوباره تلاش کنید");
                }
            });
        }
    }

    private void showDialog(String pm)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(CriticismAndSuggestionRequestActivity.this);
        TextView title = new TextView(CriticismAndSuggestionRequestActivity.this);
        title.setPadding(0, 30, 40, 30);
        title.setGravity(Gravity.RIGHT);
        //title.setTextSize((int) getResources().getDimension(R.dimen.textSizeXSmaller));
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setText("مشتری گرامی");
        builder.setCustomTitle(title);
        builder.setMessage(pm);
        builder.setCancelable(false);
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void updateUserInfo()
    {
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
        title.setPadding(0 , 30 , 45 , 10);
        title.setGravity(Gravity.RIGHT);
        //title.setTextSize((int) getResources().getDimension(R.dimen.textSizeXSmaller));
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface() , Typeface.BOLD);
        title.setText("مشتری گرامی");
        builder.setCustomTitle(title);
        builder.setMessage(R.string.register_pm);
        builder.setCancelable(false);
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
                            CriticismAndSuggestionRequestActivity.this,
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

        progressDialog = new MyProgressDialog(CriticismAndSuggestionRequestActivity.this);
    }

    public boolean validate() {
        String _birthDate = birthDate.getText().toString();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();
        String _description = description.getText().toString();

        if (_description.isEmpty()){
            description.setError("شرح الزامیست!");
            return false;
        }else{
            description.setError(null);
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
