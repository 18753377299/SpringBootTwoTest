package com.picc.riskctrl.map.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author  作者 E-mail: liqiankun
 * @date 创建时间：2019年5月21日 下午1:50:58
 * @version 1.0 
 * @parameter 
 * @since  为了创建点数据集而创建的vo类
 * @return  */
public class RiskMapAddressField implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal POINTX_2000;
	private BigDecimal POINTY_2000;
	private BigDecimal POINTX_02;
	private BigDecimal POINTY_02;
	private String CITYCODE;
	private String ADDRESSNAME;
	private BigDecimal SCORE;
	private String VALIDSTATUS;
	private String MANUALFLAG;
	private String OPERATORCODE;
	private Date OPERATORDATE;
	private BigDecimal RAINSCALE;
	private BigDecimal THUNDERSTORMSCALE;
	private BigDecimal SNOWSTORMSCALE;
	private BigDecimal HAILSCALE;
	private BigDecimal FLOODSCALE;
	private BigDecimal TYPHOONSCALE;
	private BigDecimal LANDSLIDESCALE;
	private BigDecimal EQPGA;
	
	private Integer ADDRESSID;
	/*县级代码*/
	private String  COUNTYCODE;
	/*县级名称*/
	private String  COUNTYNAME; 
	/*市级代码*/
	private String  CITYECODE; 
	/*市级名称*/
	private String  CITYENAME; 
	/*省级代码*/
	private String  PROVINCECODE;
	/*省级名称*/
	private String  PROVINCENAME;
	
	public BigDecimal getPOINTX_2000() {
		return POINTX_2000;
	}
	public void setPOINTX_2000(BigDecimal pOINTX_2000) {
		POINTX_2000 = pOINTX_2000;
	}
	public BigDecimal getPOINTY_2000() {
		return POINTY_2000;
	}
	public void setPOINTY_2000(BigDecimal pOINTY_2000) {
		POINTY_2000 = pOINTY_2000;
	}
	public BigDecimal getPOINTX_02() {
		return POINTX_02;
	}
	public void setPOINTX_02(BigDecimal pOINTX_02) {
		POINTX_02 = pOINTX_02;
	}
	public BigDecimal getPOINTY_02() {
		return POINTY_02;
	}
	public void setPOINTY_02(BigDecimal pOINTY_02) {
		POINTY_02 = pOINTY_02;
	}
	public String getCITYCODE() {
		return CITYCODE;
	}
	public void setCITYCODE(String cITYCODE) {
		CITYCODE = cITYCODE;
	}
	public String getADDRESSNAME() {
		return ADDRESSNAME;
	}
	public void setADDRESSNAME(String aDDRESSNAME) {
		ADDRESSNAME = aDDRESSNAME;
	}
	public BigDecimal getSCORE() {
		return SCORE;
	}
	public void setSCORE(BigDecimal sCORE) {
		SCORE = sCORE;
	}
	public String getVALIDSTATUS() {
		return VALIDSTATUS;
	}
	public void setVALIDSTATUS(String vALIDSTATUS) {
		VALIDSTATUS = vALIDSTATUS;
	}
	public String getMANUALFLAG() {
		return MANUALFLAG;
	}
	public void setMANUALFLAG(String mANUALFLAG) {
		MANUALFLAG = mANUALFLAG;
	}
	public String getOPERATORCODE() {
		return OPERATORCODE;
	}
	public void setOPERATORCODE(String oPERATORCODE) {
		OPERATORCODE = oPERATORCODE;
	}
	public Date getOPERATORDATE() {
		return OPERATORDATE;
	}
	public void setOPERATORDATE(Date oPERATORDATE) {
		OPERATORDATE = oPERATORDATE;
	}
	public BigDecimal getRAINSCALE() {
		return RAINSCALE;
	}
	public void setRAINSCALE(BigDecimal rAINSCALE) {
		RAINSCALE = rAINSCALE;
	}
	public BigDecimal getTHUNDERSTORMSCALE() {
		return THUNDERSTORMSCALE;
	}
	public void setTHUNDERSTORMSCALE(BigDecimal tHUNDERSTORMSCALE) {
		THUNDERSTORMSCALE = tHUNDERSTORMSCALE;
	}
	public BigDecimal getSNOWSTORMSCALE() {
		return SNOWSTORMSCALE;
	}
	public void setSNOWSTORMSCALE(BigDecimal sNOWSTORMSCALE) {
		SNOWSTORMSCALE = sNOWSTORMSCALE;
	}
	public BigDecimal getHAILSCALE() {
		return HAILSCALE;
	}
	public void setHAILSCALE(BigDecimal hAILSCALE) {
		HAILSCALE = hAILSCALE;
	}
	public BigDecimal getFLOODSCALE() {
		return FLOODSCALE;
	}
	public void setFLOODSCALE(BigDecimal fLOODSCALE) {
		FLOODSCALE = fLOODSCALE;
	}
	public BigDecimal getTYPHOONSCALE() {
		return TYPHOONSCALE;
	}
	public void setTYPHOONSCALE(BigDecimal tYPHOONSCALE) {
		TYPHOONSCALE = tYPHOONSCALE;
	}
	public BigDecimal getLANDSLIDESCALE() {
		return LANDSLIDESCALE;
	}
	public void setLANDSLIDESCALE(BigDecimal lANDSLIDESCALE) {
		LANDSLIDESCALE = lANDSLIDESCALE;
	}
	public BigDecimal getEQPGA() {
		return EQPGA;
	}
	public void setEQPGA(BigDecimal eQPGA) {
		EQPGA = eQPGA;
	}
	public Integer getADDRESSID() {
		return ADDRESSID;
	}
	public void setADDRESSID(Integer aDDRESSID) {
		ADDRESSID = aDDRESSID;
	}
	public String getCOUNTYCODE() {
		return COUNTYCODE;
	}
	public void setCOUNTYCODE(String cOUNTYCODE) {
		COUNTYCODE = cOUNTYCODE;
	}
	public String getCOUNTYNAME() {
		return COUNTYNAME;
	}
	public void setCOUNTYNAME(String cOUNTYNAME) {
		COUNTYNAME = cOUNTYNAME;
	}
	public String getCITYECODE() {
		return CITYECODE;
	}
	public void setCITYECODE(String cITYECODE) {
		CITYECODE = cITYECODE;
	}
	public String getCITYENAME() {
		return CITYENAME;
	}
	public void setCITYENAME(String cITYENAME) {
		CITYENAME = cITYENAME;
	}
	public String getPROVINCECODE() {
		return PROVINCECODE;
	}
	public void setPROVINCECODE(String pROVINCECODE) {
		PROVINCECODE = pROVINCECODE;
	}
	public String getPROVINCENAME() {
		return PROVINCENAME;
	}
	public void setPROVINCENAME(String pROVINCENAME) {
		PROVINCENAME = pROVINCENAME;
	}
	
}
