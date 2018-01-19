package key_team.com.saipa311.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import key_team.com.saipa311.MyCustomApplication;

/**
 * Created by Am on 08/31/2015.
 */
public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dailyUpdater = new Intent(context, MyService.class);
        dailyUpdater.putExtra("applicationIsVisible", MyCustomApplication.isActivityVisible());
        dailyUpdater.putExtra("applicationIsRunning" , MyCustomApplication.isAppRunning());
        context.startService(dailyUpdater);
        Log.d("AlarmReceiver", "Called context.startService from AlarmReceiver.onReceive");
    }

}
