package com.picc.riskctrl.map.vo.response;

import java.util.List;

import com.picc.riskctrl.map.vo.request.WZ_tfbh;

public class WZ_tfbhResponse {
	private  List<WZ_tfbh> resultList;
	private Integer total;
	private Integer pageNumber;
	private Integer pageSize;
	public List<WZ_tfbh> getResultList() {
		return resultList;
	}
	public void setResultList(List<WZ_tfbh> resultList) {
		this.resultList = resultList;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
