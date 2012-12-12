package kfc.mykfchotshot.db;

public class MSG {
	private String no,msg,time;
	 

	public MSG(String no, String msg, String time) {
		super();
		this.no = no;
		this.msg = msg;
		this.time = time;
	}

	public MSG() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
