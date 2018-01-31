package key_team.com.saipa311;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;

/**
 * Created by ammorteza on 12/22/17.
 */
public class MyCustomApplication extends Application {
    public static boolean isActivityVisible() {
        return activityVisible;
    }
    public static boolean isAppRunning() {
        return appIsRunning;
    }

    public static void activityResumed() {
        activityVisible = true;
        Log.d("MyApplication", "Application is visible");
    }

    public static void activityPaused() {
        activityVisible = false;
        Log.d("MyApplication", "Application is invisible");
    }

    public static void appDestroy()
    {
        appIsRunning = false;
        Log.d("MyApplication", "Application is not running");
    }

    public static void appCreate()
    {
        appIsRunning = true;
        Log.d("MyApplication", "Application is running");
    }

    private static boolean activityVisible;
    private static boolean appIsRunning;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
