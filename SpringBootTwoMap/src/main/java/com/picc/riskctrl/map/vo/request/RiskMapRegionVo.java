package com.picc.riskctrl.map.vo.request;

import java.io.Serializable;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年7月9日 下午3:40:54
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class RiskMapRegionVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String provinceName;
	private String proAdCode;
	private String cityName;
	private String cityAdCode;
	private String countryName;
	private String adminCode;
	private String addressCounty;
	/**地址名称*/
	private String addressName;
	/**险类代码*/
	private String classCode;
	/**行业名称*/
	private String businessName;
	
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityAdCode() {
		return cityAdCode;
	}
	public void setCityAdCode(String cityAdCode) {
		this.cityAdCode = cityAdCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getAdminCode() {
		return adminCode;
	}
	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}
	public String getAddressCounty() {
		return addressCounty;
	}
	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	
}
