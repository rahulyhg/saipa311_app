package key_team.com.saipa311.AfterSale_services;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCarsRegisterParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.CompanyWithProduct;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.Product;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class MyCarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button openNewCarFormBtn;
    private EditText chassisIdNumber;
    private Spinner company;
    private Spinner product;
    private Spinner buildYear;
    private EditText guaranteeStartDate;
    private EditText licensePlate;
    private EditText engineIdNumber;
    private EditText birthDate;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText mobile;
    private EditText address;
    private ViewFlipper viewFlipper;
    private List<CompanyWithProduct> companyWithProducts = new ArrayList<CompanyWithProduct>();
    private List<Product> selectedProductsList = new ArrayList<Product>();
    private MyProgressDialog progressDialog;
    private static final int EMPTY_MODE = 0;
    private static final int INSERT_MODE = 1;
    private static final int NEW_CAR_MODE = 2;
    private int toolbarMode = EMPTY_MODE;
    private static final int BIRTH_DATE_PICKER = 0;
    private static final int GUARANTEE_DATE_PICKER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        createActionBar();
        init();
        loadBuildYear();
        getAllCompanyWithProduct();
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
                MyCarActivity.this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(false);
        dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
        dpd.show(getFragmentManager(), view.getId() + "");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getTag().equals(R.id.btn_birthDate + ""))
            birthDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        else if (view.getTag().equals(R.id.input_guaranteeStartDate + ""))
            guaranteeStartDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }


    private void register()
    {
        if (validate())
        {
            progressDialog.start();
            UserInfo userInfo = UserInfo.getUserInfo();
            MyCarsRegisterParams params = new MyCarsRegisterParams();
            params.setName(name.getText().toString());
            params.setUserId(userInfo.userId);
            params.setFatherName(fatherName.getText().toString());
            params.setNationalCode(nationalCode.getText().toString());
            params.setBirthDate(birthDate.getText().toString());
            params.setIdNumber(idNumber.getText().toString());
            params.setMobile(mobile.getText().toString());
            params.setPId(selectedProductsList.get(this.product.getSelectedItemPosition() - 1).getId());
            params.setChassisIdNumber(chassisIdNumber.getText().toString());
            params.setAddress(address.getText().toString());
            params.setGuaranteeStartDate(guaranteeStartDate.getText().toString());
            params.setBuildYear(buildYear.getSelectedItem().toString());
            params.setLicensePlatePart1("98");
            params.setLicensePlatePart2("الف");
            params.setLicensePlatePart3("234");
            params.setLicensePlatePart4("ایران");
            params.setLicensePlatePart5("18");
            params.setEngineIdNumber(engineIdNumber.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call request = client.registerMyCar(params);
            request.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    progressDialog.stop();
                    if (response.code() == 200)
                    {
                        changeActivityView(1);
                        showDialog();
                        updateUserInfo();
                    }else
                    {
                        customToast.show(getLayoutInflater(), MyCarActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                    }

                    Log.d("my log" , "..................." + response.code() + " = " + response.message());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressDialog.stop();
                    customToast.show(getLayoutInflater(), MyCarActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            });
        }
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

    private void loadBuildYear()
    {
        PersianCalendar pc = new PersianCalendar();
        String[] items = new String[(pc.getPersianYear() - 1370) + 1];
        items[0] = "";
        for (int i = 0 ; i< pc.getPersianYear() - 1370 ; i++)
        {
            items[i + 1] = (pc.getPersianYear() - i) + "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        buildYear.setAdapter(adapter);
    }

    private void loadProducts(int companyPosition)
    {
        String[] items = null;
        if (companyPosition == 0) {
            items = new String[1];
            items[0] = "";
        }else{
            List<Product> products = this.companyWithProducts.get(companyPosition - 1).getProduct();
            selectedProductsList = products;
            items = new String[products.size() + 1];
            items[0] = "";
            for (int i = 1; i <= products.size(); i++) {
                items[i] = products.get(i - 1).getPrSubject();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        product.setAdapter(adapter);
    }

    private void getAllCompanyWithProduct()
    {
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<CompanyWithProduct>> request = client.getAllCompanyWithProducts();
        request.enqueue(new Callback<List<CompanyWithProduct>>() {
            @Override
            public void onResponse(Call<List<CompanyWithProduct>> call, Response<List<CompanyWithProduct>> response) {
                if (response.code() == 200) {
                    companyWithProducts = response.body();
                    loadCompanies();
                }
            }

            @Override
            public void onFailure(Call<List<CompanyWithProduct>> call, Throwable t) {

            }
        });
    }

    private void loadCompanies()
    {
        String[] items = new String[this.companyWithProducts.size() + 1];
        items[0] = "";
        for (int i = 1;i <= this.companyWithProducts.size(); i++)
        {
            items[i] = this.companyWithProducts.get(i - 1).getCoSubject();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        company.setAdapter(adapter);
    }

    private void init()
    {
        buildYear = (Spinner)findViewById(R.id.input_buildYear);
        product = (Spinner)findViewById(R.id.input_carType);
        company = (Spinner)findViewById(R.id.input_company);
        company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadProducts(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        openNewCarFormBtn = (Button)findViewById(R.id.openNewCarForm);
        openNewCarFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityView(2);
            }
        });
        viewFlipper = (ViewFlipper)findViewById(R.id.myCar_view);
/*        String goldCardInfo_string = getIntent().getExtras().getString("goldCardInfo");
        goldCardInfo = new Gson().fromJson(goldCardInfo_string, GoldCard.class);*/

        UserInfo userInfo = UserInfo.getUserInfo();

        guaranteeStartDate = (EditText)findViewById(R.id.input_guaranteeStartDate);
        engineIdNumber = (EditText)findViewById(R.id.input_engineIdNumber);
        licensePlate = (EditText)findViewById(R.id.input_licensePlate);
        birthDate = (EditText)findViewById(R.id.btn_birthDate);
        chassisIdNumber = (EditText)findViewById(R.id.input_chassisIdNumber);
        name = (EditText)findViewById(R.id.input_name);
        fatherName = (EditText)findViewById(R.id.input_fatherName);
        idNumber = (EditText)findViewById(R.id.input_idNumber);
        nationalCode = (EditText)findViewById(R.id.input_nationalCode);
        address = (EditText)findViewById(R.id.input_address);
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
                            MyCarActivity.this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.setThemeDark(false);
                    dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
                    dpd.show(getFragmentManager(), v.getId() + "");
                }
            }
        });

        guaranteeStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PersianCalendar now = new PersianCalendar();
                    now.setPersianDate(1370, 5, 5);
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            MyCarActivity.this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.setThemeDark(false);
                    dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
                    dpd.show(getFragmentManager(), v.getId() + "");
                }
            }
        });

        progressDialog = new MyProgressDialog(MyCarActivity.this);


    }

    private void changeActivityView(int childId)
    {
        switch (childId)
        {
            case 0: //list empty view
                toolbarMode = EMPTY_MODE;
                break;
            case 1: //my car list view
                toolbarMode = NEW_CAR_MODE;
                break;
            case 2: //insert form view
                toolbarMode = INSERT_MODE;
                break;
        }
        invalidateOptionsMenu();
        viewFlipper.setDisplayedChild(childId);
    }

    public boolean validate() {
        String _birthDate = birthDate.getText().toString();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();
        String _chassisIdNumber = chassisIdNumber.getText().toString();
        int _buildYear = this.buildYear.getSelectedItemPosition();
        int _product = this.product.getSelectedItemPosition();
        String _guarantee = guaranteeStartDate.getText().toString();

        if (_chassisIdNumber.isEmpty()){
            chassisIdNumber.setError("شماره شاسی الزامیست!");
            return false;
        }else{
            birthDate.setError(null);
        }

        if (((String) this.product.getItemAtPosition(_product)).isEmpty()) {
            setSpinnerError(this.product, "انتخاب خودرو الزامیست!");
            return false;
        }

        if (((String) this.buildYear.getItemAtPosition(_buildYear)).isEmpty()) {
            setSpinnerError(this.buildYear, "انتخاب سال ساخت الزامیست!");
            return false;
        }

        if (_guarantee.isEmpty()){
            guaranteeStartDate.setError("تاریخ شروع گارانتی الزامیست!");
            return false;
        }else{
            guaranteeStartDate.setError(null);
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
        getMenuInflater().inflate(R.menu.my_car_activity_menu, menu);
        MenuItem actionInsert = menu.findItem(R.id.action_insert);
        MenuItem actionNew = menu.findItem(R.id.action_new);
        switch (toolbarMode)
        {
            case EMPTY_MODE:
                actionInsert.setVisible(false);
                actionNew.setVisible(false);
                break;
            case NEW_CAR_MODE:
                actionInsert.setVisible(false);
                actionNew.setVisible(true);
                break;
            case INSERT_MODE:
                actionNew.setVisible(false);
                actionInsert.setVisible(true);
                break;
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
