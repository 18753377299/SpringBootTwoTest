package com.picc.riskctrl.map.vo.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Province implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/*省级中心点经度X*/
	private String provinceX;
	/*省级中心点纬度Y*/
	private String provinceY;
	/*中心点级别*/
	private String provinceFlag;
	/*省级名称*/
	private String provinceName;
	/*省级对应市级的集合*/
	List<City> cityList=new ArrayList<City>();

	public String getProvinceX() {
		return provinceX;
	}

	public void setProvinceX(String provinceX) {
		this.provinceX = provinceX;
	}

	public String getProvinceY() {
		return provinceY;
	}

	public void setProvinceY(String provinceY) {
		this.provinceY = provinceY;
	}

	public String getProvinceFlag() {
		return provinceFlag;
	}

	public void setProvinceFlag(String provinceFlag) {
		this.provinceFlag = provinceFlag;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
		
}
