package key_team.com.saipa311.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import key_team.com.saipa311.MainActivity;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;

/**
 * Created by Am on 08/31/2015.
 */


public class MyService extends IntentService {
    //private DatabaseHandler db;
    //private ArrayList<HashMap<String, Object>> dailySubscriptionsOffersArrayList;
    //private ArrayList<HashMap<String, Object>> newOffers;
    //private LotusJSONMethods ss;
    //private User user;
    private boolean state;
    private boolean applicationIsVisible = false;
    private boolean applicationIsRunning = false;
    private long[] vibrate = {500 , 1000};
    public MyService() {
        super("SaipaRepService");
/*        db = new DatabaseHandler(MyService.this);
        dailySubscriptionsOffersArrayList = new ArrayList<HashMap<String, Object>>();
        newOffers  = new ArrayList<HashMap<String, Object>>();
        ss = new LotusJSONMethods(MyService.this);
        user = new User(MyService.this);*/
    }

    @Override
    public void onStart(Intent intent, int startId) {
        applicationIsVisible = intent.getBooleanExtra("applicationIsVisible" , false);
        applicationIsRunning = intent.getBooleanExtra("applicationIsRunning" , false);
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MyService", "About to execute MyTask");
        if (applicationIsVisible)
            Log.d("MyService", "Application is visible");
        else
            Log.d("MyService", "Application is invisible");

        if (applicationIsRunning)
            Log.d("MyService", "Application is running");
        else
            Log.d("MyService", "Application is not running");

/*        state = Constants.NOT_NEW_DAILY_SUB_OFFERS;
        if (user.userIsLogin()) {
            if (InternetAccess.getConnectivityStatus(MyService.this) == InternetAccess.TYPE_CONNECTED) {
                dailySubscriptionsOffersArrayList = ss.getDailySubscriptionsOffers(MyService.this);
                if (ss.getStatusCode() == Constants.HTTP_STATUS_OK && dailySubscriptionsOffersArrayList.size() > 0) {
                    newOffers = db.updateDailySubOffers(dailySubscriptionsOffersArrayList);
                    if (newOffers.size() > 0) {
                        if ((boolean) db.getSetting().get("notifState") == true)
                            sendNotification(MyService.this);
                        state = Constants.NEW_DAILY_SUB_OFFERS;
                        sendSignalToActivity();
                    }
                }
            }
        }

        db.close();*/
        //sendNotification(MyService.this);
    }

    private void sendNotification(Context context) {
        if (!applicationIsVisible) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent;
            if (applicationIsRunning)
                contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            else
                contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Resources res = context.getResources();

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle("نمایندگی سایپا")
                                    //.setContentText(dailySubscriptionsOffersArrayList.get(0).get("description").toString())
                                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(dailySubscriptionsOffersArrayList.get(0).get("title").toString()))
                            .setSmallIcon(R.drawable.ic_action_car)
                            .setAutoCancel(true)
                            .setTicker("نمایندگی سایپا")
                            .setPriority(Notification.PRIORITY_MAX)
                            //.setSound((boolean)db.getSetting().get("soundState") == true ? Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif_sound) : null)
                            .setVibrate(vibrate)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_action_car));


            builder.setContentIntent(contentIntent);
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            inboxStyle.addLine("برای تست");
/*            for (int i = 0; i < newOffers.size(); i++) {

                inboxStyle.addLine(newOffers.get(i).get("title").toString());
            }*/
            inboxStyle.setSummaryText("سایپا");
            builder.setStyle(inboxStyle);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(PublicParams.NOTIFICATION_ID, builder.build());
        }
        else
        {
            new playAlarm().execute();
        }
    }

    private void sendSignalToActivity() {
        Intent intent = new Intent("LotusServiceSignal");
        sendLocationBroadcast(intent);
    }

    private void sendLocationBroadcast(Intent intent){
        intent.putExtra("state", state);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
    ////////////////////////////////////////////////////////////////////////
    private class playAlarm extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.notif_sound);
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

