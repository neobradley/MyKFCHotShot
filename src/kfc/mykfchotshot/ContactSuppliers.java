package kfc.mykfchotshot;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class ContactSuppliers extends TabActivity implements OnTabChangeListener {
	/** Called when the activity is first created. */
	private TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("VIC","ContactSuppliers page started");
		setContentView(R.layout.phone_book);
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		tabHost.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg));
	    TabHost.TabSpec spec;
	    Intent intent;
	    intent = new Intent(this,ContactSupplierList.class);
	    spec = tabHost.newTabSpec("Contact List").setIndicator("Contact List")
	    .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent(this,ContactSupplierAdd.class);
	    spec = tabHost.newTabSpec("Create Contact").setIndicator("Create Contact")
	    .setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(0);
	    for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(R.color.transparent);
        } 
	}


	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(R.color.transparent);
        } 
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(R.color.transparent);
	}
}
