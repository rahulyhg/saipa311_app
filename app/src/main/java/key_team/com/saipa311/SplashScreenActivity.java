package key_team.com.saipa311;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.activeandroid.query.Delete;

import key_team.com.saipa311.Auth.JsonSchema.RefreshTokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.TokenInfo;
import key_team.com.saipa311.Auth.JsonSchema.TokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.DB_Management.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        refreshToken();
        Animation animation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.fade_in);
        ImageView imageView = (ImageView)findViewById(R.id.saipa_logo);
        imageView.setAnimation(animation);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
                finish();
            }
        }, 3000);
    }

    private void refreshToken()
    {
        final UserInfo userInfo = UserInfo.getUserInfo();
        if (UserInfo.isLoggedIn())
        {
            RefreshTokenRequestParams params = new RefreshTokenRequestParams();
            params.setRefreshToken(userInfo.refresh_token);
            final StoreClient client = ServiceGenerator.createService(StoreClient.class);
            Call<TokenInfo> tokenInfo = client.refreshToken(params);
            tokenInfo.enqueue(new Callback<TokenInfo>() {
                @Override
                public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                    new Delete().from(UserInfo.class).execute();
                    if (response.code() == 200) {
                        TokenInfo temp = response.body();
                        final UserInfo _userInfo = new UserInfo();
                        _userInfo.access_token = temp.getAccessToken();
                        _userInfo.refresh_token = temp.getRefreshToken();
                        _userInfo.save();

                        Call<User> user_info = client.userInfo();
                        user_info.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user = response.body();
                                _userInfo.userId = user.getId();
                                _userInfo.name = user.getName();
                                _userInfo.mobile = user.getMobile();
                                _userInfo.birthDate = user.getBirthDate();
                                _userInfo.fatherName = user.getFatherName();
                                _userInfo.idNumber = user.getIdNumber();
                                _userInfo.nationalCode = user.getNationalCode();
                                _userInfo.address = user.getAddress();
                                _userInfo.save();
                                Log.d("my log", ".................. user name" + userInfo.name + " - " + userInfo.refresh_token);
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                            }
                        });
                        Log.d("my log", ".................................. user info " + _userInfo.mobile);
                    }
                }

                @Override
                public void onFailure(Call<TokenInfo> call, Throwable t) {

                }
            });
            Log.d("my log", ".................................. user table is not empty");
        }
    }
}
