package kfc.mykfchotshot.db;

import java.util.Date;

public class Report {
	private int id,batchId;
	private double price,netPrice;
	private Date date;
	public Report(int id, int batchId, double price, double netPrice, Date date) {
		super();
		this.id = id;
		this.batchId = batchId;
		this.price = price;
		this.netPrice = netPrice;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public int getBatchId() {
		return batchId;
	}
	public double getPrice() {
		return price;
	}
	public double getNetPrice() {
		return netPrice;
	}
	public Date getDate() {
		return date;
	}
	
}
