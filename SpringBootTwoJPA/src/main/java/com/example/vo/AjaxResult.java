package com.example.vo;

import java.util.Map;

import lombok.Data;



@Data
public class AjaxResult<T> {
	
	private long status;
	private String statusText;
	private T data;
	private Map<String, Object> datas;
	
	private static int FAIl = -1;
	
	public AjaxResult(T data) {
		if ((data instanceof Exception))    {
		       status = -1L;
		      statusText = ((Exception)data).getLocalizedMessage();
		}    else    {
		       status = 0L;
		       statusText = "Success";
		      this.data = data;
	    }
	}
	
	public AjaxResult(long status, String statusText, T data) {
		super();
		this.status = status;
		this.statusText = statusText;
		this.data = data;
	}

	/*无参构造函数*/
	public AjaxResult() {
		status =0L;
		statusText="success";
	}
	
	public static <T> AjaxResult<T> ok(T data){
		return new AjaxResult<T>(data);
	}

	
	
	public static <T>  AjaxResult<T> error(T data,String message){
		return new AjaxResult<T>(AjaxResult.FAIl,message,data);
	}
	
	public static <T>  AjaxResult<T> error(String message){
		return error(null,message);
	}
	
	
	
	
	

	
	
	
	
}
