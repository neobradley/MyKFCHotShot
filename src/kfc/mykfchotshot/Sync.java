package kfc.mykfchotshot;

import kfc.mykfchotshot.db.Attr;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Sync extends Activity {
	/** Called when the activity is first created. */
    private ProgressDialog progDialog;
    private SyncBG sync;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout l=new LinearLayout(this);
		l.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg));
		setContentView(l);
		sync=new SyncBG(this);
		Log.i("VIC","Sync page started");
//		RequestRegister();
		showDialog(0);
		if(DoSync(Attr.site)){
			Thread t=new Thread(new Off());
			t.start();
			Toast.makeText(Sync.this, "Sync finished", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(Sync.this, "Sync failed", Toast.LENGTH_LONG).show();
			this.finish();
		}
	}
	
	public class Off implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1500);
				progDialog.dismiss();
				Sync.this.finish();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("L",e.getMessage());
			}
		}
		
	}

	@Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case 0:                      
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setMessage("Syncing...");
            progDialog.setCancelable(false);
            return progDialog;
        default:
            return null;
        }
    }
	
	private boolean DoSync(String path){
		boolean res=sync.Sync(path);
		if(res==false){
			Toast.makeText(Sync.this, "Syncing failed, please check network setting", Toast.LENGTH_LONG).show();
		}
		return res;
	}
	@SuppressWarnings("unused")
	private void RequestRegister(){
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.registermanager, null);
		final EditText name = (EditText) v.findViewById(R.id.regName);
		name.setText("Name in DB");
		final EditText pw = (EditText) v.findViewById(R.id.regPw);
		new AlertDialog.Builder(Sync.this)
		.setTitle("Please login for sync")
		.setView(v)
//		.setCancelable(false)
		.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				showDialog(0);
				String sName=name.getText().toString().trim();
				String sPw=pw.getText().toString().trim();
				Log.i("VIC",sName+" : "+sPw);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		})
		.show();
	}
}
