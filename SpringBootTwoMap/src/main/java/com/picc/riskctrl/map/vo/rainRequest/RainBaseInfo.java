package com.picc.riskctrl.map.vo.rainRequest;

import java.io.Serializable;
import java.util.List;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年9月30日 上午10:15:28
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class RainBaseInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String displayFieldName;
	private String fieldAliases;
	private List<RainPolygonInfo> features;
	
	public String getDisplayFieldName() {
		return displayFieldName;
	}
	public void setDisplayFieldName(String displayFieldName) {
		this.displayFieldName = displayFieldName;
	}
	public String getFieldAliases() {
		return fieldAliases;
	}
	public void setFieldAliases(String fieldAliases) {
		this.fieldAliases = fieldAliases;
	}
	public List<RainPolygonInfo> getFeatures() {
		return features;
	}
	public void setFeatures(List<RainPolygonInfo> features) {
		this.features = features;
	}
	
}
