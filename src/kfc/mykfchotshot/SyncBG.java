package kfc.mykfchotshot;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import android.content.Context;
import android.util.Log;
import kfc.mykfchotshot.db.*;

public class SyncBG {
	private DB db;
	private List<Stock> stocks;
	private List<Report> reports;
	private List<Batch> batches;
	private List<Cust> customers; 
	private StringBuffer buf = new StringBuffer();
	private final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	public SyncBG(Context context) {
		db = new DB(context);
	}

	public boolean Sync(String path) {
		try {
			LoadData(path);
			db.updateStock(stocks);
			db.updateBatch(batches);
			db.updateReport(reports);
			db.updateCust(customers);
			return true;
		} catch (Exception e) {
			Log.e("L", e.getMessage());
		}
		return false;
	}

	public void LoadData(String path) throws Exception {
		StockHandler stock = new StockHandler();
		ReportHandler report = new ReportHandler();
		BatchHandler batch = new BatchHandler();
		CustHandler cust=new CustHandler();
		URL url;
		SAXParserFactory sax = SAXParserFactory.newInstance();
		SAXParser sp = sax.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		url = new URL(path + "/stocks.xml");
		xr.setContentHandler(stock);
		xr.parse(new InputSource(url.openStream()));
		stocks = stock.getItems();
		db.updateStock(stocks);
		url = new URL(path + "/reports.xml");
		xr.setContentHandler(report);
		xr.parse(new InputSource(url.openStream()));
		reports = report.getItems();
		url = new URL(path + "/batches.xml");
		xr.setContentHandler(batch);
		xr.parse(new InputSource(url.openStream()));
		batches = batch.getItems();
		url = new URL(path + "/customers.xml");
		xr.setContentHandler(cust);
		xr.parse(new InputSource(url.openStream()));
		customers = cust.getItems();
	}

	public class StockHandler extends DefaultHandler {
		private List<Stock> items;
		@SuppressWarnings("unused")
		private boolean inItem, inExp;
		private int id;
		private String name, unit, price, qty;
		private Date date, exp;

		public List<Stock> getItems() {
			return items;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if (inItem)
				buf.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String l, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			try {
				if (l.equals("item")) {
					inItem = false;
					items.add(new Stock(id, name, unit, price, qty, date, exp));
				} else if (l.equals("id")) {
					if (inItem) {
						id = Integer.parseInt(buf.toString().trim());
						buf.setLength(0);
					}
				} else if (l.equals("name")) {
					if (inItem) {
						name = buf.toString().trim();
						buf.setLength(0);
					}
				} else if (l.equals("unit")) {
					if (inItem) {
						unit = buf.toString().trim();
						buf.setLength(0);
					}
				} else if (l.equals("price")) {
					if (inItem) {
						price = buf.toString().trim();
						buf.setLength(0);
					}
				} else if (l.equals("qty")) {
					if (inItem) {
						qty = buf.toString().trim();
						buf.setLength(0);
					}
				} else if (l.equals("datePub")) {
					if (inItem) {
						date = sdf.parse(buf.toString().trim());
						buf.setLength(0);
					}
				} else if (l.equals("exp")) {
					if (inItem) {
						exp = sdf.parse(buf.toString().trim());
						buf.setLength(0);
					}
				}
			} catch (Exception e) {
				Log.e("L", e.getMessage());
			}
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			items = new ArrayList<Stock>();
		}

		@Override
		public void startElement(String uri, String l, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (l.equals("item")) {
				inItem = true;
			}
		}

	}

	public class ReportHandler extends DefaultHandler {
		private List<Report> items;
		private boolean inItem;
		private int id, batch;
		private double price, net;
		private Date date;

		public List<Report> getItems() {
			return items;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if (inItem)
				buf.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String l, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			try {
				if (l.equals("item")) {
					inItem = false;
					items.add(new Report(id, batch, price, net, date));
				} else if (l.equals("id")) {
					if (inItem) {
						id = Integer.parseInt(buf.toString().trim());
						buf.setLength(0);
					}
				} else if (l.equals("batch")) {
					if (inItem) {
						batch = Integer.parseInt(buf.toString().trim());
						buf.setLength(0);
					}
				} else if (l.equals("price")) {
					if (inItem) {
						price = Double.parseDouble(buf.toString().trim());
						buf.setLength(0);
					}
				} else if (l.equals("net")) {
					if (inItem) {
						net = Double.parseDouble(buf.toString().trim());
						buf.setLength(0);
					}
				} else if (l.equals("date")) {
					if (inItem) {
						date = sdf.parse(buf.toString().trim());
						buf.setLength(0);
					}
				}
			} catch (Exception e) {
				Log.e("L", e.getMessage());
			}
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			items = new ArrayList<Report>();
		}

		@Override
		public void startElement(String uri, String l, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (l.equals("item")) {
				inItem = true;
			}
		}
	}

	public class BatchHandler extends DefaultHandler {
		private List<Batch> items;
		private boolean inItem;
		private int id, stock, qty;
		private double price, net;

		public List<Batch> getItems() {
			return items;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if (inItem)
				buf.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String l, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			if (l.equals("item")) {
				inItem = true;
				items.add(new Batch(id, stock, qty, price, net));
			} else if (l.equals("id")) {
				id = Integer.parseInt(buf.toString().trim());
				buf.setLength(0);
			} else if (l.equals("stock")) {
				stock = Integer.parseInt(buf.toString().trim());
				buf.setLength(0);
			} else if (l.equals("qty")) {
				qty = Integer.parseInt(buf.toString().trim());
				buf.setLength(0);
			} else if (l.equals("price")) {
				price = Double.parseDouble(buf.toString().trim());
				buf.setLength(0);
			} else if (l.equals("net")) {
				net = Double.parseDouble(buf.toString().trim());
				buf.setLength(0);
			}
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			items = new ArrayList<Batch>();
		}

		@Override
		public void startElement(String uri, String l, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (l.equals("item")) {
				inItem = true;
			}
		}

	}
	
	public class CustHandler extends DefaultHandler{
		List<Cust> items;
		boolean inItem;
		String id,name,number;
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if (inItem)
				buf.append(ch, start, length);
		}
		@Override
		public void endElement(String uri, String l, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			if(l.equals("item")){
				inItem=false;
				items.add(new Cust(id,name,number));
			}else if(l.equals("id")){
				id = buf.toString().trim();
				buf.setLength(0);
			}else if(l.equals("name")){
				name = buf.toString().trim();
				buf.setLength(0);
			}else if(l.equals("number")){
				number = buf.toString().trim();
				buf.setLength(0);
			}
		}
		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			items=new ArrayList<Cust>();
		}
		@Override
		public void startElement(String uri, String l, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if(l.equals("item"))
				inItem=true;
		}
		public List<Cust> getItems() {
			return items;
		}
		
		
	}
}
