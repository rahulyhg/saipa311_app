package key_team.com.saipa311;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by ammorteza on 12/22/17.
 */
public class MyCustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
