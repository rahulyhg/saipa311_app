package key_team.com.saipa311.Auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.query.Delete;

import key_team.com.saipa311.Auth.JsonSchema.TokenInfo;
import key_team.com.saipa311.Auth.JsonSchema.TokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MyProgressDialog;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class UserRegistrationActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private MyProgressDialog progressDialog;
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
        progressDialog = new MyProgressDialog(UserRegistrationActivity.this);
    }

    public void login(View view)
    {
        if (validate())
        {
            progressDialog.start();
            TokenRequestParams params = new TokenRequestParams();
            params.setEmail(emailText.getText().toString());
            params.setPassword(passwordText.getText().toString());
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            Call<TokenInfo> tokenInfo = client.login(params);
            tokenInfo.enqueue(new Callback<TokenInfo>() {
                @Override
                public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                    TokenInfo temp = response.body();
                    new Delete().from(UserInfo.class).execute();
                    final UserInfo userInfo = new UserInfo();
                    userInfo.access_token = temp.getAccessToken();
                    userInfo.refresh_token = temp.getRefreshToken();
                    userInfo.save();
                    //Log.d("my log", ".................. access token" + UserInfo.getUserInfo().access_token);
                    Call<User> user_info = client.userInfo();
                    user_info.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            userInfo.name = user.getName();
                            userInfo.mobile = user.getMobile();
                            userInfo.save();
                            progressDialog.stop();
                            Log.d("my log", ".................. user name" + userInfo.name + " - " + userInfo.refresh_token);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            progressDialog.stop();
                        }
                    });
                    //Log.d("my log" , "........................ access token" + temp.getAccessToken());
                }

                @Override
                public void onFailure(Call<TokenInfo> call, Throwable t) {
                    progressDialog.stop();
                }
            });
        }
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
}
