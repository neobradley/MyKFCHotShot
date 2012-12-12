package kfc.mykfchotshot;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class ViewTopSellers extends TabActivity implements OnTabChangeListener {
	/** Called when the activity is first created. */
	TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("VIC","ViewTopSellers page started");
		
		tabHost = getTabHost();tabHost.setOnTabChangedListener(this);
		tabHost.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg));
	    TabHost.TabSpec spec;
	    Intent intent;
	    intent = new Intent(this,ViewTopDaily.class);
	    spec = tabHost.newTabSpec("Daily").setIndicator("Daily")
	    .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent(this,ViewTopWeekly.class);
	    spec = tabHost.newTabSpec("Weekly").setIndicator("Weekly")
	    .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent(this,ViewTopMonthly.class);
	    spec = tabHost.newTabSpec("Monthly").setIndicator("Monthly")
	    .setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(0);
	    for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(R.color.transparent);
        }
	}

	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(R.color.transparent);
        } 
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(R.color.transparent);
	}
}
