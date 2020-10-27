package com.picc.riskctrl.map.vo.typhoonRequest;

import java.io.Serializable;

public class TyphoonOuterBaseInfo  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private TyphoonBaseInfo data;
	private String code;
	
	public TyphoonBaseInfo getData() {
		return data;
	}
	public void setData(TyphoonBaseInfo data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
