package key_team.com.saipa311.Customer_services;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.google.gson.Gson;

import key_team.com.saipa311.AfterSale_services.GoldCardRequestActivity;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Assistance.AssistanceRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TrackingCode;
import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.Customer_services.JsonSchema.CriticismAndSuggestionRequestParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.R;
import key_team.com.saipa311.ServiceGenerator;
import key_team.com.saipa311.StoreClient;
import key_team.com.saipa311.customToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 11/16/17.
 */
public class CustomerServicesFragment extends Fragment {
    private Button suggestionBtn;
    private Button complaintBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_services_fragment_layout, container, false);
        suggestionBtn = (Button)view.findViewById(R.id.openSuggestionForm);
        complaintBtn = (Button)view.findViewById(R.id.openComplaintForm);
        init();
        return view;
    }

    private void init()
    {
        suggestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLoggedIn() == false) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 100);
                } else {
                    Intent intent = new Intent(getContext(), CriticismAndSuggestionRequestActivity.class);
                    startActivityForResult(intent, 2);
                }
            }
        });

        complaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLoggedIn() == false) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 100);
                } else {
                    Intent intent = new Intent(getContext(), RegisterComplaintActivity.class);
                    startActivityForResult(intent, 3);
                }
            }
        });
    }

}
