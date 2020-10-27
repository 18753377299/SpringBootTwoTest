package com.picc.riskctrl.map.po;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年5月20日 下午4:35:49
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
@Embeddable
public class WzCorporeLsPId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String tfbh;
	private String cityCode;
	private String procityFlag;
	
	@Column(name = "TFBH")
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	@Column(name = "CITYCODE")
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Column(name = "PROCITYFLAG")
	public String getProcityFlag() {
		return procityFlag;
	}
	public void setProcityFlag(String procityFlag) {
		this.procityFlag = procityFlag;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityCode == null) ? 0 : cityCode.hashCode());
		result = prime * result
				+ ((procityFlag == null) ? 0 : procityFlag.hashCode());
		result = prime * result + ((tfbh == null) ? 0 : tfbh.hashCode());
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
		WzCorporeLsPId other = (WzCorporeLsPId) obj;
		if (cityCode == null) {
			if (other.cityCode != null)
				return false;
		} else if (!cityCode.equals(other.cityCode))
			return false;
		if (procityFlag == null) {
			if (other.procityFlag != null)
				return false;
		} else if (!procityFlag.equals(other.procityFlag))
			return false;
		if (tfbh == null) {
			if (other.tfbh != null)
				return false;
		} else if (!tfbh.equals(other.tfbh))
			return false;
		return true;
	}
	
}
