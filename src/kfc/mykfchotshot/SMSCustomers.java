package kfc.mykfchotshot;

import java.util.List;

import kfc.mykfchotshot.db.Cust;
import kfc.mykfchotshot.db.DB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SMSCustomers extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private String[] numbers;
	private int[] ids={R.id.sms_customer_send,R.id.sms_customer_reset};
	private EditText txt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_customer);
		Log.i("VIC","SMSCustomers page started");
		txt=(EditText) findViewById(R.id.sms_customer_msg);
		for(int i:ids){
			View v=findViewById(i);
			v.setOnClickListener(this);
		}
		init();
	}
	
	private void init(){
		DB db=new DB(this);
		List<Cust> list;
		list=db.getCustomers();
		numbers=new String[list.size()];
		for(int i=0;i<list.size();i++){
			numbers[i]=list.get(i).getNumber();
		}
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sms_customer_send:
			new AlertDialog.Builder(getBaseContext())
			.setTitle("Confirm")
			.setMessage("You are sending message to "+numbers.length+" customers.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					new SMS(SMSCustomers.this).sendSMS(numbers, txt.getText().toString().trim());
				}
			})
			.setNegativeButton("CANCEL", null)
			.show();
			break;
		case R.id.sms_customer_reset:
			txt.setText("");
			break;
		}
	}

}
