package kfc.mykfchotshot.db;

import java.util.Date;

public class Stock {
	private int id;
	private String name, unit, price, qty;
	private Date date, exp;
	private double tPrice,tNet,tQty;
	
	public Stock(int id, String name, String unit, String price, String qty, Date date, Date exp) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.price = price;
		this.qty = qty;
		this.date = date;
		this.exp = exp;
		tQty=0;
		tPrice=0;
		tNet=0;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUnit() {
		return unit;
	}
	public String getPrice() {
		return price;
	}
	public String getQty() {
		return qty;
	}
	public Date getDate() {
		return date;
	}
	public Date getExp() {
		return exp;
	}
	public double getTPrice() {
		return tPrice;
	}
	public void addTPrice(double tPrice) {
		this.tPrice += tPrice;
	}
	public double getTNet() {
		return tNet;
	}
	public void addTNet(double tNet) {
		this.tNet += tNet;
	}
	public double getTQty() {
		return tQty;
	}
	public void addTQty(int tQty) {
		this.tQty += tQty;
	}
	public void setTPrice(double tPrice) {
		this.tPrice = tPrice;
	}
	public void setTNet(double tNet) {
		this.tNet = tNet;
	}
	public void setTQty(int tQty) {
		this.tQty = tQty;
	}
	
}
