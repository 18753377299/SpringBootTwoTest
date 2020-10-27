package com.picc.riskctrl.map.vo.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wz_Corporels_byMid {
	List<String> countList = new ArrayList<String>();
	String cityName;
	String centerX;
	String centerY;
	String procityFlag;
	String corporeSum = "0";
	BigDecimal amount = BigDecimal.ZERO;
	BigDecimal amountQ = BigDecimal.ZERO; // 01 企财险
	BigDecimal amountG = BigDecimal.ZERO; // 03工程险

	public Wz_Corporels_byMid() {
		super();
	}
	public Wz_Corporels_byMid(List<String> countList, String cityName, String centerX, String centerY, String procityFlag) {
		super();
		this.countList = countList;
		this.cityName = cityName;
		this.centerX = centerX;
		this.centerY = centerY;
		this.procityFlag = procityFlag;
	}
	public List<String> getCountList() {
		return countList;
	}
	public void setCountList(List<String> countList) {
		this.countList = countList;
	}
	public String getCenterX() {
		return centerX;
	}
	public void setCenterX(String centerX) {
		this.centerX = centerX;
	}
	public String getCenterY() {
		return centerY;
	}
	public void setCenterY(String centerY) {
		this.centerY = centerY;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmountQ() {
		return amountQ;
	}
	public void setAmountQ(BigDecimal amountQ) {
		this.amountQ = amountQ;
	}
	public BigDecimal getAmountG() {
		return amountG;
	}
	public void setAmountG(BigDecimal amountG) {
		this.amountG = amountG;
	}
	public String getProcityFlag() {
		return procityFlag;
	}
	public void setProcityFlag(String procityFlag) {
		this.procityFlag = procityFlag;
	}
	public String getCorporeSum() {
		return corporeSum;
	}
	public void setCorporeSum(String corporeSum) {
		this.corporeSum = corporeSum;
	}
	
}
