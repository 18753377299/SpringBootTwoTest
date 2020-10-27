package com.picc.riskctrl.map.vo.response;

import java.math.BigDecimal;
import java.util.Date;

public class MapMainByAddrVo {
	/** 地址编号* */
	private Integer addressID;
	/** 投保单号 */
	private String proposalNo;
	/** 保单号 */
	private String policyNo;
	/** 险种 */
	private String riskCode;
	/** 险类*/
	private String classCode;
	/** 起保日期 */
	private String startDate;
	/** 终保日期 */
	private String endDate;
	/** 总保额 */
	private BigDecimal sumAmount;
	/** 联共保比例 */
	private BigDecimal coinsRate;
	private String coinsFlag;
	/**联共保标识*/
	private String coinsFlagCName;
	/**我司保额*/ 
	private BigDecimal ourCoverage;
	/** 归属机构 */
	private String comCode;
	/**	投保人标志位*/
	private int InsuredFlag;
	/**	投保人标志位*/
	private String InsuredName;
	/**证件类型*/
	private String identifyType;
	/**证件号码*/
	private String identifyNumber;
	/**被保险人类型*/
	private String InsuredType;
	/** 是否有效 */
	private String IsEffective;
	
	public Integer getAddressID() {
		return addressID;
	}
	public void setAddressID(Integer addressID) {
		this.addressID = addressID;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}
	public BigDecimal getCoinsRate() {
		return coinsRate;
	}
	public void setCoinsRate(BigDecimal coinsRate) {
		this.coinsRate = coinsRate;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public int getInsuredFlag() {
		return InsuredFlag;
	}
	public void setInsuredFlag(int insuredFlag) {
		InsuredFlag = insuredFlag;
	}
	public String getInsuredName() {
		return InsuredName;
	}
	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}
	public String getIsEffective() {
		return IsEffective;
	}
	public void setIsEffective(String isEffective) {
		IsEffective = isEffective;
	}
	public BigDecimal getOurCoverage() {
		return ourCoverage;
	}
	public void setOurCoverage(BigDecimal ourCoverage) {
		this.ourCoverage = ourCoverage;
	}
	public String getCoinsFlagCName() {
		return coinsFlagCName;
	}
	public void setCoinsFlagCName(String coinsFlagCName) {
		this.coinsFlagCName = coinsFlagCName;
	}
	public String getInsuredType() {
		return InsuredType;
	}
	public void setInsuredType(String insuredType) {
		InsuredType = insuredType;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	public String getCoinsFlag() {
		return coinsFlag;
	}
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}
}
