package key_team.com.saipa311;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class customToast extends Activity {
	public static void show(LayoutInflater l,Context context,String pm)
	{
		LayoutInflater inflater = l;
        View layout = inflater.inflate(R.layout.custom_toast,null);
		TextView textView=(TextView)layout.findViewById(R.id.toastText);
		textView.setText(pm);
		Toast t=new Toast(context);
		t.setDuration(Toast.LENGTH_LONG);
		t.setView(layout);
		t.show();
	}

}
