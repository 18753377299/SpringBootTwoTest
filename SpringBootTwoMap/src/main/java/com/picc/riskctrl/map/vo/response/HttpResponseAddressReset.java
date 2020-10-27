package com.picc.riskctrl.map.vo.response;

public class HttpResponseAddressReset {
	private RowsFromSiwei data;
	private int errcode;
	private String errmsg;

	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public RowsFromSiwei getData() {
		return data;
	}
	public void setData(RowsFromSiwei data) {
		this.data = data;
	}

	
}
