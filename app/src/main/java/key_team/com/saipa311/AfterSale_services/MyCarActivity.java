package key_team.com.saipa311.AfterSale_services;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCar;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCarsRegisterParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.CompanyWithProduct;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.Product;
import key_team.com.saipa311.Sale_services.NewCarInfoActivity;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.SquareImageView;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class MyCarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private List<MyCar> myCars;
    private RecyclerView recyclerView;
    private MyCarsAdapter myCarAdapter;
    private ArrayList<myCar> myCarList;
    private Button openNewCarFormBtn;
    private EditText chassisIdNumber;
    private Spinner company;
    private Spinner product;
    private Spinner buildYear;
    private EditText guaranteeStartDate;
    private EditText license_part1;
    private Spinner license_part2;
    private EditText license_part3;
    private EditText license_part5;
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
    private static final int PROGRESS_MODE = 3;
    private int toolbarMode = EMPTY_MODE;
    private boolean recursive = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        createActionBar();
        init();
        fetchMyCars();
        loadBuildYear();
        loadLicensePlateLetter();
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
        if (view.getId() == R.id.input_guaranteeStartDate)
        {
            now.setPersianDate(1390, 1, 1);
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    MyCarActivity.this,
                    now.getPersianYear(),
                    now.getPersianMonth(),
                    now.getPersianDay()
            );
            dpd.setThemeDark(false);
            dpd.setYearRange(1385, new PersianCalendar().getPersianYear());
            dpd.show(getFragmentManager(), view.getId() + "");
        }else{
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
    }

    private void fetchMyCars()
    {
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<MyCar>> request = client.fetchMyCars();
        request.enqueue(new Callback<List<MyCar>>() {
            @Override
            public void onResponse(Call<List<MyCar>> call, Response<List<MyCar>> response) {
                if (response.code() == 200) {
                    myCars = response.body();
                    loadMyCars();
                } else {
                    changeActivityView(1);
                    customToast.show(getLayoutInflater(), MyCarActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<List<MyCar>> call, Throwable t) {
                customToast.show(getLayoutInflater(), MyCarActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
            }
        });
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
            params.setLicensePlatePart1(license_part1.getText().toString());
            params.setLicensePlatePart2(license_part2.getSelectedItem().toString());
            params.setLicensePlatePart3(license_part3.getText().toString());
            params.setLicensePlatePart4("ایران");
            params.setLicensePlatePart5(license_part5.getText().toString());
            params.setEngineIdNumber(engineIdNumber.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call<List<MyCar>> request = client.registerMyCar(params);
            request.enqueue(new Callback<List<MyCar>>() {
                @Override
                public void onResponse(Call<List<MyCar>> call, Response<List<MyCar>> response) {
                    progressDialog.stop();
                    if (response.code() == 200)
                    {
                        myCars = response.body();
                        loadMyCars();
                        updateUserInfo();
                    }else if (response.code() == 409)
                    {
                        customToast.show(getLayoutInflater(), MyCarActivity.this, "خودرو تکراری است!");
                    }
                    else
                    {
                        customToast.show(getLayoutInflater(), MyCarActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                    }

                    Log.d("my log" , "..................." + response.code() + " = " + response.message());
                }

                @Override
                public void onFailure(Call<List<MyCar>> call, Throwable t) {
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

    private void loadLicensePlateLetter()
    {
        String[] items = {"الف" , "ب" , "پ" , "ت" , "ث" , "ج" , "چ" , "ح" , "خ" , "د" , "ذ" , "ر" , "ز" , "ژ" , "س" , "ش" , "ص" , "ض" , "ط" , "ظ" , "ع" , "غ" , "ف" , "ق" , "ک" , "گ" , "ل" , "م" , "ن" , "و" , "ه" , "ی"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        license_part2.setAdapter(adapter);
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
        if (getIntent().hasExtra("recursive"))
            recursive = getIntent().getExtras().getBoolean("recursive");
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
                resetInsertForm();
                changeActivityView(3);
            }
        });
        viewFlipper = (ViewFlipper)findViewById(R.id.myCar_view);


        guaranteeStartDate = (EditText)findViewById(R.id.input_guaranteeStartDate);
        engineIdNumber = (EditText)findViewById(R.id.input_engineIdNumber);
        license_part1 = (EditText)findViewById(R.id.input_licensePlate_part1);
        license_part2 = (Spinner)findViewById(R.id.input_licensePlate_part2);
        license_part3 = (EditText)findViewById(R.id.input_licensePlate_part3);
        license_part5 = (EditText)findViewById(R.id.input_licensePlate_part5);
        birthDate = (EditText)findViewById(R.id.btn_birthDate);
        chassisIdNumber = (EditText)findViewById(R.id.input_chassisIdNumber);
        name = (EditText)findViewById(R.id.input_name);
        fatherName = (EditText)findViewById(R.id.input_fatherName);
        idNumber = (EditText)findViewById(R.id.input_idNumber);
        nationalCode = (EditText)findViewById(R.id.input_nationalCode);
        address = (EditText)findViewById(R.id.input_address);
        mobile = (EditText)findViewById(R.id.input_mobile);

        resetInsertForm();

        progressDialog = new MyProgressDialog(MyCarActivity.this);

        recyclerView = (RecyclerView)findViewById(R.id.my_car_recycler_view);
        myCarList = new ArrayList<>();
        myCarAdapter = new MyCarsAdapter(myCarList);
        recyclerView.setAdapter(myCarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void resetInsertForm()
    {
        guaranteeStartDate.getText().clear();
        product.setSelection(0);
        company.setSelection(0);
        buildYear.setSelection(0);
        engineIdNumber.getText().clear();
        license_part1.getText().clear();
        license_part2.setSelection(0);
        license_part3.getText().clear();
        license_part5.getText().clear();
        chassisIdNumber.getText().clear();
        chassisIdNumber.requestFocus();

        UserInfo userInfo = UserInfo.getUserInfo();
        name.setText(userInfo.name);
        fatherName.setText(userInfo.fatherName);
        idNumber.setText(userInfo.idNumber);
        nationalCode.setText(userInfo.nationalCode);
        mobile.setText(userInfo.mobile);
        address.setText(userInfo.address);
        birthDate.setText(userInfo.birthDate == null ? "" : userInfo.birthDate);
    }


    private void changeActivityView(int childId)
    {
        switch (childId)
        {
            case 0: //insert form view
                toolbarMode = PROGRESS_MODE;
                break;
            case 1: //list empty view
                toolbarMode = EMPTY_MODE;
                break;
            case 2: //my car list view
                toolbarMode = NEW_CAR_MODE;
                break;
            case 3: //insert form view
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
        String _engineIdNumber = engineIdNumber.getText().toString();

        String _lp_part1 = license_part1.getText().toString();
        String _lp_part3 = license_part3.getText().toString();
        String _lp_part5 = license_part5.getText().toString();
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

        if (_engineIdNumber.isEmpty()){
            engineIdNumber.setError("شماره موتور الزامیست!");
            return false;
        }else{
            engineIdNumber.setError(null);
        }

        if (_lp_part1.isEmpty() || _lp_part3.isEmpty() || _lp_part5.isEmpty())
        {
            license_part5.setError("شماره پلاک الزامیست!");
            return false;
        }else{
            license_part5.setError(null);
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
            case PROGRESS_MODE:
                actionInsert.setVisible(false);
                actionNew.setVisible(false);
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
                if (toolbarMode == INSERT_MODE) {
                    if (myCars.size() == 0)
                        changeActivityView(1);
                    else
                        changeActivityView(2);
                }
                else
                    finish();
                return true;
            case R.id.action_insert:
                register();
                return true;
            case R.id.action_new:
                resetInsertForm();
                changeActivityView(3);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (toolbarMode == INSERT_MODE) {
            if (myCars.size() == 0)
                changeActivityView(1);
            else
                changeActivityView(2);
        }
        else
            super.onBackPressed();
    }

    private void loadMyCars() {
        if (myCars.size() > 0) {
            myCarAdapter.clearData();
            for (int i = 0; i < myCars.size(); i++) {
                myCar a = new myCar(myCars.get(i).getProduct().getPrSubject(),
                        myCars.get(i).getMcChassisIdNumber(),
                        myCars.get(i).getMcEngineIdNumber(),
                        myCars.get(i).getMcBuildYear(),
                        myCars.get(i).getMcLicensePlatePart1(),
                        myCars.get(i).getMcLicensePlatePart2(),
                        myCars.get(i).getMcLicensePlatePart3(),
                        myCars.get(i).getMcLicensePlatePart4(),
                        myCars.get(i).getMcLicensePlatePart5(),
                        myCars.get(i).getMcGuaranteeStartDate());
                myCarAdapter.addItem(a);
            }
            changeActivityView(2);
        }else{
            changeActivityView(1);
        }
    }

    ////////////////////////// my car adapter ///////////////////////////////////////
    public class myCar {
        String _title;
        String _chassis;
        String _engineNumber;
        String _buildYear;
        String _licensePlate_part1;
        String _licensePlate_part2;
        String _licensePlate_part3;
        String _licensePlate_part4;
        String _licensePlate_part5;
        String _guaranteeStartYear;
        public myCar(String name,
                     String chassis,
                     String  enginNumber,
                     String buildYear,
                     String licensePlate_part1,
                     String licensePlate_part2,
                     String licensePlate_part3,
                     String licensePlate_part4,
                     String licensePlate_part5,
                     String guaranteeStartYear) {
            this._title = name;
            this._chassis = chassis;
            this._engineNumber = enginNumber;
            this._buildYear = buildYear;
            this._licensePlate_part1 = licensePlate_part1;
            this._licensePlate_part2 = licensePlate_part2;
            this._licensePlate_part3 = licensePlate_part3;
            this._licensePlate_part4 = licensePlate_part4;
            this._licensePlate_part5 = licensePlate_part5;
            this._guaranteeStartYear = guaranteeStartYear;
        }

        public String getTitle() {
            return this._title;
        }

        public String getChassis() {
            return this._chassis;
        }

        public String getEnginNumber() {
            return this._engineNumber;
        }

        public String getBuildYear() {
            return this._buildYear;
        }

        public String getLicensePlate_part1() {
            return _licensePlate_part1;
        }

        public String getLicensePlate_part2() {
            return _licensePlate_part2;
        }

        public String getLicensePlate_part3() {
            return _licensePlate_part3;
        }

        public String getLicensePlate_part4() {
            return _licensePlate_part4;
        }

        public String getLicensePlate_part5() {
            return _licensePlate_part5;
        }

        public String getGuaranteeStartYear() {
            return _guaranteeStartYear;
        }
    }

    public class MyCarsAdapter extends RecyclerView.Adapter<MyCarsAdapter.MyCarViewHolder> {

        private ArrayList<myCar> dataSet;
        public class MyCarViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public TextView chassis;
            public TextView engineNumber;
            public TextView buildYear;
            public TextView licensePlate_part1;
            public TextView licensePlate_part2;
            public TextView licensePlate_part3;
            public TextView licensePlate_part4;
            public TextView licensePlate_part5;
            public TextView guaranteeStartYear;

            public MyCarViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                chassis = (TextView) view.findViewById(R.id.chassisIdNumber);
                engineNumber = (TextView) view.findViewById(R.id.engineIdNumber);
                buildYear = (TextView) view.findViewById(R.id.buildYear);
                buildYear.setTypeface(PublicParams.BYekan(MyCarActivity.this));
                licensePlate_part1 = (TextView) view.findViewById(R.id.licensePlate_part1);
                licensePlate_part1.setTypeface(PublicParams.BYekan(MyCarActivity.this));
                licensePlate_part2 = (TextView) view.findViewById(R.id.licensePlate_part2);
                licensePlate_part3 = (TextView) view.findViewById(R.id.licensePlate_part3);
                licensePlate_part3.setTypeface(PublicParams.BYekan(MyCarActivity.this));
                licensePlate_part4 = (TextView) view.findViewById(R.id.licensePlate_part4);
                licensePlate_part5 = (TextView) view.findViewById(R.id.licensePlate_part5);
                licensePlate_part5.setTypeface(PublicParams.BYekan(MyCarActivity.this));
                guaranteeStartYear = (TextView) view.findViewById(R.id.guaranteeStartYear);
                guaranteeStartYear.setTypeface(PublicParams.BYekan(MyCarActivity.this));
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (recursive == true) {
                            Intent resultIntent = new Intent();
                            String arrayAsString = new Gson().toJson(myCars.get(getAdapterPosition()));
                            resultIntent.putExtra("selectedMyCar", arrayAsString);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                        //Log.d("my log" , "............................ " + getAdapterPosition());
/*                        Intent intent = new Intent(getActivity(), NewCarInfoActivity.class);
                        String arrayAsString = new Gson().toJson(newCarData.get(getAdapterPosition()));
                        intent.putExtra("newCarInfo", arrayAsString);
                        startActivity(intent);*/
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            myCarAdapter.notifyDataSetChanged();
        }
        public MyCarsAdapter(ArrayList<myCar> data) {
            this.dataSet = data;
        }

        @Override
        public MyCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_car_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            MyCarViewHolder myViewHolder = new MyCarViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyCarViewHolder holder, final int listPosition) {
            holder.title.setText(dataSet.get(listPosition).getTitle());
            holder.chassis.setText(dataSet.get(listPosition).getChassis());
            holder.engineNumber.setText(dataSet.get(listPosition).getEnginNumber());
            holder.buildYear.setText(dataSet.get(listPosition).getBuildYear());
            holder.licensePlate_part1.setText(dataSet.get(listPosition).getLicensePlate_part1());
            holder.licensePlate_part2.setText(dataSet.get(listPosition).getLicensePlate_part2());
            holder.licensePlate_part3.setText(dataSet.get(listPosition).getLicensePlate_part3());
            holder.licensePlate_part4.setText(dataSet.get(listPosition).getLicensePlate_part4());
            holder.licensePlate_part5.setText(dataSet.get(listPosition).getLicensePlate_part5());
            holder.guaranteeStartYear.setText(dataSet.get(listPosition).getGuaranteeStartYear());
        }

        public void addItem(myCar dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
