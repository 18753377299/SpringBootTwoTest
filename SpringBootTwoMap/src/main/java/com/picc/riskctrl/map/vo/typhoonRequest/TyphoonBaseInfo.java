package com.picc.riskctrl.map.vo.typhoonRequest;

import java.io.Serializable;
import java.util.List;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年9月3日 下午1:50:05
 * @version 1.0 
 * @parameter 
 * @since  与气象局对接获取台风数据
 * @return  */
public class TyphoonBaseInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String returnCode;
	private String returnMessage;
	private String rowCount;
	private String colCount;
	private String requestParams;
	private String requestTime;
	private String responseTime;
	private String takeTime;
	private String fieldNames;
	private String fieldUnits;
	
	private List<TyphoonPath> ds;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getColCount() {
		return colCount;
	}

	public void setColCount(String colCount) {
		this.colCount = colCount;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String getFieldUnits() {
		return fieldUnits;
	}

	public void setFieldUnits(String fieldUnits) {
		this.fieldUnits = fieldUnits;
	}

	public List<TyphoonPath> getDs() {
		return ds;
	}

	public void setDs(List<TyphoonPath> ds) {
		this.ds = ds;
	}
	
}