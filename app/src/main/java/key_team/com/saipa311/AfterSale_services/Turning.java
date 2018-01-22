package key_team.com.saipa311.AfterSale_services;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.google.gson.Gson;

import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCar;
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
    private ViewFlipper viewFlipper;
    private static final int TURNING_MODE = 0;
    private static final int ADMISSION_CAPACITY_LIST_MODE = 1;
    private static final int PROGRESS_MODE = 2;
    private int LOGIN_REQUEST = 1;
    private int MY_CAR_REQUEST = 2;
    private MyCar selectedMyCar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ass_turning_fragment_layout, container, false);
        viewFlipper = (ViewFlipper)view.findViewById(R.id.turning_view);
        selectMyCar = (Button)view.findViewById(R.id.selectMyCar);
        selectMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLoggedIn() == false) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST);
                } else {
                    Intent mayCar = new Intent(getContext(), MyCarActivity.class);
                    mayCar.putExtra("recursive", true);
                    startActivityForResult(mayCar, MY_CAR_REQUEST);
                }
            }
        });
        return view;

    }

    private void changeFragmentView(int childId)
    {
        viewFlipper.setDisplayedChild(childId);
    }

    private void fetchAdmissionCapacityList()
    {
        changeFragmentView(PROGRESS_MODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST)
        {

        }else if (requestCode == MY_CAR_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                String myCar_string = data.getStringExtra("selectedMyCar");
                selectedMyCar = new Gson().fromJson(myCar_string, MyCar.class);
                fetchAdmissionCapacityList();
                Log.d("my log" , ".................... buildYear:" + selectedMyCar.getMcBuildYear());
            }
        }
        //this.checkIsNoTrackedRequestExist();
    }
}
