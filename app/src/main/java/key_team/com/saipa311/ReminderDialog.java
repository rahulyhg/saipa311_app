package key_team.com.saipa311;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TurnReminder;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.Representations.JsonSchema.Representation;
import key_team.com.saipa311.Representations.JsonSchema.RepresentationWithIdRequestParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 2/7/18.
 */
public class ReminderDialog {
    public static void show(final Context context)
    {
        final ViewFlipper viewFlipper;
        final AlertDialog dTemp;
        final TextView desc;
        final TextView repInfo;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View alertLayout = layoutInflater.inflate(R.layout.reminder_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        viewFlipper = (ViewFlipper)alertLayout.findViewById(R.id.reminder_view);
        desc = (TextView)alertLayout.findViewById(R.id.description);
        repInfo = (TextView)alertLayout.findViewById(R.id.repInfo);

        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setNeutralButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dTemp = builder.show();

        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<TurnReminder> request = client.fetchUnDeliveredTurnReminder();
        request.enqueue(new Callback<TurnReminder>() {
            @Override
            public void onResponse(Call<TurnReminder> call, Response<TurnReminder> response) {
                if (response.code() == 200)
                {
                    TurnReminder turnReminder = response.body();
                    desc.setText("شما برای تاریخ " + turnReminder.getAdmissionCapacity().getAcDate() + " ساعت " + turnReminder.getAdmissionCapacity().getAdmissionCapacityTime().getActSubject() + " نوبت ثبت کرده اید، لطفا در زمان مقرر مراجعه فرمایید.");
                    repInfo.setText("نمایندگی " + turnReminder.getAdmissionCapacity().getRepresentation().getRName() + " - کد " + turnReminder.getAdmissionCapacity().getRepresentation().getRCode());
                    viewFlipper.setDisplayedChild(1);
                }else{
                    dTemp.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TurnReminder> call, Throwable t) {
                dTemp.dismiss();
            }
        });
    }
}
