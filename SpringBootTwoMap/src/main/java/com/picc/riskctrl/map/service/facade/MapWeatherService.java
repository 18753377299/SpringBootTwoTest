package com.picc.riskctrl.map.service.facade;

import java.util.Date;

import com.picc.riskctrl.common.response.RiskResponseVo;
import com.picc.riskctrl.map.po.TyphoonLatestInfo;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonBaseInfo;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonPath;
	/**
 * @author  作者 E-mail: lqk
 * @date 创建时间：2019年9月19日 上午10:40:59
 * @version 1.0 
 * @parameter 
 * @since 与气象局进行台风和降雨数据对接 
 * @return  */
//@Rpc
public interface MapWeatherService {
	/*获取气象局降雨的栅格数据*/
	public RiskResponseVo gainWeatherRainGridData();
	
	public  TyphoonLatestInfo   searchTyphoonBaseInfo(TyphoonPath typhoonPath);
	
	public RiskResponseVo updateTyphoonInfo(TyphoonBaseInfo typhoonBaseInfo);
	
	public RiskResponseVo gainWeatherRainData(Date newDate,int day,int hour);
	
	public RiskResponseVo gainDataEarlyWarning(String earlyWarning);
	
	public RiskResponseVo gainWeatherTyphoonData(String startDate ,String endDate);
	
	public RiskResponseVo operateEarlyWarnRainArea(String flag);
	
	public  void operateRainArea(String date);
	
	public  RiskResponseVo testWzCorporeLs();
	
	public RiskResponseVo gainWeatherRainYJData(Date newDate,int day,int hour);
	
	public RiskResponseVo gainWeatherNDayRainArea(String flag,int day);
	
}
