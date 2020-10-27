package com.picc.riskctrl.map.vo.request;

import java.util.Date;

public class WZ_tfbhVo {
	private WZ_tfbh wz_tfbh;
	private Integer pageNumber;
	private Integer pageSize;
	private Date tfStartDate;
	private Date tfEndDate;
	private Integer total;
	
	public WZ_tfbh getWz_tfbh() {
		return wz_tfbh;
	}
	public void setWz_tfbh(WZ_tfbh wz_tfbh) {
		this.wz_tfbh = wz_tfbh;
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
	public Date getTfStartDate() {
		return tfStartDate;
	}
	public void setTfStartDate(Date tfStartDate) {
		this.tfStartDate = tfStartDate;
	}
	public Date getTfEndDate() {
		return tfEndDate;
	}
	public void setTfEndDate(Date tfEndDate) {
		this.tfEndDate = tfEndDate;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
