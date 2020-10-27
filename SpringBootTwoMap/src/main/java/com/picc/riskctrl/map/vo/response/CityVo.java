package com.picc.riskctrl.map.vo.response;

import java.io.Serializable;

public class CityVo implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	private String cityAdCode;
	private String cityName;
	public String getCityAdCode() {
		return cityAdCode;
	}
	public void setCityAdCode(String cityAdCode) {
		this.cityAdCode = cityAdCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
