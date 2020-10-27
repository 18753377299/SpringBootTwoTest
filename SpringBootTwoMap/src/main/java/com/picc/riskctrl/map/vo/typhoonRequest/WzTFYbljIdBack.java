package com.picc.riskctrl.map.vo.typhoonRequest;


import java.io.Serializable;
	/**
 * @author  作者 E-mail: 用于list去重，日期格式在list中无法去重
 * @date 创建时间：2019年6月1日 下午3:26:22
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */

public class WzTFYbljIdBack implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*台风编号*/
	private String tfbh;
	/*国家*/
	private String  tm;
	/*预报时间*/
	private String ybsj;
	/*发生时间*/
	private String rqsj;
	
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	public String getTm() {
		return tm;
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
	public String getYbsj() {
		return ybsj;
	}
	public void setYbsj(String ybsj) {
		this.ybsj = ybsj;
	}
	public String getRqsj() {
		return rqsj;
	}
	public void setRqsj(String rqsj) {
		this.rqsj = rqsj;
	}
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
		WzTFYbljIdBack other = (WzTFYbljIdBack) obj;
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

