package com.picc.riskctrl.map.po;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年6月1日 下午3:26:22
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */

@Embeddable
public class WzTFYbljId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*台风编号*/
	private String tfbh;
	/*国家*/
	private String  tm;
	/*预报时间*/
	private Timestamp ybsj;
	/*发生时间*/
	private Timestamp rqsj;
	
//	/*预报时间*/
//	private Date ybsj;
//	/*发生时间*/
//	private Date rqsj;
	
	
	@Column(name = "TFBH",nullable=false)
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	@Column(name = "TM",nullable=false)
	public String getTm() {
		return tm;
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
	@Column(name = "YBSJ",nullable=false)
	public Timestamp getYbsj() {
		return ybsj;
	}
	public void setYbsj(Timestamp ybsj) {
		this.ybsj = ybsj;
	}
	@Column(name = "RQSJ",nullable=false)
	public Timestamp getRqsj() {
		return rqsj;
	}
	
	public void setRqsj(Timestamp rqsj) {
		this.rqsj = rqsj;
	}
	
//	@Column(name = "YBSJ",nullable=false)
//	public Date getYbsj() {
//		return ybsj;
//	}
//	public void setYbsj(Date ybsj) {
//		this.ybsj = ybsj;
//	}
//	@Column(name = "RQSJ",nullable=false)
//	public Date getRqsj() {
//		return rqsj;
//	}
//	public void setRqsj(Date rqsj) {
//		this.rqsj = rqsj;
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rqsj == null) ? 0 : rqsj.hashCode());
		result = prime * result + ((tfbh == null) ? 0 : tfbh.hashCode());
		result = prime * result + ((tm == null) ? 0 : tm.hashCode());
		result = prime * result + ((ybsj == null) ? 0 : ybsj.hashCode());
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
		WzTFYbljId other = (WzTFYbljId) obj;
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
		if (tm == null) {
			if (other.tm != null)
				return false;
		} else if (!tm.equals(other.tm))
			return false;
		if (ybsj == null) {
			if (other.ybsj != null)
				return false;
		} else if (!ybsj.equals(other.ybsj))
			return false;
		return true;	
	}
	
}

