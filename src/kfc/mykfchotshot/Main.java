package kfc.mykfchotshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class Main extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout l = new LinearLayout(this);
		l.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.splash));
		setContentView(l);
		Log.i("VIC","KFC HotShot started");
		Thread nxt=new Thread(new Run());
		nxt.start();
	}
	
	public class Run implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try{
				Thread.sleep(1500);
				startActivity(new Intent(Main.this,Menu.class));
				Main.this.finish();
			}catch(Exception e){
				Log.w("VIC", "ERROR : "+e.getMessage());
			}
		}
		
	}
}