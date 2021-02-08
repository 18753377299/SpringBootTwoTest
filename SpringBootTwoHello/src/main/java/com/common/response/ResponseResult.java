package com.common.response;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseResult {
	
	// 返回结果信息
	private Object result;
	// 状态标志位
	private String  statusCode;
	// 返回的信息
	private String message;
	// 存储键值对信息
	private Map<String,Object> parameterMap;
	// 返回分页总条数
	private long totalCount;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
