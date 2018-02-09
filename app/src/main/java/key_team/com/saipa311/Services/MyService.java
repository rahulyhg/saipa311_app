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

import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.DB_Management.Setting;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.MainActivity;
import key_team.com.saipa311.Options.JsonSchema.CarOption;
import key_team.com.saipa311.Options.JsonSchema.CarOptionsRequestParams;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.Services.JsonSchema.Events.UnDeliveredEvent;
import key_team.com.saipa311.Services.JsonSchema.Events.UnDeliveredEventsRequestParams;
import key_team.com.saipa311.Services.JsonSchema.Options.UnDeliveredCarOption;
import key_team.com.saipa311.Services.JsonSchema.Options.UnDeliveredCarOptionsRequestParams;
import key_team.com.saipa311.Services.JsonSchema.Surveys.Survey;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.SurveyFormActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PUT;

/**
 * Created by Am on 08/31/2015.
 */


public class MyService extends IntentService {
    private List<UnDeliveredCarOption> unDeliveredCarOptions;
    private List<UnDeliveredEvent> unDeliveredEvents;
    private Survey survey;
    private boolean state;
    private boolean applicationIsVisible = false;
    private boolean applicationIsRunning = false;
    private long[] vibrate = {500 , 1000};
    public MyService() {
        super("SaipaRepService");
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
        if (PublicParams.getConnectionState(MyService.this)) {
            if (UserInfo.isLoggedIn()) {
                fetchUnDeliveredSurveyForm();
            }
        }

        if (Setting.getNotifState()) {
            if (PublicParams.getConnectionState(MyService.this)) {
                this.fetchAllUnDeliveredCarOptions();
                this.fetchAllUnDeliveredEvents();
            }
        }
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
                if (unDeliveredCarOptions.size() > 0) {
                    sendOptionsNotification(MyService.this);
                }

                Log.d("my log", "............. in service car Option request:" + response.code() + " - " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<UnDeliveredCarOption>> call, Throwable t) {

            }
        });
    }

    private void fetchAllUnDeliveredEvents()
    {
        UnDeliveredEventsRequestParams params = new UnDeliveredEventsRequestParams();
        params.setDeviceId(PublicParams.deviceId(MyService.this));
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<UnDeliveredEvent>> request = client.fetchAllUnDeliveredEvents(params);
        request.enqueue(new Callback<List<UnDeliveredEvent>>() {
            @Override
            public void onResponse(Call<List<UnDeliveredEvent>> call, Response<List<UnDeliveredEvent>> response) {
                unDeliveredEvents = response.body();
                if (unDeliveredEvents.size() > 0)
                {
                    sendEventNotification(MyService.this);
                }

                Log.d("my log" , "............. in service event request:" + response.code() + " - " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<UnDeliveredEvent>> call, Throwable t) {

            }
        });
    }

    private void fetchUnDeliveredSurveyForm()
    {
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<Survey> request = client.fetchUnDeliveredSurveyForm();
        request.enqueue(new Callback<Survey>() {
            @Override
            public void onResponse(Call<Survey> call, Response<Survey> response) {
                if (response.code() == 200)
                {
                    survey = response.body();
                    sendSurveyNotification(MyService.this);
                }

                Log.d("my log" , "............. in service survey request:" + response.code());
            }

            @Override
            public void onFailure(Call<Survey> call, Throwable t) {

            }
        });
    }

    private void sendSurveyNotification(Context context) {
        if (!applicationIsVisible && Setting.getNotifState()) {
            Intent notificationIntent = new Intent(context, SurveyFormActivity.class);
            notificationIntent.putExtra("surveyFormId" , survey.getId());
            notificationIntent.setAction(Intent.ACTION_MAIN );
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER );
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent;
            if (applicationIsRunning)
                contentIntent = PendingIntent.getActivity(context, PublicParams.OPTION_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            else
                contentIntent = PendingIntent.getActivity(context, PublicParams.OPTION_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Resources res = context.getResources();

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle(" نظر سنجی")
                            .setContentText("نمایندگی " + survey.getRepresentation().getRCode().toString() + " - " + survey.getRepresentation().getRName().toString())
                            //.setContentText(dailySubscriptionsOffersArrayList.get(0).get("description").toString())
                            //.setStyle(new NotificationCompat.BigTextStyle().bigText(dailySubscriptionsOffersArrayList.get(0).get("title").toString()))
                            .setSmallIcon(R.drawable.saipa_notif_icon_small)
                            .setAutoCancel(true)
                            .setTicker("بیشتر ...")
                            .setPriority(Notification.PRIORITY_MAX)
                            .setSound(Setting.getSoundState() == true ? Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif_sound) : null)
                            .setVibrate(Setting.getVibrationState() == true ? vibrate : null)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.saipa_notif_icon));


            builder.setContentIntent(contentIntent);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(PublicParams.SURVEY_NOTIFICATION_ID, builder.build());
        }
        else
        {
            sendSignalToActivity(PublicParams.SURVEY_FORM_AVAILABLE);
        }
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
                            .setContentText(unDeliveredCarOptions.size() + " آپشن جدید" + "در نمایندگی های مختلف")
                                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(dailySubscriptionsOffersArrayList.get(0).get("title").toString()))
                            .setSmallIcon(R.drawable.saipa_notif_icon_small)
                            .setAutoCancel(true)
                            .setTicker("بیشتر ...")
                            .setPriority(Notification.PRIORITY_MAX)
                            .setSound(Setting.getSoundState() == true ? Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif_sound) : null)
                            .setVibrate(Setting.getVibrationState() == true ? vibrate : null)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.saipa_notif_icon));


            builder.setContentIntent(contentIntent);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            for (int i = 0 ; i<unDeliveredCarOptions.size() ; i++)
            {
                inboxStyle.addLine(unDeliveredCarOptions.get(i).getOption().getOName().toString() +
                        " - " +
                        unDeliveredCarOptions.get(i).getProduct().getPrSubject().toString() +
                        " در نمایندگی " +
                        unDeliveredCarOptions.get(i).getRepresentation().getRName().toString() +
                        " کد " + unDeliveredCarOptions.get(i).getRepresentation().getRCode().toString());
            }
            //inboxStyle.setSummaryText("سایپا");
            builder.setStyle(inboxStyle);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(PublicParams.OPTION_NOTIFICATION_ID, builder.build());
        }
        else
        {
            //new playAlarm().execute();
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
                            .setContentTitle("رویدادهای نمایندگی")
                            .setContentText(unDeliveredEvents.size() + " رویداد جدید")
                                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(dailySubscriptionsOffersArrayList.get(0).get("title").toString()))
                            .setSmallIcon(R.drawable.saipa_notif_icon_small)
                            .setAutoCancel(true)
                            .setTicker("بیشتر ...")
                            .setPriority(Notification.PRIORITY_MAX)
                            .setSound(Setting.getSoundState() == true ? Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif_sound) : null)
                            .setVibrate(Setting.getVibrationState() == true ? vibrate : null)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.saipa_notif_icon));


            builder.setContentIntent(contentIntent);
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            for (int i = 0 ; i<unDeliveredEvents.size() ; i++)
            {
                inboxStyle.addLine(unDeliveredEvents.get(i).getESubject().toString() +
                        " - " +
                        " : نمایندگی " +
                        unDeliveredEvents.get(i).getRepresentation().getRName().toString() +
                        " کد " + unDeliveredEvents.get(i).getRepresentation().getRCode().toString());
            }
            //inboxStyle.setSummaryText("سایپا");
            builder.setStyle(inboxStyle);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(PublicParams.EVENT_NOTIFICATION_ID, builder.build());
        }
        else
        {
            //new playAlarm().execute();
        }
    }

    private void sendSignalToActivity(int notiType) {
        Intent intent = new Intent("SaipaServiceSignal");
        sendLocationBroadcast(intent , notiType);
    }

    private void sendLocationBroadcast(Intent intent , int notifType){
        intent.putExtra("notifType", notifType);
        if (notifType == PublicParams.SURVEY_FORM_AVAILABLE)
            intent.putExtra("surveyFormId" , survey.getId());
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

