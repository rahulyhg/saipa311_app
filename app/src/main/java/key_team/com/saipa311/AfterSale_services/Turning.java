package key_team.com.saipa311.AfterSale_services;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.PublicParams;
import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.ExchangeRequestActivity;

/**
 * Created by ammorteza on 1/10/18.
 */
public class Turning extends Fragment {
    private Button selectMyCar;
    private int LOGIN_REQUEST = 1;
    private int MY_CAR_REQUEST = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ass_turning_fragment_layout, container, false);
        selectMyCar = (Button)view.findViewById(R.id.selectMyCar);
        selectMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLoggedIn() == false) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST);
                }
                else{
                    Intent mayCar = new Intent(getContext() , MyCarActivity.class);
                    mayCar.putExtra("recursive", true);
                    startActivityForResult(mayCar , MY_CAR_REQUEST);
                }
            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST)
        {

        }else if (requestCode == MY_CAR_REQUEST)
        {

        }
        //this.checkIsNoTrackedRequestExist();
    }
}
