package com.picc.riskctrl.map.vo.response;

import java.io.Serializable;

public class ProvinceVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/*省级名称*/
	private String provinceName;
	private String proAdCode;
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProAdCode() {
		return proAdCode;
	}
	public void setProAdCode(String proAdCode) {
		this.proAdCode = proAdCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
