package com.picc.riskctrl.map.vo.request;

import java.math.BigDecimal;
import java.sql.Date;

public class RiskMapTyphoonBlackAlreadyVo {
	/*保单号*/
	private String policyNo;
	/*被保险人*/
	private String insuredName;
	/*分公司*/
	private String branch;
	/*险种*/
	private String riskCode;
	/*保险地址*/
	private String insuranceAddress;
	/*保险金额*/
	private String insuredAmount;
	/*保费金额*/
	private String premiumsAmount;
	/*起保日期*/
	private Date startDate;
	/*终保日期*/
	private Date endDate;
	/*分保自留比例*/
	private Double Percentage;
	/*联共保我方份额*/
	private Double guarantee;
	/*立案号*/
	private String caseNo;
	/*出险日期*/
	private Date dangerDate;
	private String closeFlag;
	private Date closeDate;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getInsuranceAddress() {
		return insuranceAddress;
	}
	public void setInsuranceAddress(String insuranceAddress) {
		this.insuranceAddress = insuranceAddress;
	}
	public String getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public String getPremiumsAmount() {
		return premiumsAmount;
	}
	public void setPremiumsAmount(String premiumsAmount) {
		this.premiumsAmount = premiumsAmount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getPercentage() {
		return Percentage;
	}
	public void setPercentage(Double percentage) {
		Percentage = percentage;
	}
	public Double getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(Double guarantee) {
		this.guarantee = guarantee;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public Date getDangerDate() {
		return dangerDate;
	}
	public void setDangerDate(Date dangerDate) {
		this.dangerDate = dangerDate;
	}
	public String getCloseFlag() {
		return closeFlag;
	}
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public String getDangerBecause() {
		return dangerBecause;
	}
	public void setDangerBecause(String dangerBecause) {
		this.dangerBecause = dangerBecause;
	}
	public String getDangerCode() {
		return dangerCode;
	}
	public void setDangerCode(String dangerCode) {
		this.dangerCode = dangerCode;
	}
	public Double getEstimateAmount() {
		return estimateAmount;
	}
	public void setEstimateAmount(Double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	public String getDangerAddress() {
		return dangerAddress;
	}
	public void setDangerAddress(String dangerAddress) {
		this.dangerAddress = dangerAddress;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
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
	public String getRiskModel() {
		return riskModel;
	}
	public void setRiskmodel(String riskModel) {
		this.riskModel = riskModel;
	}
	/*出险原因*/
	private String dangerBecause;
	/*巨灾代码*/
	private String dangerCode;
	/*估计赔款*/
	private Double estimateAmount;
	/*出险地址*/
	private String dangerAddress;
	private BigDecimal score;
	private BigDecimal pointx_02;
	private BigDecimal pointy_02;
	private String riskModel;
}
