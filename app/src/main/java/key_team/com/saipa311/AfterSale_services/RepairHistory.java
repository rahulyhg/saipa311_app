package key_team.com.saipa311.AfterSale_services;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import key_team.com.saipa311.R;
import key_team.com.saipa311.customToast;

/**
 * Created by ammorteza on 1/10/18.
 */
public class RepairHistory extends Fragment {
    private Button dispHistoryBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ass_repair_history_fragment_layout, container, false);
        dispHistoryBtn = (Button)view.findViewById(R.id.displayHistoryBtn);
        dispHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast.show(getActivity().getLayoutInflater(), getContext(), "خطا در اتباط با سرور");
            }
        });
        return view;

    }
}
