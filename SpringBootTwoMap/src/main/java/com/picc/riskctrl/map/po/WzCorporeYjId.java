package com.picc.riskctrl.map.po;

import java.io.Serializable;

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
public class WzCorporeYjId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String tfbh;
	private Integer mid;
	
	@Column(name = "TFBH")
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	@Column(name = "MID")
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
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
		WzCorporeYjId other = (WzCorporeYjId) obj;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		if (tfbh == null) {
			if (other.tfbh != null)
				return false;
		} else if (!tfbh.equals(other.tfbh))
			return false;
		return true;
	}
	
	
	
}
