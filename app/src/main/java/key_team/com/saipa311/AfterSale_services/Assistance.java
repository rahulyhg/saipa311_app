package key_team.com.saipa311.AfterSale_services;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import key_team.com.saipa311.AfterSale_services.JsonSchema.Assistance.AssistanceRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TheTurnRequestParams;
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
    private static final int ASSISTANCE_MODE = 0;
    private static final int TRACKING_CODE_MODE = 1;
    private static final int PROGRESS_MODE = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ass_assistance_fragment_layout, container, false);
        assistanceRequestBtn = (Button)view.findViewById(R.id.assistanceRequest_btn);
        viewFlipper = (ViewFlipper)view.findViewById(R.id.assistance_view);
        _trackingCode = (TextView)view.findViewById(R.id.assis_trackingCode);
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
                if (name.getText().toString().isEmpty()){
                    name.setError("نام و نام خانوادگی الزامیست!");
                }else{
                    name.setError(null);
                    if (phone.getText().toString().isEmpty()){
                        phone.setError("شماره همراه الزامیست!");
                    }else{
                        phone.setError(null);
                        dTemp.dismiss();
                        registerRequest(name.getText().toString() , phone.getText().toString());
                    }
                }
            }
        });
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

    private void showTrackingCode(String trackingCode)
    {
        changeFragmentView(TRACKING_CODE_MODE);
        _trackingCode.setText(trackingCode);
    }

    private void changeFragmentView(int childId)
    {
        viewFlipper.setDisplayedChild(childId);
    }
}
