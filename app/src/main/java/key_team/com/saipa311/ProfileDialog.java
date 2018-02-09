package key_team.com.saipa311;
import android.app.Activity;
import android.content.Context;
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

import key_team.com.saipa311.Customer_services.JsonSchema.CustomerScoreInClubRequestParams;
import key_team.com.saipa311.Customer_services.JsonSchema.ScoreInCustomerClub;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Representations.JsonSchema.Representation;
import key_team.com.saipa311.Representations.JsonSchema.RepresentationWithIdRequestParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 2/7/18.
 */
public class ProfileDialog {
    public static void show(final Context context)
    {
        final ViewFlipper viewFlipper;
        final AlertDialog dTemp;
        final TextView name;
        final TextView phone;
        final TextView score;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View alertLayout = layoutInflater.inflate(R.layout.profile_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        viewFlipper = (ViewFlipper)alertLayout.findViewById(R.id.profile_view);
        name = (TextView)alertLayout.findViewById(R.id.prof_name);
        phone = (TextView)alertLayout.findViewById(R.id.prof_phoneNumber);
        score = (TextView)alertLayout.findViewById(R.id.customerClub_score);

        name.setText(UserInfo.getUserInfo().name);
        phone.setText(UserInfo.getUserInfo().mobile);

        builder.setView(alertLayout);
        builder.setCancelable(true);
        dTemp = builder.show();

        CustomerScoreInClubRequestParams params = new CustomerScoreInClubRequestParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<ScoreInCustomerClub> request = client.fetchCustomerScoreInClub(params);
        request.enqueue(new Callback<ScoreInCustomerClub>() {
            @Override
            public void onResponse(Call<ScoreInCustomerClub> call, Response<ScoreInCustomerClub> response) {
                if (response.code() == 200) {
                    ScoreInCustomerClub scoreInCustomerClub = response.body();
                    score.setText(scoreInCustomerClub.getScore().toString());
                    viewFlipper.setDisplayedChild(1);
                }else{
                    dTemp.dismiss();
                }

                Log.d("my log" , ".................... fetch score:" + response.code());
            }

            @Override
            public void onFailure(Call<ScoreInCustomerClub> call, Throwable t) {
                dTemp.dismiss();
                Log.d("my log", ".................... fetch score:" + t.getMessage());
            }
        });
    }
}
