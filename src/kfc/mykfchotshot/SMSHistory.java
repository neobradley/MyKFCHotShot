package kfc.mykfchotshot;

import java.util.List;

import kfc.mykfchotshot.db.*;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SMSHistory extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ListView lv=getListView();
		lv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
		DB db=new DB(this);
		this.setListAdapter(new MyAdapter(db.getMessages()));
	}
	
	private class MyAdapter extends BaseAdapter{
		private final List<MSG> list;
		public MyAdapter(List<MSG> list){
			this.list=list;
		}
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View v, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(v==null){
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.smsrow, null);
			}
			TextView dt=(TextView) v.findViewById(R.id.sms_date);
			TextView txt=(TextView) v.findViewById(R.id.sms_cont);
			dt.setText(list.get(position).getTime());
			txt.setText("To :"+list.get(position).getNo()+"\n"+list.get(position).getMsg());
			return v;
		}
		
	}
}
