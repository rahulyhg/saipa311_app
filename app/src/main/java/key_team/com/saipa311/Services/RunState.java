package key_team.com.saipa311.Services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class RunState {
	private SharedPreferences saipa;
	private Activity context;
	public RunState(Activity con)
	{
		context = con;
		saipa = context.getSharedPreferences("Saipa", 0);
	}
	
	public boolean get()
	{
        boolean state = saipa.getBoolean("firstRun" , false);
        return state;
	}
	
	public void set(boolean s)
	{
		Editor editor = saipa.edit();
		editor.putBoolean("firstRun", s);
		editor.commit();
	}
}
