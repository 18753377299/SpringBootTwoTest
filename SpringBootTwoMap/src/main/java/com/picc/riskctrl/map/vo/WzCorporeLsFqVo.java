package com.picc.riskctrl.map.vo;

import java.io.Serializable;
import java.math.BigDecimal;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年5月21日 下午4:43:10
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class WzCorporeLsFqVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String tfbh;
	private Integer mid;
	private String procityFlag;
	private BigDecimal pointx_2000;
	private BigDecimal pointy_2000;
	private BigDecimal pointx_02;
	private BigDecimal pointy_02;
	private String cityCode;
	private String addressName;
	private String validStatus;
	private BigDecimal centerX;
	private BigDecimal centerY;
	
	private String cityName;
	
	
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getProcityFlag() {
		return procityFlag;
	}
	public void setProcityFlag(String procityFlag) {
		this.procityFlag = procityFlag;
	}
	public BigDecimal getPointx_2000() {
		return pointx_2000;
	}
	public void setPointx_2000(BigDecimal pointx_2000) {
		this.pointx_2000 = pointx_2000;
	}
	public BigDecimal getPointy_2000() {
		return pointy_2000;
	}
	public void setPointy_2000(BigDecimal pointy_2000) {
		this.pointy_2000 = pointy_2000;
	}
	public BigDecimal getPointx_02() {
		return pointx_02;
	}
	public void setPointx_02(BigDecimal pointx_02) {
		this.pointx_02 = pointx_02;
	}
	public BigDecimal getPointy_02() {
		return pointy_02;
	}
	public void setPointy_02(BigDecimal pointy_02) {
		this.pointy_02 = pointy_02;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public BigDecimal getCenterX() {
		return centerX;
	}
	public void setCenterX(BigDecimal centerX) {
		this.centerX = centerX;
	}
	public BigDecimal getCenterY() {
		return centerY;
	}
	public void setCenterY(BigDecimal centerY) {
		this.centerY = centerY;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
