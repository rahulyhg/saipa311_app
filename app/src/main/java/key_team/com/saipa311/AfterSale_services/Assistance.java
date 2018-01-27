package key_team.com.saipa311.AfterSale_services;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.concurrent.TimeUnit;

import key_team.com.saipa311.AfterSale_services.JsonSchema.Assistance.AssistanceRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TrackingCode;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 1/10/18.
 */
public class Assistance extends Fragment {
    private Button assistanceRequestBtn;
    private ViewFlipper viewFlipper;
    private TextView _trackingCode;
    private TextView countDownTimer;
    private Button rescuerPhoneNumber;
    private static final int ASSISTANCE_MODE = 0;
    private static final int TRACKING_CODE_MODE = 1;
    private static final int SHOW_RESCUER_PHONE_MODE = 2;
    private static final int PROGRESS_MODE = 3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ass_assistance_fragment_layout, container, false);
        assistanceRequestBtn = (Button)view.findViewById(R.id.assistanceRequest_btn);
        viewFlipper = (ViewFlipper)view.findViewById(R.id.assistance_view);
        _trackingCode = (TextView)view.findViewById(R.id.assis_trackingCode);
        countDownTimer = (TextView)view.findViewById(R.id.assis_countDownTimer);
        rescuerPhoneNumber = (Button)view.findViewById(R.id.assis_alternativePhonenumber);
        init();
        return view;

    }

    private void init()
    {
        assistanceRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegDialog();
            }
        });
    }

    private void showRegDialog()
    {
        TextView reg_btn;
        TextView cancel_btn;
        final EditText name;
        final EditText phone;
        View alertLayout = getActivity().getLayoutInflater().inflate(R.layout.register_assistance_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        name = (EditText)alertLayout.findViewById(R.id.input_assis_req_name);
        phone = (EditText)alertLayout.findViewById(R.id.input_assis_req_phoneNumber);
        reg_btn = (TextView)alertLayout.findViewById(R.id.reg_register_btn);
        cancel_btn = (TextView)alertLayout.findViewById(R.id.reg_cancel_btn);
        builder.setView(alertLayout);
        builder.setCancelable(false);
        final AlertDialog dTemp = builder.show();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dTemp.dismiss();
            }
        });
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("نام و نام خانوادگی الزامیست!");
                } else {
                    name.setError(null);
                    if (phone.getText().toString().isEmpty()) {
                        phone.setError("شماره همراه الزامیست!");
                    } else {
                        phone.setError(null);
                        dTemp.dismiss();
                        registerRequest(name.getText().toString(), phone.getText().toString());
                    }
                }
            }
        });
    }

    private void showDialog(String pm)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TextView title = new TextView(getContext());
        title.setPadding(0, 30, 40, 30);
        title.setGravity(Gravity.RIGHT);
        //title.setTextSize((int) getResources().getDimension(R.dimen.textSizeXSmaller));
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setText("مشتری گرامی");
        builder.setCustomTitle(title);
        builder.setMessage(pm);
        builder.setCancelable(false);
        builder.setPositiveButton("تعویض نمایندگی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void registerRequest(String name , String phone)
    {
        changeFragmentView(PROGRESS_MODE);
        AssistanceRequestParams params = new AssistanceRequestParams();
        params.setName(name);
        params.setPhoneNumber(phone);
        params.setRepId(1);
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<TrackingCode> request = client.registerAssistanceRequest(params);
        request.enqueue(new Callback<TrackingCode>() {
            @Override
            public void onResponse(Call<TrackingCode> call, Response<TrackingCode> response) {
                Log.d("my log", "............................ register assistance request:" + response.code());
                if (response.code() == 200) {
                    TrackingCode trackingCode = response.body();
                    showTrackingCode(trackingCode.getTrackingCode());
                } else if (response.code() == 306) {
                    changeFragmentView(ASSISTANCE_MODE);
                    showDialog("درحال حاضر در این نمایندگی امدادگر فعال حضور ندارد، شما می توانید از طریق نمایندگی های دیگر اقدام کنید");

                } else {
                    changeFragmentView(ASSISTANCE_MODE);
                    customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است، دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<TrackingCode> call, Throwable t) {
                Log.d("my log", "............................ register assistance request:" + t.getMessage());
                changeFragmentView(ASSISTANCE_MODE);
                customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است، دوباره تلاش کنید");
            }
        });
    }

    private void setCountDownTimer(int start , final int end)
    {
        new CountDownTimer(end, start) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > (int)(end / 2))
                    countDownTimer.setTextColor(getResources().getColor(R.color.count_down_green));
                else if (millisUntilFinished < 30000)
                    countDownTimer.setTextColor(getResources().getColor(R.color.count_down_red));
                else
                    countDownTimer.setTextColor(getResources().getColor(R.color.count_down_orange));
                countDownTimer.setText(""+String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                showAlternativePhoneNumber();
            }
        }.start();
    }

    private void showAlternativePhoneNumber()
    {
        final String phoneNumber = "09187111705";
        changeFragmentView(SHOW_RESCUER_PHONE_MODE);
        rescuerPhoneNumber.setText(phoneNumber);
        rescuerPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        });
    }

    private void showTrackingCode(String trackingCode)
    {
        changeFragmentView(TRACKING_CODE_MODE);
        _trackingCode.setText(trackingCode);
        setCountDownTimer(1000, 120000);
    }

    private void changeFragmentView(int childId)
    {
        viewFlipper.setDisplayedChild(childId);
    }
}
