package key_team.com.saipa311;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.Representations.JsonSchema.Representation;
import key_team.com.saipa311.Representations.JsonSchema.RepresentationWithIdRequestParams;
import key_team.com.saipa311.Services.JsonSchema.Events.Event;
import key_team.com.saipa311.Services.JsonSchema.Events.EventRequestParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 2/7/18.
 */
public class RepresentationInfoDialog {
    public static void show(final Context context)
    {
        final List<Integer> companyId = new ArrayList<>();
        final ViewFlipper viewFlipper;
        final AlertDialog dTemp;
        TextView more;
        final TextView subject;
        final TextView code;
        final TextView desc;
        final TextView address;
        final LinearLayout tel;
        final LinearLayout companyIcon;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View alertLayout = layoutInflater.inflate(R.layout.representation_info_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        viewFlipper = (ViewFlipper)alertLayout.findViewById(R.id.representation_view);
        more = (TextView)alertLayout.findViewById(R.id.more);
        subject = (TextView)alertLayout.findViewById(R.id.rep_name);
        code = (TextView)alertLayout.findViewById(R.id.rep_code);
        desc = (TextView)alertLayout.findViewById(R.id.rep_desc);
        address = (TextView)alertLayout.findViewById(R.id.rep_address);
        tel = (LinearLayout)alertLayout.findViewById(R.id.rep_tell);
        companyIcon = (LinearLayout)alertLayout.findViewById(R.id.rep_companyIcon);

        builder.setView(alertLayout);
        builder.setCancelable(true);
        dTemp = builder.show();
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , RepresentationActivity.class);
                ((Activity) context).startActivityForResult(intent , 0);
                dTemp.dismiss();
            }
        });

        RepresentationWithIdRequestParams params = new RepresentationWithIdRequestParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<Representation> request = client.fetchRepresentationWithId(params);
        request.enqueue(new Callback<Representation>() {
            @Override
            public void onResponse(Call<Representation> call, Response<Representation> response) {
                if (response.code() == 200) {
                    Representation representation = response.body();
                    subject.setText("نمایندگی " + representation.getRName());
                    code.setText("کد " + representation.getRCode());
                    desc.setText(representation.getRDescription());
                    address.setText(representation.getRAddress());
                    for (int i = 0; i < representation.getTell().size(); i++) {
                        LinearLayout.LayoutParams tel_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        TextView tel_number = new TextView(context);
                        tel_number.setTextColor(context.getResources().getColor(android.R.color.black));
                        tel_number.setText(representation.getTell().get(i).getTNumber());
                        tel.addView(tel_number, tel_layoutParams);
                    }

                    LinearLayout.LayoutParams tel_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    tel_layoutParams.setMargins(8, 0, 8, 0);
                    for (int i=0;i<representation.getService().size(); i++)
                    {
                        int j =0;
                        for (;j<companyId.size() ; j++)
                        {
                            if (companyId.get(j) == representation.getService().get(i).getProduct().getCompany().getId())
                                break;
                        }
                        if (j == companyId.size())
                        {
                            companyId.add(representation.getService().get(i).getProduct().getCompany().getId());
                            ImageView c1 = new ImageView(context);
                            c1.setAdjustViewBounds(true);
                            companyIcon.addView(c1, tel_layoutParams);
                            Log.d("my log", "fffffffffffffffffffffffffffffffff");
                            new DownloadImageTask(c1).execute(PublicParams.BASE_URL + representation.getService().get(i).getProduct().getCompany().getCoIconPath());
                        }
                    }

                    viewFlipper.setDisplayedChild(1);
                } else {
                    dTemp.dismiss();
                }

                Log.d("my log", "................. rep info code:" + response.code());

            }

            @Override
            public void onFailure(Call<Representation> call, Throwable t) {
                dTemp.dismiss();
                Log.d("my log", "................. rep info code:" + t.getMessage());
            }
        });
    }
}
