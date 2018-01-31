package key_team.com.saipa311;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import key_team.com.saipa311.Sale_services.SaleServicesFragment;
import key_team.com.saipa311.Services.MyStartServiceReceiver;
import key_team.com.saipa311.Services.RunState;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ImageButton toolBarBotifButton;
    private ViewPager mainViewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;
    private int intentType;
    private Toast endToast;
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.getIntentType();
        MyCustomApplication.appCreate();
        this.createActionBar();
        this.createNavigationDrawer();
        this.initBottomNavigationView();
        this.setupViewPager();
        this.setNotifService(this);
        this.checkIntentType();
        this.init();
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

    private void init()
    {
        endToast = new Toast(MainActivity.this);
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
        toolBarBotifButton = (ImageButton) findViewById(R.id.tool_bar_notif_btn);
        toolBarBotifButton.setOnClickListener(new View.OnClickListener() {
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
/*        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getDefault());
        updateTime.set(Calendar.HOUR_OF_DAY, 12);
        updateTime.set(Calendar.MINUTE, 30);*/
        //if (!this.isAlaram(this)) {
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
        //this.setMessageReceiver();
        //}

    }

    private void setupViewPager()
    {
        mainViewPager = (ViewPager)findViewById(R.id.viewpager);
        mainViewPager.setOffscreenPageLimit(4);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager() , 4);
        mainViewPager.setAdapter(adapter);
        mainViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
