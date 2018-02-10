package key_team.com.saipa311;

import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Representations.JsonSchema.Representation;
import key_team.com.saipa311.Representations.JsonSchema.Service;
import key_team.com.saipa311.Representations.JsonSchema.Tell;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class RepresentationActivity extends AppCompatActivity {
    private List<Representation> representations;
    private RecyclerView recyclerView;
    private RepresentationAdapter representationAdapter;
    private ArrayList<representation> representationList;
    private ViewFlipper viewFlipper;
    private static final int REPRESENTATION_MODE = 1;
    private static final int PROGRESS_MODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representation);
        createActionBar();
        init();
    }

    @Override
    protected void onPause() {
        MyCustomApplication.activityPaused();
        super.onPause();
    }

    @Override
    protected void onResume() {
        MyCustomApplication.activityResumed();
        super.onResume();
    }

    public void displayNoInternetConnectionError()
    {
        TextView reTry_btn;
        View alertLayout = getLayoutInflater().inflate(R.layout.no_internet_connection_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        reTry_btn = (TextView)alertLayout.findViewById(R.id.reTry);
        builder.setView(alertLayout);
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                System.exit(0);
            }
        });
        final AlertDialog dTemp = builder.show();
        reTry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                dTemp.dismiss();
            }
        });
    }

    private void setActiveRepresentation(Representation selectedRep)
    {
        new Delete().from(ActiveRepresentation.class).execute();
        ActiveRepresentation activeRepresentation = new ActiveRepresentation();
        activeRepresentation.repId = selectedRep.getId();
        activeRepresentation.name = selectedRep.getRName();
        activeRepresentation.code = selectedRep.getRCode();
        activeRepresentation.save();

        Log.d("my log" , "................. set active representation ");
    }

    private void createActionBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void fetchRepresentations()
    {
        changeActivityView(PROGRESS_MODE);
        final StoreClient client = ServiceGenerator.createService(StoreClient.class);
        final Call<List<Representation>> request = client.fetchAllRepresentations();
        request.enqueue(new Callback<List<Representation>>() {
            @Override
            public void onResponse(Call<List<Representation>> call, Response<List<Representation>> response) {
                if (response.code() == 200) {
                    representations = response.body();
                    loadRepresentations();
                } else {
                    customToast.show(getLayoutInflater(), RepresentationActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
                }
            }

            @Override
            public void onFailure(Call<List<Representation>> call, Throwable t) {
                customToast.show(getLayoutInflater(), RepresentationActivity.this, "خطایی رخ داده است دوباره تلاش کنید");
            }
        });
    }

    private void init()
    {
        viewFlipper = (ViewFlipper)findViewById(R.id.representation_view);

        recyclerView = (RecyclerView)findViewById(R.id.representation_recycler_view);
        representationList = new ArrayList<>();
        representationAdapter = new RepresentationAdapter(representationList);
        recyclerView.setAdapter(representationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (PublicParams.getConnectionState(this))
        {
            fetchRepresentations();
        }else{
            displayNoInternetConnectionError();
        }

    }

    private void changeActivityView(int childId)
    {
        invalidateOptionsMenu();
        viewFlipper.setDisplayedChild(childId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (!ActiveRepresentation.activeRepresentationIsSet())
                {
                    if (representations.size() > 0)
                        setActiveRepresentation(representations.get(0));
                    setResult(RESULT_OK);
                }
                else
                    setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!ActiveRepresentation.activeRepresentationIsSet())
        {
            if (representations.size() > 0)
                setActiveRepresentation(representations.get(0));
            setResult(RESULT_OK);
        }
        else
            setResult(RESULT_CANCELED);

        finish();
        super.onBackPressed();
    }

    private void loadRepresentations() {
        representationAdapter.clearData();
        for (int i = 0; i < representations.size(); i++) {
            representation a = new representation(representations.get(i).getRName(),
                    representations.get(i).getRCode(),
                    representations.get(i).getRDescription(),
                    representations.get(i).getRAddress(),
                    representations.get(i).getTell(),
                    representations.get(i).getService());
            representationAdapter.addItem(a);
        }
        changeActivityView(REPRESENTATION_MODE);
    }

    ////////////////////////// my car adapter ///////////////////////////////////////
    public class representation {
        String _subject;
        String _code;
        String _description;
        String _address;
        List<Tell> _tell;
        List<Service> _service;
        public representation(String subject,
                     String code,
                     String  description,
                     String address,
                     List<Tell> tell,
                     List<Service> services) {
            this._subject = subject;
            this._code = code;
            this._description = description;
            this._address = address;
            this._tell = tell;
            this._service = services;
        }

        public String getSubject() {
            return this._subject;
        }

        public String getCode() {
            return this._code;
        }

        public String getDescription() {
            return this._description;
        }

        public String getAddress() {
            return this._address;
        }

        public List<Tell> getTell() {
            return this._tell;
        }

        public List<Service> getService() {
            return this._service;
        }
    }

    public class RepresentationAdapter extends RecyclerView.Adapter<RepresentationAdapter.RepresentationViewHolder> {

        private ArrayList<representation> dataSet;
        public class RepresentationViewHolder extends RecyclerView.ViewHolder {
            public TextView subject;
            public TextView code;
            public TextView description;
            public TextView address;
            public LinearLayout tell;
            public LinearLayout companyIcon;

            public RepresentationViewHolder(View view) {
                super(view);
                subject = (TextView) view.findViewById(R.id.subject);
                code = (TextView) view.findViewById(R.id.code);
                description = (TextView) view.findViewById(R.id.description);
                address = (TextView) view.findViewById(R.id.address);
                tell = (LinearLayout) view.findViewById(R.id.rep_tell);
                companyIcon = (LinearLayout) view.findViewById(R.id.rep_companyIcon);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        setActiveRepresentation(representations.get(getAdapterPosition()));
                        setResult(RESULT_OK);
                        finish();
/*                        if (recursive == true) {
                            Intent resultIntent = new Intent();
                            String arrayAsString = new Gson().toJson(myCars.get(getAdapterPosition()));
                            resultIntent.putExtra("selectedMyCar", arrayAsString);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }*/
                        //Log.d("my log" , "............................ " + getAdapterPosition());
/*                        Intent intent = new Intent(getActivity(), NewCarInfoActivity.class);
                        String arrayAsString = new Gson().toJson(newCarData.get(getAdapterPosition()));
                        intent.putExtra("newCarInfo", arrayAsString);
                        startActivity(intent);*/
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            representationAdapter.notifyDataSetChanged();
        }
        public RepresentationAdapter(ArrayList<representation> data) {
            this.dataSet = data;
        }

        @Override
        public RepresentationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.representation_cardview, parent, false);
            RepresentationViewHolder myViewHolder = new RepresentationViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final RepresentationViewHolder holder, final int listPosition) {
            holder.subject.setText("نمایندگی " + dataSet.get(listPosition).getSubject());
            holder.code.setText("کد " + dataSet.get(listPosition).getCode());
            holder.description.setText(dataSet.get(listPosition).getDescription());
            holder.address.setText(dataSet.get(listPosition).getAddress());
            for (int i = 0; i < dataSet.get(listPosition).getTell().size(); i++) {
                LinearLayout.LayoutParams tel_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tel_number = new TextView(RepresentationActivity.this);
                tel_number.setTextColor(getResources().getColor(android.R.color.black));
                tel_number.setText(dataSet.get(listPosition).getTell().get(i).getTNumber());
                holder.tell.addView(tel_number, tel_layoutParams);
            }

            LinearLayout.LayoutParams tel_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tel_layoutParams.setMargins(8, 0, 8, 0);
            List<Integer> companyId = new ArrayList<>();
            for (int i=0;i<dataSet.get(listPosition).getService().size(); i++)
            {
                int j =0;
                for (;j<companyId.size() ; j++)
                {
                    if (companyId.get(j) == dataSet.get(listPosition).getService().get(i).getProduct().getCompany().getId())
                        break;
                }
                if (j == companyId.size())
                {
                    companyId.add(dataSet.get(listPosition).getService().get(i).getProduct().getCompany().getId());
                    ImageView c1 = new ImageView(RepresentationActivity.this);
                    c1.setAdjustViewBounds(true);
                    holder.companyIcon.addView(c1, tel_layoutParams);
                    new DownloadImageTask(c1).execute(PublicParams.BASE_URL + dataSet.get(listPosition).getService().get(i).getProduct().getCompany().getCoIconPath());
                }
            }
        }

        public void addItem(representation dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
