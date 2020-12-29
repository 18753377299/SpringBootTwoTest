package com.example.pojo;

import java.io.Serializable;

public class RiskInfoDiscussId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**专家编号*/
	private Integer expertNo;
	/**序号*/
	private Integer serialNo;
	
	public Integer getExpertNo() {
		return expertNo;
	}
	public void setExpertNo(Integer expertNo) {
		this.expertNo = expertNo;
	}
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expertNo == null) ? 0 : expertNo.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RiskInfoDiscussId other = (RiskInfoDiscussId) obj;
		if (expertNo == null) {
			if (other.expertNo != null)
				return false;
		} else if (!expertNo.equals(other.expertNo))
			return false;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}
	
}
