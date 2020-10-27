package com.picc.riskctrl.map.service.spring;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.picc.riskctrl.common.jpa.condition.Restrictions;
import com.picc.riskctrl.common.jpa.vo.Criteria;
import com.picc.riskctrl.common.response.RiskResponseVo;
import com.picc.riskctrl.common.util.BeanUtils;
import com.picc.riskctrl.common.util.DateUtils;
import com.picc.riskctrl.common.util.FileUtils;
import com.picc.riskctrl.common.util.MapUtils;
import com.picc.riskctrl.map.dao.TyphoonHistoryPathRepository;
import com.picc.riskctrl.map.dao.TyphoonLatestInfoRepository;
import com.picc.riskctrl.map.dao.WzCorporeLsPRepository;
import com.picc.riskctrl.map.dao.WzCorporeLsRepository;
import com.picc.riskctrl.map.dao.WzCorporeYjPRepository;
import com.picc.riskctrl.map.dao.WzCorporeYjRepository;
import com.picc.riskctrl.map.dao.WzTFYbljRepository;
import com.picc.riskctrl.map.po.TyphoonHistoryPath;
import com.picc.riskctrl.map.po.TyphoonHistoryPathId;
import com.picc.riskctrl.map.po.TyphoonLatestInfo;
import com.picc.riskctrl.map.po.WzCorporeLs;
import com.picc.riskctrl.map.po.WzCorporeLsId;
import com.picc.riskctrl.map.po.WzCorporeLsP;
import com.picc.riskctrl.map.po.WzCorporeLsPId;
import com.picc.riskctrl.map.po.WzCorporeYj;
import com.picc.riskctrl.map.po.WzCorporeYjP;
import com.picc.riskctrl.map.po.WzTFYblj;
import com.picc.riskctrl.map.po.WzTFYbljId;
import com.picc.riskctrl.map.service.facade.MapWarnService;
import com.picc.riskctrl.map.service.facade.MapWeatherService;
import com.picc.riskctrl.map.vo.RiskMapAddressField;
import com.picc.riskctrl.map.vo.rainRequest.AttributeVo;
import com.picc.riskctrl.map.vo.rainRequest.RainBaseInfo;
import com.picc.riskctrl.map.vo.rainRequest.RainPolygonInfo;
import com.picc.riskctrl.map.vo.response.Wz_Corporels_byMid;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonBaseInfo;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonOuterBaseInfo;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonPath;
import com.picc.riskctrl.map.vo.typhoonRequest.WzTFYbljBack;
import com.picc.riskctrl.map.vo.typhoonRequest.WzTFYbljIdBack;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Feature;
import com.supermap.data.FieldInfos;
import com.supermap.data.GeoRegion;
import com.supermap.data.Geometry;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.Toolkit;
import com.supermap.data.Workspace;

//import ins.framework.web.RiskResponseVo;
import lombok.extern.slf4j.Slf4j;

	/**
 * @author  作者 E-mail: lqk
 * @date 创建时间：2019年9月19日 上午10:40:50
 * @version 1.0 
 * @parameter 
 * @since  与气象局进行台风和降雨数据对接 
 * @return  */
//@Rpc
@Slf4j
@Service(value = "mapWeatherService")
public class MapWeatherServiceSpringImpl implements MapWeatherService{
	
//	public static final Logger log = LoggerFactory.getLogger("RISKCONTROLLOG");
	//降雨数据中面数据的名称
	public static final String dataEarlyWarnRain = "dataEarlyWarnRain";
	//降雨预警数据中面数据的名称
	public static final String dataEarlyWarnRainYJ = "dataEarlyWarnRainYJ";
	//预警信息 的面数据名称
	public static final String dataEarlyWarning = "dataEarlyWarning";
	// 点数据集名称
	private static final String riskmap_address_point="RISKMAP_ADDRESS_POINT";
	// 获取路径信息
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config.savePath",Locale.getDefault());
	//获取地图配置文件信息
	private static final ResourceBundle bundleMap = ResourceBundle.getBundle("config.map",Locale.getDefault());

	//	@Autowired
//	DatabaseDao databaseDao;
	
	@Autowired
	JdbcTemplate slaveJdbcTemplate;
	
	@Autowired
	MapWarnService mapWarnService;
	
	@Autowired
	private TyphoonLatestInfoRepository typhoonLatestInfoRepository;
	
	@Autowired
	private WzCorporeYjRepository wzCorporeYjRepository;
	
	@Autowired
	private WzCorporeYjPRepository wzCorporeYjPRepository;
	
	@Autowired
	private WzCorporeLsRepository wzCorporeLsRepository;
	
	@Autowired
	private WzCorporeLsPRepository wzCorporeLsPRepository;
	
	@Autowired
	private TyphoonHistoryPathRepository typhoonHistoryPathRepository;
	
	@Autowired
	private WzTFYbljRepository wzTFYbljRepository;
	
	
	
	
	
	/**
	 * @功能：功能一：获取气象局台风数据
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20190927
	 * @修改记录：
	 */	
	public RiskResponseVo gainWeatherTyphoonData(String startDate ,String endDate){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
			// 组织调用气象局接口的url地址
			String typhoonUrl=bundleMap.getString("typhoonWeatherUrl")+"&startTime="+startDate+"&endTime="+endDate;
			// 根据http请求调用台风接口，获取台风数据,需要重新组织 地址信息
			String returnString =MapUtils.getTyphoonDataByGet(typhoonUrl);
			//增加本地调用方式，读取文件内容
//			String returnString =FileUtils.readFileContent("C:/FileRun/typhoon20200802180000_20200804180000.txt");
			System.out.println(returnString);
			log.info(returnString);
			if(StringUtils.isNotBlank(returnString)){
				// 将气象局中的数据转化成对象
//				TyphoonBaseInfo typhoonBaseInfo = JSON.parseObject(returnString, TyphoonBaseInfo.class);
				TyphoonOuterBaseInfo typhoonOuterBaseInfo = JSON.parseObject(returnString, TyphoonOuterBaseInfo.class);
				System.out.println("success");
			   /* 气象局数据组织并更新到数据库中*/
//				ajaxResult = this.updateTyphoonInfo(typhoonBaseInfo);
				if(typhoonOuterBaseInfo!=null &&
						typhoonOuterBaseInfo.getData()!=null&&typhoonOuterBaseInfo.getData().getDs().size()>0){
					//将生成的台风内容,以文本形式存储下来
					String filePath  = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
					FileUtils.writeToFile(returnString,filePath+"/map/typhoon/typhoon"+startDate+"_"+endDate+".txt");
					ajaxResult = this.updateTyphoonInfo(typhoonOuterBaseInfo.getData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	// 查询所有基本信息
	public  List<TyphoonLatestInfo>   searchTyphoonBaseInfoList(List<TyphoonLatestInfo> typhoonLatestInfoList){
//		QueryRule queryRule =QueryRule.getInstance();
		Criteria<TyphoonLatestInfo> criteria =new Criteria<>();
		List<TyphoonLatestInfo> typhoonLatestInfos = null;
//		TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
		List<Object> objList  =new ArrayList<Object>();
		for (TyphoonLatestInfo typhoonLatestInfoVo :typhoonLatestInfoList){
			objList.add(typhoonLatestInfoVo.getTfbh());
		}
		if(objList!=null&&objList.size()>0){
//			queryRule.addIn("tfbh", objList);
//			typhoonLatestInfos = databaseDao.findAll(TyphoonLatestInfo.class, queryRule);
			
			criteria.add(Restrictions.in("tfbh", objList, true));
			typhoonLatestInfos = typhoonLatestInfoRepository.findAll(criteria);
		}
		if(objList!=null&&objList.size()>0){
		}else {
			typhoonLatestInfos= new ArrayList<TyphoonLatestInfo>();
		}
		return typhoonLatestInfos;
	}
    /**
	 * @功能：根据气象局提供的台风信息来更新台风基本信息、实时路径和预报路径
	 * @author liqiankun
	 * @param 
	 * @return
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public RiskResponseVo updateTyphoonInfo(TyphoonBaseInfo typhoonBaseInfo){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
			// 组织基本表信息
			List<TyphoonPath> typhoonPathList = typhoonBaseInfo.getDs();
			
			//去除重复数据
			List<TyphoonPath> typhoonPathListNewClear = new ArrayList<TyphoonPath>();
			Set<TyphoonPath> typhoonPathSet = new HashSet<TyphoonPath>();
			typhoonPathSet.addAll(typhoonPathList);
			typhoonPathListNewClear.addAll(typhoonPathSet);
			System.out.println("set==="+typhoonPathSet.size()+",list==="+typhoonPathListNewClear.size());
			
			List<TyphoonPath> typhoonPathListNew = new ArrayList<TyphoonPath>();
			for (TyphoonPath typhoonPath: typhoonPathListNewClear){
//				if("LEKIMA".equals(typhoonPath.getTYPH_Name())){
					/*将格林时间转换为北京时间，转换方式是格林时间+8h=北京时间,过滤掉不符合条件（不是中国气象局预报的数据）的台风数据*/
				
				String num_Nati = typhoonPath.getNum_Nati();
				int  length = 0;
				if(StringUtils.isNotBlank(num_Nati)){
					length = num_Nati.trim().length();
				}
				//气象局台风编号是四位
				if("BABJ".equals(typhoonPath.getBul_Center())&&length==4){
					typhoonPath = DateUtils.fromGreenToWorldTime(typhoonPath);
					typhoonPathListNew.add(typhoonPath);
				}
//				}
			}
//			List<TyphoonLatestInfo> typhoonLatestInfoList = this.organizeTyphoonInfo(typhoonPathListNew);
			List<TyphoonLatestInfo> typhoonLatestInfoList = this.organizeTyphoonInfo_new(typhoonPathListNew);
			// 根据主键进行去重
			typhoonLatestInfoList = this.clearTyphoonRepeatData(typhoonLatestInfoList);
			
			// 获取多个台风的信息
//			List<TyphoonLatestInfo> typhoonLatestInfoList = this.organizeTyphoonInfo(typhoonPathList);
			
//			databaseDao.saveAll(TyphoonLatestInfo.class, typhoonLatestInfoList);
			typhoonLatestInfoRepository.saveAll(typhoonLatestInfoList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	// 根据主键进行去重
	public List<TyphoonLatestInfo> clearTyphoonRepeatData(List<TyphoonLatestInfo> typhoonLatestInfoListOld){
		List<TyphoonLatestInfo> typhoonLatestInfoList =new ArrayList<TyphoonLatestInfo>();
		try {
			for (TyphoonLatestInfo typhoonLatestInfo: typhoonLatestInfoListOld){
				//去除重复数据
				List<TyphoonHistoryPath> typhoonHistoryPathLisClear = new ArrayList<TyphoonHistoryPath>();
				Set<TyphoonHistoryPath> typhoonHistoryPathSet = new HashSet<TyphoonHistoryPath>();
				
				List<TyphoonHistoryPath> typhoonHistoryPathList = typhoonLatestInfo.getTyphoonHistoryPathList();
				if(null!=typhoonHistoryPathList&&typhoonHistoryPathList.size()>0){
					typhoonHistoryPathSet.addAll(typhoonHistoryPathList);
					typhoonHistoryPathLisClear.addAll(typhoonHistoryPathSet);
				}
				// 
				List<WzTFYblj> wzTFYbljLisClear = new ArrayList<WzTFYblj>();
//				Set<WzTFYblj> wzTFYbljSet = new HashSet<WzTFYblj>();
				List<WzTFYblj> wzTFYbljList = typhoonLatestInfo.getWzTFYbljList();
//				if(null!=wzTFYbljList&&wzTFYbljList.size()>0){
//					wzTFYbljSet.addAll(wzTFYbljList);
//					wzTFYbljLisClear.addAll(wzTFYbljSet);
//				}
				wzTFYbljLisClear = this.clearRepeatWzTFYbljVo(wzTFYbljList);
//				wzTFYbljSet.addAll(typhoonLatestInfo.getWzTFYbljList());
				typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathLisClear);
				typhoonLatestInfo.setWzTFYbljList(wzTFYbljLisClear);
				typhoonLatestInfoList.add(typhoonLatestInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typhoonLatestInfoList;
	}
	// 对台风预警信息进行去重
	public List<WzTFYblj> clearRepeatWzTFYbljVo(List<WzTFYblj> wzTFYbljList){
		List<WzTFYblj> wzTFYbljLisClear = new ArrayList<WzTFYblj>();
		try {
			Set<WzTFYbljBack> wzTFYbljSet = new HashSet<WzTFYbljBack>();
			for(WzTFYblj wzTFYblj:wzTFYbljList){
				WzTFYbljIdBack idVo = new WzTFYbljIdBack();
				WzTFYbljBack wzTFYbljVo = new WzTFYbljBack();
				BeanUtils.copyProperties(idVo, wzTFYblj.getId());
				String  rqsj = idVo.getRqsj().substring(0,idVo.getRqsj().lastIndexOf("."));
				String  ybsj = idVo.getYbsj().substring(0,idVo.getYbsj().lastIndexOf("."));
				//
				idVo.setRqsj(rqsj);
				idVo.setYbsj(ybsj);
//				Datas.copySimpleObjectToTargetFromSource(wzTFYbljVo, wzTFYblj);
				BeanUtils.copyProperties(wzTFYbljVo, wzTFYblj);
				
				wzTFYbljSet.add(wzTFYbljVo);
			}
			//将去重之后的数据进行重新组织成原有类型
			for (WzTFYbljBack wzTFYbljVo:wzTFYbljSet){
				WzTFYbljId id = new WzTFYbljId();
				WzTFYblj wzTFYblj = new WzTFYblj();
				BeanUtils.copyProperties(id, wzTFYbljVo.getId());
//				Datas.copySimpleObjectToTargetFromSource(wzTFYblj, wzTFYbljVo);
				BeanUtils.copyProperties(wzTFYblj, wzTFYbljVo);
				wzTFYblj.setId(id);
				wzTFYbljLisClear.add(wzTFYblj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wzTFYbljLisClear;
	}
	/**
	 * @功能：用于组织台风基本信息、实时路径和预报路径信息
	 * 首先判断是否存在这条数据，存在则判断是属于实时路径还是预报路径，获取到台风发生时间
	 * @author liqiankun
	 * @param 
	 * @return
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public List<TyphoonLatestInfo> organizeTyphoonInfo(List<TyphoonPath> typhoonPathList){
		List<TyphoonLatestInfo>  typhoonLatestInfoList  = new ArrayList<TyphoonLatestInfo>();
		// 分别存储
		Map<String, Object> mapTarget =new HashMap<String, Object>();
//		Map<String, Map<String,Object>> mapObj =new HashMap<String, Map<String,Object>>();
		if(typhoonPathList!=null&&typhoonPathList.size()>0){
			for(TyphoonPath typhoonPath:typhoonPathList){
				// Num_Nati 国内编号
				if(mapTarget.containsKey(typhoonPath.getNum_Nati())){
					mapTarget = this.organizeTyphoonLsOrYjInfo(typhoonPath,mapTarget,true);
				}else {
					Map<String, Object> map =new HashMap<String, Object>();
//					// 组织台风基本信息
					mapTarget = this.organizeTyphoonLsOrYjInfo(typhoonPath,map,false);
				}
			}
			for(String key:mapTarget.keySet()){
				typhoonLatestInfoList.add((TyphoonLatestInfo)mapTarget.get(key));
			}
		}
		return typhoonLatestInfoList;
	}
	/**
	 * @功能：将台风主表已经子表的信息组织到map中，key值为台风编号
	 * @author liqiankun
	 * @param  typhoonPath 台风获取对象， mapTarget ：存储的map，
	 * 		   isExist：map中是否存在给key值，存在为true，不存在为false
	 * @return Map<String, Object>
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	Map<String, Object> organizeTyphoonLsOrYjInfo(TyphoonPath typhoonPath,Map<String, Object> mapTarget,boolean isExist){
		// map中存在该编号信息
		if(isExist){
			//获取基本信息
			TyphoonLatestInfo typhoonLatestInfo = (TyphoonLatestInfo)mapTarget.get(typhoonPath.getNum_Nati());
			//判断是预报还是历史信息,true为历史，false为预报
//			boolean flag =  MapUtils.compareDateSize(typhoonPath);
			//预报时效 "Validtime": "0",是实况，“6”6小时预报  ，这个字段只要值为0 就是实况，不为0 就是预报台风路径。
			boolean flag = false;
			if(StringUtils.isNotBlank(typhoonPath.getValidtime())){
				flag = "0".equals(typhoonPath.getValidtime().trim());
			}
			// 组织台风基本信息，如果ISACTIVE 为1 则更新标志位，如果是0 则不进行更新
//			typhoonLatestInfo = this.organizeTyphoonLatestInfo(typhoonPath);
			if("0".equals(typhoonPath.getISACTIVE())){
				typhoonLatestInfo.setIsActive(typhoonPath.getISACTIVE());
			}
			if(flag){
				// 如果是历史，则需要进行比较基本信息中的发生时间是否是该点发生之前
				typhoonLatestInfo = MapUtils.compareObjectDateSize(typhoonPath, typhoonLatestInfo);
				//组织台风历史信息
				TyphoonHistoryPath typhoonHistoryPath = this.organizeTyphoonHistoryPath(typhoonPath);
				if(typhoonHistoryPath!=null){
					//看该list是否存在
					if(typhoonLatestInfo.getTyphoonHistoryPathList()!=null&&
							typhoonLatestInfo.getTyphoonHistoryPathList().size()>0){
						//新增到list集合中
						typhoonLatestInfo.getTyphoonHistoryPathList().add(typhoonHistoryPath);
					}else {
						// 如果没有则需要新增
						List<TyphoonHistoryPath> typhoonHistoryPathList = new ArrayList<TyphoonHistoryPath>();
						typhoonHistoryPathList.add(typhoonHistoryPath);
						typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathList);
					}
				}
			}else{
				// 台风预报信息
				WzTFYblj  wzTFYblj =this.organizeWzTFYblj(typhoonPath);
				if(wzTFYblj!=null){
					//看该list是否存在
					if(typhoonLatestInfo.getWzTFYbljList()!=null&&
							typhoonLatestInfo.getWzTFYbljList().size()>0){
						//新增到list集合中
						typhoonLatestInfo.getWzTFYbljList().add(wzTFYblj);
					}else {
						// 如果没有则需要新增
						List<WzTFYblj> wzTFYbljList = new ArrayList<WzTFYblj>();
						wzTFYbljList.add(wzTFYblj);
						typhoonLatestInfo.setWzTFYbljList(wzTFYbljList);
					}
				}
				
			}
			mapTarget.put(typhoonPath.getNum_Nati(), typhoonLatestInfo);
			
		}else {
			// map中不存在该编号信息
			try {
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				Map<String, Object> map =new HashMap<String, Object>();
				// 组织台风基本信息，如果ISACTIVE 为1 则更新标志位，如果是0 则不进行更新
				TyphoonLatestInfo typhoonLatestInfo = this.organizeTyphoonLatestInfo(typhoonPath);
//				TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
//				typhoonLatestInfo.setTfbh(typhoonPath.getNum_Nati());
				//判断是预报还是历史信息,true为历史，false为预报
//				boolean flag =  MapUtils.compareDateSize(typhoonPath);
				boolean flag = false;
				if(StringUtils.isNotBlank(typhoonPath.getValidtime())){
					flag = "0".equals(typhoonPath.getValidtime().trim());
				}
				if(flag){
					// 台风发生时间
//					Date datetime = MapUtils.getTyphoonDate(typhoonPath,"SH");
//					if(datetime!=null){
//						typhoonLatestInfo.setTfDate(datetime);
//					}
					
//					typhoonLatestInfo.setTfDate(sdf.parse(typhoonPath.getDatetime()));
					// 台风历史信息
					List<TyphoonHistoryPath> typhoonHistoryPathList = new ArrayList<TyphoonHistoryPath>();
//					TyphoonHistoryPath typhoonHistoryPath = new TyphoonHistoryPath();
					TyphoonHistoryPath typhoonHistoryPath = this.organizeTyphoonHistoryPath(typhoonPath);
					if(typhoonHistoryPath!=null){
						typhoonHistoryPathList.add(typhoonHistoryPath);
						typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathList);
					}
				}else {
					// 台风预报信息
					List<WzTFYblj> wzTFYbljList = new ArrayList<WzTFYblj>();
					WzTFYblj wzTFYblj = this.organizeWzTFYblj(typhoonPath);
//					WzTFYblj wzTFYblj =new WzTFYblj();
//					WzTFYbljId  id =new WzTFYbljId();
//					wzTFYblj.setId(id);
					if(wzTFYblj!=null){
						wzTFYbljList.add(wzTFYblj);
						typhoonLatestInfo.setWzTFYbljList(wzTFYbljList);
					}
				}
				mapTarget.put(typhoonPath.getNum_Nati(), typhoonLatestInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mapTarget;
	}
	
	
	/**
	 * @功能：组织台风基本数据信息
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public  TyphoonLatestInfo organizeTyphoonLatestInfo(TyphoonPath typhoonPath){
		TyphoonLatestInfo typhoonLatestInfo =null;
		// 查询库中是否存在该信息
		typhoonLatestInfo = this.searchTyphoonBaseInfo(typhoonPath);
		// 台风发生时间,如果typhoonLatestInfo为null，则直接插入台风发生时间
		Date datetime = MapUtils.getTyphoonDate(typhoonPath,"SH");
		if(typhoonLatestInfo == null){
			typhoonLatestInfo = new TyphoonLatestInfo();
			typhoonLatestInfo.setIsActive(typhoonPath.getISACTIVE());
			// 台风发生时间
			if(datetime!=null){
				typhoonLatestInfo.setTfDate(datetime);
			}
		}
		// 如果存在该信息且该信息ISACTIVE 标志位为1 或者不存在该信息则进行ISACTIVE 标志位的更新
		if(typhoonLatestInfo!=null&&"1".equals(typhoonLatestInfo.getIsActive())){
			typhoonLatestInfo.setIsActive(typhoonPath.getISACTIVE());
		    Date dataTimeOld = typhoonLatestInfo.getTfDate();
		    if(dataTimeOld ==null){
		    	typhoonLatestInfo.setTfDate(datetime);
		    }
			if(datetime!=null&&dataTimeOld !=null){
				boolean flagDate = MapUtils.compareDateSize(dataTimeOld, datetime);
				//新的日期小于库中日期，则替换
				if(flagDate){
					typhoonLatestInfo.setTfDate(datetime);
				}
			}
		}
		//编号
		typhoonLatestInfo.setTfbh(typhoonPath.getNum_Nati());
		typhoonLatestInfo.setIsUse("13");
		/*暂设英文名称*/
		typhoonLatestInfo.setTfm(typhoonPath.getTYPH_Name());
		/*台风名称*/
		typhoonLatestInfo.setTfme(typhoonPath.getTYPH_Name());
		/*台风等级*/
		typhoonLatestInfo.setIsCompleted(typhoonPath.getTyph_Grade());
		// 与气象局对接标志
		typhoonLatestInfo.setId("weather");
		return typhoonLatestInfo;
	}
	/**
	 * @功能：查询台风基本信息
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public  TyphoonLatestInfo   searchTyphoonBaseInfo(TyphoonPath typhoonPath){
//		QueryRule queryRule =QueryRule.getInstance();
		
		Criteria<TyphoonLatestInfo> criteria = new Criteria<>();
		
		TyphoonLatestInfo typhoonLatestInfo = null;
//		int count = 0;
		// 国内编号
		String num_Nati = typhoonPath.getNum_Nati();
		if(StringUtils.isNotBlank(num_Nati)){
//			queryRule.addEqual("tfbh", num_Nati);
			criteria.add(Restrictions.eq("tfbh", num_Nati));
			Optional<TyphoonLatestInfo> optional  =typhoonLatestInfoRepository.findOne(criteria);
			typhoonLatestInfo = optional.isPresent()?optional.get():null;
		}
//		if(null!=queryRule.getRuleList()){
//			count = queryRule.getRuleList().size();
//		}
		// 加入条件存在
//		if(count>0){
////			typhoonLatestInfo = databaseDao.findUnique(TyphoonLatestInfo.class, queryRule);
//			Optional<TyphoonLatestInfo> optional  =typhoonLatestInfoRepository.findOne(criteria);
//			typhoonLatestInfo = optional.isPresent()?optional.get():null;
//		}
		return typhoonLatestInfo;
	}
	/**
	 * @功能：通过台风编号--查询台风基本信息
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20200628
	 * @修改记录:
	 */
	public  TyphoonLatestInfo   searchTyphoonBaseInfoByTfbh(String  tfbhCode){
//		QueryRule queryRule =QueryRule.getInstance();
		
		Criteria<TyphoonLatestInfo> criteria=new Criteria<>();
		
		TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
//		int count = 0;
		// 国内编号
		if(StringUtils.isNotBlank(tfbhCode)){
//			queryRule.addEqual("tfbh", tfbhCode);
			criteria.add(Restrictions.eq("tfbh", tfbhCode));
			Optional<TyphoonLatestInfo> optional  =typhoonLatestInfoRepository.findOne(criteria);
			typhoonLatestInfo = optional.isPresent()?optional.get():null;
//			typhoonLatestInfo = databaseDao.findUnique(TyphoonLatestInfo.class, queryRule);
		}
//		if(null!=queryRule.getRuleList()){
//			count = queryRule.getRuleList().size();
//		}
		// 加入条件存在
//		if(count>0){
//			typhoonLatestInfo = databaseDao.findUnique(TyphoonLatestInfo.class, queryRule);
//		}
		return typhoonLatestInfo;
	}
	
	/**
	 * @功能：组织台风历史数据信息,组织信息的时候，
	 * 需要判断表中是否存在该信息，如果存在则不能进行添加
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public  TyphoonHistoryPath organizeTyphoonHistoryPath(TyphoonPath typhoonPath){
		TyphoonHistoryPath typhoonHistoryPath = null;
		// 查询出该条数据是否存在,如果存在则不添加到list中，不存在则添加到list集合中
		typhoonHistoryPath =  this.searchTyphoonHistoryPath(typhoonPath);
		if(typhoonHistoryPath !=null){ 
			return null;
		}
		typhoonHistoryPath = new  TyphoonHistoryPath();
		TyphoonHistoryPathId id =new TyphoonHistoryPathId();
		id.setTfbh(typhoonPath.getNum_Nati());
//		typhoonHistoryPath.setTfbh(typhoonPath.getNum_Nati());
		/*台风发生时间*/
		Date rqsj = MapUtils.getTyphoonDate(typhoonPath,"SH");
		if(rqsj!=null){
			id.setRqsj(rqsj);
		}
		typhoonHistoryPath.setId(id);
		typhoonHistoryPath.setJd(typhoonPath.getLon());
		typhoonHistoryPath.setWd(typhoonPath.getLat());
		// 中心气压
		typhoonHistoryPath.setZxqy(MapUtils.judgeValueIsZero(typhoonPath.getPRS()));
		// 中心风速
		typhoonHistoryPath.setZxfs(MapUtils.judgeValueIsZero(typhoonPath.getWIN_S_Conti_Max()));
		//移动速度
		typhoonHistoryPath.setYdsd(MapUtils.judgeValueIsZero(typhoonPath.getMoSpeed_Futrue()));
		//移动方向
		typhoonHistoryPath.setYdfx(MapUtils.judgeValueIsZero(typhoonPath.getMoDir_Future()));
		/*最大阵风风速*/
		typhoonHistoryPath.setWIN_S_Gust_Max(MapUtils.judgeValueIsZero(typhoonPath.getWIN_S_Gust_Max()));
		/*未来趋势*/ 
		typhoonHistoryPath.setTrend_Futrue(MapUtils.judgeValueIsZero(typhoonPath.getTrend_Futrue()));
		/*台风强度 */
		typhoonHistoryPath.setTyph_Intsy(MapUtils.judgeValueIsZero(typhoonPath.getTyph_Intsy()));
		/*台风三个等级的四个方位半径*/
		typhoonHistoryPath.setRadius7_en(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear1_WING_A7()));
		typhoonHistoryPath.setRadius7_wn(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear2_WING_A7()));
		typhoonHistoryPath.setRadius7_ws(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear3_WING_A7()));
		typhoonHistoryPath.setRadius7_es(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear4_WING_A7()));
		typhoonHistoryPath.setRadius10_en(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear1_WING_A10()));
		typhoonHistoryPath.setRadius10_wn(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear2_WING_A10()));
		typhoonHistoryPath.setRadius10_ws(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear3_WING_A10()));
		typhoonHistoryPath.setRadius10_es(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear4_WING_A10()));
		typhoonHistoryPath.setRadius12_en(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear1_WING_A12()));
		typhoonHistoryPath.setRadius12_wn(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear2_WING_A12()));
		typhoonHistoryPath.setRadius12_ws(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear3_WING_A12()));
		typhoonHistoryPath.setRadius12_es(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear4_WING_A12()));
		
		typhoonHistoryPath.setIsuse("13");
		//与气象局台风对接的标志
		typhoonHistoryPath.setDepict("1");
		return typhoonHistoryPath;
	}
	/**
	 * @功能：组织台风预警数据信息，
     * 需要判断表中是否存在该信息，如果存在则不能进行添加
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public  WzTFYblj organizeWzTFYblj(TyphoonPath typhoonPath){
		WzTFYblj wzTFYblj = null;
		// 查询出该条数据是否存在,如果存在则不添加到list中，不存在则添加到list集合中
		wzTFYblj =  this.searchWzTFYblj(typhoonPath);
		if(wzTFYblj !=null){ 
			return null;
		}
		wzTFYblj =new WzTFYblj();
		WzTFYbljId  id =new WzTFYbljId();
		id.setTfbh(typhoonPath.getNum_Nati());
		id.setTm("中国");
		Date ybsj = MapUtils.getTyphoonDate(typhoonPath,"SH");
		Date rqsj = MapUtils.getTyphoonDate(typhoonPath,"YB");
		if(ybsj!=null){
//			id.setYbsj((Timestamp)ybsj);
			id.setYbsj(new Timestamp(ybsj.getTime()));
		}
		if(rqsj!=null){
//			id.setRqsj((Timestamp)rqsj);
			id.setRqsj(new Timestamp(rqsj.getTime()));
		}
		wzTFYblj.setId(id); 
		wzTFYblj.setJd(typhoonPath.getLon());
		wzTFYblj.setWd(typhoonPath.getLat());
		wzTFYblj.setIsUse("13");
		//与气象局台风对接的标志
		wzTFYblj.setDepict("1");
		// 中心气压
		wzTFYblj.setZxqy(MapUtils.judgeValueIsZero(typhoonPath.getPRS()));
		// 中心风速
		wzTFYblj.setZxfs(MapUtils.judgeValueIsZero(typhoonPath.getWIN_S_Conti_Max()));
		//移动方向
		wzTFYblj.setYdfx(MapUtils.judgeValueIsZero(typhoonPath.getMoDir_Future()));
		/*最大阵风风速*/
		wzTFYblj.setWIN_S_Gust_Max(MapUtils.judgeValueIsZero(typhoonPath.getWIN_S_Gust_Max()));
		/*未来趋势*/ 
		wzTFYblj.setTrend_Futrue(MapUtils.judgeValueIsZero(typhoonPath.getTrend_Futrue()));
		/*台风强度 */
		wzTFYblj.setTyph_Intsy(MapUtils.judgeValueIsZero(typhoonPath.getTyph_Intsy()));
		/*由于气象局提供的预警数据中半径没有提供，所以默认七级半径为200,七级半径最大 。 modify by liqiankun 20200803 begin*/
		wzTFYblj.setRadius7_en("200");
		wzTFYblj.setRadius7_wn("200");
		wzTFYblj.setRadius7_ws("200");
		wzTFYblj.setRadius7_es("200");
		/*由于气象局提供的预警数据中半径没有提供，所以默认七级半径为200,七级半径最大 。 modify by liqiankun 20200803 end*/
		/*台风三个等级的四个方位半径*/
//		wzTFYblj.setRadius7_en(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear1_WING_A7()));
//		wzTFYblj.setRadius7_wn(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear2_WING_A7()));
//		wzTFYblj.setRadius7_ws(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear3_WING_A7()));
//		wzTFYblj.setRadius7_es(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear4_WING_A7()));
		wzTFYblj.setRadius10_en(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear1_WING_A10()));
		wzTFYblj.setRadius10_wn(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear2_WING_A10()));
		wzTFYblj.setRadius10_ws(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear3_WING_A10()));
		wzTFYblj.setRadius10_es(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear4_WING_A10()));
		wzTFYblj.setRadius12_en(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear1_WING_A12()));
		wzTFYblj.setRadius12_wn(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear2_WING_A12()));
		wzTFYblj.setRadius12_ws(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear3_WING_A12()));
		wzTFYblj.setRadius12_es(MapUtils.judgeValueIsZero(typhoonPath.getRadiu_Bear4_WING_A12()));
		return wzTFYblj;
	}
	/**
	 * @功能：查询台风历史信息是否存在
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191011
	 * @修改记录：
	 */
	public TyphoonHistoryPath searchTyphoonHistoryPath(TyphoonPath typhoonPath){
		TyphoonHistoryPath typhoonHistoryPath = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			QueryRule queryRule = QueryRule.getInstance();
			
			Criteria<TyphoonHistoryPath> criteria=new Criteria<>();
			
			if(StringUtils.isNotBlank(typhoonPath.getNum_Nati())){
				typhoonHistoryPath = new TyphoonHistoryPath();
//				queryRule.addEqual("id.tfbh", typhoonPath.getNum_Nati().trim());
				criteria.add(Restrictions.eq("id.tfbh", typhoonPath.getNum_Nati().trim()));
				/*发生时间*/
				Date rqsj = MapUtils.getTyphoonDate(typhoonPath,"SH");
				if(null!=rqsj){
//				queryRule.addEqual("rqsj", rqsj);
//					String sql =" this_.rqsj = to_date('"+formatter.format(rqsj)+"','yyyy-mm-dd hh24:mi:ss')"; 
//					queryRule.addSql(sql);
					
					String  rqsjValue = "to_date('"+formatter.format(rqsj)+"','yyyy-mm-dd hh24:mi:ss')";
					criteria.add(Restrictions.eq("id.rqsj", rqsjValue));
					
//					typhoonHistoryPath = databaseDao.findUnique(TyphoonHistoryPath.class, queryRule);
					Optional<TyphoonHistoryPath> optional = typhoonHistoryPathRepository.findOne(criteria);
					typhoonHistoryPath = optional.isPresent()?optional.get():null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typhoonHistoryPath;
	}
	/**
	 * @功能：查询台风预警信息是否存在
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191011
	 * @修改记录：
	 */
	public WzTFYblj searchWzTFYblj(TyphoonPath typhoonPath){
		WzTFYblj wzTFYblj = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			QueryRule queryRule = QueryRule.getInstance();
			Criteria<WzTFYblj>  criteria = new Criteria<>();
			
			if(StringUtils.isNotBlank(typhoonPath.getNum_Nati())){
//				queryRule.addEqual("id.tfbh", typhoonPath.getNum_Nati().trim());
//				queryRule.addEqual("id.tm", "中国");
				
				criteria.add(Restrictions.eq("id.tfbh", typhoonPath.getNum_Nati().trim()));
				criteria.add(Restrictions.eq("id.tm", "中国"));
				/*发生时间*/
				Date ybsj = MapUtils.getTyphoonDate(typhoonPath,"SH");
				/*预报时间*/
				Date rqsj = MapUtils.getTyphoonDate(typhoonPath,"YB");
				if(null!=ybsj&&null!=rqsj){
//					queryRule.addEqual("id.ybsj", ybsj);
//					queryRule.addEqual("id.rqsj", rqsj);
//					String ybsql =" this_.ybsj = to_date('"+formatter.format(ybsj)+"','yyyy-mm-dd hh24:mi:ss')";
//					String rqsql =" this_.rqsj = to_date('"+formatter.format(rqsj)+"','yyyy-mm-dd hh24:mi:ss')";
//					queryRule.addSql(ybsql);
//					queryRule.addSql(rqsql);
//					wzTFYblj = databaseDao.findUnique(WzTFYblj.class, queryRule);
					
					String ybsqlValue =" to_date('"+formatter.format(ybsj)+"','yyyy-mm-dd hh24:mi:ss')";
					String rqsqlValue =" to_date('"+formatter.format(rqsj)+"','yyyy-mm-dd hh24:mi:ss')";
					criteria.add(Restrictions.eq("id.ybsj", ybsqlValue));
					criteria.add(Restrictions.eq("id.rqsj", rqsqlValue));
					Optional<WzTFYblj> optional = wzTFYbljRepository.findOne(criteria);
					wzTFYblj = optional.isPresent()?optional.get():null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wzTFYblj;
	}
	
	/**
	 * @功能：功能二：获取气象局降雨面数据,实况数据
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20190930
	 * @修改记录：
	 */	
	public RiskResponseVo gainWeatherRainData(Date newDate,int day,int hour){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
//			String url=bundleMap.getString("rainWeatherUrl");
//			/*获取某一日期的下几天天几点零分的日期,yyyyMMddHH*/
//			String dataTime=  DateUtils.getNextDateEightHour(newDate,day,hour);
//			String insertTimeForHis=  DateUtils.getCurrentDateFormat(new Date());
//			// 存储到数据集中的日期格式是yyyyMMdd
//			String dataTimeNew = DateUtils.getNextDateFormat(newDate,0);
//			//组织参数的json字符串
//			String  jsonString = this.generateJson("obs",dataTime).toString();
//			/*发送Http post请求,调用气象局台风接口*/
//			String responseJson =MapUtils.getRainDataByHttpPost(jsonString, url);
//			// 当接口调用不通的时候使用的临时数据
//			String sourceJson= "\"rings\"";
//			String targetJson= "\"type\": \"Polygon\",\"coordinates\"";
//			// 增加日期格式替换，在idesktop中增加数据,以及操作的时间
//			String sourceJsonTwo= "\"attributes\":{";
//			String targetJsonTwo= "\"attributes\":{ \"dataTime\":\""+dataTimeNew+"\",\"insertTimeForHis\":\""+insertTimeForHis+"\",";
//			// 将气象局数据替换成超图所需的数格式
//			responseJson =  responseJson.replace(sourceJson, targetJson);
//			responseJson =  responseJson.replace(sourceJsonTwo, targetJsonTwo);	
			
			//增加本地调用方式，读取文件内容
			String responseJson =FileUtils.readFileContent("D:/FileWorkspace/map/Rain/RainSH_2020062808.txt");
			// 将气象局中的数据转化成对象
			RainBaseInfo rainBaseInfo = JSON.parseObject(responseJson, RainBaseInfo.class);
			if(null!=rainBaseInfo.getFeatures()&&rainBaseInfo.getFeatures().size()>0){
				/*对降雨文件信息进行备份*/
//				String filePath  = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
//				FileUtils.writeToFile(responseJson,filePath+"/map/Rain/RainSH_"+dataTime+".txt");
				// key: 为日期，value：TyphoonLatestInfo　为台风基本信息对象
				Map<String, TyphoonLatestInfo> map = new HashMap<String, TyphoonLatestInfo>();
				for (RainPolygonInfo rainPolygonInfo:rainBaseInfo.getFeatures()){
//					if(rainPolygonInfo.getAttributes().getGRIDCODE() == "9999"){
					if( "9999".equals(rainPolygonInfo.getAttributes().getGRIDCODE())){
						rainPolygonInfo.getAttributes().setGRIDCODE("0");
					}
					String dataTime = rainPolygonInfo.getAttributes().getDataTime();
					if(!map.containsKey(dataTime)){
						TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
						typhoonLatestInfo.setTfbh("RS"+dataTime);
						typhoonLatestInfo.setTfm("降雨");
						typhoonLatestInfo.setTfDate(DateUtils.strYMDNoTransferDate(dataTime));
						// 1: 未处理，2:已处理,初始化为1（未处理）
						typhoonLatestInfo.setIsUse("1");
						map.put(dataTime, typhoonLatestInfo);
					}
				}
				List<TyphoonLatestInfo>  typhoonLatestInfoList = new ArrayList<TyphoonLatestInfo>();
				for (Map.Entry<String, TyphoonLatestInfo> m : map.entrySet()){
					typhoonLatestInfoList.add(m.getValue());
				}
				//将降雨信息保存到台风基本信息表中
				if(null!=typhoonLatestInfoList&&typhoonLatestInfoList.size()>0){
//					databaseDao.saveAll(TyphoonLatestInfo.class, typhoonLatestInfoList);
					typhoonLatestInfoRepository.saveAll(typhoonLatestInfoList);
				}
				/* 气象局数据组织并更新到数据库中*/
				ajaxResult = this.operateEarlyWarn(rainBaseInfo,dataEarlyWarnRain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	/**
	 * @功能：功能二-2：获取气象局降雨预警面数据，看当前时间是6,8,12： 8点；16,20:20点。
	 * @param  
	 * @return void
	 * @author liqiankun
	 * @时间：20190930
	 * @修改记录：
	 */	
	public RiskResponseVo gainWeatherRainYJData(Date newDate,int day,int hour){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
//			String url=bundleMap.getString("rainWeatherUrl");
			RainBaseInfo rainBaseInfoNew = new RainBaseInfo();
			List<RainPolygonInfo>  rainPolygonInfoList = new ArrayList<RainPolygonInfo>();
			String dataTimeDir=  DateUtils.getNextDateEightHourMinu(newDate,0,hour);
			for (int  i=1;i<=day;i++){
				/*获取某一日期的下几天天几点零分的日期*/
				String dataTime=  DateUtils.getNextDateEightHourMinu(newDate,i,hour);
//				String insertTimeForHis=  DateUtils.getCurrentDateFormat(new Date());
//				// 存储到数据集中的日期格式是yyyyMMdd
//				String dataTimeNew = DateUtils.getNextDateFormat(newDate,i);
//				//组织参数的json字符串
//				String  jsonString = this.generateJson("fst",dataTime).toString();
//				/*发送Http post请求,调用气象局台风接口*/
//				String responseJson =MapUtils.getRainDataByHttpPost(jsonString, url);
//				// 当接口调用不通的时候使用的临时数据
////				String responseJson = rainData;
//				String sourceJson= "\"rings\"";
//				String targetJson= "\"type\": \"Polygon\",\"coordinates\"";
//				// 增加日期格式替换，在idesktop中增加数据,以及操作的时间
//				String sourceJsonTwo= "\"attributes\":{";
//				String targetJsonTwo= "\"attributes\":{ \"dataTime\":\""+dataTimeNew+"\",\"insertTimeForHis\":\""+insertTimeForHis+"\",";
//				// 将气象局数据替换成超图所需的数格式
//				responseJson =  responseJson.replace(sourceJson, targetJson);
//				responseJson =  responseJson.replace(sourceJsonTwo, targetJsonTwo);
				
				//增加本地调用方式，读取文件内容
				String responseJson =FileUtils.readFileContent("D:/FileWorkspace/map/RainYJ_"+dataTimeDir+"/RainYJ_"+dataTime+".txt");
				
				// 将气象局中的数据转化成对象
				RainBaseInfo rainBaseInfo = JSON.parseObject(responseJson, RainBaseInfo.class);
				String filePath  = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
				if(null!=rainBaseInfo&&null!=rainBaseInfo.getFeatures()&&rainBaseInfo.getFeatures().size()>0){
					
					FileUtils.writeToFile(responseJson,filePath+"/map/Rain/RainYJ_"+dataTime+".txt");
					rainPolygonInfoList.addAll(rainBaseInfo.getFeatures());
//					Datas.copySimpleObjectToTargetFromSource(rainBaseInfoNew, rainBaseInfo);
					BeanUtils.copyProperties(rainBaseInfoNew, rainBaseInfo);
				}
			}
			if(null!=rainPolygonInfoList&&rainPolygonInfoList.size()>0){
				rainBaseInfoNew.setFeatures(rainPolygonInfoList);
				/* 气象局数据组织并更新到数据库中*/
				ajaxResult = this.operateEarlyWarn(rainBaseInfoNew,dataEarlyWarnRainYJ);
			}else {
				ajaxResult.setStatus(2);
				ajaxResult.setStatusText("不能获取气象局降雨面数据！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
     * @Description 操作降雨面数据
     * @Author 
     * @param obj
     * @return void
     * @Date 20190930
     */
	public  RiskResponseVo operateEarlyWarn(RainBaseInfo receivePolygonVector,String dataSetName){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		Workspace workspace = null;
		DatasourceConnectionInfo datasourceconnection = null;
		Datasource datasource = null;
		DatasetVector datasetVector_new = null;
		Recordset recordset_new =  null;
		
		try {
			workspace = new Workspace();
			// 定义数据源连接信息，假设以下所有数据源设置都存在
			datasourceconnection = new  DatasourceConnectionInfo();
			datasource = MapUtils.connectDataSource(workspace,datasourceconnection);
			AttributeVo attributeVo =new AttributeVo();
			//创建面数据集
			datasetVector_new = MapUtils.createDataSet(dataSetName,datasource,attributeVo);
			/*如果创建面数据集失败*/
			if(datasetVector_new == null){
				ajaxResult.setStatus(2);
				ajaxResult.setStatusText("操作降雨面数据时创建新的面数据集失败！");
				return ajaxResult;
			}
			// 将面数据增加到面数据集中
			// 新增的数据集
			recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
//		List<Geometry> geometryList = new ArrayList<Geometry>();
			/*将json数据组织成面数据*/
			recordset_new = this.generatePolygonData(receivePolygonVector,recordset_new);
			Map<Integer,Feature>  features= recordset_new.getAllFeatures();
			ajaxResult.setData(features);
			ajaxResult.setStatus(1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("操作降雨面数据异常"+e.getMessage());
		}finally{
			// 关闭地图资源
		    MapUtils.closeMapResource(recordset_new,null,null,datasetVector_new,null,null,null,
		    		datasource,datasourceconnection,workspace);
		}
	    return ajaxResult;
	}
	/**
     * @Description 通过json数据生成面数据，面数据中有多条数据
     * @Author 
     * @param obj
     * @return Recordset
     * @Date 20190930
     */
	@SuppressWarnings("deprecation")
	public Recordset  generatePolygonData(RainBaseInfo receivePolygonVector,Recordset recordset_new){
		try {
			if(receivePolygonVector!=null){
				List<RainPolygonInfo>  featureVoList = receivePolygonVector.getFeatures();
				if(featureVoList!=null &&featureVoList.size()>0){
					for(RainPolygonInfo featureVo:featureVoList){
						Map<String,Object> map = MapUtils.getResultByReflect(featureVo.getAttributes());
						/*使用这种方式减少了一层嵌套，使层级减少*/
						if(map.isEmpty() || StringUtils.isBlank(featureVo.getGeometry())){
							continue;
						}
						//将json面数据转换成面
						Geometry  geometry = Toolkit.GeoJsonToGemetry(featureVo.getGeometry());
						recordset_new.edit();
						recordset_new.update();
//					recordset_new.delete();
						recordset_new.addNew(geometry,map);
						// 没有这个数据集更新不能够成功
						recordset_new.update();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordset_new;
	}
	/**
     * @Description 将数据源中的数据集进行 在页面展示(气象局降雨面数据)
     * @Author 
     * @param obj
     * @return RiskResponseVo
     * @Date 20190918
     */
	public RiskResponseVo operateEarlyWarnRainArea(String flag){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		String dataSetName = "";
		if("dataEarlyWarnRain".equals(flag.trim())){
			dataSetName = "dataEarlyWarnRain";
		}else if("dataEarlyWarning".equals(flag.trim())){
			dataSetName = "dataEarlyWarning";
		}
		Workspace workspace = new Workspace();
		// 定义数据源连接信息，假设以下所有数据源设置都存在
	    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
		Datasource datasource = MapUtils.connectDataSource(workspace,datasourceconnection);
		DatasetVector datasetVector_new = (DatasetVector)datasource.getDatasets().get(dataSetName);
		// 新增的数据集
		Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
//		List<Geometry> geometryList = new ArrayList<Geometry>();
		List<RainPolygonInfo>  featureVoList= this.getPolygonVector(recordset_new);
		ajaxResult.setData(featureVoList);
		ajaxResult.setStatus(1);
	 // 关闭地图资源
	    MapUtils.closeMapResource(recordset_new,null,null,datasetVector_new,null,null,null,
	    		datasource,datasourceconnection,workspace);
	    return ajaxResult;
	}
	/**
     * @Description 将数据源中的数据集进行 在页面展示(气象局未来第n天降雨面数据)
     * @Author 
     * @param obj
     * @return RiskResponseVo
     * @Date 20190918
     */
	public RiskResponseVo gainWeatherNDayRainArea(String flag,int day){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		String dataSetName =dataEarlyWarnRainYJ;
//		if("dataEarlyWarnRain".equals(flag.trim())){
//			dataSetName = "dataEarlyWarnRain";
//		}else if("dataEarlyWarning".equals(flag.trim())){
//			dataSetName = "dataEarlyWarning";
//		}
		Workspace workspace = new Workspace();
		// 定义数据源连接信息，假设以下所有数据源设置都存在
	    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
		Datasource datasource = MapUtils.connectDataSource(workspace,datasourceconnection);
		
		DatasetVector datasetVector_new = (DatasetVector)datasource.getDatasets().get(dataSetName);
		// 查询未来第几天的降雨信息
//		String dataTimeNew = DateUtils.getNextDateFormat(new Date(),day);
//		String filter = "dataTime ="+dataTimeNew;
		// 暂时写死
		String dataTimeNew = DateUtils.getNextStringDateFormat("20191212",day);
		String filter = "dataTime ="+dataTimeNew;
		// 新增的数据集
//		Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
		Recordset recordset_new = datasetVector_new.query(filter,CursorType.DYNAMIC );
//		List<Geometry> geometryList = new ArrayList<Geometry>();
		List<RainPolygonInfo>  featureVoList= this.getPolygonVector(recordset_new);
		ajaxResult.setData(featureVoList);
		ajaxResult.setStatus(1);
	 // 关闭地图资源
	    MapUtils.closeMapResource(recordset_new,null,null,datasetVector_new,null,null,null,
	    		datasource,datasourceconnection,workspace);
	    return ajaxResult;
	}
	/**
     * @Description 获取返回的对象的值
     * @Author 
     * @param obj
     * @return List<FeatureVo>
     * @Date 20190918
     */
	public List<RainPolygonInfo> getPolygonVector(Recordset recordset_new){
//		ReceivePolygonVector receivePolygonVector =new ReceivePolygonVector();
		List<RainPolygonInfo> featureVoList =new ArrayList<RainPolygonInfo>();
		if(recordset_new != null){
			Map<Integer,Feature>  features= recordset_new.getAllFeatures();
			for(Feature feature:features.values()){
				RainPolygonInfo featureVo  =new RainPolygonInfo();
	    		Geometry geometry= feature.getGeometry(); 
	    		String geometryJson = Toolkit.GemetryToGeoJson(geometry);
	    		featureVo.setGeometry(geometryJson);
	    		AttributeVo attributeVo =  this.setAttributeVo(feature);
	    		featureVo.setAttributes(attributeVo);
	    		featureVoList.add(featureVo);
	    		if(feature!=null){
	    			feature.dispose();
	    		}
			}
		}
		return  featureVoList;
	} 
	/**
     * @Description 将字段中值设置到对象中去
     * @Author 
     * @param obj
     * @return AttributeVo
     * @Date 20190918
     */
	public AttributeVo setAttributeVo(Feature feature){
		AttributeVo attributeVo =new AttributeVo();
		if(feature!=null){
			String areaDesc = feature.getString("areaDesc");
			String description = feature.getString("description");
			String eventType = feature.getString("eventType");
			String headline = feature.getString("headline");
			String identifier = feature.getString("identifier");
			String sendTime = feature.getString("sendTime");
			String severity = feature.getString("severity");
			String x = feature.getString("x");
			String y = feature.getString("y");
			/*降雨的attributes字段名称*/
			String FID = feature.getString("FID");
			String ID = feature.getString("ID");
			String GRIDCODE = feature.getString("GRIDCODE");
			String dataTime = feature.getString("DATATIME");
			
			attributeVo.setAreaDesc(areaDesc);
			attributeVo.setDescription(description);
			attributeVo.setEventType(eventType);
			attributeVo.setHeadline(headline);
			attributeVo.setIdentifier(identifier);
			attributeVo.setSendTime(sendTime);
			attributeVo.setSeverity(severity);
			attributeVo.setX(x);
			attributeVo.setY(y);
			attributeVo.setFID(FID);
			attributeVo.setID(ID);
			attributeVo.setGRIDCODE(GRIDCODE);
			attributeVo.setDataTime(dataTime);
		}
		return attributeVo;
	}
	 /**
	 * @功能：功能三：获取气象局降雨的栅格数据，使用方式暂未确定
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20190919  gainWeatherRainGridData
	 * @修改记录：
	 */	
	public RiskResponseVo gainWeatherRainGridData (){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
//			String url = "http://60.205.166.252/renbaofile/FileProvider/downloadFileByJson?secretuid=433f3dd6-d9eb-11e9-9637-00163e30bfa0&secretkey=YYUTAKPEV6Y4F3P8NQZD3CWF5J&Content-type=application/text";
			String url = bundleMap.getString("rainWeatherUrl");
			/*json文件能够成功*/
//		String jsonString = "{'pattern': 'name','type': 'fst','files': [{'file_name': 'Z_NWGD_C_BABJ_P_RFFC_SCMOC-ER24_201909310800.json'}]}";
			/*asc文件，也能够成功，需要将asc文件内容直接输出到一个asc文件中，获取直接生成栅格数据*/
//			String jsonString = "{'pattern': 'name','type': 'fst','files': [{'file_name': 'Z_NWGD_C_BABJ_P_RFFC_SCMOC-ER24_201910112000.asc'}]}";
			/*获取某一日期的下几天天几点零分的日期*/
			String dataTime=  DateUtils.getNextDateEightHour(new Date(),1,8);
			//组织参数的json字符串
			String  jsonString = this.generateJson("fst",dataTime).toString();
			/*发送Http post请求,调用气象局台风接口*/
			String responseJson =MapUtils.getRainDataByHttpPost(jsonString, url);
			// 当接口调用不通的时候使用的临时数据
//			String fileName = "C:/Users/Administrator/Desktop/aaaaa.asc";  
			String filePath  = bundle.getString("saveRootPath")+bundle.getString("saveTypePath")+
					"/downloadSuperMap/mapGridData.asc";
//			String  responseJson =  MapUtils.readFileByLines(fileName);
			/*将栅格数据生成到指定的文件夹下*/
//			ajaxResult = MapUtils.generateFileToFtp(responseJson);
			/*生成asc文件*/
			MapUtils.generateAscFile(responseJson,filePath);
			//在数据源中生成栅格数据集
//			MapUtils.setThemeRangeByDataPng(filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	/**
	 * @功能：功能四：获取气象局预警数据，使用方式暂未确定
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191010
	 * @修改记录：
	 */	
	public RiskResponseVo gainDataEarlyWarning(String earlyWarning){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
			/*后续将url添加到配置文件中*/
//			String url = "http://fabu.12379.cn/picc.json";
			String url = bundleMap.getString("earlyWarnUrl");
			/*发送Http Get请求,调用气象局台风接口*/
			String responseJson =MapUtils.getTyphoonDataByGet(url);
			// 当接口调用不通的时候使用的临时数据
//			String responseJson = earlyWarning;
			// 将气象局中的数据转化成对象
			RainBaseInfo rainBaseInfo = JSON.parseObject(responseJson, RainBaseInfo.class);
			/* 气象局数据组织并更新到数据库中*/
			ajaxResult = this.operateEarlyWarn(rainBaseInfo,dataEarlyWarning);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	/**
	 * @功能：组织调用气象局接口数据参数的组织
	 * @param 第一位传递是实况（obs）还是预报(fst)标志位，第二位传递时间(时间是加一天的)
	 * @return void
	 * @author liqiankun
	 * @时间：20190930
	 * @修改记录：
	 */	
		
	public  JSONObject generateJson(String isRealFlag,String dataTime) {
		JSONObject audience = new JSONObject();
		try {
			String header = "";
			if("fst".equals(isRealFlag)){
				// 预警
				header = bundleMap.getString("YJRain");
			}else if("obs".equals(isRealFlag)){
				//实况
				header = bundleMap.getString("SHRain");
			}
//			StringBuilder stringBuilder = new StringBuilder("Z_NWGD_C_BABJ_P_RFFC_SCMOC-ER24_");
			StringBuilder stringBuilder = new StringBuilder(header);
			stringBuilder.append(dataTime);
			stringBuilder.append(".json");
			audience.put("pattern", "name");
//			audience.put("type", "fst");
			audience.put("type", isRealFlag);
			//数组
			JSONArray platform = new JSONArray();
			//数组中对象
			JSONObject message = new JSONObject();
//			message.put("file_name", "Z_NWGD_C_BABJ_P_RFFC_SCMOC-ER24_201910160800.json");
			message.put("file_name", stringBuilder.toString());
			
			platform.add(message);
			// 将数组添加到大对象中
			audience.put("files", platform);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("组织json字符串出错"+e.getMessage());
		}
		return audience;
	}
	/**
	 * @功能：通过定时任务触发，来获取降雨的数据的影响的标的信息,以不同的降雨等级进行汇总。
	 * 进行标的信息操作时首先需要判断该面数据更新时间是否大于标的处理时间，如果晚于标的处理时间则需要再次进行处理。
	 * 通过该信息来判断是否处理过，如果处理过就不进行处理
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191015
	 * @修改记录：
	 */
	public  void operateRainArea(String date) {
		long startTimeFirst = System.currentTimeMillis();
		String dataSetName = "dataEarlyWarnRain",dataSetName_new="dataEarlyWarnRain_new";
		String dataSetNameYJ = "dataEarlyWarnRainYJ",dataSetNameYJ_new="dataEarlyWarnRainYJ_new";
//		String rainCode = "RS2019121308",rainYJCode = "RY2019121308";
		// 进行日期格式生成
//		String date = "20191212";
		//首先判断该降雨信息是否已经处理过
		String rainCode= "";
		
		Workspace workspace = null;
		DatasourceConnectionInfo dscon = null;
		Datasource datasource =  null;
		
		try {
			if(StringUtils.isBlank(date)){
				Assert.notNull(date, "传递日期信息不能为空!");
			}
			rainCode= "RS"+date.substring(0, 8);
			TyphoonLatestInfo  typhoonLatestInfo =   this.searchTyphoonBaseInfoByTfbh(rainCode);
			if(null==typhoonLatestInfo || !"1".equals(typhoonLatestInfo.getIsUse())){
				throw new RuntimeException("该降雨信息不存在或已经经过处理!");
			}
			//进行占位
			this.updateWzTfbhFlag("5","1");
			workspace = new Workspace();
			// 定义数据源连接信息，假设以下所有数据源设置都存在
		    dscon = new  DatasourceConnectionInfo();
			datasource = MapUtils.connectDataSource(workspace,dscon);
			/*降雨实况模块对面数据的处理 begin  */
			// 降雨的实况面数据集
			DatasetVector datasetVector = (DatasetVector)datasource.getDatasets().get(dataSetName);
			//查询出大雨或暴雨以上数据的记录集,GRIDCODE(25-50是大雨，50-100是暴雨)
//			Recordset recordset_rain = datasetVector.getRecordset(false, CursorType.DYNAMIC);
			String filter = "GRIDCODE>=50 and dataTime = 'RS"+date+"'";
			Recordset recordset_rain = datasetVector.query(filter,CursorType.DYNAMIC ); 
			// 新建一个面数据集，用于存储处理之后的数据
			AttributeVo attributeVo =new AttributeVo();
			DatasetVector datasetVector_new = MapUtils.createDataSet(dataSetName_new,datasource,attributeVo);
			// 将面数据融合之后，放到一个新的记录集中
			Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
			/*获取该记录集的面数据信息*/
			this.getRecordGeoMap(datasetVector_new,recordset_rain,recordset_new);
			/*降雨实况模块对面数据的处理 end*/
			/*降雨预警模块对面数据的处理 begin  */
			// 降雨的预警面数据集
			DatasetVector datasetVectorYJ = (DatasetVector)datasource.getDatasets().get(dataSetNameYJ);
			String filterYJ = "GRIDCODE >=50 ";
			Recordset recordset_rainYJ = datasetVectorYJ.query(filterYJ,CursorType.DYNAMIC );
			// 新建一个面数据集，用于存储处理之后的数据
			DatasetVector datasetVectorYJ_new = MapUtils.createDataSet(dataSetNameYJ_new,datasource,attributeVo);
			// 将面数据融合之后，放到一个新的记录集中
			Recordset recordsetYJ_new = datasetVectorYJ_new.getRecordset(false, CursorType.DYNAMIC);
			/*获取该记录集的面数据信息*/
			this.getRecordGeoMap(datasetVectorYJ_new,recordset_rainYJ,recordsetYJ_new);
			/*降雨预警模块对数据的处理 end */
			
			// 点矢量集
			DatasetVector datasetVector_address = (DatasetVector)datasource.getDatasets().get("RISKMAP_ADDRESS");
			//获取县行政区划china_county
			DatasetVector datasetVector_county = (DatasetVector)datasource.getDatasets().get("china_county");
			//用于将没有被全覆盖的县级标的点放到一个新的数据集中,map用于存储数据集和全覆盖的县级信息
//			QueryParameter  queryParameter_new =MapUtils.organSpatialQueryParam(dscon,datasetVector_new,null,"geoRegionContain");
//			Map<String, Object> map_recordset = this.getRecordsetMap(recordset_new,datasetVector_county);
			
			// 操作实况降雨影响的标的信息
//			List<WzCorporeLs> wzCorporeLsList = this.operateRainCorpore(datasource,dscon,datasetVector_new,datasetVector_address,date);
			/*首先删除之前降雨实况处理信息*/
			String deleteLSql="delete from WZ_CORPORELS where 1=1 and tfbh like '"+"RS"+date+"'";
			this.deleteSqlInfo(deleteLSql);
			/*修改之后的标的信息*/
			List<WzCorporeLs> wzCorporeLsList = this.operateRainCorpore_new(datasource,dscon,
																	datasetVector_new,datasetVector_address,date);
			//操作预警降雨影响的标的信息，包括未来十天的降雨信息
//			List<WzCorporeLs> wzCorporeYjList =null;
			String deleteLYSql ="delete from WZ_CORPOREYJ where 1=1 and tfbh like 'RY%'";
			this.deleteSqlInfo(deleteLYSql);
			List<WzCorporeLs> wzCorporeYjList =  new ArrayList<WzCorporeLs>();
//			List<WzCorporeLs> wzCorporeYjList = this.operateRainYJCorpore_new(datasource,dscon,datasetVectorYJ_new,datasetVector_address,date);
			
			/*通过全覆盖方式操作 =----begin*/
			// 使用县级信息进行数据的操作
//			Map<String, Object>  mapRainCorpore= this.operateRainCorpore_new(dscon,datasetVector_new,riskmap_address_new,datasetVector_county);
			//没有全覆盖的县级标的信息
//			List<WzCorporeLs> wzCorporeLsList = (List<WzCorporeLs>)mapRainCorpore.get("wzCorporeLs");
//			// 全覆盖的县级信息，需要组织标的信息，关联RISKMAP_MAIN过滤掉已经终保的信息(我们需要把标的信息重新拉取到一个表中，并把他对应的县代码进行数据更新)
//			Map<String,Set<String>> mapCover = (Map<String,Set<String>>)mapRainCorpore.get("mapCover");
//			String riskmap_address_tableName = riskmap_address_new.getTableName();
//			List<WzCorporeLs> wzCorporeLsListCover = new ArrayList<WzCorporeLs>();
			/*通过全覆盖方式操作 =----end*/
			System.out.println("==========降雨影响的实况标的信息count=============:"+wzCorporeLsList.size());
			log.info("==========降雨影响的实况标的信息count=============:"+wzCorporeLsList.size());
			if(null!=wzCorporeLsList&&wzCorporeLsList.size()>0){
				/*首先删除之前降雨实况处理信息*/
				String deleteLpSql="delete from WZ_CORPORELS_P where 1=1 and tfbh like '"+"RS"+date+"'";
//				databaseDao.saveAll(WzCorporeLs.class, wzCorporeLsList);
				wzCorporeLsRepository.saveAll(wzCorporeLsList);
//				Map<String, ArrayList<WzCorporeLs>> map = new HashMap<String, ArrayList<WzCorporeLs>>();
//				map.put("corporeRain", (ArrayList)wzCorporeLsList);
				// 获取实况不同等级的降雨数据,4:1_10类型
//				List<WzCorporeLsP>  corporePGradeList = this.sumRainAmount(wzCorporeLsList,"4","1");
				this.deleteSqlInfo(deleteLpSql);
				List<WzCorporeLsP>  corporePGradeList = this.sumRainAmount_new(wzCorporeLsList,"4","1");

//				List<WzCorporeLsP>  corporePList = this.sumRainAmount(wzCorporeLsList,"5","1");
//				corporePGradeList.addAll(corporePList);
				
//				databaseDao.saveAll(WzCorporeLsP.class, corporePGradeList);
				wzCorporeLsPRepository.saveAll(corporePGradeList);
			}
			System.out.println("==========降雨影响的预警标的信息count=============:"+wzCorporeYjList.size());
			log.info("==========降雨影响的预警标的信息count=============:"+wzCorporeYjList.size());
			/*对预警降雨处理的台风进行复制，用于存储到数据库中  addby  liqiankun 20200311 begin */
			if(null!=wzCorporeYjList&&wzCorporeYjList.size()>0){
//				List<WzCorporeYj> wzCorporeYjList_new = new ArrayList<WzCorporeYj>();
				List<WzCorporeYj> wzCorporeYjList_new = BeanUtils.convertByType(wzCorporeYjList, WzCorporeYj.class);
//				Datas.copySimpleObjectToTargetFromSource(wzCorporeYjList_new, wzCorporeYjList);
				/*首先删除之前降雨实况处理信息*/
				String deleteLYpSql="delete from WZ_CORPOREYJ_P where 1=1 and tfbh like 'RY%'";
				// 储存预警数据到数据库中
//				databaseDao.saveAll(WzCorporeYj.class, wzCorporeYjList_new);
				wzCorporeYjRepository.saveAll(wzCorporeYjList_new);
				// 获取预警不同等级的降雨数据
				this.deleteSqlInfo(deleteLYpSql);
				List<WzCorporeLsP>  corporeYJPGradeList = this.sumRainAmount_new(wzCorporeYjList,"4","2");
//				List<WzCorporeLsP>  corporeYJPList = this.sumRainAmount(wzCorporeLsList,"5","2");
//				corporeYJPGradeList.addAll(corporeYJPList);
//				List<WzCorporeYjP> WzCorporeYjPList_new = new ArrayList<WzCorporeYjP>();
				List<WzCorporeYjP> WzCorporeYjPList_new = BeanUtils.convertByType(corporeYJPGradeList, WzCorporeYjP.class);
//				Datas.copySimpleObjectToTargetFromSource(WzCorporeYjPList_new, corporeYJPGradeList);
				
//				databaseDao.saveAll(WzCorporeYjP.class, WzCorporeYjPList_new);
				wzCorporeYjPRepository.saveAll(WzCorporeYjPList_new);
			}
			/*对预警降雨处理的台风进行复制，用于存储到数据库中  addby  liqiankun 20200311 end */
			String  update_TFBH ="update WZ_TFBH set ISUSE='2' where 1=1 and tfbh ='"+rainCode+"' and ISUSE in ('1','5')"; 
			this.slaveJdbcTemplate.update(update_TFBH);
			/*关闭资源*/
			MapUtils.closeMapResource(null, recordset_new, recordset_rain,null,
					datasetVector_address, datasetVector_new,datasetVector, datasource, dscon, workspace);
			long endTimeFirst = System.currentTimeMillis();
			System.out.println("========countTime============"+(endTimeFirst-startTimeFirst));
		} catch (Exception e) {
			e.printStackTrace();
			this.updateWzTfbhFlag("1","5");
		}
	}
	/**
	 * @功能：测试标地组织信息
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191015
	 * @修改记录：
	 */
	public  RiskResponseVo testWzCorporeLs(){
		RiskResponseVo ajaxResult =new RiskResponseVo();
//		QueryRule queryRule = QueryRule.getInstance();
		Criteria<WzCorporeLs> criteria = new Criteria<>();
		try {
//			queryRule.addEqual("id.tfbh", "Rain");
//			queryRule.addEqual("id.mid", "653716");
//			List<WzCorporeLs> wzCorporeLsList = databaseDao.findAll(WzCorporeLs.class, queryRule);
			
			criteria.add(Restrictions.eq("id.tfbh", "Rain"));
			List<WzCorporeLs> wzCorporeLsList = wzCorporeLsRepository.findAll(criteria);
			
			List<WzCorporeLsP>  corporePGradeList = this.sumRainAmount(wzCorporeLsList,"4","1");
			List<WzCorporeLsP>  corporePList = this.sumRainAmount(wzCorporeLsList,"5","1");
			corporePGradeList.addAll(corporePList);
			mapWarnService.saveWzCorporeLsP(corporePGradeList);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setStatus(2);
		}
		return ajaxResult;
	}
	
	/**
	 * @功能：统计降雨的保额保费,统计各个标的点的SMID来进行查询,
	 *   procityFlag:4(按等级，比如 降雨量1-10为一等级),5(按降雨量来进行划分)
	 *   judgeFlag: 1(实况)，2（预警）
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191017
	 * @修改记录：
	 */
	public List<WzCorporeLsP> sumRainAmount(List<WzCorporeLs> wzCorporeLsList,String procityFlag,String judgeFlag){
		List<WzCorporeLsP>  wzCorporeLsPList= new ArrayList<WzCorporeLsP>();
		try {
			// 组织标的信息
			Map<Map<String, String>,Wz_Corporels_byMid> mapCountMap = 
					this.organizeRainCorpore(wzCorporeLsList,procityFlag);
			for(Map<String,String> keyMap : mapCountMap.keySet()){
				WzCorporeLsP wzCorporeLsP =new WzCorporeLsP();
				WzCorporeLsPId id = new WzCorporeLsPId();
				List<String> midList = mapCountMap.get(keyMap).getCountList();
				BigDecimal[]  amounts = new BigDecimal[3];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
				String centerX="0",centerY="0";
				if(midList.size()>0&& midList !=null){
					centerX =mapCountMap.get(keyMap).getCenterX();
					centerY = mapCountMap.get(keyMap).getCenterY();
//					centerX = new BigDecimal(centerX).divide(new BigDecimal(midList.size())).toString();
//					centerY=new BigDecimal(centerY).divide(new BigDecimal(midList.size())).toString();
				}
				
				Date date = new Date();
				String endTime = sdf.format(date);
				String endHour = sdfHour.format(date);
				// 判断查询字段的长度
				int max = midList.size()/500;
				if(max>0){
					List<String> countList = null;
					for(int i=1;i<=max;i++){
						if(i<max){
							countList =midList.subList((i-1)*500, i*500);
						}else {
							countList = midList.subList((i-1)*500, midList.size());
						}
						BigDecimal [] amountSingle = this.doworkSumAmount(countList,endTime,endHour);
						amounts[0] = amounts[0].add(amountSingle[0]);
						amounts[1] = amounts[1].add(amountSingle[1]);
						amounts[2] = amounts[2].add(amountSingle[2]);
					}
				}else {
					amounts = this.doworkSumAmount(midList,endTime,endHour);
				}
				for(String key : keyMap.keySet()){
					id.setTfbh(key);
					id.setCityCode(keyMap.get(key));
				}
				id.setProcityFlag(procityFlag);
				wzCorporeLsP.setId(id);
				wzCorporeLsP.setJAmount(BigDecimal.ZERO);
				List<WzCorporeLsP> wzCorporeLsPListOld= new ArrayList<WzCorporeLsP>();
				if("1".equals(judgeFlag)){
					/*查询该标的整合信息是否存在*/
					wzCorporeLsPListOld=  this.searchWzCorporeLsP(id);
				}else if("2".equals(judgeFlag)){
					// 进行数据的复制
					List<WzCorporeYjP> wzCorporeYJPListOld  =this.searchWzCorporeYJP(id);
					wzCorporeLsPListOld = BeanUtils.convertByType(wzCorporeYJPListOld, WzCorporeLsP.class);
				}
				/*查询该标的整合信息是否存在*/
//				List<WzCorporeLsP> wzCorporeLsPListOld=  this.searchWzCorporeLsP(id);
				if(wzCorporeLsPListOld==null||wzCorporeLsPListOld.size()==0){
					wzCorporeLsP.setCorporeSum(midList.size());
					wzCorporeLsP.setTotalAmount(amounts[0]);
					wzCorporeLsP.setQAmount(amounts[1]);
					wzCorporeLsP.setGAmount(amounts[2]);
					// 中心点X,Y 
					wzCorporeLsP.setCenterX(new BigDecimal(centerX).divide(new BigDecimal(midList.size()),8,RoundingMode.HALF_UP));
					wzCorporeLsP.setCenterY(new BigDecimal(centerY).divide(new BigDecimal(midList.size()),8,RoundingMode.HALF_UP));
					wzCorporeLsPList.add(wzCorporeLsP);
				}else {
					WzCorporeLsP wzCorporeLsPNew=wzCorporeLsPListOld.get(0);
//						BeanUtils.copyProperties(wzCorporeLsPNew, wzCorporeLsPListOld.get(0));
					wzCorporeLsPNew.setCorporeSum(midList.size()+wzCorporeLsPNew.getCorporeSum());
					wzCorporeLsPNew.setTotalAmount(wzCorporeLsPNew.getTotalAmount().add(amounts[0]));
					wzCorporeLsPNew.setQAmount(wzCorporeLsPNew.getQAmount().add(amounts[1]));
					wzCorporeLsPNew.setGAmount(wzCorporeLsPNew.getGAmount().add(amounts[2]));
					// 重新计算中心点的位置
					BigDecimal x= wzCorporeLsPNew.getCenterX().add(new BigDecimal(wzCorporeLsPNew.getCorporeSum())
					.multiply(wzCorporeLsPNew.getCenterX())).divide(new BigDecimal(wzCorporeLsPNew.getCorporeSum()+midList.size()),8,RoundingMode.HALF_UP);
					BigDecimal y= wzCorporeLsPNew.getCenterY().add(new BigDecimal(wzCorporeLsPNew.getCorporeSum())
					.multiply(wzCorporeLsPNew.getCenterY())).divide(new BigDecimal(wzCorporeLsPNew.getCorporeSum()+midList.size()),8,RoundingMode.HALF_UP);;
					
					wzCorporeLsPNew.setCenterX(x);
					wzCorporeLsPNew.setCenterY(y);
//						wzCorporeLsP.setCorporeSum(midList.size());
//						wzCorporeLsP.setTotalAmount(amounts[0]);
//						wzCorporeLsP.setQAmount(amounts[1]);
//						wzCorporeLsP.setGAmount(amounts[2]);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wzCorporeLsPList;

	}
	
	
	public BigDecimal[] doworkSumAmount(List<String> countList,String tfdate,String tftime){
		BigDecimal[] bigdecimals = new BigDecimal[3];
		bigdecimals[0] = BigDecimal.ZERO;
		bigdecimals[1] = BigDecimal.ZERO;
		bigdecimals[2] = BigDecimal.ZERO;
		if(countList.size() == 0){
			return bigdecimals;
		}
		String list_str = StringUtils.join(countList,"','");
		
		String sql = "select sum(sumamount) as amountQ from riskmap_main where classcode = '01' and addressid in ('" +list_str+ "') and (startdate<to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
				+ " or (startdate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and starthour <='" +tftime+ "')) and (enddate>to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
				+ " or (enddate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and endhour >='" +tftime+ "')) group by classcode";
		List<Map<String, Object>> resultQ = null;
		resultQ = slaveJdbcTemplate.queryForList(sql);
		if(resultQ!=null && resultQ.size() == 1 && resultQ.get(0)!=null && resultQ.get(0).get("AMOUNTQ")!=null){
			BigDecimal amountQ = new BigDecimal(resultQ.get(0).get("AMOUNTQ").toString()); 
//			amountQ=amountQ.setScale(2, BigDecimal.ROUND_DOWN); //小数位 直接舍去
			bigdecimals[1]=amountQ.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
		}
		
		String sql2 = "select sum(sumamount) as amountG from riskmap_main where classcode = '03' and addressid in ('" +list_str+ "') and (startdate<to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
				+ " or (startdate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and starthour <='" +tftime+ "')) and (enddate>to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
				+ " or (enddate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and endhour >='" +tftime+ "')) group by classcode";
		List<Map<String, Object>> resultG = null;
		resultG = slaveJdbcTemplate.queryForList(sql2);
		if(resultG!=null && resultG.size() == 1 && resultG.get(0)!=null && resultG.get(0).get("AMOUNTG")!=null){
			BigDecimal amountG = new BigDecimal(resultG.get(0).get("AMOUNTG").toString()); 
	//		amountG=amountG.setScale(2, BigDecimal.ROUND_DOWN); //小数位 直接舍去
			bigdecimals[2]=amountG.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
		}
		bigdecimals[0] = bigdecimals[1].add(bigdecimals[2]).setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println("计算中临时 amount = " + bigdecimals[0] + " Q = "  + bigdecimals[1] + " G = "  + bigdecimals[2]);
		return bigdecimals;
		
	}
	/**
	 * @功能：查询存储标的集合信息的数据是否存在
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191017
	 * @修改记录：
	 */
	public  List<WzCorporeLsP> searchWzCorporeLsP(WzCorporeLsPId id){
		List<WzCorporeLsP>  wzCorporeLsPList = null;
		try {
//			QueryRule  queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.tfbh", id.getTfbh());
//			queryRule.addEqual("id.cityCode", id.getCityCode());
//			queryRule.addEqual("id.procityFlag", id.getProcityFlag());
//			wzCorporeLsPList =databaseDao.findAll(WzCorporeLsP.class, queryRule);
			
			Criteria<WzCorporeLsP> criteria =new Criteria<>();
			criteria.add(Restrictions.eq("id.tfbh", id.getTfbh()));
			criteria.add(Restrictions.eq("id.cityCode", id.getCityCode()));
			criteria.add(Restrictions.eq("id.procityFlag", id.getProcityFlag()));
			wzCorporeLsPList = wzCorporeLsPRepository.findAll(criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wzCorporeLsPList;
	}
	/**
	 * @功能：查询存储预警标的集合信息的数据是否存在
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191017
	 * @修改记录：
	 */
	public  List<WzCorporeYjP> searchWzCorporeYJP(WzCorporeLsPId id){
		List<WzCorporeYjP>  wzCorporeYjPList = null;
		try {
//			QueryRule  queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.tfbh", id.getTfbh());
//			queryRule.addEqual("id.cityCode", id.getCityCode());
//			queryRule.addEqual("id.procityFlag", id.getProcityFlag());
//			wzCorporeYjPList =databaseDao.findAll(WzCorporeYjP.class, queryRule);
			
			Criteria<WzCorporeYjP> criteria =new Criteria<>();
			criteria.add(Restrictions.eq("id.tfbh", id.getTfbh()));
			criteria.add(Restrictions.eq("id.cityCode", id.getCityCode()));
			criteria.add(Restrictions.eq("id.procityFlag", id.getProcityFlag()));
			wzCorporeYjPList = wzCorporeYjPRepository.findAll(criteria);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wzCorporeYjPList;
	}
	
	// 重新组织标的信息
	public Map<Map<String, String>,Wz_Corporels_byMid> organizeRainCorpore(List<WzCorporeLs> wzCorporeLsList,String flag){
		Map<Map<String, String>,Wz_Corporels_byMid> mapCountMap=
				  new HashMap<Map<String,String>, Wz_Corporels_byMid>();
		if(wzCorporeLsList!=null&&wzCorporeLsList.size()>0){
			for(WzCorporeLs wzCorporeLs:wzCorporeLsList){
				String tfbh = wzCorporeLs.getId().getTfbh();
				//降雨等级
				String cityCode = wzCorporeLs.getCityCode();
				String centerX = wzCorporeLs.getPointx_2000().toString();
				String centerY = wzCorporeLs.getPointy_2000().toString();
				String mid =wzCorporeLs.getId().getMid().toString();
//				map.put(tfbh, cityCode);
				//key:value分别是降雨编号和降雨等级
				Map<String,String> map =  this.organRainMap(tfbh,cityCode, flag);
				// 假如key值 存在则进行叠加，否则新建
				if(mapCountMap.containsKey(map)){
					mapCountMap.get(map).getCountList().add(mid);
					mapCountMap.get(map).setCenterX((Double.parseDouble(mapCountMap.get(map).getCenterX())+Double.parseDouble(centerX))+"");
					mapCountMap.get(map).setCenterY((Double.parseDouble(mapCountMap.get(map).getCenterY())+Double.parseDouble(centerY))+"");
				}else{
					/*不存在则新建*/
					Wz_Corporels_byMid corporelsData =
							new Wz_Corporels_byMid(new ArrayList<String>(Arrays.asList(mid)),"",centerX,centerY,flag);
					mapCountMap.put(map, corporelsData);
				}
			}
		}
		return mapCountMap;
	}
	//组织降雨等级数据
	public Map<String,String> organRainMap(String tfbh,String cityCode,String flag){
		Map<String,String>  map = new HashMap<String, String>();
		Integer  codeCount = Integer.parseInt(cityCode);
		String [] grades = {"1_10","10_25","25_50","50_100","100_150","150_200","200_300","300_+"};
		if("4".equals(flag)){
			if(codeCount<10){
				map.put(tfbh, grades[0]);
			}else if(codeCount>=10&&codeCount<25){
				map.put(tfbh, grades[1]);
			}else if(codeCount>=25&&codeCount<50){
				map.put(tfbh, grades[2]);
			}else if(codeCount>=50&&codeCount<100){
				map.put(tfbh, grades[3]);
			}else if(codeCount>=100&&codeCount<150){
				map.put(tfbh, grades[4]);
			}else if(codeCount>=150&&codeCount<200){
				map.put(tfbh, grades[5]);
			}else if(codeCount>=200&&codeCount<300){
				map.put(tfbh, grades[6]);
			}else if(codeCount>=300){
				map.put(tfbh, grades[7]);
			}
		}else if("5".equals(flag)){
			map.put(tfbh, cityCode);
		}
		return map;
	}
	/**
	 * @功能：操作降雨数据的标的信息
	 * @param datasetVector_new： 为dataEarlyWarnRain_new数据集
	 * 	@param 	riskmap_address_new：为RISKMAP_ADDRESS_POINT 数据集
	 * @return void
	 * @author liqiankun
	 * @时间：20191015
	 * @修改记录：
	 */
	public List<WzCorporeLs> operateRainCorpore(Datasource datasource,DatasourceConnectionInfo dscon,
			DatasetVector datasetVector_new,DatasetVector datasetVector_address,String date){
		//将降雨面之内的有效标的统计到一个新的数据集中
		QueryParameter  queryParameter =MapUtils.organSpatialQueryParam(dscon,datasetVector_new,null,"datasetVector");
		Recordset recordset_address_old = datasetVector_address.query(queryParameter);
		System.out.println("与面相交的标的点的个数"+recordset_address_old.getRecordCount());
		// 创建新的点数据集
		RiskMapAddressField riskMapAddressField =new RiskMapAddressField();
		DatasetVector riskmap_address_new = MapUtils.createDataSet(riskmap_address_point,datasource,riskMapAddressField);
		System.out.println("添加到新的点数据集的标的点的个数before"+riskmap_address_new.getRecordCount());
		/*清除已有标的点数据，添加新的标的点数据集*/
		MapUtils.clearRecordset(riskmap_address_new,recordset_address_old);
		System.out.println("添加到新的点数据集的标的点的个数after"+riskmap_address_new.getRecordCount());
		
		List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
		String gridCode ="";
		String rainCode = "RS"+date;
//		,rainCode ="Rain";
		if(datasetVector_new.getRecordCount()>0){
			int count=0;
			Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
			while (!recordset_new.isEOF()){
				gridCode = recordset_new.getString("GRIDCODE");
				System.out.println("=============="+gridCode);
				// 单个降雨等级的面数据集
				GeoRegion geoRegion=(GeoRegion)recordset_new.getGeometry();
				QueryParameter param_single=  MapUtils.organSpatialQueryParam(dscon,null,geoRegion,"geoRegion");
				
				Recordset recordset_address = riskmap_address_new.query(param_single);
				System.out.println(gridCode+"对应的标的 点个数："+recordset_address.getRecordCount());
				FieldInfos  fieldInfos = recordset_address.getFieldInfos();
				int count1 = fieldInfos.getCount();
				System.out.println("=====================输出字段名称");
//				for(int i=0;i<count1;i++){
//					FieldInfo  fieldInfo = fieldInfos.get(i);
//					String  name = fieldInfo.getName();
//					String  value = fieldInfo.getDefaultValue();
//					System.out.println(name+":"+value);
//				}
				// 对标的点进行循环
				int j=0;
				while(!recordset_address.isEOF()){
					// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
					String  proposalNo11 = recordset_address.getString("PROPOSALNO")+"";
					String  proposalNo = recordset_address.getFieldValue("PROPOSALNO")+"";
					if(recordset_address!=null&&recordset_address.getFieldValue("ADDRESSID")!=null&&
							StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){ 
						j++;
						Integer  smid = Integer.parseInt(recordset_address.getFieldValue("ADDRESSID")+"");
						String addressName = recordset_address.getFieldValue("ADDRESSNAME")+"";
						BigDecimal pointx_2000 = new BigDecimal(recordset_address.getString("POINTX_2000"));
						BigDecimal pointy_2000 = new BigDecimal(recordset_address.getString("POINTY_2000"));
						BigDecimal pointx_02 = new BigDecimal(recordset_address.getString("POINTX_02"));
						BigDecimal pointy_02 = new BigDecimal(recordset_address.getString("POINTY_02"));
						// 求标的表中是否存在该信息,若不存在则进行添加
						long totalCount =  getCorporeTotalCount(rainCode ,smid,"","WZ_CORPORELS");
						if(totalCount==0){
							WzCorporeLs wzCorporeLs =new WzCorporeLs();
							WzCorporeLsId  wzCorporeLsId = new WzCorporeLsId();
							wzCorporeLsId.setTfbh(rainCode);
							wzCorporeLsId.setMid(smid);
							wzCorporeLs.setId(wzCorporeLsId);
							
//							wzCorporeLs.setCenterX(centerX);
//							wzCorporeLs.setCenterY(centerY);
							wzCorporeLs.setCityCode(gridCode);
							wzCorporeLs.setAddressName(addressName);
							// 状态标志位为2 ，则是未生成标的保额信息
							wzCorporeLs.setValidStatus("2");
							
							wzCorporeLs.setPointx_2000(pointx_2000);
							wzCorporeLs.setPointy_2000(pointy_2000);
							wzCorporeLs.setPointx_02(pointx_02);
							wzCorporeLs.setPointy_02(pointy_02);
							
							wzCorporeLsList.add(wzCorporeLs);
						}
					}
					recordset_address.moveNext();
				}
				System.out.println("雨场有效标的个数"+j);
				count+=j;
				recordset_new.moveNext();
			}
			System.out.println("雨场标的总数"+count);
		}
		return wzCorporeLsList;
	}
	
	/**
	 * @功能：操作降雨预警数据的标的信息，分别对未来十天的降雨信息进行处理
	 * @param datasetVector_new： 为dataEarlyWarnRainYJ_new数据集
	 * 	@param 	riskmap_address_new：为RISKMAP_ADDRESS_POINT 数据集
	 * @return void
	 * @author liqiankun
	 * @时间：20191015
	 * @修改记录：
	 */
	public List<WzCorporeLs> operateRainYJCorpore(Datasource datasource,DatasourceConnectionInfo dscon,
			DatasetVector datasetVector_new,DatasetVector datasetVector_address,String date){
		//将降雨面之内的有效标的统计到一个新的数据集中
		QueryParameter  queryParameter =MapUtils.organSpatialQueryParam(dscon,datasetVector_new,null,"datasetVector");
		Recordset recordset_address_old = datasetVector_address.query(queryParameter);
		System.out.println("与预警降雨面相交的标的点的个数"+recordset_address_old.getRecordCount());
		// 创建新的点数据集
		RiskMapAddressField riskMapAddressField =new RiskMapAddressField();
		DatasetVector riskmap_address_new = MapUtils.createDataSet(riskmap_address_point,datasource,riskMapAddressField);
		System.out.println("添加到新的点数据集的标的点的RainYJ个数before"+riskmap_address_new.getRecordCount());
		/*清除已有标的点数据，添加新的标的点数据集*/
		MapUtils.clearRecordset(riskmap_address_new,recordset_address_old);
		System.out.println("添加到新的点数据集的标的点的RainYJ个数after"+riskmap_address_new.getRecordCount());
		
		List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
		//进行未来十天预警的操作
		for (int i=1;i<=10;i++){
			String gridCode ="";
			date = DateUtils.getNextStringDateFormat(date,i);
			String rainCode ="RY"+date;
			if(datasetVector_new.getRecordCount()>0){
				int count=0;
				// 现在这个时间是年月日形式，不带小时
				String filterYJ = "dataTime="+date.substring(0,date.length()-2);
//				Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
				Recordset recordset_new = datasetVector_new.query(filterYJ,CursorType.DYNAMIC );
				while (!recordset_new.isEOF()){
					gridCode = recordset_new.getString("GRIDCODE");
					System.out.println("=============="+gridCode+"===="+rainCode);
					// 单个降雨等级的面数据集
					GeoRegion geoRegion=(GeoRegion)recordset_new.getGeometry();
					QueryParameter param_single=  MapUtils.organSpatialQueryParam(dscon,null,geoRegion,"geoRegion");
					
					Recordset recordset_address = riskmap_address_new.query(param_single);
					System.out.println(gridCode+"对应的标的 点个数："+recordset_address.getRecordCount());
					FieldInfos  fieldInfos = recordset_address.getFieldInfos();
					int count1 = fieldInfos.getCount();
					System.out.println("=====================输出字段名称");
//					for(int i=0;i<count1;i++){
//						FieldInfo  fieldInfo = fieldInfos.get(i);
//						String  name = fieldInfo.getName();
//						String  value = fieldInfo.getDefaultValue();
//						System.out.println(name+":"+value);
//					}
					// 对标的点进行循环
					int j=0;
					while(!recordset_address.isEOF()){
						// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
						String  proposalNo11 = recordset_address.getString("PROPOSALNO")+"";
						String  proposalNo = recordset_address.getFieldValue("PROPOSALNO")+"";
						if(recordset_address!=null&&recordset_address.getFieldValue("ADDRESSID")!=null&&
								StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){ 
							j++;
							Integer  smid = Integer.parseInt(recordset_address.getFieldValue("ADDRESSID")+"");
							String addressName = recordset_address.getFieldValue("ADDRESSNAME")+"";
							BigDecimal pointx_2000 = new BigDecimal(recordset_address.getString("POINTX_2000"));
							BigDecimal pointy_2000 = new BigDecimal(recordset_address.getString("POINTY_2000"));
							BigDecimal pointx_02 = new BigDecimal(recordset_address.getString("POINTX_02"));
							BigDecimal pointy_02 = new BigDecimal(recordset_address.getString("POINTY_02"));
							// 求标的表中是否存在该信息,若不存在则进行添加
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
							long totalCount =  getCorporeTotalCount(rainCode ,smid,"","WZ_CORPOREYJ");
							if(totalCount==0){
								WzCorporeLs wzCorporeLs =new WzCorporeLs();
								WzCorporeLsId  wzCorporeLsId = new WzCorporeLsId();
								wzCorporeLsId.setTfbh(rainCode);
								wzCorporeLsId.setMid(smid);
								wzCorporeLs.setId(wzCorporeLsId);
								
//								wzCorporeLs.setCenterX(centerX);
//								wzCorporeLs.setCenterY(centerY);
								wzCorporeLs.setCityCode(gridCode);
								wzCorporeLs.setAddressName(addressName);
								// 状态标志位为2 ，则是未生成标的保额信息
								wzCorporeLs.setValidStatus("2");
								
								wzCorporeLs.setPointx_2000(pointx_2000);
								wzCorporeLs.setPointy_2000(pointy_2000);
								wzCorporeLs.setPointx_02(pointx_02);
								wzCorporeLs.setPointy_02(pointy_02);
								
								wzCorporeLsList.add(wzCorporeLs);
							}
							
						}
						recordset_address.moveNext();
					}
					System.out.println("雨场有效标的个数"+j);
					count+=j;
					recordset_new.moveNext();
				}
				System.out.println("雨场标的总数"+count);
			}
		}
		return wzCorporeLsList;
	}
	/**
	 * @功能：操作降雨数据的标的信息
	 *  @param datasetVector_new： 为dataEarlyWarnRain_new数据集
	 * 	@param 	riskmap_address_new：为RISKMAP_ADDRESS_POINT 数据集
	 * @param 	datasetVector_county：为chinna_city县级 数据集
	 * @return void
	 * @author liqiankun
	 * @时间：20200119
	 * @修改记录：修改
	 */
	public Map<String, Object> operateRainCorpore_new(DatasourceConnectionInfo dscon,
			DatasetVector datasetVector_new,DatasetVector riskmap_address_new,DatasetVector datasetVector_county){
		Map<String, Object> map = new HashMap<String, Object>();
		List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
		String gridCode ="",rainCode ="Rain";
		try {
			if(datasetVector_new.getRecordCount()>0){
				int count=0;
				//全覆盖县的信息
				Map<String,Set<String>> mapCover = new HashMap<String, Set<String>>();
				Set<String> setIntersect = new HashSet<String>();
				int coverCount = 0;
				Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
				while (!recordset_new.isEOF()){
					String gridCodeCover = recordset_new.getString("GRIDCODE");
					GeoRegion geoRegionContain=(GeoRegion)recordset_new.getGeometry();
					QueryParameter param_contain=  MapUtils.organSpatialQueryParam(null,null,geoRegionContain,"geoRegionContain");
					//查询到全覆盖的县级信息
					Recordset recordset_county = datasetVector_county.query(param_contain);
					Set<String> strSet = new HashSet<String>();
					while (!recordset_county.isEOF()){
						coverCount++;
						String adminCode = recordset_county.getString("ADMINCODE");
						strSet.add(adminCode);
						setIntersect.add(adminCode);
						recordset_county.moveNext();
					}
					mapCover.put(gridCodeCover, strSet);
					recordset_new.moveNext();
				}
				// 全部覆盖的县级区域的个数
				System.out.println("============cover=county=num============="+coverCount);
				//重新对该数据进行操作,第二阶段操作
				recordset_new.moveFirst();
				int countAddress = 0; // 统计没有全覆盖标的个数
				long startTimeSecond = System.currentTimeMillis();
				while (!recordset_new.isEOF()){
					gridCode = recordset_new.getString("GRIDCODE");
					System.out.println("=============="+gridCode);
					GeoRegion geoRegion=(GeoRegion)recordset_new.getGeometry();
					//查询相交的县的信息
					QueryParameter param_Intersect=  MapUtils.organSpatialQueryParam(null,null,geoRegion,"geoRegionIntersect");
					Recordset recordset_county_Intersect = datasetVector_county.query(param_Intersect);
					// 用于统计总的相交的县
					System.out.println("========相交的县的个数county==========="+recordset_county_Intersect.getRecordCount());
					// 对相交的县进行循环
					int cycleNum = 0;
					while (!recordset_county_Intersect.isEOF()){
						String proName = recordset_county_Intersect.getString("PROVINCENAME");
						// 县级名称
						String adminCode = recordset_county_Intersect.getString("AdminCode");
						System.out.println("==========AdminCode================"+adminCode+":"+proName);
						if(StringUtils.isNotBlank(adminCode)&&!setIntersect.contains(adminCode)){
							cycleNum ++;
							QueryParameter param_single=  MapUtils.organSpatialQueryParam(dscon,null,geoRegion,"geoRegion");
							Recordset queryRecordset_address = riskmap_address_new.query(param_single);
							countAddress += queryRecordset_address.getRecordCount();
							System.out.println("========contain==总标的数量sum==============="+countAddress);
							log.info("========contain==总标的数量sum==============="+countAddress);
							int j=0;
							while(!queryRecordset_address.isEOF()){
								// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
								String  proposalNo11 = queryRecordset_address.getString("PROPOSALNO")+"";
								String  proposalNo = queryRecordset_address.getFieldValue("PROPOSALNO")+"";
								if(queryRecordset_address!=null&&queryRecordset_address.getFieldValue("ADDRESSID")!=null&&
										StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){ 
									j++;
									Integer  smid = Integer.parseInt(queryRecordset_address.getFieldValue("ADDRESSID")+"");
									String addressName = queryRecordset_address.getFieldValue("ADDRESSNAME")+"";
									BigDecimal pointx_2000 = new BigDecimal(queryRecordset_address.getString("POINTX_2000"));
									BigDecimal pointy_2000 = new BigDecimal(queryRecordset_address.getString("POINTY_2000"));
									BigDecimal pointx_02 = new BigDecimal(queryRecordset_address.getString("POINTX_02"));
									BigDecimal pointy_02 = new BigDecimal(queryRecordset_address.getString("POINTY_02"));
									// 求标的表中是否存在该信息,若不存在则进行添加
									long totalCount =  getCorporeTotalCount(rainCode ,smid,"","WZ_CORPORELS");
									if(totalCount==0){
										WzCorporeLs wzCorporeLs =new WzCorporeLs();
										WzCorporeLsId  wzCorporeLsId = new WzCorporeLsId();
										wzCorporeLsId.setTfbh(rainCode);
										wzCorporeLsId.setMid(smid);
										wzCorporeLs.setId(wzCorporeLsId);
										
//									wzCorporeLs.setCenterX(centerX);
//									wzCorporeLs.setCenterY(centerY);
										wzCorporeLs.setCityCode(gridCode);
										wzCorporeLs.setAddressName(addressName);
										// 状态标志位为2 ，则是未生成标的保额信息
										wzCorporeLs.setValidStatus("2");
										
										wzCorporeLs.setPointx_2000(pointx_2000);
										wzCorporeLs.setPointy_2000(pointy_2000);
										wzCorporeLs.setPointx_02(pointx_02);
										wzCorporeLs.setPointy_02(pointy_02);
										
										wzCorporeLsList.add(wzCorporeLs);
									}
								}
								queryRecordset_address.moveNext();
							}
							System.out.println("雨场有效标的个数"+j);
							log.info("雨场有效标的个数"+j);
							count+=j;
						}
						
						recordset_county_Intersect.moveNext();
					}
					recordset_new.moveNext();
				}
				map.put("wzCorporeLs", wzCorporeLsList);
				map.put("mapCover", mapCover);
				long endTimeSecond = System.currentTimeMillis();
				System.out.println("===========valid=count========================"+count);
				log.info("===========valid=count================"+count+"=======secondTime=========="+(endTimeSecond-startTimeSecond)/1000+"s");
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.info("处理标的异常：" + e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * @功能：查询表中标的的数量
	 * @param 
	 * @return 
	 * @author liqiankun
	 * @时间：20190520
	 * @修改记录：
	 */
	public long getCorporeTotalCount(String tfbh ,Integer mid,String proCityFlag,String tableName){
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		long totalCount = 0L;
		String sql =" select count(*) from "+tableName+" where TFBH='"+tfbh+"' and MID="+mid;
		try {
			conn = slaveJdbcTemplate.getDataSource().getConnection();
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			for(;rs.next();) {
				totalCount = rs.getLong(1);
			}
		} catch (SQLException e) {
			log.info("查询异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("查询异常:"+e);
		}finally {
			MapUtils.releaseResources(stat, conn, rs);
		}
		return totalCount;
	}
	/*获取该记录集的面数据信息*/
	public void getRecordGeoMap(DatasetVector datasetVector_new,
			Recordset recordset_rain,Recordset recordset_new){
		// key值为GRIDCODE，value值为面数据的集合
//		Map<String,List<Geometry>>  geometryMap = new HashMap<String, List<Geometry>>();
		Map<Map<String,Object>,List<Geometry>>  geometryMap = new HashMap<Map<String,Object>, List<Geometry>>();
		try {
		 if(datasetVector_new!=null){
			     /*当与台风进行关联的时候，我们可以进行台风编号作为查询条件进行查询*/
//					 String filter = "GRIDCODE ="+tfbh;
//					 Recordset recordset_7 = datasetVector_7.query(filter,CursorType.DYNAMIC ); 
			 Map<Integer,Feature>  features= recordset_rain.getAllFeatures();
			 if(recordset_rain.getRecordCount()>0){
				 for(Feature feature:features.values()){
					 // 这个是降雨的深度值
					 String gridCode =   feature.getString("GRIDCODE");
					 String dataTime =   feature.getString("dataTime");
					 if(StringUtils.isBlank(gridCode)){
						 continue;
					 }
					 Map<String,Object> keyMerge = new HashMap<String,Object>();
					 keyMerge.put("GRIDCODE",gridCode);
					 keyMerge.put("dataTime",dataTime);
					 Geometry geometry= feature.getGeometry(); 
					 if(geometryMap.containsKey(keyMerge)){
						 List<Geometry> geometryList= geometryMap.get(keyMerge);
						 geometryList.add(geometry);
						 geometryMap.put(keyMerge, geometryList);
					 }else {
						 List<Geometry> geometryList= new ArrayList<Geometry>();
						 geometryList.add(geometry);
						 geometryMap.put(keyMerge, geometryList);
					 }
				 }
			 }
			for(Map<String,Object> key : geometryMap.keySet()){
//				Map<String , Object> map = new HashMap<String, Object>();
//				map.put("GRIDCODE", key);
				MapUtils.unionCycleGeometryList(geometryMap.get(key),recordset_new,key);
//				datasetVector_new.append(recordset_new);
			} 
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*对降雨信息增加新的省市信息    addby liqiankun 20200408 begin */
	/**
	 * @功能：操作降雨数据的标的信息(增加降雨影响标的的省市县信息，数据库中需要增加相应的字段信息)
	 * @param datasetVector_new： 为dataEarlyWarnRain_new数据集
	 * 	@param 	riskmap_address_new：为RISKMAP_ADDRESS_POINT 数据集
	 * @return void
	 * @author liqiankun
	 * @时间：20200407
	 * @修改记录：
	 */
	public List<WzCorporeLs> operateRainCorpore_new(Datasource datasource,DatasourceConnectionInfo dscon,
			DatasetVector datasetVector_new,DatasetVector datasetVector_address,String date){
		//将降雨面之内的有效标的统计到一个新的数据集中
		QueryParameter  queryParameter =MapUtils.organSpatialQueryParam(dscon,datasetVector_new,null,"datasetVector");
		Recordset recordset_address_old = datasetVector_address.query(queryParameter);
		System.out.println("与面相交的标的点的个数"+recordset_address_old.getRecordCount());
		// 创建新的点数据集
		RiskMapAddressField riskMapAddressField =new RiskMapAddressField();
		DatasetVector riskmap_address_new = MapUtils.createDataSet(riskmap_address_point,datasource,riskMapAddressField);
		System.out.println("添加到新的点数据集的标的点的个数before"+riskmap_address_new.getRecordCount());
		/*清除已有标的点数据，添加新的标的点数据集*/
		MapUtils.clearRecordset(riskmap_address_new,recordset_address_old);
		System.out.println("添加到新的点数据集的标的点的个数after"+riskmap_address_new.getRecordCount());
		
		List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
		
		List<WzCorporeLs> wzCorporeLsListNew = new ArrayList<WzCorporeLs>();
		Set<WzCorporeLs> wzCorporeLsSet = new HashSet<WzCorporeLs>();
		
		String gridCode ="";
		String rainCode = "RS"+date;
//		,rainCode ="Rain";
		if(datasetVector_new.getRecordCount()>0){
			int count=0;
			Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
			while (!recordset_new.isEOF()){
				gridCode = recordset_new.getString("GRIDCODE");
				System.out.println("=============="+gridCode);
				// 单个降雨等级的面数据集
				GeoRegion geoRegion=(GeoRegion)recordset_new.getGeometry();
				QueryParameter param_single=  MapUtils.organSpatialQueryParam(dscon,null,geoRegion,"geoRegion");
				
				Recordset recordset_address = riskmap_address_new.query(param_single);
				System.out.println(gridCode+"对应的标的 点个数："+recordset_address.getRecordCount());
				FieldInfos  fieldInfos = recordset_address.getFieldInfos();
				int count1 = fieldInfos.getCount();
				System.out.println("=====================输出字段名称");
//				for(int i=0;i<count1;i++){
//					FieldInfo  fieldInfo = fieldInfos.get(i);
//					String  name = fieldInfo.getName();
//					String  value = fieldInfo.getDefaultValue();
//					System.out.println(name+":"+value);
//				}
				// 对标的点进行循环
				int j=0;
				while(!recordset_address.isEOF()){
					// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
					String  proposalNo11 = recordset_address.getString("PROPOSALNO")+"";
					String  proposalNo = recordset_address.getFieldValue("PROPOSALNO")+"";
					if(recordset_address!=null&&recordset_address.getFieldValue("ADDRESSID")!=null&&
							StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){ 
						j++;
						Integer  smid = Integer.parseInt(recordset_address.getFieldValue("ADDRESSID")+"");
						String addressName = recordset_address.getFieldValue("ADDRESSNAME")+"";
						BigDecimal pointx_2000 = new BigDecimal(recordset_address.getString("POINTX_2000"));
						BigDecimal pointy_2000 = new BigDecimal(recordset_address.getString("POINTY_2000"));
						BigDecimal pointx_02 = new BigDecimal(recordset_address.getString("POINTX_02"));
						BigDecimal pointy_02 = new BigDecimal(recordset_address.getString("POINTY_02"));
						//获取省市县级信息
						/*县级代码*/
						String  countyCode = recordset_address.getString("COUNTYCODE");
						/*县级名称*/
						String  countyName = recordset_address.getString("COUNTYNAME");
						/*市级代码*/
						String  cityEcode = recordset_address.getString("CITYECODE");
						/*市级名称*/
						String  cityEname = recordset_address.getString("CITYENAME");
						/*省级代码*/
						String  provinceCode = recordset_address.getString("PROVINCECODE");
						/*省级名称*/
						String  provinceName = recordset_address.getString("PROVINCENAME");
						
						// 求标的表中是否存在该信息,若不存在则进行添加
						long totalCount =  getCorporeTotalCount(rainCode ,smid,"","WZ_CORPORELS");
						if(totalCount==0){
							WzCorporeLs wzCorporeLs =new WzCorporeLs();
							WzCorporeLsId  wzCorporeLsId = new WzCorporeLsId();
							wzCorporeLsId.setTfbh(rainCode);
							wzCorporeLsId.setMid(smid);
							wzCorporeLs.setId(wzCorporeLsId);
							
//							wzCorporeLs.setCenterX(centerX);
//							wzCorporeLs.setCenterY(centerY);
							wzCorporeLs.setCityCode(gridCode);
							wzCorporeLs.setAddressName(addressName);
							// 状态标志位为2 ，则是未生成标的保额信息
							wzCorporeLs.setValidStatus("2");
							
							wzCorporeLs.setPointx_2000(pointx_2000);
							wzCorporeLs.setPointy_2000(pointy_2000);
							wzCorporeLs.setPointx_02(pointx_02);
							wzCorporeLs.setPointy_02(pointy_02);
							/*省市县信息*/
							wzCorporeLs.setCountyName(countyName);
							wzCorporeLs.setCountyCode(countyCode);
							wzCorporeLs.setCityEcode(cityEcode);
							wzCorporeLs.setCityName(cityEname);
							wzCorporeLs.setProvinceCode(provinceCode);
							wzCorporeLs.setProName(provinceName);
							
							wzCorporeLsList.add(wzCorporeLs);
						}
					}
					recordset_address.moveNext();
				}
				System.out.println("雨场有效标的个数"+j);
				count+=j;
				recordset_new.moveNext();
			}
			//进行数据去重操作
			wzCorporeLsSet.addAll(wzCorporeLsList);
			wzCorporeLsListNew.addAll(wzCorporeLsSet);
			System.out.println("雨场标的总数"+count);
		}
		
		return wzCorporeLsListNew;
	}
	/**
	 * @功能：操作降雨预警数据的标的信息，分别对未来十天的降雨信息进行处理
	 * @param datasetVector_new： 为dataEarlyWarnRainYJ_new数据集
	 * 	@param 	riskmap_address_new：为RISKMAP_ADDRESS_POINT 数据集
	 * @return void
	 * @author liqiankun
	 * @时间：20191015
	 * @修改记录：
	 */
	public List<WzCorporeLs> operateRainYJCorpore_new(Datasource datasource,DatasourceConnectionInfo dscon,
			DatasetVector datasetVector_new,DatasetVector datasetVector_address,String date){
		//将降雨面之内的有效标的统计到一个新的数据集中
		QueryParameter  queryParameter =MapUtils.organSpatialQueryParam(dscon,datasetVector_new,null,"datasetVector");
		Recordset recordset_address_old = datasetVector_address.query(queryParameter);
		System.out.println("与预警降雨面相交的标的点的个数"+recordset_address_old.getRecordCount());
		// 创建新的点数据集
		RiskMapAddressField riskMapAddressField =new RiskMapAddressField();
		DatasetVector riskmap_address_new = MapUtils.createDataSet(riskmap_address_point,datasource,riskMapAddressField);
		System.out.println("添加到新的点数据集的标的点的RainYJ个数before"+riskmap_address_new.getRecordCount());
		/*清除已有标的点数据，添加新的标的点数据集*/
		MapUtils.clearRecordset(riskmap_address_new,recordset_address_old);
		System.out.println("添加到新的点数据集的标的点的RainYJ个数after"+riskmap_address_new.getRecordCount());
		
		List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
		
		List<WzCorporeLs> wzCorporeLsListNew = new ArrayList<WzCorporeLs>();
		Set<WzCorporeLs> wzCorporeLsSet = new HashSet<WzCorporeLs>();
		//进行未来十天预警的操作
		for (int i=1;i<=3;i++){
			String gridCode ="";
			date = DateUtils.getNextStringDateFormat(date,i);
			String rainCode ="RY"+date;
			if(datasetVector_new.getRecordCount()>0){
				int count=0;
				// 现在这个时间是年月日形式，不带小时
				String filterYJ = "dataTime="+date;
//				Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
				Recordset recordset_new = datasetVector_new.query(filterYJ,CursorType.DYNAMIC );
				while (!recordset_new.isEOF()){
					gridCode = recordset_new.getString("GRIDCODE");
					System.out.println("=============="+gridCode+"===="+rainCode);
					// 单个降雨等级的面数据集
					GeoRegion geoRegion=(GeoRegion)recordset_new.getGeometry();
					QueryParameter param_single=  MapUtils.organSpatialQueryParam(dscon,null,geoRegion,"geoRegion");
					
					Recordset recordset_address = riskmap_address_new.query(param_single);
					System.out.println(gridCode+"对应的标的 点个数："+recordset_address.getRecordCount());
					FieldInfos  fieldInfos = recordset_address.getFieldInfos();
					int count1 = fieldInfos.getCount();
					System.out.println("=====================输出字段名称");
//					for(int i=0;i<count1;i++){
//						FieldInfo  fieldInfo = fieldInfos.get(i);
//						String  name = fieldInfo.getName();
//						String  value = fieldInfo.getDefaultValue();
//						System.out.println(name+":"+value);
//					}
					// 对标的点进行循环
					int j=0;
					while(!recordset_address.isEOF()){
						// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
						String  proposalNo11 = recordset_address.getString("PROPOSALNO")+"";
						String  proposalNo = recordset_address.getFieldValue("PROPOSALNO")+"";
						if(recordset_address!=null&&recordset_address.getFieldValue("ADDRESSID")!=null&&
								StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){ 
							j++;
							Integer  smid = Integer.parseInt(recordset_address.getFieldValue("ADDRESSID")+"");
							String addressName = recordset_address.getFieldValue("ADDRESSNAME")+"";
							BigDecimal pointx_2000 = new BigDecimal(recordset_address.getString("POINTX_2000"));
							BigDecimal pointy_2000 = new BigDecimal(recordset_address.getString("POINTY_2000"));
							BigDecimal pointx_02 = new BigDecimal(recordset_address.getString("POINTX_02"));
							BigDecimal pointy_02 = new BigDecimal(recordset_address.getString("POINTY_02"));
							//获取省市县级信息
							/*县级代码*/
							String  countyCode = recordset_address.getString("COUNTYCODE");
							/*县级名称*/
							String  countyName = recordset_address.getString("COUNTYNAME");
							/*市级代码*/
							String  cityEcode = recordset_address.getString("CITYECODE");
							/*市级名称*/
							String  cityEname = recordset_address.getString("CITYENAME");
							/*省级代码*/
							String  provinceCode = recordset_address.getString("PROVINCECODE");
							/*省级名称*/
							String  provinceName = recordset_address.getString("PROVINCENAME");
														
							// 求标的表中是否存在该信息,若不存在则进行添加
							long totalCount =  getCorporeTotalCount(rainCode ,smid,"","WZ_CORPOREYJ");
							if(totalCount==0){
								WzCorporeLs wzCorporeLs =new WzCorporeLs();
								WzCorporeLsId  wzCorporeLsId = new WzCorporeLsId();
								wzCorporeLsId.setTfbh(rainCode);
								wzCorporeLsId.setMid(smid);
								wzCorporeLs.setId(wzCorporeLsId);
								
//								wzCorporeLs.setCenterX(centerX);
//								wzCorporeLs.setCenterY(centerY);
								wzCorporeLs.setCityCode(gridCode);
								wzCorporeLs.setAddressName(addressName);
								// 状态标志位为2 ，则是未生成标的保额信息
								wzCorporeLs.setValidStatus("2");
								
								wzCorporeLs.setPointx_2000(pointx_2000);
								wzCorporeLs.setPointy_2000(pointy_2000);
								wzCorporeLs.setPointx_02(pointx_02);
								wzCorporeLs.setPointy_02(pointy_02);
								/*省市县信息*/
								wzCorporeLs.setCountyName(countyName);
								wzCorporeLs.setCountyCode(countyCode);
								wzCorporeLs.setCityEcode(cityEcode);
								wzCorporeLs.setCityName(cityEname);
								wzCorporeLs.setProvinceCode(provinceCode);
								wzCorporeLs.setProName(provinceName);
								
								wzCorporeLsList.add(wzCorporeLs);
							}
							
						}
						recordset_address.moveNext();
					}
					System.out.println("雨场有效标的个数"+j);
					count+=j;
					recordset_new.moveNext();
				}
				// 进行去重操作
				wzCorporeLsSet.addAll(wzCorporeLsList);
				wzCorporeLsListNew.addAll(wzCorporeLsSet);
				System.out.println("雨场标的总数"+count);
			}
		}
		return wzCorporeLsListNew;
	}
	/**
	 * @功能：统计降雨的保额保费,统计各个标的点的SMID来进行查询,
	 *   procityFlag:4(按等级，比如 降雨量1-10为一等级),5(按降雨量来进行划分)
	 *   judgeFlag: 1(实况)，2（预警）
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20191017
	 * @修改记录：
	 */
	public List<WzCorporeLsP> sumRainAmount_new(List<WzCorporeLs> wzCorporeLsList,String procityFlag,String judgeFlag){
		List<WzCorporeLsP>  wzCorporeLsPList= new ArrayList<WzCorporeLsP>();
		try {
			// 组织标的信息
			Map<Map<Map<String, String>,String>,Wz_Corporels_byMid> mapCountMap = 
					this.organizeRainCorpore_new(wzCorporeLsList,procityFlag);
			/*降雨编号、降雨等级---降雨影响省市信息*/
			for(Map<Map<String, String>,String> keyMapTwo : mapCountMap.keySet()){
				/*降雨编号、降雨等级*/
				for(Map<String,String> keyMap : keyMapTwo.keySet()){
					WzCorporeLsP wzCorporeLsP =new WzCorporeLsP();
					WzCorporeLsPId id = new WzCorporeLsPId();
					List<String> midList = mapCountMap.get(keyMapTwo).getCountList();
					// 省市名称
					String cityName = mapCountMap.get(keyMapTwo).getCityName();
//					List<String> midList = mapCountMap.get(keyMap).getCountList();
					BigDecimal[]  amounts = new BigDecimal[3];
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
					String centerX="0",centerY="0";
					if(midList.size()>0&& midList !=null){
						centerX =mapCountMap.get(keyMapTwo).getCenterX();
						centerY = mapCountMap.get(keyMapTwo).getCenterY();
//						centerX = new BigDecimal(centerX).divide(new BigDecimal(midList.size())).toString();
//						centerY=new BigDecimal(centerY).divide(new BigDecimal(midList.size())).toString();
					}
					
					Date date = new Date();
					String endTime = sdf.format(date);
					String endHour = sdfHour.format(date);
					// 判断查询字段的长度
					int max = midList.size()/500;
					if(max>0){
						List<String> countList = null;
						for(int i=1;i<=max;i++){
							if(i<max){
								countList =midList.subList((i-1)*500, i*500);
							}else {
								countList = midList.subList((i-1)*500, midList.size());
							}
							BigDecimal [] amountSingle = this.doworkSumAmount(countList,endTime,endHour);
							amounts[0] = amounts[0].add(amountSingle[0]);
							amounts[1] = amounts[1].add(amountSingle[1]);
							amounts[2] = amounts[2].add(amountSingle[2]);
						}
					}else {
						amounts = this.doworkSumAmount(midList,endTime,endHour);
					}
					for(String key : keyMap.keySet()){
						id.setTfbh(key);
						// 降雨影响等级
						id.setProcityFlag(keyMap.get(key));
					}
					// 降雨影响省市信息
					String cityProCode = keyMapTwo.get(keyMap);
					id.setCityCode(cityProCode);
					// 省市名称
					wzCorporeLsP.setCityName(cityName);
					wzCorporeLsP.setId(id);
					wzCorporeLsP.setJAmount(BigDecimal.ZERO);
					List<WzCorporeLsP> wzCorporeLsPListOld= new ArrayList<WzCorporeLsP>();
					if("1".equals(judgeFlag)){
						/*查询该标的整合信息是否存在*/
						wzCorporeLsPListOld=  this.searchWzCorporeLsP(id);
					}else if("2".equals(judgeFlag)){
						// 进行数据的复制
						List<WzCorporeYjP> wzCorporeYJPListOld  =this.searchWzCorporeYJP(id);
						wzCorporeLsPListOld = BeanUtils.convertByType(wzCorporeYJPListOld, WzCorporeLsP.class);
					}
					/*查询该标的整合信息是否存在*/
//					List<WzCorporeLsP> wzCorporeLsPListOld=  this.searchWzCorporeLsP(id);
					if(wzCorporeLsPListOld==null||wzCorporeLsPListOld.size()==0){
						wzCorporeLsP.setCorporeSum(midList.size());
						wzCorporeLsP.setTotalAmount(amounts[0]);
						wzCorporeLsP.setQAmount(amounts[1]);
						wzCorporeLsP.setGAmount(amounts[2]);
						// 中心点X,Y 
						wzCorporeLsP.setCenterX(new BigDecimal(centerX).divide(new BigDecimal(midList.size()),8,RoundingMode.HALF_UP));
						wzCorporeLsP.setCenterY(new BigDecimal(centerY).divide(new BigDecimal(midList.size()),8,RoundingMode.HALF_UP));
						wzCorporeLsPList.add(wzCorporeLsP);
					}else {
						WzCorporeLsP wzCorporeLsPNew=wzCorporeLsPListOld.get(0);
//							BeanUtils.copyProperties(wzCorporeLsPNew, wzCorporeLsPListOld.get(0));
						wzCorporeLsPNew.setCorporeSum(midList.size()+wzCorporeLsPNew.getCorporeSum());
						wzCorporeLsPNew.setTotalAmount(wzCorporeLsPNew.getTotalAmount().add(amounts[0]));
						wzCorporeLsPNew.setQAmount(wzCorporeLsPNew.getQAmount().add(amounts[1]));
						wzCorporeLsPNew.setGAmount(wzCorporeLsPNew.getGAmount().add(amounts[2]));
						// 重新计算中心点的位置
						BigDecimal x= wzCorporeLsPNew.getCenterX().add(new BigDecimal(wzCorporeLsPNew.getCorporeSum())
						.multiply(wzCorporeLsPNew.getCenterX())).divide(new BigDecimal(wzCorporeLsPNew.getCorporeSum()+midList.size()),8,RoundingMode.HALF_UP);
						BigDecimal y= wzCorporeLsPNew.getCenterY().add(new BigDecimal(wzCorporeLsPNew.getCorporeSum())
						.multiply(wzCorporeLsPNew.getCenterY())).divide(new BigDecimal(wzCorporeLsPNew.getCorporeSum()+midList.size()),8,RoundingMode.HALF_UP);;
						
						wzCorporeLsPNew.setCenterX(x);
						wzCorporeLsPNew.setCenterY(y);
//							wzCorporeLsP.setCorporeSum(midList.size());
//							wzCorporeLsP.setTotalAmount(amounts[0]);
//							wzCorporeLsP.setQAmount(amounts[1]);
//							wzCorporeLsP.setGAmount(amounts[2]);
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wzCorporeLsPList;

	}
	// 重新组织标的信息
	public Map<Map<Map<String, String>,String>,Wz_Corporels_byMid> organizeRainCorpore_new(List<WzCorporeLs> wzCorporeLsList,String flag){
//		Map<Map<String, String>,Wz_Corporels_byMid> mapCountMap=
//				  new HashMap<Map<String,String>, Wz_Corporels_byMid>();
		Map<Map<Map<String, String>,String>,Wz_Corporels_byMid> mapCountMap=
				  new HashMap<Map<Map<String, String>,String>, Wz_Corporels_byMid>();
		if(wzCorporeLsList!=null&&wzCorporeLsList.size()>0){
			Map<Map<Map<String, String>,String>,String> mapPro = new HashMap<Map<Map<String, String>,String>,String>();

			for(WzCorporeLs wzCorporeLs:wzCorporeLsList){
				String tfbh = wzCorporeLs.getId().getTfbh();
				//降雨等级
				String cityCode = wzCorporeLs.getCityCode();
				// key：台风编号，value:降雨等级
				Map<String,String> map =  this.organRainMap(tfbh,cityCode, flag);
				// 市级编码
				String cityEcode = wzCorporeLs.getCityEcode();
				// 省级编码
				String provinceCode = wzCorporeLs.getProvinceCode();
				// 市级名称
				String cityName =  wzCorporeLs.getCityName();
				//省级名称
				String proName  = wzCorporeLs.getProName();
				//key:分别是降雨编号和降雨等级,value是市级编码和省级编码的集合
				Map<Map<String, String>,String> mapTwo = new HashMap<Map<String, String>,String>();
				mapTwo.put(map, cityEcode);
				if(!mapPro.containsKey(mapTwo)){
					mapPro.put(mapTwo, cityName);
				}
				Map<Map<String, String>,String> mapThree = new HashMap<Map<String, String>,String>();
				mapThree.put(map, provinceCode);
				if(!mapPro.containsKey(mapThree)){
					mapPro.put(mapThree, proName);
				}
			}
			for(WzCorporeLs wzCorporeLs:wzCorporeLsList){
				String centerX = wzCorporeLs.getPointx_2000().toString();
				String centerY = wzCorporeLs.getPointy_2000().toString();
				String mid =wzCorporeLs.getId().getMid().toString();
				// 市级编码
				String cityEcode = wzCorporeLs.getCityEcode();
				// 省级编码
				String provinceCode = wzCorporeLs.getProvinceCode();
				// 市级名称
//				String cityName =  wzCorporeLs.getCityName();
				//省级名称
//				String proName  = wzCorporeLs.getProName();
//				Map<String,String> map =  this.organRainMap(tfbh,cityCode, flag);
				for(Map<Map<String,String>,String> mapProKey:mapPro.keySet()){
					String cityProName = mapPro.get(mapProKey);
					for (Map<String,String> mapKey:mapProKey.keySet()){
						String cirProCode =mapProKey.get(mapKey);
						boolean isExist = false;
						if(cirProCode.endsWith("0000")&&provinceCode.equals(cirProCode)){
							// 与省级编码匹配
							isExist = true;
						}else if(cirProCode.equals(cityEcode)){
							// 与市级编码匹配
							isExist = true;
						}else{
							break;
						}
						if(isExist){
							// 假如key值 存在则进行叠加，否则新建
							if(mapCountMap.containsKey(mapProKey)){
								mapCountMap.get(mapProKey).getCountList().add(mid);
								mapCountMap.get(mapProKey).setCenterX((Double.parseDouble(mapCountMap.get(mapProKey).getCenterX())+Double.parseDouble(centerX))+"");
								mapCountMap.get(mapProKey).setCenterY((Double.parseDouble(mapCountMap.get(mapProKey).getCenterY())+Double.parseDouble(centerY))+"");
							}else{
								/*不存在则新建*/
								Wz_Corporels_byMid corporelsData =
										new Wz_Corporels_byMid(new ArrayList<String>(Arrays.asList(mid)),cityProName,centerX,centerY,flag);
								mapCountMap.put(mapProKey, corporelsData);
							}
						}
					}
					
				}
				
			}
		}
		return mapCountMap;
	}
	/**
	 * @功能：执行删除语句
	 * @author liqiankun
	 * @throws Exception
	 * @时间：20200409
	 */
	public void deleteSqlInfo(String sqlSearch){
		System.out.println("=====deleteSqlInfo===start=====");
		Connection conn = null;
	    PreparedStatement stat = null;
	    try {
			conn = slaveJdbcTemplate.getDataSource().getConnection();
			stat = conn.prepareStatement(sqlSearch);
			stat.executeUpdate();
			System.out.println("=====deleteSqlInfo===end=====");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MapUtils.releaseResources(stat, conn, null);
		}  
	}
	/*对降雨信息增加新的省市信息    addby liqiankun 20200408 begin */
	/*对从气象局中获取的台风数据进行重新处理    addby liqiankun 20200412 begin */
	/**
	 * @功能：用于组织台风基本信息、实时路径和预报路径信息
	 * 首先判断是否存在这条数据，存在则判断是属于实时路径还是预报路径，获取到台风发生时间
	 * @author liqiankun
	 * @param 
	 * @return
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public List<TyphoonLatestInfo> organizeTyphoonInfo_new(List<TyphoonPath> typhoonPathList){
		List<TyphoonLatestInfo>  typhoonLatestInfoList  = new ArrayList<TyphoonLatestInfo>();
		// 分别存储
		Map<String, TyphoonLatestInfo> mapTarget =new HashMap<String, TyphoonLatestInfo>();
//		Map<String, Map<String,Object>> mapObj =new HashMap<String, Map<String,Object>>();
		if(typhoonPathList!=null&&typhoonPathList.size()>0){
			for(TyphoonPath typhoonPath:typhoonPathList){
				// Num_Nati 国内编号
				if(mapTarget.containsKey(typhoonPath.getNum_Nati())){
					// 获取之前处理的台风信息
					TyphoonLatestInfo typhoonLatestInfoOld=  mapTarget.get(typhoonPath.getNum_Nati());
					
					TyphoonLatestInfo typhoonLatestInfoNew = this.organizeTyphoonLsOrYjInfo_new(typhoonPath,typhoonLatestInfoOld,true);
					mapTarget.put(typhoonPath.getNum_Nati(), typhoonLatestInfoNew);
				}else {
//					Map<String, Object> map =new HashMap<String, Object>();
//					// 组织台风基本信息
					TyphoonLatestInfo typhoonLatestInfoNew = this.organizeTyphoonLsOrYjInfo_new(typhoonPath,null,false);
					mapTarget.put(typhoonPath.getNum_Nati(), typhoonLatestInfoNew);
				}
			}
			for(String key:mapTarget.keySet()){
				typhoonLatestInfoList.add(mapTarget.get(key));
			}
		}
		return typhoonLatestInfoList;
	}
	/**
	 * @功能：将台风主表已经子表的信息组织到map中，key值为台风编号
	 * @author liqiankun
	 * @param  typhoonPath 台风获取对象， mapTarget ：存储的map，
	 * 		   isExist：map中是否存在给key值，存在为true，不存在为false
	 * @return Map<String, Object>
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public TyphoonLatestInfo organizeTyphoonLsOrYjInfo_new(TyphoonPath typhoonPath,TyphoonLatestInfo typhoonLatestInfoOld,boolean isExist){
		// map中存在该编号信息
		if(isExist){
			//获取基本信息
			TyphoonLatestInfo typhoonLatestInfo = typhoonLatestInfoOld;
			//判断是预报还是历史信息,true为历史，false为预报
//			boolean flag =  MapUtils.compareDateSize(typhoonPath);
			//预报时效 "Validtime": "0",是实况，“6”6小时预报  ，这个字段只要值为0 就是实况，不为0 就是预报台风路径。
			boolean flag = false;
			if(StringUtils.isNotBlank(typhoonPath.getValidtime())){
				flag = "0".equals(typhoonPath.getValidtime().trim());
			}
			// 组织台风基本信息，如果ISACTIVE 为1 则更新标志位，如果是0 则不进行更新
//			typhoonLatestInfo = this.organizeTyphoonLatestInfo(typhoonPath);
			if("0".equals(typhoonPath.getISACTIVE())){
				typhoonLatestInfo.setIsActive(typhoonPath.getISACTIVE());
			}
			if(flag){
				// 如果是历史，则需要进行比较基本信息中的发生时间是否是该点发生之前
				typhoonLatestInfo = MapUtils.compareObjectDateSize(typhoonPath, typhoonLatestInfo);
				//组织台风历史信息
				TyphoonHistoryPath typhoonHistoryPath = this.organizeTyphoonHistoryPath(typhoonPath);
				if(typhoonHistoryPath!=null){
					//看该list是否存在
					if(typhoonLatestInfo.getTyphoonHistoryPathList()!=null&&
							typhoonLatestInfo.getTyphoonHistoryPathList().size()>0){
						List<TyphoonHistoryPath> typhoonHistoryPathListOld = 
								typhoonLatestInfo.getTyphoonHistoryPathList();
						//如果list中已经有该对象，则不添加
						if(!typhoonHistoryPathListOld.contains(typhoonHistoryPath)){
							typhoonHistoryPathListOld.add(typhoonHistoryPath);
						}
						//新增到list集合中
						typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathListOld);
					}else {
						// 如果没有则需要新增
						List<TyphoonHistoryPath> typhoonHistoryPathList = new ArrayList<TyphoonHistoryPath>();
						typhoonHistoryPathList.add(typhoonHistoryPath);
						typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathList);
					}
				}
			}else{
				// 台风预报信息
				WzTFYblj  wzTFYblj =this.organizeWzTFYblj(typhoonPath);
				if(wzTFYblj!=null){
					//看该list是否存在
					if(typhoonLatestInfo.getWzTFYbljList()!=null&&
							typhoonLatestInfo.getWzTFYbljList().size()>0){
						List<WzTFYblj> wzTFYbljListOld=typhoonLatestInfo.getWzTFYbljList();
						if(!wzTFYbljListOld.contains(wzTFYblj)){
							wzTFYbljListOld.add(wzTFYblj);
						}
 						//新增到list集合中
						typhoonLatestInfo.setWzTFYbljList(wzTFYbljListOld);
					}else {
						// 如果没有则需要新增
						List<WzTFYblj> wzTFYbljList = new ArrayList<WzTFYblj>();
						wzTFYbljList.add(wzTFYblj);
						typhoonLatestInfo.setWzTFYbljList(wzTFYbljList);
					}
				}
				
			}
			return typhoonLatestInfo;
			
		}else {
			// map中不存在该编号信息
			TyphoonLatestInfo typhoonLatestInfo =  null;
			try {
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				Map<String, Object> map =new HashMap<String, Object>();
				// 组织台风基本信息，如果ISACTIVE 为1 则更新标志位，如果是0 则不进行更新
				typhoonLatestInfo = this.organizeTyphoonLatestInfo(typhoonPath);
//				TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
//				typhoonLatestInfo.setTfbh(typhoonPath.getNum_Nati());
				//判断是预报还是历史信息,true为历史，false为预报
//				boolean flag =  MapUtils.compareDateSize(typhoonPath);
				boolean flag = false;
				if(StringUtils.isNotBlank(typhoonPath.getValidtime())){
					flag = "0".equals(typhoonPath.getValidtime().trim());
				}
				if(flag){
					// 台风发生时间
//					Date datetime = MapUtils.getTyphoonDate(typhoonPath,"SH");
//					if(datetime!=null){
//						typhoonLatestInfo.setTfDate(datetime);
//					}
					
//					typhoonLatestInfo.setTfDate(sdf.parse(typhoonPath.getDatetime()));
					// 台风历史信息
//					TyphoonHistoryPath typhoonHistoryPath = new TyphoonHistoryPath();
					TyphoonHistoryPath typhoonHistoryPath = this.organizeTyphoonHistoryPath(typhoonPath);
					if(typhoonHistoryPath!=null){
						List<TyphoonHistoryPath> typhoonHistoryPathList = new ArrayList<TyphoonHistoryPath>();
						typhoonHistoryPathList.add(typhoonHistoryPath);
						typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathList);
					}
				}else {
					// 台风预报信息
					WzTFYblj wzTFYblj = this.organizeWzTFYblj(typhoonPath);
//					WzTFYblj wzTFYblj =new WzTFYblj();
//					WzTFYbljId  id =new WzTFYbljId();
//					wzTFYblj.setId(id);
					if(wzTFYblj!=null){
						List<WzTFYblj> wzTFYbljList = new ArrayList<WzTFYblj>();
						wzTFYbljList.add(wzTFYblj);
						typhoonLatestInfo.setWzTFYbljList(wzTFYbljList);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typhoonLatestInfo;
		}
		
	}
	/*对从气象局中获取的台风数据进行重新处理    addby liqiankun 20200412 end */
	/**
	 * @功能：更改标志位,用来进行占位
	 * @author liqiankun
	 * @param 
	 * @return
	 * @throws Exception
	 * @时间：20200628
	 * @修改记录:
	 */
	public RiskResponseVo updateWzTfbhFlag(String targetFlag,String sourceFlag){
		System.out.println("=====updateWzTfbhFlag===begin=====target:"+targetFlag+"source:"+sourceFlag);
		RiskResponseVo ajaxResult =new RiskResponseVo();
		Connection conn = null;
	    PreparedStatement stat = null;
	    try {
	    	String sql ="update WZ_TFBH set ISUSE='"+targetFlag +"' where ISUSE ='"+sourceFlag+"'";
//		    	String sql ="update WZ_TFBH set ISACTIVE='"+targetFlag+"' where ISACTIVE='"+sourceFlag+"'";
			conn = slaveJdbcTemplate.getDataSource().getConnection();
			stat = conn.prepareStatement(sql);
			stat.executeUpdate();
			System.out.println("=====updateWzTfbhFlag===end=====");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MapUtils.releaseResources(stat, conn, null);
		}
		return ajaxResult;
	}
}
