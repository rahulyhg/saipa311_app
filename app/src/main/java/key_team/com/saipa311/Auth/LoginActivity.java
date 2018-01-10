package key_team.com.saipa311.Auth;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.activeandroid.query.Delete;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;

import java.util.HashMap;
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
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private MyProgressDialog progressDialog;
    private ViewFlipper viewFlipper;

    private EditText emailText_reg;
    private EditText passwordText_reg;
    private EditText nameText_reg;
    private EditText mobileText_reg;
    private RegisterUserResult registerUserResult;

    private EditText activationCodeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init()
    {
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);
        viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        progressDialog = new MyProgressDialog(LoginActivity.this);

        emailText_reg = (EditText)findViewById(R.id.input_username);
        passwordText_reg = (EditText)findViewById(R.id.input_pass);
        nameText_reg = (EditText)findViewById(R.id.input_name);
        mobileText_reg = (EditText)findViewById(R.id.input_mobile);

        activationCodeText = (EditText)findViewById(R.id.input_activationCode);
    }

    public void registerForm(View view)
    {
        viewFlipper.setDisplayedChild(1);
    }

    public void loginForm(View view)
    {
        viewFlipper.setDisplayedChild(0);
    }

    public void register(View view)
    {
        if (validateRegisterForm())
        {
            progressDialog.start();
            RegisterUserRequestParams params = new RegisterUserRequestParams();
            params.setEmail(emailText_reg.getText().toString());
            params.setPassword(passwordText_reg.getText().toString());
            params.setMobile(mobileText_reg.getText().toString());
            params.setName(nameText_reg.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            Call<RegisterUserResult> regUser = client.registerUser(params);
            regUser.enqueue(new Callback<RegisterUserResult>() {
                @Override
                public void onResponse(Call<RegisterUserResult> call, Response<RegisterUserResult> response) {
                    Log.d("my log", "................................... register user" + response.code());
                    progressDialog.stop();
                    if (response.code() == 200) {
                        registerUserResult = response.body();
                        Log.d("my log", "................................... register user 200");
                        viewFlipper.setDisplayedChild(2);
                    }else if (response.code() == 409)
                    {
                        customToast.show(getLayoutInflater(), LoginActivity.this, "شماره همراه یا نام کاربری تکراری است");
                    }
                }

                @Override
                public void onFailure(Call<RegisterUserResult> call, Throwable t) {
                    Log.d("my log" , "................................... register user" + t.getMessage());
                    progressDialog.stop();
                }
            });
        }
    }

    public void activeAccount(View view)
    {
        if (activationFormValidate())
        {
            progressDialog.start();
            UserActivationRequestParams params = new UserActivationRequestParams();
            params.setActivationCode(activationCodeText.getText().toString());
            params.setId(registerUserResult.getId());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            Call activation = client.userActivation(params);
            activation.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == 200)
                    {
                        emailText.setText(emailText_reg.getText().toString());
                        passwordText.setText(passwordText_reg.getText().toString());
                        signIn();
                    }
                    else if (response.code() == 412){
                        progressDialog.stop();
                        customToast.show(getLayoutInflater() , LoginActivity.this , "کد فعالسازی اشتباه است");
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressDialog.stop();
                }
            });
        }
    }

    private void signIn()
    {
        if (validate())
        {
            Log.d("my log", ".................. user name" + emailText.getText().toString() + " - " + passwordText.getText().toString());
            progressDialog.start();
            TokenRequestParams params = new TokenRequestParams();
            params.setEmail(emailText.getText().toString());
            params.setPassword(passwordText.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            Call<TokenInfo> tokenInfo = client.login(params);
            tokenInfo.enqueue(new Callback<TokenInfo>() {
                @Override
                public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                    if (response.code() == 200) {
                        TokenInfo temp = response.body();
                        new Delete().from(UserInfo.class).execute();
                        final UserInfo userInfo = new UserInfo();
                        userInfo.access_token = temp.getAccessToken();
                        userInfo.refresh_token = temp.getRefreshToken();
                        userInfo.save();
                        Log.d("my log", ".................. access token" + UserInfo.getUserInfo().access_token);
                        Call<User> user_info = client.userInfo();
                        user_info.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user = response.body();
                                userInfo.userId = user.getId();
                                userInfo.name = user.getName();
                                userInfo.mobile = user.getMobile();
                                userInfo.birthDate = user.getBirthDate();
                                userInfo.fatherName = user.getFatherName();
                                userInfo.idNumber = user.getIdNumber();
                                userInfo.nationalCode = user.getNationalCode();
                                userInfo.save();
                                progressDialog.stop();
                                Log.d("my log", ".................. user name" + userInfo.name + " - " + userInfo.refresh_token);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                progressDialog.stop();
                            }
                        });
                    } else if (response.code() == 401){
                        progressDialog.stop();
                        customToast.show(getLayoutInflater() , LoginActivity.this , "نام کاربری یا کلمه عبور اشتباه است");
                    }
                    else{
                        progressDialog.stop();
                        customToast.show(getLayoutInflater() , LoginActivity.this , "خطای داخلی رخ داده است");
                        Log.d("my log" , "........................ get access token error" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<TokenInfo> call, Throwable t) {
                    progressDialog.stop();
                    Log.d("my log", "........................ get access token error " + t.getMessage());
                }
            });
        }
    }

    public void login(View view)
    {
        this.signIn();
    }

    public boolean validateRegisterForm() {
        boolean valid = true;

        String username = emailText_reg.getText().toString();
        String password = passwordText_reg.getText().toString();
        String name = nameText_reg.getText().toString();
        String mobile = mobileText_reg.getText().toString();

        if (username.isEmpty()) {
            emailText_reg.setError("نام کاربری الزامیست!");
            valid = false;
        } else {
            emailText_reg.setError(null);
        }

        if (name.isEmpty()) {
            nameText_reg.setError("نام الزامیست!");
            valid = false;
        } else {
            nameText_reg.setError(null);
        }

        if (mobile.isEmpty()) {
            mobileText_reg.setError("شماره موبایل الزامیست!");
            valid = false;
        } else {
            mobileText_reg.setError(null);
        }

        if (password.isEmpty()) {
            passwordText_reg.setError("کلمه عبور الزامیست!");
            valid = false;
        } else {
            passwordText_reg.setError(null);
        }

        return valid;
    }

    public boolean validate() {
        boolean valid = true;

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

        return valid;
    }

    public boolean activationFormValidate() {
        boolean valid = true;

        String activeCode = activationCodeText.getText().toString();

        if (activeCode.isEmpty()) {
            activationCodeText.setError("کد فعالسازی الزامیست!");
            valid = false;
        } else {
            activationCodeText.setError(null);
        }

        return valid;
    }
}
