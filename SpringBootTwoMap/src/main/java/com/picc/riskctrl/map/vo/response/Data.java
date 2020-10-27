package com.picc.riskctrl.map.vo.response;

import java.util.List;

public class Data {

	private int pageCount;
	private int page_num;
	private int total;
	private List<Pois> pois;
	private List<Rows> rows;
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPage_num() {
		return page_num;
	}
	public void setPage_num(int page_num) {
		this.page_num = page_num;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Pois> getPois() {
		return pois;
	}
	public void setPois(List<Pois> pois) {
		this.pois = pois;
	}
	public List<Rows> getRows() {
		return rows;
	}
	public void setRows(List<Rows> rows) {
		this.rows = rows;
	}

	
	
	

}
