package key_team.com.saipa311;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ammorteza on 2/5/18.
 */
public class NoInternetConnectionDialog {
    private Context context;
    private AlertDialog dTemp;

    public NoInternetConnectionDialog(Context context)
    {
        this.context = context;
    }
    public void show(View.OnClickListener onClickListener)
    {
        TextView reTry_btn;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View alertLayout = inflater.inflate(R.layout.no_internet_connection_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        reTry_btn = (TextView)alertLayout.findViewById(R.id.reTry);
        builder.setView(alertLayout);
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((Activity)context).finish();
            }
        });
        reTry_btn.setOnClickListener(onClickListener);
        dTemp = builder.show();
    }

    public void hide()
    {
        dTemp.dismiss();
    }
}
