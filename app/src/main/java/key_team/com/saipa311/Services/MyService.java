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
import java.util.List;

import key_team.com.saipa311.MainActivity;
import key_team.com.saipa311.Options.JsonSchema.CarOption;
import key_team.com.saipa311.Options.JsonSchema.CarOptionsRequestParams;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.Services.JsonSchema.Options.UnDeliveredCarOption;
import key_team.com.saipa311.Services.JsonSchema.Options.UnDeliveredCarOptionsRequestParams;
import key_team.com.saipa311.StoreClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PUT;

/**
 * Created by Am on 08/31/2015.
 */


public class MyService extends IntentService {
    //private DatabaseHandler db;
    //private ArrayList<HashMap<String, Object>> dailySubscriptionsOffersArrayList;
    //private ArrayList<HashMap<String, Object>> newOffers;
    //private LotusJSONMethods ss;
    //private User user;
    private List<UnDeliveredCarOption> unDeliveredCarOptions;
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
        //sendEventNotification(MyService.this);
        this.fetchAllUnDeliveredCarOptions();

    }

    private void fetchAllUnDeliveredCarOptions()
    {
        UnDeliveredCarOptionsRequestParams params = new UnDeliveredCarOptionsRequestParams();
        params.setDeviceId(PublicParams.deviceId(MyService.this));
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<UnDeliveredCarOption>> request = client.fetchAllUnDeliveredCarOptions(params);
        request.enqueue(new Callback<List<UnDeliveredCarOption>>() {
            @Override
            public void onResponse(Call<List<UnDeliveredCarOption>> call, Response<List<UnDeliveredCarOption>> response) {
                unDeliveredCarOptions = response.body();
                if (unDeliveredCarOptions.size() > 0)
                {
                    sendOptionsNotification(MyService.this);
                }

                Log.d("my log" , "............. in service car Option request:" + response.code() + " - " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<UnDeliveredCarOption>> call, Throwable t) {

            }
        });
    }

    private void sendOptionsNotification(Context context) {
        if (!applicationIsVisible) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.putExtra("notificationType" , PublicParams.OPTION_NOTIFICATION_ID);
            notificationIntent.setAction( Intent.ACTION_MAIN );
            notificationIntent.addCategory( Intent.CATEGORY_LAUNCHER );
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            PendingIntent contentIntent;
            if (applicationIsRunning)
                contentIntent = PendingIntent.getActivity(context, PublicParams.OPTION_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            else
                contentIntent = PendingIntent.getActivity(context, PublicParams.OPTION_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Resources res = context.getResources();

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle("آپشن خودرو")
                                    //.setContentText("آپشن")
                                    //.setContentText(dailySubscriptionsOffersArrayList.get(0).get("description").toString())
                                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(dailySubscriptionsOffersArrayList.get(0).get("title").toString()))
                            .setSmallIcon(R.drawable.ic_action_car)
                            .setAutoCancel(true)
                            .setTicker("بیشتر ...")
                            .setPriority(Notification.PRIORITY_MAX)
                            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif_sound))
                            .setVibrate(vibrate)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_action_car));


            builder.setContentIntent(contentIntent);
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
/*            for (int i = 0; i < newOffers.size(); i++) {

                inboxStyle.addLine(newOffers.get(i).get("title").toString());
            }*/
            for (int i = 0 ; i<unDeliveredCarOptions.size() ; i++)
            {
                inboxStyle.addLine(unDeliveredCarOptions.get(i).getOption().getOName().toLowerCase() +
                        " - " +
                        unDeliveredCarOptions.get(i).getProduct().getPrSubject().toString() +
                        " در نمایندگی " +
                        unDeliveredCarOptions.get(i).getRepresentation().getRName().toString() +
                        " کد " + unDeliveredCarOptions.get(i).getRepresentation().getRCode().toString());
            }
            inboxStyle.setSummaryText("سایپا");
            builder.setStyle(inboxStyle);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(PublicParams.OPTION_NOTIFICATION_ID, builder.build());
        }
        else
        {
            new playAlarm().execute();
        }
    }

    private void sendEventNotification(Context context) {
        if (!applicationIsVisible) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.putExtra("notificationType", PublicParams.EVENT_NOTIFICATION_ID);
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            PendingIntent contentIntent;
            if (applicationIsRunning)
                contentIntent = PendingIntent.getActivity(context, PublicParams.EVENT_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            else
                contentIntent = PendingIntent.getActivity(context, PublicParams.EVENT_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Resources res = context.getResources();

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle("نمایندگی سایپا")
                            .setContentText("رویداد")
                                    //.setContentText(dailySubscriptionsOffersArrayList.get(0).get("description").toString())
                                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(dailySubscriptionsOffersArrayList.get(0).get("title").toString()))
                            .setSmallIcon(R.drawable.ic_action_car)
                            .setAutoCancel(true)
                            .setTicker("نمایندگی سایپا")
                            .setPriority(Notification.PRIORITY_MAX)
                            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif_sound))
                            .setVibrate(vibrate)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_action_car));


            builder.setContentIntent(contentIntent);
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            inboxStyle.addLine("رویداد");
/*            for (int i = 0; i < newOffers.size(); i++) {

                inboxStyle.addLine(newOffers.get(i).get("title").toString());
            }*/
            inboxStyle.setSummaryText("سایپا");
            builder.setStyle(inboxStyle);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(PublicParams.EVENT_NOTIFICATION_ID, builder.build());
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

