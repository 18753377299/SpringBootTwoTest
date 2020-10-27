//package com.picc.riskctrl.map.web.action;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.ResourceBundle;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.aspectj.apache.bcel.classfile.Code;
//import org.easy.excel.result.ExcelImportResult;
//import org.easy.excel.util.ApplicationContextUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.alibaba.fastjson.JSON;
//import com.picc.riskctrl.common.datasources.service.facade.DataSourcesService;
//import com.picc.riskctrl.common.model.UserInfo;
//import com.picc.riskctrl.common.schema.RiskDaddress;
//import com.picc.riskctrl.common.schema.RiskMapAddress;
//import com.picc.riskctrl.common.schema.RiskMapAddressModify;
//import com.picc.riskctrl.common.schema.RiskMapDisaster;
//import com.picc.riskctrl.common.schema.RiskMapFloodAddress;
//import com.picc.riskctrl.common.schema.RiskMapInsured;
//import com.picc.riskctrl.common.schema.RiskMapItemkind;
//import com.picc.riskctrl.common.schema.RiskMapMain;
//import com.picc.riskctrl.common.schema.RiskMapTyphoonBlack;
//import com.picc.riskctrl.common.schema.RiskWarningPush;
//import com.picc.riskctrl.common.schema.vo.RiskMapAddressVo;
//import com.picc.riskctrl.common.schema.vo.RiskMapDangerVo;
//import com.picc.riskctrl.common.schema.vo.RiskMapMainVo;
//import com.picc.riskctrl.common.util.FTPUtil;
//import com.picc.riskctrl.common.util.MapUtils;
////import com.picc.riskctrl.common.vo.ExchRateVo;
//import com.picc.riskctrl.common.vo.PrplClaimVo;
//import com.picc.riskctrl.map.service.facade.MapService;
//import com.picc.riskctrl.map.service.facade.MapWarnService;
//import com.picc.riskctrl.map.service.facade.MapWeatherService;
//import com.picc.riskctrl.map.vo.TfWordReqVo;
//import com.picc.riskctrl.map.vo.request.LongAndLatitude;
//import com.picc.riskctrl.map.vo.request.MapBound;
//import com.picc.riskctrl.map.vo.request.MapQuery;
//import com.picc.riskctrl.map.vo.request.MapRequestInfoVo;
//import com.picc.riskctrl.map.vo.request.RiskMapFloodUpdateVo;
//import com.picc.riskctrl.map.vo.request.RiskMapRegionVo;
//import com.picc.riskctrl.map.vo.request.WZ_tfbh;
//import com.picc.riskctrl.map.vo.request.WZ_tfbhVo;
//import com.picc.riskctrl.map.vo.response.CurrentDistrict;
//import com.picc.riskctrl.map.vo.response.MapMainByAddrVo;
//import com.picc.riskctrl.map.vo.response.MapMainResponseVo;
//import com.picc.riskctrl.map.vo.response.MapQueryResponse;
//import com.picc.riskctrl.map.vo.response.Province;
//import com.picc.riskctrl.map.vo.response.WZ_tfbhResponse;
//import com.picc.riskctrl.map.vo.response.dangerManage.DangerManageMapInfo;
//import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonBaseInfo;
//import com.picc.riskctrl.riskins.service.facade.RiskInsConfigService;
//import com.picc.riskctrl.riskins.service.facade.RiskInsService;
//import com.picc.riskctrl.typhoonearlywarning.service.facade.TyphoonEarlyWarningService;
//
//import ins.framework.web.AjaxResult;
//
///**
// * @author  作者 E-mail: liqiankun
// * @date 创建时间：2017年10月23日 上午10:14:35
// * @version 1.0 
// * @parameter 
// * @since  
// * @return  */
//@Controller
//@RequestMapping("/riskmap")
//public class RiskMapAction {
//	@Autowired
//	MapService mapService;
//	@Autowired
//	DataSourcesService dataSourcesService;
//	@Autowired
//	RiskInsConfigService riskInsConfigService;
//	@Autowired
//	RiskInsService riskInsService;
//	
//	@Autowired
//	MapWarnService mapWarnService;
//
//	@Autowired
//	TyphoonEarlyWarningService typhoonEarlyWarningService;
//	
//	@Autowired
//	MapWeatherService mapWeatherService;
//	
////	@Autowired
////	ExcelContext excelContext; 	
//	
//	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
//	/**
//	 * @功能:初始化跳转到初始页
//	 * @param 
//	 * @return String 
//	 * @author liqiankun
//	 * @日期:20171023
//	 */
//	@RequestMapping("/claimMap")
//	public String listMap(){
//		return "map/MapEdit";
//	}
//	
//	/**
//	 * 
//	 * <p>Title:queryMyInfoByPage</p>
//	 * <p>Description:搜索进行分页查询</p>
//	 * @author Wei shituan
//	 * @date 20171027
//	 * @param mapRequestInfoVo 页面所传信息封装的实体类
//	 * @return List<RiskMapDisaster> 根据地址调用接口及数据库所返回的地址集合
//	 * @throws Exception 
//	 */
//	@RequestMapping(value="/queryMyInfoByPage")
//	@ResponseBody
//	public List<RiskMapDisaster>  queryMyInfoByPage(@RequestBody MapRequestInfoVo mapRequestInfoVo) throws Exception{
//		List<RiskMapDisaster> dataList = mapService.queryTotalData(mapRequestInfoVo);
//		return dataList;
//	}
//	/**
//	 * 
//	 * <p>Title:queryDataByPageAnaly</p>
//	 * <p>Description:页面分析数据</p>
//	 * @author Wei shituan
//	 * @date 20171027
//	 * @param mapRequestInfoVo 页面所传信息封装的实体类
//	 * @return List<RiskMapDisaster> 
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/queryDataByPageAnaly")
//	@ResponseBody
//	public List<RiskMapDisaster>  queryDataByPageAnaly(@RequestBody MapRequestInfoVo mapRequestInfoVo) throws Exception{
//		List<RiskMapDisaster> riskMapDisaster=mapService.queryDataByPageAnaly(mapRequestInfoVo);
//		return riskMapDisaster;
//		
//	}
//	/**
//	 * 
//	 * <p>Title:queryInfoByXY</p>
//	 * <p>Description: </p>
//	 * @author  Wei shituan
//	 * @date  20171027
//	 * @param request
//	 * @return  LongAndLatitude  地图页面始化分公司对应经纬度的包装类
//	 */
//	@RequestMapping(value="/queryInfoByXY")
//	@ResponseBody
//	public  LongAndLatitude queryInfoByXY(HttpServletRequest request,@RequestBody MapRequestInfoVo mapRequestInfoVo){
//		String attribute="";
//		if("".equals(mapRequestInfoVo.getBranchCompany().trim())){
//			 UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//			attribute = userInfo.getComCode();
//		} else {
//			attribute=mapRequestInfoVo.getBranchCompany();
//		}
//	 return mapService.queryComeCode(attribute);
//	}
//	/**
//	 * 
//	 * <p>Title:queryInfoByBound</p>
//	 * <p>Description: </p>
//	 * @author  liqiankun
//	 * @date  20180424
//	 * @param request
//	 * @return  List<RiskMapDisaster>
//	 */
//
//	@RequestMapping(value="/queryInfoByBound", method = RequestMethod.POST)
//	@ResponseBody
//	public List<RiskMapDisaster>  queryInfoByBound(@RequestBody MapBound mapBound) throws Exception{
//		List<RiskMapDisaster> riskMapDisasters=mapService.queryInfoByBound(mapBound);
//		return riskMapDisasters;
//	}
//	/**
//	 * 
//	 * <p>Title:queryDetailAddress</p>
//	 * <p>Description: </p>
//	 * @author  AnQ
//	 * @date  20180822
//	 * @param request
//	 * @return  AjaxResult 
//	 */
//	@RequestMapping(value="/queryDetailAddress", method = RequestMethod.POST)
//	@ResponseBody
//	public MapQueryResponse queryDetailAddress(@RequestBody MapQuery mapQuery) throws Exception{
//		
//		MapQueryResponse mapQueryResponse = mapService.queryDetailAddress(mapQuery);
//		System.out.println(JSON.toJSONString(mapQueryResponse));
//		return mapQueryResponse;
//	}
//	
//	/**
//	 * 
//	 * @author  王坤龙
//	 * @date  20180830
//	 * @param addressId
//	 * @return  AjaxResult 
//	 */
//	@RequestMapping(value="/queryMainByAddress",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<MapMainByAddrVo> queryMainByAddress(@RequestBody RiskMapMainVo riskMapMainVo) throws Exception{
//		List<MapMainByAddrVo> result = new ArrayList<MapMainByAddrVo>();
//		List<RiskMapMain> list;
//		List<RiskMapInsured> dataInsuredList;
//		list = mapService.queryMainByAddressId(riskMapMainVo.getIds(),riskMapMainVo.getSumAmount(),riskMapMainVo.getPolicyno(),riskMapMainVo.getMapCode(),riskMapMainVo.getNowdate(),riskMapMainVo.getPowerSQL(),riskMapMainVo.getSumPay());
//	for (RiskMapMain riskmain:list){
////			合并初始时间
//			Date startDateTemp = riskmain.getStartDate();
//			int startHour = riskmain.getStartHour();
//			Calendar c = Calendar.getInstance();
//		    c.setTime(startDateTemp);
//		    c.add(Calendar.HOUR_OF_DAY, startHour);
//	        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时");
//	        String newStartDate = format.format(c.getTime());
////		   	 合并结束时间
//			Date endDateTemp = riskmain.getEndDate();
//			int endHour = riskmain.getEndHour();
//			Calendar e = Calendar.getInstance();
//		    e.setTime(endDateTemp);
//		    e.add(Calendar.HOUR_OF_DAY, endHour);
//	        String newEndDate = format.format(e.getTime());
////	    	判断保单是否有效（是否在保时间内）
//	        Date now = new Date();
//	        long now_long = now.getTime();
//	        String isEffective = "";
//	        if(now_long > startDateTemp.getTime() && now_long < endDateTemp.getTime()){
//	        	isEffective = "有效";
//	        } else {
//	        	isEffective = "无效";
//	        }
////		   	 翻译riskCode
//		    String riskCode = riskmain.getRiskCode();
//		    String comCode = riskmain.getComCode();
//		    riskCode = dataSourcesService.queryRiskCodeName(riskCode, comCode);
////			翻译comCode
//		    comCode = dataSourcesService.queryComCodeCName(comCode);
////			翻译classCode 01:企财险 03:工程险
//		    String classCode = riskmain.getClassCode();
//		    if("01".equals(classCode)){
//		    	classCode = "企财险";
//		    } else if("03".equals(classCode)){
//		    	classCode = "工程险";
//		    } else{
//		    	classCode = "其他险类";
//		    }
//
//		    MapMainByAddrVo mapMainByAddrVo = new MapMainByAddrVo();
//			mapMainByAddrVo.setAddressID(riskmain.getId().getAddressID());
//			mapMainByAddrVo.setProposalNo(riskmain.getId().getProposalNo());
//			mapMainByAddrVo.setPolicyNo(riskmain.getPolicyNo());
//			mapMainByAddrVo.setRiskCode(riskCode);
//			mapMainByAddrVo.setStartDate(newStartDate);
//			mapMainByAddrVo.setEndDate(newEndDate);
//			mapMainByAddrVo.setSumAmount(riskmain.getSumAmount());
//			mapMainByAddrVo.setCoinsRate(riskmain.getCoinsRate());
//			mapMainByAddrVo.setComCode(comCode);
//			mapMainByAddrVo.setIsEffective(isEffective);
//			mapMainByAddrVo.setInsuredName("");
//			
////			假设一张保单对应n个被保险人的,查询所有被保险人名称拼接成字符串
//			dataInsuredList = mapService.queryInsuredByAddressId(riskMapMainVo.getIds().get(0), riskmain.getId().getProposalNo());
//			for (RiskMapInsured riskinsured:dataInsuredList){
//				String insuredFlag = riskinsured.getInsuredFlag();
//				char flag = insuredFlag.charAt(1);
//				if(flag == '1'){
//					if("".equals(mapMainByAddrVo.getInsuredName())){
//						mapMainByAddrVo.setInsuredName(riskinsured.getInsuredName());
//					} else {
//						mapMainByAddrVo.setInsuredName(mapMainByAddrVo.getInsuredName() + "、" + riskinsured.getInsuredName());
//					}
//				}
//			}		
//			result.add(mapMainByAddrVo);
//		}
//		return result;
//	}
//	
//	/**
//	 * 
//	 * @author  王坤龙
//	 * @date  20180830
//	 * @param addressId
//	 * @return  AjaxResult 
//	 */
//	@RequestMapping(value="/queryDetailByAddressId", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Object> queryDetailByAddressId(@RequestBody MapMainByAddrVo mapMainByAddrVo) throws Exception{
//		int addressId = mapMainByAddrVo.getAddressID();
//		String proposalNo = mapMainByAddrVo.getProposalNo();
//		String policyNo = mapMainByAddrVo.getPolicyNo();
//		List<Object> result = new ArrayList<Object>();
////		查询第一个main表
//		RiskMapMain riskmain;
//		riskmain = mapService.queryMainByAddAndProNo(addressId, proposalNo);
//		if(riskmain!=null){
////			合并初始时间
//			Date startDateTemp = riskmain.getStartDate();
//			int startHour = riskmain.getStartHour();
//			Calendar c = Calendar.getInstance();
//		    c.setTime(startDateTemp);
//		    c.add(Calendar.HOUR_OF_DAY, startHour);
//	        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时");
//	        String newStartDate = format.format(c.getTime());
////		   	 合并结束时间
//			Date endDateTemp = riskmain.getEndDate();
//			int endHour = riskmain.getEndHour();
//			Calendar e = Calendar.getInstance();
//		    e.setTime(endDateTemp);
//		    e.add(Calendar.HOUR_OF_DAY, endHour);
//	        String newEndDate = format.format(e.getTime());
////		   	 翻译riskCode
//		    String riskCode = riskmain.getRiskCode();
//		    String comCode = riskmain.getComCode();
//		    riskCode = dataSourcesService.queryRiskCodeName(riskCode, comCode);
////			翻译comCode
//		    comCode = dataSourcesService.queryComCodeCName(comCode);
////			翻译classCode 01:企财险 03:工程险
//		    String classCode = riskmain.getClassCode();
//		    if("01".equals(classCode)){
//		    	classCode = "企财险";
//		    } else if("03".equals(classCode)){
//		    	classCode = "工程险";
//		    } else{
//		    	classCode = "其他险类";
//		    }
////			翻译coinsFlag
//		    String coinsFlag = riskmain.getCoinsFlag();
//		    String coinsFlagCName = dataSourcesService.queryCoinsFlagCName(coinsFlag);
//
//		    MapMainResponseVo mapMainResponseVo = new MapMainResponseVo();
//		    mapMainResponseVo.setAddressId(riskmain.getId().getAddressID());
//		    mapMainResponseVo.setProposalNo(riskmain.getId().getProposalNo());
//		    mapMainResponseVo.setPolicyNo(riskmain.getPolicyNo());
//			mapMainResponseVo.setClassCode(classCode);
//		    mapMainResponseVo.setRiskCode(riskCode);
//		    mapMainResponseVo.setStartDate(newStartDate);
//		    mapMainResponseVo.setEndDate(newEndDate);
//		    mapMainResponseVo.setSumAmount(riskmain.getSumAmount());
//			mapMainResponseVo.setCoinsFlag(coinsFlagCName);
//		    mapMainResponseVo.setCoinsRate(riskmain.getCoinsRate());
//		    mapMainResponseVo.setComCode(comCode);
//		    mapMainResponseVo.setSumRealPay(riskmain.getSumRealPay());
//		    mapMainResponseVo.setSumUnsolvedPay(riskmain.getSumUnsolvedPay());
//		    mapMainResponseVo.setSumReamountChg(riskmain.getSumReamountChg());
//			result.add(0, mapMainResponseVo);
//		}
////		查询第二个表
//		List<RiskMapInsured> insuredList;
//		insuredList = mapService.queryInsuredByAddressId(addressId, proposalNo);
//		for(RiskMapInsured riskMapInsured:insuredList){
//			//翻译关系人角色标志(投保人、被保险人)
//		    @SuppressWarnings("serial")
//			Map<String, String> insuredFlagMap = new HashMap<String, String>() {{
//		    	put( "00", "00" ); 
//		        put( "01", "被保险人" ); 
//		        put( "10", "投保人" ); 
//		        put( "11", "投保人、被保险人" ); 
//		    }}; 
//			String insuredFlag = riskMapInsured.getInsuredFlag().substring(0, 2);
//			String insuredFlagName = "";
//			Set<String> set = insuredFlagMap.keySet();
//			for(String obj:set){
//				if(obj.equals(insuredFlag)){
//					insuredFlagName = insuredFlagMap.get(obj);
//					riskMapInsured.setInsuredFlag(insuredFlagName);
//					break;
//				}
//			}
//			//翻译关系人类型
//			String insuredType = riskMapInsured.getInsuredType();
//			if("1".equals(insuredType)){
//				insuredType = "个人";
//			} else if("2".equals(insuredType)){
//				insuredType = "团体";
//			}
//			riskMapInsured.setInsuredType(insuredType);
//			//翻译证件类型
//			String identifyType = riskMapInsured.getIdentifyType();
//			String identifyTypeName = "";
//			identifyTypeName = dataSourcesService.queryIdentifyTypeName(identifyType);
//			riskMapInsured.setIdentifyType(identifyTypeName);
//			// 证件类型为身份证01，证件号码修改为 5201111972******34 格式
//			if(StringUtils.isNotBlank(identifyType)
//					&& StringUtils.equals("01", identifyType.trim())
//					&& StringUtils.isNotBlank(riskMapInsured.getIdentifyNumber()) 
//					&& riskMapInsured.getIdentifyNumber().trim().length() == 18) {
//				StringBuffer identifyNumber = new StringBuffer(riskMapInsured.getIdentifyNumber().trim());
//				identifyNumber.replace(10, 16, "******");
//				riskMapInsured.setIdentifyNumber(identifyNumber.toString());
//			}
//		}
//		result.add(1, insuredList);
//		List<RiskMapItemkind> itemkindList;
//		itemkindList = mapService.queryItemkindByAddressId(addressId, proposalNo);
//		for(RiskMapItemkind riskMapItemkind:itemkindList){
//			//翻译是否计算保额
//			String calculateFlag = riskMapItemkind.getCalculateFlag();
//			if("1".equals(calculateFlag)){
//				calculateFlag = "是";
//			} else if("0".equals(calculateFlag)){
//				calculateFlag = "否";
//			}
//			riskMapItemkind.setCalculateFlag(calculateFlag);
//		}
//		result.add(2, itemkindList);
//		// 查询理赔数据
//		List<PrplClaimVo> prplClaimVoList = new ArrayList<PrplClaimVo>();
//		prplClaimVoList = mapService.queryPrpLclaim(policyNo,itemkindList);
//		result.add(3, prplClaimVoList);
//		return result;
//	}
//	
//	/** @author 周东旭
//	 * 标的地址查询
//	 * @param 
//	 * @return list*/
//	@RequestMapping(value="/queryData",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<RiskMapMainVo> queryData(@RequestBody RiskMapMainVo vo){
////		System.out.println(Arrays.toString(vo.getIds().toArray()));
//		
//		List<RiskMapMainVo> riskMapMainVoList =mapService.queryData(vo.getIds(),vo.getSumAmount(),vo.getPolicyno(),vo.getMapCode(),vo.getNowdate(),vo.getPowerSQL(),vo.getSumPay());
//		return riskMapMainVoList;
//	}
//	/**
//	 * @author 周东旭
//	 * 台风黑点数据查询
//	 * @param
//	 * return list*/
//	@RequestMapping(value="/queryDangerData",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<Object> queryDangerData(@RequestBody RiskMapDangerVo vo){
//		List<Object> list =mapService.queryDangerData(vo.getSql(),vo.getDangerFlag());
//		return list;
//	}
//	/**
//	 * @autor 周东旭
//	 * 灾害值获取
//	 * */
//	@RequestMapping(value="/queryRainStrom",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<RiskMapMainVo> queryRainStrom(@RequestBody RiskMapMainVo vo){
//		List<RiskMapMainVo> riskMapMainVoList =mapService.queryRainStrom(vo.getIds(),vo.getSumAmount(),vo.getPolicyno(),vo.getMapCode(),vo.getNowdate(),vo.getPowerSQL(),vo.getGraphicsFG(),vo.getSumPay());
//		return riskMapMainVoList;
//	}
//	/**
//	 * @autor 周东旭
//	 * 求总保单数   总赔款金额   总保额*/
//	@RequestMapping(value="/queryPolicyNumber",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<RiskMapMainVo> queryPolicyNumber(@RequestBody RiskMapMainVo vo){
//		List<RiskMapMainVo> riskMapMainVoList =mapService.queryPolicyNumber(vo.getIds(),vo.getSumAmount(),vo.getPolicyno(),vo.getMapCode(),vo.getNowdate(),vo.getPowerSQL(), vo.getSumPay());
//		return riskMapMainVoList;
//	}
//	/**
//	 * @author 周东旭
//	 * 分类饼图*/
//	@RequestMapping(value="/querySwitching",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<RiskMapMainVo> querySwitching(@RequestBody RiskMapMainVo vo){
//		List<RiskMapMainVo> riskMapMainVoList = mapService.querySwitching(vo.getIds(),vo.getSumAmount(),vo.getPolicyno(),vo.getMapCode(),vo.getNowdate(),vo.getPowerSQL(),vo.getGraphicsFG(),vo.getSumPay());
//		return riskMapMainVoList;
//	}
//	
//	
//	@RequestMapping(value="/queryCode",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public RiskMapMainVo queryCode(HttpServletRequest request,@RequestBody RiskMapMainVo vo) {
//		List CodeList=vo.getCodeList();
//		RiskMapMainVo riskMapMainVo =new RiskMapMainVo();
//		List riskCodeList2=new ArrayList();
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		List list=new ArrayList();
//		Code code=null;
//		for(int i=0; i<CodeList.size();i++) {
//			List<Code> riskCodeList = null;
//			try {
//				riskCodeList = riskInsConfigService.queryRiskCode((String)CodeList.get(i), userInfo.getComCode());
//				if(CodeList.get(i).equals("YQ")) {
//					code=new Code();
//					for(int j=0;j<riskCodeList.size();j++) {
//						code=riskCodeList.get(j);
//						code.setClassName("企财险（一切险）");
//						list.add(code);
//					}
//				}else if(CodeList.get(i).equals("JB")) {
//					code=new Code();
//					for(int j=0;j<riskCodeList.size();j++) {
//						code=riskCodeList.get(j);
//						code.setClassName("企财险（基本险）");
//						list.add(code);
//					}
//				}else if(CodeList.get(i).equals("ZH")) {
//					code=new Code();
//					for(int j=0;j<riskCodeList.size();j++) {
//						code=riskCodeList.get(j);
//						code.setClassName("企财险（综合险）");
//						list.add(code);
//					}
//				}else if(CodeList.get(i).equals("QT")) {
//					code=new Code();
//					for(int j=0;j<riskCodeList.size();j++) {
//						code=riskCodeList.get(j);
//						code.setClassName("企财险（其他险））");
//						list.add(code);
//					}
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		riskMapMainVo.setCodeList(list);
//		return riskMapMainVo;
//		
//	}
//	/**
//	 * @author 周东旭
//	 * @param userInfo
//	 * return String*/
//	@RequestMapping(value="queryUserCode",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult queryCode(@RequestParam(value="userCode")String userCode) {
//		AjaxResult ajax=new AjaxResult();
//		String powerSQL=mapService.queryUserCode(userCode);
//		ajax.setData(powerSQL);
//		return ajax;
//	}
//	/**
//	 * @author 周东旭
//	 * 导出统计清单excal
//	 * */
//	@SuppressWarnings("deprecation")
//	@RequestMapping(value="exportExcal",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult exportExcal(@RequestBody RiskMapMainVo vo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		OutputStream output = null;
//		FTPUtil ftp =new FTPUtil();
//    	try {
//			//创建HSSFWorkbook对象(excel的文档对象)
//			@SuppressWarnings("resource")
//			HSSFWorkbook wkb = new HSSFWorkbook();
//			//建立新的sheet对象（excel的表单）
//			HSSFSheet sheet=wkb.createSheet("统计表");
//			//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//			HSSFRow row1=sheet.createRow(0);
//			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//			HSSFCell cell=row1.createCell(0);
//			//设置居中
//			HSSFCellStyle style = wkb.createCellStyle();
//			//替换poi版本3.1.1为3.16，修改调用居中为HorizontalAlignment.CENTER add by wangwenjie 2019/7/22
////			style.setAlignment(HorizontalAlignment.CENTER);
//			//暂时替换回3.1.1
//	    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		    // 设置字体
//	        HSSFFont font = wkb.createFont();
//	        //设置字体大小
//	        font.setFontHeightInPoints((short)11);
//			//字体加粗
//			//替换poi版本3.1.1为3.16，修改调用加粗为font.setBold(true) add by wangwenjie 2019/7/22
//			font.setBold(true);
////        	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//	        //在样式用应用设置的字体;  
//	         style.setFont(font);
//			//设置单元格内容
//			cell.setCellValue("标的查询统计表");
//			cell.setCellStyle(style);
//			//创建单元格并设置单元格内容
//			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//			sheet.addMergedRegion(new CellRangeAddress(0,1,0,13));
//			//在sheet里创建第二行
//			HSSFRow row2=sheet.createRow(2);
//			
//			HSSFCell cell0=row2.createCell(0);
//			cell0.setCellValue("投保单号");
//			sheet.setColumnWidth(cell0.getColumnIndex(), 256 * 20);
//			HSSFCell cell1=row2.createCell(1);
//			cell1.setCellValue("保单号");
//			sheet.setColumnWidth(cell1.getColumnIndex(), 256 * 20);
//			HSSFCell cell2=row2.createCell(2);
//			cell2.setCellValue("投保险类");
//			sheet.setColumnWidth(cell2.getColumnIndex(), 256 * 20);
//			HSSFCell cell3=row2.createCell(3);
//			cell3.setCellValue("投保险种");
//			sheet.setColumnWidth(cell3.getColumnIndex(), 256 * 20);
//			HSSFCell cell4=row2.createCell(4);
//			cell4.setCellValue("保险期间");
//			sheet.setColumnWidth(cell4.getColumnIndex(), 256 * 20);
//			HSSFCell cell5=row2.createCell(5);
//			row2.createCell(5).setCellValue("归属机构");
//			sheet.setColumnWidth(cell5.getColumnIndex(), 256 * 20);
//			HSSFCell cell6=row2.createCell(6);
//			row2.createCell(6).setCellValue("联共保标识");
//			sheet.setColumnWidth(cell6.getColumnIndex(), 256 * 20);
//			HSSFCell cell7=row2.createCell(7);
//			row2.createCell(7).setCellValue("联共保比例(%)");
//			sheet.setColumnWidth(cell7.getColumnIndex(), 256 * 20);
//			HSSFCell cell8=row2.createCell(8);
//			row2.createCell(8).setCellValue("总保额(万元)");
//			sheet.setColumnWidth(cell8.getColumnIndex(), 256 * 20);
//			HSSFCell cell9=row2.createCell(9);
//			row2.createCell(9).setCellValue("我司保额(万元)");
//			sheet.setColumnWidth(cell9.getColumnIndex(), 256 * 20);
//			HSSFCell cell10=row2.createCell(10);
//			row2.createCell(10).setCellValue("被保险人名称");
//			sheet.setColumnWidth(cell10.getColumnIndex(), 256 * 20);
//			HSSFCell cell11=row2.createCell(11);
//			row2.createCell(11).setCellValue("被保险人类型");
//			sheet.setColumnWidth(cell11.getColumnIndex(), 256 * 20);
//			HSSFCell cell12=row2.createCell(12);
//			row2.createCell(12).setCellValue("证件类型");
//			sheet.setColumnWidth(cell12.getColumnIndex(), 256 * 20);
//			HSSFCell cell13=row2.createCell(13);
//			row2.createCell(13).setCellValue("证件号码");
//			sheet.setColumnWidth(cell13.getColumnIndex(), 256 * 20);
//			List<MapMainByAddrVo> mapMainByAddrVoList=mapService.queryRiskMapMain(vo.getIds(),vo.getSumAmount(),vo.getPolicyno(),vo.getMapCode(),vo.getNowdate(),vo.getPowerSQL(),vo.getSumPay());
//			
//			if(mapMainByAddrVoList.size()>=20000) {
//				ajaxResult.setStatusText("加载数据超过两万条！请缩小查询范围");
//			}else {
//				for (int i = 0; i < mapMainByAddrVoList.size(); i++) {
//					HSSFRow row=sheet.createRow(i + 3);
//					row.createCell(0).setCellValue(mapMainByAddrVoList.get(i).getProposalNo());
//					row.createCell(1).setCellValue(mapMainByAddrVoList.get(i).getPolicyNo());
//					row.createCell(2).setCellValue(mapMainByAddrVoList.get(i).getClassCode());
//					row.createCell(3).setCellValue(mapMainByAddrVoList.get(i).getRiskCode());   
//					row.createCell(4).setCellValue(mapMainByAddrVoList.get(i).getStartDate()+"至"+mapMainByAddrVoList.get(i).getEndDate());
//					row.createCell(5).setCellValue(mapMainByAddrVoList.get(i).getComCode());
//					row.createCell(6).setCellValue(mapMainByAddrVoList.get(i).getCoinsFlag());
//					row.createCell(7).setCellValue(mapMainByAddrVoList.get(i).getCoinsRate().toString());
//					row.createCell(8).setCellValue(mapMainByAddrVoList.get(i).getSumAmount().toString());
//					row.createCell(9).setCellValue(mapMainByAddrVoList.get(i).getOurCoverage().toString());
//					row.createCell(10).setCellValue(mapMainByAddrVoList.get(i).getInsuredName());
//					row.createCell(11).setCellValue(mapMainByAddrVoList.get(i).getInsuredType());
//					row.createCell(12).setCellValue(mapMainByAddrVoList.get(i).getIdentifyType());
//					row.createCell(13).setCellValue(mapMainByAddrVoList.get(i).getIdentifyNumber());
//				}
//				// 存储到公共上传目录下
//	    		output =ftp.uploadFile("downloadExcel/statistics.xls");
//				ajaxResult.setData("/downloadExcel/statistics.xls");
//				wkb.write(output);
//				output.flush();
//			}
//    	} catch (Exception e) {
//			LOGGER.info( e.getMessage() ,e);
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally {
//            if (output != null) {
//                try {
//                	output.close();
//                } catch (Exception e2) {
//                	LOGGER.error(e2.getMessage(), e2);
//                }
//                
//            }
//            if(ftp!=null) {
//	            try {
//					ftp.close();
//				} catch (IOException e) {
//					LOGGER.info("关闭ftp异常：" + e.getMessage() ,e);
//				}
//            }
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：根据地址、保单号获取标的信息
//	 * @param RiskMapMainVo
//	 * @return RiskMapMainVo
//	 * @author 马军亮
//	 * @时间：2018-09-18 
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryAddress",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public RiskMapMainVo queryInsured(HttpServletRequest request,@RequestBody RiskMapMainVo riskMapMainVo){
//		
//	
////		List<ExchRateVo> ExchRateVoList = mapService.getExchRate();
////		for (ExchRateVo exchRateVo : ExchRateVoList) {
////				mapService.saveExchRate(exchRateVo, exchRateVo.getProposalNo(), exchRateVo.getAddressId());
////		}
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		
//		String userCode = userInfo.getUserCode();
//		
//		String comCode = userInfo.getComCode();
//		
//		String riskCode = userInfo.getRiskCode();
//		
//		String powerSQL=mapService.queryUserCode(userInfo.getUserCode());
//		
//		riskMapMainVo = mapService.queryAddress(riskMapMainVo, powerSQL);
//		
//		List<RiskMapAddressVo> riskMapAddressVoList = riskMapMainVo.getRiskMapAddressVoList();
//		
//		if (riskMapAddressVoList != null) {
//			for (RiskMapAddressVo riskMapAddressVo : riskMapAddressVoList) {
//				// 最后修改人中文翻译
//				if (StringUtils.isNotBlank(riskMapAddressVo.getOperatorCode())) {
//					riskMapAddressVo.setOperatorName(riskInsService.queryCodeCName("UserCode", riskMapAddressVo.getOperatorCode(),
//							userCode, comCode, riskCode));
//				}
//			}
//			
//			riskMapMainVo.setRiskMapAddressVoList(riskMapAddressVoList);
//		}
//		
//		return riskMapMainVo;
//	}
//	
//	/**
//	 * @功能：修改标的经纬度
//	 * @param RiskMapInsuredModify
//	 * @return AjaxResult
//	 * @author 马军亮
//	 * @时间：2018-09-20
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/updateLonLat",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult updateLonLat(HttpServletRequest request,@RequestBody RiskMapAddressModify riskMapAddressModify){
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		String userCode = userInfo.getUserCode();
//		AjaxResult ajaxResult = new AjaxResult();
//		riskMapAddressModify.setOperatorCode(userCode);
//		ajaxResult = mapService.updateLonLat(riskMapAddressModify);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：保存excell表中导入的信息
//	 * @return AjaxResult
//	 * @author liqiankun
//	 * @时间：2018-10-12
//	 */
//	@RequestMapping(value="/importMapInfo",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult importMapInfo(HttpServletRequest request,@RequestParam("file") MultipartFile file){
//		AjaxResult ajaxResult =new AjaxResult();
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//        if(!file.isEmpty()&&file.getOriginalFilename().indexOf("map")!=-1){  
//            try {
//            	//设置上下文，方便后来取到dao
//				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
//				ApplicationContextUtil.setApplicationContext(context);
//				//文件实际存储路径
////				String path = request.getSession().getServletContext().getRealPath("/upload");
//				// 获取存储地址
//				ResourceBundle bundle = ResourceBundle.getBundle("config.savePath",
//						Locale.getDefault());
//				
//				String path = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
//				//文件浏览路径
////				String projectUrl = request.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + 
////							request.getLocalPort() + request.getContextPath() + "/upload/save/riskexpert/";
//				String projectUrl = bundle.getString("saveTypePath")+"/save/riskmap/";
//				
//				//第二个参数需要注意,它是指标题索引的位置,可能你的前几行并不是标题,而是其他信息
//				//比如数据批次号之类的,关于如何转换成javaBean,具体参考配置信息描述
//				ExcelImportResult result = excelContext.readExcel("map", 2, file.getInputStream(),"save/riskmap",projectUrl);
//				//无错误 方可存入数据库
//				if(!result.hasErrors()) {
//					List<RiskMapFloodAddress> floodAddressList = result.getListBean();
//					if(floodAddressList == null || floodAddressList.size() == 0) {
//						ajaxResult.setStatus(2);
//						ajaxResult.setStatusText("表中不存在数据，请重新导入!"); 
//						return ajaxResult;
//					}
////					for(RiskMapFloodAddress floodAddress:floodAddressList){
////						expert.setValidStatus("1");
////						expert.setScore(BigDecimal.valueOf(0));
////						if (StringUtils.isBlank(expert.getUrl())) {
////							expert.setUrl(projectUrl + "default.jpg");
////						}
//						mapService.saveRiskMapFloodAddress(floodAddressList,userInfo);
////					}
//					ajaxResult.setStatus(1);
//				}else {
//					ajaxResult.setStatus(-1);
//					String[] errorExcelUrl = {result.getErrorFileNetUrl(),result.getErrorFileRealUrl()};
//					ajaxResult.setData(errorExcelUrl);
//				}
//			} catch (Exception e) {
//				LOGGER.info( e.getMessage() ,e);
//				e.printStackTrace();
//	        	ajaxResult.setStatus(0);
//	        	ajaxResult.setStatusText(e.getMessage());
//	        	throw new RuntimeException(e);
//			}
//
//        }else{
//        	ajaxResult.setStatus(0);
//        	ajaxResult.setStatusText("文件不存在");
//        }
//		return ajaxResult;
//	}
//	
//	@RequestMapping(value = "/deleteTempExcel",method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult deleteTempExcel(HttpServletRequest request,@RequestParam("url") String url) {
//		AjaxResult ajaxResult =new AjaxResult();
//		try {
//			if(StringUtils.isNotBlank(url)) {
//				File file=new File(url); 
//				if(file.exists()) {
//					file.delete();
//				}
//				ajaxResult.setStatus(1);
//			}
//		}catch (Exception e) {
//			LOGGER.info( e.getMessage() ,e);
//			e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setStatusText(e.getMessage());
//			throw new RuntimeException(e);
//		}
//		return ajaxResult;
//	}
//	
//	
//	/**
//	 * @功能：修改一条经纬度或插入一条水淹记录
//	 * @param riskFloodUpdateVo
//	 * @return AjaxResult
//	 * @author 王坤龙
//	 * @时间：2018-10-22
//	 * @修改或增加记录：
//	 */
//	@RequestMapping(value="/updateFloodLonLat",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult updateFloodLonLat(HttpServletRequest request,@RequestBody RiskMapFloodUpdateVo riskMapFloodUpdateVo){
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		String comCode = userInfo.getComCode();
//		String userCode = userInfo.getUserCode();
//		AjaxResult ajaxResult = new AjaxResult();
//		riskMapFloodUpdateVo.setOperatorCode(userCode);
//		riskMapFloodUpdateVo.setComCode(comCode);
//		ajaxResult = mapService.updateFloodLonLat(riskMapFloodUpdateVo);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：删除一条水淹记录，但没有删除这条数据的修改记录
//	 * @param mapId
//	 * @return AjaxResult
//	 * @author 王坤龙
//	 * @时间：2018-10-22
//	 * @删除记录
//	 */
//	@RequestMapping(value="/deleteFloodLonLat",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult deleteFloodLonLat(HttpServletRequest request,@RequestParam(value = "dangerId") String dangerId){
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.deleteFloodLonLat(dangerId);
//		return ajaxResult;
//	}
//	@RequestMapping(value="/updateDtvData",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public RiskMapMainVo updateDtvData(){
//		
//		mapService.updateDtvData();
// 		return null;
//	}
//	
//	@RequestMapping(value="/transferData",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult transferData(@RequestParam(value = "geometry") String geometry){
//		
//		System.out.println("success");
//		System.out.println(geometry);
//		
//		AjaxResult ajaxResult = new AjaxResult();
//
////       ajaxResult = mapService.downloadMapExcelData(geometry);
////		Workspace workspace = new Workspace();
////		MapControl mapControl = new MapControl();
////		
////		WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
////		Datasource datasource = new Datasource(EngineType.UDB);
////		workspaceConnectionInfo.setType(WorkspaceType.SMWU);
//////			String file = "C:/Users/Administrator/Desktop/map2.2/FXDT.smwu";
////		String  file = "F:/A_supermap/superMap_file/Dissovle/dissolveDatasetVector/data/dissovle.smwu";
////		
////		String  fileMif = "F:/A_supermap/superMap_file/Dissovle/dissolveDatasetVector/data/Dbeijing.mif";
////		
//////			String file = filePath.getString("filePath");
////		workspaceConnectionInfo.setServer(file);
////		workspace.open(workspaceConnectionInfo);			
//////			datasource = workspace.getDatasources().get("areakind"); 		
////		mapControl.getMap().setWorkspace(workspace);
////		datasource = workspace.getDatasources().get(0);
//////		
////		ImportSettingMIF importSettingMIF=  new ImportSettingMIF(fileMif,datasource);	
////		DataImport dataImport =new DataImport();
////		dataImport.getImportSettings().add(importSettingMIF);
////		dataImport.run();
////		
////		if (datasource == null) {
////            System.out.println("打开数据源失败");
////	    } else {
////	        System.out.println("数据源打开成功！");
////	    }
////		// ThiessenPolygon
////		DatasetVector dtv=(DatasetVector) datasource.getDatasets().get("ThiessenPolygon");
////		
////		DatasetType dataSetType = datasource.getDatasets().get("ThiessenPolygon").getType();
////		
////		String dtvNewName=datasource.getDatasets().getAvailableDatasetName("New_"+dtv.getName());
////		String[] fieldNames=new String[]{"bsc"};
////		
////		DissolveParameter dissolveParameter=new DissolveParameter();
////		 //设置融合类型     (MULTIPART :融合后组合, ONLYMULTIPART : 组合,SINGLE: 融合)
////		dissolveParameter.setDissolveType(DissolveType.MULTIPART);
//////		dissolveParameter.setDissolveType(DissolveType.ONLYMULTIPART);
//////		dissolveParameter.setDissolveType(DissolveType.SINGLE);
////		// 设置融合字段的名称的集合
////		dissolveParameter.setFieldNames(fieldNames);
////		// 融合容限
////		dissolveParameter.setTolerance(0.0000008338);	
////		// 矢量数据融合
////		DatasetVector datasetVector=GeneralizeAnalyst.dissolve(dtv, datasource, dtvNewName, 
////				dissolveParameter);
////		if (datasetVector==null) {
////			System.out.println("矢量数据集为空");
//////			return;
////		}
////		
////		mapControl.getMap().getLayers().add(datasetVector, true);
////		mapControl.getMap().refresh();
////		
////		dtv.close();
////		datasetVector.close();			
////		datasource.close();
////		workspaceConnectionInfo.dispose();
////	    workspace.dispose();
//		
//	    // 直接通过.udb 文件来 打开数据源
////	    String  fileUdb = "F:/A_supermap/superMap_file/Dissovle/dissolveDatasetVector/data/eq.udb";
////		DatasourceConnectionInfo Info = new DatasourceConnectionInfo(
////				fileUdb,"eq" ,"");
////		mapControl.getMap().setWorkspace(workspace);
////		Datasource Ds = workspace.getDatasources().open(Info);
////		if (Ds != null) {
////			System.out.println("打开数据源成功");
////			System.out.println(Ds.getDatasets().get(0).getName());
////		} else {
////			System.out.println("打开数据源失败");
////		}
////		Ds.close();
////		workspace.dispose();
//				
// 		return ajaxResult;
//	}
//	
//	@RequestMapping(value="/searchAddress",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public Map<String, String> searchAddress(HttpServletRequest request, @RequestParam(value = "lon") String lon,@RequestParam(value = "lat") String lat,@RequestParam(value = "locaKindFlag") String locaKindFlag){
//		
//		String client = request.getHeader("user-agent");
//		// 移动端请求处理
//		if (client.contains("Android") || client.contains("iPad")) {
//			System.out.println("===================Android==duan=======================");
//		}else if(client.contains("iPhone")){
//			System.out.println("===================iPhone==duan=======================");
//		}else{
//			System.out.println("===================web==duan======================");
//		}	
//		
//		Map<String, String> map = new HashMap<String, String>();
//				
//		map = mapService.searchAddress(lon,lat,locaKindFlag);
//				
// 		return map;
//	}
//	/*
//	 *  寻找所有的区域
//	 */	
//	@RequestMapping(value="/findAllArea",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<Province> findAllArea(){		
//		
//		List<Province> provinceList=new ArrayList<Province>();
//		
//		provinceList = mapService.findAllArea();
//				
// 		return provinceList;
//	}
//	/**
//	 * 	根据省市县来查询不同的信息，记性全模糊查询
//	 * areaFlag: 1  省，2  市，3  县
//	 * */
//	@RequestMapping(value="/findKindArea",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public Map<String, List<CurrentDistrict>> findKindArea(@RequestParam(value = "areaName") String areaName,@RequestParam(value = "areaFlag") String areaFlag){
//		
//		Map<String, List<CurrentDistrict>> map = new HashMap<String, List<CurrentDistrict>>();
//		
//		map = mapService.findKindArea(areaName,areaFlag);
//				
// 		return map;
//	}
//	/**
//	 * 测试查找省市区
//	 * */
//	@RequestMapping(value="/queryPCC",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<RiskDaddress> queryPCC(@RequestParam(value = "address") String address){
//		List<RiskDaddress> riskAddressList=riskInsService.queryPCC(address);
//		return riskAddressList;
//		
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcmain数据
//	 * @author 马军亮
//	 * @RequestParam businessNo
//	 * @return AjaxResult 
//	 * @日期：2019-1-28
//	 */
//	@RequestMapping(value="/saveDPrpMain",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveDPrpMain(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process process = null;
//			String[] command = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/del.prpcmain.20181212"}; 
//			process = Runtime.getRuntime().exec(command); 
////			int exitValue = process.waitFor(); 
////			System.out.println(exitValue);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			String line = ""; 
//			while ((line = input.readLine()) != null) { 
//				mapService.saveDayPrpMain(line, "D");
//			} 
//			input.close(); 
//			
//			System.out.println(new Date());
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcmain数据
//	 * @author 马军亮
//	 * @RequestParam businessNo
//	 * @return AjaxResult 
//	 * @日期：2019-1-28
//	 */
//	@RequestMapping(value="/saveIPrpMain",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveIPrpMain(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process processI = null; 
//			String[] commandI = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/inc.prpcmain.20181212"}; 
//			processI = Runtime.getRuntime().exec(commandI); 
////			int exitValueI = processI.waitFor(); 
////			System.out.println(exitValueI);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader inputI = new BufferedReader(new InputStreamReader(processI.getInputStream())); 
//			String lineI = ""; 
//			while ((lineI = inputI.readLine()) != null) { 
//				mapService.saveDayPrpMain(lineI, "I");
//			} 
//			inputI.close();
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcaddress表数据
//	 * @author 马军亮
//	 * @RequestParam businessNo
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveDPrpAddress",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveDPrpAddress(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process process = null; 
//			String[] command = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/del.prpcaddress.20181212"}; 
//			process = Runtime.getRuntime().exec(command); 
////			int exitValue = process.waitFor(); 
////			System.out.println(exitValue);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream())); 
//			String line = ""; 
//			while ((line = input.readLine()) != null) { 
//				mapService.saveDayPrpAddress(line, "D");
//			}
//			input.close(); 
//			
//			System.out.println(new Date());
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcaddress表数据
//	 * @author 马军亮
//	 * @RequestParam businessNo
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveIPrpAddress",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveIPrpAddress(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process processI = null; 
//			String[] commandI = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/inc.prpcaddress.20181212"}; 
//			processI = Runtime.getRuntime().exec(commandI); 
////			int exitValueI = processI.waitFor(); 
////			System.out.println(exitValueI);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader inputI = new BufferedReader(new InputStreamReader(processI.getInputStream()));
//			String lineI = ""; 
//			while ((lineI = inputI.readLine()) != null) { 
//				mapService.saveDayPrpAddress(lineI, "I");
//			} 
//			inputI.close();
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpccoins表数据
//	 * @author 马军亮
//	 * @RequestParam businessNo
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveDPrpCoins",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveDPrpCoins(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process process = null;
//			String[] command = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/del.prpccoins.20181212"}; 
//			process = Runtime.getRuntime().exec(command); 
////			int exitValue = process.waitFor(); 
////			System.out.println(exitValue);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream())); 
//			String line = ""; 
//			while ((line = input.readLine()) != null) { 
//				mapService.saveDayPrpCoins(line, "D");
//			} 
//			input.close(); 
//			
//			System.out.println(new Date());
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpccoins表数据
//	 * @author 马军亮
//	 * @RequestParam businessNo
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveIPrpCoins",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveIPrpCoins(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {		
//			Process processI = null;
//			String[] commandI = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/inc.prpccoins.20181212"}; 
//			processI = Runtime.getRuntime().exec(commandI); 
////			int exitValueI = processI.waitFor(); 
////			System.out.println(exitValueI);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader inputI = new BufferedReader(new InputStreamReader(processI.getInputStream())); 
//			String lineI = ""; 
//			while ((lineI = inputI.readLine()) != null) { 
//				mapService.saveDayPrpCoins(lineI, "I");
//			}
//			inputI.close(); 
//			
//			System.out.println(new Date());
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcinsured表数据
//	 * @author 马军亮
//	 * @RequestParam param
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveDPrpInsured",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveDPrpInsured(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process process = null; 
//			String[] command = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/del.prpcinsured.20181212"}; 
//			process = Runtime.getRuntime().exec(command); 
////			int exitValue = process.waitFor();
////			System.out.println(exitValue);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream())); 
//			String line = "";
//			while ((line = input.readLine()) != null) { 
//				mapService.saveDayPrpInsured(line, "D");
//			} 
//			input.close(); 
//			
//			System.out.println(new Date());
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("成功");
//		} catch(Exception e){
//			e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("失败");
//		}
//		return ajaxResult;
//	}
//	
//	
//	/**
//	 * @功能:每日承保更新prpcinsured表数据
//	 * @author 马军亮
//	 * @RequestParam param
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveIPrpInsured",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveIPrpInsured(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {			
//			Process processI = null; 
//			String[] commandI = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/inc.prpcinsured.20181212"}; 
//			processI = Runtime.getRuntime().exec(commandI); 
////			int exitValueI = processI.waitFor(); 
////			System.out.println(exitValueI);//返回0表示脚本之行成功 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader inputI = new BufferedReader(new InputStreamReader(processI.getInputStream())); 
//			String lineI = ""; 
//			while ((lineI = inputI.readLine()) != null) { 
//				mapService.saveDayPrpInsured(lineI, "I");
//	}
//			inputI.close(); 
//	
//			System.out.println(new Date());
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("成功");
//		} catch(Exception e){
//			e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("失败");
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcitemkind表数据
//	 * @author 马军亮
//	 * @RequestParam param
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveDPrpItem",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveDPrpItem(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process process = null; 
//			String[] command = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/del.prpcitemkind.20181212"}; 
//			process = Runtime.getRuntime().exec(command); 
////			int exitValue = process.waitFor(); 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream())); 
//			String line = ""; 
//			while ((line = input.readLine()) != null) { 
//				mapService.saveDayPrpItem(line, "D");
//			} 
//			input.close(); 
//			
//			System.out.println(new Date());
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("成功");
//		} catch(Exception e){
//			e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("失败");
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保更新prpcitemkind表数据
//	 * @author 马军亮
//	 * @RequestParam param
//	 * @return AjaxResult 
//	 * @日期：2019-1-29
//	 */
//	@RequestMapping(value="/saveIPrpItem",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult saveIPrpItem(){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			Process processI = null;
//			String[] commandI = new String[] {"/bin/sh", "-c", "awk -F'^' '{if(substr($1,2,1)~/G/ || substr($1,2,1)~/Q/ ||substr($1,2,1)~/T/) {print}}' /home/middleware/apphome/riskcontrol_file/201810161004/inc.prpcitemkind.20181212"}; 
//			processI = Runtime.getRuntime().exec(commandI); 
//			//将不符合要求的行号和内容一并输出 
//			BufferedReader inputI = new BufferedReader(new InputStreamReader(processI.getInputStream())); 
//			String lineI = ""; 
//			while ((lineI = inputI.readLine()) != null) { 
//				mapService.saveDayPrpItem(lineI, "I");
//			} 
//			inputI.close();
//			System.out.println(new Date());
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("成功");
//		} catch(Exception e){
//			e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("失败");
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能:每日承保数据处理
//	 * @author 马军亮
//	 * @RequestParam param
//	 * @return AjaxResult 
//	 * @日期：2019-2-13
//	 */
//	@RequestMapping(value="/everyDayProcess",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult everyDayProcess(@RequestParam(value = "param") String param){
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
////			saveDPrpMain();
////			saveIPrpMain();
////			saveDPrpAddress();
////			saveIPrpAddress();
////			saveDPrpCoins();
////			saveIPrpCoins();
////			saveDPrpInsured();
////			saveIPrpInsured();
////			saveDPrpItem();
////			saveIPrpItem();
//			Set<String> set = mapService.everyDayProcess();
//			for (String str : set) {  
//				mapService.createData(str);
//			} 
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("成功");
//		} catch(Exception e){
//			e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("失败");
//		}
//		return ajaxResult;
//	}
//	/**
//	 * @功能 台风黑点修改
//	 * @author 周东旭*/
//	@RequestMapping(value="/updateTyphoon",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult updateTyphoon(HttpServletRequest request,@RequestBody RiskMapTyphoonBlack vo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		ajaxResult=mapService.updateTyphoon(vo,userInfo);
//		return ajaxResult;
//	}
//	/**
//	 * @功能： 删除一条台风黑点数据
//	 * @author 周东旭*/
//	@RequestMapping(value="/deleteTyphoon",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult deleteTyphoon(HttpServletRequest request,@RequestParam("deleteId") String deleteId){
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.deleteTyphoon(deleteId);
//		return ajaxResult;
//	}
//	@RequestMapping(value="/queryDataone",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult queryDataone(){
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.queryDataone();
//		return ajaxResult;
//	}
//	/**
//	 * 向数据库中导入黑点数据
//	 * @author 周东旭*/
//	@RequestMapping(value="/importTyphoonBlack",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult importTyphoonBlack(HttpServletRequest request,@RequestParam("file") MultipartFile file){
//		AjaxResult ajaxResult =new AjaxResult();
////		ajaxResult = mapService.queryDataone();
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		
//		if(!file.isEmpty() && file.getOriginalFilename().indexOf("typhoonBlack")!=-1){
//			try {
//				//设置上下文，方便后来取到dao
//				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
//				ApplicationContextUtil.setApplicationContext(context);
//				// 获取存储地址
//				ResourceBundle bundle = ResourceBundle.getBundle("config.savePath",
//						Locale.getDefault());
//				
//				String path = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
//				//文件浏览路径
//				String projectUrl = bundle.getString("saveTypePath")+"/save/riskmap/";
//				//第二个参数需要注意,它是指标题索引的位置,可能你的前几行并不是标题,而是其他信息
//				//比如数据批次号之类的,关于如何转换成javaBean,具体参考配置信息描述
//				ExcelImportResult result = excelContext.readExcel("typhoonBlack", 2, file.getInputStream(),"save/riskmap",projectUrl);
//				//导入无错误进行下一步
//				if(!result.hasErrors()) {
//					List<RiskMapTyphoonBlack> riskMapTyphoonBlackList=result.getListBean();
//					if(riskMapTyphoonBlackList==null||riskMapTyphoonBlackList.size()==0) {
//						ajaxResult.setStatus(2);
//						ajaxResult.setStatusText("表中不存在数据，请重新导入!"); 
//						return ajaxResult;
//					}
//					mapService.saveRiskMapTyphoonBlack(userInfo,riskMapTyphoonBlackList);
//					ajaxResult.setStatus(1);
//					ajaxResult.setStatusText("数据导入成功!");
//				}else {
//					ajaxResult.setStatus(-1);
//					String[] errorExcelUrl = {result.getErrorFileNetUrl(),result.getErrorFileRealUrl()};
//					ajaxResult.setData(errorExcelUrl);
//				}
//			} catch (Exception e) {
//				LOGGER.info( e.getMessage() ,e);
//				e.printStackTrace();
//	        	ajaxResult.setStatus(0);
//	        	ajaxResult.setStatusText(e.getMessage());
//	        	throw new RuntimeException(e);
//			}
//		}else {
//			ajaxResult.setStatus(0);
//        	ajaxResult.setStatusText("文件不存在或传入的文件不符合条件");
//		}
//		return ajaxResult;
//	}
//	/*
//	 *  省市
//	 */	
//	@RequestMapping(value="/findProvince",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult findProvince(){		
//		AjaxResult ajaxResult = new AjaxResult();
//		
//		ajaxResult = mapService.findProvince();
// 		return ajaxResult;
//	}
//	/*
//	 *  省市
//	 */	
//	@RequestMapping(value="/queryCity",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult queryCity(@RequestParam(value = "upperCode") String upperCode){		
//		AjaxResult ajaxResult = new AjaxResult();
//		
//		ajaxResult = mapService.queryCity(upperCode);
// 		return ajaxResult;
//	}
//	/**
//	 * @功能：标的信息导出
//	 * @return AjaxResult
//	 * @author liqiankun
//	 * @时间：2019-03-15
//	 */
//	@RequestMapping(value="/exportStandard",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult exportStandard(@RequestBody RiskMapMainVo riskMapMainVo){
//		AjaxResult ajaxResult = new AjaxResult();
//		
//		ajaxResult=mapService.exportStandard(riskMapMainVo);
//		
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：测试新思维接口是否正常 测试接口，可删除
//	 * @param 
//	 * @return 
//	 * @author 王坤龙
//	 * @时间：2019-4-4 
//	 * @测试新四维获取坐标接口
//	 */
//	@RequestMapping(value="/querySiWei",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult querySiWei(){
//		RiskMapAddress riskMapAddress = new RiskMapAddress();
//		riskMapAddress.setAddressName("四维图新大厦");
////		HttpResponseAddressReset result = mapService.getLongAndLat_test(riskMapAddress);
//		String str = "TQEN201922050000000058";
//		AjaxResult result = mapService.createData(str);
//		
//		return result;
//	}
//	/**
//	 * @功能： 台风黑点统计总损失金额 总保费金额 总保险金额 总案件数量
//	 * @param 统计条件
//	 * @return
//	 * @author 周东旭
//	 * */
//	@RequestMapping(value="/sumTyphoon",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult sumTyphoon(@RequestBody RiskMapDangerVo vo){		
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.sumTyphoon(vo.getIds(),vo.getSql(),vo.getDangerFlag());
// 		return ajaxResult;
//	}
//		
//	/**
//	 * WZ_tfbhVo台风列表分页查询（实时台风和历史台风）
//	 * @author 崔凤志
//	 * @param wz_tfbhVo
//	 * @return
//	 */
//	@RequestMapping(value="/findWzTfbhBypage",method= RequestMethod.POST)
//	@ResponseBody  
//	public AjaxResult findWzTfbhBypage(@RequestBody WZ_tfbhVo wz_tfbhVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			WZ_tfbhResponse response = mapService.findWzTfbhBypage(wz_tfbhVo);
//			ajaxResult.setData(response);
//			ajaxResult.setStatusText("success");
//		}catch(Exception e) {
//			ajaxResult.setStatusText("error");
//			e.printStackTrace();
//		}
// 		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：iobjectjava 直接把后台的多个面数据转换成一个面数据
//	 * @param 
//	 * @return AjaxResult
//	 * @author liqiankun
//	 * @时间：20190515
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/returnGeometrist",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult  returnGeometrist(@RequestBody MapRequestInfoVo  mapRequestInfoVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.returnGeometrist(mapRequestInfoVo);
// 		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：根据台风编号查询历史台风信息并排序返回
//	 * @param tfbh
//	 * @return
//	 * @throws Exception
//	 * @author AnQingsen
//	 * @时间：2019-05-21
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryInfoTfbh", method = RequestMethod.GET)
//	@ResponseBody
//	public AjaxResult queryInfoTfbh(@RequestParam("tfbh") String tfbh) throws Exception{
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.queryLSTfbh(tfbh);
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：根据台风编号和时间查询台风预测表数据信息
//	 * @param tfbh
//	 * @param rqsj
//	 * @return
//	 * @throws Exception
//	 * @author AnQingsen
//	 * @时间： 2019-05-21
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryYblj", method=RequestMethod.GET)
//	@ResponseBody
//	public AjaxResult queryYbljForTimeAndTfbh(@RequestParam("tfbh") String tfbh, 
//			@RequestParam("ybsj") String ybsj) throws Exception{
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.queryYbljForTimeAndTfbh(tfbh, ybsj);	
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：用于根据预警信息和历史信息生成面数据和标的信息(手动生成)
//	 * @param tfbh
//	 * @param rqsj
//	 * @return
//	 * @throws Exception
//	 * @author liqiankun
//	 * @时间： 2019-05-21
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/returnTest",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult  returnTest() {
//		AjaxResult ajaxResult = new AjaxResult();
//		
//		mapWarnService.createRegionDataSet();
//		
//		return ajaxResult;
//	}
//	
//	
//	/**
//	 * @author 周东旭
//	 * @功能： 获取最新的预警推送序列号*/
//	@RequestMapping(value="/getMaxSerialNo",method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult getMaxSerialNo() {
//		AjaxResult ajaxResult = new AjaxResult();
//		//获取最新环境信息序号
//		String serialNo = mapService.getMaxSerialNo();
//		ajaxResult.setStatusText(serialNo);
//		return ajaxResult;
//	}
//	/**
//	 * @author 周东旭
//	 * @功能 获取台风名称和编号*/
//	@RequestMapping(value="/getTFBH",method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult getTFBH() {
//		AjaxResult ajaxResult = new AjaxResult();
//		//获取最新环境信息序号
//		List<WZ_tfbh> list = mapService.getTFBH();
//		ajaxResult.setData(list);;
//		return ajaxResult;
//	}
//	/**
//	 * @author 周东旭
//	 * @功能： 保存台风预警推送*/
//	@RequestMapping(value="/saveRiskWarningPush",method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult saveRiskWarningPush(@RequestBody RiskWarningPush wp) {
//		AjaxResult ajaxResult = new AjaxResult();
//		//获取最新环境信息序号
//		mapService.saveRiskWarningPush(wp);
//		ajaxResult.setStatus(1);
//		ajaxResult.setStatusText("保存成功");
//		return ajaxResult;
//	}
//	/**
//	 * @author 周东旭
//	 * @功能 预警推送查询功能*/
//	@RequestMapping(value="/queryRiskWarningPush",method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult queryRiskWarningPush(HttpServletRequest request,@RequestBody RiskWarningPush wp) {
//		AjaxResult ajaxResult = new AjaxResult();
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		ajaxResult=mapService.queryRiskWarningPush(wp,userInfo);
//		return ajaxResult;
//	}
//	/**
//	 * @author 周东旭
//	 * @功能  删除预警推送*/
//	@RequestMapping(value="/deleteWarningPush",method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult deleteWarningPush(@RequestParam("serialNo") Integer serialNo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult=mapService.deleteWarningPush(serialNo);
//		return ajaxResult;
//	}
//	/**
//	 * @author 周东旭
//	 * @功能 按照序号查询预警推送信息*/
//	 @RequestMapping(value="/queryByserialNo",method = {RequestMethod.POST,RequestMethod.GET})
//	 @ResponseBody
//	 public AjaxResult queryByserialNo(@RequestParam("serialNo") Integer serialNo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult=mapService.queryByserialNo(serialNo);
//		return ajaxResult;
//	 }
//	
//	/**
//	 * @功能 生成实时台风报告
//	 * @author 崔凤志
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="/createTyphoonWord", method=RequestMethod.POST)
//	@ResponseBody AjaxResult createTyphoonWordCurrent(HttpServletRequest req,@RequestBody TfWordReqVo  vo) {
//		UserInfo userInfo = (UserInfo) req.getAttribute("userInfo");
//		String userCode=userInfo.getUserCode();
//		AjaxResult ajaxResult = mapService.createTyphoonWordCurrent(vo.getTfbh(),vo.getImgData(),userCode,vo.getRainCode());
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能 生成预警台风报告
//	 * @author 崔凤志
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value="/createTyphoonWordYJ", method=RequestMethod.POST)
//	@ResponseBody AjaxResult createTyphoonWordYJ(HttpServletRequest req,@RequestBody TfWordReqVo  vo) {
//		UserInfo userInfo = (UserInfo) req.getAttribute("userInfo");
//		String userCode=userInfo.getUserCode();
//		AjaxResult ajaxResult = mapService.createTyphoonWordYJ(vo.getTfbh(),
//									vo.getImgData(),userCode,vo.getRainCode());
//		return ajaxResult;
//	}
//	
//	
//	
//	/**
//	 * @功能：根据台风编号查询台风影响标的 及 预测台风影响标的
//	 * @author AnQingsen
//	 * @param tfbh
//	 * @return
//	 * @throws Exception
//	 * @时间：2019-05-23
//	 * @修改记录:
//	 */
//	@RequestMapping(value="/queryTarget",method = RequestMethod.GET)
//	@ResponseBody
//	public AjaxResult queryTargetForTfbh(@RequestParam("tfbh") String tfbh, 
//			@RequestParam("target") String target) throws Exception{
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.queryTargetForTfbh(tfbh, target,null);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：根据台风编号查询台风影响标的 及 预测台风影响标的(大灾调度平台调用接口)
//	 * @author liqiankun
//	 * @param tfbh
//	 * @return
//	 * @throws Exception
//	 * @时间：20200403
//	 * @修改记录:
//	 */
//	@RequestMapping(value="/queryDangerTarget",method = RequestMethod.GET)
//	@ResponseBody
//	public AjaxResult queryDangerTarget(@RequestParam("tfbh") String tfbh, 
//			@RequestParam("target") String target,
//			@RequestParam("comCode") String comCode) throws Exception{
//		AjaxResult ajaxResult = new AjaxResult();
//		ajaxResult = mapService.queryTargetForTfbh(tfbh, target,comCode);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：根据台风编号查询降雨影响标的 及 预测降雨影响标的
//	 * @author liqiankun
//	 * @param tfbh
//	 * @return
//	 * @throws Exception
//	 * @时间：20200324
//	 * @修改记录:
//	 */
//	@RequestMapping(value="/queryRainTarget",method = RequestMethod.GET)
//	@ResponseBody
//	public AjaxResult queryRainTarget(@RequestParam("tfbh") String tfbh, 
//			@RequestParam("target") String target) throws Exception{
//		AjaxResult ajaxResult = new AjaxResult();
////		if(target.equals("RainLsTarget")){
////			tfbh = "RS20191212";
////		}else if(target.equals("RainYbTarget")){
////			tfbh = "RS2019122208";
////		}
//		ajaxResult = mapService.queryRainTarget(tfbh, target);
//		return ajaxResult;
//	}
//	/**
//	 * @author wkl
//	 * @功能：ceshi*/
//	@RequestMapping(value="/sumAmountLs",method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult sumAmountLs() {
//		AjaxResult ajaxResult = new AjaxResult();
//		//获取最新环境信息序号
//		ajaxResult = mapService.sumAmountLs();
//		return ajaxResult;
//	}
//
//	/**
//	 * 企财险数据查询
//	 * 根据标的地址信息 获取该标的点保费、费率等数据  数据返回单位为（万元） 赔付率单位（百分之xxx）
//	 * @author 崔凤志
//	 * @param address
//	 * @return
//	 */
//	@RequestMapping(value="/getActuarialDataByAddress",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult getActuarialDataByAddress(@RequestParam("locationX") String locationX,
//				@RequestParam("locationY") String locationY) {
//		AjaxResult result = new AjaxResult();
//		try {
//			result = mapService.getActuarialDataByAddress(locationX,locationY);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	@RequestMapping(value="/getQManagerByAdd",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult getQManagerByAdd(@RequestBody MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult result = new AjaxResult();
//		RiskMapRegionVo mapAddress = mapRequestInfoVo.getMapAddress();
//		String provinceName = mapAddress.getProvinceName();
//		String cityName = mapAddress.getCityName();
//		String countryName =  mapAddress.getCountryName();
//		String address="",businessName="";
//		if(null!=mapAddress){
////			address = mapAddress.getAddressName();
//			businessName = mapAddress.getBusinessName();
//			// 四个直辖市特殊处理
//			if("110000,120000,310000,500000".indexOf(mapAddress.getProAdCode().trim())>-1){
//				if("province".equals(mapAddress.getAddressName())){
//					address = provinceName;
//				}else {
//					address =  provinceName + countryName;
//				}
//			}else {
//				if("province".equals(mapAddress.getAddressName())){
//					address = provinceName;
//				}else if("city".equals(mapAddress.getAddressName())){
//					address = provinceName+cityName;
//				}else {
//					address = provinceName+cityName+countryName;
//				}
//				
//			}
//		}
//		
//		try {
//			result = mapService.getActuarialData(address,businessName);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * 标的查询企财险数据详情查询  数据返回单位为（万元） 赔付率单位（百分之xxx）
//	 * @param address
//	 * @param pageNo
//	 * @param pageSize
//	 * @return
//	 */
//	@RequestMapping(value="getActuarialDataInfoByAddress",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult getActuarialDataInfoByAddress(@RequestBody MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			ajaxResult = mapService.getActuarialDataInfoByAddress(mapRequestInfoVo);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * 工程险基本数据查询 
//	 * 根据标的地址信息 获取该标的点保费、费率等数据  数据返回单位为（万元） 赔付率单位（百分之xxx）
//	 * @author 崔凤志
//	 * @param address
//	 * @return
//	 */
//	@RequestMapping(value="getGManagerByAdd", method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult getGManagerActuarialDataByAdd(@RequestBody MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			ajaxResult = mapService.getGManagerActuarialDataByAdd(mapRequestInfoVo);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * 工程险详细数据查询
//	 * 根据标的地址信息 获取该标的点保费、费率等详细数据
//	 * @author 崔凤志
//	 * @param address
//	 * @param pageNo
//	 * @param pageSize 
//	 */
//	@RequestMapping(value="getGManagerInfoByAdd",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult getGManagerActuarialDataInfoByAdd(@RequestBody MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult ajaxResult =new AjaxResult();
//		try {
//			ajaxResult = mapService.getGManagerActuarialDataInfoByAdd(mapRequestInfoVo);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	} 
//	
//	
//	/**
//	 * @功能：根据气象局提供的台风信息来更新台风基本信息、实时路径和预报路径
//	 * @author liqiankun
//	 * @param 
//	 * @return
//	 * @throws Exception
//	 * @时间：20190903
//	 * @修改记录:
//	 */
//	@RequestMapping(value="/updateTyphoonInfo",method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult updateTyphoonInfo(@RequestBody String  returnString) {
//		AjaxResult ajaxResult = new AjaxResult();
//		//获取最新环境信息序号
////		ajaxResult = mapWarnService.updateTyphoonInfo(typhoonBaseInfo);
//		if(StringUtils.isNotBlank(returnString)){
//			// 将气象局中的数据转化成对象
//			TyphoonBaseInfo typhoonBaseInfo = JSON.parseObject(returnString, TyphoonBaseInfo.class);
//			System.out.println("success");
//		   /* 气象局数据组织并更新到数据库中*/
////			ajaxResult = this.updateTyphoonInfo(typhoonBaseInfo);
//			ajaxResult = mapWeatherService.updateTyphoonInfo(typhoonBaseInfo);
//		}
//		return ajaxResult;
//	}
//	
//	// 接收栅格数据文件接口
//	@RequestMapping(value="/getGridData",method= {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult getGridData(){
//		AjaxResult ajaxResult =new AjaxResult();
//		ajaxResult =mapWeatherService.gainWeatherRainGridData();
//		
//		return ajaxResult;
//	}
//	// 接收栅格数据文件接口
//	@RequestMapping(value="/testHttpPost",method= {RequestMethod.POST})
//	@ResponseBody
//	public AjaxResult testHttpPost(@RequestBody TyphoonBaseInfo typhoonBaseInfo){
//		AjaxResult ajaxResult =new AjaxResult();
//		
////		mapWeatherService.queryTyphoonBaseInfo();
//		
//		String returnCode =typhoonBaseInfo.getReturnCode();
//		String returnMessage = typhoonBaseInfo.getReturnMessage();
//		ajaxResult.setData(returnCode);
//		ajaxResult.setStatusText(returnMessage);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：获取气象局降雨面数据，预警情况， 测试
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20190930
//	 * @修改记录：
//	 */	
//	@RequestMapping(value="/getWeatherRainData",method= {RequestMethod.POST,RequestMethod.GET})
//	public AjaxResult getWeatherRainData(@RequestParam String rainDate) {
//		AjaxResult ajaxResult =new AjaxResult();
//		try {
//			SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyyMMddHHmmss");
//			Date date =sdf_YMD.parse(rainDate);
//			// 实况
//	//		Date date = new Date();
//				// 获取当天的数据
//	//		mapWeatherService.getWeatherRainData(date,0,8);
//	//		mapWeatherService.getWeatherRainData(rainBaseInfo.getDisplayFieldName());
//			// 预警未来十天的数据
//			mapWeatherService.gainWeatherRainYJData(date,10,8);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		return ajaxResult;
//	}
//	/**
//	 * @功能：获取气象局降雨面数据，实况情况， 测试
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20190930
//	 * @修改记录：
//	 */	
//	@RequestMapping(value="/getWeatherRainSKData",method= {RequestMethod.POST,RequestMethod.GET})
//	public AjaxResult getWeatherRainSKData(@RequestParam String rainDate,@RequestParam int hour) {
//		AjaxResult ajaxResult =new AjaxResult();
//		try {
//			SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyyMMddHHmmss");
//			Date date =sdf_YMD.parse(rainDate);
//			ajaxResult = mapWeatherService.gainWeatherRainData(date,0,hour);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：获取预警面数据
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20190930
//	 * @修改记录：
//	 */	
//	@RequestMapping(value="/getDataEarlyWarning",method= {RequestMethod.POST,RequestMethod.GET})
//	public AjaxResult getDataEarlyWarning(@RequestParam String startTime,@RequestParam String endTime) {
//		AjaxResult ajaxResult =new AjaxResult();
//		
////		mapWeatherService.getDataEarlyWarning("123");
////		mapWeatherService.gainWeatherTyphoonData("20190804000000","20190813000000");
//		mapWeatherService.gainWeatherTyphoonData(startTime,endTime);
////		mapWeatherService.getDataEarlyWarning("");
//		return ajaxResult;
//	}
//	
//	/**
//	 * @功能：获取气象局降雨面数据
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20190930
//	 * @修改记录：
//	 */	
//	@RequestMapping(value="/operateEarlyWarnRainArea",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult operateEarlyWarnRainArea(@RequestParam String flag) {
//		AjaxResult ajaxResult =new AjaxResult();
//		
//		ajaxResult = mapWeatherService.operateEarlyWarnRainArea(flag);
//		
//		return ajaxResult;
//	}
//	/**
//	 * @功能：获取未来第n天的气象局降雨面数据
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20191129
//	 * @修改记录：
//	 */	
//	@RequestMapping(value="/getWeatherNDayRainArea",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult getWeatherNDayRainArea(@RequestParam String flag,@RequestParam String day) {
//		AjaxResult ajaxResult =new AjaxResult();
//		
//		ajaxResult = mapWeatherService.gainWeatherNDayRainArea(flag,Integer.valueOf(day));
//		
//		return ajaxResult;
//	}
//	/**
//	 * @功能：来获取降雨的数据的影响的标的信息,以不同的降雨等级进行汇总
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20191129
//	 * @修改记录：
//	 */	
//	@RequestMapping(value="/operateRainArea",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult operateRainArea(@RequestParam String date) {
//		AjaxResult ajaxResult =new AjaxResult();
////		String date = "20191212";
//		mapWeatherService.operateRainArea(date);
////		ajaxResult = mapWeatherService.testWzCorporeLs();
//		return ajaxResult;
//	}
//	/**
//	 * @功能：获取超图数据集中表的名称
//	 * @param 
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20191126
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/getDataSetTableName",method= {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult getDataSetTableName(@RequestParam String name) {
//		AjaxResult ajaxResult =new AjaxResult();
//		String tableName = MapUtils.getDataSetTableName(name);
//		ajaxResult.setData(tableName);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：获取台风影响单个标的坐标信息（供大灾调度平台使用）
//	 * @param tfbh: 台风编号,  isHappen: 0（预计影响）1（已影响）,
//	 * comCode: 地区编码,coordinateType: 坐标类型(02坐标系/2000坐标系/84坐标系)
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryTySingleInfo",method= {RequestMethod.GET})
//	@ResponseBody
//	public List<DangerManageMapInfo> queryTySingleInfo(@RequestParam String tfbh,
//			@RequestParam String isHappen,@RequestParam String comCode,@RequestParam String coordinateType,
//			@RequestParam int pageNo,@RequestParam int pageSize) {
//		List<DangerManageMapInfo> dangerManageMapInfoList =new ArrayList<DangerManageMapInfo>();
//		dangerManageMapInfoList = mapService.queryTySingleInfo(tfbh,isHappen,comCode,coordinateType,pageNo,pageSize);
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：获取台风影响单个标的坐标信息的数量（供大灾调度平台使用）
//	 * @param tfbh: 台风编号,  isHappen: 0（预计影响）1（已影响）,
//	 * comCode: 地区编码
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200327
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryTySingleCount",method= {RequestMethod.GET})
//	@ResponseBody
//	public Integer queryTySingleCount(@RequestParam String tfbh,
//			@RequestParam String isHappen,@RequestParam String comCode) {
//		Integer count = mapService.queryTySingleCount(tfbh,isHappen,comCode);
//		return count;
//	}
//	/**
//	 * @功能：获取台风影响单个标的详细信息
//	 * @param tfbh: 台风编号,  isHappen: 0（预计影响）1（已影响）,
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryTyDetailInfo",method= {RequestMethod.GET})
//	@ResponseBody
//	public AjaxResult queryTyDetailInfo(@RequestParam String tfbh,
//			@RequestParam Integer addressId) {
//		AjaxResult ajaxResult =new AjaxResult();
//		ajaxResult = mapWarnService.queryTyDetailInfo(tfbh,addressId);
//		return ajaxResult;
//	}
//	/**
//	 * @功能：获取台风影响标的信息,获取大于一亿元保额的单子（供大灾调度平台使用）
//	 * @param tfbh: 台风编号,  isHappen: 0（预计影响）1（已影响）,
//	 * comCode: 地区编码,coordinateType: 坐标类型(02坐标系/2000坐标系/84坐标系)
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200428
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryTySubjectInfo",method= {RequestMethod.GET})
//	@ResponseBody
//	public List<DangerManageMapInfo> queryTySubjectInfo(@RequestParam String tfbh,
//			@RequestParam String isHappen,@RequestParam String comCode,@RequestParam String coordinateType,
//			@RequestParam int pageNo,@RequestParam int pageSize) {
//		List<DangerManageMapInfo> dangerManageMapInfoList =new ArrayList<DangerManageMapInfo>();
//		dangerManageMapInfoList = mapService.queryTySubjectInfo(tfbh,isHappen,comCode,coordinateType,pageNo,pageSize);
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：获取台风影响标的信息,根据保额的范围（供大灾调度平台使用）
//	 * @param tfbh: 台风编号,  isHappen: 0（预计影响）1（已影响）,
//	 * comCode: 地区编码,coordinateType: 坐标类型(02坐标系/2000坐标系/84坐标系)
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200510
//	 * @修改记录：
//	 */
//	@RequestMapping(value="/queryTyAffectInfo",method= {RequestMethod.GET})
//	@ResponseBody
//	public List<DangerManageMapInfo> queryTyAffectInfo(@RequestParam String tfbh,
//			@RequestParam String isHappen,@RequestParam String comCode,@RequestParam String coordinateType,
//			@RequestParam BigDecimal minCoverage,@RequestParam BigDecimal maxCoverage,
//			@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
//		List<DangerManageMapInfo> dangerManageMapInfoList =new ArrayList<DangerManageMapInfo>();
//		dangerManageMapInfoList = mapService.queryTyAffectInfo(tfbh,isHappen,comCode,coordinateType,minCoverage,maxCoverage,pageNo,pageSize);
//		return dangerManageMapInfoList;
//	}
//	
//	
//	
//}
//
//
//
//
//
//
