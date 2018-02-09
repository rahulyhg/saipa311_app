package key_team.com.saipa311.DB_Management;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by ammorteza on 12/22/17.
 */
@Table(name = "tbl_setting")
public class Setting extends Model {
    @Column(name = "notifState")
    public boolean notifState;

    @Column(name = "soundState")
    public boolean soundState;

    @Column(name = "vibrateState")
    public boolean vibrateState;

    public Setting(){
        super();
    }

    public static Setting getSetting()
    {
        return new Select()
                .from(Setting.class)
                .executeSingle();
    }

    public static boolean settingAvailable()
    {
        int notif = new Select().from(Setting.class).execute().size();
        if (notif == 0)
            return false;
        else
            return true;
    }

    public static boolean getNotifState()
    {
        Setting setting = Setting.getSetting();
        return setting.notifState;
    }

    public static boolean getSoundState()
    {
        Setting setting = Setting.getSetting();
        return setting.soundState;
    }

    public static boolean getVibrationState()
    {
        Setting setting = Setting.getSetting();
        return setting.vibrateState;
    }
}
