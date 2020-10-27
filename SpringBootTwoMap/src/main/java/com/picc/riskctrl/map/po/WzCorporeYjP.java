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
 * @author  作者 wkl: 
 * @date 创建时间：2019年5月23日 下午4:26:09
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "WZ_CORPOREYJ_P")
public class WzCorporeYjP implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private WzCorporeLsPId id;
	private String cityName;
	private BigDecimal totalAmount;
	private BigDecimal JAmount;
	private BigDecimal QAmount;
	private BigDecimal GAmount;
	private BigDecimal centerX;
	private BigDecimal centerY;
	private int corporeSum;
	
	
	/**       
	 *联合主键
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "tfbh", column = @Column(name = "TFBH")),
			@AttributeOverride(name = "cityCode", column = @Column(name = "CITYCODE")),
			@AttributeOverride(name = "procityFlag", column = @Column(name = "PROCITYFLAG"))})
	public WzCorporeLsPId getId() {
		return id;
	}
	public void setId(WzCorporeLsPId id) {
		this.id = id;
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
	@Column(name = "TOTALAMOUNT")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Column(name = "JAMOUNT")
	public BigDecimal getJAmount() {
		return JAmount;
	}
	public void setJAmount(BigDecimal jAmount) {
		JAmount = jAmount;
	}
	@Column(name = "QAMOUNT")
	public BigDecimal getQAmount() {
		return QAmount;
	}
	public void setQAmount(BigDecimal qAmount) {
		QAmount = qAmount;
	}
	@Column(name = "GAMOUNT")
	public BigDecimal getGAmount() {
		return GAmount;
	}
	public void setGAmount(BigDecimal gAmount) {
		GAmount = gAmount;
	}
	@Column(name = "CORPORESUM")
	public int getCorporeSum() {
		return corporeSum;
	}
	public void setCorporeSum(int corporeSum) {
		this.corporeSum = corporeSum;
	}
	
  
}
