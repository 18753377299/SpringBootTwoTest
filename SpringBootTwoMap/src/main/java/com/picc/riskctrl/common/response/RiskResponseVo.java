package com.picc.riskctrl.common.response;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
* @author  作者 E-mail: 
* @date 创建时间：2019年7月19日 下午3:57:35
* @version 1.0 
* @parameter 
* @since  返回类型处理
* @return  */
@Data
public class RiskResponseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*状态标志位*/
	private long status;
	/*返回信息*/
	private String statusText;
	/*返回数据*/
	private Object data;
	/*返回的多个数据*/
	private Map<String, Object> datas;
	
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Map<String, Object> getDatas() {
		return datas;
	}
	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
	
	
}
