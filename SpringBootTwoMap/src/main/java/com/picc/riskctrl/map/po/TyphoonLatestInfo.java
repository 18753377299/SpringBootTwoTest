package com.picc.riskctrl.map.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "WZ_TFBH")
public class TyphoonLatestInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tfbh;
	private String tfm;
	private String tFland;
	private String id;
	private String isCompleted;
	private String tfme;
	/*存储台风强度*/
	private String bedite;
	private String isLand;
	private String isActive;
	private Date tfDate;
	private String isUse;
	
	/*台风历史路径信息*/
	private List<TyphoonHistoryPath> typhoonHistoryPathList;
	/*台风预报路径信息*/
	private List<WzTFYblj> wzTFYbljList;
	
	@Id
	@Column(name = "TFBH",nullable= false)
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	@Column(name = "TFM")
	public String getTfm() {
		return tfm;
	}
	public void setTfm(String tfm) {
		this.tfm = tfm;
	}
	@Column(name = "TFLAND")
	public String gettFland() {
		return tFland;
	}
	public void settFland(String tFland) {
		this.tFland = tFland;
	}
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "ISCOMPLETED")
	public String getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
	@Column(name = "TFME")
	public String getTfme() {
		return tfme;
	}
	public void setTfme(String tfme) {
		this.tfme = tfme;
	}
	@Column(name = "TFDATE")
	public Date getTfDate() {
		return tfDate;
	}
	public void setTfDate(Date tfDate) {
		this.tfDate = tfDate;
	}
	@Column(name = "BEDITE")
	public String getBedite() {
		return bedite;
	}
	public void setBedite(String bedite) {
		this.bedite = bedite;
	}
	@Column(name = "ISLAND")
	public String getIsLand() {
		return isLand;
	}
	public void setIsLand(String isLand) {
		this.isLand = isLand;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Column(name = "ISUSE")
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "typhoonLatestInfo")
	public List<TyphoonHistoryPath> getTyphoonHistoryPathList() {
		return typhoonHistoryPathList;
	}
	public void setTyphoonHistoryPathList(
			List<TyphoonHistoryPath> typhoonHistoryPathList) {
		this.typhoonHistoryPathList = typhoonHistoryPathList;
	}
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "typhoonLatestInfo")
	public List<WzTFYblj> getWzTFYbljList() {
		return wzTFYbljList;
	}
	public void setWzTFYbljList(List<WzTFYblj> wzTFYbljList) {
		this.wzTFYbljList = wzTFYbljList;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TyphoonLatestInfo other = (TyphoonLatestInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
