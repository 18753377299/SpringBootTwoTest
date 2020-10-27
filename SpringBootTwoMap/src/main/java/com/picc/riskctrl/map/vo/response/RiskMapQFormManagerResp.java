package com.picc.riskctrl.map.vo.response;

import java.math.BigDecimal;

/**
 * 企财险 responseVo
 */
public class RiskMapQFormManagerResp {
	/*保费*/
	private BigDecimal calendarThisYPremium;
	/*保额*/
	private BigDecimal calendarThisYAmount;
	/*费率*/
	private BigDecimal calendarThisYRate;
	/*保单年当年已报告赔付率*/
	private BigDecimal policyThisYRePayRate;
	/*事故年当年已报告赔付率(整体)*/
	private BigDecimal accidentThisYRePayRate;
	/*事故年当年已报告赔付率(常规)*/
	private BigDecimal accidentThisYRePayRateC;
	/*事故年当年已报告赔付率(除大灾外大赔案)*/
	private BigDecimal accidentThisYRePayRateOP;
	/*事故年当年已报告赔付率(大灾)*/
	private BigDecimal accidentThisYRePayRateP;
}
