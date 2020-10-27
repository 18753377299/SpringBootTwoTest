package com.picc.riskctrl.map.po;

import java.util.Date;

import javax.persistence.Column;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年10月12日 下午7:04:25
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class TyphoonHistoryPathId implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String tfbh;
	private Date rqsj;
	
	@Column(name = "TFBH",nullable= false)
	public String getTfbh() {
		return tfbh;
	}

	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	@Column(name = "RQSJ",nullable= false)
	public Date getRqsj() {
		return rqsj;
	}
	public void setRqsj(Date rqsj) {
		this.rqsj = rqsj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rqsj == null) ? 0 : rqsj.hashCode());
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
		TyphoonHistoryPathId other = (TyphoonHistoryPathId) obj;
		if (rqsj == null) {
			if (other.rqsj != null)
				return false;
		} else if (!rqsj.equals(other.rqsj))
			return false;
		if (tfbh == null) {
			if (other.tfbh != null)
				return false;
		} else if (!tfbh.equals(other.tfbh))
			return false;
		return true;
	}
	
}
