package com.picc.riskctrl.map.vo.response.dangerManage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @功能:大灾调度平台返回基本信息
 * @author liqiankun
 * @时间：20200326
 * @修改记录：
 */
public class DangerManageMapInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*台风编号*/
	private String tfbh;
	/*纬度*/
	private BigDecimal latitude;
	/*经度*/
	private BigDecimal longitude;
	/*标的编号*/
	private Integer addressId;
	/*标的地址*/
	private String addressName;
	/*区分已影响标的和将要影响标的:0（影响）1（已影响）,2（已影响标的未影响标的全部数据）*/
	private String isHappen;
	/*标的具体名称集合*/
	private List<String> insuredName;
	/*所有险类总保额*/
	private BigDecimal totalAmount;
	/*企财险总保额*/
	private BigDecimal qAmount;
	/*工程险总保额*/
	private BigDecimal gAmount;
	/*市级机构名称*/
	private String cityName;
	/*市级机构编码*/
	private String cityCode;
	/*省级机构名称*/
	private String proName;
	/*省级机构编码*/
	private String proCode;
	
	public String getTfbh() {
		return tfbh;
	}
	public void setTfbh(String tfbh) {
		this.tfbh = tfbh;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getIsHappen() {
		return isHappen;
	}
	public void setIsHappen(String isHappen) {
		this.isHappen = isHappen;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getqAmount() {
		return qAmount;
	}
	public void setqAmount(BigDecimal qAmount) {
		this.qAmount = qAmount;
	}
	public BigDecimal getgAmount() {
		return gAmount;
	}
	public void setgAmount(BigDecimal gAmount) {
		this.gAmount = gAmount;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public List<String> getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(List<String> insuredName) {
		this.insuredName = insuredName;
	}
	
}
