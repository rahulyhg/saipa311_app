package key_team.com.saipa311;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ammorteza on 12/11/17.
 */
public class PublicParams {
    //public static final String BASE_URL = "http://10.0.2.2/SaipaRepresentation/public/";
    //public static final String BASE_URL = "http://192.168.1.31/SaipaRepresentation/public/";
    public static final String BASE_URL = "http://192.168.1.3/SaipaRepresentation/public/";
    public static final int MAX_IMAGE_ATTACHED = 3;

    public static Typeface BYekan(Context context)
    {
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/traffic.ttf");
        return type;
    }
}
