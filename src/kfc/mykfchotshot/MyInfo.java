package kfc.mykfchotshot;

import java.util.List;

import kfc.mykfchotshot.db.DB;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MyInfo extends Activity {
	/** Called when the activity is first created. */
	private DB db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		Log.i("VIC","MyInfo page started");
		db=new DB(this);
		init();
	}
	
	private void init(){
		List<String> data=db.getInfo();
		if(data.size()>0){
			TextView title=(TextView) findViewById(R.id.info_name);
			title.setText(data.get(1).toUpperCase()+" ("+data.get(0)+")");
			TextView text=(TextView) findViewById(R.id.info_content);
			text.setText("Branch Name :\n"+data.get(4).toUpperCase()+" ("+data.get(3)+")"+
			"\nBranch Phone :\n"+data.get(5)+
			"\nBranch Address :\n"+data.get(6).toUpperCase()+
			"\nLast update :\n"+data.get(2));
		}else{
			Toast.makeText(MyInfo.this, "Not login yet", Toast.LENGTH_SHORT).show();
		}
	}
}
