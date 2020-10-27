package com.picc.riskctrl.map.vo.response;

import java.util.List;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2017年10月25日 下午2:08:56
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class SearchResult {
	private  String status;
	private  String time;
	private  String count;
	private  List<Poi> list;
	private String  spellcorrect;
	
	public String getSpellcorrect() {
		return spellcorrect;
	}
	public void setSpellcorrect(String spellcorrect) {
		this.spellcorrect = spellcorrect;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<Poi> getList() {
		return list;
	}
	public void setList(List<Poi> list) {
		this.list = list;
	}
	
	
}
