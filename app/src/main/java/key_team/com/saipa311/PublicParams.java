package key_team.com.saipa311;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ammorteza on 12/11/17.
 */
public class PublicParams {
    public static final String BASE_URL = "http://192.168.1.3/SaipaRepresentation/public/";
    public static final int MAX_IMAGE_ATTACHED = 3;
    public static final long REPEAT_TIME = 1000 * 60 * 1;
    public static final int OPTION_NOTIFICATION_ID = 136561951;
    public static final int EVENT_NOTIFICATION_ID = 136561952;
    private static String deviceUniqueID = null;
    private static final String DEVICE_UNIQUE_ID = "DEVICE_UNIQUE_ID";

    public static Typeface BYekan(Context context)
    {
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/yekan.ttf");
        return type;
    }

    public synchronized static String deviceId(Context context) {
        if (deviceUniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    DEVICE_UNIQUE_ID, Context.MODE_PRIVATE);
            deviceUniqueID = sharedPrefs.getString(DEVICE_UNIQUE_ID, null);

            if (deviceUniqueID == null) {
                deviceUniqueID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(DEVICE_UNIQUE_ID, deviceUniqueID);
                editor.commit();
            }
        }

        return deviceUniqueID;
    }
}
