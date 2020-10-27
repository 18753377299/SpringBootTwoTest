package com.picc.riskctrl.map.vo.response;

public class HttpResponseAddress {
	private GEO geo;
	private CurrentDistrict currentDistrict;

	private int total;
	private int statusCode;
	private String statusInfo;
	private String dataType;
	public GEO getGeo() {
		return geo;
	}

	public void setGeo(GEO geo) {
		this.geo = geo;
	}



	public CurrentDistrict getCurrentDistrict() {
		return currentDistrict;
	}

	public void setCurrentDistrict(CurrentDistrict currentDistrict) {
		this.currentDistrict = currentDistrict;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
