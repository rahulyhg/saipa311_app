package key_team.com.saipa311.Sale_services;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.CompanyWithProduct;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.Product;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlans;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class OutDatedCarRequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private OutDatedCarChangePlans changePlan;
    private Spinner buildYear;
    private Switch carInTheParking;
    private EditText subject;
    private EditText km;
    private EditText chassisIdNumber;
    private EditText name;
    private EditText fatherName;
    private EditText idNumber;
    private EditText nationalCode;
    private EditText birthDate;
    private EditText mobile;
    private EditText description;
    private SliderLayout mDemoSlider;
    private MyProgressDialog progressDialog;
    HashMap<String,String> file_maps = new HashMap<String, String>();
    private boolean HIDE_INSERT_ACTION_MENU = false;
    private int GALLERY_REQUEST = 1;
    private int CAMERA_CAPTURE = 2;
    private int PIC_CROP = 3;
    private Uri tempCropImage;
    private int actionInsertColor = R.color.colorPrimary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_dated_car_request);
        createActionBar();
        init();
        loadBuildYear();
        initSlider();
    }

    public void openDatePicker(View  view)
    {
        PersianCalendar now = new PersianCalendar();
        now.setPersianDate(1370, 5, 5);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                OutDatedCarRequestActivity.this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(false);
        dpd.setYearRange(1300, new PersianCalendar().getPersianYear() - 1);
        dpd.show(getFragmentManager(), "tpd");
    }

    private void loadBuildYear()
    {
        PersianCalendar pc = new PersianCalendar();
        int start = Integer.parseInt(changePlan.getCcpStartYear());
        int end = Integer.parseInt(changePlan.getCcpEndYear());
        String[] items = new String[(end - start) + 1];
        items[0] = "";
        for (int i = start ; i<= end ; i++)
        {
            items[i - start] = i + "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        buildYear.setAdapter(adapter);
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
            progressDialog.start();
            UserInfo userInfo = UserInfo.getUserInfo();
            MultipartBody.Part[] files = new MultipartBody.Part[file_maps.size()];
            int pos = 0;
            for (Object key : file_maps.keySet()) {
                File file = new File(getRealPathFromURI(Uri.parse(key.toString())));
                RequestBody requestBody = createRequestBody(file);
                files[pos] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "attachments[%d]", pos++), file.getName(), requestBody);
                //customToast.show(getLayoutInflater(), ExchangeRequestActivity.this, Uri.parse(key.toString()).toString());
            }
            Map<String, RequestBody> params = new HashMap<>();
            params.put("user_id", createRequestBody(userInfo.userId.toString()));
            params.put("fatherName", createRequestBody(fatherName.getText().toString()));
            params.put("birthDate", createRequestBody(birthDate.getText().toString()));
            params.put("mobile", createRequestBody(mobile.getText().toString()));
            params.put("nationalCode", createRequestBody(nationalCode.getText().toString()));
            params.put("idNumber", createRequestBody(idNumber.getText().toString()));
            params.put("repId", createRequestBody(1 + ""));
            //params.put("pId", createRequestBody(selectedProductsList.get(this.product.getSelectedItemPosition() - 1).getId().toString()));
            params.put("buildYear", createRequestBody(buildYear.getSelectedItem().toString()));
            params.put("km", createRequestBody(km.getText().toString()));
            params.put("chassisIdNumber", createRequestBody(chassisIdNumber.getText().toString()));
            params.put("description", createRequestBody(description.getText().toString()));

            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            final Call request = client.registerExchangeRequest(params, files);
            request.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    progressDialog.stop();
                    if (response.code() == 200)
                    {
                        showDialog();

                    } else{
                        customToast.show(getLayoutInflater(), OutDatedCarRequestActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                    }
                    Log.d("my log" , "................ error message:" + response.code() + " - " + response.message());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressDialog.stop();
                    Log.d("my log", "................ error message:" + t.getMessage());
                    customToast.show(getLayoutInflater(), OutDatedCarRequestActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
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

    private void openImagePicker()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        if (isSDCARDMounted()) {
            File f = new File(Environment.getExternalStorageDirectory() + "/saipaRepImages", UUID.randomUUID().toString() + ".jpg");
            this.tempCropImage = Uri.fromFile(f);
            try {
                f.createNewFile();
            } catch (IOException e) {

            }
            return f;
        } else {
            return null;
        }
    }

    private boolean isSDCARDMounted(){
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    private void init()
    {
        String newCarInfo_string = getIntent().getExtras().getString("changePlan");
        changePlan = new Gson().fromJson(newCarInfo_string, OutDatedCarChangePlans.class);

        UserInfo userInfo = UserInfo.getUserInfo();
        subject = (EditText)findViewById(R.id.input_subject);
        subject.setTypeface(PublicParams.BYekan(this));
        carInTheParking = (Switch)findViewById(R.id.input_carInTheParking);
        buildYear = (Spinner)findViewById(R.id.input_buildYear);
        birthDate = (EditText)findViewById(R.id.btn_birthDate);
        birthDate.setTypeface(PublicParams.BYekan(this));
        km = (EditText)findViewById(R.id.input_km);
        km.setTypeface(PublicParams.BYekan(this));
        chassisIdNumber = (EditText)findViewById(R.id.input_chassisIdNumber);
        chassisIdNumber.setTypeface(PublicParams.BYekan(this));
        name = (EditText)findViewById(R.id.input_name);
        name.setTypeface(PublicParams.BYekan(this));
        fatherName = (EditText)findViewById(R.id.input_fatherName);
        fatherName.setTypeface(PublicParams.BYekan(this));
        idNumber = (EditText)findViewById(R.id.input_idNumber);
        idNumber.setTypeface(PublicParams.BYekan(this));
        mobile = (EditText)findViewById(R.id.input_mobile);
        mobile.setTypeface(PublicParams.BYekan(this));
        nationalCode = (EditText)findViewById(R.id.input_nationalCode);
        nationalCode.setTypeface(PublicParams.BYekan(this));
        description = (EditText)findViewById(R.id.input_description);
        description.setTypeface(PublicParams.BYekan(this));
        progressDialog = new MyProgressDialog(OutDatedCarRequestActivity.this);

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
                            OutDatedCarRequestActivity.this,
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

        final FloatingActionsMenu floatingActionsMenu = (FloatingActionsMenu)findViewById(R.id.floatMenu);

        com.getbase.floatingactionbutton.FloatingActionButton openIB = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.openImagePicker);
        openIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allowAttacheImage()) {
                    openImagePicker();
                }
                else
                    customToast.show(getLayoutInflater() , OutDatedCarRequestActivity.this , "اضافه کردن تصویر بیشتر از سه مورد مجاز نیست");
                floatingActionsMenu.collapse();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton openCamera = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.openCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allowAttacheImage()) {
                    try {
                        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(captureIntent, CAMERA_CAPTURE);
                    }catch(ActivityNotFoundException anfe){
                        customToast.show(getLayoutInflater(), OutDatedCarRequestActivity.this , "دستگاه شما استفاده از دوربین را پشتیبانی نمی کند!");
                    }
                }
                else
                    customToast.show(getLayoutInflater() , OutDatedCarRequestActivity.this , "اضافه کردن تصویر بیشتر از سه مورد مجاز نیست");
                floatingActionsMenu.collapse();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton deleteImage = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.deleteImage);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_maps.remove(mDemoSlider.getCurrentSlider().getUrl());
                initSlider();
                floatingActionsMenu.collapse();
            }
        });
    }

    private boolean allowAttacheImage()
    {
        if (file_maps.size() < PublicParams.MAX_IMAGE_ATTACHED)
            return true;
        else
            return false;
    }

    private void imageCrop(Uri picUri)
    {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 512);
            cropIntent.putExtra("outputY", 512);
            cropIntent.putExtra("return-data", false);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(cropIntent, PIC_CROP);
        }catch(ActivityNotFoundException anfe){
            customToast.show(getLayoutInflater(), OutDatedCarRequestActivity.this , "متاسفانه دستگاه شما برش تصویر را پشتیبانی نمی کند!");
        }
    }

    public boolean validate() {
        int _buildYear = this.buildYear.getSelectedItemPosition();
        String _km = this.km.getText().toString();
        String _chassisNumber = chassisIdNumber.getText().toString();
        String _birthDate = birthDate.getText().toString();
        String _name = name.getText().toString();
        String _fatherName = fatherName.getText().toString();
        String _idNumber = idNumber.getText().toString();
        String _nationalCode = nationalCode.getText().toString();
        String _mobile = mobile.getText().toString();
        String _subject = subject.getText().toString();

        if (_chassisNumber.isEmpty()){
            this.chassisIdNumber.setError("شماره ساشی الزامیست!");
            return false;
        } else {
            birthDate.setError(null);
        }

        if (_subject.isEmpty()){
            this.subject.setError("نام خودرو الزامیست!");
            return false;
        } else {
            birthDate.setError(null);
        }

        if (((String) this.buildYear.getItemAtPosition(_buildYear)).isEmpty()) {
            setSpinnerError(this.buildYear, "انتخاب سال ساخت الزامیست!");
            return false;
        }

        if (_km.isEmpty()){
            this.km.setError("کارکرد الزامیست!");
            return false;
        }else{
            this.km.setError(null);
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

        if (_birthDate.isEmpty()){
            birthDate.setError("تاریخ تولد الزامیست");
            return false;
        }else{
            birthDate.setError(null);
        }

        if (_mobile.isEmpty()) {
            mobile.setError("شماره همراه الزامبست!");
            return false;
        } else {
            mobile.setError(null);
        }

        if (_nationalCode.isEmpty()) {
            nationalCode.setError("کد ملی الزامیست!");
            return false;
        } else {
            nationalCode.setError(null);
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

    private void initSlider()
    {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.removeAllSliders();
        mDemoSlider.stopAutoCycle();
        if (file_maps.size() == 0)
        {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(R.drawable.placeholder_car)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Log.d("my log" , "......................... current url" + mDemoSlider.getCurrentSlider().getUrl());
                        }
                    });
            mDemoSlider.addSlider(textSliderView);
        }
        else{
            for(String name : file_maps.keySet()){
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .empty(R.drawable.oops)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                Log.d("my log" , "......................... current url" + mDemoSlider.getCurrentSlider().getUrl());
                            }
                        });
                mDemoSlider.addSlider(textSliderView);
            }
        }
        final ViewTreeObserver observer = mDemoSlider.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mDemoSlider.setLayoutParams(new LinearLayout.LayoutParams(mDemoSlider.getWidth(), mDemoSlider.getWidth()));
                        //Log.d("Log", "Height: ............................................." + mDemoSlider.getWidth());
                    }
                });
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(Color.parseColor("#F39C12"), Color.parseColor("#FFFFFF"));
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                //Log.d("my log" , ".................... offset" + offset);
                Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back, null);
                if ((Math.abs(offset) + 30) >= appBarLayout.getTotalScrollRange()) {
                    upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    actionInsertColor = android.R.color.black;
                    invalidateOptionsMenu();

                } else {
                    upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    actionInsertColor = R.color.colorPrimary;
                    invalidateOptionsMenu();
                }
            }
        });
    }


    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(StoreClient.MULTIPART_FORM_DATA), file);
    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse(StoreClient.MULTIPART_FORM_DATA), s);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String path;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
            path=contentURI.getPath();

        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path=cursor.getString(idx);

        }
        if(cursor!=null)
            cursor.close();
        return path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_menu, menu);
        MenuItem actionInsert = menu.findItem(R.id.action_insert);
        Drawable drawable = actionInsert.getIcon();
        drawable.mutate();
        drawable.setColorFilter(getResources().getColor(actionInsertColor) , PorterDuff.Mode.SRC_ATOP);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        birthDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
            String imageFilePath = tempCropImage.toString();
            file_maps.put(imageFilePath , imageFilePath);
            initSlider();
        }else if ((requestCode == GALLERY_REQUEST || requestCode == CAMERA_CAPTURE) && resultCode == RESULT_OK) {
            imageCrop(data.getData());
        }
    }
}
