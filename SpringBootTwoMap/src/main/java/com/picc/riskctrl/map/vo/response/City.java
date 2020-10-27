package com.picc.riskctrl.map.vo.response;

public class City implements java.io.Serializable{
		
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/*市级中心点经度X*/
	private String cityX;
	/*市级中心点纬度Y*/
	private String cityY;
	/*中心点级别*/
	private String cityFlag;
	
	private String cityName;
	
	public String getCityX() {
		return cityX;
	}
	public void setCityX(String cityX) {
		this.cityX = cityX;
	}
	public String getCityY() {
		return cityY;
	}
	public void setCityY(String cityY) {
		this.cityY = cityY;
	}
	public String getCityFlag() {
		return cityFlag;
	}
	public void setCityFlag(String cityFlag) {
		this.cityFlag = cityFlag;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((cityX == null) ? 0 : cityX.hashCode());
		result = prime * result + ((cityY == null) ? 0 : cityY.hashCode());
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
		City other = (City) obj;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (cityX == null) {
			if (other.cityX != null)
				return false;
		} else if (!cityX.equals(other.cityX))
			return false;
		if (cityY == null) {
			if (other.cityY != null)
				return false;
		} else if (!cityY.equals(other.cityY))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "City [cityX=" + cityX + ", cityY=" + cityY + ", cityName="
				+ cityName + "]";
	}
		
	
	
	
}
