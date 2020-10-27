package com.picc.riskctrl.map.po;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年5月23日 下午1:33:39
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "WZ_CORPOREYJ")
public class WzCorporeYj implements Serializable{

	private static final long serialVersionUID = 1L;
	
//	private WzCorporeYjId id;
	private WzCorporeLsId id;
	
	
	private  BigDecimal pointx_2000;
	private  BigDecimal pointy_2000;
	private  BigDecimal pointx_02;
	private  BigDecimal pointy_02;
	private String cityCode;
	private String addressName;
	private String validStatus;
	
	private BigDecimal centerX;
	private BigDecimal centerY;
	/*市级名称*/
	private String cityName;
	/*省级名称*/
	private String proName;
	/*县级代码*/
	private String  countyCode;
	/*县级名称*/
	private String  countyName; 
	/*市级代码*/
	private String  cityEcode; 
	/*省级代码*/
	private String  provinceCode;
	/**       
	 *联合主键
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "tfbh", column = @Column(name = "TFBH")),
			@AttributeOverride(name = "mid", column = @Column(name = "MID"))})
	public WzCorporeLsId getId() {
		return id;
	}
	public void setId(WzCorporeLsId id) {
		this.id = id;
	}
	@Column(name = "POINTX_2000")
	public BigDecimal getPointx_2000() {
		return pointx_2000;
	}
	public void setPointx_2000(BigDecimal pointx_2000) {
		this.pointx_2000 = pointx_2000;
	}
	@Column(name = "POINTY_2000")
	public BigDecimal getPointy_2000() {
		return pointy_2000;
	}
	public void setPointy_2000(BigDecimal pointy_2000) {
		this.pointy_2000 = pointy_2000;
	}
	@Column(name = "POINTX_02")
	public BigDecimal getPointx_02() {
		return pointx_02;
	}
	public void setPointx_02(BigDecimal pointx_02) {
		this.pointx_02 = pointx_02;
	}
	@Column(name = "POINTY_02")
	public BigDecimal getPointy_02() {
		return pointy_02;
	}
	public void setPointy_02(BigDecimal pointy_02) {
		this.pointy_02 = pointy_02;
	}
	@Column(name = "CITYCODE")
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Column(name = "ADDRESSNAME")
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@Column(name = "CENTERX")
	public BigDecimal getCenterX() {
		return centerX;
	}
	public void setCenterX(BigDecimal centerX) {
		this.centerX = centerX;
	}
	@Column(name = "CENTERY")
	public BigDecimal getCenterY() {
		return centerY;
	}
	public void setCenterY(BigDecimal centerY) {
		this.centerY = centerY;
	}
	
	@Column(name = "CITYNAME")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Column(name = "PRONAME")
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	@Column(name = "countyCode")
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	@Column(name = "countyName")
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	@Column(name = "cityEcode")
	public String getCityEcode() {
		return cityEcode;
	}
	public void setCityEcode(String cityEcode) {
		this.cityEcode = cityEcode;
	}
	@Column(name = "provinceCode")
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
}
