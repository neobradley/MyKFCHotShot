package kfc.mykfchotshot;

import kfc.mykfchotshot.db.DB;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class Setting extends Activity implements OnClickListener{
	
	CheckBox auto;
	RadioButton min,hour,day;
	DB db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		db=new DB(this);
		auto=(CheckBox) findViewById(R.id.check_sync);
		min=(RadioButton) findViewById(R.id.radio_minute);
		hour=(RadioButton) findViewById(R.id.radio_hour);
		day=(RadioButton) findViewById(R.id.radio_day);
		auto.setOnClickListener(this);
		disableAll();
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.check_sync:
			if(auto.isChecked()){
				enableAll();
			}
			if(!auto.isChecked()){
				disableAll();
			}
			break;
		case R.id.sync_confirm:
			save();
			break;
		}
	}
	
	private void enableAll(){
		min.setEnabled(true);
		hour.setEnabled(true);
		day.setEnabled(true);
	}
	
	private void disableAll(){
		min.setEnabled(false);
		hour.setEnabled(false);
		day.setEnabled(false);
	}
	
	private void save(){
		
	}
}
