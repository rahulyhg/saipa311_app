package key_team.com.saipa311;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

/**
 * Created by ammorteza on 12/22/17.
 */
public class MyProgressDialog {
    final ProgressDialog progressDialog;
    public MyProgressDialog(Activity activity)
    {
        progressDialog = new ProgressDialog(activity, R.style.TransparentProgressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("منتظر بمانید ...");
        Drawable drawable = new ProgressBar(activity).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
    }

    public void start()
    {
        progressDialog.show();
    }

    public void stop()
    {
        progressDialog.dismiss();
    }
}
