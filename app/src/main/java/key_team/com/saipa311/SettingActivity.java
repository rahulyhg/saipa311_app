package key_team.com.saipa311;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.DB_Management.ActiveRepresentation;
import key_team.com.saipa311.DB_Management.Setting;
import key_team.com.saipa311.Representations.JsonSchema.Representation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ammorteza on 12/1/17.
 */
public class SettingActivity extends AppCompatActivity {
    private SwitchCompat notifSwitch;
    private CheckBox soundSwitch;
    private CheckBox vibrateSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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

    private void init()
    {
        setVersionLabel();
        notifSwitch = (SwitchCompat)findViewById(R.id.setNotifSwitch);
        soundSwitch = (CheckBox)findViewById(R.id.setSoundcheckBox);
        vibrateSwitch = (CheckBox)findViewById(R.id.setVibrateCheckBox);
        notifSwitch.setChecked(Setting.getNotifState());
        soundSwitch.setChecked(Setting.getSoundState());
        vibrateSwitch.setChecked(Setting.getVibrationState());

        soundSwitch.setEnabled(Setting.getNotifState());
        vibrateSwitch.setEnabled(Setting.getNotifState());

        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Setting.getSetting();
                setting.notifState = isChecked;
                setting.save();

                soundSwitch.setEnabled(isChecked);
                vibrateSwitch.setEnabled(isChecked);
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Setting.getSetting();
                setting.soundState = isChecked;
                setting.save();
            }
        });

        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Setting.getSetting();
                setting.vibrateState = isChecked;
                setting.save();
            }
        });
    }

    private void setVersionLabel()
    {
        ((TextView)findViewById(R.id.versionLabel)).setText("V" + BuildConfig.VERSION_NAME);
    }

    private void createActionBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
