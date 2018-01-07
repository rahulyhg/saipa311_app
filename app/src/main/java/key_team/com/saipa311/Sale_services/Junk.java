package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlans;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlanRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarRequestExistsParams;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class Junk extends Fragment {
    private OutDatedCarChangePlans changePlan;
    private TextView subject;
    private TextView startAndEndYear;
    private TextView description;
    private ViewFlipper junk_view;
    private Button openRequestForm;
    private int changePlanHttpCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.junk_fragment_layout, container, false);
        junk_view = (ViewFlipper)view.findViewById(R.id.junk_view);
        subject = (TextView)view.findViewById(R.id.subject);
        startAndEndYear = (TextView)view.findViewById(R.id.startAndEndYear);
        description = (TextView)view.findViewById(R.id.description);
        openRequestForm = (Button)view.findViewById(R.id.openRequestForm);
        openRequestForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLoggedIn() == false) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(getContext(), OutDatedCarRequestActivity.class);
                    String arrayAsString = new Gson().toJson(changePlan);
                    intent.putExtra("changePlan", arrayAsString);
                    startActivityForResult(intent, 2);
                }
            }
        });
        this.fetchOldDatedCarChangePlans();
        return view;
    }

    private void loadActivePlan()
    {
        subject.setText(changePlan.getCcpSubject());
        startAndEndYear.setText("از سال " + changePlan.getCcpStartYear() + " تا سال " + changePlan.getCcpEndYear());
        description.setText(changePlan.getCcpDescription());
    }

    private void fetchOldDatedCarChangePlans()
    {
        junk_view.setDisplayedChild(0);
        OutDatedCarChangePlanRequestParams params = new OutDatedCarChangePlanRequestParams();
        params.setRepId(1);
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<OutDatedCarChangePlans> request = client.getOutDatedCarChangeActivePlan(params);
        request.enqueue(new Callback<OutDatedCarChangePlans>() {
            @Override
            public void onResponse(Call<OutDatedCarChangePlans> call, Response<OutDatedCarChangePlans> response) {
                changePlanHttpCode = response.code();
                if (response.code() == 200) {
                    changePlan = response.body();
                    loadActivePlan();
                    if (UserInfo.isLoggedIn() == false) {
                        junk_view.setDisplayedChild(1);
                    }else{
                        checkIsNoTrackedRequestExist();
                    }
                }else if (response.code() == 204) {
                    if (UserInfo.isLoggedIn() == false) {
                        junk_view.setDisplayedChild(3);
                    }else{
                        checkIsNoTrackedRequestExist();
                    }
                }else{
                    customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است دوباره تلاش کنید");
                    Log.d("my log" , "............  get changePlan error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<OutDatedCarChangePlans> call, Throwable t) {
                customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است دوباره تلاش کنید");
                Log.d("my log", "............  get changePlan error:" + t.getMessage());
            }
        });
    }

    private void checkIsNoTrackedRequestExist()
    {
        OutDatedCarRequestExistsParams params = new OutDatedCarRequestExistsParams();
        params.setRepId(1);
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<OutDatedCarRequestExists> request = client.isNotTrackedODCCRequestExist(params);
        request.enqueue(new Callback<OutDatedCarRequestExists>() {
            @Override
            public void onResponse(Call<OutDatedCarRequestExists> call, Response<OutDatedCarRequestExists> response) {
                if (response.code() == 200)
                {
                    OutDatedCarRequestExists exist = response.body();
                    if (exist.getExist() == false) {
                        if (changePlanHttpCode == 200)
                            junk_view.setDisplayedChild(1);
                        else
                            junk_view.setDisplayedChild(3);
                    }
                    else
                        junk_view.setDisplayedChild(2);
                }else{
                    customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است دوباره تلاش کنید");
                    Log.d("my log", "............  isNoTrackedExist error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<OutDatedCarRequestExists> call, Throwable t) {
                customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است دوباره تلاش کنید");
                Log.d("my log", "............  isNoTrackedExist error:" + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.fetchOldDatedCarChangePlans();
    }
}
