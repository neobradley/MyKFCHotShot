package kfc.mykfchotshot.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;
import android.widget.TextView;

public class FiltAlgo {

	public static List<Stock> getDailyStock(List<Report> reports,
			List<Batch> batches, List<Stock> stocks, Date date) {
		List<Batch> batch = new ArrayList<Batch>();
		List<Stock> stock = new ArrayList<Stock>();
		for (Report r : reports)
			if (r.getDate().getYear() == date.getYear()
					&& r.getDate().getMonth() == date.getMonth()
					&& r.getDate().getDate() == date.getDate())
				for (Batch b : batches)
					if (r.getBatchId() == b.getId())
						batch.add(b);

		boolean added = false;
		for (Batch b : batch)
			for (Stock s : stocks)
				if (b.getStockId() == s.getId()) {
					added = false;
					for (Stock s2 : stock)
						if (b.getStockId() == s2.getId()) {
							s2.addTQty(b.getQty());
							s2.addTPrice(b.getPrice());
							s2.addTNet(b.getNetPrice());
							stock.set(stock.lastIndexOf(s2), s2);
							added = true;
						}
					if (added == false) {
						s.setTQty(b.getQty());
						s.setTPrice(b.getPrice());
						s.setTNet(b.getNetPrice());
						stock.add(s);
					}
				}
		return SortAlgo.srtStockSold(stock);
	}

	public static List<Stock> getWeeklyStock(List<Report> reports,
			List<Batch> batches, List<Stock> stocks, Date date, TextView tv) {
		List<Batch> batch = new ArrayList<Batch>();
		List<Stock> stock = new ArrayList<Stock>();
		Date dateS = getWeekDate(date, 0);
		String str = (dateS.getMonth() + 1) + "/" + dateS.getDate() + "/"
				+ (dateS.getYear() + 1900) + "(Sun) ~ ";
		int y=dateS.getYear(),m=dateS.getMonth(),d=dateS.getDate();
		Date dateE = getWeekDate(date, 1);
		str += (dateE.getMonth() + 1) + "/" + dateE.getDate() + "/"
				+ (dateS.getYear() + 1900) + "(Mon)";
		tv.setText(str);
		for (Report r : reports)
			if (r.getDate().getYear() >= y
					&& r.getDate().getMonth() >= m
					&& r.getDate().getDate() >= d
					&& r.getDate().getYear() <= dateE.getYear()
					&& r.getDate().getMonth() <= dateE.getMonth()
					&& r.getDate().getDate() <= dateE.getDate())
				for (Batch b : batches)
					if (r.getBatchId() == b.getId())
						batch.add(b);
		boolean added = false;
		for (Batch b : batch)
			for (Stock s : stocks)
				if (b.getStockId() == s.getId()) {
					added = false;
					for (Stock s2 : stock)
						if (b.getStockId() == s2.getId()) {
							s2.addTQty(b.getQty());
							s2.addTPrice(b.getPrice());
							s2.addTNet(b.getNetPrice());
							stock.set(stock.lastIndexOf(s2), s2);
							added = true;
						}
					if (added == false) {
						s.setTQty(b.getQty());
						s.setTPrice(b.getPrice());
						s.setTNet(b.getNetPrice());
						stock.add(s);
					}
				}
		return SortAlgo.srtStockSold(stock);
	}

	private static String[] months = { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	public static List<Stock> getMonthlyStock(List<Report> reports,
			List<Batch> batches, List<Stock> stocks, Date date, TextView tv) {
		List<Batch> batch = new ArrayList<Batch>();
		List<Stock> stock = new ArrayList<Stock>();
		Date dateS = getMonthDate(date, 0);
		int y=dateS.getYear(),m=dateS.getMonth(),d=dateS.getDate();
		Date dateE = getMonthDate(date, 1);
		tv.setText(months[dateE.getMonth()] + "/" + (dateE.getYear() + 1900));
		for (Report r : reports)
			if (r.getDate().getYear() >= y
					&& r.getDate().getMonth() >= m
					&& r.getDate().getDate() >= d
					&& r.getDate().getYear() <= dateE.getYear()
					&& r.getDate().getMonth() <= dateE.getMonth()
					&& r.getDate().getDate() <= dateE.getDate())
				for (Batch b : batches)
					if (r.getBatchId() == b.getId())
						batch.add(b);

		boolean added = false;
		for (Batch b : batch)
			for (Stock s : stocks)
				if (b.getStockId() == s.getId()) {
					added = false;
					for (Stock s2 : stock)
						if (b.getStockId() == s2.getId()) {
							s2.addTQty(b.getQty());
							s2.addTPrice(b.getPrice());
							s2.addTNet(b.getNetPrice());
							stock.set(stock.lastIndexOf(s2), s2);
							added = true;
						}
					if (added == false) {
						s.setTQty(b.getQty());
						s.setTPrice(b.getPrice());
						s.setTNet(b.getNetPrice());
						stock.add(s);
					}
				}
		return SortAlgo.srtStockSold(stock);
	}

	public static Date getWeekDate(Date date, int mode) {
		// 0=start, 1=end
		switch (mode) {
		case 0:
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int diff = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			date.setDate(date.getDate() - diff);
			break;
		case 1:
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			diff = Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK);
			date.setDate(date.getDate() + diff);
			break;
		}
		Log.i("L", date.toString());
		return date;
	}

	public static Date getMonthDate(Date date, int mode) {
		switch (mode) {
		case 0:
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int diff = calendar.get(Calendar.DAY_OF_MONTH)
					- calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
			date.setDate(date.getDate() - diff);
			break;
		case 1:
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			diff = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
					- calendar.get(Calendar.DAY_OF_MONTH);
			date.setDate(date.getDate() + diff);
			break;
		}
		Log.i("L", date.toString());
		return date;
	}
}
