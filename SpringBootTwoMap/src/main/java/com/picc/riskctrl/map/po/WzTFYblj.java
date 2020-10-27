package com.picc.riskctrl.map.po;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年5月17日 上午10:03:32
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "WZ_TFYBLJ")
public class WzTFYblj implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private WzTFYbljId  id;
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
	/*用于与气象局台风对接的标志，1： 与气象局对接，没有信息：指的是与气象局对接之前的数据*/
	private String depict;
	private String linecoloe;
	/*是否处理， 13 未处理，24已处理*/
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
	
	/** 台风主表 */
	private TyphoonLatestInfo typhoonLatestInfo;
	
	/**       
	 *联合主键
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "tfbh", column = @Column(name = "TFBH")),
			@AttributeOverride(name = "tm", column = @Column(name = "TM")),
			@AttributeOverride(name = "ybsj", column = @Column(name = "YBSJ")),
			@AttributeOverride(name = "rqsj", column = @Column(name = "RQSJ"))})
	public WzTFYbljId getId() {
		return id;
	}
	public void setId(WzTFYbljId id) {
		this.id = id;
	}
	@Column(name = "WD")
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	@Column(name = "JD")
	public String getJd() {
		return jd;
	}
	public void setJd(String jd) {
		this.jd = jd;
	}
	@Column(name = "ZXQY")
	public String getZxqy() {
		return zxqy;
	}
	public void setZxqy(String zxqy) {
		this.zxqy = zxqy;
	}
	@Column(name = "ZXFS")
	public String getZxfs() {
		return zxfs;
	}
	public void setZxfs(String zxfs) {
		this.zxfs = zxfs;
	}
	@Column(name = "YDFX")
	public String getYdfx() {
		return ydfx;
	}
	public void setYdfx(String ydfx) {
		this.ydfx = ydfx;
	}
	@Column(name = "RADIUS7")
	public String getRadius7() {
		return radius7;
	}
	public void setRadius7(String radius7) {
		this.radius7 = radius7;
	}
	@Column(name = "RADIUS10")
	public String getRadius10() {
		return radius10;
	}
	public void setRadius10(String radius10) {
		this.radius10 = radius10;
	}
	@Column(name = "DEPICT")
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	@Column(name = "LINECOLOE")
	public String getLinecoloe() {
		return linecoloe;
	}
	public void setLinecoloe(String linecoloe) {
		this.linecoloe = linecoloe;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	@Column(name = "ISUSE")
	public String getIsUse() {
		return isUse;
	}
	@Column(name = "RADIUS7_EN")
	public String getRadius7_en() {
		return radius7_en;
	}
	public void setRadius7_en(String radius7_en) {
		this.radius7_en = radius7_en;
	}
	@Column(name = "RADIUS7_WN")
	public String getRadius7_wn() {
		return radius7_wn;
	}
	public void setRadius7_wn(String radius7_wn) {
		this.radius7_wn = radius7_wn;
	}
	@Column(name = "RADIUS7_WS")
	public String getRadius7_ws() {
		return radius7_ws;
	}
	public void setRadius7_ws(String radius7_ws) {
		this.radius7_ws = radius7_ws;
	}
	@Column(name = "RADIUS7_ES")
	public String getRadius7_es() {
		return radius7_es;
	}
	public void setRadius7_es(String radius7_es) {
		this.radius7_es = radius7_es;
	}
	@Column(name = "RADIUS10_EN")
	public String getRadius10_en() {
		return radius10_en;
	}
	public void setRadius10_en(String radius10_en) {
		this.radius10_en = radius10_en;
	}
	@Column(name = "RADIUS10_WN")
	public String getRadius10_wn() {
		return radius10_wn;
	}
	public void setRadius10_wn(String radius10_wn) {
		this.radius10_wn = radius10_wn;
	}
	@Column(name = "RADIUS10_WS")
	public String getRadius10_ws() {
		return radius10_ws;
	}
	public void setRadius10_ws(String radius10_ws) {
		this.radius10_ws = radius10_ws;
	}
	@Column(name = "RADIUS10_ES")
	public String getRadius10_es() {
		return radius10_es;
	}
	public void setRadius10_es(String radius10_es) {
		this.radius10_es = radius10_es;
	}
	@Column(name = "RADIUS12_EN")
	public String getRadius12_en() {
		return radius12_en;
	}
	public void setRadius12_en(String radius12_en) {
		this.radius12_en = radius12_en;
	}
	@Column(name = "RADIUS12_WN")
	public String getRadius12_wn() {
		return radius12_wn;
	}
	public void setRadius12_wn(String radius12_wn) {
		this.radius12_wn = radius12_wn;
	}
	@Column(name = "RADIUS12_WS")
	public String getRadius12_ws() {
		return radius12_ws;
	}
	public void setRadius12_ws(String radius12_ws) {
		this.radius12_ws = radius12_ws;
	}
	@Column(name = "RADIUS12_ES")
	public String getRadius12_es() {
		return radius12_es;
	}
	public void setRadius12_es(String radius12_es) {
		this.radius12_es = radius12_es;
	}
	
	@Column(name = "WIN_S_GUST_MAX")
	public String getWIN_S_Gust_Max() {
		return WIN_S_Gust_Max;
	}
	public void setWIN_S_Gust_Max(String wIN_S_Gust_Max) {
		WIN_S_Gust_Max = wIN_S_Gust_Max;
	}
	@Column(name = "TYPH_INTSY")
	public String getTyph_Intsy() {
		return Typh_Intsy;
	}
	public void setTyph_Intsy(String typh_Intsy) {
		Typh_Intsy = typh_Intsy;
	}
	@Column(name = "TREND_FUTRUE")
	public String getTrend_Futrue() {
		return Trend_Futrue;
	}
	public void setTrend_Futrue(String trend_Futrue) {
		Trend_Futrue = trend_Futrue;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TFBH", nullable = false, insertable = false, updatable = false)
	public TyphoonLatestInfo getTyphoonLatestInfo() {
		return typhoonLatestInfo;
	}
	public void setTyphoonLatestInfo(TyphoonLatestInfo typhoonLatestInfo) {
		this.typhoonLatestInfo = typhoonLatestInfo;
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
		WzTFYblj other = (WzTFYblj) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

