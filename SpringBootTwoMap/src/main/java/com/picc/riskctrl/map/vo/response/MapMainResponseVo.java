package com.picc.riskctrl.map.vo.response;

import java.math.BigDecimal;

public class MapMainResponseVo {
	/** 地址Id */
	private int addressId;
	/** 投保单号 */
	private String proposalNo;
	/** 保单号 */
	private String policyNo;
	/** 险类 */
	private String classCode;
	/** 险种 */
	private String riskCode;
	/** 起保日期 */
	private String startDate;
	/** 终保日期 */
	private String endDate;
	/** 总保额 */
	private BigDecimal sumAmount;
	/** 联共保标志 */
	private String coinsFlag;
	/** 联共保比例 */
	private BigDecimal coinsRate;
	/** 归属机构 */
	private String comCode;
	/** 已决实赔金额 SUMREALPAY*/
	private BigDecimal sumRealPay;
	/** 已决实赔金额 SUMUNSOLVEDPAY*/
	private BigDecimal sumUnsolvedPay;
	/** 分出保额 SUMREAMOUNTCHG*/
	private BigDecimal sumReamountChg;
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
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
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
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
	public String getCoinsFlag() {
		return coinsFlag;
	}
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
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
	public BigDecimal getSumRealPay() {
		return sumRealPay;
	}
	public void setSumRealPay(BigDecimal sumRealPay) {
		this.sumRealPay = sumRealPay;
	}
	public BigDecimal getSumUnsolvedPay() {
		return sumUnsolvedPay;
	}
	public void setSumUnsolvedPay(BigDecimal sumUnsolvedPay) {
		this.sumUnsolvedPay = sumUnsolvedPay;
	}
	public BigDecimal getSumReamountChg() {
		return sumReamountChg;
	}
	public void setSumReamountChg(BigDecimal sumReamountChg) {
		this.sumReamountChg = sumReamountChg;
	}
}
