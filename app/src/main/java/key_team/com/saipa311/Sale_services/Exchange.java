package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.google.gson.Gson;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.PhotoViewer;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.ExchangeRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.ExchangeRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarRequestExists;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class Exchange extends Fragment {
    private ViewFlipper exchange_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_fragment_layout, container, false);
        ((Button)view.findViewById(R.id.openExchangeForm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLoggedIn() == false) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                else{
                    Intent intent = new Intent(getContext(), ExchangeRequestActivity.class);
                    startActivityForResult(intent, 2);
                }
            }
        });
        exchange_view = (ViewFlipper)view.findViewById(R.id.exchange_view);
        this.checkIsNoTrackedRequestExist();
        return view;
    }

    private void checkIsNoTrackedRequestExist()
    {
        ExchangeRequestExistsParams params = new ExchangeRequestExistsParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<ExchangeRequestExists> request = client.isNotTrackedExchangeRequestExist(params);
        request.enqueue(new Callback<ExchangeRequestExists>() {
            @Override
            public void onResponse(Call<ExchangeRequestExists> call, Response<ExchangeRequestExists> response) {
                if (response.code() == 200)
                {
                    ExchangeRequestExists exist = response.body();
                    if (exist.getExist() == false) {
                        exchange_view.setDisplayedChild(0);
                    }
                    else
                        exchange_view.setDisplayedChild(1);
                }else{
                    exchange_view.setDisplayedChild(0);
                    //customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است دوباره تلاش کنید");
                    Log.d("my log", "............  isNoTrackedExist error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ExchangeRequestExists> call, Throwable t) {
                customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است دوباره تلاش کنید");
                Log.d("my log", "............  isNoTrackedExist error:" + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.checkIsNoTrackedRequestExist();
    }
}
