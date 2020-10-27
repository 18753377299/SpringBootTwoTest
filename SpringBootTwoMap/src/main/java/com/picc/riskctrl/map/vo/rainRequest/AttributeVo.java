package com.picc.riskctrl.map.vo.rainRequest;

import java.io.Serializable;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年9月16日 下午2:15:20
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class AttributeVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String areaDesc;
	private String description;
	private String eventType;
	private String headline;
	private String identifier;
	private String sendTime;
	private String severity;
	private String x;
	private String y;
	/*降雨的attributes字段名称*/
	private String  FID;
	private String  ID;
	private String  GRIDCODE;
	//降雨获取的时间字段
	private String dataTime;
	// 增加处理降雨插入时间
	private String insertTimeForHis;
	// 增加降雨数据是否经过处理标志位
	private String isUse;
	
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getGRIDCODE() {
		return GRIDCODE;
	}
	public void setGRIDCODE(String gRIDCODE) {
		GRIDCODE = gRIDCODE;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public String getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(String insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	
	
}
