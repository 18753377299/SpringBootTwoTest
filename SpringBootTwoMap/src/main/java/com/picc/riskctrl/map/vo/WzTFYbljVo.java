package com.picc.riskctrl.map.vo;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年5月17日 上午10:03:32
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class WzTFYbljVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/*台风编号*/
	private String tfbh;
	/*国家*/
	private String  tm;
	/*预报时间*/
	private Timestamp ybsj;
	/*发生时间*/
	private Timestamp rqsj;
	/*纬度*/
	private String wd;
	/*经度*/
	private String jd;
	/*中心气压*/
	private String zxqy;
	/*最大风速*/
	private String zxfs;
	/*移动方向*/
	private String ydfx;
	/*七级半径*/
	private String radius7;
	/*十级半径*/
	private String radius10;
	private String depict;
	private String bedit;
	private String linecoloe;
	/*是否处理， 1 未处理，0已处理*/
	private String isUse;
	
	/*七级十级十二级台风四个方位的半径大小，如果是999999则用0 代替*/
	private String  radius7_en;
	private String  radius7_wn;
	private String  radius7_ws;
	private String  radius7_es;
	private String  radius10_en;
	private String  radius10_wn;
	private String  radius10_ws;
	private String  radius10_es;
	private String  radius12_en;
	private String  radius12_wn;
	private String  radius12_ws;
	private String  radius12_es;
	/*最大阵风风速*/
	private String WIN_S_Gust_Max;
	/*台风强度 */
	private String Typh_Intsy;
	/*未来趋势*/ 
	private String Trend_Futrue;
	
	
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
	public Timestamp getYbsj() {
		return ybsj;
	}
	public void setYbsj(Timestamp ybsj) {
		this.ybsj = ybsj;
	}
	public Timestamp getRqsj() {
		return rqsj;
	}
	public void setRqsj(Timestamp rqsj) {
		this.rqsj = rqsj;
	}
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	public String getJd() {
		return jd;
	}
	public void setJd(String jd) {
		this.jd = jd;
	}
	public String getZxqy() {
		return zxqy;
	}
	public void setZxqy(String zxqy) {
		this.zxqy = zxqy;
	}
	public String getZxfs() {
		return zxfs;
	}
	public void setZxfs(String zxfs) {
		this.zxfs = zxfs;
	}
	public String getYdfx() {
		return ydfx;
	}
	public void setYdfx(String ydfx) {
		this.ydfx = ydfx;
	}
	public String getRadius7() {
		return radius7;
	}
	public void setRadius7(String radius7) {
		this.radius7 = radius7;
	}
	public String getRadius10() {
		return radius10;
	}
	public void setRadius10(String radius10) {
		this.radius10 = radius10;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getBedit() {
		return bedit;
	}
	public void setBedit(String bedit) {
		this.bedit = bedit;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getLinecoloe() {
		return linecoloe;
	}
	public void setLinecoloe(String linecoloe) {
		this.linecoloe = linecoloe;
	}
	
	public String getRadius7_en() {
		return radius7_en;
	}
	public void setRadius7_en(String radius7_en) {
		this.radius7_en = radius7_en;
	}
	public String getRadius7_wn() {
		return radius7_wn;
	}
	public void setRadius7_wn(String radius7_wn) {
		this.radius7_wn = radius7_wn;
	}
	public String getRadius7_ws() {
		return radius7_ws;
	}
	public void setRadius7_ws(String radius7_ws) {
		this.radius7_ws = radius7_ws;
	}
	public String getRadius7_es() {
		return radius7_es;
	}
	public void setRadius7_es(String radius7_es) {
		this.radius7_es = radius7_es;
	}
	public String getRadius10_en() {
		return radius10_en;
	}
	public void setRadius10_en(String radius10_en) {
		this.radius10_en = radius10_en;
	}
	public String getRadius10_wn() {
		return radius10_wn;
	}
	public void setRadius10_wn(String radius10_wn) {
		this.radius10_wn = radius10_wn;
	}
	public String getRadius10_ws() {
		return radius10_ws;
	}
	public void setRadius10_ws(String radius10_ws) {
		this.radius10_ws = radius10_ws;
	}
	public String getRadius10_es() {
		return radius10_es;
	}
	public void setRadius10_es(String radius10_es) {
		this.radius10_es = radius10_es;
	}
	public String getRadius12_en() {
		return radius12_en;
	}
	public void setRadius12_en(String radius12_en) {
		this.radius12_en = radius12_en;
	}
	public String getRadius12_wn() {
		return radius12_wn;
	}
	public void setRadius12_wn(String radius12_wn) {
		this.radius12_wn = radius12_wn;
	}
	public String getRadius12_ws() {
		return radius12_ws;
	}
	public void setRadius12_ws(String radius12_ws) {
		this.radius12_ws = radius12_ws;
	}
	public String getRadius12_es() {
		return radius12_es;
	}
	public void setRadius12_es(String radius12_es) {
		this.radius12_es = radius12_es;
	}
	public String getWIN_S_Gust_Max() {
		return WIN_S_Gust_Max;
	}
	public void setWIN_S_Gust_Max(String wIN_S_Gust_Max) {
		WIN_S_Gust_Max = wIN_S_Gust_Max;
	}
	public String getTyph_Intsy() {
		return Typh_Intsy;
	}
	public void setTyph_Intsy(String typh_Intsy) {
		Typh_Intsy = typh_Intsy;
	}
	public String getTrend_Futrue() {
		return Trend_Futrue;
	}
	public void setTrend_Futrue(String trend_Futrue) {
		Trend_Futrue = trend_Futrue;
	}
}
