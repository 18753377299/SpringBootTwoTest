package com.picc.riskctrl.map.vo.response;

public class CurrentDistrict {

	private int level;
    private String centerPoint;
    // 市级编码
    private String citycode;
    // 名称
    private String name;
    //省级编码
    private String adcode;
    // 县级编码
    private String  countyCode;
    
    public void setLevel(int level) {
         this.level = level;
     }
     public int getLevel() {
         return level;
     }

    public void setCenterPoint(String centerPoint) {
         this.centerPoint = centerPoint;
     }
     public String getCenterPoint() {
         return centerPoint;
     }

    public void setCitycode(String citycode) {
         this.citycode = citycode;
     }
     public String getCitycode() {
         return citycode;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setAdcode(String adcode) {
         this.adcode = adcode;
     }
     public String getAdcode() {
         return adcode;
     }
     
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
     
}
