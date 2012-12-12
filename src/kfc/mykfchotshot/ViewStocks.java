package kfc.mykfchotshot;

import java.text.SimpleDateFormat;
import java.util.List;
import kfc.mykfchotshot.db.DB;
import kfc.mykfchotshot.db.SortAlgo;
import kfc.mykfchotshot.db.Stock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;

public class ViewStocks extends Activity implements OnItemSelectedListener, OnItemClickListener {
	/** Called when the activity is first created. */
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private ListView lv;
	private List<Stock> stocks;
	private DB db;
	private MyAdapter myadp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stock);
		Log.i("VIC", "ViewStocks page started");
		db=new DB(this);
		Spinner spin = (Spinner) findViewById(R.id.spinner_category);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.view_stock_category,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(this);
		lv = (ListView) findViewById(R.id.view_stock_List);
		stocks=db.getStocks();
		myadp=new MyAdapter(stocks);
		lv.setAdapter(myadp);
		lv.setOnItemClickListener(this);
	}

	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
		// TODO Auto-generated method stub
		switch(pos){
		case 0:
			stocks=SortAlgo.srtStockQty(stocks);
			refreshList();
			break;
		case 1:
			stocks=SortAlgo.srtStockName(stocks);
			refreshList();
			break;
		case 2:
			stocks=SortAlgo.srtStockDate(stocks);
			refreshList();
			break;
		case 3:
			stocks=SortAlgo.srtStockExp(stocks);
			refreshList();
			break;
		}
//		Toast.makeText(ViewStocks.this,
//				parent.getItemAtPosition(pos).toString()+"="+pos, Toast.LENGTH_SHORT)
//				.show();
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	public void refreshList() {
		myadp=new MyAdapter(stocks);
		myadp.notifyDataSetChanged();
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

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return stocks.get(arg0);
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
			tvQty.setText(s.getQty()+" "+s.getUnit()+" Remaining");
			tvDate.setText("Stocked "+sdf.format(s.getDate()));
			tvExp.setText("Exp. "+sdf.format(s.getExp()));
//			tvSpec.setText("");
			return v;
		}
		
		
	}

	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(ViewStocks.this).setItems(R.array.view_stock_options,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int index) {
						// TODO Auto-generated method stub
						switch(index){
						case 0:startActivity(new Intent(ViewStocks.this,ContactSuppliers.class));
							break;
						case 1:startActivity(new Intent(ViewStocks.this,SMSCustomers.class));
							break;
						default:
							break;
						}
					}
				}).show();
	}
}