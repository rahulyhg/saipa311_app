package key_team.com.saipa311.AfterSale_services;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCar;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.AdmissionList;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.AdmissionListRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.AdmissionServiceType;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.Declaration;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.DeclarationGroup;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TheTurnRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TrackingCode;
import key_team.com.saipa311.Auth.LoginActivity;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
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
public class Turning extends Fragment {
    private List<AdmissionServiceType> admissionServiceTypes;
    private List<AdmissionList> admissionData;
    private List<DeclarationGroup> selectedDecGroup;
    private List<Declaration> selectedDec;
    private RecyclerView recyclerView;
    private Button selectMyCar;
    private ViewFlipper viewFlipper;
    private TurningAdapter turningAdapter;
    private ArrayList<admissionCapacity> turningList;
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
        recyclerView = (RecyclerView)view.findViewById(R.id.turning_recycler_view);
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
        turningList = new ArrayList<>();
        turningAdapter = new TurningAdapter(turningList);
        recyclerView.setAdapter(turningAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllAdmissionServiceType();
        return view;

    }

    private void changeFragmentView(int childId)
    {
        viewFlipper.setDisplayedChild(childId);
    }

    private void fetchAdmissionCapacityList()
    {
        changeFragmentView(PROGRESS_MODE);
        AdmissionListRequestParams params = new AdmissionListRequestParams();
        params.setRepId(ActiveRepresentation.getActiveRepresentationId());
        params.setPId(selectedMyCar.getPId());
        params.setMcId(selectedMyCar.getId());
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<AdmissionList>> admissions = client.getAllTimesForMyCarWithRepId(params);
        admissions.enqueue(new Callback<List<AdmissionList>>() {
            @Override
            public void onResponse(Call<List<AdmissionList>> call, Response<List<AdmissionList>> response) {
                if (response.code() == 200) {
                    admissionData = response.body();
                    Log.d("my log", "................................. admission size:" + admissionData.size());
                    if (admissionData.size() > 0)
                        reloadTurningList();
                    else{
                        changeFragmentView(TURNING_MODE);
                        showDialog(getResources().getString(R.string.admissionCapacityDoesNotExistInRep_pm));
                    }
                } else if (response.code() == 306) {
                    changeFragmentView(TURNING_MODE);
                    showDialog(getResources().getString(R.string.afterSaleServiceDoesnotExistInRep_pm));
                }else if (response.code() == 409)
                {
                    changeFragmentView(TURNING_MODE);
                    showDialog("برای خودروی انتخاب شده در این نمایندگی نوبت پذیرش رزرو شده است!");
                }
                else {
                    customToast.show(getActivity().getLayoutInflater(), getActivity(), "خطایی رخ داده است دوباره تلاش کنید");
                }

                Log.d("my log", ".......................................... admission result code:" + response.code());
            }

            @Override
            public void onFailure(Call<List<AdmissionList>> call, Throwable t) {

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

    private void showRegDialog(final int acPos)
    {
        TextView date;
        TextView time;
        TextView reg_btn;
        TextView cancel_btn;
        final Spinner admissionST;
        final Spinner decGroup;
        final Spinner dec;
        View alertLayout = getActivity().getLayoutInflater().inflate(R.layout.register_turning_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        date = (TextView)alertLayout.findViewById(R.id.reg_admission_date);
        date.setText(admissionData.get(acPos).getAcDate());
        time = (TextView)alertLayout.findViewById(R.id.reg_admission_time);
        time.setText(admissionData.get(acPos).getAdmissionCapacityTime().getActSubject());
        reg_btn = (TextView)alertLayout.findViewById(R.id.reg_register_btn);
        cancel_btn = (TextView)alertLayout.findViewById(R.id.reg_cancel_btn);
        admissionST = (Spinner)alertLayout.findViewById(R.id.input_admissionServiceType);
        loadAdmissionServiceType(admissionST);
        decGroup = (Spinner)alertLayout.findViewById(R.id.input_declarationGroup);
        dec = (Spinner)alertLayout.findViewById(R.id.input_declaration);
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
                if (((String) admissionST.getItemAtPosition(admissionST.getSelectedItemPosition())).isEmpty()) {
                    setSpinnerError(admissionST , "انتخاب نوع سرویس الزامیست!");
                }else if (((String) decGroup.getItemAtPosition(decGroup.getSelectedItemPosition())).isEmpty()) {
                    setSpinnerError(decGroup , "انتخاب گروه اظهار الزامیست!");
                }else if (((String) dec.getItemAtPosition(dec.getSelectedItemPosition())).isEmpty()) {
                    setSpinnerError(dec , "انتخاب اظهار الزامیست!");
                }else{
                    dTemp.dismiss();
                    registerTheTurnRequest(acPos, dec.getSelectedItemPosition() - 1);
                }
            }
        });
        admissionST.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDeclarationGroup(position, decGroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        decGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDeclaration(position, dec);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showTrackingCodeDialog(String _trackingCode)
    {
        TextView trackingCodePm;
        TextView trackingCode;
        View alertLayout = getActivity().getLayoutInflater().inflate(R.layout.tracking_code_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        trackingCodePm = (TextView)alertLayout.findViewById(R.id.trackingCode_pm);
        trackingCode = (TextView)alertLayout.findViewById(R.id.trachingCode);
        trackingCodePm.setText("درخواست نوبت شما با موفقیت ثبت شد، شما می توانید با استفاده از کد پیگیری ذیل درخواست خود را پیگیری نموده و یا لغو کنید.");
        trackingCode.setText(_trackingCode);
        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.
        }
    }

    private void registerTheTurnRequest(int acPos , int decPos)
    {
        changeFragmentView(PROGRESS_MODE);
        TheTurnRequestParams params = new TheTurnRequestParams();
        params.setMcId(selectedMyCar.getId());
        params.setDId(selectedDec.get(decPos).getId());
        params.setAcId(admissionData.get(acPos).getId());
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<TrackingCode> request = client.registerTheTurn(params);
        request.enqueue(new Callback<TrackingCode>() {
            @Override
            public void onResponse(Call<TrackingCode> call, Response<TrackingCode> response) {
                changeFragmentView(TURNING_MODE);
                Log.d("my log" , "............................ register turn request:" + response.code());
                if (response.code() == 200)
                {
                    TrackingCode trackingCode = response.body();
                    showTrackingCodeDialog(trackingCode.getTrackingCode());
                }else if (response.code() == 306)
                {
                    customToast.show(getActivity().getLayoutInflater() , getContext() , "ظرفیت تکمیل شده، موارد دیگر را انتخاب کنید");
                }
            }

            @Override
            public void onFailure(Call<TrackingCode> call, Throwable t) {
                Log.d("my log" , "............................ register turn request:" + t.getMessage());
                customToast.show(getActivity().getLayoutInflater(), getContext(), "خطایی رخ داده است، دوباره تلاش کنید");
            }
        });
    }

    private void loadAdmissionServiceType(Spinner spinner)
    {
        String[] items = new String[this.admissionServiceTypes.size() + 1];
        items[0] = "";
        for (int i = 1;i <= this.admissionServiceTypes.size(); i++)
        {
            items[i] = this.admissionServiceTypes.get(i - 1).getAstSubject();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void loadDeclarationGroup(int pos , Spinner spinner)
    {
        String[] items = null;
        if (pos == 0) {
            items = new String[1];
            items[0] = "";
        }else{
            List<DeclarationGroup> decGroup = this.admissionServiceTypes.get(pos - 1).getDeclarationGroup();
            selectedDecGroup = decGroup;
            items = new String[decGroup.size() + 1];
            items[0] = "";
            for (int i = 1; i <= decGroup.size(); i++) {
                items[i] = decGroup.get(i - 1).getDgSubject();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void loadDeclaration(int pos , Spinner spinner)
    {
        String[] items = null;
        if (pos == 0) {
            items = new String[1];
            items[0] = "";
        }else{
            List<Declaration> dec = this.selectedDecGroup.get(pos - 1).getDeclaration();
            selectedDec = dec;
            items = new String[dec.size() + 1];
            items[0] = "";
            for (int i = 1; i <= dec.size(); i++) {
                items[i] = dec.get(i - 1).getDSubject();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void getAllAdmissionServiceType()
    {
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<AdmissionServiceType>> request = client.getAllAdmissionServiceType();
        request.enqueue(new Callback<List<AdmissionServiceType>>() {
            @Override
            public void onResponse(Call<List<AdmissionServiceType>> call, Response<List<AdmissionServiceType>> response) {
                if (response.code() == 200) {
                    admissionServiceTypes = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<AdmissionServiceType>> call, Throwable t) {

            }
        });
    }

    private void reloadTurningList() {
        turningAdapter.clearData();
        for (int i=0 ; i < admissionData.size() ; i++) {
            admissionCapacity a = new admissionCapacity(admissionData.get(i).getAcDate(),
                    admissionData.get(i).getAdmissionCapacityTime().getActSubject(),
                    admissionData.get(i).getAcCapacity().toString(),
                    admissionData.get(i).getAcReservedCapacity().toString());
            turningAdapter.addItem(a);
        }
        changeFragmentView(ADMISSION_CAPACITY_LIST_MODE);
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

    public class admissionCapacity {
        String date;
        String time;
        String totalCapacity;
        String remainingCapacity;
        public admissionCapacity(String date , String time , String totalCapacity , String remainingCapacity) {
            this.date = date;
            this.time = time;
            this.totalCapacity = totalCapacity;
            this.remainingCapacity = remainingCapacity;
        }

        public String getDate() {
            return this.date;
        }

        public String getTime() {
            return this.time;
        }

        public String getTotalCapacity() {
            return totalCapacity;
        }

        public String getRemainingCapacity() {
            return remainingCapacity;
        }
    }

    public class TurningAdapter extends RecyclerView.Adapter<TurningAdapter.TurningViewHolder> {

        private ArrayList<admissionCapacity> dataSet;
        public class TurningViewHolder extends RecyclerView.ViewHolder {
            public TextView date;
            public TextView time;
            public TextView totalCapacity;
            public TextView remainingCapacity;
            public TextView turningRequest;
            public LinearLayout capacityBoard;
            public RelativeLayout completedPm;
            public LinearLayout registerBtnBoard;

            public TurningViewHolder(View view) {
                super(view);
                date = (TextView) view.findViewById(R.id.admission_date);
                time = (TextView) view.findViewById(R.id.admission_time);
                totalCapacity = (TextView) view.findViewById(R.id.totalCapacity);
                remainingCapacity = (TextView) view.findViewById(R.id.remainingCapacity);
                turningRequest = (TextView)view.findViewById(R.id.turningRequest);
                capacityBoard = (LinearLayout)view.findViewById(R.id.capacityBoard);
                registerBtnBoard = (LinearLayout)view.findViewById(R.id.registerBtnBoard);
                completedPm = (RelativeLayout)view.findViewById(R.id.completedPm);

            }
        }

        public void clearData(){
            dataSet.clear();
            turningAdapter.notifyDataSetChanged();
        }
        public TurningAdapter(ArrayList<admissionCapacity> data) {
            this.dataSet = data;
        }

        @Override
        public TurningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.turning_cardview, parent, false);
            TurningViewHolder myViewHolder = new TurningViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final TurningViewHolder holder, final int listPosition) {
            holder.date.setText(dataSet.get(listPosition).getDate());
            holder.time.setText(dataSet.get(listPosition).getTime());
            holder.totalCapacity.setText(dataSet.get(listPosition).getTotalCapacity());
            holder.remainingCapacity.setText(dataSet.get(listPosition).getRemainingCapacity());
            holder.turningRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRegDialog(listPosition);
                }
            });
            if (Integer.parseInt(dataSet.get(listPosition).getTotalCapacity()) > Integer.parseInt(dataSet.get(listPosition).getRemainingCapacity())) {
                holder.completedPm.setVisibility(RelativeLayout.GONE);
                holder.registerBtnBoard.setVisibility(LinearLayout.VISIBLE);
                holder.capacityBoard.setVisibility(LinearLayout.VISIBLE);
            }else{
                holder.capacityBoard.setVisibility(LinearLayout.GONE);
                holder.registerBtnBoard.setVisibility(LinearLayout.GONE);
                holder.completedPm.setVisibility(RelativeLayout.VISIBLE);
            }
        }

        public void addItem(admissionCapacity dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
