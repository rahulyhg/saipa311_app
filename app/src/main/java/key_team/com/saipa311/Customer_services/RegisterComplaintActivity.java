package key_team.com.saipa311.Customer_services;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCar;
import key_team.com.saipa311.AfterSale_services.MyCarActivity;
import key_team.com.saipa311.Customer_services.JsonSchema.Complaint;
import key_team.com.saipa311.Customer_services.JsonSchema.ComplaintType;
import key_team.com.saipa311.Customer_services.JsonSchema.CriticismAndSuggestionRequestParams;
import key_team.com.saipa311.Customer_services.JsonSchema.RegisterComplaintRequestParams;
import key_team.com.saipa311.Customer_services.JsonSchema.SelectedComplaint;
import key_team.com.saipa311.Customer_services.JsonSchema.TrackingCode;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyCustomApplication;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.CompanyWithProduct;
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
public class RegisterComplaintActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private List<ComplaintType> complaintTypes;
    private List<SelectedComplaint> selectedComplaints = new ArrayList<SelectedComplaint>();
    private Button selectMyCar;
    private MyCar selectedMyCar = null;
    private TextView selectedCarSubject;
    private EditText birthDate;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText mobile;
    private EditText address;

    private EditText description;
    private EditText receptionDate;
    private EditText receptionNumber;
    private EditText kmOfOperation;
    private MyProgressDialog progressDialog;
    private boolean HIDE_INSERT_ACTION_MENU = false;
    private int LOGIN_REQUEST = 1;
    private int MY_CAR_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_request);
        createActionBar();
        getAllComplaintTypes();
        init();
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

    public void getAllComplaintTypes()
    {
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<ComplaintType>> request = client.getAllComplainttypes();
        request.enqueue(new Callback<List<ComplaintType>>() {
            @Override
            public void onResponse(Call<List<ComplaintType>> call, Response<List<ComplaintType>> response) {
                if (response.code() == 200) {
                    complaintTypes = response.body();
                    loadComplaintTypes();
                }

                Log.d("my log" , ".......................... get all complaint type:" + response.code());
            }

            @Override
            public void onFailure(Call<List<ComplaintType>> call, Throwable t) {
                Log.d("my log" , ".......................... get all complaint type:" + t.getMessage());
            }
        });
    }

    private void loadComplaintTypes()
    {
        LinearLayout complaintTypeLayout = (LinearLayout)findViewById(R.id.complaintType);
        for (int i=0;i<complaintTypes.size();i++)
        {
            final List<Complaint> complaints = complaintTypes.get(i).getComplaint();
            TextView ctTitle = new TextView(this);
            if (i > 0) {
                LinearLayout.LayoutParams tLParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tLParams.setMargins(0, 30, 0, 0);
                ctTitle.setLayoutParams(tLParams);
            }
            complaintTypeLayout.addView(ctTitle);
            ctTitle.setText(complaintTypes.get(i).getCtSubject());
            for (int j=0;j<complaints.size();j++)
            {
                final int curent_j = j;
                Switch sw = new Switch(this);
                sw.setText(complaints.get(j).getCSubject());
                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SelectedComplaint selectedComplaint = new SelectedComplaint();
                        if (isChecked) {
                            selectedComplaint.setId(complaints.get(curent_j).getId());
                            selectedComplaints.add(selectedComplaint);
                        } else {
                            for (int i = 0; i < selectedComplaints.size(); i++) {
                                SelectedComplaint temp = selectedComplaints.get(i);
                                if (temp.getId() == complaints.get(curent_j).getId()) {
                                    selectedComplaints.remove(i);
                                    break;
                                }
                            }
                        }
                        Log.d("my log" , ".......................... selectedComplaint list size:" + selectedComplaints.size());
                    }
                });
                complaintTypeLayout.addView(sw);
            }
        }
    }

    public void openDatePicker(View  view)
    {
        PersianCalendar now = new PersianCalendar();
        if (view.getId() == R.id.input_receptionDate)
        {
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    RegisterComplaintActivity.this,
                    now.getPersianYear(),
                    now.getPersianMonth(),
                    now.getPersianDay()
            );
            dpd.setThemeDark(false);
            dpd.setYearRange(1395, new PersianCalendar().getPersianYear());
            dpd.show(getFragmentManager(), view.getId() + "");
        }else{
            now.setPersianDate(1370, 5, 5);
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    RegisterComplaintActivity.this,
                    now.getPersianYear(),
                    now.getPersianMonth(),
                    now.getPersianDay()
            );
            dpd.setThemeDark(false);
            dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
            dpd.show(getFragmentManager(), view.getId() + "");
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
        if (view.getTag().equals(R.id.btn_birthDate + ""))
            birthDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        else if (view.getTag().equals(R.id.input_receptionDate + ""))
            receptionDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }

    private void register()
    {
        if (validate())
        {
            progressDialog.start();
            RegisterComplaintRequestParams params = new RegisterComplaintRequestParams();
            params.setRepId(1);
            params.setName(name.getText().toString());
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(birthDate.getText().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setMobile(mobile.getText().toString());
            params.setAddress(address.getText().toString());

            params.setDescription(description.getText().toString());
            params.setReceptionNumber(receptionNumber.getText().toString());
            params.setReceptionDate(receptionDate.getText().toString());
            params.setKmOfOperation(kmOfOperation.getText().toString());
            params.setMcId(selectedMyCar.getId());
            params.setSelectedComplaints(selectedComplaints);
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call<TrackingCode> request = client.registerComplaintRequest(params);
            request.enqueue(new Callback<TrackingCode>() {
                @Override
                public void onResponse(Call<TrackingCode> call, Response<TrackingCode> response) {
                    progressDialog.stop();
                    if (response.code() == 200)
                    {
                        TrackingCode trackingCode = response.body();
                        HIDE_INSERT_ACTION_MENU = true;
                        invalidateOptionsMenu();
                        showTrackingCodeDialog(trackingCode.getTrackingCode());
                        updateUserInfo();
                    }else{
                        customToast.show(getLayoutInflater(), RegisterComplaintActivity.this, "خطایی رخ داده است، دوباره تلاش کنید");
                    }
                }

                @Override
                public void onFailure(Call<TrackingCode> call, Throwable t) {
                    progressDialog.stop();
                    customToast.show(getLayoutInflater(), RegisterComplaintActivity.this, "خطایی رخ داده است، دوباره تلاش کنید");
                }
            });
        }
    }

    private void showTrackingCodeDialog(String _trackingCode)
    {
        TextView trackingCodePm;
        TextView trackingCode;
        View alertLayout = getLayoutInflater().inflate(R.layout.tracking_code_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        trackingCodePm = (TextView)alertLayout.findViewById(R.id.trackingCode_pm);
        trackingCode = (TextView)alertLayout.findViewById(R.id.trachingCode);
        trackingCodePm.setText("مشتری گرامی، دراسرع وقت به شکایت شما رسیدگی خواهد شد، شما می تواند با استفاده از کد رهگیری ذیل نتیجه را پیگیری نمایید.");
        trackingCode.setText(_trackingCode);
        builder.setView(alertLayout);
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

/*    private void showDialog()
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
    }*/

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
        selectMyCar = (Button)findViewById(R.id.openMyCarList);
        selectedCarSubject = (TextView)findViewById(R.id.myCarSubject);
        receptionDate = (EditText)findViewById(R.id.input_receptionDate);
        receptionNumber = (EditText)findViewById(R.id.input_receptionNumber);
        kmOfOperation = (EditText)findViewById(R.id.input_kmOfOperation);
        description = (EditText)findViewById(R.id.input_description);

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
                            RegisterComplaintActivity.this,
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

        selectMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mayCar = new Intent(RegisterComplaintActivity.this, MyCarActivity.class);
                mayCar.putExtra("recursive", true);
                startActivityForResult(mayCar, MY_CAR_REQUEST);
            }
        });

        progressDialog = new MyProgressDialog(RegisterComplaintActivity.this);
    }

    public boolean validate() {
        String _birthDate = birthDate.getText().toString();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();

        String _kmOfOperation = kmOfOperation.getText().toString();
        String _receptionDate = receptionDate.getText().toString();
        String _receptionNumber = receptionNumber.getText().toString();
        String _description = description.getText().toString();

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

        if (_mobile.isEmpty()) {
            mobile.setError("شماره همراه الزامبست!");
            return false;
        } else {
            mobile.setError(null);
        }

        if (_birthDate.isEmpty()){
            birthDate.setError("تاریخ تولد الزامیست");
            return false;
        }else{
            birthDate.setError(null);
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

        if (selectedMyCar == null)
        {
            customToast.show(getLayoutInflater(), this, "لطفا خودروی خود را از لیست خودروی من انتخاب کنید!");
            return false;
        }

        if (_receptionNumber.isEmpty())
        {
            receptionNumber.setError("شماره پذیرش الزامیست!");
            return false;
        }
        else
        {
            receptionNumber.setError(null);
        }

        if (_receptionDate.isEmpty())
        {
            receptionDate.setError("تاریخ پذیرش الزامیست!");
            return false;
        }else{
            receptionDate.setError(null);
        }

        if (_kmOfOperation.isEmpty())
        {
            kmOfOperation.setError("کارکرد خودرو الزامیست!");
            return false;
        }else{
            kmOfOperation.setError(null);
        }

        if (selectedComplaints.size() == 0)
        {
            customToast.show(getLayoutInflater() , this , "حداقل یک مورد از لیست نوع شکایت را انتخاب کنید!");
            return false;
        }

        if (_description.isEmpty()){
            description.setError("شرح الزامیست!");
            return false;
        }else{
            description.setError(null);
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST)
        {

        }else if (requestCode == MY_CAR_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                String myCar_string = data.getStringExtra("selectedMyCar");
                selectedMyCar = new Gson().fromJson(myCar_string, MyCar.class);
                selectedCarSubject.setText("خودرو: " + selectedMyCar.getProduct().getPrSubject());
            }
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
