package kfc.mykfchotshot.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final String[] tbls = {
			Attr.C + Attr.Mgr + "(" + Attr.MgrID + Attr.PK + Attr.MgrN
					+ " text," + Attr.MgrLog + " text)",// tbl mgr
			Attr.C + Attr.Brch + "(" + Attr.BrchID + Attr.PK + Attr.BrchN
					+ " text," + Attr.BrchNo + " text," + Attr.BrchAd
					+ " text," + Attr.BrchM + " text)",// tbl brch
			Attr.C + Attr.St + "(" + Attr.StID + Attr.PK + Attr.StN + " text,"
					+ Attr.StU + " text," + Attr.StP + " text," + Attr.StQ
					+ " text," + Attr.StD + " text," + Attr.StE + " text)",// tbl
																			// st
			// Attr.C + Attr.Inv + "(" + Attr.InvID + Attr.PK + Attr.InvB
			// + " integer," + Attr.InvS + " integer," + Attr.InvQ
			// + " integer," + Attr.InvD + " date)",// tbl inv
			Attr.C + Attr.Bat + "(" + Attr.BatID + " integer," + Attr.BatS
					+ " integer," + Attr.BatQ + " integer," + Attr.BatP
					+ " double," + Attr.BatN + " double)",// tbl bat
			Attr.C + Attr.Rep + "(" + Attr.RepID + Attr.PK + Attr.RepBr
					+ " integer," + Attr.RepBa + " integer," + Attr.RepP
					+ " double," + Attr.RepN + " double," + Attr.RepD
					+ " text)",// tbl rep
			Attr.C + Attr.Cus + "(" + Attr.CusID + Attr.PK + Attr.CusN
					+ " text," + Attr.CusNo + " text)",// tbl cus
			Attr.C + Attr.SMS + "(" + Attr.SMSN + " text," + Attr.SMST
					+ " text," + Attr.SMSDT + " text)",// tbl sms
			Attr.C + Attr.Set + "(" + Attr.SetU + " text," + Attr.SetM
					+ " text," + Attr.SetT + " text)"// tbl set

	};

	public DB(Context context) {
		super(context, Attr.DBN, null, Attr.DBV);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		for (String tbl : tbls)
			db.execSQL(tbl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public boolean HasRegistered() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * from " + Attr.Mgr, null);
		boolean res = cur.getCount() == 0 ? false : true;
		Log.i("VIC", "Count " + cur.getCount());
		cur.close();
		db.close();
		return res;
	}

	public void Register(int id, String name, String log, int bid,
			String bname, String bno, String bad) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Attr.MgrID, id);
		cv.put(Attr.MgrN, name);
		cv.put(Attr.MgrLog, log);
		db.insert(Attr.Mgr, null, cv);
		cv = new ContentValues();
		cv.put(Attr.BrchID, bid);
		cv.put(Attr.BrchN, bname);
		cv.put(Attr.BrchNo, bno);
		cv.put(Attr.BrchAd, bad);
		cv.put(Attr.BrchM, id);
		db.insert(Attr.Brch, null, cv);
		db.close();
	}
	
	

	public List<String> getInfo() {
		List<String> data = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM " + Attr.Mgr, null);
		while (cur.moveToNext()) {
			data.add(String.valueOf(cur.getInt(cur.getColumnIndex(Attr.MgrID))));
			data.add(cur.getString(cur.getColumnIndex(Attr.MgrN)));
			data.add(cur.getString(cur.getColumnIndex(Attr.MgrLog)));
		}
		cur.close();
		cur = db.rawQuery("SELECT * FROM " + Attr.Brch, null);
		while (cur.moveToNext()) {
			data.add(String.valueOf(cur.getInt(cur.getColumnIndex(Attr.BrchID))));
			data.add(cur.getString(cur.getColumnIndex(Attr.BrchN)));
			data.add(cur.getString(cur.getColumnIndex(Attr.BrchNo)));
			data.add(cur.getString(cur.getColumnIndex(Attr.BrchAd)));
		}
		cur.close();
		db.close();
		return data;
	}

	public void updateStock(List<Stock> items) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Attr.St, null, null);
		ContentValues cv = new ContentValues();
		for (Stock s : items) {
			cv.put(Attr.StID, s.getId());
			cv.put(Attr.StN, s.getName());
			cv.put(Attr.StU, s.getUnit());
			cv.put(Attr.StP, s.getPrice());
			cv.put(Attr.StQ, s.getQty());
			cv.put(Attr.StD, sdf.format(s.getDate()));
			cv.put(Attr.StE, sdf.format(s.getExp()));
			db.insert(Attr.St, null, cv);
		}
		db.close();
	}

	public void updateBatch(List<Batch> items) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Attr.Bat, null, null);
		ContentValues cv = new ContentValues();
		for (Batch s : items) {
			cv.put(Attr.BatID, s.getId());
			cv.put(Attr.BatS, s.getStockId());
			cv.put(Attr.BatQ, s.getQty());
			cv.put(Attr.BatP, s.getPrice());
			cv.put(Attr.BatN, s.getNetPrice());
			db.insert(Attr.Bat, null, cv);
		}
		db.close();
	}
	
	public void updateCust(List<Cust> items) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Attr.Cus, null, null);
		ContentValues cv = new ContentValues();
		for (Cust s : items) {
			cv.put(Attr.CusID, s.getId());
			cv.put(Attr.CusN, s.getName());
			cv.put(Attr.CusNo, s.getNumber());
			db.insert(Attr.Cus, null, cv);
		}
		db.close();
	}

	public void updateReport(List<Report> items) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Attr.Rep, null, null);
		ContentValues cv = new ContentValues();
		for (Report s : items) {
			cv.put(Attr.RepID, s.getId());
			cv.put(Attr.RepBa, s.getBatchId());
			cv.put(Attr.RepP, s.getPrice());
			cv.put(Attr.RepN, s.getNetPrice());
			cv.put(Attr.RepD, sdf.format(s.getDate()));
			db.insert(Attr.Rep, null, cv);
		}
		db.close();
	}
	
	public void insertMSG(MSG msg){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Attr.SMSN, msg.getNo());
		cv.put(Attr.SMST, msg.getMsg());
		cv.put(Attr.SMSDT, msg.getTime());
		db.insert(Attr.SMS, null, cv);
		db.close();
	}

	public List<Stock> getStocks() {
		List<Stock> list = new ArrayList<Stock>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * from " + Attr.St, null);
		try {
			while (cur.moveToNext()) {
				list.add(new Stock(cur.getInt(cur.getColumnIndex(Attr.StID)),
						cur.getString(cur.getColumnIndex(Attr.StN)), cur
								.getString(cur.getColumnIndex(Attr.StU)), cur
								.getString(cur.getColumnIndex(Attr.StP)), cur
								.getString(cur.getColumnIndex(Attr.StQ)), sdf
								.parse(cur.getString(cur
										.getColumnIndex(Attr.StD))), sdf
								.parse(cur.getString(cur
										.getColumnIndex(Attr.StE)))));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("L", e.getMessage());
		} finally {
			cur.close();
			db.close();
		}
		return list;
	}

	public List<Report> getReports() {
		List<Report> list = new ArrayList<Report>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM " + Attr.Rep, null);
		try {
			while (cur.moveToNext()) {
				list.add(new Report(cur.getInt(cur.getColumnIndex(Attr.RepID)),
						cur.getInt(cur.getColumnIndex(Attr.RepBa)), cur
								.getDouble(cur.getColumnIndex(Attr.RepP)), cur
								.getDouble(cur.getColumnIndex(Attr.RepN)), sdf
								.parse(cur.getString(cur
										.getColumnIndex(Attr.RepD)))));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("L", e.getMessage());
		} finally {
			cur.close();
			db.close();
		}
		return list;
	}

	public List<Batch> getBatches() {
		List<Batch> bat = new ArrayList<Batch>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * from " + Attr.Bat, null);
		try {
			while (cur.moveToNext()) {
				bat.add(new Batch(cur.getInt(cur.getColumnIndex(Attr.BatID)),
						cur.getInt(cur.getColumnIndex(Attr.BatS)), cur
								.getInt(cur.getColumnIndex(Attr.BatQ)), cur
								.getDouble(cur.getColumnIndex(Attr.BatP)), cur
								.getDouble(cur.getColumnIndex(Attr.BatN))));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("L", e.getMessage());
		} finally {
			cur.close();
			db.close();
		}
		return bat;
	}
	
	public List<Cust> getCustomers() {
		List<Cust> list = new ArrayList<Cust>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * from " + Attr.Cus, null);
		try {
			while (cur.moveToNext()) {
				list.add(new Cust(cur.getString(cur.getColumnIndex(Attr.CusID)),
						cur.getString(cur.getColumnIndex(Attr.CusN)),
						cur.getString(cur.getColumnIndex(Attr.CusNo))));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("L", e.getMessage());
		} finally {
			cur.close();
			db.close();
		}
		return list;
	}
	
	public List<MSG> getMessages(){
		List<MSG> list=new ArrayList<MSG>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * from " + Attr.SMS, null);
		try {
			while (cur.moveToNext()) {
				list.add(new MSG(cur.getString(cur.getColumnIndex(Attr.SMSN)),
						cur.getString(cur.getColumnIndex(Attr.SMST)),
						cur.getString(cur.getColumnIndex(Attr.SMSDT))));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("L", e.getMessage());
		} finally {
			cur.close();
			db.close();
		}
		return list;
	}
	
	public String[] getSetting(){
		
		SQLiteDatabase db = this.getReadableDatabase();
		try{
			
		}finally{
			db.close();
		}
		return null;
	}
}
