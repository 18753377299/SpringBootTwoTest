package com.picc.riskctrl.map.vo.typhoonRequest;

import java.io.Serializable;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年9月3日 下午1:56:54
 * @version 1.0 
 * @parameter 
 * @since  资料时间 如果晚于 Year ， Mon，Day 的时间就是预报路径
 * @return  */
public class TyphoonPath  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*资料时间*/
	private String Datetime;
	/*年(资料时间)*/
	private String Year_Data;
	/*月(资料时间)*/
	private String Mon_Data;
	/*日(资料时间)*/
	private String Day_Data;
	/*时(资料时间) */
	private String Hour_Data;
	/*编报(加工)中心*/
	private String Bul_Center;
	/*编报中心代号*/
	private String Bul_Center_Code;
	/*台风名*/
	private String TYPH_Name;
	/*台风等级*/
	private String Typh_Grade;
	/*产品类型*/
	private String Prod_type;
	/*年*/
	private String Year;
	/*月*/
	private String Mon;
	/*日*/
	private String Day;
	/*时 */
	private String Hour;
	 /*分*/
	private String Min;
	/*国内编号*/
	private String Num_Nati;
	/*国际编号*/
	private String Num_INati;
	/*预报时效个数*/
	private String Validtime_Count;
	/*资料标识*/
	private String DATA_ID;
	/*预报时效 */
	private String Validtime;
	/*纬度*/
	private String Lat;
	/*经度*/
	private String Lon;
	/*气压*/ 
	private String PRS;
	/*最大阵风风速*/
	private String WIN_S_Gust_Max;
	/*中心附近最大持续风风速*/
	private String WIN_S_Conti_Max;
	/*大于等于7级大风的方位1*/
	private String WING_A7_Bear1;
	/*大于等于7级大风的方位1风圈半径*/
	private String Radiu_Bear1_WING_A7;
	/*大于等于7级大风的方位2*/
	private String WING_A7_Bear2;
	/*大于等于7级大风的方位2风圈半径*/
	private String Radiu_Bear2_WING_A7;
	/*大于等于7级大风的方位3*/
	private String WING_A7_Bear3;
	/*大于等于7级大风的方位3风圈半径 */
	private String Radiu_Bear3_WING_A7;
	/*大于等于7级大风的方位4 */
	private String WING_A7_Bear4;
	/*大于等于7级大风的方位4风圈半径 */
	private String Radiu_Bear4_WING_A7;
	/*大于等于10级大风的方位1 */
	private String WING_A10_Bear1;
	/*大于等于10级大风的方位1风圈半径*/
	private String Radiu_Bear1_WING_A10;
	/*大于等于10级大风的方位2*/
	private String WING_A10_Bear2;
	/*大于等于10级大风的方位2风圈半径*/
	private String Radiu_Bear2_WING_A10;
	/*大于等于10级大风的方位3*/
	private String WING_A10_Bear3;
	/*大于等于10级大风的方位3风圈半径*/
	private String Radiu_Bear3_WING_A10;
	/*大于等于10级大风的方位4 */
	private String WING_A10_Bear4;
	/*大于等于10级大风的方位4风圈半径*/
	private String Radiu_Bear4_WING_A10;
	/*大于等于12级大风的方位1*/
	private String WING_A12_Bear1;
	/*大于等于12级大风的方位1风圈半径*/
	private String Radiu_Bear1_WING_A12;
	/*大于等于12级大风的方位2*/
	private String WING_A12_Bear2;
	/*大于等于12级大风的方位2风圈半径 */
	private String Radiu_Bear2_WING_A12;
	/*大于等于12级大风的方位3*/
	private String WING_A12_Bear3;
	/*大于等于12级大风的方位3风圈半径*/
	private String Radiu_Bear3_WING_A12;
	/*大于等于12级大风的方位4*/
	private String WING_A12_Bear4;
	/*大于等于12级大风的方位4风圈半径*/
	private String Radiu_Bear4_WING_A12;
	/*未来移向*/
	private String MoDir_Future;
	/*未来移速 */
	private String MoSpeed_Futrue;
	/*台风强度 */
	private String Typh_Intsy;
	/*未来趋势*/ 
	private String Trend_Futrue;
	/*台风是否结束，0：结束，1：未结束*/
	private String ISACTIVE;
	
	public String getDatetime() {
		return Datetime;
	}
	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
	public String getYear_Data() {
		return Year_Data;
	}
	public void setYear_Data(String year_Data) {
		Year_Data = year_Data;
	}
	public String getMon_Data() {
		return Mon_Data;
	}
	public void setMon_Data(String mon_Data) {
		Mon_Data = mon_Data;
	}
	public String getDay_Data() {
		return Day_Data;
	}
	public void setDay_Data(String day_Data) {
		Day_Data = day_Data;
	}
	public String getHour_Data() {
		return Hour_Data;
	}
	public void setHour_Data(String hour_Data) {
		Hour_Data = hour_Data;
	}
	public String getBul_Center() {
		return Bul_Center;
	}
	public void setBul_Center(String bul_Center) {
		Bul_Center = bul_Center;
	}
	public String getBul_Center_Code() {
		return Bul_Center_Code;
	}
	public void setBul_Center_Code(String bul_Center_Code) {
		Bul_Center_Code = bul_Center_Code;
	}
	public String getTYPH_Name() {
		return TYPH_Name;
	}
	public void setTYPH_Name(String tYPH_Name) {
		TYPH_Name = tYPH_Name;
	}
	public String getTyph_Grade() {
		return Typh_Grade;
	}
	public void setTyph_Grade(String typh_Grade) {
		Typh_Grade = typh_Grade;
	}
	public String getProd_type() {
		return Prod_type;
	}
	public void setProd_type(String prod_type) {
		Prod_type = prod_type;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getMon() {
		return Mon;
	}
	public void setMon(String mon) {
		Mon = mon;
	}
	public String getDay() {
		return Day;
	}
	public void setDay(String day) {
		Day = day;
	}
	public String getHour() {
		return Hour;
	}
	public void setHour(String hour) {
		Hour = hour;
	}
	public String getMin() {
		return Min;
	}
	public void setMin(String min) {
		Min = min;
	}
	public String getNum_Nati() {
		return Num_Nati;
	}
	public void setNum_Nati(String num_Nati) {
		Num_Nati = num_Nati;
	}
	public String getNum_INati() {
		return Num_INati;
	}
	public void setNum_INati(String num_INati) {
		Num_INati = num_INati;
	}
	public String getValidtime_Count() {
		return Validtime_Count;
	}
	public void setValidtime_Count(String validtime_Count) {
		Validtime_Count = validtime_Count;
	}
	public String getDATA_ID() {
		return DATA_ID;
	}
	public void setDATA_ID(String dATA_ID) {
		DATA_ID = dATA_ID;
	}
	public String getValidtime() {
		return Validtime;
	}
	public void setValidtime(String validtime) {
		Validtime = validtime;
	}
	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getLon() {
		return Lon;
	}
	public void setLon(String lon) {
		Lon = lon;
	}
	public String getPRS() {
		return PRS;
	}
	public void setPRS(String pRS) {
		PRS = pRS;
	}
	public String getWIN_S_Gust_Max() {
		return WIN_S_Gust_Max;
	}
	public void setWIN_S_Gust_Max(String wIN_S_Gust_Max) {
		WIN_S_Gust_Max = wIN_S_Gust_Max;
	}
	public String getWIN_S_Conti_Max() {
		return WIN_S_Conti_Max;
	}
	public void setWIN_S_Conti_Max(String wIN_S_Conti_Max) {
		WIN_S_Conti_Max = wIN_S_Conti_Max;
	}
	public String getWING_A7_Bear1() {
		return WING_A7_Bear1;
	}
	public void setWING_A7_Bear1(String wING_A7_Bear1) {
		WING_A7_Bear1 = wING_A7_Bear1;
	}
	public String getRadiu_Bear1_WING_A7() {
		return Radiu_Bear1_WING_A7;
	}
	public void setRadiu_Bear1_WING_A7(String radiu_Bear1_WING_A7) {
		Radiu_Bear1_WING_A7 = radiu_Bear1_WING_A7;
	}
	public String getWING_A7_Bear2() {
		return WING_A7_Bear2;
	}
	public void setWING_A7_Bear2(String wING_A7_Bear2) {
		WING_A7_Bear2 = wING_A7_Bear2;
	}
	public String getRadiu_Bear2_WING_A7() {
		return Radiu_Bear2_WING_A7;
	}
	public void setRadiu_Bear2_WING_A7(String radiu_Bear2_WING_A7) {
		Radiu_Bear2_WING_A7 = radiu_Bear2_WING_A7;
	}
	public String getWING_A7_Bear3() {
		return WING_A7_Bear3;
	}
	public void setWING_A7_Bear3(String wING_A7_Bear3) {
		WING_A7_Bear3 = wING_A7_Bear3;
	}
	public String getRadiu_Bear3_WING_A7() {
		return Radiu_Bear3_WING_A7;
	}
	public void setRadiu_Bear3_WING_A7(String radiu_Bear3_WING_A7) {
		Radiu_Bear3_WING_A7 = radiu_Bear3_WING_A7;
	}
	public String getWING_A7_Bear4() {
		return WING_A7_Bear4;
	}
	public void setWING_A7_Bear4(String wING_A7_Bear4) {
		WING_A7_Bear4 = wING_A7_Bear4;
	}
	public String getRadiu_Bear4_WING_A7() {
		return Radiu_Bear4_WING_A7;
	}
	public void setRadiu_Bear4_WING_A7(String radiu_Bear4_WING_A7) {
		Radiu_Bear4_WING_A7 = radiu_Bear4_WING_A7;
	}
	public String getWING_A10_Bear1() {
		return WING_A10_Bear1;
	}
	public void setWING_A10_Bear1(String wING_A10_Bear1) {
		WING_A10_Bear1 = wING_A10_Bear1;
	}
	public String getRadiu_Bear1_WING_A10() {
		return Radiu_Bear1_WING_A10;
	}
	public void setRadiu_Bear1_WING_A10(String radiu_Bear1_WING_A10) {
		Radiu_Bear1_WING_A10 = radiu_Bear1_WING_A10;
	}
	public String getWING_A10_Bear2() {
		return WING_A10_Bear2;
	}
	public void setWING_A10_Bear2(String wING_A10_Bear2) {
		WING_A10_Bear2 = wING_A10_Bear2;
	}
	public String getRadiu_Bear2_WING_A10() {
		return Radiu_Bear2_WING_A10;
	}
	public void setRadiu_Bear2_WING_A10(String radiu_Bear2_WING_A10) {
		Radiu_Bear2_WING_A10 = radiu_Bear2_WING_A10;
	}
	public String getWING_A10_Bear3() {
		return WING_A10_Bear3;
	}
	public void setWING_A10_Bear3(String wING_A10_Bear3) {
		WING_A10_Bear3 = wING_A10_Bear3;
	}
	public String getRadiu_Bear3_WING_A10() {
		return Radiu_Bear3_WING_A10;
	}
	public void setRadiu_Bear3_WING_A10(String radiu_Bear3_WING_A10) {
		Radiu_Bear3_WING_A10 = radiu_Bear3_WING_A10;
	}
	public String getWING_A10_Bear4() {
		return WING_A10_Bear4;
	}
	public void setWING_A10_Bear4(String wING_A10_Bear4) {
		WING_A10_Bear4 = wING_A10_Bear4;
	}
	public String getRadiu_Bear4_WING_A10() {
		return Radiu_Bear4_WING_A10;
	}
	public void setRadiu_Bear4_WING_A10(String radiu_Bear4_WING_A10) {
		Radiu_Bear4_WING_A10 = radiu_Bear4_WING_A10;
	}
	public String getWING_A12_Bear1() {
		return WING_A12_Bear1;
	}
	public void setWING_A12_Bear1(String wING_A12_Bear1) {
		WING_A12_Bear1 = wING_A12_Bear1;
	}
	public String getRadiu_Bear1_WING_A12() {
		return Radiu_Bear1_WING_A12;
	}
	public void setRadiu_Bear1_WING_A12(String radiu_Bear1_WING_A12) {
		Radiu_Bear1_WING_A12 = radiu_Bear1_WING_A12;
	}
	public String getWING_A12_Bear2() {
		return WING_A12_Bear2;
	}
	public void setWING_A12_Bear2(String wING_A12_Bear2) {
		WING_A12_Bear2 = wING_A12_Bear2;
	}
	public String getRadiu_Bear2_WING_A12() {
		return Radiu_Bear2_WING_A12;
	}
	public void setRadiu_Bear2_WING_A12(String radiu_Bear2_WING_A12) {
		Radiu_Bear2_WING_A12 = radiu_Bear2_WING_A12;
	}
	public String getWING_A12_Bear3() {
		return WING_A12_Bear3;
	}
	public void setWING_A12_Bear3(String wING_A12_Bear3) {
		WING_A12_Bear3 = wING_A12_Bear3;
	}
	public String getRadiu_Bear3_WING_A12() {
		return Radiu_Bear3_WING_A12;
	}
	public void setRadiu_Bear3_WING_A12(String radiu_Bear3_WING_A12) {
		Radiu_Bear3_WING_A12 = radiu_Bear3_WING_A12;
	}
	public String getWING_A12_Bear4() {
		return WING_A12_Bear4;
	}
	public void setWING_A12_Bear4(String wING_A12_Bear4) {
		WING_A12_Bear4 = wING_A12_Bear4;
	}
	public String getRadiu_Bear4_WING_A12() {
		return Radiu_Bear4_WING_A12;
	}
	public void setRadiu_Bear4_WING_A12(String radiu_Bear4_WING_A12) {
		Radiu_Bear4_WING_A12 = radiu_Bear4_WING_A12;
	}
	public String getMoDir_Future() {
		return MoDir_Future;
	}
	public void setMoDir_Future(String moDir_Future) {
		MoDir_Future = moDir_Future;
	}
	public String getMoSpeed_Futrue() {
		return MoSpeed_Futrue;
	}
	public void setMoSpeed_Futrue(String moSpeed_Futrue) {
		MoSpeed_Futrue = moSpeed_Futrue;
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
	public String getISACTIVE() {
		return ISACTIVE;
	}
	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}
	
}
