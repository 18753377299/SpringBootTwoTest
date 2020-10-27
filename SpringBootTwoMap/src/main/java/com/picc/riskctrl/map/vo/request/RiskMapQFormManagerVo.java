package com.picc.riskctrl.map.vo.request;

import java.math.BigDecimal;

/**
 * @date 创建时间：2019年6月28日 下午16:30:56
 * @author 崔凤志
 *
 */
public class RiskMapQFormManagerVo {
	
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
	
	/*事故年近三年已报告赔付率(整体)*/
	private BigDecimal accidentThreeYRePayRate;
	
	/*事故年近三年已报告赔付率(常规)*/
	private BigDecimal accidentThreeYRePayRateC;
	
	/*事故年近三年已报告赔付率(除大灾外大赔案)*/
	private BigDecimal accidentThreeYRePayRateOP;
	
	/*事故年近三年已报告赔付率(大灾)*/
	private BigDecimal accidentThreeYRePayRateP;
	
	
	
	
	
	
	
	/*日历年近三年签单保费*/
	private BigDecimal calendarThreeYPremium;
	
	/*日历年（近三年）保险金额*/
	private String calendarYAmount;
	
	
	
	
}
