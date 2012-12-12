package kfc.mykfchotshot.db;

public class Attr {
	public static final String site="http://192.168.1.109:80/kfc";
	public static final String DBN = "kfchotshotdb";
	public static final int DBV = 1;
	public static final String C="CREATE TABLE ", PK=" integer primary key,",
	ASC = " ASC", DESC= " DESC";
	public static final String Mgr = "Manager",
	Brch = "Branch",
	St = "Stock",
	Inv = "Inventory",
	Bat = "Batch",
	Rep = "Report",
	Cus="Customer",
	SMS="SMS",
	Set="Setting";
	public static final String MgrID="ManagerID",
	MgrN="ManagerName",
	MgrPw="ManagerPassword",
	MgrLog="ManagerLog";
	public static final String BrchID="BranchID",
	BrchN="BranchName",
	BrchNo="BranchNumber",
	BrchAd="BranchAddress",
	BrchM="ManagerID";
	public static final String StID="StockID",
	StN="StockName",
	StU="StockUnit",
	StP="StockPrice",
	StQ="StockQuantity",
	StD="StockDateTime",
	StE="StockExpiration";
//	public static String InvID="InventoryID",
//	InvB="BranchID",
//	InvS="StockID",
//	InvQ="InventoryQuantity",
//	InvD="InventoryDateTime",
//	InvE="InventoryExpiration";
	public static final String BatID="BatchID",
	BatS="StockID",
	BatQ="BatchQuantity",
	BatP="BatchPrice",
	BatN="BatchNetPrice";
	public static final String RepID="ReportID",
	RepBr="BranchID",
	RepBa="BatchID",
	RepP="ReportTotalPrice",
	RepN="ReportTotalNetPrice",
	RepD="ReportDateTime";
	public static final String CusID="CustomerID",
	CusN="CustomerName",
	CusNo="CustomerNumber";
	public static final String SMSDT="SMSDateTime",
	SMSN="SMSTo",
	SMST="SMSText";
	public static final String SetU="SettingURl",
	SetM="SettingMode",
	SetT="SettingTime";
}
