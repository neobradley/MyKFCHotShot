package kfc.mykfchotshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import kfc.mykfchotshot.db.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

public class ViewTopDaily extends Activity implements OnItemClickListener, OnClickListener{
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private ListView lv;
	private List<Stock> stocks,tmp;
	private List<Batch> batches;
	private List<Report> reports;
	private MyAdapter myadp;
	private DB db;
	private Calendar c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank_stock_list);
		c=Calendar.getInstance();
		db=new DB(this);
		lv=(ListView) findViewById(R.id.rank_list);
		stocks=db.getStocks();
		reports=db.getReports();
		batches=db.getBatches();
		lv.setOnItemClickListener(this);
		filtList();
		View v=findViewById(R.id.rank_selector);
		v.setOnClickListener(this);
	}
	private String[] days={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	public void filtList() {
		TextView tv=(TextView) findViewById(R.id.date);
		tv.setText((c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.YEAR)+" ("+days[c.getTime().getDay()]+")");
		tmp=FiltAlgo.getDailyStock(reports, batches, stocks, c.getTime());
		myadp=new MyAdapter(tmp);
		lv.setAdapter(myadp);
	}
	
	
	public class MyAdapter extends BaseAdapter {
		private List<Stock> stocks;
		public MyAdapter(List<Stock> stocks) {
			// TODO Auto-generated constructor stub
			this.stocks=stocks;
		}
		public int getCount() {
			// TODO Auto-generated method stub
			return stocks.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return stocks.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		public View getView(int pos, View v, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(v==null){
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.view_stock_row, null);
			}
			TextView tvName=(TextView) v.findViewById(R.id.stock_name);
			TextView tvQty=(TextView) v.findViewById(R.id.stock_qty);
			TextView tvDate=(TextView) v.findViewById(R.id.stock_date);
			TextView tvExp=(TextView) v.findViewById(R.id.stock_exp);
//			TextView tvSpec=(TextView) v.findViewById(R.id.stock_spec);
			Stock s=stocks.get(pos);
			tvName.setText(s.getName()+" ("+s.getId()+")");
			tvQty.setText(s.getTQty()+" "+s.getUnit()+" Sold");
			tvDate.setText("Price "+s.getTPrice());
			tvExp.setText("Net "+s.getTNet());
//			tvSpec.setText("");
			return v;
		}
	}


	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
		// TODO Auto-generated method stub
		Stock s=tmp.get(pos);
		new AlertDialog.Builder(this)
		.setTitle(s.getName())
		.setMessage("ID :"+s.getId()+
				"Total Sold: "+s.getTQty()+
				"\nTotal Price: "+s.getTPrice()+
				"\nTotal Net: "+s.getTNet()+
				"\nRemaining: "+s.getQty()+" "+s.getUnit()+
				"\nDate Stocked: "+sdf.format(s.getDate())+
				"\nExpiration: "+sdf.format(s.getExp()))
				.setPositiveButton("OK", null)
				.show();
	}

	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.rank_selector:
			new DatePickerDialog(ViewTopDaily.this,new DatePickerDialog.OnDateSetListener() {
				
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					c.set(year, monthOfYear, dayOfMonth);
					filtList();
					Log.i("L",c.getTime().toString());
				}
			},c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
			break;
		}
	}
	
	
}
