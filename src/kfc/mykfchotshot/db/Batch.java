package kfc.mykfchotshot.db;

public class Batch {
	private int _id,stockId,qty;
	private double price,netPrice;
	public Batch(int id,int stockId, int qty, double price, double netPrice) {
		super();
		this._id=id;
		this.stockId = stockId;
		this.qty = qty;
		this.price = price;
		this.netPrice = netPrice;
	}
	
	public int getId() {
		return _id;
	}
	public int getStockId() {
		return stockId;
	}
	public int getQty() {
		return qty;
	}
	public double getPrice() {
		return price;
	}
	public double getNetPrice() {
		return netPrice;
	}
	
}
