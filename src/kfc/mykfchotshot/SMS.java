package kfc.mykfchotshot;

import java.text.SimpleDateFormat;
import java.util.Date;

import kfc.mykfchotshot.db.DB;
import kfc.mykfchotshot.db.MSG;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMS {
	private Context cont;
	private SmsManager sms;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public SMS(Context context){
		cont=context;
	}
	
	public void sendSMS(String[] numbers,String msg){
		DB db= new DB(cont);
		sms=SmsManager.getDefault();
		PendingIntent mPI = PendingIntent.getBroadcast(cont, 0, new Intent(), 0);
		for(String no:numbers){
			try{
				sms.sendTextMessage(no, null, msg, mPI, null);
				db.insertMSG(new MSG(no,msg,sdf.format(new Date())));
			}catch(Exception e){
				Toast.makeText(cont, "Message not send", Toast.LENGTH_SHORT).show();
			}
		}
		Toast.makeText(cont, "Message sent", Toast.LENGTH_SHORT).show();
	}
}
