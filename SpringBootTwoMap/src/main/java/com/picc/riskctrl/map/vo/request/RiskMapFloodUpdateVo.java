package com.picc.riskctrl.map.vo.request;

import java.math.BigDecimal;
import java.sql.Date;
/**
* @author  王坤龙
* @date 创建时间：2018年10月22日
* @version 1.0 
* @parameter 
* @since  
* @return  
* */
public class RiskMapFloodUpdateVo {
	/** 地址id */
	private String dangerId;
	/** 经度2000 */
	private BigDecimal before_pointX_2000;
	/** 纬度2000 */
	private BigDecimal before_pointY_2000;
	/** 经度 02*/
	private BigDecimal before_pointX_02;
	/** 纬度02 */
	private BigDecimal before_pointY_02;
	/** 经度2000 */
	private BigDecimal after_pointX_2000;
	/** 纬度2000 */
	private BigDecimal after_pointY_2000;
	/** 经度 02*/
	private BigDecimal after_pointX_02;
	/** 纬度02 */
	private BigDecimal after_pointY_02;
	/** comCode */
	private String  comCode;
	/** 修改人代码*/
	private String operatorCode;
	/** 插入地址*/
	private String floodAddress;
	/** 插入等级*/
	private Integer floodGrade;
	/** 插入深度*/
	private BigDecimal floodDepth;
	/** 插入水淹日期*/
	private Date floodDate;
	/** 最后修改日期 */
	private Date operatorDate;
	/** 插入时间 */
	private Date insertTimeForHis;
	/** 更新时间 */
	private Date operateTimeForHis;
	public BigDecimal getBefore_pointX_2000() {
		return before_pointX_2000;
	}
	public void setBefore_pointX_2000(BigDecimal before_pointX_2000) {
		this.before_pointX_2000 = before_pointX_2000;
	}
	public BigDecimal getBefore_pointY_2000() {
		return before_pointY_2000;
	}
	public void setBefore_pointY_2000(BigDecimal before_pointY_2000) {
		this.before_pointY_2000 = before_pointY_2000;
	}
	public BigDecimal getBefore_pointX_02() {
		return before_pointX_02;
	}
	public void setBefore_pointX_02(BigDecimal before_pointX_02) {
		this.before_pointX_02 = before_pointX_02;
	}
	public BigDecimal getBefore_pointY_02() {
		return before_pointY_02;
	}
	public void setBefore_pointY_02(BigDecimal before_pointY_02) {
		this.before_pointY_02 = before_pointY_02;
	}
	public BigDecimal getAfter_pointX_2000() {
		return after_pointX_2000;
	}
	public void setAfter_pointX_2000(BigDecimal after_pointX_2000) {
		this.after_pointX_2000 = after_pointX_2000;
	}
	public BigDecimal getAfter_pointY_2000() {
		return after_pointY_2000;
	}
	public void setAfter_pointY_2000(BigDecimal after_pointY_2000) {
		this.after_pointY_2000 = after_pointY_2000;
	}
	public BigDecimal getAfter_pointX_02() {
		return after_pointX_02;
	}
	public void setAfter_pointX_02(BigDecimal after_pointX_02) {
		this.after_pointX_02 = after_pointX_02;
	}
	public BigDecimal getAfter_pointY_02() {
		return after_pointY_02;
	}
	public void setAfter_pointY_02(BigDecimal after_pointY_02) {
		this.after_pointY_02 = after_pointY_02;
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
	public Date getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	public String getFloodAddress() {
		return floodAddress;
	}
	public void setFloodAddress(String floodAddress) {
		this.floodAddress = floodAddress;
	}
	public BigDecimal getFloodDepth() {
		return floodDepth;
	}
	public void setFloodDepth(BigDecimal floodDepth) {
		this.floodDepth = floodDepth;
	}
	public Date getFloodDate() {
		return floodDate;
	}
	public void setFloodDate(Date floodDate) {
		this.floodDate = floodDate;
	}
	public Integer getFloodGrade() {
		return floodGrade;
	}
	public void setFloodGrade(Integer floodGrade) {
		this.floodGrade = floodGrade;
	}
	public String getDangerId() {
		return dangerId;
	}
	public void setDangerId(String dangerId) {
		this.dangerId = dangerId;
	}
}
