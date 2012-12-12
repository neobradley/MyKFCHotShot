package kfc.mykfchotshot;

import kfc.mykfchotshot.db.Attr;
import kfc.mykfchotshot.db.DB;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TableLayout;

public class Menu extends Activity implements OnClickListener {
	public final static int[] KFC_MEMU_VIEWS = { R.id.layoutViewStock,
			R.id.layoutViewSeller, R.id.layoutSuppPB, R.id.layoutCustSMS,
			R.id.layoutSync, R.id.layoutMyInfo };
	private DB db;
    private ProgressDialog progDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("VIC", "Menu page started");
		setContentView(R.layout.main);
		TableLayout l=(TableLayout) findViewById(R.id.menu_layout);
		l.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg));
		db=new DB(this);
//		db.Register(1, "l", "2011-11-10 11:11:11", 1, "taft", "9123333", "taft");
		if(!db.HasRegistered())
			RequestRegister();
		
		for (int id : Menu.KFC_MEMU_VIEWS) {
			View v = findViewById(id);
			v.setOnClickListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.i("VIC","Menu "+item.getTitle()+" is selected");
		switch(item.getItemId()){
		case R.id.menu1:
			startActivity(new Intent(Menu.this,Setting.class));
			break;
		case R.id.menu2:
			Help();
			break;
		case R.id.menu3:
			startActivity(new Intent(Menu.this,SMSHistory.class));
			break;
		case R.id.menu4:
			AboutUs();
			break;
		}
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("VIC", "Switch Page");
		Intent nextPage = new Intent();
		switch (v.getId()) {
		case R.id.layoutViewStock:
			nextPage = new Intent(Menu.this, ViewStocks.class);
			break;
		case R.id.layoutViewSeller:
			nextPage = new Intent(Menu.this, ViewTopSellers.class);
			break;
		case R.id.layoutSuppPB:
			nextPage = new Intent(Menu.this, ContactSuppliers.class);
			break;
		case R.id.layoutCustSMS:
			nextPage = new Intent(Menu.this, SMSCustomers.class);
			break;
		case R.id.layoutSync:
			nextPage = new Intent(Menu.this, Sync.class);
			break;
		case R.id.layoutMyInfo:
			nextPage = new Intent(Menu.this, MyInfo.class);
			break;
		}
		startActivity(nextPage);
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case 0:                      
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setMessage("Login and Syncing...");
            progDialog.setCancelable(false);
            return progDialog;
        default:
            return null;
        }
    }
	
	private void AboutUs(){
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.aboutus, null);
		new AlertDialog.Builder(Menu.this)
		.setView(v)
		.setPositiveButton("OK", null)
		.show();
	}
	
	private void Help(){
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.help, null);
		new AlertDialog.Builder(Menu.this)
		.setView(v)
		.setPositiveButton("OK", null)
		.show();
	}
	
	private void RequestRegister(){
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.registermanager, null);
		final EditText name = (EditText) v.findViewById(R.id.regName);
		final EditText pw = (EditText) v.findViewById(R.id.regPw);
		new AlertDialog.Builder(Menu.this)
		.setTitle("Please login for the first time")
		.setView(v)
//		.setCancelable(false)
		.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				showDialog(0);
				String sName=name.getText().toString().trim();
				String sPw=pw.getText().toString().trim();
				Log.i("VIC",sName+" : "+sPw);
				WebView webView = new WebView(Menu.this);
				webView.getSettings().setJavaScriptEnabled(true);
				webView.addJavascriptInterface(new JavaScriptInterface(Menu.this), "Android");
				webView.setWebChromeClient(new MyWebChromeClient());
				webView.loadUrl("http://"+Attr.site+"/login.php?id="+sName+"&pw="+sPw);
				showDialog(0);
			}
		})
		.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Menu.this.finish();
			}
		})
		.show();
	}
	
	public class JavaScriptInterface {
		Context mContext;
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void Register(int id, String name,  String log, int bid,String bname,String bno, String bad){
	    	db.Register(id, name, log, bid, bname, bno, bad);
	    	progDialog.dismiss();
	    }
	}
	
	class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			if (message.length() != 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
				builder.setTitle("From JavaScript").setMessage(message).show();
				result.cancel();
				return true;
			}
			return false;
		}
	}
}
