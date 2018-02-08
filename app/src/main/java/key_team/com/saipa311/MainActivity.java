package key_team.com.saipa311;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCar;
import key_team.com.saipa311.AfterSale_services.MyCarActivity;
import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.Options.JsonSchema.CarOption;
import key_team.com.saipa311.Options.JsonSchema.CarOptionsRequestParams;
import key_team.com.saipa311.Options.OptionInfoActivity;
import key_team.com.saipa311.Sale_services.SaleServicesFragment;
import key_team.com.saipa311.Services.JsonSchema.Events.Event;
import key_team.com.saipa311.Services.JsonSchema.Events.EventRequestParams;
import key_team.com.saipa311.Services.MyStartServiceReceiver;
import key_team.com.saipa311.Services.RunState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Event> events;
    private RecyclerView eventRecyclerView;
    private MainViewPagerAdapter adapter;
    private List<CarOption> eventData;
    private EventsAdapter eventsAdapter;
    private ArrayList<eventItem> eventList;
    private DrawerLayout mDrawerLayout;
    private ImageButton toolBarNotifButton;
    private ImageButton toolBarRepresentationButton;
    private LinearLayout representationInfo;
    private TextView representationName;
    private TextView representationCode;
    private ViewPager mainViewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;
    private int intentType;
    private Toast endToast;
    private boolean doubleBackToExitPressedOnce = false;
    private BroadcastReceiver mMessageReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getIntentType();
        MyCustomApplication.appCreate();
        this.createActionBar();
        this.createNavigationDrawer();
        this.initBottomNavigationView();
        this.init();
        this.setNotifService(this);
        this.checkIntentType();
        this.setupViewPager();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.getIntentType();
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        MyCustomApplication.appDestroy();
        super.onDestroy();
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

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        else {
            if (doubleBackToExitPressedOnce) {
                endToast.cancel();
                finish();
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            endToast.setDuration(Toast.LENGTH_SHORT);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,null);
            TextView textView=(TextView)layout.findViewById(R.id.toastText);
            textView.setText("لطفا برای خروج دو بار لمس کنید");
            endToast.setDuration(Toast.LENGTH_SHORT);
            endToast.setView(layout);
            endToast.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) // representation activity
        {
            if (resultCode == RESULT_OK)
            {
                setupViewPager();
            }
        }
    }

    private void init()
    {
        endToast = new Toast(MainActivity.this);
        eventList = new ArrayList<>();
        eventsAdapter = new EventsAdapter(eventList);
        eventRecyclerView = (RecyclerView)findViewById(R.id.event_recycler_view);
        eventRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        eventRecyclerView.setAdapter(eventsAdapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.recycler_item_offset);
        eventRecyclerView.addItemDecoration(itemDecoration);
        toolBarRepresentationButton = (ImageButton)findViewById(R.id.tool_bar_representation_btn);
        toolBarRepresentationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRepresentationPage();
            }
        });

        representationName = (TextView)findViewById(R.id.representation_name);
        representationCode = (TextView)findViewById(R.id.representation_code);
        representationInfo = (LinearLayout)findViewById(R.id.representation_info);
        representationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepresentationInfoDialog.show(MainActivity.this);
            }
        });
    }

    private void openRepresentationPage()
    {
        Intent intent = new Intent(MainActivity.this , RepresentationActivity.class);
        startActivityForResult(intent, 0);
    }

    private void checkIntentType()
    {
        if (intentType == PublicParams.OPTION_NOTIFICATION_ID)
        {
            View view = bottomNavigationView.findViewById(R.id.options);
            view.performClick();
        }else if (intentType == PublicParams.EVENT_NOTIFICATION_ID)
        {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    private void getIntentType()
    {
        Intent intent = getIntent();
        intentType = intent.getIntExtra("notificationType", 0);
    }

    private void createNavigationDrawer()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolBarNotifButton = (ImageButton) findViewById(R.id.tool_bar_notif_btn);
        toolBarNotifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void createActionBar()
    {
        Typeface type;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(.color.colorPrimary)));
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, layoutParams);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    private void initBottomNavigationView()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.saleServices:
                                mainViewPager.setCurrentItem(0);
                                break;
                            case R.id.afterSaleServices:
                                mainViewPager.setCurrentItem(1);
                                break;
                            case R.id.customerServices:
                                mainViewPager.setCurrentItem(2);
                                break;
                            case R.id.options:
                                mainViewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });
    }

    private void setNotifService(Context context) {
        RunState runState = new RunState(MainActivity.this);
        if (runState.get() == false) {
            Intent downloader = new Intent(context, MyStartServiceReceiver.class);
            downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1365, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), PublicParams.REPEAT_TIME, pendingIntent);
            //Log.d("MyActivity", "Set alarmManager.setRepeating to: " + updateTime.getTime().toLocaleString());
            runState.set(true);
        }
        this.setMessageReceiver();
    }

    private void setMessageReceiver()
    {
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int notifType = intent.getIntExtra("notifType", 0);
                if (notifType == PublicParams.SURVEY_FORM_AVAILABLE) {
                    Intent surveyFormIntent = new Intent(MainActivity.this , SurveyFormActivity.class);
                    surveyFormIntent.putExtra("surveyFormId" , intent.getIntExtra("surveyFormId" , 0));
                    startActivity(surveyFormIntent);
                    Log.d("my log", ".......................... notifType : surveyForm");
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("SaipaServiceSignal"));
    }

    private void setupViewPager()
    {
        if (PublicParams.getConnectionState(this))
        {
            if (ActiveRepresentation.activeRepresentationIsSet()) {
                if (ActiveRepresentation.activeRepresentationIsSet())
                {
                    ActiveRepresentation activeRepresentation = ActiveRepresentation.getActiveRepresentationInfo();
                    representationName.setText("نمایندگی " + activeRepresentation.name);
                    representationCode.setText("کد " + activeRepresentation.code);
                }
                mainViewPager = (ViewPager) findViewById(R.id.viewpager);
                mainViewPager.setOffscreenPageLimit(4);
                adapter = new MainViewPagerAdapter(getSupportFragmentManager(), 4);
                mainViewPager.setAdapter(adapter);
                mainViewPager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (prevMenuItem != null) {
                            prevMenuItem.setChecked(false);
                        } else {
                            bottomNavigationView.getMenu().getItem(0).setChecked(false);
                        }

                        bottomNavigationView.getMenu().getItem(position).setChecked(true);
                        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                this.fetchAllEvents();
            }else{
                openRepresentationPage();
            }
        }else{
            displayNoInternetConnectionError();
        }
    }

    private void fetchAllEvents()
    {
        EventRequestParams params = new EventRequestParams();
        params.setDeviceId(PublicParams.deviceId(this));
        StoreClient client = ServiceGenerator.createService(StoreClient.class);
        Call<List<Event>> request = client.fetchAllEvents(params);
        request.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.code() == 200) {
                    events = response.body();
                    displayUnViewedEvents();
                    prepareEvents();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
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
                setupViewPager();
                dTemp.dismiss();
            }
        });

    }

    private void displayUnViewedEvents()
    {
        LinearLayout alarm = (LinearLayout)findViewById(R.id.notif_unread_count_alarm);
        TextView unViewedEvent = (TextView)findViewById(R.id.notif_unread_count);
        int count = 0;
        for (int i =0 ; i < events.size() ; i++)
        {
            if (events.get(i).getViewed().get(0).getEdViewed() == 0)
                count++;
        }

        if (count > 0)
        {
            alarm.setVisibility(LinearLayout.VISIBLE);
            unViewedEvent.setText(count + "");
        }
        else
        {
            alarm.setVisibility(LinearLayout.GONE);
            unViewedEvent.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_myCar:
                Intent myCarIntent = new Intent(MainActivity.this , MyCarActivity.class);
                startActivity(myCarIntent);
                break;
            case R.id.action_representations:
                Intent intent = new Intent(MainActivity.this , RepresentationActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.action_profile:
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareEvents()
    {
        eventsAdapter.clearData();
        for (int i=0 ; i < events.size() ; i++) {
            eventItem a = new eventItem(events.get(i).getESubject(),
                    events.get(i).getEDescription(),
                    "نمایندگی " + events.get(i).getRepresentation().getRCode() + " " + events.get(i).getRepresentation().getRName(),
                    events.get(i).getViewed().get(0).getEdViewed());
            eventsAdapter.addItem(a);
        }
    }

    public class eventItem {
        String subject;
        String description;
        String repInfo;
        int viewed;
        public eventItem(String subject, String desc, String repInfo , int viewed) {
            this.subject = subject;
            this.description = desc;
            this.repInfo = repInfo;
            this.viewed = viewed;
        }

        public String getSubject() {
            return this.subject;
        }

        public String getDescription() {
            return description;
        }

        public String getRepInfo() {
            return repInfo;
        }

        public int getViewed() {
            return viewed;
        }
    }

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

        private ArrayList<eventItem> dataSet;
        public class EventsViewHolder extends RecyclerView.ViewHolder {
            public TextView subject;
            public TextView description;
            public TextView repInfo;

            public EventsViewHolder(View view) {
                super(view);
                subject = (TextView) view.findViewById(R.id.subject);
                description = (TextView) view.findViewById(R.id.description);
                repInfo = (TextView) view.findViewById(R.id.repInto);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Log.d("my log" , "............................ " + getAdapterPosition());
                        Intent intent = new Intent(MainActivity.this, EventInfoActivity.class);
                        String arrayAsString = new Gson().toJson(events.get(getAdapterPosition()));
                        intent.putExtra("eventInfo", arrayAsString);
                        startActivity(intent);
                        events.get(getAdapterPosition()).getViewed().get(0).setEdViewed(1);
                        displayUnViewedEvents();
                        prepareEvents();
                    }
                });
            }
        }

        public void clearData(){
            dataSet.clear();
            eventsAdapter.notifyDataSetChanged();
        }
        public EventsAdapter(ArrayList<eventItem> data) {
            this.dataSet = data;
        }

        @Override
        public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            EventsViewHolder myViewHolder = new EventsViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final EventsViewHolder holder, final int listPosition) {
            holder.subject.setText(dataSet.get(listPosition).getSubject());
            if (dataSet.get(listPosition).getViewed() == 1)
            {
                holder.subject.setTypeface(null, Typeface.NORMAL);
            }
            holder.description.setText(dataSet.get(listPosition).getDescription());
            holder.repInfo.setText(dataSet.get(listPosition).getRepInfo());
        }

        public void addItem(eventItem dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }

}
