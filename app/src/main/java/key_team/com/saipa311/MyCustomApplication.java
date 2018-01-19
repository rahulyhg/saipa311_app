package key_team.com.saipa311;

import android.app.Application;

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
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public static void appDestroy()
    {
        appIsRunning = false;
    }

    public static void appCreate()
    {
        appIsRunning = true;
    }

    private static boolean activityVisible;
    private static boolean appIsRunning;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
