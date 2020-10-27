package com.picc.riskctrl.map.vo.request;
	/**
 * @author  作者 E-mail: liqiankun
 * @date 创建时间：2017年10月30日 下午6:59:19
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class LongAndLatitude {
	    private String dangerAdderss;
		private String lngX;
		private String latY;
		public String getLngX() {
			return lngX;
		}
		public void setLngX(String lngX) {
			this.lngX = lngX;
		}
		public String getLatY() {
			return latY;
		}
		public void setLatY(String latY) {
			this.latY = latY;
		}
		public String getDangerAdderss() {
			return dangerAdderss;
		}
		public void setDangerAdderss(String dangerAdderss) {
			this.dangerAdderss = dangerAdderss;
		}
		
		
}
