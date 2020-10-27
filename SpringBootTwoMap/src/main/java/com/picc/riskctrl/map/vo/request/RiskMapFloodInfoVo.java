package com.picc.riskctrl.map.vo.request;

import java.math.BigDecimal;
import java.util.Date;
/**
* @author  王坤龙
* @date 创建时间：2018年10月12日
* @version 1.0 
* @parameter 
* @since  
* @return  
* */
public class RiskMapFloodInfoVo {
	
	/** 水淹等级* */
	private Integer floodGrade;
	/** 水淹深度*/
	private BigDecimal floodDepth;
	/** 机构代码 */
	private String comCode;
	/** 操作人员*/
	private String operatorCode;
	/** 水淹时间 */
	private Date floodDate;
	/** 更新时间 */
	private String  locationFlag;
	private BigDecimal pointx_02;
	private BigDecimal pointy_02;
	private BigDecimal pointx_2000;
	private BigDecimal pointy_2000;
	private String floodAddress;
	/** 地图编号* */
	private String dangerId;
	private Integer totalCount;
	private Integer minFloodGrade;
	private BigDecimal maxFloodDepth;
	public Integer getMinFloodGrade() {
		return minFloodGrade;
	}
	public void setMinFloodGrade(Integer minFloodGrade) {
		this.minFloodGrade = minFloodGrade;
	}
	public BigDecimal getMaxFloodDepth() {
		return maxFloodDepth;
	}
	public void setMaxFloodDepth(BigDecimal maxFloodDepth) {
		this.maxFloodDepth = maxFloodDepth;
	}
	public Integer getFloodGrade() {
		return floodGrade;
	}
	public void setFloodGrade(Integer floodGrade) {
		this.floodGrade = floodGrade;
	}
	public BigDecimal getFloodDepth() {
		return floodDepth;
	}
	public void setFloodDepth(BigDecimal floodDepth) {
		this.floodDepth = floodDepth;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public Date getFloodDate() {
		return floodDate;
	}
	public void setFloodDate(Date floodDate) {
		this.floodDate = floodDate;
	}
	public String getLocationFlag() {
		return locationFlag;
	}
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
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
	public String getFloodAddress() {
		return floodAddress;
	}
	public void setFloodAddress(String floodAddress) {
		this.floodAddress = floodAddress;
	}
	public String getDangerId() {
		return dangerId;
	}
	public void setDangerId(String dangerId) {
		this.dangerId = dangerId;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
