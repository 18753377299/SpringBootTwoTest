//package com.picc.riskctrl.map.service.spring;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import ins.framework.cache.CacheManager;
//import ins.framework.cache.CacheService;
//import ins.framework.dao.database.DatabaseDao;
//import ins.framework.dao.database.support.Page;
//import ins.framework.dao.database.support.QueryRule;
//import ins.framework.lang.Datas;
//import ins.framework.rpc.annotation.Rpc;
//import ins.framework.web.AjaxResult;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.ResourceBundle;
//import java.util.Set;
//import java.util.UUID;
//import java.util.regex.Pattern;
//
//import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.SimpleHttpConnectionManager;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.params.HttpClientParams;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StreamUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.picc.riskctrl.common.RiskControlConst;
//import com.picc.riskctrl.common.datasources.service.facade.DataSourcesService;
//import com.picc.riskctrl.common.model.UserInfo;
//import com.picc.riskctrl.common.schema.PrpCaddress;
//import com.picc.riskctrl.common.schema.PrpCaddressId;
//import com.picc.riskctrl.common.schema.PrpCaddressTemp;
//import com.picc.riskctrl.common.schema.PrpCaddressTempId;
//import com.picc.riskctrl.common.schema.PrpCcoins;
//import com.picc.riskctrl.common.schema.PrpCcoinsId;
//import com.picc.riskctrl.common.schema.PrpCcoinsTemp;
//import com.picc.riskctrl.common.schema.PrpCcoinsTempId;
//import com.picc.riskctrl.common.schema.PrpCinsured;
//import com.picc.riskctrl.common.schema.PrpCinsuredId;
//import com.picc.riskctrl.common.schema.PrpCinsuredTemp;
//import com.picc.riskctrl.common.schema.PrpCinsuredTempId;
//import com.picc.riskctrl.common.schema.PrpCitemKind;
//import com.picc.riskctrl.common.schema.PrpCitemKindId;
//import com.picc.riskctrl.common.schema.PrpCitemKindTemp;
//import com.picc.riskctrl.common.schema.PrpCitemKindTempId;
//import com.picc.riskctrl.common.schema.PrpCmain;
//import com.picc.riskctrl.common.schema.PrpCmainTemp;
//import com.picc.riskctrl.common.schema.RiskDcode;
//import com.picc.riskctrl.common.schema.RiskMapAddress;
//import com.picc.riskctrl.common.schema.RiskMapAddressModify;
//import com.picc.riskctrl.common.schema.RiskMapAddressModifyId;
//import com.picc.riskctrl.common.schema.RiskMapDisaster;
//import com.picc.riskctrl.common.schema.RiskMapFloodAddress;
//import com.picc.riskctrl.common.schema.RiskMapGFormManager;
//import com.picc.riskctrl.common.schema.RiskMapInsured;
//import com.picc.riskctrl.common.schema.RiskMapInsuredId;
//import com.picc.riskctrl.common.schema.RiskMapItemkind;
//import com.picc.riskctrl.common.schema.RiskMapItemkindId;
//import com.picc.riskctrl.common.schema.RiskMapMain;
//import com.picc.riskctrl.common.schema.RiskMapMainId;
//import com.picc.riskctrl.common.schema.RiskMapQFormManager;
//import com.picc.riskctrl.common.schema.RiskMapTyphoonBlack;
//import com.picc.riskctrl.common.schema.RiskWarningPush;
//import com.picc.riskctrl.common.schema.WzCorporeLs;
//import com.picc.riskctrl.common.schema.WzCorporeYj;
//import com.picc.riskctrl.common.schema.WzCorporeYjP;
//import com.picc.riskctrl.common.schema.vo.RiskMapAddressVo;
//import com.picc.riskctrl.common.schema.vo.RiskMapMainVo;
//import com.picc.riskctrl.common.service.facade.DictService;
//import com.picc.riskctrl.common.service.facade.PrpdcompanyService;
//import com.picc.riskctrl.common.util.FTPUtil;
//import com.picc.riskctrl.common.util.MapTransferUtils;
//import com.picc.riskctrl.common.util.MapUtils;
//import com.picc.riskctrl.common.vo.ExchRateVo;
//import com.picc.riskctrl.common.vo.PrplClaimVo;
//import com.picc.riskctrl.map.service.facade.MapService;
//import com.picc.riskctrl.map.utils.MapUtil;
//import com.picc.riskctrl.map.vo.WzCorporeLsPVo;
//import com.picc.riskctrl.map.vo.WzCorporeYjPVo;
//import com.picc.riskctrl.map.vo.WzTFLsljVo;
//import com.picc.riskctrl.map.vo.WzTFYbljVo;
//import com.picc.riskctrl.map.vo.exactsearch.Pinyin;
//import com.picc.riskctrl.map.vo.request.LongAndLatitude;
//import com.picc.riskctrl.map.vo.request.MapBound;
//import com.picc.riskctrl.map.vo.request.MapMoreQuery;
//import com.picc.riskctrl.map.vo.request.MapQuery;
//import com.picc.riskctrl.map.vo.request.MapRequestInfoVo;
//import com.picc.riskctrl.map.vo.request.RiskMapFloodInfoVo;
//import com.picc.riskctrl.map.vo.request.RiskMapFloodUpdateVo;
//import com.picc.riskctrl.map.vo.request.RiskMapRegionVo;
//import com.picc.riskctrl.map.vo.request.RiskMapTyphoonBlackVo;
//import com.picc.riskctrl.map.vo.request.WZ_tfbh;
//import com.picc.riskctrl.map.vo.request.WZ_tfbhVo;
//import com.picc.riskctrl.map.vo.response.City;
//import com.picc.riskctrl.map.vo.response.CityVo;
//import com.picc.riskctrl.map.vo.response.CurrentDistrict;
//import com.picc.riskctrl.map.vo.response.Gps;
//import com.picc.riskctrl.map.vo.response.GridValue;
//import com.picc.riskctrl.map.vo.response.HouseNumber;
//import com.picc.riskctrl.map.vo.response.HttpResponseAddress;
//import com.picc.riskctrl.map.vo.response.HttpResponseAddressReset;
//import com.picc.riskctrl.map.vo.response.MapMainByAddrVo;
//import com.picc.riskctrl.map.vo.response.MapQueryResponse;
//import com.picc.riskctrl.map.vo.response.Poi;
//import com.picc.riskctrl.map.vo.response.Pois;
//import com.picc.riskctrl.map.vo.response.Province;
//import com.picc.riskctrl.map.vo.response.ProvinceVo;
//import com.picc.riskctrl.map.vo.response.SearchResult;
//import com.picc.riskctrl.map.vo.response.TfWordRespVo;
//import com.picc.riskctrl.map.vo.response.WZ_tfbhResponse;
//import com.picc.riskctrl.map.vo.response.dangerManage.DangerManageMapInfo;
//import com.picc.riskctrl.riskins.service.facade.RiskInsService;
//import com.picc.riskctrl.typhoonearlywarning.service.facade.TyphoonEarlyWarningService;
//import com.sinosoft.bpsdriver.domain.getUserMsg.UserMsgResInfo;
//import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
//import com.sinosoft.bpsdriver.service.facade.UserMgrAPIService;
//import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;
//import com.sinosoft.bpsdriver.service.spring.UserMgrAPIServiceImpl;
//import com.sinosoft.dmsdriver.model.PrpDexch;
//import com.sinosoft.dmsdriver.service.server.DictAPIService;
//import com.supermap.data.CursorType;
//import com.supermap.data.DatasetVector;
//import com.supermap.data.Datasource;
//import com.supermap.data.DatasourceConnectionInfo;
//import com.supermap.data.EngineType;
//import com.supermap.data.Feature;
//import com.supermap.data.GeoPoint;
//import com.supermap.data.GeoRegion;
//import com.supermap.data.Geometrist;
//import com.supermap.data.Geometry;
//import com.supermap.data.QueryParameter;
//import com.supermap.data.Recordset;
//import com.supermap.data.SpatialQueryMode;
//import com.supermap.data.Toolkit;
//import com.supermap.data.Workspace;
//import com.supermap.data.WorkspaceConnectionInfo;
//import com.supermap.data.WorkspaceType;
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//
////@Rpc
//@Slf4j
//@Service(value = "mapService")
//public class MapServiceSpringImpl implements MapService {
//	@Autowired
//	DictService dictService;
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//	@Autowired
//	DatabaseDao databaseDao;
//	@Autowired
//	TyphoonEarlyWarningService TyphoonEarlyWarningService;
//	@Autowired
//	DataSourcesService dataSourcesService;
//	// @Autowired
//	// SimpleDriverDataSource slaveDataSource;
//	@Autowired
//	JdbcTemplate slaveJdbcTemplate;
//	@Autowired
//	PrpdcompanyService prpdcompanyService;
//	@Autowired
//	RiskInsService riskInsService;
//
//	private static final ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//	// private static final String url = bundle.getString("riskMap_Typhoon");
//	// private static final String INSERT_TFMAIN = "insert into
//	// "+url+"(SMID,SMX,SMY,DANGERID,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02,DANGERADDRESS,SCORE,VALIDSTATUS,DANGERFLAG)
//	// values(?,?,?,?,?,?,?,?,?,?,?,?)";
//	// private static String url = "";
//	// private static String INSERT_TFMAIN ="";
//
//	private static final CacheService cacheManager = CacheManager.getInstance("local");
//
//	public static final Logger log = LoggerFactory.getLogger("RISKCONTROLLOG");
//
//	public static final String BAIDU_LBS_TYPE = "bd09ll";
//	// static {
//	// url = bundle.getString("riskMap_Typhoon");
//	// String typhoonName = MapUtils.getDataSetTableName(url);
//	// INSERT_TFMAIN = "insert into
//	// "+typhoonName+"(SMID,SMX,SMY,DANGERID,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02,DANGERADDRESS,SCORE,VALIDSTATUS,DANGERFLAG)
//	// values(?,?,?,?,?,?,?,?,?,?,?,?)";
//	// }
//
//	/**
//	 * Description:进行sql数据组织
//	 * 
//	 * @author liqiankun
//	 * @date 20191212
//	 * @param
//	 * @return String
//	 */
//	public String getTfmainSql() {
//		String INSERT_TFMAIN = "";
//		try {
//			String url = bundle.getString("riskMap_Typhoon");
//			String typhoonName = MapUtils.getDataSetTableName(url);
//			INSERT_TFMAIN = "insert into " + typhoonName
//					+ "(SMID,SMX,SMY,DANGERID,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02,DANGERADDRESS,SCORE,VALIDSTATUS,DANGERFLAG) values(?,?,?,?,?,?,?,?,?,?,?,?)";
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return INSERT_TFMAIN;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:connectServletUserXmlFile
//	 * </p>
//	 * <p>
//	 * Description:获取地图接口数据
//	 * </p>
//	 * 
//	 * @author Wei shutuan
//	 * @date 20171020
//	 * @param url
//	 *            传入的接口地址
//	 * @return strReturnXML 调用接口所返回的xml
//	 */
//	public String connectServletUserXmlFile(String url) {
//		String strReturnXML = "";
//		HttpURLConnection httpConnection;
//		try {
//			// 1、打开连接
//			httpConnection = (HttpURLConnection) new URL(url).openConnection();
//			httpConnection.setRequestMethod("POST");
//			httpConnection.setDoOutput(true);
//			httpConnection.setDoInput(true);
//			httpConnection.setAllowUserInteraction(true);
//			httpConnection.connect();
//			// 2、发送数据
//			OutputStream outputStream = httpConnection.getOutputStream();
//			outputStream.close();
//			// 3、返回数据
//			InputStreamReader inputStreamReader = new InputStreamReader(httpConnection.getInputStream(), "GBK");
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//			String inputLine = "";
//			StringBuffer inputLines = new StringBuffer();
//			while ((inputLine = bufferedReader.readLine()) != null) {
//				inputLines.append(inputLine);
//			}
//			inputStreamReader.close();
//			bufferedReader.close();
//			// 4、关闭连接
//			httpConnection.disconnect();
//			strReturnXML = inputLines.toString();
//
//		} catch (MalformedURLException e) {
//			log.info("连接异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("连接异常:" + e);
//		} catch (IOException e) {
//			log.info("连接异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("连接异常:" + e);
//		}
//		return strReturnXML;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryDataByPageAnaly
//	 * </p>
//	 * <p>
//	 * Description:根据页面传过来的信息查询所选地址周围的出险点
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171028
//	 * @param mapRequestInfoVo
//	 *            页面所传信息vo类
//	 * @return List<RiskMapDisaster> 查询所选地址周围的出险点
//	 * @throws Exception
//	 */
//	public List<RiskMapDisaster> queryDataByPageAnaly(MapRequestInfoVo mapRequestInfoVo) throws Exception {
//		int sum = 0;
//		String scope = mapRequestInfoVo.getScope();
//		List<LongAndLatitude> lngXLatYList = mapRequestInfoVo.getLngXLatYList();
//		List<RiskMapDisaster> validDataList = new ArrayList<RiskMapDisaster>();
//		List<RiskMapDisaster> riskMapDisasterNum = new ArrayList<RiskMapDisaster>();
//		for (LongAndLatitude longAndLatitude : lngXLatYList) {
//			sum = 0;
//			String latY = longAndLatitude.getLatY();
//			String longX = longAndLatitude.getLngX();
//			// 分析范围
//			Double radius = Double.parseDouble(scope);
//			// 纬度
//			Double latitude = Double.parseDouble(latY);
//			// 经度
//			Double longitude = Double.parseDouble(longX);
//			double[] point1 = MapUtil.lonLatToMeters(longitude, latitude);
//			// 把范围转换成经纬度距离
//			double meterDistance = MapUtil.meterDistanceToDegreeDistance(longitude, latitude, radius);
//			// 得到地址为中心点，分析范围为半径的周围所有的出险地址
//			List<RiskMapDisaster> queryDangerDSata = this.queryDangerDSata(longitude, latitude, meterDistance,
//					mapRequestInfoVo);
//			if (queryDangerDSata.size() == 0) {
//				RiskMapDisaster riskMapDisasterData = new RiskMapDisaster();
//				riskMapDisasterData.setLongitude(longAndLatitude.getLngX());
//				riskMapDisasterData.setLatitude(longAndLatitude.getLatY());
//				riskMapDisasterData.setDangerAdderss(longAndLatitude.getDangerAdderss());
//				riskMapDisasterData.setNum(0);
//				riskMapDisasterData.setFlag("3");
//				validDataList.add(riskMapDisasterData);
//			} else {
//				for (RiskMapDisaster riskMapDisaster : queryDangerDSata) {
//					String valid = riskMapDisaster.getValid();
//					if (("1").equals(valid.trim())) {
//						Double longitude2 = Double.parseDouble(riskMapDisaster.getLongitude());
//						Double latitude2 = Double.parseDouble(riskMapDisaster.getLatitude());
//						double[] point2 = MapUtil.lonLatToMeters(longitude2, latitude2);
//						double distance = MapUtil.calDistanceByTowPoint(point1, point2);
//						if (distance <= (Double.parseDouble(scope))) {
//							if (riskMapDisaster.getLongitude().equals(longX)
//									&& riskMapDisaster.getLatitude().equals(latY)) {
//								continue;
//							} else {
//								riskMapDisaster.setFlag("2");
//								sum++;
//							}
//							validDataList.add(riskMapDisaster);
//						}
//					}
//				}
//				QueryRule queryRule = QueryRule.getInstance();
//				queryRule.addLike("longitude", longX);
//				queryRule.addLike("latitude", latY);
//				queryRule.addSql("this_.VALID = '1' ");
//				this.queryMoreMethod(mapRequestInfoVo, queryRule);
//				List<RiskMapDisaster> riskMapList = new ArrayList<RiskMapDisaster>();
//				riskMapList = databaseDao.findAll(RiskMapDisaster.class, queryRule);
//				if (riskMapList.size() != 0) {
//					for (RiskMapDisaster riskMapDisaste : riskMapList) {
//						riskMapDisaste.setFlag("3");
//						validDataList.add(riskMapDisaste);
//					}
//				} else {
//					RiskMapDisaster disaster = new RiskMapDisaster();
//					disaster.setLatitude(latY);
//					disaster.setLongitude(longX);
//					disaster.setFlag("3");
//					disaster.setDangerAdderss(longAndLatitude.getDangerAdderss());
//					validDataList.add(disaster);
//				}
//			}
//			if (validDataList.size() > 0) {
//				for (RiskMapDisaster riskMapDisaster : validDataList) {
//					if ("3".equals(riskMapDisaster.getFlag())) {
//						riskMapDisaster.setNum(sum);
//					}
//					riskMapDisasterNum.add(riskMapDisaster);
//				}
//				validDataList.clear();
//			}
//		}
//		return riskMapDisasterNum;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryDangerDSata
//	 * </p>
//	 * <p>
//	 * Description:根据经纬度，半径初步过滤的到以地址为中心的正方形内 的点
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171028
//	 * @param longitude
//	 *            经度
//	 * @param latitude
//	 *            纬度
//	 * @param meterDistance
//	 *            分析范围（半径）
//	 * @return List<RiskMapDisaster> 正方形内 的点
//	 */
//	public List<RiskMapDisaster> queryDangerDSata(Double longitude, Double latitude, Double meterDistance,
//			MapRequestInfoVo mapRequestInfoVo) {
//		QueryRule queryRule = QueryRule.getInstance();
//		// DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		List<RiskMapDisaster> dataList = new ArrayList<RiskMapDisaster>();
//		MapMoreQuery mapMoreQuery = mapRequestInfoVo.getMapMoreQuery();
//		String searchType = mapRequestInfoVo.getSearchType();
//		try {
//			if (mapMoreQuery != null) {
//				this.queryMoreMethod(mapRequestInfoVo, queryRule);
//				if (StringUtils.isNotBlank(String.valueOf(longitude))) {
//					queryRule.addGreaterEqual("longitude", String.valueOf(longitude - meterDistance));
//					queryRule.addLessEqual("longitude", String.valueOf(longitude + meterDistance));
//				}
//				if (StringUtils.isNotBlank(String.valueOf(latitude))) {
//					queryRule.addGreaterEqual("latitude", String.valueOf(latitude - meterDistance));
//					queryRule.addLessEqual("latitude", String.valueOf(latitude + meterDistance));
//				}
//				queryRule.addSql("this_.VALID = '1' ");
//				if (StringUtils.isNotBlank(searchType)) {
//					if ("0".equals(searchType.trim())) {
//						queryRule.addSql("(this_.GREATCLAIMFLAG = '1' or this_.DANGERFLAG = '1')");
//					} else if ("1".equals(searchType.trim())) {
//						queryRule.addSql("this_.GREATCLAIMFLAG = '1'");
//					} else if ("2".equals(searchType.trim())) {
//						queryRule.addSql("this_.DANGERFLAG = '1'");
//					}
//				}
//			}
//			// RiskMapDisaster model = new RiskMapDisaster();
//			// List<RiskMapDisaster> riskMapList=new ArrayList<RiskMapDisaster>();
//			dataList = databaseDao.findAll(RiskMapDisaster.class, queryRule);
//		} catch (Exception e) {
//			log.info("地图查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("地图查询异常:" + e);
//		}
//		return dataList;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryComeCode
//	 * </p>
//	 * <p>
//	 * Description:根据归属机构查询用户，定位中心
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171028
//	 * @param attribute
//	 *            归属机构
//	 * @return LongAndLatitude 所选分公司对应的经纬度封装类
//	 */
//	public LongAndLatitude queryComeCode(String attribute) {
//		LongAndLatitude longAndLatitude = new LongAndLatitude();
//
//		if ("1200".equals(attribute)) {
//			longAndLatitude.setLngX("117.190182");
//			longAndLatitude.setLatY("39.125596");
//		} else if ("1300".equals(attribute)) {
//			longAndLatitude.setLngX("114.502461");
//			longAndLatitude.setLatY("38.045474");
//		} else if ("1400".equals(attribute)) {
//			longAndLatitude.setLngX("112.549248");
//			longAndLatitude.setLatY("37.857014");
//		} else if ("1500".equals(attribute)) {
//			longAndLatitude.setLngX("111.670801");
//			longAndLatitude.setLatY("40.818311");
//		} else if ("2100".equals(attribute)) {
//			longAndLatitude.setLngX("123.429096");
//			longAndLatitude.setLatY("41.796767");
//		} else if ("2102".equals(attribute)) {
//			longAndLatitude.setLngX("121.618622");
//			longAndLatitude.setLatY("38.914590");
//		} else if ("2200".equals(attribute)) {
//			longAndLatitude.setLngX("125.324500");
//			longAndLatitude.setLatY("43.886841");
//		} else if ("2300".equals(attribute)) {
//			longAndLatitude.setLngX("126.642464");
//			longAndLatitude.setLatY("45.756967");
//		} else if ("5400".equals(attribute)) {
//			longAndLatitude.setLngX("91.132212");
//			longAndLatitude.setLatY("29.660361");
//		} else if ("3100".equals(attribute)) {
//			longAndLatitude.setLngX("121.472644");
//			longAndLatitude.setLatY("31.231706");
//		} else if ("3200".equals(attribute)) {
//			longAndLatitude.setLngX("118.767413");
//			longAndLatitude.setLatY("32.041544");
//		} else if ("3300".equals(attribute)) {
//			longAndLatitude.setLngX("120.153576");
//			longAndLatitude.setLatY("30.287459");
//		} else if ("3302".equals(attribute)) {
//			longAndLatitude.setLngX("121.549792");
//			longAndLatitude.setLatY("29.868388");
//		} else if ("3400".equals(attribute)) {
//			longAndLatitude.setLngX("117.283042");
//			longAndLatitude.setLatY("31.861190");
//		} else if ("3500".equals(attribute)) {
//			longAndLatitude.setLngX("119.306239");
//			longAndLatitude.setLatY("26.075302");
//		} else if ("3502".equals(attribute)) {
//			longAndLatitude.setLngX("118.110220");
//			longAndLatitude.setLatY("24.490474");
//		} else if ("3600".equals(attribute)) {
//			longAndLatitude.setLngX("115.892151");
//			longAndLatitude.setLatY("28.676493");
//		} else if ("3700".equals(attribute)) {
//			longAndLatitude.setLngX("117.000923");
//			longAndLatitude.setLatY("36.675807");
//		} else if ("3702".equals(attribute)) {
//			longAndLatitude.setLngX("120.355173");
//			longAndLatitude.setLatY("36.082982");
//		} else if ("4100".equals(attribute)) {
//			longAndLatitude.setLngX("113.665412");
//			longAndLatitude.setLatY("34.757975");
//		} else if ("4200".equals(attribute)) {
//			longAndLatitude.setLngX("114.298572");
//			longAndLatitude.setLatY("30.584355");
//		} else if ("4300".equals(attribute)) {
//			longAndLatitude.setLngX("112.982279");
//			longAndLatitude.setLatY("28.194090");
//		} else if ("4400".equals(attribute)) {
//			longAndLatitude.setLngX("113.280637");
//			longAndLatitude.setLatY("23.125178");
//		} else if ("4401".equals(attribute)) {
//			longAndLatitude.setLngX("113.280637");
//			longAndLatitude.setLatY("23.125178");
//		} else if ("4403".equals(attribute)) {
//			longAndLatitude.setLngX("114.085947");
//			longAndLatitude.setLatY("22.547000");
//		} else if ("4500".equals(attribute)) {
//			longAndLatitude.setLngX("108.320004");
//			longAndLatitude.setLatY("22.824020");
//		} else if ("4600".equals(attribute)) {
//			longAndLatitude.setLngX("110.331190");
//			longAndLatitude.setLatY("20.031971");
//		} else if ("5000".equals(attribute)) {
//			longAndLatitude.setLngX("106.504962");
//			longAndLatitude.setLatY("29.533155");
//		} else if ("5100".equals(attribute)) {
//			longAndLatitude.setLngX("104.065735");
//			longAndLatitude.setLatY("30.659462");
//		} else if ("5200".equals(attribute)) {
//			longAndLatitude.setLngX("106.713478");
//			longAndLatitude.setLatY("26.578343");
//		} else if ("5300".equals(attribute)) {
//			longAndLatitude.setLngX("102.712251");
//			longAndLatitude.setLatY("25.040609");
//		} else if ("6100".equals(attribute)) {
//			longAndLatitude.setLngX("108.948024");
//			longAndLatitude.setLatY("34.263161");
//		} else if ("6200".equals(attribute)) {
//			longAndLatitude.setLngX("103.823557");
//			longAndLatitude.setLatY("36.058039");
//		} else if ("6300".equals(attribute)) {
//			longAndLatitude.setLngX("101.778916");
//			longAndLatitude.setLatY("36.623178");
//		} else if ("6400".equals(attribute)) {
//			longAndLatitude.setLngX("106.278179");
//			longAndLatitude.setLatY("38.466370");
//		} else if ("6500".equals(attribute)) {
//			longAndLatitude.setLngX("87.617733");
//			longAndLatitude.setLatY("43.792818");
//		} else {
//			longAndLatitude.setLngX("116.397487");
//			longAndLatitude.setLatY("39.908685");
//		}
//		return longAndLatitude;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryDataForDb
//	 * </p>
//	 * <p>
//	 * Description:
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171106
//	 * @param mapRequestInfoVo
//	 *            页面所传信息封装的实体类
//	 * @return List<RiskMapDisaster> 根据搜索地址查询数据库
//	 */
//	public List<RiskMapDisaster> queryDataForDb(MapRequestInfoVo mapRequestInfoVo) {
//		List<RiskMapDisaster> dataList = new ArrayList<RiskMapDisaster>();
//
//		try {
//			String dangerAdderss = mapRequestInfoVo.getDangerAdderss();
//			String branchCompany = mapRequestInfoVo.getBranchCompany();
//			String searchType = mapRequestInfoVo.getSearchType();
//			int pageNo = mapRequestInfoVo.getPageNo();
//			int pageSize = mapRequestInfoVo.getPageSize();
//			String sql = "";
//			if (StringUtils.isNotBlank(searchType)) {
//				if ("1".equals(searchType.trim())) {
//					sql = "select * from RISKMAP_DISASTER  where " + "dangeradderss like'" + dangerAdderss
//							+ "%' and company ='" + branchCompany + "'" + "and valid ='1' and GREATCLAIMFLAG='1'";
//				} else if ("2".equals(searchType.trim())) {
//					sql = "select * from RISKMAP_DISASTER  where " + "dangeradderss like'" + dangerAdderss
//							+ "%' and company ='" + branchCompany + "'" + "and valid ='1' and DANGERFLAG='1'";
//				} else if ("0".equals(searchType.trim())) {
//					sql = "select * from RISKMAP_DISASTER  where " + "dangeradderss like'" + dangerAdderss
//							+ "%' and company ='" + branchCompany + "'"
//							+ "and valid ='1' and (GREATCLAIMFLAG='1' or DANGERFLAG='1')";
//				}
//			} else {
//				sql = "select * from RISKMAP_DISASTER  where " + "dangeradderss like'" + dangerAdderss
//						+ "%' and company ='" + branchCompany + "'" + "and valid ='1'";
//			}
//			// String sql="select * from RISKMAP_DISASTER where "+ "dangeradderss
//			// like'"+dangerAdderss+"%' and company ='"+branchCompany+"'"+"and valid ='1'";
//
//			dataList = jdbcTemplate.query(sql, new Object[] {},
//					new BeanPropertyRowMapper<RiskMapDisaster>(RiskMapDisaster.class));
//			long TotalCount = dataList.size();
//			long pageCount = 0L;
//			if (TotalCount % pageSize == 0) {
//				pageCount = TotalCount / (long) pageSize;
//			} else {
//				pageCount = TotalCount / (long) pageSize + 1L;
//			}
//			for (RiskMapDisaster riskMapDisaster : dataList) {
//				riskMapDisaster.setPageNo(pageNo);
//				riskMapDisaster.setPageSize(pageSize);
//				riskMapDisaster.setPageCount(pageCount);
//				riskMapDisaster.setTotalCount(TotalCount);
//				riskMapDisaster.setCity(mapRequestInfoVo.getBranchCompanyZH());
//			}
//		} catch (DataAccessException e) {
//			log.info("地图查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("地图查询异常:" + e);
//		}
//		return dataList;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryDataForApi
//	 * </p>
//	 * <p>
//	 * Description:
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171106
//	 * @param mapRequestInfoVo
//	 *            页面所传信息封装的实体类
//	 * @return List<RiskMapDisaster> 根据搜索地址调用高德接口所返回的地址
//	 * @throws Exception
//	 */
//	public List<RiskMapDisaster> queryDataForApi(MapRequestInfoVo mapRequestInfoVo) throws Exception {
//		List<RiskMapDisaster> dataList = new ArrayList<RiskMapDisaster>();
//		try {
//			int pageNo = mapRequestInfoVo.getPageNo();
//			int pageSize = mapRequestInfoVo.getPageSize();
//			String resultXML = "";
//			if (null != mapRequestInfoVo.getDangerAdderss() && !mapRequestInfoVo.getDangerAdderss().equals("")) {
//				String dangerAdderss = mapRequestInfoVo.getBranchCompanyZH();
//				String city = java.net.URLEncoder.encode(dangerAdderss, "GBK");
//				String address = java.net.URLEncoder.encode(mapRequestInfoVo.getDangerAdderss(), "GBK");
//				ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//				String url1 = bundle.getString("url1") + address + "&city=" + city;
//				resultXML = connectServletUserXmlFile(url1);
//				System.out.println("resultXML====11==================================" + resultXML);
//				XStream xs = new XStream(new DomDriver());
//				xs.alias("searchresult", SearchResult.class);
//				xs.alias("poi", Poi.class);
//				xs.alias("housenumber", HouseNumber.class);
//				xs.aliasField("name", Poi.class, "name");
//				xs.aliasField("x", Poi.class, "longitude");
//				xs.aliasField("y", Poi.class, "latitude");
//				SearchResult searchResult;
//				searchResult = (SearchResult) xs.fromXML(resultXML);
//				if ("E0".equals(searchResult.getStatus().trim())) {
//					List<Poi> poList = searchResult.getList();
//					for (Poi poi : poList) {
//						if (poList.size() > 0) {
//							pageNo = mapRequestInfoVo.getPageNo();
//							pageSize = mapRequestInfoVo.getPageSize();
//							long TotalCount = poList.size();
//							long pageCount = 0L;
//							if (TotalCount % pageSize == 0) {
//								pageCount = TotalCount / (long) pageSize;
//							} else {
//								pageCount = TotalCount / (long) pageSize + 1L;
//							}
//							RiskMapDisaster riskMapDisaster = new RiskMapDisaster();
//							riskMapDisaster.setPageNo(pageNo);
//							riskMapDisaster.setPageSize(pageSize);
//							riskMapDisaster.setPageCount(pageCount);
//							riskMapDisaster.setTotalCount(TotalCount);
//							riskMapDisaster.setCity(mapRequestInfoVo.getBranchCompanyZH());
//							riskMapDisaster.setLongitude(poi.getLongitude());
//							riskMapDisaster.setLatitude(poi.getLatitude());
//							riskMapDisaster.setFlag("1");
//							riskMapDisaster.setDangerAdderss(poi.getName());
//							dataList.add(riskMapDisaster);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.info("地图查询接口异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("地图查询接口异常:" + e);
//		}
//		return dataList;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryKeyWordForApi
//	 * </p>
//	 * <p>
//	 * Description:
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171108
//	 * @param mapRequestInfoVo
//	 *            页面所传信息封装的实体类
//	 * @return List<RiskMapDisaster> 根据搜索地址
//	 * @throws Exception
//	 */
//	public List<RiskMapDisaster> queryKeyWordForApi(MapRequestInfoVo mapRequestInfoVo) throws Exception {
//		List<RiskMapDisaster> dataList = new ArrayList<RiskMapDisaster>();
//		try {
//			int pageNo = mapRequestInfoVo.getPageNo();
//			int pageSize = mapRequestInfoVo.getPageSize();
//			String resultXML = "";
//			if (null != mapRequestInfoVo.getDangerAdderss() && !mapRequestInfoVo.getDangerAdderss().equals("")) {
//				String dangerAdderss = mapRequestInfoVo.getBranchCompanyZH();
//				String city = java.net.URLEncoder.encode(dangerAdderss, "GBK");
//				String address = java.net.URLEncoder.encode(mapRequestInfoVo.getDangerAdderss(), "GBK");
//				ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//				String url2 = bundle.getString("url2") + address + "&city=" + city;
//				resultXML = connectServletUserXmlFile(url2);
//				System.out.println("resultXML====11==================================" + resultXML);
//				XStream xs = new XStream(new DomDriver());
//				xs.alias("searchresult", com.picc.riskctrl.map.vo.exactsearch.SearchResult.class);
//				xs.alias("poi", com.picc.riskctrl.map.vo.exactsearch.Poi.class);
//				xs.alias("pinyin", Pinyin.class);
//				xs.aliasField("x", com.picc.riskctrl.map.vo.exactsearch.Poi.class, "longitude");
//				xs.aliasField("y", com.picc.riskctrl.map.vo.exactsearch.Poi.class, "latitude");
//				com.picc.riskctrl.map.vo.exactsearch.SearchResult searchResult;
//				searchResult = (com.picc.riskctrl.map.vo.exactsearch.SearchResult) xs.fromXML(resultXML);
//				if ("E0".equals(searchResult.getStatus().trim())) {
//					List<com.picc.riskctrl.map.vo.exactsearch.Poi> poList = searchResult.getList();
//					for (com.picc.riskctrl.map.vo.exactsearch.Poi poi : poList) {
//						if (poList.size() > 0) {
//							pageNo = mapRequestInfoVo.getPageNo();
//							pageSize = mapRequestInfoVo.getPageSize();
//							long TotalCount = poList.size();
//							long pageCount = 0L;
//							if (TotalCount % pageSize == 0) {
//								pageCount = TotalCount / (long) pageSize;
//							} else {
//								pageCount = TotalCount / (long) pageSize + 1L;
//							}
//							RiskMapDisaster riskMapDisaster = new RiskMapDisaster();
//							riskMapDisaster.setPageNo(pageNo);
//							riskMapDisaster.setPageSize(pageSize);
//							riskMapDisaster.setPageCount(pageCount);
//							riskMapDisaster.setTotalCount(TotalCount);
//							riskMapDisaster.setCity(mapRequestInfoVo.getBranchCompanyZH());
//							riskMapDisaster.setLongitude(poi.getLongitude());
//							riskMapDisaster.setLatitude(poi.getLatitude());
//							riskMapDisaster.setFlag("1");
//							riskMapDisaster.setDangerAdderss(
//									mapRequestInfoVo.getBranchCompanyZH() + poi.getAddress() + "-" + poi.getName());
//							dataList.add(riskMapDisaster);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.info("地图查询接口异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("地图查询接口异常:" + e);
//		}
//		return dataList;
//	}
//
//	/**
//	 * 
//	 * <p>
//	 * Title:queryTotalData
//	 * </p>
//	 * <p>
//	 * Description:
//	 * </p>
//	 * 
//	 * @author Wei shituan
//	 * @date 20171108
//	 * @return List<RiskMapDisaster> 调用高德接口及数据库返回的所有地址
//	 */
//	public List<RiskMapDisaster> queryTotalData(MapRequestInfoVo mapRequestInfoVo) {
//		List<RiskMapDisaster> dataList = new ArrayList<RiskMapDisaster>();
//		List<RiskMapDisaster> subList = new ArrayList<RiskMapDisaster>();
//		try {
//			int pageNo = mapRequestInfoVo.getPageNo();
//			int pageSize = mapRequestInfoVo.getPageSize();
//			List<RiskMapDisaster> queryKeyWordForApi = this.queryKeyWordForApi(mapRequestInfoVo);
//			List<RiskMapDisaster> queryDataForDb = this.queryDataForDb(mapRequestInfoVo);
//			List<RiskMapDisaster> queryDataForApi = this.queryDataForApi(mapRequestInfoVo);
//			if (queryKeyWordForApi.size() != 0) {
//				for (RiskMapDisaster riskMapDisaster : queryKeyWordForApi) {
//					dataList.add(riskMapDisaster);
//				}
//			}
//			if (queryDataForDb.size() != 0) {
//				for (RiskMapDisaster riskMapDisaster : queryDataForDb) {
//					dataList.add(riskMapDisaster);
//				}
//			}
//			if (queryDataForApi.size() != 0) {
//				for (RiskMapDisaster riskMapDisaster : queryDataForApi) {
//					dataList.add(riskMapDisaster);
//				}
//			}
//			if (dataList.size() > 0) {
//				pageNo = mapRequestInfoVo.getPageNo();
//				pageSize = mapRequestInfoVo.getPageSize();
//				long TotalCount = dataList.size();
//				long pageCount = 0L;
//				if (TotalCount % pageSize == 0) {
//					pageCount = TotalCount / (long) pageSize;
//				} else {
//					pageCount = TotalCount / (long) pageSize + 1L;
//				}
//				subList = dataList.subList(pageSize * (pageNo - 1),
//						((pageSize * pageNo) > TotalCount ? (int) TotalCount : (pageSize * pageNo)));
//				for (RiskMapDisaster riskMapDisaster : subList) {
//					riskMapDisaster.setPageNo(pageNo);
//					riskMapDisaster.setPageSize(pageSize);
//					riskMapDisaster.setPageCount(pageCount);
//					riskMapDisaster.setTotalCount(TotalCount);
//				}
//			}
//			if (dataList.size() == 0) {
//				RiskMapDisaster disaster = new RiskMapDisaster();
//				disaster.setPageNo(1);
//				disaster.setPageSize(1);
//				disaster.setPageCount(1);
//				disaster.setTotalCount(0l);
//				dataList.add(disaster);
//			}
//		} catch (Exception e) {
//			log.info("地图查询接口异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("地图查询接口异常:" + e);
//		}
//		return subList;
//	}
//
//	public void queryMoreMethod(MapRequestInfoVo mapRequestInfoVo, QueryRule queryRule) throws Exception {
//		MapMoreQuery mapMoreQuery = mapRequestInfoVo.getMapMoreQuery();
//		DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (StringUtils.isNotBlank(mapMoreQuery.getPolicyNo().trim())) {
//			queryRule.addLike("policyNo", mapMoreQuery.getPolicyNo());
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getInsuredName().trim())) {
//			queryRule.addLike("insuredName", mapMoreQuery.getInsuredName());
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getReportNo().trim())) {
//			queryRule.addLike("reportNo", mapMoreQuery.getReportNo());
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getCaseNo().trim())) {
//			queryRule.addLike("caseNo", mapMoreQuery.getCaseNo());
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getCatastropheCoding().trim())) {
//			queryRule.addLike("catastroPhecoding", mapMoreQuery.getCatastropheCoding());
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getDangerAdderss().trim())) {
//			queryRule.addLike("dangerAdderss", mapMoreQuery.getDangerAdderss());
//		}
//		if (StringUtils.isNotBlank(mapRequestInfoVo.getBranchCompany().trim())) {
//			queryRule.addLike("company", mapRequestInfoVo.getBranchCompany());
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getSettlementAmountStart().trim())) {
//
//			queryRule.addGreaterEqual("settlementAmount", new BigDecimal(mapMoreQuery.getSettlementAmountStart()));
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getSettlementAmountEnd().trim())) {
//			queryRule.addLessEqual("settlementAmount", new BigDecimal(mapMoreQuery.getSettlementAmountEnd()));
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getOutdangerDateStart())) {
//			queryRule.addGreaterEqual("outDangerDate", date.parse(mapMoreQuery.getOutdangerDateStart()));
//		}
//		if (StringUtils.isNotBlank(mapMoreQuery.getOutdangerDateEnd())) {
//			queryRule.addLessEqual("outDangerDate", date.parse(mapMoreQuery.getOutdangerDateEnd()));
//		}
//	}
//
//	public List<RiskMapDisaster> queryInfoByBound(MapBound mapBound) {
//		// List<RiskMapDisaster> dataList= new ArrayList<RiskMapDisaster>();
//		QueryRule queryRule = QueryRule.getInstance();
//		double latCenter = (Double.valueOf(mapBound.getBottom()) + Double.valueOf(mapBound.getTop())) / 2;
//		double lonCenter = (Double.valueOf(mapBound.getRight()) + Double.valueOf(mapBound.getLeft())) / 2;
//		double radius = Double.valueOf(mapBound.getRight()) - lonCenter;
//
//		if (StringUtils.isNotBlank(mapBound.getRight()) && StringUtils.isNotBlank(mapBound.getLeft())) {
//			queryRule.addGreaterEqual("longitude", String.valueOf(mapBound.getLeft()));
//			queryRule.addLessEqual("longitude", String.valueOf(mapBound.getRight()));
//		}
//		if (StringUtils.isNotBlank(mapBound.getBottom()) && StringUtils.isNotBlank(mapBound.getTop())) {
//			queryRule.addGreaterEqual("latitude", String.valueOf(mapBound.getBottom()));
//			queryRule.addLessEqual("latitude", String.valueOf(mapBound.getTop()));
//		}
//		// queryRule.addEqual("valid","1");
//		queryRule.addSql("this_.VALID='1'");
//		List<RiskMapDisaster> riskMapDisasterList = new ArrayList<RiskMapDisaster>();
//		try {
//			riskMapDisasterList = databaseDao.findAll(RiskMapDisaster.class, queryRule);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		List<RiskMapDisaster> riskMapDisasters = new ArrayList<RiskMapDisaster>();
//		if (null != riskMapDisasterList && riskMapDisasterList.size() > 0) {
//			for (int i = 0; i < riskMapDisasterList.size(); i++) {
//				RiskMapDisaster riskMapDisaster = riskMapDisasterList.get(i);
//				if (Math.pow((Double.valueOf(riskMapDisaster.getLongitude()) - lonCenter), 2)
//						+ Math.pow((Double.valueOf(riskMapDisaster.getLatitude()) - latCenter), 2) <= Math.pow(radius,
//								2)) {
//					riskMapDisasters.add(riskMapDisaster);
//				}
//			}
//		}
//
//		return riskMapDisasters;
//	}
//
//	public MapQueryResponse queryDetailAddress(MapQuery mapQuery) {
//		MapQueryResponse mapQueryResponse = new MapQueryResponse();
//		try {
//			ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//			String url = bundle.getString("url");
//			// String token = "25cc55a69ea7422182d00d6b7c0ffa93";
//			// String url =
//			// "http://10.10.1.2/service/search/keyword?token=25cc55a69ea7422182d00d6b7c0ffa93&source=3&adCode=110000&key=四维图新大厦";
//			String keywords = URLEncoder.encode(mapQuery.getKeywords(), "utf-8");
//			String city = "";
//			if (StringUtils.isNotBlank(mapQuery.getCity())) {
//				city = URLEncoder.encode(mapQuery.getCity(), "utf-8");
//			}
//			int page_num = mapQuery.getPage_num();
//			int page_size = mapQuery.getPage_size();
//			// int source = mapQuery.getSource();
//			String location = mapQuery.getLocation();
//			String param = "";
//			if (StringUtils.isNotBlank(location)) {
//				param = "&keywords=" + keywords + "&location=" + location + "&page_size=" + page_size + "&page_num="
//						+ page_num;
//			} else {
//				param = "&keywords=" + keywords + "&city=" + city + "&page_size=" + page_size + "&page_num=" + page_num;
//			}
//			String resJson = doGet(url, param);
//			System.out.println(resJson);
//			mapQueryResponse = JSON.parseObject(resJson.toString(), MapQueryResponse.class);
//			if (mapQueryResponse.getData() != null) {
//				// 02坐标系转84坐标系
//				mapQueryResponse = this.modifyMapQueryResponse(mapQueryResponse);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("查询地址信息时，发生异常！", e);
//			throw new RuntimeException("查询地址信息时，发生异常！", e);
//		}
//		return mapQueryResponse;
//	}
//
//	/**
//	 * 执行一个带参数的HTTP GET请求，返回请求响应的JSON字符串
//	 *
//	 * @param url
//	 *            请求的URL地址
//	 * @return 返回请求响应的JSON字符串
//	 */
//	public static String doGet(String url, String param) {
//		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
//		GetMethod method = new GetMethod(url + "?" + param);
//		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//		try {
//			client.executeMethod(method);
//			if (method.getStatusCode() == HttpStatus.SC_OK) {
//				return StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName("utf-8"));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.info("执行HTTP Get请求" + url + "时，发生异常！", e);
//			throw new RuntimeException("执行HTTP Get请求" + url + "时，发生异常！", e);
//		} finally {
//			method.releaseConnection();
//		}
//		return "";
//	}
//
//	// 火星坐标系(02)转84坐标系的方法
//	public MapQueryResponse modifyMapQueryResponse(MapQueryResponse mapQueryResponse) {
//
//		// List<Rows> rowsList = mapQueryResponse.getData().getRows();
//		// List<Rows> poisLists=new ArrayList<Rows>();
//		// if (null != rowsList&&rowsList.size()>0) {
//		// for (Rows rows:rowsList){
//		// String location =rows.getLocation();
//		// if(location!=null&&location!="") {
//		// String [] lonAndLat= location.split(",");
//		// Gps gps =
//		// gcj_To_Gps84(Double.parseDouble(lonAndLat[1]),Double.parseDouble(lonAndLat[0]));
//		// double lat= stringToDouble(gps.getWgLat());
//		// double lon= stringToDouble(gps.getWgLon());
//		// String gps84Locations= String.valueOf(lon)+","+String.valueOf(lat);
//		// rows.setLocation(gps84Locations);
//		// rows.setLocaltion1(location);
//		// poisLists.add(rows);
//		// }
//		// }
//		// }
//
//		List<Pois> poisList = mapQueryResponse.getData().getPois();
//		List<Pois> poisLists = new ArrayList<Pois>();
//		if (null != poisList && poisList.size() > 0) {
//			for (Pois pois : poisList) {
//				String location = pois.getLocation();
//				if (location != null && location != "") {
//					String[] lonAndLat = location.split(",");
//
//					Gps gps = MapTransferUtils.gcj_To_Gps84(Double.parseDouble(lonAndLat[1]),
//							Double.parseDouble(lonAndLat[0]));
//					double lat = stringToDouble(gps.getWgLat());
//					double lon = stringToDouble(gps.getWgLon());
//					String gps84Locations = String.valueOf(lon) + "," + String.valueOf(lat);
//					pois.setLocation(gps84Locations);
//					poisLists.add(pois);
//				}
//			}
//		}
//		mapQueryResponse.getData().setPois(poisLists);
//		return mapQueryResponse;
//	}
//	// double保留6位小数
//	public double stringToDouble(double log) {
//		BigDecimal b = new BigDecimal(log);
//		double dou = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
//		return dou;
//	}
//
//	/**
//	 * 按照addressId查找riskMapMain表信息
//	 *
//	 * @param addressId地址id
//	 * @return List<RiskMapMain>
//	 */
//	public List<RiskMapMain> queryMainByAddressId(List<Integer> ids, String sumAmount, String policyno, String MapCode,
//			String nowdate, String powerSQL, String sumPay) {
//		List<RiskMapMain> result = new ArrayList<RiskMapMain>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select * from riskmap_main where" + nowdate + sumAmount + MapCode + policyno
//					+ " riskmap_main.sumamount <> 0"
//					+ " and EXISTS  ( select prpDcompany.comCode from PrpDcompany prpDcompany where comCode = prpDcompany.comCode and (prpDcompany.upperPath like '00000000%'))"
//					+ sumPay + " and addressID = " + ids.get(0);
//			// String sql_new = sql.replace("riskmap_main.", "");
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				RiskMapMain riskMapMain = new RiskMapMain();
//				RiskMapMainId id = new RiskMapMainId();
//				id.setAddressID(rs.getInt(1));
//				id.setProposalNo(rs.getString(2));
//				riskMapMain.setId(id);
//				riskMapMain.setPolicyNo(rs.getString(3));
//				riskMapMain.setClassCode(rs.getString(4));
//				riskMapMain.setRiskCode(rs.getString(5));
//				riskMapMain.setStartDate(rs.getDate(6));
//				riskMapMain.setStartHour(rs.getInt(7));
//				riskMapMain.setEndDate(rs.getDate(8));
//				riskMapMain.setEndHour(rs.getInt(9));
//				riskMapMain.setSumAmount(rs.getBigDecimal(10));
//				riskMapMain.setCoinsFlag(rs.getString(11));
//				riskMapMain.setCoinsRate(rs.getBigDecimal(12));
//				riskMapMain.setComCode(rs.getString(13));
//
//				result.add(riskMapMain);
//			}
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return result;
//	}
//
//	/**
//	 * 按照addressId和proposalNo查找riskMapMain表信息
//	 * 
//	 * @param addressId地址id
//	 * @param proposalNo投保单号
//	 * @return List<RiskMapMain>
//	 */
//	/** 根据AddressId和ProposalNo 获取主表main信息 */
//	public RiskMapMain queryMainByAddAndProNo(int addressId, String proposalNo) {
//		RiskMapMain main = null;
//		RiskMapMainId pk = new RiskMapMainId();
//		pk.setAddressID(addressId);
//		pk.setProposalNo(proposalNo);
//		main = databaseDao.findByPK(RiskMapMain.class, pk);
//		return main;
//	}
//
//	/**
//	 * 按照addressId和proposalNo查找RiskMapInsured表信息
//	 * 
//	 * @param addressId地址id
//	 * @param proposalNo投保单号
//	 * @return
//	 */
//	public List<RiskMapInsured> queryInsuredByAddressId(int addressId, String proposalNo) {
//		List<RiskMapInsured> list = null;
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("id.addressID", addressId);
//		queryRule.addEqual("id.proposalNo", proposalNo);
//		list = databaseDao.findAll(RiskMapInsured.class, queryRule);
//		return list;
//	}
//
//	/**
//	 * 按照addressId和proposalNo查找RiskMapItemkind表信息
//	 * 
//	 * @param addressId地址id
//	 * @param proposalNo投保单号
//	 * @return
//	 */
//	public List<RiskMapItemkind> queryItemkindByAddressId(int addressId, String proposalNo) {
//		List<RiskMapItemkind> list = null;
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("id.addressID", addressId);
//		queryRule.addEqual("id.proposalNo", proposalNo);
//		list = databaseDao.findAll(RiskMapItemkind.class, queryRule);
//		return list;
//	}
//
//	/**
//	 * 获取总保额 保单数 周东旭
//	 * 
//	 * @params addressId，SMX，SMY
//	 * @return
//	 */
//	public List<RiskMapMainVo> queryData(List<Integer> ids, String sumAmount, String policyno, String MapCode,
//			String nowdate, String powerSQL, String sumPay) {
//		List<RiskMapMainVo> riskMapMainVoList = null;
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select addressid,sum(sumamount),count(SUMAMOUNT) addressName, sum(nvl(RiskMap_Main.SUMREALPAY,0)),sum(nvl(RiskMap_Main.SUMUNSOLVEDPAY,0)),sum(nvl(RiskMap_Main.SUMREAMOUNTCHG,0))  from riskmap_main "
//					+ "where" + nowdate + sumAmount + MapCode + policyno + " riskmap_main.sumamount <>0" + powerSQL
//					+ sumPay + " and ADDRESSID in " + Arrays.toString(ids.toArray()).replace("[", "(").replace("]", ")")
//					+ " group by addressid";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//			List<String[]> countListC = new ArrayList<String[]>();
//			for (int i = 1; rs.next(); i++) {
//				String[] str = new String[9];
//				str[0] = rs.getString(1);
//				str[1] = rs.getString(2);
//				str[2] = rs.getString(3);
//				str[5] = i + "";
//				str[6] = rs.getString(4);
//				str[7] = rs.getString(5);
//				str[8] = rs.getString(6);
//				countListC.add(str);
//			}
//			List list = countListC;
//			riskMapMainVoList = list;
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return riskMapMainVoList;
//	}
//
//	/**
//	 * 获取灾害值 周东旭
//	 */
//	public List<RiskMapMainVo> queryRainStrom(List<Integer> ids, String sumAmount, String proposalNo, String MapCode,
//			String nowdate, String powerSQL, String GraphicsFG, String sumPay) {
//		String Graphics = null;
//		if (GraphicsFG.equals("1")) {
//			Graphics = "RAINSCALE";
//		} else if (GraphicsFG.equals("2")) {
//			Graphics = "THUNDERSTORMSCALE";
//		} else if (GraphicsFG.equals("3")) {
//			Graphics = "SNOWSTORMSCALE";
//		} else if (GraphicsFG.equals("4")) {
//			Graphics = "HAILSCALE";
//		} else if (GraphicsFG.equals("5")) {
//			Graphics = "FLOODSCALE";
//		} else if (GraphicsFG.equals("6")) {
//			Graphics = "TYPHOONSCALE";
//		} else if (GraphicsFG.equals("7")) {
//			Graphics = "LANDSLIDESCALE";
//		} else if (GraphicsFG.equals("8")) {
//			Graphics = "EQPGA";
//		}
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMap_Address = bundle.getString("riskMap_address");
//		String riskMap_AddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMap_Address = MapUtils.getDataSetTableName(riskMap_AddressName);
//		List<RiskMapMainVo> riskMapMainVoList = null;
//		StringBuffer sqlString = new StringBuffer();
//		for (int i = 0; i < ids.size(); i++) {
//			if (i == (ids.size() - 1)) {
//				sqlString.append(ids.get(i)); // SQL拼装，最后一条不加“,”。
//			} else if ((i % 999) == 0 && i > 0) {
//				sqlString.append(ids.get(i)).append(") or riskmap_main.ADDRESSID in ("); // 解决ORA-01795问题
//			} else {
//				sqlString.append(ids.get(i)).append(",");
//			}
//		}
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select " + riskMap_Address + "." + Graphics + ", sum(riskmap_main.sumamount) from "
//					+ riskMap_Address + ", riskmap_main  where " + riskMap_Address + ".SMID=riskmap_main.ADDRESSID and "
//					+ nowdate + sumAmount + MapCode + proposalNo + " riskmap_main.sumamount <>0" + powerSQL + sumPay
//					+ " and (riskmap_main.ADDRESSID in (" + sqlString.toString() + ")) group by " + riskMap_Address
//					+ "." + Graphics + " order by " + riskMap_Address + "." + Graphics + "";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			List<String[]> countListC = new ArrayList<String[]>();
//			for (; rs.next();) {
//				String[] str = new String[2];
//				BigDecimal big = new BigDecimal("0");
//				str[0] = String.valueOf(rs.getDouble(1));
//				big = rs.getBigDecimal(2).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN);
//				str[1] = big.stripTrailingZeros().toPlainString();
//				countListC.add(str);
//			}
//			List list = countListC;
//			riskMapMainVoList = list;
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return riskMapMainVoList;
//	}
//
//	/** 求总保单 总赔款金额 总保额 */
//	public List<RiskMapMainVo> queryPolicyNumber(List<Integer> ids, String sumAmount, String policyno, String MapCode,
//			String nowdate, String powerSQL, String sumPay) {
//		List<RiskMapMainVo> riskMapMainVoList = null;
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		StringBuffer sqlString = new StringBuffer();
//		for (int i = 0; i < ids.size(); i++) {
//			if (i == (ids.size() - 1)) {
//				sqlString.append(ids.get(i)); // SQL拼装，最后一条不加“,”。
//			} else if ((i % 999) == 0 && i > 0) {
//				sqlString.append(ids.get(i)).append(") or riskmap_main.ADDRESSID in ("); // 解决ORA-01795问题
//			} else {
//				sqlString.append(ids.get(i)).append(",");
//			}
//		}
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select sum(sumamount),count(sumamount), sum(SUMREALPAY) from riskmap_main " + "where"
//					+ nowdate + sumAmount + MapCode + policyno + " riskmap_main.sumamount <>0" + powerSQL + sumPay
//					+ " and (ADDRESSID in (" + sqlString.toString() + "))";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//			List<String[]> countListC = new ArrayList<String[]>();
//			for (int i = 1; rs.next(); i++) {
//				String[] str = new String[3];
//				str[0] = String.valueOf((double) Math.round((rs.getDouble(1) / 100000000) * 100) / 100);
//				str[1] = rs.getString(2);
//				str[2] = String.valueOf((double) Math.round((rs.getDouble(3) / 10000) * 100) / 100);
//				countListC.add(str);
//			}
//			List list = countListC;
//			riskMapMainVoList = list;
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return riskMapMainVoList;
//	}
//
//	public List<RiskMapMainVo> querySwitching(List<Integer> ids, String sumAmount, String proposalNo, String MapCode,
//			String nowdate, String powerSQL, String GraphicsFG, String sumPay) {
//		String Graphics = null;
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMap_Address = bundle.getString("riskMap_address");
//		String riskMap_AddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMap_Address = MapUtils.getDataSetTableName(riskMap_AddressName);
//		if (GraphicsFG.equals("classCode")) {
//			Graphics = "CLASSCODE";
//		} else if (GraphicsFG.equals("RiskCode")) {
//			Graphics = "RISKCODE";
//		} else if (GraphicsFG.equals("SumAmount")) {
//			Graphics = "SUMAMOUNT";
//		}
//		List<RiskMapMainVo> riskMapMainVoList = null;
//		StringBuffer sqlString = new StringBuffer();
//		for (int i = 0; i < ids.size(); i++) {
//			if (i == (ids.size() - 1)) {
//				sqlString.append(ids.get(i)); // SQL拼装，最后一条不加“,”。
//			} else if ((i % 999) == 0 && i > 0) {
//				sqlString.append(ids.get(i)).append(") or riskmap_main.ADDRESSID in ("); // 解决ORA-01795问题
//			} else {
//				sqlString.append(ids.get(i)).append(",");
//			}
//		}
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select riskmap_main." + Graphics + ", sum(riskmap_main.sumamount) from " + riskMap_Address
//					+ ", riskmap_main  where " + riskMap_Address + ".SMID=riskmap_main.ADDRESSID and " + nowdate
//					+ sumAmount + MapCode + proposalNo + " riskmap_main.sumamount <>0" + powerSQL + sumPay
//					+ " and (riskmap_main.ADDRESSID in (" + sqlString.toString() + ")) group by riskmap_main."
//					+ Graphics + "";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			List<String[]> countListC = new ArrayList<String[]>();
//			List<Double[]> countListD = new ArrayList<Double[]>();
//			List list = new ArrayList();
//			if (Graphics.equals("SUMAMOUNT")) {
//				for (; rs.next();) {
//					Double[] str = new Double[2];
//					str[0] = rs.getDouble(1);
//					str[1] = (double) Math.round((rs.getDouble(2) / 1000000) * 100) / 100;
//					countListD.add(str);
//				}
//				list = countListD;
//			} else {
//				for (; rs.next();) {
//					String[] str = new String[2];
//					str[0] = rs.getString(1);
//					str[1] = String.valueOf((double) Math.round((rs.getDouble(2) / 1000000) * 100) / 100);
//					countListC.add(str);
//				}
//				list = countListC;
//			}
//			riskMapMainVoList = list;
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return riskMapMainVoList;
//	}
//
//	/**
//	 * @author 周东旭 导出标的统计
//	 */
//	public List<MapMainByAddrVo> queryRiskMapMain(List<Integer> ids, String sumAmount, String policyno, String MapCode,
//			String nowdate, String powerSQL, String sumPay) {
//		List<MapMainByAddrVo> mapMainByAddrVoList = new ArrayList<MapMainByAddrVo>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		StringBuffer sqlString = new StringBuffer();
//		for (int i = 0; i < ids.size(); i++) {
//			if (i == (ids.size() - 1)) {
//				sqlString.append(ids.get(i)); // SQL拼装，最后一条不加“,”。
//			} else if ((i % 999) == 0 && i > 0) {
//				sqlString.append(ids.get(i)).append(") or riskmap_main.ADDRESSID in ("); // 解决ORA-01795问题
//			} else {
//				sqlString.append(ids.get(i)).append(",");
//			}
//		}
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			// String sql ="select * from riskmap_main where" + nowdate +
//			// sumAmount+MapCode+policyno+" riskmap_main.sumamount <> 0"+
//			// " and EXISTS ( select prpDcompany.comCode from PrpDcompany prpDcompany where
//			// comCode = prpDcompany.comCode and (prpDcompany.upperPath like '00000000%'))"+
//			// " and addressID = " + ids.get(0);
//			String sql = "select riskmap_main.policyNo,riskmap_main.classcode,riskmap_main.riskCode,riskmap_main.startDate,riskmap_main.enddate,riskmap_main.comcode,riskmap_main.coinsflag,riskmap_main.coinsRate,riskmap_main.sumAmount,riskmap_insured.InsuredName,riskmap_insured.insuredType,riskmap_insured.IdentifyType,riskmap_insured.IdentifyNumber,riskmap_main.proposalNo from riskmap_main , riskmap_insured  where riskmap_main.addressID = riskmap_insured.addressID  and "
//					+ nowdate + sumAmount + MapCode + policyno + " riskmap_main.sumamount <>0" + powerSQL + sumPay
//					+ " and (riskmap_main.ADDRESSID in (" + sqlString.toString() + "))";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			BigDecimal sumAmount1 = new BigDecimal(10000);
//			BigDecimal oneHundred = new BigDecimal(100);
//			for (; rs.next();) {
//				MapMainByAddrVo mapMainByAddrVo = new MapMainByAddrVo();
//				mapMainByAddrVo.setPolicyNo(rs.getString(1));
//				mapMainByAddrVo.setClassCode(rs.getString(2));
//				mapMainByAddrVo.setRiskCode(rs.getString(3));
//				mapMainByAddrVo.setStartDate(rs.getDate(4).toString());
//				mapMainByAddrVo.setEndDate(rs.getDate(5).toString());
//				mapMainByAddrVo.setComCode(rs.getString(6));
//				mapMainByAddrVo.setCoinsFlag(rs.getString(7));
//				// 翻译coinsFlag
//				String coinsFlag = mapMainByAddrVo.getCoinsFlag();
//				String coinsFlagCName = dataSourcesService.queryCoinsFlagCName(coinsFlag);
//				mapMainByAddrVo.setCoinsFlag(coinsFlagCName);
//				mapMainByAddrVo.setCoinsRate(rs.getBigDecimal(8));
//				mapMainByAddrVo.setSumAmount(rs.getBigDecimal(9));
//				mapMainByAddrVo.setSumAmount(rs.getBigDecimal(9).divide(sumAmount1, 2, BigDecimal.ROUND_HALF_EVEN));
//				mapMainByAddrVo.setInsuredName(rs.getString(10));
//				mapMainByAddrVo.setInsuredType(rs.getString(11));
//				mapMainByAddrVo.setIdentifyType(rs.getString(12));
//				String identifyType = mapMainByAddrVo.getIdentifyType();
//				String identifyTypeName = "";
//				identifyTypeName = dataSourcesService.queryIdentifyTypeName(identifyType);
//				mapMainByAddrVo.setIdentifyType(identifyTypeName);
//				mapMainByAddrVo.setIdentifyNumber(rs.getString(13));
//				mapMainByAddrVo.setProposalNo(rs.getString(14));
//				// 计算我司保额
//				mapMainByAddrVo.setOurCoverage(
//						mapMainByAddrVo.getSumAmount().multiply(mapMainByAddrVo.getCoinsRate().divide(oneHundred, 0)));
//				mapMainByAddrVoList.add(mapMainByAddrVo);
//			}
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return mapMainByAddrVoList;
//	}
//
//	/**
//	 * 释放数据库资源，包括数据库连接和PrepareStatement对象
//	 */
//
//	private void releaseResources(Statement stat, Connection conn, ResultSet rs) {
//		try {
//			if (rs != null) {
//				rs.close();
//			}
//		} catch (SQLException e) {
//			log.info("关闭异常：" + e.getMessage(), e);
//			throw new RuntimeException("关闭异常:" + e);
//		}
//		try {
//			if (stat != null) {
//				stat.close();
//			}
//		} catch (SQLException e) {
//			log.info("关闭异常：" + e.getMessage(), e);
//			throw new RuntimeException("关闭异常:" + e);
//		}
//		try {
//			if (conn != null) {
//				conn.close();
//			}
//		} catch (SQLException e) {
//			log.info("关闭异常：" + e.getMessage(), e);
//			throw new RuntimeException("关闭异常:" + e);
//		}
//	}
//
//	/**
//	 * 校验
//	 */
//	@Override
//	public String queryUserCode(String userCode) {
//		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
//		String powerSQL;
//		try {
//			powerSQL = saaAPIService.addPower("riskcontrol", userCode, "riskins_query", "comCode", "", "");
//			if (powerSQL.indexOf("riskmap_main.comCode") == -1) {
//				// System.out.println(powerSQL.indexOf("comCode"));
//				// System.out.println(powerSQL.indexOf("comCode",powerSQL.indexOf("comCode")+7));
//				int begin = powerSQL.indexOf("comCode", powerSQL.indexOf("comCode") + 7);
//				int end = begin + 7;
//				StringBuilder sb = new StringBuilder(powerSQL);
//				powerSQL = sb.replace(begin, end, "riskmap_main.comCode").toString();
//			}
//			System.out.println(powerSQL);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("addPower执行异常：" + e.getMessage(), e);
//			throw new RuntimeException("addPower执行异常:" + e);
//		}
//		return powerSQL;
//	}
//
//	/**
//	 * @功能：根据地址、保单号获取标的信息
//	 * @param RiskMapMainVo
//	 * @return RiskMapMainVo
//	 * @author 马军亮 @时间：2018-09-18 @修改记录：
//	 */
//	@Override
//	public RiskMapMainVo queryAddress(RiskMapMainVo riskMapMainVo, String powerSQL) {
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMapAddress = bundle.getString("riskMap_address");
//		String riskMapAddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMapAddress = MapUtils.getDataSetTableName(riskMapAddressName);
//		String policyNo = riskMapMainVo.getPolicyno();
//		String address = riskMapMainVo.getAddress();
//		String validStatus = riskMapMainVo.getValidStatus();
//		String operatorCode = riskMapMainVo.getOperatorCode();
//		String addressSql = "";
//		String operatorCodeSql = "";
//		String validStatusSql = "";
//		String policyNoSql = "";
//		String addPowerSQL = "";
//		int pageNo = riskMapMainVo.getPageNo();
//		int pageSize = riskMapMainVo.getPageSize();
//		int rownum = pageNo * pageSize + 1;
//		int rn = (pageNo - 1) * pageSize;
//		long totalCount = 0L;
//		totalCount = this.getTotalCount(riskMapMainVo, powerSQL);
//		long pageCount = 0L;
//		if (totalCount % pageSize == 0) {
//			pageCount = totalCount / (long) pageSize;
//		} else {
//			pageCount = totalCount / (long) pageSize + 1L;
//		}
//		riskMapMainVo.setTotalCount(totalCount);
//		riskMapMainVo.setTotalPage(pageCount);
//		if (totalCount != 0L) {
//			Connection conn = null;
//			PreparedStatement stat = null;
//			ResultSet rs = null;
//			List<RiskMapAddressVo> riskMapAddressVoList = new ArrayList<RiskMapAddressVo>();
//			try {
//				if (StringUtils.isNotBlank(address)) {
//					addressSql = " ADDRESSNAME like '" + address.trim() + "%' and";
//				}
//				if (StringUtils.isNotBlank(operatorCode)) {
//					operatorCodeSql = " OPERATORCODE = '" + operatorCode.trim() + "' and";
//				}
//				String checkSql = " SMID in(select addressid from  RISKMAP_MAIN b where 1=1 ";
//				if ("1".equals(validStatus)) {
//					validStatusSql = " VALIDSTATUS = '1' and";
//					// validStatusSql = " and sysdate between STARTDATE and ENDDATE ";
//				}
//				if (StringUtils.isNotBlank(policyNo)) {
//					policyNoSql = " SMID in(select addressid from  RISKMAP_MAIN where policyno='" + policyNo.trim()
//							+ "') and";
//					// policyNoSql = "and policyno='"+policyNo.trim()+"'";
//				}
//				String checkOverSql = checkSql + validStatusSql + policyNoSql + ") and";
//				// addPowerSQL = " exists (select 1 from riskmap_main riskmap_main where
//				// riskmap_main.addressid = "+riskMapAddress+".smid and "+powerSQL+")";
//				addPowerSQL = " exists (select 1 from riskmap_main  where riskmap_main.addressid = " + riskMapAddress
//						+ ".smid and " + powerSQL + ")";
//
//				String allSql = addressSql + operatorCodeSql + validStatusSql + policyNoSql + addPowerSQL;
//				// String allSql = addressSql + operatorCodeSql + checkOverSql +addPowerSQL;
//
//				if (StringUtils.isNotBlank(allSql)) {
//					if (allSql.substring(allSql.length() - 3, allSql.length()).indexOf("and") > -1) {
//						allSql = allSql.substring(0, allSql.length() - 3);
//					}
//				}
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				// String sql= "select
//				// SMID,SMX,SMY,ADDRESSNAME,SCORE,VALIDSTATUS,OPERATORCODE,OPERATORDATE,POINTX_2000,POINTY_2000,b.STARTDATE,b.ENDDATE
//				// from(select a.*,rownum rn from (select * from "
//				// + riskMapAddress + " ,RISKMAP_MAIN where "+riskMapAddress+".SMID =
//				// riskmap_main.ADDRESSID and "+ allSql +") a where rownum < "+ rownum +") b
//				// where VALIDSTATUS = '1' and rn>" + rn;
//
//				String sql = "select SMID,SMX,SMY,ADDRESSNAME,SCORE,VALIDSTATUS,OPERATORCODE,OPERATORDATE,POINTX_2000,POINTY_2000 from(select a.*,rownum rn from (select * from "
//						+ riskMapAddress + " where " + allSql + ") a where rownum < " + rownum + ") where  rn>" + rn;
//
//				stat = conn.prepareStatement(sql);
//				rs = stat.executeQuery();
//				for (; rs.next();) {
//					RiskMapAddressVo riskMapAddressVo = new RiskMapAddressVo();
//					riskMapAddressVo.setSmId(rs.getInt(1));
//					riskMapAddressVo.setSmX(rs.getBigDecimal(2));
//					riskMapAddressVo.setSmY(rs.getBigDecimal(3));
//					riskMapAddressVo.setAddressName(rs.getString(4));
//					riskMapAddressVo.setScore(rs.getBigDecimal(5));
//					riskMapAddressVo.setValidStatus(rs.getString(6));
//					riskMapAddressVo.setOperatorCode(rs.getString(7));
//					riskMapAddressVo.setOperatorDate(rs.getDate(8));
//					riskMapAddressVo.setPointX_2000(rs.getBigDecimal(9));
//					riskMapAddressVo.setPointY_2000(rs.getBigDecimal(10));
//
//					// Date now = new Date();
//					// long now_long = now.getTime();
//					// if(now_long > rs.getDate(11).getTime() && now_long <
//					// rs.getDate(12).getTime()){
//					// riskMapAddressVo.setValidStatus("1");
//					// } else {
//					// riskMapAddressVo.setValidStatus("0");
//					// }
//
//					riskMapAddressVoList.add(riskMapAddressVo);
//				}
//				riskMapMainVo.setRiskMapAddressVoList(riskMapAddressVoList);
//			} catch (SQLException e) {
//				log.info("查询异常：" + e.getMessage(), e);
//				e.printStackTrace();
//				throw new RuntimeException("查询异常:" + e);
//			} finally {
//				releaseResources(stat, conn, rs);
//			}
//		}
//		return riskMapMainVo;
//	}
//
//	private long getTotalCount(RiskMapMainVo riskMapMainVo, String powerSQL) {
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMapAddress = bundle.getString("riskMap_address");
//		String riskMapAddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMapAddress = MapUtils.getDataSetTableName(riskMapAddressName);
//		long totalCount = 0L;
//		String policyNo = riskMapMainVo.getPolicyno();
//		String address = riskMapMainVo.getAddress();
//		String validStatus = riskMapMainVo.getValidStatus();
//		String operatorCode = riskMapMainVo.getOperatorCode();
//		String addressSql = "";
//		String operatorCodeSql = "";
//		String validStatusSql = "";
//		String policyNoSql = "";
//		String addPowerSQL = "";
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			if (StringUtils.isNotBlank(address)) {
//				addressSql = " ADDRESSNAME like '" + address.trim() + "%' and";
//			}
//			if (StringUtils.isNotBlank(operatorCode)) {
//				operatorCodeSql = " OPERATORCODE = '" + operatorCode.trim() + "' and";
//			}
//			if ("1".equals(validStatus)) {
//				validStatusSql = " VALIDSTATUS = '1' and";
//			}
//			if (StringUtils.isNotBlank(policyNo)) {
//				policyNoSql = " SMID in(select addressid from  RISKMAP_MAIN where policyno='" + policyNo.trim()
//						+ "') and";
//			}
//
//			addPowerSQL = " exists (select 1 from riskmap_main where riskmap_main.addressid = " + riskMapAddress
//					+ ".smid and " + powerSQL + ")";
//			String allSql = addressSql + operatorCodeSql + validStatusSql + policyNoSql + addPowerSQL;
//
//			if (StringUtils.isNotBlank(allSql)) {
//				if (allSql.substring(allSql.length() - 3, allSql.length()).indexOf("and") > -1) {
//					allSql = allSql.substring(0, allSql.length() - 3);
//				}
//			}
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select count(*) from " + riskMapAddress + " where " + allSql;
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				totalCount = rs.getLong(1);
//			}
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return totalCount;
//	}
//
//	/**
//	 * @功能：修改标的经纬度
//	 * @param RiskMapInsuredModify
//	 * @return AjaxResult
//	 * @author 马军亮 @时间：2018-09-20 @修改记录：
//	 */
//	@Override
//	public AjaxResult updateLonLat(RiskMapAddressModify riskMapAddressModify) {
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			RiskMapAddressModifyId id = new RiskMapAddressModifyId();
//			id.setAddressID(riskMapAddressModify.getAddressID());
//			int sum = getMaxSerialNo(riskMapAddressModify.getAddressID());
//			id.setSerialNo(sum + 1);
//			riskMapAddressModify.setId(id);
//			Gps gps = MapTransferUtils.gps84_To_Gcj02(riskMapAddressModify.getAfter_pointY_2000().doubleValue(),
//					riskMapAddressModify.getAfter_pointX_2000().doubleValue());
//			double lat = stringToDouble(gps.getWgLat());
//			double lon = stringToDouble(gps.getWgLon());
//			riskMapAddressModify.setAfter_pointX_02(BigDecimal.valueOf(lon));
//			riskMapAddressModify.setAfter_pointY_02(BigDecimal.valueOf(lat));
//			if (this.updateAddressLonLat(riskMapAddressModify)) {
//				databaseDao.save(RiskMapAddressModify.class, riskMapAddressModify);
//			} else {
//				ajaxResult.setStatus(0);
//				ajaxResult.setData("保存失败");
//			}
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("保存成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("保存失败");
//			log.info("保存异常：" + e.getMessage(), e);
//			e.printStackTrace();
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * @功能：查询修改标的记录条数
//	 * @param RiskMapInsuredModify
//	 * @return int
//	 * @author 马军亮 @时间：2018-09-20 @修改记录：
//	 */
//	private int getMaxSerialNo(Integer addressId) {
//		int sum = 0;
//		try {
//			sum = (int) databaseDao.getCount("select count(*) from RiskMapAddressModify a where a.id.addressID=?",
//					addressId);
//		} catch (Exception e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		}
//		return sum;
//	}
//
//	/**
//	 * @功能：修改CommonConst.RISKMAP_ADDRESS
//	 * @param RiskMapInsuredModify
//	 * @return boolean
//	 * @author 马军亮 @时间：2018-09-20
//	 */
//	private boolean updateAddressLonLat(RiskMapAddressModify riskMapAddressModify) {
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMapAddress = bundle.getString("riskMap_address");
//		String riskMapAddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMapAddress = MapUtils.getDataSetTableName(riskMapAddressName);
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String operatorCode = riskMapAddressModify.getOperatorCode();
//		Integer addressId = riskMapAddressModify.getId().getAddressID();
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "UPDATE " + riskMapAddress + " SET SCORE = '1',SMX = '"
//					+ riskMapAddressModify.getAfter_pointX_2000() + "',SMY= '"
//					+ riskMapAddressModify.getAfter_pointY_2000() + "',POINTX_2000='"
//					+ riskMapAddressModify.getAfter_pointX_2000() + "',POINTY_2000 = '"
//					+ riskMapAddressModify.getAfter_pointY_2000() + "',POINTX_02 = '"
//					+ riskMapAddressModify.getAfter_pointX_02() + "',POINTY_02='"
//					+ riskMapAddressModify.getAfter_pointY_02() + "', MANUALFLAG = '1',OPERATORCODE='" + operatorCode
//					+ "',OPERATORDATE = to_date(to_CHAR(sysdate,'YYYY-MM-DD')||to_char(OPERATORDATE,'hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') WHERE SMID = '"
//					+ addressId + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			// 更新栅格数据风险值
//			String[] smArray = new String[3];
//			smArray[0] = addressId.toString();
//			smArray[1] = riskMapAddressModify.getAfter_pointX_2000().toString();
//			smArray[2] = riskMapAddressModify.getAfter_pointY_2000().toString();
//			this.updateData(smArray, riskMapAddress);
//
//		} catch (SQLException e) {
//			log.info("update异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			return false;
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return true;
//	}
//
//	/** 每日数据处理，生成riskmap各表以及地图表 */
//	@Override
//	public AjaxResult createData(String proposalNo) {
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMap_Address = bundle.getString("riskMap_address");
//		String riskMap_AddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMap_Address = MapUtils.getDataSetTableName(riskMap_AddressName);
//		AjaxResult ajaxResult = new AjaxResult();
//		// 查询cmain表数据
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("proposalNo", proposalNo);
//		PrpCmain cmain = databaseDao.findUnique(PrpCmain.class, queryRule);
//		// 需满足如下条件方才进行数据存储:
//		// 1、险类为01、03;
//		// 2、核保通过;
//		// 3、联共保类型为00,01,10,11,20,21
//		if (cmain != null && "1,3".indexOf(cmain.getUnderWriteFlag()) > -1
//				&& "00,01,10,11,20,21".indexOf(cmain.getCoinsFlag()) > -1
//				&& "01,03".indexOf(cmain.getClassCode()) > -1) {
//			// 查询cmain表数据
//			QueryRule queryRuleForId = QueryRule.getInstance();
//			// 查询子表数据
//			queryRuleForId.addEqual("id.proposalNo", proposalNo);
//			List<PrpCaddress> caddressList = databaseDao.findAll(PrpCaddress.class, queryRuleForId);
//			List<PrpCinsured> cinsuredList = databaseDao.findAll(PrpCinsured.class, queryRuleForId);
//			List<PrpCitemKind> citemKindList = databaseDao.findAll(PrpCitemKind.class, queryRuleForId);
//			List<PrpCcoins> ccoinsList = databaseDao.findAll(PrpCcoins.class, queryRuleForId);
//
//			// 地址处理
//			Map<Integer, Integer> addressMap = new HashMap<Integer, Integer>();
//			Set<String> addressSet = new HashSet<String>();
//			for (PrpCaddress address : caddressList) {
//				// 如果地址为空，此单不作处理
//				if (StringUtils.isBlank(address.getAddressName())) {
//					break;
//				}
//				addressSet.add(address.getAddressName());
//				Connection conn = null;
//				PreparedStatement stats = null;
//				PreparedStatement statsAdd = null;
//				ResultSet rs = null;
//				int SMID = 0;
//				try {
//					// 对地址进行插入
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					RiskMapAddress riskMapAddress = new RiskMapAddress();
//					riskMapAddress.setAddressName(address.getAddressName());
//					riskMapAddress.setPostCode(address.getPostCode());
//
//					// 获取经纬度、分值以及其他信息
//					riskMapAddress = getLongAndLat(riskMapAddress);
//					String sqlAdd = "insert into " + riskMap_Address
//							+ "(SMID,SMX,SMY,SMLIBTILEID,SMUSERID,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02,CITYCODE,"
//							+ "ADDRESSNAME,SCORE,VALIDSTATUS,MANUALFLAG,OPERATORCODE,OPERATORDATE,RAINSCALE,THUNDERSTORMSCALE,SNOWSTORMSCALE,HAILSCALE,"
//							+ "FLOODSCALE,TYPHOONSCALE,LANDSLIDESCALE,EQPGA) select max(SMID)+1,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? from "
//							+ riskMap_Address + " WHERE NOT EXISTS(SELECT addressname FROM " + riskMap_Address
//							+ " WHERE addressname = ?)";
//					statsAdd = conn.prepareStatement(sqlAdd);
//					// 设置参数
//					statsAdd.setBigDecimal(1, riskMapAddress.getPointX());
//					statsAdd.setBigDecimal(2, riskMapAddress.getPointY());
//					statsAdd.setInt(3, 1);
//					statsAdd.setInt(4, 0);
//					statsAdd.setBigDecimal(5, riskMapAddress.getPointX());
//					statsAdd.setBigDecimal(6, riskMapAddress.getPointY());
//					statsAdd.setBigDecimal(7, riskMapAddress.getPointX2());
//					statsAdd.setBigDecimal(8, riskMapAddress.getPointY2());
//					statsAdd.setString(9, riskMapAddress.getCtiyCode());
//					statsAdd.setString(10, riskMapAddress.getAddressName());
//					statsAdd.setBigDecimal(11, riskMapAddress.getScore());
//					statsAdd.setString(12, riskMapAddress.getValidStatus());
//					statsAdd.setString(13, riskMapAddress.getManualFlag());
//					statsAdd.setString(14, null);
//					statsAdd.setTime(15, null);
//					statsAdd.setBigDecimal(16, riskMapAddress.getDangerArray()[0]);
//					statsAdd.setBigDecimal(17, riskMapAddress.getDangerArray()[1]);
//					statsAdd.setBigDecimal(18, riskMapAddress.getDangerArray()[2]);
//					statsAdd.setBigDecimal(19, riskMapAddress.getDangerArray()[3]);
//					statsAdd.setBigDecimal(20, riskMapAddress.getDangerArray()[4]);
//					statsAdd.setBigDecimal(21, riskMapAddress.getDangerArray()[5]);
//					statsAdd.setBigDecimal(22, riskMapAddress.getDangerArray()[6]);
//					statsAdd.setBigDecimal(23, riskMapAddress.getDangerArray()[7]);
//					statsAdd.setString(24, riskMapAddress.getAddressName());
//					// 处理不存在地址信息而产生的报错问题
//					try {
//						statsAdd.executeUpdate();
//					} catch (Exception e) {
//						if (e.getMessage().indexOf("ORA-01400") == -1) {
//							e.printStackTrace();
//							log.info(proposalNo + "处理失败,原因：地址信息存储出错!");
//							ajaxResult.setStatus(0);
//							ajaxResult.setStatusText("地址信息存储出错!");
//							return ajaxResult;
//						}
//					}
//					// 查询地址所对应的编号
//					String sql = "select SMID from " + riskMap_Address + " where addressname = ?";
//					stats = conn.prepareStatement(sql);
//					stats.setString(1, address.getAddressName());
//					rs = stats.executeQuery();
//					if (rs.next()) {
//						SMID = rs.getInt(1);
//						addressMap.put(address.getId().getAddressNo(), SMID);
//						// PrpCaddress id，smdtv_xx id
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//					log.info(proposalNo + "处理失败,原因：地址信息查询出错!");
//					ajaxResult.setStatus(0);
//					ajaxResult.setStatusText("地址信息查询出错!");
//					return ajaxResult;
//				} finally {
//					try {
//						if (stats != null) {
//							stats.close();
//						}
//						if (statsAdd != null) {
//							statsAdd.close();
//						}
//						if (conn != null && !conn.isClosed()) {
//							conn.close();
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			// 如果set存储地址与原地址数量不匹配，说明地址存在问题。
//			if (addressSet.size() != caddressList.size()) {
//				ajaxResult.setStatus(0);
//				ajaxResult.setStatusText("该单号下地址信息错误!");
//				return ajaxResult;
//			}
//			// 关系人处理
//			List<RiskMapInsured> minsuredList = new ArrayList<RiskMapInsured>();
//			for (PrpCinsured insured : cinsuredList) {
//				RiskMapInsured minsured = new RiskMapInsured();
//				if (insured != null && insured.getInsuredFlag() != null) {
//					// 被保险人和投保人进行存储
//					if ('1' == insured.getInsuredFlag().charAt(0) || '1' == insured.getInsuredFlag().charAt(1)) {
//						for (int key : addressMap.keySet()) {
//							BeanUtils.copyProperties(insured, minsured);
//							RiskMapInsuredId id = new RiskMapInsuredId();
//							id.setAddressID(addressMap.get(key));
//							id.setInsuredNo(insured.getId().getSerialNo());
//							id.setProposalNo(proposalNo);
//							minsured.setId(id);
//							minsuredList.add(minsured);
//						}
//					}
//				}
//			}
//			// 条款责任处理
//			List<RiskMapItemkind> mitemKindList = new ArrayList<RiskMapItemkind>();
//			for (PrpCitemKind itemKind : citemKindList) {
//				// 条款责任无关联地址
//				if (itemKind.getAddressNo() == null) {
//					for (int key : addressMap.keySet()) {
//						RiskMapItemkind mitemKind = new RiskMapItemkind();
//						BeanUtils.copyProperties(itemKind, mitemKind);
//						RiskMapItemkindId id = new RiskMapItemkindId();
//						id.setAddressID(addressMap.get(key));
//						id.setItemkindNo(itemKind.getId().getItemKindNo());
//						id.setProposalNo(proposalNo);
//						mitemKind.setId(id);
//						mitemKindList.add(mitemKind);
//					}
//					// 条款责任存在关联地址
//				} else {
//					RiskMapItemkind mitemKind = new RiskMapItemkind();
//					if (addressMap.containsKey(itemKind.getAddressNo())) {
//						BeanUtils.copyProperties(itemKind, mitemKind);
//						RiskMapItemkindId id = new RiskMapItemkindId();
//						id.setAddressID(addressMap.get(itemKind.getAddressNo()));
//						id.setItemkindNo(itemKind.getId().getItemKindNo());
//						id.setProposalNo(proposalNo);
//						mitemKind.setId(id);
//						mitemKindList.add(mitemKind);
//					}
//				}
//
//			}
//			// 主表处理
//			List<RiskMapMain> mmainList = new ArrayList<RiskMapMain>();
//			for (int key : addressMap.keySet()) {
//				RiskMapMain mmain = new RiskMapMain();
//				BeanUtils.copyProperties(cmain, mmain);
//				RiskMapMainId id = new RiskMapMainId();
//				id.setAddressID(addressMap.get(key));
//				id.setProposalNo(cmain.getProposalNo());
//				mmain.setId(id);
//				// 总保额计算
//				mmain.setSumAmount(BigDecimal.ZERO);
//				for (RiskMapItemkind mitemKind : mitemKindList) {
//					if (mitemKind.getId().getAddressID().equals(addressMap.get(key))
//							&& "1".equals(mitemKind.getCalculateFlag())) {
//						if ("CNY".equals(mitemKind.getCurrency())) {
//							mmain.setSumAmount(mmain.getSumAmount().add(mitemKind.getAmount()));
//						} else {
//							// 获取汇率
//							PrpDexch prpDexch = null;
//							try {
//								prpDexch = DictAPIService.getPrpDexch(RiskControlConst.SYSTEMCODE, cmain.getStartDate(),
//										mitemKind.getCurrency(), "CNY");
//								if (prpDexch != null) {
//									mmain.setSumAmount(mmain.getSumAmount()
//											.add(mitemKind.getAmount().multiply(prpDexch.getExchRate())));
//								}
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								ajaxResult.setStatus(0);
//								ajaxResult.setStatusText("数据字典获取汇率失败!");
//								log.info(proposalNo + "处理失败,原因：数据字典获取汇率失败!");
//								return ajaxResult;
//							}
//
//						}
//
//					}
//				}
//
//				// 联共保比例计算
//				if (mmain.getCoinsFlag() != null && '0' == (mmain.getCoinsFlag().charAt(0))) {
//					mmain.setCoinsRate(BigDecimal.valueOf(100));
//				} else {
//					mmain.setCoinsRate(BigDecimal.ZERO);
//					for (int i = 0; i < ccoinsList.size(); i++) {
//						// 去除掉同序号得联共保信息（多币别情况下）
//						for (int j = 1; j < ccoinsList.size(); j++) {
//							if (ccoinsList.get(i).getId().getSerialNo() == ccoinsList.get(j).getId().getSerialNo()) {
//								ccoinsList.remove(j);
//							}
//						}
//						// 我方所占份额
//						if ("1".equals(ccoinsList.get(i).getCoinsType())
//								|| "2".equals(ccoinsList.get(i).getCoinsType())) {
//							mmain.setCoinsRate(mmain.getCoinsRate().add(ccoinsList.get(i).getCoinsRate()));
//						}
//					}
//				}
//				mmainList.add(mmain);
//			}
//
//			// 删除原数据
//			Connection conn = null;
//			PreparedStatement stats = null;
//			String[] tablename = { "riskmap_main", "riskmap_insured", "riskmap_itemkind" };
//			for (int i = 0; i < 3; i++) {
//				try {
//					String sql = "delete from " + tablename[i] + " where proposalno = ?";
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					stats = conn.prepareStatement(sql);
//					stats.setString(1, proposalNo);
//					stats.executeUpdate();
//				} catch (SQLException e) {
//					e.printStackTrace();
//					ajaxResult.setStatus(0);
//					ajaxResult.setStatusText("删除riskmap表数据失败!");
//					log.info(proposalNo + "处理失败,原因：删除riskmap表数据失败!");
//					return ajaxResult;
//				} finally {
//					try {
//						if (stats != null) {
//							stats.close();
//						}
//						if (conn != null && !conn.isClosed()) {
//							conn.close();
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			// 保存新数据
//			databaseDao.saveAll(RiskMapMain.class, mmainList);
//			databaseDao.saveAll(RiskMapItemkind.class, mitemKindList);
//			databaseDao.saveAll(RiskMapInsured.class, minsuredList);
//			ajaxResult.setStatus(1);
//		} else {
//			// 删除原数据
//			Connection conn = null;
//			PreparedStatement stats = null;
//			String[] tablename = { "riskmap_main", "riskmap_insured", "riskmap_itemkind" };
//			for (int i = 0; i < 3; i++) {
//				try {
//					String sql = "delete from " + tablename[i] + " where proposalno = ?";
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					stats = conn.prepareStatement(sql);
//					stats.setString(1, proposalNo);
//					stats.executeUpdate();
//					ajaxResult.setStatus(1);
//				} catch (SQLException e) {
//					e.printStackTrace();
//					ajaxResult.setStatus(0);
//					ajaxResult.setStatusText("删除riskmap表数据失败!");
//					log.info(proposalNo + "处理失败,原因：删除riskmap表数据失败!");
//					return ajaxResult;
//				} finally {
//					try {
//						if (stats != null) {
//							stats.close();
//						}
//						if (conn != null && !conn.isClosed()) {
//							conn.close();
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//		}
//		return ajaxResult;
//	}
//
//	/** 根据地址信息获取经纬度 */
//	@Override
//	public HttpResponseAddressReset getLongAndLat_test(RiskMapAddress riskMapAddress) {
//		String addressname = riskMapAddress.getAddressName();
//		String postName = "北京";
//		String url_test = "http://10.10.1.2/service/coder/geocoding/score2";
//		HttpResponseAddressReset resp = getScore(url_test, filteraddress(addressname), postName);
//		return resp;
//	}
//
//	/** 根据地址信息获取经纬度 */
//	@Override
//	public RiskMapAddress getLongAndLat(RiskMapAddress riskMapAddress) {
//		Map<String, String> map = new HashMap<String, String>();
//		String key = cacheManager.generateCacheKey("getLongAndLat", "PostCode");
//		Object object = cacheManager.getCache(key);
//		if (object == null) {
//			QueryRule queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.codeType", "PostCode");
//			List<RiskDcode> dcodeList = databaseDao.findAll(RiskDcode.class, queryRule);
//			for (RiskDcode dcode : dcodeList) {
//				map.put(dcode.getId().getCodeCode(), dcode.getCodeCname());
//			}
//			cacheManager.putCache(key, map);
//		} else {
//			map = (HashMap<String, String>) object;
//		}
//		String postName = "";
//		if (riskMapAddress.getPostCode() != null) {
//			if (map.containsKey(riskMapAddress.getPostCode().substring(0, 4) + "00")) {
//				postName = map.get(riskMapAddress.getPostCode().substring(0, 4) + "00");
//			} else if (map.containsKey(riskMapAddress.getPostCode().substring(0, 3) + "000")) {
//				postName = map.get(riskMapAddress.getPostCode().substring(0, 3) + "000");
//			} else if (map.containsKey(riskMapAddress.getPostCode().substring(0, 2) + "0000")) {
//				postName = map.get(riskMapAddress.getPostCode().substring(0, 2) + "0000");
//			} else {
//				postName = "全国";
//			}
//		} else {
//			postName = "全国";
//		}
//
//		// 获取地址经纬度和风险值
//		String response;
//		try {
//			ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//			String addressname = riskMapAddress.getAddressName();
//			// String geoUrl = bundle.getString("geoUrl");
//			// HttpResponseAddress resp =
//			// getScore(geoUrl,filteraddress(addressname),postName);
//			String url_new = bundle.getString("geoUrl");
//			HttpResponseAddressReset resp = getScore(url_new, filteraddress(addressname), postName);
//
//			if (resp != null && resp.getData().getRows()[0].getLocation() != null) {
//				if (new BigDecimal(resp.getData().getRows()[0].getScore()).compareTo(new BigDecimal("0.4")) > 0) {
//					// 初级清洗分值过低，进入二次地址清洗
//				} else {
//					// 重新获取城市进行查询
//					resp = getScore(url_new, filteraddress(addressname), subpostname(addressname));
//					if (resp != null && resp.getData() != null && resp.getData().getRows() != null
//							&& resp.getData().getRows()[0] != null && resp.getData().getRows()[0].getScore() != null
//							&& (new BigDecimal(resp.getData().getRows()[0].getScore())
//									.compareTo(new BigDecimal("0.4")) > 0)) {
//					} else {
//						List<HttpResponseAddressReset> list = new ArrayList<HttpResponseAddressReset>();
//						// 1.截取第一个特殊字符前的内容
//						// System.out.println("截取第一个特殊字符前的内容");
//						list.add(getScore(url_new, subaddress(addressname), postName));
//						// 2.截取第一个特殊字符前内容后，取消特殊业务叙述
//						// System.out.println("截取第一个特殊字符前内容后，取消特殊业务叙述");
//						list.add(getScore(url_new, busSubaddress(addressname), postName));
//						// 3.地址信息截取第一个特殊字符内容，查询城市从地址信息中获取
//						// System.out.println("地址信息截取第一个特殊字符内容，查询城市从地址信息中获取");
//						list.add(getScore(url_new, subaddress(addressname), subpostname(addressname)));
//						// 4.地址信息截取第一个特殊字符前内容后，取消特殊业务叙述，查询城市从地址信息中获取
//						// System.out.println("地址信息截取第一个特殊字符前内容后，取消特殊业务叙述，查询城市从地址信息中获取");
//						list.add(getScore(url_new, busSubaddress(addressname), subpostname(addressname)));
//
//						resp = compareResList_New(list);
//					}
//				}
//			}
//			if (resp != null && resp.getData().getRows()[0] != null) {
//				String location = resp.getData().getRows()[0].getLocation();
//				String[] aa = location.split(",");
//				BigDecimal score = new BigDecimal(resp.getData().getRows()[0].getScore());
//
//				Gps gps = MapTransferUtils.gcj_To_Gps84(Double.parseDouble(aa[1]), Double.parseDouble(aa[0]));
//				riskMapAddress.setPointX(BigDecimal.valueOf(stringToDouble(gps.getWgLon())));
//				riskMapAddress.setPointY(BigDecimal.valueOf(stringToDouble(gps.getWgLat())));
//				riskMapAddress.setPointX2(new BigDecimal(aa[0]));
//				riskMapAddress.setPointY2(new BigDecimal(aa[1]));
//				riskMapAddress.setScore(score);
//				if (score.compareTo(new BigDecimal(0.4)) > 0) {
//					riskMapAddress.setValidStatus("1");
//				} else {
//					riskMapAddress.setValidStatus("0");
//				}
//				if (resp.getData().getRows()[0].getAdcode() != null) {
//					riskMapAddress.setCtiyCode(resp.getData().getRows()[0].getAdcode());
//				}
//				// 请求参数
//				String[] rasterDangerArray = { "rain_hazard_scale_1km", "thunderstorm_hazard_scale_1km",
//						"snowstorm_hazard_scale_1km", "hail_hazard_scale_1km", "flood_hazard_scale_1km",
//						"typhoon_hazard_scale_1km", "landslide_hazard_scale_1km", "eq_hazard_PGA" };
//
//				BigDecimal[] valueArray = new BigDecimal[8];
//				for (int i = 0; i < rasterDangerArray.length; i++) {
//					String serverName = bundle.getString("serverName");
//					String url = serverName + "iserver/services/data-FXDT/rest/data/datasources/china/datasets/"
//							+ rasterDangerArray[i] + "/gridValue.json";
//					String resJson = doGet(url, "x=" + riskMapAddress.getPointX() + "&y=" + riskMapAddress.getPointY());
//					// System.out.println("请求的url:"+url+"?"+param);
//					GridValue gridValue = new GridValue();
//					if (StringUtils.isNotBlank(resJson)) {
//						gridValue = JSON.parseObject(resJson.toString(), GridValue.class);
//						BigDecimal gridValueBd = gridValue.getValue();
//						// 假如值小于0，则置为0,保留两位小数
//						if (gridValueBd.compareTo(new BigDecimal(0)) == -1) {
//							gridValueBd = BigDecimal.ZERO;
//						}
//						valueArray[i] = gridValueBd.setScale(2, BigDecimal.ROUND_HALF_UP);
//					} else {
//						System.out.println("请求的url:" + url + "?" + "x=" + riskMapAddress.getPointX() + "&y="
//								+ riskMapAddress.getPointY());
//						valueArray[i] = BigDecimal.ZERO;
//					}
//				}
//				riskMapAddress.setDangerArray(valueArray);
//
//			}
//
//		} catch (Exception e) {
//			log.info("地址数据处理失败:" + e.getMessage(), e);
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 若没有获取经纬度，赋值为0
//		if (riskMapAddress.getPointX() == null) {
//			riskMapAddress.setPointX(BigDecimal.ZERO);
//			riskMapAddress.setPointY(BigDecimal.ZERO);
//			riskMapAddress.setPointX2(BigDecimal.ZERO);
//			riskMapAddress.setPointY2(BigDecimal.ZERO);
//			riskMapAddress.setScore(BigDecimal.ZERO);
//			riskMapAddress.setValidStatus("0");
//		}
//		riskMapAddress.setManualFlag("0");
//		return riskMapAddress;
//	}
//
//	/** 地址特殊符号处理 */
//	public static String subAddressName(String result) {
//		result = result.trim().replaceAll("\\s*", "").replaceAll("#", "").replaceAll("\\([^\\(^\\)]*\\)", "")
//				.replaceAll("\\（[^\\（^\\）]*\\）", "").replaceAll("\\\\", "");
//		if (result.indexOf("、") > -1) {
//			result = result.substring(0, result.indexOf("、") + 1);
//		}
//		return result.trim();
//	}
//
//	/**
//	 * @功能：保存excell表中导入的信息
//	 * @param List<RiskMapFloodAddress>
//	 * @author liqiankun @时间：2018-10-12
//	 */
//	public void saveRiskMapFloodAddress(List<RiskMapFloodAddress> floodAddressList, UserInfo userInfo) {
//		List<RiskMapFloodAddress> riskMapFloodAddressLs = new ArrayList<RiskMapFloodAddress>();
//		List<Object[]> insertList = new ArrayList<>();
//		int i = 0;
//		String INSERT_TFMAIN = this.getTfmainSql();
//		if (floodAddressList.size() > 0 && floodAddressList != null) {
//			for (RiskMapFloodAddress floodAddress : floodAddressList) {
//				i++;
//				String dangerId = UUID.randomUUID().toString();
//				floodAddress.setDangerId(dangerId);
//				// 库中保留02 和84 的经纬度坐标点
//				Gps gps = MapTransferUtils.gps84_To_Gcj02(floodAddress.getPointy_2000().doubleValue(),
//						floodAddress.getPointx_2000().doubleValue());
//				double lat = stringToDouble(gps.getWgLat());
//				double lon = stringToDouble(gps.getWgLon());
//				floodAddress.setPointx_02(BigDecimal.valueOf(lon));
//				floodAddress.setPointy_02(BigDecimal.valueOf(lat));
//				BigDecimal floodDepth = floodAddress.getFloodDepth();
//				Integer floodGrade = 0;
//				if (floodDepth.compareTo(new BigDecimal(80)) > 0) {
//					floodGrade = 1;
//				} else if (floodDepth.compareTo(new BigDecimal(50)) > 0) {
//					floodGrade = 2;
//				} else if (floodDepth.compareTo(new BigDecimal(27)) > 0) {
//					floodGrade = 3;
//				} else if (floodDepth.compareTo(new BigDecimal(5)) > 0) {
//					floodGrade = 4;
//				} else {
//					floodGrade = 5;
//				}
//				floodAddress.setFloodGrade(floodGrade);
//				// 坐标系标志（1表示2000坐标系，0表示02 坐标系）
//				floodAddress.setLocationFlag("1");
//
//				String operatorCode = userInfo.getUserCode();
//				floodAddress.setOperatorCode(operatorCode);
//				insertList.add(new Object[] { this.getMaxValue() + i, floodAddress.getPointx_2000(),
//						floodAddress.getPointy_2000(), dangerId, floodAddress.getPointx_2000(),
//						floodAddress.getPointy_2000(), floodAddress.getPointx_02(), floodAddress.getPointy_02(),
//						floodAddress.getFloodAddress(), "1", "1", "B" });
//				try {
//					UserMgrAPIService um = new UserMgrAPIServiceImpl();
//					UserMsgResInfo userMsgInfo;
//					userMsgInfo = um.getUserMsg(operatorCode);
//					if (null != userMsgInfo) {
//						floodAddress.setComCode(userMsgInfo.getCOMCODE());
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				riskMapFloodAddressLs.add(floodAddress);
//			}
//			this.slaveJdbcTemplate.batchUpdate(INSERT_TFMAIN, insertList);
//			databaseDao.saveAll(RiskMapFloodAddress.class, riskMapFloodAddressLs);
//		}
//	}
//
//	/**
//	 * @功能：获取库中的最大的索引值
//	 * @param
//	 * @author liqiankun @时间：2018-10-12
//	 */
//	public Integer getFloodMaxNo() {
//
//		Integer serialNo = 0;
//		String sql = "select max(MAPID) from  RISKMAP_FLOODADDRESS";
//		List<Map<String, Object>> maxSerialNo = null;
//		try {
//			maxSerialNo = slaveJdbcTemplate.queryForList(sql);
//		} catch (Exception e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		}
//		if (null != maxSerialNo && maxSerialNo.size() > 0 && null != maxSerialNo.get(0).get("MAX(MAPID)")) {
//			// return (Integer) maxSerialNo.get(0).get("MAX(MAPID)");
//			return Integer.parseInt(maxSerialNo.get(0).get("MAX(MAPID)").toString());
//		} else {
//			return serialNo;
//		}
//	}
//
//	/**
//	 * 修改一条水淹数据
//	 * 
//	 * @param riskMapFloodUpdateVo
//	 */
//	public AjaxResult updateFloodLonLat(RiskMapFloodUpdateVo riskMapFloodUpdateVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		String riskmapTyphoonName = filePath.getString("riskMap_Typhoon");
//		// 获取表名
//		String riskmapTyphoon = MapUtils.getDataSetTableName(riskmapTyphoonName);
//		Connection conn = null;
//		Statement stat = null;
//		ResultSet rs = null;
//		// 根据传入的addressId查询数据库
//		try {
//			String floodDate = "";
//			if (riskMapFloodUpdateVo.getFloodDate() != null) {
//				floodDate = ", floodDate= to_date('" + riskMapFloodUpdateVo.getFloodDate() + "','yyyy-mm-dd')";
//			}
//			Gps gps = MapTransferUtils.gps84_To_Gcj02(riskMapFloodUpdateVo.getAfter_pointY_2000().doubleValue(),
//					riskMapFloodUpdateVo.getAfter_pointX_2000().doubleValue());
//			double lat = stringToDouble(gps.getWgLat());
//			double lon = stringToDouble(gps.getWgLon());
//			String sql = "update riskmap_floodAddress set FLOODGRADE= '" + riskMapFloodUpdateVo.getFloodGrade()
//					+ "', FLOODDEPTH= '" + riskMapFloodUpdateVo.getFloodDepth() + "', COMCODE = '"
//					+ riskMapFloodUpdateVo.getComCode() + "', OPERATORCODE='" + riskMapFloodUpdateVo.getOperatorCode()
//					+ "'" + floodDate + " where dangerId= '" + riskMapFloodUpdateVo.getDangerId() + "'";
//			String sql1 = "update " + riskmapTyphoon + " set SMX = '" + riskMapFloodUpdateVo.getAfter_pointX_2000()
//					+ "', SMY='" + riskMapFloodUpdateVo.getAfter_pointY_2000() + "', pointx_2000 = '"
//					+ riskMapFloodUpdateVo.getAfter_pointX_2000() + "', pointy_2000='"
//					+ riskMapFloodUpdateVo.getAfter_pointY_2000() + "', pointx_02 = '" + lon + "', pointy_02= '" + lat
//					+ "', dangeraddress = '" + riskMapFloodUpdateVo.getFloodAddress() + "' where dangerId= '"
//					+ riskMapFloodUpdateVo.getDangerId() + "'";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.createStatement();
//			stat.addBatch(sql);
//			stat.addBatch(sql1);
//			stat.executeBatch();
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("保存成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("保存失败");
//			log.info("保存异常：" + e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * 插入一条水淹数据
//	 * 
//	 * @param riskMapFloodUpdateVo
//	 */
//	public AjaxResult insertFloodLonLat(RiskMapFloodUpdateVo riskMapFloodUpdateVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		// 根据传入的addressId查询数据库
//		try {
//			// 查询主表RiskMapFloodAddress中最大的id
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select MAX(MAPID) MaxMapId from RISKMAP_FLOODADDRESS";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			int maxMapId = 0;
//			if (rs.next()) {
//				maxMapId = rs.getInt("MaxMapId") + 1;
//			}
//			Gps gps = MapTransferUtils.gps84_To_Gcj02(riskMapFloodUpdateVo.getAfter_pointY_2000().doubleValue(),
//					riskMapFloodUpdateVo.getAfter_pointX_2000().doubleValue());
//			double lat = stringToDouble(gps.getWgLat());
//			double lon = stringToDouble(gps.getWgLon());
//			sql = "insert into RISKMAP_FLOODADDRESS values (?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
//			stat = conn.prepareStatement(sql);
//			stat.setInt(1, maxMapId);
//			stat.setBigDecimal(2, riskMapFloodUpdateVo.getAfter_pointX_2000());
//			stat.setBigDecimal(3, riskMapFloodUpdateVo.getAfter_pointY_2000());
//			stat.setBigDecimal(4, riskMapFloodUpdateVo.getAfter_pointX_2000());
//			stat.setBigDecimal(5, riskMapFloodUpdateVo.getAfter_pointY_2000());
//			stat.setBigDecimal(6, BigDecimal.valueOf(lon));
//			stat.setBigDecimal(7, BigDecimal.valueOf(lat));
//			stat.setString(8, riskMapFloodUpdateVo.getFloodAddress());
//			stat.setInt(9, riskMapFloodUpdateVo.getFloodGrade());
//			stat.setBigDecimal(10, riskMapFloodUpdateVo.getFloodDepth());
//			stat.setString(11, riskMapFloodUpdateVo.getComCode());
//			stat.setString(12, riskMapFloodUpdateVo.getOperatorCode());
//			stat.setDate(13, new java.sql.Date(riskMapFloodUpdateVo.getFloodDate().getTime()));
//			stat.executeUpdate();
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("保存成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("保存失败");
//			log.info("保存异常：" + e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * 删除一条水淹数据
//	 * 
//	 * @param riskMapFloodUpdateVo
//	 */
//	public AjaxResult deleteFloodLonLat(String dangerId) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		Statement stat = null;
//		ResultSet rs = null;
//		// 根据传入的addressId查询数据库
//		try {
//			ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//			// String riskmapTyphoon = filePath.getString("riskMap_Typhoon");
//			String riskmapTyphoonName = filePath.getString("riskMap_Typhoon");
//			// 获取表名
//			String riskmapTyphoon = MapUtils.getDataSetTableName(riskmapTyphoonName);
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "delete from RISKMAP_FLOODADDRESS where dangerId = '" + dangerId + "'";
//			String sql1 = "delete from " + riskmapTyphoon + " where DANGERID = '" + dangerId + "'";
//			stat = conn.createStatement();
//			stat.addBatch(sql1);
//			stat.addBatch(sql);
//			stat.executeBatch();
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("删除成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("删除失败");
//			log.info("删除异常：" + e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//		return ajaxResult;
//	}
//
//	// 更新数据
//	public void updateDtvData() {
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskMap_Address = bundle.getString("riskMap_address");
//		String riskMap_AddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMap_Address = MapUtils.getDataSetTableName(riskMap_AddressName);
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select SMID,SMX,SMY from " + riskMap_Address
//					+ " where VALIDSTATUS ='1' and RAINSCALE is null";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//			for (int i = 1; rs.next(); i++) {
//				long startTime = System.currentTimeMillis();
//				String[] str = new String[3];
//				str[0] = rs.getString(1);
//				str[1] = rs.getString(2);
//				str[2] = rs.getString(3);
//				// 更新数据
//				System.out.println("执行的条数：" + i);
//				this.updateData(str, riskMap_Address);
//				long endTime = System.currentTimeMillis();
//				System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//
//		}
//
//	}
//
//	// 刷新表中的栅格风险值
//	public void updateData(String[] smArray, String riskMapAddress) {
//		// 获取iserver的地址
//		ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		String serverName = bundle.getString("serverName");
//
//		Connection conn = null;
//		PreparedStatement stat = null;
//		// ResultSet rs = null;
//		String[] rasterDangerArray = { "rain_hazard_scale_1km", "thunderstorm_hazard_scale_1km",
//				"snowstorm_hazard_scale_1km", "hail_hazard_scale_1km", "flood_hazard_scale_1km",
//				"typhoon_hazard_scale_1km", "landslide_hazard_scale_1km", "eq_hazard_PGA" };
//
//		String[] valueArray = new String[8];
//		for (int i = 0; i < rasterDangerArray.length; i++) {
//			String url = serverName + "iserver/services/data-FXDT/rest/data/datasources/china/datasets/"
//					+ rasterDangerArray[i] + "/gridValue.json";
//			String param = "x=" + smArray[1] + "&y=" + smArray[2];
//			String resJson = doGet(url, param);
//			// System.out.println("请求的url:"+url+"?"+param);
//			GridValue gridValue = new GridValue();
//			if (StringUtils.isNotBlank(resJson)) {
//				gridValue = JSON.parseObject(resJson.toString(), GridValue.class);
//				BigDecimal gridValueBd = gridValue.getValue();
//				// 假如值小于0，则置为0,保留两位小数
//				if (gridValueBd.compareTo(new BigDecimal(0)) == -1) {
//					gridValueBd = BigDecimal.ZERO;
//				}
//				valueArray[i] = gridValueBd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//			} else {
//				System.out.println("请求的url:" + url + "?" + param);
//				valueArray[i] = BigDecimal.ZERO.toString();
//			}
//		}
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "";
//			sql = "update " + riskMapAddress + " set RAINSCALE=" + valueArray[0] + ",THUNDERSTORMSCALE=" + valueArray[1]
//					+ ",SNOWSTORMSCALE=" + valueArray[2] + ",HAILSCALE=" + valueArray[3] + ",FLOODSCALE="
//					+ valueArray[4] + ",TYPHOONSCALE=" + valueArray[5] + ",LANDSLIDESCALE=" + valueArray[6] + ",EQPGA="
//					+ valueArray[7] + " where SMID=" + smArray[0];
//			stat = conn.prepareStatement(sql);
//			stat.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	public List<ExchRateVo> getExchRate() {
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		List<ExchRateVo> ExchRateVoList = new ArrayList<ExchRateVo>();
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select a.ADDRESSID,a.PROPOSALNO,a.STARTDATE,b.CURRENCY from RISKMAP_MAIN a, RISKMAP_ITEMKIND b where a.ADDRESSID = b.ADDRESSID and a.PROPOSALNO = b.PROPOSALNO and  b.CURRENCY !='CNY'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//			for (int i = 1; rs.next(); i++) {
//				ExchRateVo exchRateVo = new ExchRateVo();
//				exchRateVo.setAddressId(rs.getInt(1));
//				exchRateVo.setProposalNo(rs.getString(2));
//				exchRateVo.setStartDate(rs.getDate(3));
//				exchRateVo.setCurrency(rs.getString(4));
//				ExchRateVoList.add(exchRateVo);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ExchRateVoList;
//	}
//
//	@Override
//	public void saveExchRate(ExchRateVo exchRateVo, String proposalNo, Integer addressId) {
//		Connection conn = null;
//		PreparedStatement stat = null;
//		try {
//			// 获取汇率
//			PrpDexch prpDexch = null;
//			prpDexch = DictAPIService.getPrpDexch(RiskControlConst.SYSTEMCODE, exchRateVo.getStartDate(),
//					exchRateVo.getCurrency(), "CNY");
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "";
//			if (prpDexch != null) {
//				sql = "update RISKMAP_ITEMKIND set EXCHRATE =" + prpDexch.getExchRate() + " where PROPOSALNO='"
//						+ proposalNo + "' and ADDRESSID =" + addressId;
//			}
//			stat = conn.prepareStatement(sql);
//			stat.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//
//		}
//	}
//
//	/**
//	 * @功能:查询理赔信息
//	 * @author 马军亮
//	 * @param policyNo
//	 *            保单号
//	 * @param itemkindList
//	 *            条款责任
//	 * @return List<PrplClaimVo> @日期：2018-10-25
//	 */
//	@Override
//	public List<PrplClaimVo> queryPrpLclaim(String policyNo, List<RiskMapItemkind> itemkindList) {
//
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String itemkindNoSql = "";
//		for (RiskMapItemkind riskMapItemkind : itemkindList) {
//			Integer itemkindNo = riskMapItemkind.getId().getItemkindNo();
//			itemkindNoSql += itemkindNo + ",";
//		}
//		if (StringUtils.isNotBlank(itemkindNoSql)) {
//			itemkindNoSql = itemkindNoSql.substring(0, itemkindNoSql.length() - 1);
//		}
//		List<PrplClaimVo> prplClaimVoList = new ArrayList<PrplClaimVo>();
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select damageStartDate,damageName,DAMAGEADDRESS,CLAIMNO,startDate from PRPLCLAIM where policyNo = '"
//					+ policyNo + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				PrplClaimVo prplClaimVo = new PrplClaimVo();
//				prplClaimVo.setDamageStartDate(rs.getDate(1));
//				prplClaimVo.setDamageName(rs.getString(2));
//				prplClaimVo.setDamageAddress(rs.getString(3));
//				String claimNo = rs.getString(4);
//				prplClaimVo.setClaimNo(claimNo);
//				Date startDate = rs.getDate(5);
//				BigDecimal sumRealPay = this.getSumRealPay(claimNo, itemkindNoSql, policyNo);
//				String currency = this.getCurrency(claimNo, itemkindNoSql, policyNo);
//				if (StringUtils.isNoneBlank(currency)) {
//					String underWriteFlag = this.getUnderWriteFlag(claimNo, policyNo);
//					prplClaimVo.setUnderWriteFlag(underWriteFlag);
//					if ("CNY".equals(currency)) {
//						prplClaimVo.setSumRealPay(sumRealPay);
//					} else {
//						// 获取汇率
//						PrpDexch prpDexch = null;
//						prpDexch = DictAPIService.getPrpDexch(RiskControlConst.SYSTEMCODE, startDate, currency, "CNY");
//						if (prpDexch != null) {
//							prplClaimVo.setSumRealPay(sumRealPay.multiply(prpDexch.getExchRate()));
//						}
//
//					}
//					prplClaimVoList.add(prplClaimVo);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//
//		return prplClaimVoList;
//	}
//
//	/**
//	 * @功能:获取实赔金额
//	 * @author 马军亮
//	 * @param policyNo
//	 *            保单号
//	 * @param claimNo
//	 *            立案号
//	 * @param itemkindNoSql
//	 *            条款
//	 * @return SumRealPay 实赔金额 @日期：2018-10-25
//	 */
//	private BigDecimal getSumRealPay(String claimNo, String itemkindNoSql, String policyNo) {
//
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		BigDecimal sumRealPay = new BigDecimal(0);
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select sum(SumRealPay) from prplloss where POLICYNO = '" + policyNo + "' and ITEMKINDNO in ("
//					+ itemkindNoSql + ") and COMPENSATENO in (select COMPENSATENO from PRPLCOMPENSATE where claimno ='"
//					+ claimNo + "')";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				sumRealPay = rs.getBigDecimal(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return sumRealPay;
//	}
//
//	/**
//	 * @功能:获取币别
//	 * @author 马军亮
//	 * @param policyNo
//	 *            保单号
//	 * @param claimNo
//	 *            立案号
//	 * @param itemkindNoSql
//	 *            条款
//	 * @return Currency 币别 @日期：2018-10-25
//	 */
//	private String getCurrency(String claimNo, String itemkindNoSql, String policyNo) {
//
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String currency = "";
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select currency from prplloss where POLICYNO = '" + policyNo + "' and ITEMKINDNO in ("
//					+ itemkindNoSql + ") and COMPENSATENO in (select COMPENSATENO from PRPLCOMPENSATE where claimno ='"
//					+ claimNo + "')";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				currency = rs.getString(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return currency;
//	}
//
//	/**
//	 * @功能:获取核保标志位
//	 * @author 马军亮
//	 * @param policyNo
//	 *            保单号
//	 * @param claimNo
//	 *            立案号
//	 * @param itemkindNoSql
//	 *            条款
//	 * @return Currency 币别 @日期：2018-10-25
//	 */
//	private String getUnderWriteFlag(String claimNo, String policyNo) {
//
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String underWriteFlag = "";
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select underWriteFlag from PRPLCOMPENSATE where claimno ='" + claimNo + "' and POLICYNO = '"
//					+ policyNo + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				underWriteFlag = rs.getString(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return underWriteFlag;
//	}
//
//	// 导出地图excel数据
//	public AjaxResult downloadMapExcelData(String geometry) {
//
//		AjaxResult ajaxResult = new AjaxResult();
//
//		Workspace workspace = new Workspace();
//		// 定义数据源连接信息，假设以下所有数据源设置都存在
//		DatasourceConnectionInfo datasourceconnection = new DatasourceConnectionInfo();
//		datasourceconnection.setEngineType(EngineType.ORACLEPLUS);
//		datasourceconnection.setServer("10.10.68.248:1521/orcl");
//		// datasourceconnection.setDatabase("riskcontrol");
//		// datasourceconnection.setUser("riskcontrol"); // riskcontrol_freeze
//		// datasourceconnection.setPassword("riskcontrol");
//
//		datasourceconnection.setDatabase("riskcontrol_freeze");
//		datasourceconnection.setUser("riskcontrol_freeze"); // riskcontrol_freeze
//		datasourceconnection.setPassword("riskcontrol_freeze");
//		datasourceconnection.setAlias("ORACLE");
//
//		// 打开数据源
//		Datasource datasource = workspace.getDatasources().open(datasourceconnection);
//		// 获取的点数据集
//		DatasetVector datasetVector = (DatasetVector) datasource.getDatasets().get("RISKMAP_ADDRESS");
//
//		// DatasetVector dataset2 =
//		// (DatasetVector)datasource.getDatasets().get("RISKMAP_DISASTER2");
//
//		if (datasource == null) {
//			System.out.println("打开数据源失败");
//		} else {
//			System.out.println("数据源打开成功！");
//		}
//
//		// double average = dataset.statistic("POINTX_2000",StatisticMode.AVERAGE);
//		// System.out.println("POINTX_2000的平均值为：" + average);
//		// average = 131;
//		//
//		// QueryParameter queryParameter = new QueryParameter();
//		// queryParameter.setAttributeFilter("POINTX_2000 >"+average);
//		// queryParameter.setHasGeometry(true);
//
//		// Toolkit toolkit =new Toolkit();
//
//		Geometry geome = Toolkit.GeoJsonToGemetry(geometry);
//		GeoRegion geoRegion = (GeoRegion) geome;
//		// 设置查询参数
//		QueryParameter parameter = new QueryParameter();
//		parameter.setSpatialQueryObject(geoRegion);
//		parameter.setSpatialQueryMode(SpatialQueryMode.CONTAIN);
//		parameter.setAttributeFilter("POINTX_2000 >110");
//		// ,"POINTY_2000 desc"
//		parameter.setOrderBy(new String[] { "SmID asc" });
//		// parameter.setCursorType(CursorType.DYNAMIC);
//
//		Recordset queryRecordset = datasetVector.query(parameter);
//
//		// FieldInfos fieldInfos = queryRecordset.getFieldInfos();
//		// FieldInfo fieldInfo = fieldInfos.get(0);
//		// String name = fieldInfo.getName();
//		// String value = fieldInfo.getDefaultValue();
//		//
//		// Map<Integer,Feature> features= queryRecordset.getAllFeatures();
//		// for(Feature feature:features.values()){
//		// String valueString = feature.getString("POINTX_2000");
//		// System.out.println("POINTX_2000:"+valueString);
//		// }
//
//		HSSFWorkbook wkb = new HSSFWorkbook();
//		// 建立新的sheet对象（excel的表单）
//		HSSFSheet sheet = wkb.createSheet("统计表");
//		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//		HSSFRow row1 = sheet.createRow(0);
//		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//		HSSFCell cell = row1.createCell(0);
//		// 设置居中
//		HSSFCellStyle style = wkb.createCellStyle();
//		// 替换poi版本3.1.1为3.16，修改调用居中为HorizontalAlignment.CENTER add by wangwenjie
//		// 2019/7/22
//		// style.setAlignment(HorizontalAlignment.CENTER);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置字体
//		HSSFFont font = wkb.createFont();
//		// 设置字体大小
//		font.setFontHeightInPoints((short) 11);
//		// 字体加粗
//		// 替换poi版本3.1.1为3.16，修改调用加粗为font.setBold(true) add by wangwenjie 2019/7/22
//		font.setBold(true);
//		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置单元格内容
//		cell.setCellValue("地图统计表");
//		cell.setCellStyle(style);
//		// 替换poi版本3.1.1为3.16,修改CellRangeAddress 为org.apache.poi.ss.util.CellRangeAddress
//		// add by wangwenjie 2019/7/22
//		// 创建单元格并设置单元格内容
//		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
//		// 在sheet里创建第二行
//		HSSFRow row2 = sheet.createRow(2);
//
//		HSSFCell cell0 = row2.createCell(0);
//		cell0.setCellValue("地图ID");
//		sheet.setColumnWidth(cell0.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell1 = row2.createCell(1);
//		cell1.setCellValue("POINTX_2000");
//		sheet.setColumnWidth(cell1.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell2 = row2.createCell(2);
//		cell2.setCellValue("POINTX_2000");
//		sheet.setColumnWidth(cell2.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell3 = row2.createCell(3);
//		cell3.setCellValue("ADDRESSNAME");
//		sheet.setColumnWidth(cell3.getColumnIndex(), 256 * 60);
//
//		HSSFCell cell4 = row2.createCell(4);
//		row2.createCell(4).setCellValue("SCORE");
//		sheet.setColumnWidth(cell4.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell5 = row2.createCell(5);
//		row2.createCell(5).setCellValue("RAINSCALE");
//		sheet.setColumnWidth(cell5.getColumnIndex(), 256 * 20);
//
//		Map<Integer, Feature> features = queryRecordset.getAllFeatures();
//		System.out.println(queryRecordset.getRecordCount());
//		int i = 0;
//		for (Integer key : features.keySet()) {
//			HSSFRow row = sheet.createRow(i + 3);
//			System.out.println(features.get(key).getString("SMID") + ":" + features.get(key).getString("POINTX_2000"));
//			row.createCell(0).setCellValue(features.get(key).getString("SMID"));
//			row.createCell(1).setCellValue(features.get(key).getString("POINTX_2000"));
//			row.createCell(2).setCellValue(features.get(key).getString("POINTY_2000"));
//			row.createCell(3).setCellValue(features.get(key).getString("ADDRESSNAME"));
//			row.createCell(4).setCellValue(features.get(key).getString("SCORE"));
//			row.createCell(5).setCellValue(features.get(key).getString("RAINSCALE"));
//			i++;
//		}
//		// 释放工作空间资源
//		datasourceconnection.dispose();
//		workspace.dispose();
//		OutputStream output = null;
//		FTPUtil ftp = new FTPUtil();
//		// 存储到公共上传目录下
//		// 输出Excel文件
//		try {
//			ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
//
//			output = ftp.uploadFile("downloadExcel/mapData.xls");
//			ajaxResult.setData("/downloadExcel/mapData.xls");
//			wkb.write(output);
//			output.flush();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (output != null) {
//				try {
//					output.close();
//				} catch (Exception e2) {
//					log.error(e2.getMessage(), e2);
//				}
//
//			}
//			if (ftp != null) {
//				try {
//					ftp.close();
//				} catch (IOException e) {
//					log.info("关闭ftp异常：" + e.getMessage(), e);
//				}
//			}
//		}
//		return ajaxResult;
//
//	}
//
//	// 空间查询点面相交
//	public Map<String, String> searchAddress(String lon, String lat, String locaKindFlag) {
//		// AjaxResult ajaxResult =new AjaxResult();
//		Map<String, String> map = new HashMap<String, String>();
//		// ResourceBundle filePath = ResourceBundle.getBundle("config.map",
//		// Locale.getDefault());
//		double lonX = Double.parseDouble(lon);
//		double latY = Double.parseDouble(lat);
//		// double lonX= 79.724 ,latY = 37.385;
//		// 如果是02坐标系转为84坐标系
//		if ("02".equals(locaKindFlag)) {
//			Gps gps = MapTransferUtils.gcj_To_Gps84(latY, lonX);
//			latY = stringToDouble(gps.getWgLat());
//			lonX = stringToDouble(gps.getWgLon());
//		}
//		try {
//			System.out.println("======================begin==================================");
//			Workspace workspace = new Workspace();
//			System.out.println("======================after==================================");
//			// 组织成点数据集
//			GeoPoint geoPoint = new GeoPoint(lonX, latY);
//
//			// 定义数据源连接信息，假设以下所有数据源设置都存在
//
//			// 定义数据源连接信息，假设以下所有数据源设置都存在
//			DatasourceConnectionInfo datasourceconnection = new DatasourceConnectionInfo();
//			// 进行数据源的连接
//			Datasource datasource = MapUtils.connectDataSource(workspace, datasourceconnection);
//
//			System.out.println("Alias:" + datasource.getAlias());
//			System.out.println("count:" + datasource.getDatasets().getCount());
//			// for(int i=0;i<datasource.getDatasets().getCount();i++){
//			// System.out.println(datasource.getDatasets().get(i).getName());
//			// }
//			DatasetVector datasetVectorCountry = (DatasetVector) datasource.getDatasets().get("china_county");
//
//			QueryParameter parameter = new QueryParameter();
//			parameter.setSpatialQueryObject(geoPoint);
//			parameter.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
//
//			Recordset queryRecordset = datasetVectorCountry.query(parameter);
//
//			Map<Integer, Feature> features = queryRecordset.getAllFeatures();
//			System.out.println(queryRecordset.getRecordCount());
//			String adminCode = "", countryName = "", cityAdCode = "", proAdCode = "";
//			String cityName = "", provinceName = "";
//			if (datasource.getDatasets().getCount() > 0 && queryRecordset.getRecordCount() > 0) {
//				for (Feature feature : features.values()) {
//					countryName = feature.getString("Name");
//					adminCode = feature.getString("AdminCode");
//					cityAdCode = feature.getString("CityAdCode");
//					proAdCode = feature.getString("ProAdCode");
//					cityName = feature.getString("cityName");
//					provinceName = feature.getString("provinceName");
//					System.out.println("countryName:" + countryName + ":" + proAdCode + ":" + cityName + ":"
//							+ provinceName + ":" + adminCode + ":" + cityAdCode);
//				}
//
//				map.put("errorFlag", "1");
//				map.put("errorMessage", "恭喜您，查询成功！");
//
//			} else if (datasource.getDatasets().getCount() > 0) {
//				map.put("errorFlag", "0");
//				map.put("errorMessage", "此经纬度不在中国范围之内或没有数据，请重新选择！");
//			} else {
//				// 其他情况
//				map.put("errorFlag", "2");
//				map.put("errorMessage", "系统异常");
//			}
//			map.put("proAdCode", proAdCode);
//			map.put("provinceName", provinceName);
//			map.put("cityAdCode", cityAdCode);
//			map.put("cityName", cityName);
//			map.put("adminCode", adminCode);
//			map.put("countryName", countryName);
//
//			if (parameter != null) {
//				parameter.dispose();
//			}
//
//			if (queryRecordset != null) {
//				queryRecordset.dispose();
//			}
//
//			if (datasetVectorCountry != null) {
//				datasetVectorCountry.close();
//			}
//			if (geoPoint != null) {
//				geoPoint.dispose();
//			}
//			if (datasource != null) {
//				datasource.close();
//			}
//			// if(workspaceConnectionInfo!=null){
//			// workspaceConnectionInfo.dispose();
//			// }
//			if (datasourceconnection != null) {
//				datasourceconnection.dispose();
//			}
//			if (workspace != null) {
//				workspace.dispose();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			// 其他情况
//			map.put("errorFlag", "2");
//			map.put("errorMessage", "ObjectJava中缺少依赖,请更新依赖！");
//		}
//		return map;
//	}
//
//	public List<Province> findAllArea() {
//		List<Province> provinceList = new ArrayList<Province>();
//
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		String key = cacheManager.generateCacheKey("findAllArea");
//		Object object = cacheManager.getCache(key);
//		try {
//			Workspace workspace = new Workspace();
//			WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
//			Datasource datasource = new Datasource(EngineType.UDB);
//			if (object == null) {
//				workspaceConnectionInfo.setType(WorkspaceType.SMWU);
//				// String file = "C:/Users/Administrator/Desktop/map2.2/FXDT.smwu";
//				String file = filePath.getString("filePath");
//				workspaceConnectionInfo.setServer(file);
//				workspace.open(workspaceConnectionInfo);
//
//				datasource = workspace.getDatasources().get("areakind");
//
//				System.out.println("Alias:" + datasource.getAlias());
//				System.out.println("count:" + datasource.getDatasets().getCount());
//				// System.out.println("name:"+datasource.getDatasets().get(0).getName());
//
//				DatasetVector datasetVectorCity = (DatasetVector) datasource.getDatasets().get("china_city");
//
//				DatasetVector datasetVectorProvince = (DatasetVector) datasource.getDatasets().get("china_province");
//
//				QueryParameter provinceParameter = new QueryParameter();
//				provinceParameter.setAttributeFilter("1=1 ");
//				provinceParameter.setHasGeometry(true);
//
//				Recordset queryRecordProvince = datasetVectorProvince.query(provinceParameter);
//				Map<Integer, Feature> featuresprovince = queryRecordProvince.getAllFeatures();
//				System.out.println(queryRecordProvince.getRecordCount());
//
//				if (queryRecordProvince.getRecordCount() > 0) {
//					for (Feature feature : featuresprovince.values()) {
//						Province province = new Province();
//
//						String provinceName = feature.getString("Name");
//						String provinceCenterX = feature.getString("centerX");
//						String provinceCenterY = feature.getString("centerY");
//						String provinceAdminCode = feature.getString("AdminCode");
//						if ("110000,120000,310000,500000".indexOf(provinceAdminCode) > -1) {
//							// 直辖市标志位为0
//							province.setProvinceFlag("0");
//						} else {
//							// 非直辖市标志位为1
//							province.setProvinceFlag("1");
//						}
//						province.setProvinceName(provinceName);
//						province.setProvinceX(provinceCenterX);
//						province.setProvinceY(provinceCenterY);
//						System.out.println("cityName:" + provinceName);
//						if ("1".equals(province.getProvinceFlag())) {
//							QueryParameter cityParameter = new QueryParameter();
//							cityParameter.setAttributeFilter("ProAdCode=" + provinceAdminCode);
//							cityParameter.setHasGeometry(true);
//							// cityParameter.setResultFields(new String [] {
//							// "distinct(Name) as Name","distinct(centerX) as centerX","distinct(centerY) as
//							// centerY"
//							// });
//
//							Recordset queryRecordCity = datasetVectorCity.query(cityParameter);
//							Map<Integer, Feature> featuresCity = queryRecordCity.getAllFeatures();
//							System.out.println(queryRecordCity.getRecordCount());
//
//							List<City> cityList = new ArrayList<City>();
//							Set<City> citySet = new HashSet<City>();
//							if (queryRecordCity.getRecordCount() > 0) {
//								for (Feature featureCity : featuresCity.values()) {
//									City city = new City();
//									String cityName = featureCity.getString("Name");
//									String cityCenterX = featureCity.getString("centerX");
//									String cityCenterY = featureCity.getString("centerY");
//									city.setCityName(cityName);
//									city.setCityX(cityCenterX);
//									city.setCityY(cityCenterY);
//
//									citySet.add(city);
//									// System.out.println("cityName:"+cityName);
//								}
//							}
//							cityList.addAll(citySet);
//							province.setCityList(cityList);
//							// 释放资源
//							queryRecordCity.dispose();
//						}
//						provinceList.add(province);
//					}
//				}
//				if (queryRecordProvince != null) {
//					queryRecordProvince.dispose();
//				}
//				if (datasetVectorProvince != null) {
//					datasetVectorProvince.close();
//				}
//				if (datasetVectorCity != null) {
//					datasetVectorCity.close();
//				}
//				if (datasource != null) {
//					datasource.close();
//				}
//				if (workspaceConnectionInfo != null) {
//					workspaceConnectionInfo.dispose();
//				}
//				if (workspace != null) {
//					workspace.dispose();
//				}
//				// datasetVectorCity.close();
//				// datasource.close();
//				// workspaceConnectionInfo.dispose();
//				// workspace.dispose();
//
//				cacheManager.putCache(key, provinceList);
//			} else {
//				provinceList = (List<Province>) object;
//			}
//		} catch (Exception e) {
//			log.info("objectJava异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("objectJava异常:" + e);
//		}
//
//		return provinceList;
//	}
//
//	/**
//	 * 根据省市县来查询不同的信息 areaFlag: 1 省，2 市，3 县
//	 */
//	public Map<String, List<CurrentDistrict>> findKindArea(String areaName, String areaFlag) {
//		Map<String, List<CurrentDistrict>> map = new HashMap<String, List<CurrentDistrict>>();
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//
//		try {
//			Workspace workspace = new Workspace();
//			WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
//			Datasource datasource = new Datasource(EngineType.UDB);
//			workspaceConnectionInfo.setType(WorkspaceType.SMWU);
//			// String file = "F:/A_supermap/superMap_file/allmap/map/map/FXDT.smwu";
//			String file = filePath.getString("filePath");
//			workspaceConnectionInfo.setServer(file);
//			workspace.open(workspaceConnectionInfo);
//			datasource = workspace.getDatasources().get("areakind");
//
//			if (datasource != null) {
//				System.out.println("打开数据源成功");
//				System.out.println(datasource.getDatasets().get(0).getName());
//			} else {
//				System.out.println("打开数据源失败");
//			}
//
//			DatasetVector datasetVectorProvince = (DatasetVector) datasource.getDatasets().get("china_province");
//			DatasetVector datasetVectorCity = (DatasetVector) datasource.getDatasets().get("china_city");
//			DatasetVector datasetVectorCounty = (DatasetVector) datasource.getDatasets().get("china_county");
//
//			List<CurrentDistrict> currentDistrictList = new ArrayList<CurrentDistrict>();
//			if ("1".equals(areaFlag.trim())) {
//
//				QueryParameter parameter = new QueryParameter();
//				// 只有当 setHasGeometry(false) ，setCursorType(CursorType.STATIC) 时才有效，否则返回全部的字段
//				parameter.setResultFields(new String[] { "distinct AdminCode", "Name" });
//				parameter.setAttributeFilter("Name like '%" + areaName + "%'");
//				parameter.setHasGeometry(true);
//				parameter.setCursorType(CursorType.STATIC);
//
//				Recordset queryRecordProvince = datasetVectorProvince.query(parameter);
//				Map<Integer, Feature> featuresProvince = queryRecordProvince.getAllFeatures();
//				if (queryRecordProvince.getRecordCount() > 0) {
//					for (Feature feature : featuresProvince.values()) {
//						CurrentDistrict currentDistrict = new CurrentDistrict();
//						String provinceName = feature.getString("Name");
//						String adminCode = feature.getString("AdminCode");
//						System.out.println("cityName:" + provinceName + ":" + adminCode);
//						currentDistrict.setAdcode(adminCode);
//						currentDistrict.setName(provinceName);
//						currentDistrictList.add(currentDistrict);
//					}
//				}
//				map.put("addressInfo", currentDistrictList);
//
//			} else if ("2".equals(areaFlag.trim())) {
//
//				QueryParameter parameterCity = new QueryParameter();
//				// 只有当 setHasGeometry(false) ，setCursorType(CursorType.STATIC) 时才有效，否则返回全部的字段
//				parameterCity.setResultFields(new String[] { "distinct CityAdCode", "Name" });
//				parameterCity.setAttributeFilter("Name like '%" + areaName.trim() + "%'");
//				// parameterCity.setAttributeFilter("CityAdCode =460399");
//				// parameterCity.setHasGeometry(true);
//				parameterCity.setHasGeometry(false);
//				parameterCity.setCursorType(CursorType.STATIC);
//
//				Recordset queryRecordCity = datasetVectorCity.query(parameterCity);
//				Map<Integer, Feature> featuresCity = queryRecordCity.getAllFeatures();
//				System.out.println(queryRecordCity.getRecordCount());
//				if (queryRecordCity.getRecordCount() > 0) {
//					for (Feature feature : featuresCity.values()) {
//						CurrentDistrict currentDistrict = new CurrentDistrict();
//						String cityName = feature.getString("Name");
//						String cityAdCode = feature.getString("CityAdCode");
//						System.out.println("cityName:" + cityName + ":" + cityAdCode);
//						currentDistrict.setCitycode(cityAdCode);
//						currentDistrict.setName(cityName);
//						currentDistrictList.add(currentDistrict);
//					}
//				}
//				map.put("addressInfo", currentDistrictList);
//				queryRecordCity.dispose();
//
//			} else if ("3".equals(areaFlag.trim())) {
//
//				QueryParameter parameterCounty = new QueryParameter();
//				// 只有当 setHasGeometry(false) ，setCursorType(CursorType.STATIC) 时才有效，否则返回全部的字段
//				parameterCounty.setResultFields(new String[] { "distinct AdminCode", "Name" });
//				parameterCounty.setAttributeFilter("Name like '%" + areaName.trim() + "%'");
//				parameterCounty.setHasGeometry(false);
//				parameterCounty.setCursorType(CursorType.STATIC);
//
//				// parameterCounty.setOrderBy(new String[] {"SmSdriW asc"});
//				// parameterCounty.setGroupBy(new String[] {"AdminCode asc"});
//				// parameterCounty.setCursorType(CursorType.DYNAMIC);
//
//				Recordset queryRecordCounty = datasetVectorCounty.query(parameterCounty);
//				Map<Integer, Feature> featuresCounty = queryRecordCounty.getAllFeatures();
//				System.out.println(queryRecordCounty.getRecordCount());
//				if (queryRecordCounty.getRecordCount() > 0) {
//					for (Feature feature : featuresCounty.values()) {
//						CurrentDistrict currentDistrict = new CurrentDistrict();
//						String countyName = feature.getString("Name");
//						String adminCode = feature.getString("AdminCode");
//						System.out.println("countyName:" + countyName + ":" + adminCode);
//						currentDistrict.setCountyCode(adminCode);
//						currentDistrict.setName(countyName);
//						currentDistrictList.add(currentDistrict);
//					}
//				}
//				map.put("addressInfo", currentDistrictList);
//			}
//			if (datasetVectorCounty != null) {
//				datasetVectorCounty.close();
//			}
//			if (datasetVectorProvince != null) {
//				datasetVectorProvince.close();
//			}
//			if (datasetVectorCity != null) {
//				datasetVectorCity.close();
//			}
//
//			if (datasource != null) {
//				datasource.close();
//			}
//			if (workspaceConnectionInfo != null) {
//				workspaceConnectionInfo.dispose();
//			}
//			if (workspace != null) {
//				workspace.dispose();
//			}
//		} catch (Exception e) {
//			log.info("objectJava异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("objectJava异常:" + e);
//		}
//		return map;
//	}
//
//	/** 每日承保数据处理 */
//	public void handlePrpins() {
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select * from prpinslog where handleflag = '0' ";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				PreparedStatement statSuccess = null;
//				try {
//					AjaxResult ajaxResult = createData(rs.getString(2));
//					if (ajaxResult.getStatus() == 1) {
//						String sqlSuccess = "update prpinslog set handleflag = '1',handletime =? where id = ?";
//						statSuccess = conn.prepareStatement(sqlSuccess);
//						statSuccess.setDate(1, new java.sql.Date(System.currentTimeMillis()));
//						statSuccess.setInt(2, rs.getInt(1));
//						statSuccess.executeUpdate();
//					} else {
//						String sqlSuccess = "update prpinslog set handleflag = '2',handletime =?,errormessage = ? where id = ?";
//						statSuccess = conn.prepareStatement(sqlSuccess);
//						statSuccess.setDate(1, new java.sql.Date(System.currentTimeMillis()));
//						statSuccess.setString(2, ajaxResult.getStatusText());
//						statSuccess.setInt(3, rs.getInt(1));
//						statSuccess.executeUpdate();
//					}
//				} catch (Exception e) {
//					log.info("承保数据处理失败,单号为" + rs.getString(2), e);
//					e.printStackTrace();
//				} finally {
//					if (statSuccess != null) {
//						statSuccess.close();
//					}
//				}
//			}
//		} catch (SQLException e) {
//			log.info("承保数据处理失败:" + e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//	}
//
//	/**
//	 * @功能 获取HttpResponseAddress集合中的分值最高者
//	 * @author 梁尚
//	 * @param List<HttpResponseAddress>
//	 * @return HttpResponseAddress @日期：2019-2-14
//	 */
//	private HttpResponseAddress compareResList(List<HttpResponseAddress> list) {
//		HttpResponseAddress endResponse = new HttpResponseAddress();
//		for (int i = 0; i < list.size(); i++) {
//			HttpResponseAddress respFir = list.get(i);
//			if (respFir != null && respFir.getGeo() != null && respFir.getGeo().getScore() != null) {
//				for (int j = i + 1; j < list.size(); j++) {
//					HttpResponseAddress respSec = list.get(j);
//					if (respSec != null && respSec.getGeo() != null && respSec.getGeo().getScore() != null) {
//						if (respSec.getGeo().getScore().compareTo(respFir.getGeo().getScore()) > 0) {
//							endResponse = respSec;
//						} else {
//							endResponse = respFir;
//						}
//					}
//				}
//			}
//		}
//		return endResponse;
//	}
//
//	private HttpResponseAddressReset compareResList_New(List<HttpResponseAddressReset> list) {
//		HttpResponseAddressReset endResponse = new HttpResponseAddressReset();
//		for (int i = 0; i < list.size(); i++) {
//			HttpResponseAddressReset respFir = list.get(i);
//			if (respFir.getData() != null && respFir.getData().getRows() != null
//					&& respFir.getData().getRows()[0] != null && respFir.getData().getRows()[0].getScore() != null) {
//				for (int j = i + 1; j < list.size(); j++) {
//					HttpResponseAddressReset respSec = list.get(j);
//					if (respSec.getData() != null && respSec.getData().getRows() != null
//							&& respSec.getData().getRows()[0] != null
//							&& respSec.getData().getRows()[0].getScore() != null) {
//						if (new BigDecimal(respSec.getData().getRows()[0].getScore())
//								.compareTo(new BigDecimal(respFir.getData().getRows()[0].getScore())) > 0) {
//							endResponse = respSec;
//						} else {
//							endResponse = respFir;
//						}
//					}
//				}
//			}
//		}
//		return endResponse;
//	}
//
//	// 特殊字符过滤
//	public static String filteraddress(String result) {
//		String regEx = "[`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//		System.out.println("asdasd==" + Pattern.compile(regEx).matcher(result).replaceAll("").trim());
//		return Pattern.compile(regEx).matcher(result).replaceAll("").trim();
//	}
//
//	// 特殊字符截取
//	public static String subaddress(String result) {
//		String regEx = " [`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//		char[] specialSymbols = regEx.toCharArray();
//		for (int j = 0; j < specialSymbols.length; j++) {
//			if (result.indexOf(specialSymbols[j]) > -1) {
//				result = result.substring(0, result.indexOf(specialSymbols[j]));
//			}
//		}
//		return result.trim();
//	}
//
//	// 特殊字符截取
//	public static String busSubaddress(String result) {
//		result = subaddress(result);
//		String[] str = { "特别约定", "特约", "地址详见", "详见", "等详", "等见", "及其" };
//		for (int i = 0; i < str.length; i++) {
//			if (result.indexOf(str[i]) > -1) {
//				result = result.substring(0, result.indexOf(str[i]));
//			}
//		}
//		return result.trim();
//	}
//
//	// 特殊字符截取
//	public static String subpostname(String result) {
//		String[] str = { "省", "市", "自治区", "自治州", "自治县", "县" };
//		for (int j = 0; j < str.length; j++) {
//			if (result.indexOf(str[j]) > -1) {
//				result = result.substring(0, result.indexOf(str[j]));
//			}
//		}
//		return result.trim();
//	}
//
//	/** 每日承保数据main表处理 */
//	public AjaxResult saveDayPrpMain(String line, String dataType) {
//		AjaxResult ajaxResult = new AjaxResult();
//		if (StringUtils.isNotBlank(line)) {
//			try {
//				String[] str = line.split("\\^");
//				System.out.println(str);
//				PrpCmainTemp prpCmainTemp = new PrpCmainTemp();
//				prpCmainTemp.setDataType(dataType);
//				prpCmainTemp.setProposalNo(str[0]);
//				prpCmainTemp.setPolicyNo(str[1]);
//				prpCmainTemp.setClassCode(str[2]);
//				prpCmainTemp.setRiskCode(str[3]);
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//				prpCmainTemp.setStartDate(sdf.parse(str[12]));
//				prpCmainTemp.setStartHour(Integer.valueOf(str[13]));
//				prpCmainTemp.setEndDate(sdf.parse(str[14]));
//				prpCmainTemp.setEndHour(Integer.valueOf(str[15]));
//				BigDecimal sumAmount = new BigDecimal(str[21]);
//				prpCmainTemp.setSumAmount(sumAmount);
//				prpCmainTemp.setComCode(str[37]);
//				prpCmainTemp.setCoinsFlag(str[51]);
//				prpCmainTemp.setUnderWriteFlag(str[55]);
//				prpCmainTemp.setPolicySort(str[6]);
//				prpCmainTemp.setMakeCom(str[35]);
//				prpCmainTemp.setComCode(str[37]);
//				// for (int i=0;i<str.length;i++){
//				// System.out.println("***********第"+i+"列数据为:"+str[i]+"*********");
//				// }
//				databaseDao.save(PrpCmainTemp.class, prpCmainTemp);
//			} catch (Exception e) {
//				log.info("每日承保数据处理失败:" + e.getMessage(), e);
//				e.printStackTrace();
//			}
//		}
//		return ajaxResult;
//	}
//
//	/** 每日承保数据address表处理 */
//	public AjaxResult saveDayPrpAddress(String line, String dataType) {
//		AjaxResult ajaxResult = new AjaxResult();
//		if (StringUtils.isNotBlank(line)) {
//			try {
//				String[] str = line.split("\\^");
//				System.out.println(str);
//				PrpCaddressTemp prpCaddressTemp = new PrpCaddressTemp();
//				PrpCaddressTempId id = new PrpCaddressTempId();
//				id.setDataType(dataType);
//				id.setProposalNo(str[0]);
//				id.setAddressNo(Integer.valueOf(str[2]));
//				prpCaddressTemp.setId(id);
//				prpCaddressTemp.setAddressCode(str[4]);
//				prpCaddressTemp.setAddressName(str[5]);
//				prpCaddressTemp.setPostCode(str[3]);
//				prpCaddressTemp.setRiskCode(str[1]);
//				databaseDao.save(PrpCaddressTemp.class, prpCaddressTemp);
//			} catch (Exception e) {
//				log.info("每日承保数据处理失败:" + e.getMessage(), e);
//				e.printStackTrace();
//			}
//		}
//		return ajaxResult;
//	}
//
//	/** 每日承保数据coins表处理 */
//	public AjaxResult saveDayPrpCoins(String line, String dataType) {
//		AjaxResult ajaxResult = new AjaxResult();
//		if (StringUtils.isNotBlank(line)) {
//			try {
//				String[] str = line.split("\\^");
//				System.out.println(str);
//				PrpCcoinsTemp prpCcoinsTemp = new PrpCcoinsTemp();
//				PrpCcoinsTempId id = new PrpCcoinsTempId();
//				id.setDataType(dataType);
//				id.setProposalNo(str[0]);
//				id.setSerialNo(Integer.valueOf(str[1]));
//				id.setCurrency(str[10]);
//				prpCcoinsTemp.setId(id);
//				if (StringUtils.isNotBlank(str[9])) {
//					BigDecimal coinsRate = new BigDecimal(str[9]);
//					prpCcoinsTemp.setCoinsRate(coinsRate);
//				}
//				prpCcoinsTemp.setCoinsType(str[7]);
//				prpCcoinsTemp.setMainPolicyNo(str[3]);
//				prpCcoinsTemp.setCoinsName(str[6]);
//				databaseDao.save(PrpCcoinsTemp.class, prpCcoinsTemp);
//			} catch (Exception e) {
//				log.info("每日承保数据处理失败:" + e.getMessage(), e);
//				e.printStackTrace();
//			}
//		}
//		return ajaxResult;
//	}
//
//	/** 每日承保数据insured表处理 */
//	public AjaxResult saveDayPrpInsured(String line, String dataType) {
//		AjaxResult ajaxResult = new AjaxResult();
//		if (StringUtils.isNotBlank(line)) {
//			try {
//				String[] str = line.split("\\^");
//				System.out.println(str);
//				if ("Q,T,G".indexOf(str[1].substring(0, 1)) > -1) {
//					PrpCinsuredTemp prpCinsuredTemp = new PrpCinsuredTemp();
//					PrpCinsuredTempId id = new PrpCinsuredTempId();
//					id.setDataType(dataType);
//					id.setProposalNo(str[0]);
//					id.setSerialNo(Integer.valueOf(str[2]));
//					prpCinsuredTemp.setId(id);
//					prpCinsuredTemp.setIdentifyNumber(str[17]);
//					if (StringUtils.isNotBlank(str[16])) {
//						prpCinsuredTemp.setIdentifyType(str[16].trim());
//					}
//					prpCinsuredTemp.setInsuredCode(str[5]);
//					prpCinsuredTemp.setInsuredFlag(str[11]);
//					prpCinsuredTemp.setInsuredName(str[6]);
//					prpCinsuredTemp.setInsuredType(str[4]);
//					prpCinsuredTemp.setPhoneNumber(str[30]);
//					prpCinsuredTemp.setRiskCode(str[1]);
//					prpCinsuredTemp.setUnifiedSocialCreditCode(str[55]);
//					databaseDao.save(PrpCinsuredTemp.class, prpCinsuredTemp);
//				}
//			} catch (Exception e) {
//				log.info("每日承保数据处理失败:" + e.getMessage(), e);
//				e.printStackTrace();
//			}
//		}
//		return ajaxResult;
//	}
//
//	/** 每日承保数据itemkind表处理 */
//	public AjaxResult saveDayPrpItem(String line, String dataType) {
//		AjaxResult ajaxResult = new AjaxResult();
//		if (StringUtils.isNotBlank(line)) {
//			try {
//				String[] str = line.split("\\^");
//				System.out.println(str);
//				if ("Q,T,G".indexOf(str[1].substring(0, 1)) > -1) {
//					PrpCitemKindTemp prpCitemKindTemp = new PrpCitemKindTemp();
//					PrpCitemKindTempId id = new PrpCitemKindTempId();
//					id.setDataType(dataType);
//					id.setProposalNo(str[0]);
//					id.setItemKindNo(Integer.valueOf(str[2]));
//					prpCitemKindTemp.setId(id);
//					if (StringUtils.isNotBlank(str[22])) {
//						prpCitemKindTemp.setAddressNo(Integer.valueOf(str[22]));
//					}
//					if (StringUtils.isNotBlank(str[29])) {
//						BigDecimal amount = new BigDecimal(str[29]);
//						prpCitemKindTemp.setAmount(amount);
//					}
//					prpCitemKindTemp.setCalculateFlag(str[23]);
//					prpCitemKindTemp.setClauseCode(str[6]);
//					prpCitemKindTemp.setClauseName(str[7]);
//					prpCitemKindTemp.setCurrency(str[24]);
//					prpCitemKindTemp.setItemCode(str[11]);
//					prpCitemKindTemp.setItemDetailName(str[12]);
//					if (StringUtils.isNotBlank(str[10])) {
//						prpCitemKindTemp.setItemNo(Integer.valueOf(str[10]));
//					}
//					prpCitemKindTemp.setKindCode(str[8]);
//					prpCitemKindTemp.setKindName(str[9]);
//					prpCitemKindTemp.setRiskCode(str[1]);
//					prpCitemKindTemp.setProjectCode(str[5]);
//					databaseDao.save(PrpCitemKindTemp.class, prpCitemKindTemp);
//				}
//			} catch (Exception e) {
//				log.info("每日承保数据处理失败:" + e.getMessage(), e);
//				e.printStackTrace();
//			}
//		}
//		return ajaxResult;
//	}
//
//	/** 每日承保数据，当数据导入prpc表后将prpc_temp各表清空 */
//	public void deletePrpTemp() {
//		// 删除原数据
//		Connection conn = null;
//		PreparedStatement stats = null;
//		String[] tables = { "prpcmaintemp", "prpcaddresstemp", "prpccoinstemp", "prpcitemkindtemp", "prpcinsuredtemp" };
//		for (String table : tables) {
//			try {
//				String sql = "delete from " + table;
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				stats = conn.prepareStatement(sql);
//				stats.executeUpdate();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					if (stats != null) {
//						stats.close();
//					}
//					if (conn != null && !conn.isClosed()) {
//						conn.close();
//					}
//				} catch (Exception e) {
//					log.info("临时表删除失败:" + e.getMessage(), e);
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	public void everyDayDel(String sql) {
//		// 删除原数据
//		Connection conn = null;
//		PreparedStatement stats = null;
//		try {
//			// String sql = "delete from PRPCMAINTEMP where classcode not in ('01','03') or
//			// coinsflag not in ('00','01','10','11','20','21') or underwriteflag in
//			// ('1','3')";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stats = conn.prepareStatement(sql);
//			stats.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (stats != null) {
//					stats.close();
//				}
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	Set<String> set = new HashSet<String>();
//
//	public Set<String> everyDayProcess() {
//		String sql = "delete from PRPCMAINTEMP where classcode not in ('01','03') or coinsflag not in ('00','01','10','11','20','21') or underwriteflag not in ('1','3')";
//		everyDayDel(sql);
//		updatePrpCmain();
//		updatePrpCInsured();
//		updatePrpCItemKind();
//		updatePrpCaddress();
//		updatePrpCcoins();
//		// for (String str : set) {
//		// createData(str);
//		// }
//		return set;
//	}
//
//	public void updatePrpCmain() {
//		String sql = "delete from PRPCMAINTEMP t1  where exists(select 1 from PRPCMAINTEMP t2 where t1.proposalno =t2.proposalno and t1.DATATYPE != t2.DATATYPE and t1.LOADTIME<t2.LOADTIME and t1.DATATYPE='I')";
//		everyDayDel(sql);
//		String sqlD = "delete from prpcmain where proposalno in (select proposalno from  PRPCMAINTEMP where datatype = 'D')";
//		everyDayDel(sqlD);
//		QueryRule queryRule = QueryRule.getInstance();
//		// queryRule.addEqual("dataType", "I");
//		List<PrpCmainTemp> mainList = databaseDao.findAll(PrpCmainTemp.class, queryRule);
//		for (PrpCmainTemp prpCmainTemp : mainList) {
//			set.add(prpCmainTemp.getProposalNo());
//			if (prpCmainTemp.getDataType().equals("I")) {
//				PrpCmain prpCmain = new PrpCmain();
//				Datas.copySimpleObjectToTargetFromSource(prpCmain, prpCmainTemp);
//				databaseDao.save(PrpCmain.class, prpCmain);
//			}
//		}
//		System.out.println(">>updatePrpCmain:" + new Date());
//	}
//
//	public void updatePrpCInsured() {
//		String sql = "delete  from prpcinsuredtemp t1 where exists(select 1 from prpcinsuredtemp t2 where t1.proposalno =t2.proposalno and t1.SERIALNO = t2.SERIALNO and t1.DATATYPE != t2.DATATYPE and t1.LOADTIME<t2.LOADTIME and t1.DATATYPE='I')";
//		everyDayDel(sql);
//		String sqlD = "delete  from prpcinsured c where exists (select 1 from prpcinsuredtemp t where c.proposalno = t.proposalno and c.SERIALNO = t.SERIALNO and t.datatype = 'D')";
//		everyDayDel(sqlD);
//		QueryRule queryRule = QueryRule.getInstance();
//		// queryRule.addEqual("id.dataType", "I");
//		List<PrpCinsuredTemp> insuredList = databaseDao.findAll(PrpCinsuredTemp.class, queryRule);
//		for (PrpCinsuredTemp prpCinsuredTemp : insuredList) {
//			set.add(prpCinsuredTemp.getId().getProposalNo());
//			if (prpCinsuredTemp.getId().getDataType().equals("I")) {
//				PrpCinsured prpCinsured = new PrpCinsured();
//				Datas.copySimpleObjectToTargetFromSource(prpCinsured, prpCinsuredTemp);
//				PrpCinsuredId id = new PrpCinsuredId();
//				id.setProposalNo(prpCinsuredTemp.getId().getProposalNo());
//				id.setSerialNo(prpCinsuredTemp.getId().getSerialNo());
//				prpCinsured.setId(id);
//				databaseDao.save(PrpCinsured.class, prpCinsured);
//			}
//		}
//		System.out.println(">>updatePrpCInsured:" + new Date());
//	}
//
//	public void updatePrpCItemKind() {
//		String sql = "delete  from PRPCITEMKINDTEMP t1  where exists(select 1 from PRPCITEMKINDTEMP t2 where  t1.proposalno =t2.proposalno and t1.ITEMKINDNO = t2.ITEMKINDNO and t1.DATATYPE != t2.DATATYPE and t1.LOADTIME<t2.LOADTIME and t1.DATATYPE='I')";
//		everyDayDel(sql);
//		String sqlD = "delete  from PRPCITEMKIND c where exists (select 1 from PRPCITEMKINDTEMP t where  c.proposalno = t.proposalno and c.ITEMKINDNO = t.ITEMKINDNO and t.datatype = 'D')";
//		everyDayDel(sqlD);
//		QueryRule queryRule = QueryRule.getInstance();
//		// queryRule.addEqual("id.dataType", "I");
//		List<PrpCitemKindTemp> itemKindList = databaseDao.findAll(PrpCitemKindTemp.class, queryRule);
//		for (PrpCitemKindTemp prpCitemKindTemp : itemKindList) {
//			set.add(prpCitemKindTemp.getId().getProposalNo());
//			if (prpCitemKindTemp.getId().getDataType().equals("I")) {
//				PrpCitemKind prpCitemKind = new PrpCitemKind();
//				Datas.copySimpleObjectToTargetFromSource(prpCitemKind, prpCitemKindTemp);
//				PrpCitemKindId id = new PrpCitemKindId();
//				id.setItemKindNo(prpCitemKindTemp.getId().getItemKindNo());
//				id.setProposalNo(prpCitemKindTemp.getId().getProposalNo());
//				prpCitemKind.setId(id);
//				databaseDao.save(PrpCitemKind.class, prpCitemKind);
//			}
//		}
//		System.out.println(">>updatePrpCItemKind:" + new Date());
//	}
//
//	public void updatePrpCaddress() {
//		String sql = "delete  from PRPCADDRESSTEMP t1  where exists (select 1 from PRPCADDRESSTEMP t2 where t1.proposalno =t2.proposalno and t1.ADDRESSNO = t2.ADDRESSNO and t1.DATATYPE != t2.DATATYPE and t1.LOADTIME<t2.LOADTIME and t1.DATATYPE='I')";
//		everyDayDel(sql);
//		String sqlD = "delete  from PRPCADDRESS c where exists(select 1 from PRPCADDRESSTEMP t where c.proposalno = t.proposalno and c.ADDRESSNO = t.ADDRESSNO and t.datatype = 'D')";
//		everyDayDel(sqlD);
//		QueryRule queryRule = QueryRule.getInstance();
//		// queryRule.addEqual("id.dataType", "I");
//		List<PrpCaddressTemp> addressList = databaseDao.findAll(PrpCaddressTemp.class, queryRule);
//		for (PrpCaddressTemp prpCaddressTemp : addressList) {
//			set.add(prpCaddressTemp.getId().getProposalNo());
//			if (prpCaddressTemp.getId().getDataType().equals("I")) {
//				PrpCaddress prpCaddress = new PrpCaddress();
//				Datas.copySimpleObjectToTargetFromSource(prpCaddress, prpCaddressTemp);
//				PrpCaddressId id = new PrpCaddressId();
//				id.setAddressNo(prpCaddressTemp.getId().getAddressNo());
//				id.setProposalNo(prpCaddressTemp.getId().getProposalNo());
//				prpCaddress.setId(id);
//				databaseDao.save(PrpCaddress.class, prpCaddress);
//			}
//		}
//		System.out.println(">>updatePrpCaddress:" + new Date());
//	}
//
//	public void updatePrpCcoins() {
//		String sql = "delete  from PRPCCOINSTEMP t1  where exists (select 1 from PRPCCOINSTEMP t2 where  t1.proposalno =t2.proposalno and t1.SERIALNO = t2.SERIALNO and t1.CURRENCY = t2.CURRENCY and t1.DATATYPE != t2.DATATYPE and t1.LOADTIME<t2.LOADTIME and t1.DATATYPE='I')";
//		everyDayDel(sql);
//		String sqlD = "delete  from PRPCCOINS c where exists (select 1 from PRPCCOINSTEMP t where  c.proposalno = t.proposalno and c.SERIALNO = t.SERIALNO and c.CURRENCY = t.CURRENCY and t.datatype = 'D')";
//		everyDayDel(sqlD);
//		QueryRule queryRule = QueryRule.getInstance();
//		// queryRule.addEqual("id.dataType", "I");
//		List<PrpCcoinsTemp> coinsList = databaseDao.findAll(PrpCcoinsTemp.class, queryRule);
//		for (PrpCcoinsTemp prpCcoinsTemp : coinsList) {
//			set.add(prpCcoinsTemp.getId().getProposalNo());
//			if (prpCcoinsTemp.getId().getDataType().equals("I")) {
//				PrpCcoins prpCcoins = new PrpCcoins();
//				Datas.copySimpleObjectToTargetFromSource(prpCcoins, prpCcoinsTemp);
//				PrpCcoinsId id = new PrpCcoinsId();
//				id.setCurrency(prpCcoinsTemp.getId().getCurrency());
//				id.setProposalNo(prpCcoinsTemp.getId().getProposalNo());
//				id.setSerialNo(prpCcoinsTemp.getId().getSerialNo());
//				prpCcoins.setId(id);
//				databaseDao.save(PrpCcoins.class, prpCcoins);
//			}
//		}
//		System.out.println(">>updatePrpCcoins:" + new Date());
//	}
//
//	/**
//	 * @功能 获取地址分值
//	 * @author 梁尚
//	 * @param geoUrl
//	 * @param addressname
//	 * @param postName
//	 * @return HttpResponseAddress @日期：2019-2-14
//	 */
//	// public static HttpResponseAddress getScore(String geoUrl,String
//	// addressname,String postName){
//	// HttpResponseAddress resp =new HttpResponseAddress();
//	// GetMethod method = null;
//	// try {
//	// HttpClient client = new HttpClient(new HttpClientParams(),new
//	// SimpleHttpConnectionManager(true));
//	// method = new GetMethod(geoUrl +"?keywords="+URLEncoder.encode(addressname,
//	// "UTF-8")+"&city="+URLEncoder.encode(postName,
//	// "UTF-8")+"&search_type=for_simple");
//	// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new
//	// DefaultHttpMethodRetryHandler());
//	// client.executeMethod(method);
//	// if (method.getStatusCode() == HttpStatus.SC_OK) {
//	// String result =StreamUtils.copyToString(method.getResponseBodyAsStream(),
//	// Charset.forName("utf-8"));
//	// resp = JSON.parseObject(result,HttpResponseAddress.class);
//	// }
//	// } catch (IOException e) {
//	// e.printStackTrace();
//	// log.info("执行HTTP Get请求" + geoUrl + "时，发生异常！",e);
//	// resp = null;
//	// } finally {
//	// if(method!=null) {
//	// method.releaseConnection();
//	// }
//	// }
//	// return resp;
//	// }
//
//	public static HttpResponseAddressReset getScore(String url, String addressname, String postName) {
//		HttpResponseAddressReset res = new HttpResponseAddressReset();
//		GetMethod method = null;
//		try {
//			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
//			method = new GetMethod(url + "?token=917e1f8b849b4e26932bdf98a8634469&keywords="
//					+ URLEncoder.encode(addressname, "UTF-8") + "&city=" + URLEncoder.encode(postName, "UTF-8"));
//			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//			client.executeMethod(method);
//			if (method.getStatusCode() == HttpStatus.SC_OK) {
//				String result = StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName("utf-8"));
//				res = JSON.parseObject(result, HttpResponseAddressReset.class);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.info("执行HTTP Get请求" + url + "时，发生异常！", e);
//			res = null;
//		} finally {
//			if (method != null) {
//				method.releaseConnection();
//			}
//		}
//		return res;
//	}
//
//	@Override
//	public AjaxResult updateTyphoon(RiskMapTyphoonBlack vo, UserInfo userInfo) {
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskmapTyphoon = filePath.getString("riskMap_Typhoon");
//		String riskmapTyphoonName = filePath.getString("riskMap_Typhoon");
//		// 获取表名
//		String riskmapTyphoon = MapUtils.getDataSetTableName(riskmapTyphoonName);
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		Statement stats = null;
//		String startDate = "";
//		if (vo.getStartDate() != null) {
//			startDate = ", startDate= to_date('" + vo.getStartDate() + "','yyyy-mm-dd')";
//		}
//		String endDate = "";
//		if (vo.getEndDate() != null) {
//			endDate = ",endDate= to_date('" + vo.getEndDate() + "','yyyy-mm-dd')";
//		}
//		String closeDate = "";
//		if (vo.getCloseDate() != null) {
//			closeDate = ",closeDate=to_date('" + vo.getCloseDate() + "','yyyy-mm-dd')";
//		} else {
//			closeDate = ", closeDate = ''";
//		}
//		String estimateAmount = "";
//		if (vo.getEstimateAmount() != null) {
//			estimateAmount = "',estimateAmount = '" + vo.getEstimateAmount() + "'";
//		} else {
//			estimateAmount = "', estimateAmount = ''";
//		}
//		String dangerDate = "";
//		if (vo.getDangerDate() != null) {
//			dangerDate = ",dangerDate= to_date('" + vo.getDangerDate() + "','yyyy-mm-dd')";
//		}
//		String sql = "update riskmap_typhoonblack set policyNo= '" + vo.getPolicyNo() + "', insuredName= '"
//				+ vo.getInsuredName() + "', branch = '" + vo.getBranch() + "', riskCode='" + vo.getRiskCode()
//				+ "', INSUERDADDRESS='" + vo.getInsuerdAddress() + "', insuredAmount='" + vo.getInsuredAmount()
//				+ "', premiumsAmount='" + vo.getPremiumsAmount() + "', Percentage='" + vo.getPercentage()
//				+ "', guarantee='" + vo.getGuarantee() + "', caseNo='" + vo.getCaseNo() + "', dangerBecause='"
//				+ vo.getDangerBecause() + "', dangerCode='" + vo.getDangerCode() + estimateAmount + ", closeFlag='"
//				+ vo.getCloseFlag() + "', operatorCode='" + userInfo.getUserCode() + "'" + closeDate + dangerDate
//				+ startDate + endDate + " where dangerId= '" + vo.getDangerId() + "'";
//		String sql1 = "update " + riskmapTyphoon + " set SMX = '" + vo.getPointx_2000() + "', SMY='"
//				+ vo.getPointy_2000() + "', pointx_2000 = '" + vo.getPointx_2000() + "', pointy_2000='"
//				+ vo.getPointy_2000() + "', pointx_02 = '" + vo.getPointx_02() + "', pointy_02= '" + vo.getPointy_02()
//				+ "', dangeraddress = '" + vo.getDangerAddress() + "', score = '" + vo.getScore()
//				+ "', dangerFlag = 'A' where dangerId= '" + vo.getDangerId() + "'";
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stats = conn.createStatement();
//			stats.addBatch(sql);
//			stats.addBatch(sql1);
//			stats.executeBatch();
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("保存成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("保存失败");
//			e.printStackTrace();
//		} finally {
//			try {
//				if (stats != null) {
//					stats.close();
//				}
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public AjaxResult deleteTyphoon(String deleteId) {
//		AjaxResult ajaxResult = new AjaxResult();
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskmapTyphoon = filePath.getString("riskMap_Typhoon");
//		String riskmapTyphoonName = filePath.getString("riskMap_Typhoon");
//		// 获取表名
//		String riskmapTyphoon = MapUtils.getDataSetTableName(riskmapTyphoonName);
//		Connection conn = null;
//		Statement stat = null;
//		ResultSet rs = null;
//		// 根据传入的addressId查询数据库
//		try {
//			// 查询主表riskmap_typhoonBlack中最大的id
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "delete from riskmap_typhoonBlack where DANGERID = '" + deleteId + "'";
//			String sql1 = "delete from " + riskmapTyphoon + " where DANGERID = '" + deleteId + "'";
//			stat = conn.createStatement();
//			stat.addBatch(sql1);
//			stat.addBatch(sql);
//			stat.executeBatch();
//			ajaxResult.setStatus(1);
//			ajaxResult.setData("删除成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setData("删除失败");
//			log.info("删除异常：" + e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public void saveRiskMapTyphoonBlack(UserInfo userInfo, List<RiskMapTyphoonBlack> riskMapTyphoonBlackList) {
//		List<RiskMapTyphoonBlack> RiskMapTyphoonBlackLs = new ArrayList<RiskMapTyphoonBlack>();
//		// List<RiskMapDanger> riskMapDangerList = new ArrayList<RiskMapDanger>();
//		List<Object[]> insertList = new ArrayList<>();
//		String INSERT_TFMAIN = this.getTfmainSql();
//		int i = 0;
//		if (riskMapTyphoonBlackList != null && riskMapTyphoonBlackList.size() > 0) {
//			for (RiskMapTyphoonBlack riskMapTyphoonBlack : riskMapTyphoonBlackList) {
//				i++;
//				// RiskMapDanger riskMapDanger = new RiskMapDanger();
//				String dangerId = UUID.randomUUID().toString();
//				riskMapTyphoonBlack.setDangerId(dangerId);
//				riskMapTyphoonBlack.setPolicyNo(riskMapTyphoonBlack.getPolicyNo());
//				riskMapTyphoonBlack.setInsuredName(riskMapTyphoonBlack.getInsuredName());
//				riskMapTyphoonBlack.setBranch(riskMapTyphoonBlack.getBranch());
//				riskMapTyphoonBlack.setRiskCode(riskMapTyphoonBlack.getRiskCode());
//				riskMapTyphoonBlack.setInsuredAmount(riskMapTyphoonBlack.getInsuredAmount());
//				riskMapTyphoonBlack.setPremiumsAmount(riskMapTyphoonBlack.getPremiumsAmount());
//				riskMapTyphoonBlack.setStartDate(riskMapTyphoonBlack.getStartDate());
//				riskMapTyphoonBlack.setEndDate(riskMapTyphoonBlack.getEndDate());
//				riskMapTyphoonBlack.setPercentage(riskMapTyphoonBlack.getPercentage());
//				riskMapTyphoonBlack.setGuarantee(riskMapTyphoonBlack.getGuarantee());
//				riskMapTyphoonBlack.setCaseNo(riskMapTyphoonBlack.getCaseNo());
//				riskMapTyphoonBlack.setDangerDate(riskMapTyphoonBlack.getDangerDate());
//				riskMapTyphoonBlack.setDangerBecause(riskMapTyphoonBlack.getDangerBecause());
//				if ("null".equals(riskMapTyphoonBlack.getDangerCode())) {
//					riskMapTyphoonBlack.setDangerCode("");
//				} else {
//					riskMapTyphoonBlack.setDangerCode(riskMapTyphoonBlack.getDangerCode());
//				}
//				riskMapTyphoonBlack.setCloseFlag(riskMapTyphoonBlack.getCloseFlag());
//				if ("null".equals(riskMapTyphoonBlack.getCloseDate())) {
//					riskMapTyphoonBlack.setCloseDate(null);
//				} else {
//					riskMapTyphoonBlack.setCloseDate(riskMapTyphoonBlack.getCloseDate());
//				}
//				Gps gps = MapTransferUtils.gps84_To_Gcj02(riskMapTyphoonBlack.getPointy_2000().doubleValue(),
//						riskMapTyphoonBlack.getPointx_2000().doubleValue());
//				double lat = stringToDouble(gps.getWgLat());
//				double lon = stringToDouble(gps.getWgLon());
//				insertList.add(new Object[] { this.getMaxValue() + i, riskMapTyphoonBlack.getPointx_2000(),
//						riskMapTyphoonBlack.getPointy_2000(), dangerId, riskMapTyphoonBlack.getPointx_2000(),
//						riskMapTyphoonBlack.getPointy_2000(), lon, lat, riskMapTyphoonBlack.getDangerAddress(), "1",
//						"1", "A" });
//				riskMapTyphoonBlack.setOperatorCode(userInfo.getUserCode());
//				riskMapTyphoonBlack.setRiskModel(riskMapTyphoonBlack.getRiskModel());
//				RiskMapTyphoonBlackLs.add(riskMapTyphoonBlack);
//			}
//			this.slaveJdbcTemplate.batchUpdate(INSERT_TFMAIN, insertList);
//			databaseDao.saveAll(RiskMapTyphoonBlack.class, RiskMapTyphoonBlackLs);
//		}
//	}
//
//	@Override
//	public AjaxResult findProvince() {
//		AjaxResult ajaxResult = new AjaxResult();
//		List<ProvinceVo> provinceList = new ArrayList<ProvinceVo>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//
//		// String riskMap_county = bundle.getString("riskMap_county");
//		String riskMap_countyName = bundle.getString("riskMap_county");
//		// 获取表名
//		String riskMap_county = MapUtils.getDataSetTableName(riskMap_countyName);
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select distinct proadcode, provinceName from " + riskMap_county;
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				ProvinceVo vo = new ProvinceVo();
//				vo.setProAdCode(rs.getString(1));
//				vo.setProvinceName(rs.getString(2));
//				provinceList.add(vo);
//			}
//			ajaxResult.setData(provinceList);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public AjaxResult queryCity(String upperCode) {
//
//		AjaxResult ajaxResult = new AjaxResult();
//		List<CityVo> provinceList = new ArrayList<CityVo>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		// String riskMap_county = bundle.getString("riskMap_county");
//		String riskMap_countyName = bundle.getString("riskMap_county");
//		// 获取表名
//		String riskMap_county = MapUtils.getDataSetTableName(riskMap_countyName);
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select distinct cityAdCode,cityName from " + riskMap_county + " where proAdcode = '"
//					+ upperCode + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				CityVo vo = new CityVo();
//				vo.setCityAdCode(rs.getString(1));
//				vo.setCityName(rs.getString(2));
//				provinceList.add(vo);
//			}
//			ajaxResult.setData(provinceList);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * @功能：标的信息导出
//	 * @return AjaxResult
//	 * @author liqiankun @时间：2019-03-15
//	 */
//	public AjaxResult exportStandard(RiskMapMainVo riskMapMainVo) {
//
//		AjaxResult ajaxResult = new AjaxResult();
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//
//		Workspace workspace = new Workspace();
//
//		// 定义数据源连接信息，假设以下所有数据源设置都存在
//		// String riskMap_address = filePath.getString("riskMap_address");
//		String riskMap_AddressName = bundle.getString("riskMap_address");
//		// 获取表名
//		String riskMap_address = MapUtils.getDataSetTableName(riskMap_AddressName);
//		// 定义数据源连接信息，假设以下所有数据源设置都存在
//		DatasourceConnectionInfo datasourceconnection = new DatasourceConnectionInfo();
//		// 进行数据源的连接
//		Datasource datasource = MapUtils.connectDataSource(workspace, datasourceconnection);
//		// 获取的点数据集
//		DatasetVector datasetVector = (DatasetVector) datasource.getDatasets().get("RISKMAP_ADDRESS");
//
//		Geometry geome = Toolkit.GeoJsonToGemetry(riskMapMainVo.getGeometry());
//		GeoRegion geoRegion = (GeoRegion) geome;
//		// 设置查询参数
//		QueryParameter parameter = new QueryParameter();
//		parameter.setSpatialQueryObject(geoRegion);
//		parameter.setSpatialQueryMode(SpatialQueryMode.CONTAIN);
//		// parameter.setAttributeFilter("POINTX_2000 >110");
//		// ,"POINTY_2000 desc"
//		// parameter.setOrderBy(new String[] {"SmID asc"});
//		// parameter.setCursorType(CursorType.DYNAMIC);
//
//		Recordset queryRecordset = datasetVector.query(parameter);
//		Map<Integer, Feature> features = queryRecordset.getAllFeatures();
//		System.out.println(queryRecordset.getRecordCount());
//
//		List<Integer> smidList = new ArrayList<Integer>(1000);
//		for (Integer key : features.keySet()) {
//			smidList.add(Integer.parseInt(features.get(key).getString("SMID")));
//		}
//		riskMapMainVo.setIds(smidList);
//
//		if (parameter != null) {
//			parameter.dispose();
//		}
//		if (geoRegion != null) {
//			geoRegion.dispose();
//		}
//		if (geome != null) {
//			geome.dispose();
//		}
//		if (queryRecordset != null) {
//			queryRecordset.close();
//			queryRecordset.dispose();
//		}
//		if (datasetVector != null) {
//			datasetVector.close();
//		}
//		if (datasource != null) {
//			datasource.close();
//		}
//		if (datasourceconnection != null) {
//			datasourceconnection.dispose();
//		}
//		if (workspace != null) {
//			// 关闭工作空间
//			workspace.close();
//			// 释放该对象所占用的资源
//			workspace.dispose();
//		}
//		// 查询出表中数据
//		List<String[]> riskMapMainVoList = this.exportData(riskMapMainVo, riskMap_address);
//		// 导出excel表格
//		ajaxResult = this.exportExcel(riskMapMainVoList);
//
//		return ajaxResult;
//	}
//
//	/**
//	 * @功能：查询出表中数据
//	 * @return AjaxResult
//	 * @author liqiankun @时间：2019-03-15
//	 */
//	public List<String[]> exportData(RiskMapMainVo riskMapMainVo, String riskMap_address) {
//
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String nowdate = riskMapMainVo.getNowdate();
//		String sumAmount = riskMapMainVo.getSumAmount();
//		String MapCode = riskMapMainVo.getMapCode();
//		String policyno = riskMapMainVo.getPolicyno();
//		String powerSQL = riskMapMainVo.getPowerSQL();
//		String sumPay = riskMapMainVo.getSumPay();
//		List<Integer> ids = riskMapMainVo.getIds();
//		System.out.println("id个数：" + ids.size());
//		List<String[]> countListC = new ArrayList<String[]>();
//		int num = 0;
//		String addressSql = "";
//		String addressArray = "";
//		if (ids != null && ids.size() > 1000) {
//			addressSql = "and ( riskmap_main.ADDRESSID in "
//					+ Arrays.toString(ids.subList(0, 1000).toArray()).replace("[", "(").replace("]", ")");
//			if (ids.size() % 1000 == 0) {
//				num = ids.size() / 1000;
//			} else {
//				num = ids.size() / 1000 + 1;
//			}
//			for (int i = 2; i <= num; i++) {
//				if (i < num) {
//					addressArray = addressArray + " or riskmap_main.ADDRESSID in "
//							+ Arrays.toString(ids.subList((i - 1) * 1000, 1000 * i).toArray()).replace("[", "(")
//									.replace("]", ")");
//				} else if (i == num) {
//					addressArray = addressArray + " or riskmap_main.ADDRESSID in "
//							+ Arrays.toString(ids.subList((i - 1) * 1000, ids.size()).toArray()).replace("[", "(")
//									.replace("]", ")");
//				}
//			}
//		} else {
//			addressSql = " and ( riskmap_main.ADDRESSID in "
//					+ Arrays.toString(ids.toArray()).replace("[", "(").replace("]", ")");
//		}
//		addressSql = addressSql + addressArray + ")";
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select smdtv.Pointx_2000,smdtv.Pointy_2000,smdtv.POINTX_02,smdtv.POINTY_02,smdtv.ADDRESSNAME,riskmap_main.PROPOSALNO,riskmap_main.POLICYNO,riskmap_main.COMCODE,riskmap_main.RISKCODE,insured.INSUREDNAME,insured.INSUREDCODE,insured.INSUREDTYPE  from riskmap_main,"
//					+ riskMap_address + " smdtv ,RISKMAP_INSURED insured "
//					+ " where smdtv.SMID = riskmap_main.ADDRESSID and riskmap_main.ADDRESSID=insured.ADDRESSID and riskmap_main.PROPOSALNO=insured.PROPOSALNO and "
//					+ nowdate + sumAmount + MapCode + policyno + " riskmap_main.sumamount <>0" + powerSQL + sumPay
//					+ addressSql;
//			// " and riskmap_main.ADDRESSID in "+Arrays.toString(ids.toArray()).replace("[",
//			// "(").replace("]", ")");
//			// " group by riskmap_main.addressid";
//			// String sql="select addressid,sum(sumamount),count(SUMAMOUNT) addressName,
//			// sum(nvl(RiskMap_Main.SUMREALPAY,0)),sum(nvl(RiskMap_Main.SUMUNSOLVEDPAY,0))
//			// from riskmap_main " +
//			// "where"+nowdate + sumAmount+MapCode+policyno+" riskmap_main.sumamount
//			// <>0"+powerSQL+ sumPay+
//			// " and ADDRESSID in "+Arrays.toString(ids.toArray()).replace("[",
//			// "(").replace("]", ")")+
//			// " group by addressid";
//			System.out.println("======sql=====" + sql);
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//
//			for (int i = 1; rs.next(); i++) {
//				String[] str = new String[12];
//				str[0] = rs.getString(1);
//				str[1] = rs.getString(2);
//				str[2] = rs.getString(3);
//				str[3] = rs.getString(4);
//				str[4] = rs.getString(5);
//				str[5] = rs.getString(6);
//				str[6] = rs.getString(7);
//				str[7] = rs.getString(8);
//				str[8] = rs.getString(9);
//				str[9] = rs.getString(10);
//				str[10] = rs.getString(11);
//				str[11] = rs.getString(12);
//				countListC.add(str);
//				if (i >= 2000) {
//					break;
//				}
//			}
//			System.out.println("查询出来的条数是：" + countListC.size());
//			// List list=countListC;
//			// riskMapMainVoList =list;
//		} catch (SQLException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return countListC;
//	}
//
//	/**
//	 * @功能：导出excel表格
//	 * @return AjaxResult
//	 * @author liqiankun @时间：2019-03-15
//	 */
//	public AjaxResult exportExcel(List<String[]> mapMainVoList) {
//		AjaxResult ajaxResult = new AjaxResult();
//
//		HSSFWorkbook wkb = new HSSFWorkbook();
//		// 建立新的sheet对象（excel的表单）
//		HSSFSheet sheet = wkb.createSheet("统计表");
//		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//		HSSFRow row1 = sheet.createRow(0);
//		// 设置行高
//		row1.setHeightInPoints(25);
//		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//		HSSFCell cell = row1.createCell(0);
//		// 设置居中
//		HSSFCellStyle style = wkb.createCellStyle();
//		// 替换poi版本3.1.1为3.16，修改调用居中为HorizontalAlignment.CENTER add by wangwenjie
//		// 2019/7/22
//		// style.setAlignment(HorizontalAlignment.CENTER);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置字体
//		HSSFFont font = wkb.createFont();
//		// 设置字体大小
//		font.setFontHeightInPoints((short) 11);
//		// 替换poi版本3.1.1为3.16，修改调用加粗为font.setBold(true) add by wangwenjie 2019/7/22
//		// 字体加粗
//		font.setBold(true);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置单元格内容
//		cell.setCellValue("地图标的导出信息统计表");
//		cell.setCellStyle(style);
//		// 创建单元格并设置单元格内容
//		// 设置导出模板的字体颜色
//		HSSFCellStyle styleColor = wkb.createCellStyle();
//		// 设置字体
//		HSSFFont fontColor = wkb.createFont();
//		// 替换poi版本3.1.1为3.16，Font.COLOR_RED add by wangwenjie 2019/7/22
//		fontColor.setColor(Font.COLOR_RED);
//		// fontColor.setColor(HSSFColor.RED.index);
//		styleColor.setFont(fontColor);
//
//		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
//		// 设置第二行
//		HSSFRow row12 = sheet.createRow(1);
//		// 设置行高
//		row12.setHeightInPoints(25);
//		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//		HSSFCell cell12 = row12.createCell(0);
//		cell12.setCellValue("温馨提示：1、红色标题为必录项 2.被保险人类型取值为个人、团体");
//		cell12.setCellStyle(styleColor);
//		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));
//
//		// 在sheet里创建第三行
//		HSSFRow row2 = sheet.createRow(2);
//
//		HSSFCell cell0 = row2.createCell(0);
//		cell0.setCellValue("地球坐标系经度");
//		cell0.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell0.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell1 = row2.createCell(1);
//		cell1.setCellValue("地球坐标系纬度");
//		cell1.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell1.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell2 = row2.createCell(2);
//		cell2.setCellValue("火星坐标系经度");
//		cell2.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell2.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell3 = row2.createCell(3);
//		cell3.setCellValue("火星坐标系纬度");
//		cell3.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell3.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell4 = row2.createCell(4);
//		row2.createCell(4).setCellValue("保险财产地址");
//		sheet.setColumnWidth(cell4.getColumnIndex(), 256 * 60);
//
//		HSSFCell cell5 = row2.createCell(5);
//		// row2.createCell(5).setCellValue("投保单号");
//		cell5.setCellValue("投保单号");
//		cell5.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell5.getColumnIndex(), 256 * 40);
//
//		HSSFCell cell6 = row2.createCell(6);
//		cell6.setCellValue("保单号");
//		cell6.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell6.getColumnIndex(), 256 * 40);
//
//		HSSFCell cell7 = row2.createCell(7);
//		cell7.setCellValue("归属机构");
//		cell7.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell7.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell8 = row2.createCell(8);
//		cell8.setCellValue("产品代码");
//		cell8.setCellStyle(styleColor);
//		sheet.setColumnWidth(cell8.getColumnIndex(), 256 * 20);
//
//		HSSFCell cell9 = row2.createCell(9);
//		row2.createCell(9).setCellValue("被保险人姓名/企业名称");
//		sheet.setColumnWidth(cell9.getColumnIndex(), 256 * 60);
//
//		HSSFCell cell10 = row2.createCell(10);
//		row2.createCell(10).setCellValue("被保险人代码");
//		sheet.setColumnWidth(cell10.getColumnIndex(), 256 * 30);
//
//		HSSFCell cell11 = row2.createCell(11);
//		row2.createCell(11).setCellValue("被保险人类型");
//		sheet.setColumnWidth(cell11.getColumnIndex(), 256 * 20);
//
//		int j = 0;
//		for (int i = 0; i < mapMainVoList.size(); i++) {
//			// System.out.println(mapMainVoList.get(i)[0]+":"+mapMainVoList.get(i)[1]);
//			HSSFRow row = sheet.createRow(j + 3);
//			row.createCell(0).setCellValue(mapMainVoList.get(i)[0]);
//			row.createCell(1).setCellValue(mapMainVoList.get(i)[1]);
//			row.createCell(2).setCellValue(mapMainVoList.get(i)[2]);
//			row.createCell(3).setCellValue(mapMainVoList.get(i)[3]);
//			row.createCell(4).setCellValue(mapMainVoList.get(i)[4]);
//			row.createCell(5).setCellValue(mapMainVoList.get(i)[5]);
//			row.createCell(6).setCellValue(mapMainVoList.get(i)[6]);
//			row.createCell(7).setCellValue(mapMainVoList.get(i)[7]);
//			row.createCell(8).setCellValue(mapMainVoList.get(i)[8]);
//			row.createCell(9).setCellValue(mapMainVoList.get(i)[9]);
//			row.createCell(10).setCellValue(mapMainVoList.get(i)[10]);
//			String insuredName = "";
//			if ("1".equals(mapMainVoList.get(i)[11])) {
//				insuredName = "个人";
//			} else if ("2".equals(mapMainVoList.get(i)[11])) {
//				insuredName = "团体";
//			}
//			row.createCell(11).setCellValue(insuredName);
//			j++;
//		}
//		// ResourceBundle bundle =
//		// ResourceBundle.getBundle("config.savePath",Locale.getDefault());
//		// String dir =
//		// bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
//		// StringBuffer stringBuf = new StringBuffer();
//		// stringBuf.append(dir).append("/").append("downloadExcel");
//		// File dirPath = new File(stringBuf.toString());
//		// if(!dirPath.exists()) {
//		// dirPath.mkdirs();
//		// }
//		// File outFile = new
//		// File(stringBuf.append("/").append("mapDataStandard").append(".xls").toString());
//		// //输出Excel文件
//		// try {
//		// FileOutputStream output=new FileOutputStream(outFile);
//		// ajaxResult.setData(stringBuf.toString());
//		// ajaxResult.setStatus(1);
//		// wkb.write(output);
//		// output.flush();
//		// } catch (Exception e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//
//		OutputStream output = null;
//		FTPUtil ftp = new FTPUtil();
//		// 存储到公共上传目录下
//		// 输出Excel文件
//		try {
//			// ResourceBundle bundle =
//			// ResourceBundle.getBundle("config.savePath",Locale.getDefault());
//			output = ftp.uploadFile("downloadExcel/mapDataStandard.xls");
//			ajaxResult.setData("/downloadExcel/mapDataStandard.xls");
//			wkb.write(output);
//			output.flush();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (output != null) {
//				try {
//					output.close();
//				} catch (Exception e2) {
//					log.error(e2.getMessage(), e2);
//				}
//
//			}
//			if (ftp != null) {
//				try {
//					ftp.close();
//				} catch (IOException e) {
//					log.info("关闭ftp异常：" + e.getMessage(), e);
//				}
//			}
//		}
//
//		return ajaxResult;
//	}
//
//	@Override
//	public AjaxResult queryDataone() {
//		AjaxResult ajaxResult = new AjaxResult();
//		String url = "http://10.10.1.2/service/coder/geocoding/score2?token=917e1f8b849b4e26932bdf98a8634469";
//		String city = "广东省";
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			if (StringUtils.isNotBlank(city)) {
//				city = URLEncoder.encode(city, "utf-8");
//			}
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			String sql = "select branch,dangerid from RiskMap_typhoonBlack where length(branch) < 5";
//			// String sql="select dangerid, dangeraddress from RISKMAP_danger where score is
//			// null";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//			// List<RiskMapDanger> countListC = new ArrayList<RiskMapDanger>();
//			List<String[]> countListC = new ArrayList<String[]>();
//			for (; rs.next();) {
//				String[] arr = new String[4];
//				arr[0] = rs.getString(1);
//				arr[1] = rs.getString(2);
//				countListC.add(arr);
//			}
//			for (int i = 0; i < countListC.size(); i++) {
//				// String mm="";
//				// if (StringUtils.isNotBlank(countListC.get(i)[1])) {
//				// mm = URLEncoder.encode(countListC.get(i)[1],"utf-8");
//				// }
//				// String params="&keywords="+mm+"&city="+city;
//				// String resJson = doGet(url,params);
//				// MapQueryResponse mapQueryResponse = new MapQueryResponse();
//				// mapQueryResponse =
//				// JSON.parseObject(resJson.toString(),MapQueryResponse.class);
//				// mapQueryResponse=this.modifyMapQueryResponse(mapQueryResponse);
//				// String lon =
//				// mapQueryResponse.getData().getRows().get(0).getLocation().split(",")[0];
//				// String lat =
//				// mapQueryResponse.getData().getRows().get(0).getLocation().split(",")[1];
//				// String score = mapQueryResponse.getData().getRows().get(0).getScore();
//				// String lon1=
//				// mapQueryResponse.getData().getRows().get(0).getLocaltion1().split(",")[0];
//				// String lat1=
//				// mapQueryResponse.getData().getRows().get(0).getLocaltion1().split(",")[1];
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				String sql1 = "update riskmap_typhoonBlack set branch = '" + (countListC.get(i)[0] + "0000")
//						+ "' where dangerID = '" + countListC.get(i)[1] + "'";
//				// String sql1 = "update riskmap_danger set pointx_2000 = '"+lon+"', pointy_2000
//				// = '"+lat+"', pointx_02 = '"+lon1+"', pointy_02 = '"+lat1+"', score =
//				// '"+score+"' where dangerID = '"+countListC.get(i)[0]+"'";
//				stat = conn.prepareStatement(sql1);
//				rs = stat.executeQuery();
//			}
//		} catch (SQLException | UnsupportedEncodingException e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	public String translate(String dangerCode) {
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("id.codeType", "riskMapTyphoonBlack");
//		queryRule.addEqual("id.codeCode", "20180000TF10");
//		queryRule.addEqual("validStatus", "1");
//		List<RiskDcode> dcodeList = databaseDao.findAll(RiskDcode.class, queryRule);
//		String dangerCodeCName = dcodeList.get(0).getCodeCname();
//		return dangerCodeCName;
//	}
//
//	@Override
//	public AjaxResult sumTyphoon(List<String> ids, String sql, String dangerFlag) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			StringBuffer sqlString = new StringBuffer();
//			for (int i = 0; i < ids.size(); i++) {
//				if (i == (ids.size() - 1)) {
//					sqlString.append(ids.get(i)); // SQL拼装，最后一条不加“,”。
//				} else if ((i % 999) == 0 && i > 0) {
//					sqlString.append(ids.get(i)).append(") or riskmap_typhoonBlack.DangerID in ("); // 解决ORA-01795问题
//				} else {
//					sqlString.append(ids.get(i)).append(",");
//				}
//			}
//			String sqlT = sql + " (DangerID in (" + sqlString.toString() + "))";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sqlT);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				BigDecimal[] l = new BigDecimal[4];
//				BigDecimal bd8 = new BigDecimal(10000);
//				if (rs.getBigDecimal(1) == null) {
//					l[0] = new BigDecimal("0");
//				} else {
//					l[0] = (rs.getBigDecimal(1).divide(bd8, 2, BigDecimal.ROUND_HALF_UP));
//				}
//				if (rs.getBigDecimal(2) == null) {
//					l[1] = new BigDecimal("0");
//				} else {
//					l[1] = (rs.getBigDecimal(2).divide(bd8, 2, BigDecimal.ROUND_HALF_UP));
//				}
//				if (rs.getBigDecimal(3) == null) {
//					l[2] = new BigDecimal("0");
//				} else {
//					l[2] = (rs.getBigDecimal(3).divide(bd8, 2, BigDecimal.ROUND_HALF_UP));
//				}
//				l[3] = rs.getBigDecimal(4);
//				ajaxResult.setData(l);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	// 根据经纬度获取暴雨分数
//	public AjaxResult getRainScoreByPoint(String pointX, String pointY) {
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			// 获取iserver的地址
//			ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
//			String serverName = bundle.getString("serverName");
//
//			String rainScore = "";
//			String url = serverName
//					+ "iserver/services/data-FXDT/rest/data/datasources/china/datasets/rain_hazard_scale_1km/gridValue.json";
//			String param = "x=" + pointX + "&y=" + pointY;
//			String resJson = doGet(url, param);
//			GridValue gridValue = new GridValue();
//			if (StringUtils.isNotBlank(resJson)) {
//				gridValue = JSON.parseObject(resJson.toString(), GridValue.class);
//				BigDecimal gridValueBd = gridValue.getValue();
//				// 假如值小于0，则置为0,保留两位小数
//				if (gridValueBd.compareTo(new BigDecimal(0)) == -1) {
//					gridValueBd = BigDecimal.ZERO;
//				}
//				rainScore = gridValueBd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//			} else {
//				System.out.println("请求的url:" + url + "?" + param);
//				rainScore = BigDecimal.ZERO.toString();
//			}
//			ajaxResult.setStatus(1);
//			ajaxResult.setData(rainScore);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("获取暴雨分数异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("获取暴雨分数异常:" + e);
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * @功能：iobjectjava 直接把后台的多个面数据转换成一个面数据
//	 * @param RiskMapInsuredModify
//	 * @return AjaxResult
//	 * @author liqiankun @时间：20190515 @修改记录：
//	 */
//	@SuppressWarnings("deprecation")
//	public AjaxResult returnGeometrist(MapRequestInfoVo mapRequestInfoVo) {
//
//		AjaxResult ajaxResult = new AjaxResult();
//
//		WzTFLsljVo wzTFLslj = mapRequestInfoVo.getWzTFLslj();
//
//		if (wzTFLslj != null && StringUtils.isNotBlank(wzTFLslj.getTfbh())) {
//
//			Workspace workspace = new Workspace();
//			// 定义数据源连接信息，假设以下所有数据源设置都存在
//			DatasourceConnectionInfo datasourceconnection = new DatasourceConnectionInfo();
//			// 进行数据源的连接
//			Datasource datasource = MapUtils.connectDataSource(workspace, datasourceconnection);
//			String TF_7 = "", TF_10 = "";
//			if (wzTFLslj.getFlag().equals("YJ")) {
//				TF_7 = "TFYJUNION_7M";
//				TF_10 = "TFYJUNION_10M";
//			} else if (wzTFLslj.getFlag().equals("LS")) {
//				TF_7 = "TFUNION_7M";
//				TF_10 = "TFUNION_10M";
//			}
//			// 获取的面数据集
//			DatasetVector datasetVector_7 = (DatasetVector) datasource.getDatasets().get(TF_7);
//
//			DatasetVector datasetVector_10 = (DatasetVector) datasource.getDatasets().get(TF_10);
//			/** 组织查询过滤信息 */
//			// String filter = "TFBH = 201822";
//			String filter = "TFBH =" + wzTFLslj.getTfbh();
//			Recordset recordset_7 = datasetVector_7.query(filter, CursorType.DYNAMIC);
//			Recordset recordset_10 = datasetVector_10.query(filter, CursorType.DYNAMIC);
//			Map<Integer, Feature> features = new HashMap<Integer, Feature>();
//			Map<Integer, Feature> features_7 = recordset_7.getAllFeatures();
//			Map<Integer, Feature> features_10 = recordset_10.getAllFeatures();
//
//			for (Integer key : features_7.keySet()) {
//				features.put(7, features_7.get(key));
//			}
//			for (Integer key : features_10.keySet()) {
//				features.put(10, features_10.get(key));
//			}
//			// features.putAll(features_7);
//			// features.putAll(features_10);
//
//			String geometryJson = "";
//			List<Geometry> geoList = new ArrayList<Geometry>();
//			if (recordset_7.getRecordCount() > 0 || recordset_10.getRecordCount() > 0) {
//				for (Feature feature : features.values()) {
//					Geometry geometry = feature.getGeometry();
//					geoList.add(geometry);
//				}
//				Geometry geometry = geoList.get(0);
//				for (int j = 1; j < geoList.size(); j++) {
//					Geometry geome = geoList.get(j);
//					geometry = Geometrist.union(geometry, geome);
//				}
//				geometryJson = Toolkit.GemetryToGeoJson(geometry);
//				ajaxResult.setData(geometryJson);
//				ajaxResult.setStatus(1);
//				ajaxResult.setStatusText("提取数据成功！");
//
//			} else {
//				System.out.println("==============没有数据");
//				ajaxResult.setStatus(2);
//				ajaxResult.setStatusText("没有数据!");
//			}
//			// 关闭资源
//			MapUtils.closeMapResource(recordset_10, recordset_7, null, null, null, datasetVector_10, datasetVector_7,
//					datasource, datasourceconnection, workspace);
//
//		} else {
//			ajaxResult.setStatus(3);
//			ajaxResult.setStatusText("请传递台风编号信息!");
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * @author 崔凤志
//	 * @date 2019-5-14
//	 * @param wz_tfbh表分页查询
//	 *            默认日期降序
//	 * @return
//	 */
//	@Override
//	public WZ_tfbhResponse findWzTfbhBypage(WZ_tfbhVo wz_tfbhVo) {
//		QueryRule queryRule = QueryRule.getInstance();
//		WZ_tfbh wz_tfbh = wz_tfbhVo.getWz_tfbh();
//		WZ_tfbhResponse response = new WZ_tfbhResponse();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			Integer rn = (wz_tfbhVo.getPageNumber() - 1) * (wz_tfbhVo.getPageSize());
//			Integer rowNum = (wz_tfbhVo.getPageNumber()) * (wz_tfbhVo.getPageSize());
//			StringBuffer sql = new StringBuffer(
//					"select * from(select a.*,ROWNUM rn from(select * from WZ_TFBH where 1=1 ");
//			// 查询总条数
//			StringBuffer totalSql = new StringBuffer("select count(*) from WZ_TFBH where 1=1 ");
//			// 拼接查询条件
//			String typhoonNo = wz_tfbh.getTfbh();
//			if (typhoonNo != null && typhoonNo.length() > 0) {
//				// 拼接台风编号
//				sql.append(" and tfbh = '" + typhoonNo + "'");
//				totalSql.append(" and tfbh = '" + typhoonNo + "'");
//			}
//			String isActive = wz_tfbh.getIsActive();
//			if (isActive != null && isActive.length() > 0) {
//				// 实时台风/历史台风 区分标志位
//				sql.append(" and isActive = '" + isActive + "'");
//				totalSql.append(" and isActive = '" + isActive + "'");
//			}
//			String typhoonName = wz_tfbh.getTfm();
//			if (typhoonName != null && typhoonName.length() > 0) {
//				// 拼接台风名称
//				sql.append(" and tfm = '" + typhoonName + "'");
//				totalSql.append(" and tfm = '" + typhoonName + "'");
//			}
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			if (wz_tfbhVo.getTfStartDate() != null) {
//				String startDate = formatter.format(wz_tfbhVo.getTfStartDate());
//				// 拼接起始时间
//				sql.append(" and (tfdate >= to_date('" + startDate + "','yyyy-mm-dd hh24:mi:ss') )");
//				totalSql.append(" and (tfdate >= to_date('" + startDate + "','yyyy-mm-dd hh24:mi:ss') )");
//			}
//			if (wz_tfbhVo.getTfEndDate() != null) {
//				String endDate = formatter.format(wz_tfbhVo.getTfEndDate());
//				// 拼接结束时间
//				sql.append(" and (tfdate <= to_date('" + endDate + "','yyyy-mm-dd hh24:mi:ss') )");
//				totalSql.append(" and (tfdate <= to_date('" + endDate + "','yyyy-mm-dd hh24:mi:ss') )");
//			}
//			sql.append(" order by tfdate desc,tfbh) a ) where rn>=" + rn + " and rn<" + rowNum);
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql.toString());
//			rs = stat.executeQuery();
//			List<WZ_tfbh> resultList = new ArrayList<>();
//			for (; rs.next();) {
//				WZ_tfbh temp = new WZ_tfbh();
//				temp.setTfbh(rs.getString("tfbh"));
//				temp.setTfm(rs.getString("tfm"));
//				temp.settFland(rs.getString("tFland"));
//				temp.setId(rs.getString("id"));
//				temp.setIsCompleted(rs.getString("isCompleted"));
//				temp.setTfme(rs.getString("tfme"));
//				temp.setBedite(rs.getString("bedite"));
//				temp.setIsLand(rs.getString("isLand"));
//				temp.setIsActive(rs.getString("isActive"));
//				temp.setTfDate(rs.getTimestamp("tfDate"));
//				resultList.add(temp);
//			}
//			response.setResultList(resultList);
//			response.setPageNumber(wz_tfbhVo.getPageNumber());
//			response.setPageSize(wz_tfbhVo.getPageSize());
//
//			stat = conn.prepareStatement(totalSql.toString());
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				response.setTotal(rs.getInt(1));
//			}
//		} catch (Exception e) {
//			log.info("查询异常：" + e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return response;
//	}
//
//	@Override
//	public List<Object> queryDangerData(String sql, String dangerFlag) {
//		List<Object> list = new ArrayList<Object>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			System.out.println(rs);
//			for (; rs.next();) {
//				if ("A".equals(dangerFlag)) {
//					RiskMapTyphoonBlackVo vo = new RiskMapTyphoonBlackVo();
//					vo.setPolicyNo(rs.getString(1));
//					vo.setInsuredName(rs.getString(2));
//					vo.setBranch(rs.getString(3));
//					vo.setBranchName(prpdcompanyService.getComCnameByComCode(vo.getBranch()));
//					vo.setRiskCode(rs.getString(4));
//					vo.setInsuerdAddress(rs.getString(5));
//					if (rs.getString(6) == null) {
//						vo.setInsuredAmount("0");
//					} else {
//						vo.setInsuredAmount(rs.getString(6));
//					}
//					if (rs.getString(7) == null) {
//						vo.setPremiumsAmount("0");
//					} else {
//						vo.setPremiumsAmount(rs.getString(7));
//					}
//					vo.setStartDate(rs.getDate(8));
//					vo.setEndDate(rs.getDate(9));
//					vo.setPercentage(rs.getDouble(10));
//					vo.setGuarantee(rs.getDouble(11));
//					vo.setCaseNo(rs.getString(12));
//					vo.setDangerDate(rs.getDate(13));
//					vo.setDangerBecause(rs.getString(14));
//					vo.setDangerCode(rs.getString(15));
//					String dangerCodeCName = this.queryDangerCodeName(vo.getDangerCode());
//					vo.setDangerCodeCName(dangerCodeCName);
//					vo.setEstimateAmount(rs.getDouble(16));
//					vo.setCloseFlag(rs.getString(17));
//					vo.setCloseDate(rs.getDate(18));
//					vo.setRiskModel(rs.getString(19));
//					vo.setDangerId(rs.getString(20));
//					list.add(vo);
//				} else if ("B".equals(dangerFlag)) {
//					RiskMapFloodInfoVo vo = new RiskMapFloodInfoVo();
//					vo.setTotalCount(rs.getInt(1));
//					vo.setMinFloodGrade(rs.getInt(2));
//					vo.setMaxFloodDepth(rs.getBigDecimal(3));
//					vo.setDangerId(rs.getString(4));
//					vo.setFloodGrade(rs.getInt(5));
//					vo.setFloodDepth(rs.getBigDecimal(6));
//					vo.setOperatorCode(rs.getString(8));
//					vo.setFloodDate(rs.getDate(9));
//					list.add(vo);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return list;
//	}
//
//	public String queryDangerCodeName(String dangerCode) {
//		QueryRule queryRule = QueryRule.getInstance();
//		String dangerCodeCName = "";
//		List<RiskDcode> list = null;
//		if (StringUtils.isNotBlank(String.valueOf(dangerCode)) && !"null".equals(String.valueOf(dangerCode))) {
//			queryRule.addLike("id.codeType", "riskMapTyphoonBlack");
//			queryRule.addLike("id.codeCode", dangerCode);
//			list = databaseDao.findAll(RiskDcode.class, queryRule);
//			if (null != list && list.size() > 0) {
//				dangerCodeCName = list.get(0).getCodeCname();
//			}
//		}
//		return dangerCodeCName;
//	}
//
//	public int getMaxValue() {
//		ResourceBundle filePath = ResourceBundle.getBundle("config.map", Locale.getDefault());
//		// String riskmapTyphoon = filePath.getString("riskMap_Typhoon");
//		String riskmapTyphoonName = filePath.getString("riskMap_Typhoon");
//		// 获取表名
//		String riskmapTyphoon = MapUtils.getDataSetTableName(riskmapTyphoonName);
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		int maxSMID = 1;
//		try {
//			String sql = "select max(SMID) from " + riskmapTyphoon;
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				maxSMID = rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return maxSMID;
//	}
//
//	@Override
//	public AjaxResult queryLSTfbh(String tfbh) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		List<WzTFLsljVo> tfHistory = new ArrayList<WzTFLsljVo>();
//		try {
//			if (StringUtils.isNotBlank(tfbh)) {
//				String sql = "select * from WZ_TFLSLJ where tfbh = '" + tfbh.trim() + "' order by rqsj asc";
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				stat = conn.prepareStatement(sql);
//				rs = stat.executeQuery();
//				// int size = rs.getRow();
//				for (; rs.next();) {
//					WzTFLsljVo temp = new WzTFLsljVo();
//					temp.setTfbh(rs.getString("tfbh"));
//					temp.setRqsj(rs.getTimestamp("rqsj"));
//					temp.setWd(rs.getString("wd"));
//					temp.setJd(rs.getString("jd"));
//					temp.setZxqy(rs.getString("zxqy"));
//					temp.setZxfs(rs.getString("zxfs"));
//					temp.setYdsd(rs.getString("ydsd"));
//					temp.setYdfx(rs.getString("ydfx"));
//					temp.setRadius7(rs.getString("radius7"));
//					temp.setRadius10(rs.getString("radius10"));
//					temp.setDepict(rs.getString("depict"));
//					temp.setBedit(rs.getString("bedit"));
//
//					temp.setRadius7_en(rs.getString("radius7_en"));
//					temp.setRadius7_wn(rs.getString("radius7_wn"));
//					temp.setRadius7_es(rs.getString("radius7_es"));
//					temp.setRadius7_ws(rs.getString("radius7_ws"));
//					temp.setRadius10_en(rs.getString("radius10_en"));
//					temp.setRadius10_wn(rs.getString("radius10_wn"));
//					temp.setRadius10_es(rs.getString("radius10_es"));
//					temp.setRadius10_ws(rs.getString("radius10_ws"));
//					temp.setRadius12_en(rs.getString("radius12_en"));
//					temp.setRadius12_wn(rs.getString("radius12_wn"));
//					temp.setRadius12_es(rs.getString("radius12_es"));
//					temp.setRadius12_ws(rs.getString("radius12_ws"));
//
//					tfHistory.add(temp);
//				}
//				if (!(tfHistory.isEmpty())) {
//					ajaxResult.setData(tfHistory);
//					ajaxResult.setStatus(0);
//				} else {
//					ajaxResult.setStatus(1);
//					ajaxResult.setStatusText("无台风历史信息数据");
//				}
//			}
//		} catch (Exception e) {
//			log.info("查询台风历史信息数据异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询台风历史信息数据异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public AjaxResult queryYbljForTimeAndTfbh(String tfbh, String ybsj) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		List<WzTFYbljVo> yblj = new ArrayList<WzTFYbljVo>();
//		try {
//			if (StringUtils.isNotBlank(tfbh) && StringUtils.isNotBlank(ybsj)) {
//				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				// select * from WZ_TFYBLJ where tfbh = '201822' and rqsj = to_date('2018-09-17
//				// 00:00:00','yyyy-mm-dd hh24:mi:ss')
//				String sql = "select * from WZ_TFYBLJ where tfbh = '" + tfbh.trim() + "' and ybsj = to_date('" + ybsj
//						+ "','yyyy-mm-dd hh24:mi:ss')";
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				stat = conn.prepareStatement(sql);
//				rs = stat.executeQuery();
//				// System.out.println(rs);
//				for (; rs.next();) {
//					WzTFYbljVo item = new WzTFYbljVo();
//					item.setTfbh(rs.getString("tfbh"));
//					item.setTm(rs.getString("tm"));
//					item.setYbsj(rs.getTimestamp("ybsj"));
//					item.setRqsj(rs.getTimestamp("rqsj"));
//					item.setWd(rs.getString("wd"));
//					item.setJd(rs.getString("jd"));
//					item.setZxqy(rs.getString("zxqy"));
//					item.setZxfs(rs.getString("zxfs"));
//					item.setYdfx(rs.getString("ydfx"));
//					item.setRadius7(rs.getString("radius7"));
//					item.setRadius10(rs.getString("radius10"));
//					item.setDepict(rs.getString("depict"));
//					item.setLinecoloe(rs.getString("linecoloe"));
//					item.setIsUse(rs.getString("isUse"));
//
//					item.setRadius7_en(rs.getString("radius7_en"));
//					item.setRadius7_wn(rs.getString("radius7_wn"));
//					item.setRadius7_es(rs.getString("radius7_es"));
//					item.setRadius7_ws(rs.getString("radius7_ws"));
//					item.setRadius10_en(rs.getString("radius10_en"));
//					item.setRadius10_wn(rs.getString("radius10_wn"));
//					item.setRadius10_es(rs.getString("radius10_es"));
//					item.setRadius10_ws(rs.getString("radius10_ws"));
//					item.setRadius12_en(rs.getString("radius12_en"));
//					item.setRadius12_wn(rs.getString("radius12_wn"));
//					item.setRadius12_es(rs.getString("radius12_es"));
//					item.setRadius12_ws(rs.getString("radius12_ws"));
//
//					yblj.add(item);
//				}
//				if (!(yblj.isEmpty())) {
//					// 台风预测数据信息根据地区（tm）重新分组
//					Map<String, List<WzTFYbljVo>> ybljMap = new HashMap<String, List<WzTFYbljVo>>();
//					for (WzTFYbljVo ybljItem : yblj) {
//						if (ybljMap.containsKey(ybljItem.getTm())) {
//							ybljMap.get(ybljItem.getTm()).add(ybljItem);
//						} else {
//							List<WzTFYbljVo> listNew = new ArrayList<WzTFYbljVo>();
//							listNew.add(ybljItem);
//							ybljMap.put(ybljItem.getTm(), listNew);
//						}
//					}
//					ajaxResult.setData(ybljMap);
//					ajaxResult.setStatus(0);
//				} else {
//					ajaxResult.setStatus(1);
//					ajaxResult.setStatusText("无台风预测信息数据");
//				}
//			}
//		} catch (Exception e) {
//			log.info("查询台风预测信息数据异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询台风预测信息数据异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public String getMaxSerialNo() {
//		int serialNo = 0;
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			sql = "select max(SERIALNO) SERIALNO  from WZ_RISKWARNINGPUSH";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			while (rs.next()) {
//				serialNo = rs.getInt("SERIALNO") + 1;
//			}
//		} catch (Exception e) {
//			log.info("获取预警推送的最新序号异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("获取预警推送的最新序号异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//
//		return serialNo + "";
//	}
//
//	@Override
//	public List<WZ_tfbh> getTFBH() {
//		List<WZ_tfbh> list = new ArrayList<WZ_tfbh>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			sql = "select * from WZ_TFBH where isactive = '1'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				WZ_tfbh tf = new WZ_tfbh();
//				tf.setTfbh(rs.getString(1));
//				tf.setTfm(rs.getString(2));
//				list.add(tf);
//			}
//		} catch (Exception e) {
//			log.info("获取台风编号异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("获取台风编号异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return list;
//	}
//
//	@Override
//	public void saveRiskWarningPush(RiskWarningPush wp) {
//		wp.setMadeDate(new Date());
//		AjaxResult ajaxResult = this.queryByserialNo(wp.getSerialNo());
//		if (ajaxResult.getData() != null) {
//			databaseDao.update(RiskWarningPush.class, wp);
//		} else {
//			databaseDao.save(RiskWarningPush.class, wp);
//		}
//	}
//
//	@Override
//	public AjaxResult queryRiskWarningPush(RiskWarningPush wp, UserInfo userInfo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Page page = new Page();
//		QueryRule queryRule = QueryRule.getInstance();
//		if (wp.getStartRadius() != null || wp.getEndRadius() != null) {
//			Integer startRadius = wp.getStartRadius();
//			Integer endRdius = wp.getEndRadius();
//			if (startRadius != null && endRdius != null) {
//				queryRule.addBetween("radius", startRadius, endRdius);
//			} else if (startRadius != null) {
//				queryRule.addGreaterEqual("radius", startRadius);
//			} else {
//				queryRule.addLessEqual("radius", endRdius);
//			}
//		}
//		if (StringUtils.isNotBlank(String.valueOf(wp.getTFBH()))) {
//			queryRule.addLike("TFBH", wp.getTFBH());
//		}
//		if (StringUtils.isNotBlank(String.valueOf(wp.getComCode()))) {
//			queryRule.addLike("comCode", wp.getComCode());
//		}
//		if (StringUtils.isNotBlank(String.valueOf(wp.getAddressCode()))) {
//			queryRule.addLike("addressCode", wp.getAddressCode());
//		}
//		if (StringUtils.isNotBlank(String.valueOf(wp.getAddressProvinceCode()))) {
//			queryRule.addLike("addressProvinceCode", wp.getAddressProvinceCode());
//		}
//		if (StringUtils.isNotBlank(String.valueOf(wp.getOperatorCode()))) {
//			queryRule.addLike("operatorCode", wp.getOperatorCode());
//		}
//		// 权限校验
//		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
//		try {
//			String powerSQL = saaAPIService.addPower("riskcontrol", userInfo.getUserCode(), "riskins_query",
//					"this_.comCode", "", "");
//			queryRule.addSql(powerSQL);
//		} catch (Exception e) {
//			log.info("addPower执行异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("addPower执行异常:" + e);
//		}
//		queryRule.addAscOrder("insertTimeForHis");
//		// DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd日");
//		page = databaseDao.findPage(RiskWarningPush.class, queryRule, wp.getPageNo(), wp.getPageSize());
//		List<RiskWarningPush> list = page.getResult();
//		for (RiskWarningPush WP : list) {
//			WP.setOperatorName(WP.getOperatorName());
//			if (StringUtils.isNotBlank(WP.getAddressCode())) {
//				String address = this.queryAddress(WP.getAddressCode());
//				WP.setAddressName(address);
//			}
//			// 翻译台风名称
//			if (StringUtils.isNotBlank(WP.getTFBH())) {
//				String tFBHName = this.queryTFBHName(WP.getTFBH());
//				WP.setTFBHName(tFBHName);
//			}
//		}
//		ajaxResult.setData(list);
//		ajaxResult.setStatus(page.getTotalCount());
//		return ajaxResult;
//	}
//
//	public String queryTFBHName(String tFBH) {
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String sql = "";
//		String TFBHName = "";
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			sql = "select * from WZ_TFBH where tfbh = '" + tFBH + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				TFBHName = rs.getString(2);
//			}
//		} catch (Exception e) {
//			log.info("翻译台风编号异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("翻译台风编号异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return TFBHName;
//	}
//
//	@Override
//	public AjaxResult deleteWarningPush(Integer serialNo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			sql = "delete from WZ_RISKWARNINGPUSH where serialNo = '" + serialNo + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			ajaxResult.setStatus(1);
//			ajaxResult.setStatusText("删除成功");
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setStatusText("删除失败");
//			log.info("删除预警信息异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("删除预警信息异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public AjaxResult queryByserialNo(Integer serialNo) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			sql = "select * from WZ_RISKWARNINGPUSH where serialNo = '" + serialNo + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				RiskWarningPush wp = new RiskWarningPush();
//				wp.setSerialNo(rs.getInt(1));
//				wp.setComCode(rs.getString(2));
//				wp.setAddressCode(rs.getString(3));
//				wp.setOperatorCode(rs.getString(4));
//				wp.setForehead(rs.getBigDecimal(5));
//				wp.setBusiness(rs.getString(6));
//				wp.setLossRatio(rs.getDouble(7));
//				wp.setOccurrences(rs.getInt(8));
//				wp.setRiskCode(rs.getString(9));
//				wp.setInventory(rs.getString(10));
//				wp.setTFBH(rs.getString(14));
//				wp.setComCodeCName(rs.getString(15));
//				wp.setOperatorName(rs.getString(16));
//				wp.setEmail(rs.getString(17));
//				wp.setRadius(rs.getInt(18));
//				wp.setAddressProvinceCode(rs.getString(19));
//				ajaxResult.setData(wp);
//			}
//			ajaxResult.setStatus(1);
//		} catch (Exception e) {
//			ajaxResult.setStatus(0);
//			ajaxResult.setStatusText("查询失败");
//			log.info("查询预警信息异常：" + e.getMessage(), e);
//			e.printStackTrace();
//			throw new RuntimeException("查询预警信息异常:" + e);
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	public String queryAddress(String addressCode) {
//		String address = "";
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		// String riskMap_county = bundle.getString("riskMap_county");
//		String riskMap_countyName = bundle.getString("riskMap_county");
//		// 获取表名
//		String riskMap_county = MapUtils.getDataSetTableName(riskMap_countyName);
//		try {
//			String key = cacheManager.generateCacheKey("queryaddressCode", addressCode);
//			Object object = cacheManager.getCache(key);
//			if (object == null) {
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				String sql = "select distinct provinceName,cityName from " + riskMap_county + " where cityAdCode = '"
//						+ addressCode + "'";
//				stat = conn.prepareStatement(sql);
//				rs = stat.executeQuery();
//				for (; rs.next();) {
//					address = rs.getString(1) + rs.getString(2);
//				}
//				cacheManager.putCache(key, address);
//			} else {
//				address = (String) object;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//			if (stat != null) {
//				try {
//					conn.close();
//				} catch (Exception e3) {
//					e3.printStackTrace();
//				}
//			}
//			if (rs != null) {
//				try {
//					conn.close();
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}
//		}
//		return address;
//	}
//
//	/**
//	 * 生成实时台风报告
//	 * 
//	 * @author 崔凤志
//	 * @param typhoonNo,imgData
//	 * @return
//	 */
//	@Override
//	public AjaxResult createTyphoonWordCurrent(String typhoonNo,String imgData,String userCode,String rainCode) {
//		QueryRule queryRule = QueryRule.getInstance();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		TfWordRespVo vo = new TfWordRespVo();
//		AjaxResult ajaxResult = new AjaxResult();
//		FTPUtil ftp = new FTPUtil();
//		OutputStream out = null;
//		Writer write = null;
//		// 处理图片路径
//		imgData = imgData.split(",")[1];
//		try {
//			// 查询台风名称和发生时间
//			String sql = "select  TFM,TFDATE  from  WZ_TFBH where TFBH='" + typhoonNo + "'";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				vo.setTfName(rs.getString("tfm"));
//				vo.setStartDate(rs.getTimestamp("tfDate"));
//			}
//			if (vo.getStartDate() != null) {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(vo.getStartDate());
//				// 设置台风起始年、月、日
//				vo.setStartYear(cal.get(Calendar.YEAR) + "");
//				vo.setStartMonth(cal.get(Calendar.MONTH) + 1 + "");
//				vo.setStartDay(cal.get(Calendar.DAY_OF_MONTH) + "");
//			}
//			// 查询台风生成时间的台风经度、台风纬度
//			sql = "select JD,WD  from (select *  from WZ_TFLSLJ a where TFBH='" + typhoonNo
//					+ "' order by RQSJ asc) where rownum=1";
//			stat = conn.prepareStatement(sql.toString());
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				vo.setJd(rs.getString("JD"));
//				vo.setWd(rs.getString("WD"));
//			}
//			// 查询台风最新时间 最大YDSD 最大ZXFS 最低ZXQY
//			sql = "select max(to_number(YDSD)),max(to_number(ZXFS)),min(to_number(ZXQY)) from WZ_TFLSLJ  where TFBH='"
//					+ typhoonNo + "'";
//			stat = conn.prepareStatement(sql.toString());
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				vo.setYdsd(rs.getString("max(to_number(YDSD))"));
//				vo.setZxfs(rs.getString("max(to_number(ZXFS))"));
//				vo.setZxqy(rs.getString("min(to_number(ZXQY))"));
//			}
//			sql = "select RADIUS7,RADIUS10 from (select * from WZ_TFLSLJ where TFBH='" + typhoonNo
//					+ "' order by RQSJ desc) where ROWNUM =1";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				vo.setRadius7(rs.getString("radius7"));
//				vo.setRadius10(rs.getString("radius10"));
//			}
//			// 查询七级风圈的 险种类别、保额
//			sql = "select JAMOUNT,QAMOUNT,GAMOUNT,TOTALAMOUNT from  WZ_CORPORELS_P  WHERE  tfbh='" + typhoonNo
//					+ "' and  procityflag =3";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			WzCorporeLsPVo p = new WzCorporeLsPVo();
//			Map<String, Object> fqRisk = new LinkedHashMap<>();
//			for (; rs.next();) {
//				if (rs.getBigDecimal("jAmount") != null) {
//					p.setjAmount(rs.getBigDecimal("jAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("qAmount") != null) {
//					p.setqAmount(rs.getBigDecimal("qAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("gAmount") != null) {
//					p.setgAmount(rs.getBigDecimal("gAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("TotalAmount") != null) {
//					p.setTotalAmount(
//							rs.getBigDecimal("TotalAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//			}
//			fqRisk.put("家财险", p.getjAmount());
//			fqRisk.put("企财险", p.getqAmount());
//			fqRisk.put("工程险", p.getgAmount());
//			fqRisk.put("合计", p.getTotalAmount());
//
//			// 查询市级台风 地市、险种类别、保额
//			sql = "select  *  from  WZ_CORPORELS_P  where  tfbh='" + typhoonNo + "' and  procityflag =2";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			List<WzCorporeLsPVo> wzCorporelsPList = new ArrayList<>();
//			BigDecimal allCityTotal = new BigDecimal(0);
//			for (; rs.next();) {
//				WzCorporeLsPVo corporelsP = new WzCorporeLsPVo();
//				corporelsP.setCityName(rs.getString("cityname"));
//				if (rs.getBigDecimal("jAmount") != null) {
//					corporelsP.setjAmount(
//							rs.getBigDecimal("jAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("qAmount") != null) {
//					corporelsP.setqAmount(
//							rs.getBigDecimal("qAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("gAmount") != null) {
//					corporelsP.setgAmount(
//							rs.getBigDecimal("gAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("totalAmount") != null) {
//					corporelsP.setTotalAmount(
//							rs.getBigDecimal("totalAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				allCityTotal = allCityTotal.add(corporelsP.getTotalAmount());
//				wzCorporelsPList.add(corporelsP);
//			}
//			String allCityTotalStr=allCityTotal.toString();
//			Map<String,Object> rainMap =new HashMap<>();
//			if(StringUtils.isNotBlank(rainCode)){
//				rainMap = this.countRainCorporelsP(rainCode,rainCode.substring(0,2));
//			}
//			
//			HashMap<String, Object> map =new HashMap<>();
//			map.put("typhoonVo", vo);
//			map.put("imgData", imgData);
//			map.put("fqRisk", fqRisk);
//			map.put("wzCorporelsPList", wzCorporelsPList);
//			map.put("allCityTotalStr", allCityTotalStr);
//			map.putAll(rainMap);
//			
//			//使用freemarker模板生成台风报告
//			Configuration configuration = new Configuration();
//			configuration.setDefaultEncoding("utf-8");
//			String path = this.getClass().getResource("/").getPath();
//			path = path + "com/picc/riskctrl/map/template";
//			File file = new File(path);
//			configuration.setDirectoryForTemplateLoading(file);
//
//			Template template = configuration.getTemplate("TyphoonWordCurrentTime.ftl");
//			template.setEncoding("utf-8");
//			long currentTime = System.currentTimeMillis();
//			out = ftp.uploadFile("downloadFile/TyphoonWord_" + userCode +"_"+currentTime+ ".doc");
//			write = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
//
//			template.process(map, write);
//
//			ajaxResult.setStatusText("success");
//			ajaxResult.setData("riskcontrol_file/downloadFile/TyphoonWord_" + userCode+"_"+currentTime+ ".doc"); // 返回文档路径
//		} catch (Exception e) {
//			ajaxResult.setStatusText("error");
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (write != null) {
//				try {
//					write.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (ftp != null) {
//				try {
//					ftp.close();
//				} catch (IOException e) {
//					log.info("关闭ftp异常：" + e.getMessage(), e);
//				}
//			}
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//	/**
//	 * 统计降雨标的信息
//	 * @author liqiankun 20200325
//	 * @param rainCode,rainCode
//	 * @return
//	 */
//	public Map<String,Object> countRainCorporelsP(String rainCode,String rainWarnFlag){
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String sql="";
//		Map<String,Object> map = new HashMap<String, Object>();
//		List<WzCorporeLsPVo> wzCorporelsPListGrade=new ArrayList<WzCorporeLsPVo>();
//		//降雨影响金额总计
////		BigDecimal totalAllAmount=new BigDecimal(0);
//		try {
//			//查询市级降雨等级、险种类别、保额
//			if("RS".equals(rainWarnFlag)){
//				// 实况降雨
////				sql="select  *  from  WZ_CORPORELS_P  where  tfbh='"+rainCode+"' and  procityflag ='4'";
//				sql="select  *  from  WZ_CORPORELS_P  where  tfbh='"+rainCode+"' and substr(CITYCODE,3,4) ='0000'";
//			}else if("RY".equals(rainWarnFlag)){
//				// 预警降雨
////				sql="select  *  from  WZ_CORPOREYJ_P  where  tfbh='"+rainCode+"' and  procityflag ='4'";
//				sql="select  *  from  WZ_CORPOREYJ_P  where  tfbh='"+rainCode+"'  and substr(CITYCODE,3,4) ='0000'";
//			}
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat=conn.prepareStatement(sql);
//			rs=stat.executeQuery();
//			Map<String, List<WzCorporeLsPVo>> mapNew = new HashMap<String, List<WzCorporeLsPVo>>();
//			for(;rs.next();) {
//				//降雨等级
//				String proCityFlag = rs.getString("proCityFlag");
//				WzCorporeLsPVo corporelsP=new WzCorporeLsPVo();
//				/*省市信息*/
//				String cityCode = rs.getString("cityCode");
//				String cityName = rs.getString("cityName");
//				corporelsP.setProCityFlag(proCityFlag);
//				corporelsP.setCityCode(cityCode);
//				corporelsP.setCityName(cityName);
//				if(rs.getBigDecimal("jAmount")!=null) {
//					corporelsP.setjAmount(rs.getBigDecimal("jAmount").divide(new BigDecimal("10000"),2, RoundingMode.HALF_UP));
//				}
//				if(rs.getBigDecimal("qAmount")!=null) {
//					corporelsP.setqAmount(rs.getBigDecimal("qAmount").divide(new BigDecimal("10000"),2, RoundingMode.HALF_UP));
//				}
//				if(rs.getBigDecimal("gAmount")!=null) {
//					corporelsP.setgAmount(rs.getBigDecimal("gAmount").divide(new BigDecimal("10000"),2, RoundingMode.HALF_UP));
//				}
//				if(rs.getBigDecimal("totalAmount")!=null) {
//					corporelsP.setTotalAmount(rs.getBigDecimal("totalAmount").divide(new BigDecimal("10000"),2, RoundingMode.HALF_UP));
//				}
//				
//				if(mapNew.containsKey(proCityFlag)){
//					List<WzCorporeLsPVo> wzCorporelsPList = mapNew.get(proCityFlag);
//					wzCorporelsPList.add(corporelsP);
//					mapNew.put(proCityFlag, wzCorporelsPList);
//				}else {
//					List<WzCorporeLsPVo> wzCorporelsPList=new ArrayList<WzCorporeLsPVo>();
////					totalAllAmount = totalAllAmount.add(corporelsP.getTotalAmount());
//					wzCorporelsPList.add(corporelsP);
//					mapNew.put(proCityFlag, wzCorporelsPList);
//				}
//			}
//			for(Map.Entry<String, List<WzCorporeLsPVo>> keyList : mapNew.entrySet()){
//				WzCorporeLsPVo corporelsP=new WzCorporeLsPVo();
//				//进行降雨等级翻译
//				corporelsP.setProCityFlag(MapUtils.translateRainGrade(keyList.getKey()));
//				corporelsP.setWzCorporeLsPChildVo(keyList.getValue());
//				BigDecimal totalAllAmount=new BigDecimal(0);
//				for(WzCorporeLsPVo wzCorporeLsPVo:keyList.getValue()){
//					totalAllAmount = totalAllAmount.add(wzCorporeLsPVo.getTotalAmount());
//				}
//				corporelsP.setTotalAmount(totalAllAmount);
//				wzCorporelsPListGrade.add(corporelsP);
//			}
////			String totalAllAmountStr=totalAllAmount.toString();
////			map.put("totalAllAmountStr", totalAllAmountStr);
////			map.put("wzCorporelsPListRain", wzCorporelsPList);
//			map.put("wzCorporelsPListGrade", wzCorporelsPListGrade);
//		} catch (SQLException e) {
//			log.error("查询降雨标的信息失败：" + e.getMessage() ,e);
//			e.printStackTrace();
//		}finally{
//			MapUtils.releaseResources(stat, conn, rs);
//		}
//		return map;
//	}
//	/**
//	 * 生成预警台风报告
//	 * 
//	 * @author 崔凤志
//	 * @param typhoonNo,imgData
//	 * @return
//	 */
//	@Override
//	public AjaxResult createTyphoonWordYJ(String tfbh, String imgData,String userCode,String rainCode) {
//		QueryRule queryRule = QueryRule.getInstance();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		TfWordRespVo vo = new TfWordRespVo();
//		AjaxResult ajaxResult = new AjaxResult();
//		FTPUtil ftp = new FTPUtil();
//		OutputStream out = null;
//		Writer write = null;
//		// 处理图片路径
//		imgData = imgData.split(",")[1];
//		try {
//			// 根据台风编号查询台风名称
//			String sql = "select  TFM  from  WZ_TFBH  where TFBH='" + tfbh + "'";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				vo.setTfName(rs.getString("TFM"));
//			}
//			// 查询最新预警时间的一条台风数据
//			sql = "select * from (select * from  WZ_TFYBLJ  where tfbh='" + tfbh
//					+ "' and tm='中国' order by YBSJ desc) where rownum =1";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			Calendar cal = Calendar.getInstance();
//			for (; rs.next();) {
//				vo.setRqsj(rs.getTimestamp("rqsj"));
//				vo.setYbsj(rs.getTimestamp("ybsj"));
//				if (rs.getTimestamp("ybsj") != null) {
//					cal.setTime(rs.getTimestamp("ybsj"));
//					// 设置预警时间 年 月 日
//					vo.setStartYear(cal.get(Calendar.YEAR) + "");
//					vo.setStartMonth(cal.get(Calendar.MONTH) + 1 + "");
//					vo.setStartDay(cal.get(Calendar.DAY_OF_MONTH) + "");
//				}
//				vo.setJd(rs.getString("jd"));
//				vo.setWd(rs.getString("wd"));
//				vo.setZxfs(rs.getString("zxfs"));
//				vo.setZxqy(rs.getString("zxqy"));
//				vo.setRadius7(rs.getString("radius7"));
//				vo.setRadius10(rs.getString("radius10"));
//			}
//			long hours = 0l;
//			if (vo.getRqsj() != null & vo.getYbsj() != null) {
//				// 计算预警时长
//				Long rqsj = vo.getRqsj().getTime();
//				Long ybsj = vo.getYbsj().getTime();
//				hours = (rqsj - ybsj) / (1000 * 60 * 60);
//			}
//
//			// 查询预警台风 风圈 对应的险种类别和保额
//			sql = "select  *  from  WZ_CORPOREYJ_P  WHERE procityflag =3 and tfbh='" + tfbh + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			Map<String, Object> fqRisk = new LinkedHashMap<>();
//			for (; rs.next();) {
//				if (rs.getBigDecimal("jAmount") != null) {
//					fqRisk.put("家财险",
//							rs.getBigDecimal("jAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("qAmount") != null) {
//					fqRisk.put("企财险",
//							rs.getBigDecimal("qAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("gAmount") != null) {
//					fqRisk.put("工程险",
//							rs.getBigDecimal("gAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("totalAmount") != null) {
//					fqRisk.put("合计",
//							rs.getBigDecimal("totalAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//			}
//
//			// 查询预警台风 市级 对应的险种类别和保额
//			sql = "select  *  from  WZ_CORPOREYJ_P  where procityflag =2 and tfbh='" + tfbh + "'";
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			List<WzCorporeYjP> wzCorporeYjPList = new ArrayList<>();
//			BigDecimal allCityTotal = new BigDecimal(0);
//			for (; rs.next();) {
//				WzCorporeYjP yjp = new WzCorporeYjP();
//				yjp.setCityName(rs.getString("cityname"));
//				if (rs.getBigDecimal("jAmount") != null) {
//					yjp.setJAmount(
//							rs.getBigDecimal("jAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("qAmount") != null) {
//					yjp.setQAmount(
//							rs.getBigDecimal("qAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("gAmount") != null) {
//					yjp.setGAmount(
//							rs.getBigDecimal("gAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				if (rs.getBigDecimal("totalAmount") != null) {
//					yjp.setTotalAmount(
//							rs.getBigDecimal("totalAmount").divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP));
//				}
//				allCityTotal = allCityTotal.add(yjp.getTotalAmount());
//				wzCorporeYjPList.add(yjp);
//			}
//			String allCityTotalStr = allCityTotal.toString();
//			HashMap<String, Object> map = new HashMap<>();
//			map.put("typhoonVo", vo);
//			map.put("hours", hours);
//			map.put("imgData", imgData);
//			map.put("fqRisk", fqRisk);
//			map.put("wzCorporeYjPList", wzCorporeYjPList);
//			map.put("allCityTotalStr", allCityTotalStr);
//			
//			Map<String,Object> rainMap =new HashMap<>();
//			if(StringUtils.isNotBlank(rainCode)){
//				rainMap = this.countRainCorporelsP(rainCode,rainCode.substring(0,2));
//			}
//			map.putAll(rainMap);
//			//使用freemarker模板生成台风报告
//			Configuration configuration = new Configuration();
//			configuration.setDefaultEncoding("utf-8");
//			String path = this.getClass().getResource("/").getPath();
//			path = path + "com/picc/riskctrl/map/template";
//			configuration.setDirectoryForTemplateLoading(new File(path));
//			
//			Template template = configuration.getTemplate("TyphoonWordYJ.ftl");
//			template.setEncoding("utf-8");
//			long currentTime=System.currentTimeMillis();
//			out = ftp.uploadFile("downloadFile/TyphoonYJ_" + userCode+"_"+currentTime + ".doc");
//			write = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
//
//			template.process(map, write);
//
//			ajaxResult.setStatusText("success");
//			ajaxResult.setData("riskcontrol_file/downloadFile/TyphoonYJ_" + userCode+"_"+currentTime + ".doc");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (write != null) {
//				try {
//					write.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (ftp != null) {
//				try {
//					ftp.close();
//				} catch (IOException e) {
//					log.info("关闭ftp异常：" + e.getMessage(), e);
//				}
//			}
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	@Override
//	public AjaxResult queryTargetForTfbh(String tfbh, String target,String comCode) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String tableName = "";
//		List<WzCorporeYjPVo> YjTarget = new ArrayList<WzCorporeYjPVo>();
//		List<WzCorporeLsPVo> LsTarget = new ArrayList<WzCorporeLsPVo>();
//		String cityCodeSql = "";
//		if(StringUtils.isNotBlank(comCode)){
//			if("000000".equals(comCode.trim())){
//				cityCodeSql = " and cityCode ='-1'";
//			}else if(comCode.trim().endsWith("0000")){
//				cityCodeSql = " and substr(cityCode,1,2) ='"+comCode.trim().substring(0,2)+"'";
//			}else {
//				cityCodeSql = " and cityCode ='"+comCode.trim()+"'";
//			}
//		}
//		if (target.equals("YbTarget")) {
//			tableName = "WZ_CORPOREYJ_P";
//			try {
//				if (StringUtils.isNotBlank(tfbh)) {
//					String sql = "select * from " + tableName.trim() + " where tfbh = '" + tfbh.trim() + "'";
//					sql+=cityCodeSql;
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					stat = conn.prepareStatement(sql);
//					rs = stat.executeQuery();
//					for (; rs.next();) {
//						WzCorporeYjPVo itemp = new WzCorporeYjPVo();
//						itemp.setTfbh(rs.getString("tfbh"));
//						itemp.setCityCode(rs.getString("cityCode"));
//						itemp.setCityName(rs.getString("cityName"));
//						itemp.setTotalAmount(rs.getBigDecimal("totalAmount"));
//						itemp.setjAmount(rs.getBigDecimal("jAmount"));
//						itemp.setqAmount(rs.getBigDecimal("qAmount"));
//						itemp.setgAmount(rs.getBigDecimal("gAmount"));
//						itemp.setCenterX(rs.getBigDecimal("centerX"));
//						itemp.setCenterY(rs.getBigDecimal("centerY"));
//						itemp.setProCityFlag(rs.getString("proCityFlag"));
//						itemp.setCorporeSum(rs.getBigDecimal("corporeSum"));
//						YjTarget.add(itemp);
//					}
//					if (!(target.isEmpty())) {
//						// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
//						Map<String, List<WzCorporeYjPVo>> targetMap = new HashMap<String, List<WzCorporeYjPVo>>();
//
//						for (WzCorporeYjPVo itemTarget : YjTarget) {
//							if (targetMap.containsKey(itemTarget.getProCityFlag())) {
//								// itemList.add(itemTarget);
//								targetMap.get(itemTarget.getProCityFlag()).add(itemTarget);
//								// targetMap.put(itemTarget.getProCityFlag(), itemList);
//							} else {
//								List<WzCorporeYjPVo> itemList = new ArrayList<WzCorporeYjPVo>();
//								itemList.add(itemTarget);
//								targetMap.put(itemTarget.getProCityFlag(), itemList);
//							}
//						}
//						ajaxResult.setData(targetMap);
//						ajaxResult.setStatus(0);
//					} else {
//						ajaxResult.setStatus(1);
//						ajaxResult.setStatusText("无台风预测影响标的");
//					}
//				}
//			} catch (Exception e) {
//				log.info("查询台风预测影响标的异常：" + e.getMessage(), e);
//				e.printStackTrace();
//				throw new RuntimeException("查询台风预测影响标的异常:" + e);
//			} finally {
//				releaseResources(stat, conn, rs);
//			}
//		} else if (target.equals("LsTarget")) {
//			tableName = "WZ_CORPORELS_P";
//			try {
//				if (StringUtils.isNotBlank(tfbh)) {
//					String sql = "select * from " + tableName.trim() + " where tfbh = '" + tfbh.trim() + "'";
//					sql+=cityCodeSql;
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					stat = conn.prepareStatement(sql);
//					rs = stat.executeQuery();
//					for (; rs.next();) {
//						WzCorporeLsPVo itemp = new WzCorporeLsPVo();
//						itemp.setTfbh(rs.getString("tfbh"));
//						itemp.setCityCode(rs.getString("cityCode"));
//						itemp.setCityName(rs.getString("cityName"));
//						itemp.setTotalAmount(rs.getBigDecimal("totalAmount"));
//						itemp.setjAmount(rs.getBigDecimal("jAmount"));
//						itemp.setqAmount(rs.getBigDecimal("qAmount"));
//						itemp.setgAmount(rs.getBigDecimal("gAmount"));
//						itemp.setCenterX(rs.getBigDecimal("centerX"));
//						itemp.setCenterY(rs.getBigDecimal("centerY"));
//						itemp.setProCityFlag(rs.getString("PROCITYFLAG"));
//						itemp.setCorporeSum(rs.getBigDecimal("CORPORESUM"));
//						LsTarget.add(itemp);
//					}
//					if (!(target.isEmpty())) {
//						// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
//						Map<String, List<WzCorporeLsPVo>> targetMap = new HashMap<String, List<WzCorporeLsPVo>>();
//
//						for (WzCorporeLsPVo itemTarget : LsTarget) {
//							if (targetMap.containsKey(itemTarget.getProCityFlag())) {
//								// itemList.add(itemTarget);
//								targetMap.get(itemTarget.getProCityFlag()).add(itemTarget);
//								// targetMap.put(itemTarget.getProCityFlag(), itemList);
//							} else {
//								List<WzCorporeLsPVo> itemList = new ArrayList<WzCorporeLsPVo>();
//								itemList.add(itemTarget);
//								targetMap.put(itemTarget.getProCityFlag(), itemList);
//							}
//						}
//						ajaxResult.setData(targetMap);
//						ajaxResult.setStatus(0);
//					} else {
//						ajaxResult.setStatus(1);
//						ajaxResult.setStatusText("无台风影响标的");
//					}
//				}
//			} catch (Exception e) {
//				log.info("查询台风影响标的异常：" + e.getMessage(), e);
//				e.printStackTrace();
//				throw new RuntimeException("查询台风影响标的异常:" + e);
//			} finally {
//				releaseResources(stat, conn, rs);
//			}
//		}
//		return ajaxResult;
//	}
//
//	public AjaxResult sumAmountLs() {
//		AjaxResult ajax = new AjaxResult();
//		QueryRule queryRule = QueryRule.getInstance();
//		List<WzCorporeLs> resultSelect = new ArrayList<WzCorporeLs>();
//		resultSelect = databaseDao.findAll(WzCorporeLs.class, queryRule);
//		Map<String, ArrayList<WzCorporeLs>> map = new HashMap<String, ArrayList<WzCorporeLs>>();
//		map.put("corporeYJF", (ArrayList<WzCorporeLs>) resultSelect);
//		// ajax = sumAmount(map);
//		return ajax;
//	}
//
//	/**
//	 * 精算数据：企财险基本数据 根据标的地址信息 获取该标的点保费、费率等数据 数据单位为（万元） 费率为（百分之xxx）
//	 * 
//	 * @author 崔凤志
//	 * @param address
//	 * @return
//	 */
//	@Override
//	public AjaxResult getActuarialDataByAddress(String locationX, String locationY) {
//		AjaxResult result = new AjaxResult();
//		// 查询出该点的地址信息
//		Map<String, String> map = this.searchAddress(locationX, locationY, "");
//
//		Map<String, Object> newMap = new HashMap<String, Object>();
//		String address = "";
//		/* 当查询出的地址信息不为空，并且执行成功 */
//		if (!map.isEmpty() && "1".equals(map.get("errorFlag"))) {
//			/* 对特殊的省份进行处理 */
//			newMap = this.handleSpecialProvince(map);
//			address = newMap.get("addressCounty") + "";
//
//			AjaxResult ajaxResult = this.getActuarialData(address, "");
//			if (ajaxResult.getStatus() == 1) {
//				result.setData(ajaxResult.getData());
//				result.setDatas(newMap);
//				result.setStatus(1);
//			} else {
//				result.setStatus(2);
//			}
//			result.setStatusText(ajaxResult.getStatusText());
//
//		} else {
//			result.setStatus(2);
//			result.setStatusText("查询省市县信息失败");
//		}
//		return result;
//	}
//
//	public AjaxResult getActuarialData(String address, String businessName) {
//		AjaxResult result = new AjaxResult();
//
//		RiskMapQFormManager resp = new RiskMapQFormManager();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			String sql = "select " + " nvl(sum(calendarThisYPremium),0) as calendarThisYPremium,"
//					+ "nvl(sum(calendarThisYAmount),0) as calendarThisYAmount,"
//					+ "nvl(sum(policyThisYSumChRep),0) as policyThisYSumChRep,"
//					+ "nvl(sum(policyThisYNoChRep),0) as policyThisYNoChRep,"
//					+ "nvl(sum(policyThisYEarnPremium),0) as policyThisYEarnPremium,"
//					+ "nvl(sum(accidentThisYSumChRep),0) as accidentThisYSumChRep,"
//					+ "nvl(sum(accidentThisYNoChRep),0) as accidentThisYNoChRep,"
//					+ "nvl(sum(accidentThisYSumChRepC),0) as accidentThisYSumChRepC,"
//					+ "nvl(sum(accidentThisYNoChRepC),0) as accidentThisYNoChRepC,"
//					+ "nvl(sum(accidentThisYSumChRepOP),0) as accidentThisYSumChRepOP,"
//					+ "nvl(sum(accidentThisYNoChRepOP),0) as accidentThisYNoChRepOP,"
//					+ "nvl(sum(accidentThisYSumChRepP),0) as accidentThisYSumChRepP,"
//					+ "nvl(sum(accidentThisYNoChRepP),0) as accidentThisYNoChRepP,"
//					+ "nvl(sum(accidentThisYEarnPremium),0) as accidentThisYEarnPremium,"
//					+ "nvl(sum(policyThreeYSumChRep),0) as policyThreeYSumChRep,"
//					+ "nvl(sum(policyThreeYNoChRep),0) as policyThreeYNoChRep,"
//					+ "nvl(sum(policyThreeYEarnPremium),0) as policyThreeYEarnPremium,"
//					+ "nvl(sum(accidentThreeYEarnPremium),0) as accidentThreeYEarnPremium,"
//					+ "nvl(sum(accidentThreeYSumChRep),0) as accidentThreeYSumChRep,"
//					+ "nvl(sum(accidentThreeYNoChRep),0) as accidentThreeYNoChRep,"
//					+ "nvl(sum(accidentThreeYSumChRepC),0) as accidentThreeYSumChRepC,"
//					+ "nvl(sum(accidentThreeYNoChRepC),0) as accidentThreeYNoChRepC,"
//					+ "nvl(sum(accidentThreeYSumChRepOP),0) as accidentThreeYSumChRepOP,"
//					+ "nvl(sum(accidentThreeYNoChRepOP),0) as accidentThreeYNoChRepOP,"
//					+ "nvl(sum(accidentThreeYSumChRepP),0) as accidentThreeYSumChRepP, "
//					+ "nvl(sum(accidentThreeYNoChRepP),0) as accidentThreeYNoChRepP "
//					+ "from RiskMap_QFormManager where addressname like '" + address + "%'";
//			if (StringUtils.isNotBlank(businessName)) {
//				sql += " and businessname like '%" + businessName.trim() + "%'";
//			}
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			rs = stat.executeQuery();
//			rs.last(); // 移到最后一行
//			int currentRow = rs.getRow();
//			if (currentRow > 0) {
//				// 如果还要用结果集，就把指针再移到初始化的位置
//				rs.beforeFirst();
//				// 数据库数据转换为万元
//				for (; rs.next();) {
//					resp.setCalendarThisYPremium(
//							rs.getBigDecimal("calendarThisYPremium").divide(new BigDecimal("10000")));
//					resp.setCalendarThisYAmount(
//							rs.getBigDecimal("calendarThisYAmount").divide(new BigDecimal("10000")));
//					resp.setPolicyThisYSumChRep(
//							rs.getBigDecimal("policyThisYSumChRep").divide(new BigDecimal("10000")));
//					resp.setPolicyThisYNoChRep(rs.getBigDecimal("policyThisYNoChRep").divide(new BigDecimal("10000")));
//
//					resp.setPolicyThisYEarnPremium(
//							rs.getBigDecimal("policyThisYEarnPremium").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThisYSumChRep(
//							rs.getBigDecimal("accidentThisYSumChRep").divide(new BigDecimal("10000")));
//					resp.setAccidentThisYNoChRep(
//							rs.getBigDecimal("accidentThisYNoChRep").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThisYSumChRepC(
//							rs.getBigDecimal("accidentThisYSumChRepC").divide(new BigDecimal("10000")));
//					resp.setAccidentThisYNoChRepC(
//							rs.getBigDecimal("accidentThisYNoChRepC").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThisYSumChRepOP(
//							rs.getBigDecimal("accidentThisYSumChRepOP").divide(new BigDecimal("10000")));
//					resp.setAccidentThisYNoChRepOP(
//							rs.getBigDecimal("accidentThisYNoChRepOP").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThisYSumChRepP(
//							rs.getBigDecimal("accidentThisYSumChRepP").divide(new BigDecimal("10000")));
//					resp.setAccidentThisYNoChRepP(
//							rs.getBigDecimal("accidentThisYNoChRepP").divide(new BigDecimal("10000")));
//					resp.setAccidentThisYEarnPremium(
//							rs.getBigDecimal("accidentThisYEarnPremium").divide(new BigDecimal("10000")));
//
//					resp.setPolicyThreeYSumChRep(
//							rs.getBigDecimal("policyThreeYSumChRep").divide(new BigDecimal("10000")));
//					resp.setPolicyThreeYNoChRep(
//							rs.getBigDecimal("policyThreeYNoChRep").divide(new BigDecimal("10000")));
//					resp.setPolicyThreeYEarnPremium(
//							rs.getBigDecimal("policyThreeYEarnPremium").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThreeYEarnPremium(
//							rs.getBigDecimal("accidentThreeYEarnPremium").divide(new BigDecimal("10000")));
//					resp.setAccidentThreeYSumChRep(
//							rs.getBigDecimal("accidentThreeYSumChRep").divide(new BigDecimal("10000")));
//					resp.setAccidentThreeYNoChRep(
//							rs.getBigDecimal("accidentThreeYNoChRep").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThreeYSumChRepC(
//							rs.getBigDecimal("accidentThreeYSumChRepC").divide(new BigDecimal("10000")));
//					resp.setAccidentThreeYNoChRepC(
//							rs.getBigDecimal("accidentThreeYNoChRepC").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThreeYSumChRepOP(
//							rs.getBigDecimal("accidentThreeYSumChRepOP").divide(new BigDecimal("10000")));
//					resp.setAccidentThreeYNoChRepOP(
//							rs.getBigDecimal("accidentThreeYNoChRepOP").divide(new BigDecimal("10000")));
//
//					resp.setAccidentThreeYSumChRepP(
//							rs.getBigDecimal("accidentThreeYSumChRepP").divide(new BigDecimal("10000")));
//					resp.setAccidentThreeYNoChRepP(
//							rs.getBigDecimal("accidentThreeYNoChRepP").divide(new BigDecimal("10000")));
//				}
//
//				// 保费
//				BigDecimal calendarThisYPremium = resp.getCalendarThisYPremium();
//				// 保额
//				BigDecimal calendarThisYAmount = resp.getCalendarThisYAmount();
//				// 计算费率
//				if (calendarThisYAmount.compareTo(BigDecimal.ZERO) == 0) {
//					resp.setCalendarThisYRate(BigDecimal.ZERO);
//				} else {
//					BigDecimal calendarThisYRate = calendarThisYPremium
//							.divide(calendarThisYAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//					resp.setCalendarThisYRate(calendarThisYRate);
//				}
//
//				// 保单年当年已核赔款
//				BigDecimal policyThisYSumChRep = resp.getPolicyThisYSumChRep();
//				// 保单年当年未核赔款
//				BigDecimal policyThisYNoChRep = resp.getPolicyThisYNoChRep();
//				// 保单年当年已赚保费
//				BigDecimal policyThisYEarnPremium = resp.getPolicyThisYEarnPremium();
//				// 保单年当年已报告赔付率
//				if (policyThisYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//					resp.setPolicyThisYRePayRate(BigDecimal.ZERO);
//				} else {
//					BigDecimal sum = policyThisYSumChRep.add(policyThisYNoChRep);
//					BigDecimal policyThisYRePayRate = sum.divide(policyThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//							.multiply(new BigDecimal("100"));
//					resp.setPolicyThisYRePayRate(policyThisYRePayRate);
//				}
//
//				// 事故年当年已赚保费
//				BigDecimal accidentThisYEarnPremium = resp.getAccidentThisYEarnPremium();
//				// 事故年当年已核赔款（整体）
//				BigDecimal accidentThisYSumChRep = resp.getAccidentThisYSumChRep();
//				// 事故年当年未核赔款（整体）
//				BigDecimal accidentThisYNoChRep = resp.getAccidentThisYNoChRep();
//				// 事故年当年已核赔款（常规）
//				BigDecimal accidentThisYSumChRepC = resp.getAccidentThisYSumChRepC();
//				// 事故年当年未核赔款（常规）
//				BigDecimal accidentThisYNoChRepC = resp.getAccidentThisYNoChRepC();
//				// 事故年当年已核赔款（除大灾外大赔案）
//				BigDecimal accidentThisYSumChRepOP = resp.getAccidentThisYSumChRepOP();
//				// 事故年当年未核赔款（除大灾外大赔案）
//				BigDecimal accidentThisYNoChRepOP = resp.getAccidentThisYNoChRepOP();
//				// 事故年当年已核赔款（大灾）
//				BigDecimal accidentThisYSumChRepP = resp.getAccidentThisYSumChRepP();
//				BigDecimal accidentThisYNoChRepP = resp.getAccidentThisYNoChRepP();
//
//				if (accidentThisYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//					resp.setAccidentThisYRePayRate(BigDecimal.ZERO);
//					resp.setAccidentThisYRePayRateC(BigDecimal.ZERO);
//					resp.setAccidentThisYRePayRateOP(BigDecimal.ZERO);
//					resp.setAccidentThisYRePayRateP(BigDecimal.ZERO);
//				} else {
//					// 事故年当年已报告赔付率 --整体
//					BigDecimal sum = accidentThisYSumChRep.add(accidentThisYNoChRep);
//					BigDecimal accidentThisYRePayRate = sum
//							.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThisYRePayRate(accidentThisYRePayRate);
//					// 事故年当年已报告赔付率--常规
//					BigDecimal sumC = accidentThisYSumChRepC.add(accidentThisYNoChRepC);
//					BigDecimal accidentThisYRePayRateC = sumC
//							.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThisYRePayRateC(accidentThisYRePayRateC);
//					// 事故年当年已报告赔付率--除大灾外大赔案
//					BigDecimal sumOP = accidentThisYSumChRepOP.add(accidentThisYNoChRepOP);
//					BigDecimal accidentThisYRePayRateOP = sumOP
//							.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThisYRePayRateOP(accidentThisYRePayRateOP);
//					// 事故年当年已报告赔付率--大灾
//					BigDecimal sumP = accidentThisYSumChRepP.add(accidentThisYNoChRepP);
//					BigDecimal accidentThisYRePayRateP = sumP
//							.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThisYRePayRateP(accidentThisYRePayRateP);
//				}
//
//				// 保单年近三年已核赔款
//				BigDecimal policyThreeYSumChRep = resp.getPolicyThreeYSumChRep();
//				// 保单年近三年未核赔款
//				BigDecimal policyThreeYNoChRep = resp.getPolicyThreeYNoChRep();
//				// 保单年近三年已赚保费
//				BigDecimal policyThreeYEarnPremium = resp.getPolicyThreeYEarnPremium();
//				// 保单年近三年已报告赔付率
//				if (policyThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//					resp.setPolicyThreeYRePayRate(BigDecimal.ZERO);
//				} else {
//					BigDecimal sum = policyThreeYSumChRep.add(policyThreeYNoChRep);
//					BigDecimal policyThreeYRePayRate = sum.divide(policyThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//							.multiply(new BigDecimal("100"));
//					resp.setPolicyThreeYRePayRate(policyThreeYRePayRate);
//				}
//
//				// 事故年近三年已赚保费
//				BigDecimal accidentThreeYEarnPremium = resp.getAccidentThreeYEarnPremium();
//				// 事故年近三年已核赔款（整体）
//				BigDecimal accidentThreeYSumChRep = resp.getAccidentThreeYSumChRep();
//				// 事故年近三年未核赔款（整体）
//				BigDecimal accidentThreeYNoChRep = resp.getAccidentThreeYNoChRep();
//				// 事故年近三年已核赔款（常规）
//				BigDecimal accidentThreeYSumChRepC = resp.getAccidentThreeYSumChRepC();
//				// 事故年近三年未核赔款（常规）
//				BigDecimal accidentThreeYNoChRepC = resp.getAccidentThreeYNoChRepC();
//				// 事故年近三年已核赔款（除大灾大赔案）
//				BigDecimal accidentThreeYSumChRepOP = resp.getAccidentThreeYSumChRepOP();
//				// 事故年近三年未核赔款（除大灾大赔案）
//				BigDecimal accidentThreeYNoChRepOP = resp.getAccidentThreeYNoChRepOP();
//				// 事故年近三年累计已核赔款(大灾)
//				BigDecimal accidentThreeYSumChRepP = resp.getAccidentThreeYSumChRepP();
//				// 事故年近三年累计未核赔款(大灾)
//				BigDecimal accidentThreeYNoChRepP = resp.getAccidentThreeYNoChRepP();
//
//				if (accidentThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//					resp.setAccidentThreeYRePayRate(BigDecimal.ZERO);
//					resp.setAccidentThreeYRePayRateC(BigDecimal.ZERO);
//					resp.setAccidentThreeYRePayRateOP(BigDecimal.ZERO);
//					resp.setAccidentThreeYRePayRateP(BigDecimal.ZERO);
//				} else {
//					// 事故年近三年已报告赔付率--整体
//					BigDecimal sum = accidentThreeYSumChRep.add(accidentThreeYNoChRep);
//					BigDecimal accidentThreeYRePayRate = sum
//							.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThreeYRePayRate(accidentThreeYRePayRate);
//					// 事故年近三年已报告赔付率--常规
//					BigDecimal sumC = accidentThreeYSumChRepC.add(accidentThreeYNoChRepC);
//					BigDecimal accidentThreeYRePayRateC = sumC
//							.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThreeYRePayRateC(accidentThreeYRePayRateC);
//					// 事故年近三年已报告赔付率--除大灾大赔案
//					BigDecimal sumOP = accidentThreeYSumChRepOP.add(accidentThreeYNoChRepOP);
//					BigDecimal accidentThreeYRePayRateOP = sumOP
//							.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThreeYRePayRateOP(accidentThreeYRePayRateOP);
//					// 事故年近三年已报告赔付率--大灾
//					BigDecimal sumP = accidentThreeYSumChRepP.add(accidentThreeYNoChRepP);
//					BigDecimal accidentThreeYRePayRateP = sumP
//							.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//							.multiply(new BigDecimal("100"));
//					resp.setAccidentThreeYRePayRateP(accidentThreeYRePayRateP);
//				}
//				result.setData(resp);
//				result.setStatus(1);
//				result.setStatusText("查询企财险基本数据成功！");
//			} else {
//				result.setStatus(2);
//				result.setStatusText("查询企财险基本数据为空！");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setStatus(2);
//			result.setStatusText("查询企财险异常！");
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return result;
//	}
//
//	/**
//	 * 精算数据：企财险详细数据 根据标的地址信息 获取该标的点保费、费率等详细数据
//	 * 
//	 * @author 崔凤志
//	 * @param address
//	 * @param pageNo
//	 * @param pageSize
//	 */
//	public AjaxResult getActuarialDataInfoByAddress(MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//
//		List<RiskMapQFormManager> resultList = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//
//		// 进行分页sql语句的拼接
//		String pageSql = this.handleSplitSql(mapRequestInfoVo, "Q");
//		// 组织查询总数的sql语句
//		String totalSql = this.totalCountSql(mapRequestInfoVo, "Q");
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(pageSql);
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				RiskMapQFormManager resp = this.calculationActuarialDataInfoForQ(rs);
//				resultList.add(resp);
//			}
//			Map<String, Object> mapObject = this.slaveJdbcTemplate.queryForMap(totalSql);
//			ajaxResult.setData(resultList);
//			ajaxResult.setDatas(mapObject);
//			ajaxResult.setStatus(1);
//			ajaxResult.setStatusText("标的查询企财险数据详情查询成功！");
//		} catch (Exception e) {
//			ajaxResult.setStatus(2);
//			ajaxResult.setStatusText("标的查询企财险数据详情查询失败！");
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * 精算数据：标的查询 精算数据 企财险数据详细计算
//	 * 
//	 * @param rs
//	 * @return
//	 */
//	private RiskMapQFormManager calculationActuarialDataInfoForQ(ResultSet rs) {
//		RiskMapQFormManager resp = new RiskMapQFormManager();
//		try {
//			resp.setAddressName(rs.getString("addressName"));
//
//			if ((rs.getString("secondLevelProduct").indexOf("、")) != -1) {
//				String secondLevelProduct = rs.getString("secondLevelProduct").split("、")[1];
//				resp.setSecondLevelProduct(secondLevelProduct);
//			} else {
//				resp.setSecondLevelProduct(rs.getString("secondLevelProduct"));
//			}
//
//			resp.setBusinessName(rs.getString("businessName"));
//			resp.setConisBeforeAmountSection(rs.getString("conisBeforeAmountSection"));
//			resp.setStockScaleSection(rs.getString("stockScaleSection"));
//
//			resp.setCalendarThisYPremium(rs.getBigDecimal("calendarThisYPremium").divide(new BigDecimal("10000")));// 日历年当年签单保费
//			resp.setCalendarThisYAmount(rs.getBigDecimal("calendarThisYAmount").divide(new BigDecimal("10000")));// 日历年当年保额
//
//			resp.setCalendarThreeYPremium(rs.getBigDecimal("calendarThreeYPremium").divide(new BigDecimal("10000")));// 日历年近三年签单保费
//			resp.setCalendarYAmount(rs.getBigDecimal("calendarYAmount").divide(new BigDecimal("10000")));// 日历年近三年保额
//
//			resp.setPolicyThisYSumChRep(rs.getBigDecimal("policyThisYSumChRep").divide(new BigDecimal("10000")));// 保单年当年已核赔款
//			resp.setPolicyThisYNoChRep(rs.getBigDecimal("policyThisYNoChRep").divide(new BigDecimal("10000")));// 保单年当年未核赔款
//
//			resp.setPolicyThisYEarnPremium(rs.getBigDecimal("policyThisYEarnPremium").divide(new BigDecimal("10000")));// 保单年当年已赚保费
//			resp.setAccidentThisYSumChRep(rs.getBigDecimal("accidentThisYSumChRep").divide(new BigDecimal("10000")));// 事故年当年累计已核赔款(整体)
//			resp.setAccidentThisYNoChRep(rs.getBigDecimal("accidentThisYNoChRep").divide(new BigDecimal("10000")));// 事故年当年累计未核赔款(整体)
//			resp.setAccidentThisYSumChRepC(rs.getBigDecimal("accidentThisYSumChRepC").divide(new BigDecimal("10000")));// 事故年当年累计已核赔款(常规)
//			resp.setAccidentThisYNoChRepC(rs.getBigDecimal("accidentThisYNoChRepC").divide(new BigDecimal("10000")));// 事故年当年累计未核赔款(常规)
//
//			resp.setAccidentThisYSumChRepOP(
//					rs.getBigDecimal("accidentThisYSumChRepOP").divide(new BigDecimal("10000")));// 已核
//			resp.setAccidentThisYNoChRepOP(rs.getBigDecimal("accidentThisYNoChRepOP").divide(new BigDecimal("10000")));// 未核
//			resp.setAccidentThisYSumChRepP(rs.getBigDecimal("accidentThisYSumChRepP").divide(new BigDecimal("10000")));
//			resp.setAccidentThisYNoChRepP(rs.getBigDecimal("accidentThisYNoChRepP").divide(new BigDecimal("10000")));
//			resp.setAccidentThisYEarnPremium(
//					rs.getBigDecimal("accidentThisYEarnPremium").divide(new BigDecimal("10000")));// 事故年当年累计已赚保费
//
//			resp.setPolicyThreeYSumChRep(rs.getBigDecimal("policyThreeYSumChRep").divide(new BigDecimal("10000")));// 保单年近三年已核赔款
//			resp.setPolicyThreeYNoChRep(rs.getBigDecimal("policyThreeYNoChRep").divide(new BigDecimal("10000")));// 保单年近三年未核赔款
//			resp.setPolicyThreeYEarnPremium(
//					rs.getBigDecimal("policyThreeYEarnPremium").divide(new BigDecimal("10000")));// 保单年近三年已赚保费
//
//			resp.setAccidentThreeYEarnPremium(
//					rs.getBigDecimal("accidentThreeYEarnPremium").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRep(rs.getBigDecimal("accidentThreeYSumChRep").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRep(rs.getBigDecimal("accidentThreeYNoChRep").divide(new BigDecimal("10000")));
//
//			resp.setAccidentThreeYSumChRepC(
//					rs.getBigDecimal("accidentThreeYSumChRepC").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRepC(rs.getBigDecimal("accidentThreeYNoChRepC").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRepOP(
//					rs.getBigDecimal("accidentThreeYSumChRepOP").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRepOP(
//					rs.getBigDecimal("accidentThreeYNoChRepOP").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRepP(
//					rs.getBigDecimal("accidentThreeYSumChRepP").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRepP(rs.getBigDecimal("accidentThreeYNoChRepP").divide(new BigDecimal("10000")));
//
//			// 日历年当年费率
//			BigDecimal calendarThisYPremium = resp.getCalendarThisYPremium();
//			BigDecimal calendarThisYAmount = resp.getCalendarThisYAmount();
//			if (calendarThisYAmount.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setCalendarThisYRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal calendarThisYRate = calendarThisYPremium
//						.divide(calendarThisYAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setCalendarThisYRate(calendarThisYRate);
//			}
//
//			// 日历年近三年费率
//			BigDecimal calendarThreeYPremium = resp.getCalendarThreeYPremium();
//			BigDecimal CalendarThreeYAmount = resp.getCalendarYAmount();
//			if (CalendarThreeYAmount.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setCalendarThreeYRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal calendarThreeYRate = calendarThreeYPremium
//						.divide(CalendarThreeYAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setCalendarThreeYRate(calendarThreeYRate);
//			}
//
//			// 保单年当年事故已报告赔付率
//			BigDecimal policyThisYSumChRep = resp.getPolicyThisYSumChRep();
//			BigDecimal policyThisYNoChRep = resp.getPolicyThisYNoChRep();
//			BigDecimal policyThisYEarnPremium = resp.getPolicyThisYEarnPremium();
//			if (policyThisYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setPolicyThisYRePayRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal sum = policyThisYSumChRep.add(policyThisYNoChRep);
//				BigDecimal policyThisYRePayRate = sum.divide(policyThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setPolicyThisYRePayRate(policyThisYRePayRate);
//			}
//
//			// 保单年近三年事故已报告赔付率
//			BigDecimal policyThreeYSumChRep = resp.getPolicyThreeYSumChRep();
//			BigDecimal policyThreeYNoChRep = resp.getPolicyThreeYNoChRep();
//			BigDecimal policyThreeYEarnPremium = resp.getPolicyThreeYEarnPremium();
//			if (policyThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setPolicyThreeYRePayRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal sum = policyThreeYSumChRep.add(policyThreeYNoChRep);
//				BigDecimal policyThreeYRePayRate = sum.divide(policyThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setPolicyThreeYRePayRate(policyThreeYRePayRate);
//			}
//
//			// 事故年当年已报告赔付率
//			BigDecimal accidentThisYEarnPremium = resp.getAccidentThisYEarnPremium();
//			BigDecimal accidentThisYSumChRep = resp.getAccidentThisYSumChRep();// 整体
//			BigDecimal accidentThisYNoChRep = resp.getAccidentThisYNoChRep();// 整体 未核赔款
//			BigDecimal accidentThisYSumChRepC = resp.getAccidentThisYSumChRepC();// 常规
//			BigDecimal accidentThisYNoChRepC = resp.getAccidentThisYNoChRepC();// 常规 未核赔款
//			BigDecimal accidentThisYSumChRepOP = resp.getAccidentThisYSumChRepOP();// 除大灾大赔
//			BigDecimal accidentThisYNoChRepOP = resp.getAccidentThisYNoChRepOP();// 除大灾大赔 未核赔款
//			BigDecimal accidentThisYSumChRepP = resp.getAccidentThisYSumChRepP();// 大灾
//			BigDecimal accidentThisYNoChRepP = resp.getAccidentThisYNoChRepP();// 大灾 未核赔款
//			if (accidentThisYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setAccidentThisYRePayRate(BigDecimal.ZERO);
//				resp.setAccidentThisYRePayRateC(BigDecimal.ZERO);
//				resp.setAccidentThisYRePayRateOP(BigDecimal.ZERO);
//				resp.setAccidentThisYRePayRateP(BigDecimal.ZERO);
//			} else {
//				// 事故年当年已报告赔付率（整体）
//				BigDecimal sum1 = accidentThisYSumChRep.add(accidentThisYNoChRep);
//				BigDecimal accidentThisYRePayRate = sum1.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRate(accidentThisYRePayRate);
//				// 事故年当年已报告赔付率（常规）
//				BigDecimal sum2 = accidentThisYSumChRepC.add(accidentThisYNoChRepC);
//				BigDecimal accidentThisYRePayRateC = sum2.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRateC(accidentThisYRePayRateC);
//				// 事故年当年已报告赔付率（除大灾大赔案）
//				BigDecimal sum3 = accidentThisYSumChRepOP.add(accidentThisYNoChRepOP);
//				BigDecimal accidentThisYRePayRateOP = sum3.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRateOP(accidentThisYRePayRateOP);
//				// 事故年当年已报告赔付率（大灾）
//				BigDecimal sum4 = accidentThisYSumChRepP.add(accidentThisYNoChRepP);
//				BigDecimal accidentThisYRePayRateP = sum4.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRateP(accidentThisYRePayRateP);
//			}
//
//			// 事故年近三年已报告赔付率
//			BigDecimal accidentThreeYEarnPremium = resp.getAccidentThreeYEarnPremium();// 已赚保费
//			BigDecimal accidentThreeYSumChRep = resp.getAccidentThreeYSumChRep();// 已核赔款 整体
//			BigDecimal accidentThreeYNoChRep = resp.getAccidentThreeYNoChRep();// 未核赔款 整体
//			BigDecimal accidentThreeYSumChRepC = resp.getAccidentThreeYSumChRepC();// 已核赔款 常规
//			BigDecimal accidentThreeYNoChRepC = resp.getAccidentThreeYNoChRepC();// 未核赔款 常规
//			BigDecimal accidentThreeYSumChRepOP = resp.getAccidentThreeYSumChRepOP();// 已核赔款 除大灾大赔
//			BigDecimal accidentThreeYNoChRepOP = resp.getAccidentThreeYNoChRepOP();// 未核赔款 除大灾大赔
//			BigDecimal accidentThreeYSumChRepP = resp.getAccidentThreeYSumChRepP();// 已核赔款 大灾
//			BigDecimal accidentThreeYNoChRepP = resp.getAccidentThreeYNoChRepP();// 未核赔款 大灾
//			if (accidentThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setAccidentThreeYRePayRate(BigDecimal.ZERO);
//				resp.setAccidentThreeYRePayRateC(BigDecimal.ZERO);
//				resp.setAccidentThreeYRePayRateOP(BigDecimal.ZERO);
//				resp.setAccidentThreeYRePayRateP(BigDecimal.ZERO);
//			} else {
//				// 事故年近三年已报告赔付率（整体）
//				BigDecimal sum = accidentThreeYSumChRep.add(accidentThreeYNoChRep);
//				BigDecimal accidentThreeYRePayRate = sum.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRate(accidentThreeYRePayRate);
//				// 事故年近三年已报告赔付率（常规）
//				BigDecimal sumC = accidentThreeYSumChRepC.add(accidentThreeYNoChRepC);
//				BigDecimal accidentThreeYRePayRateC = sumC
//						.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRateC(accidentThreeYRePayRateC);
//				// 事故年近三年已报告赔付率（除大灾大赔）
//				BigDecimal sumOP = accidentThreeYSumChRepOP.add(accidentThreeYNoChRepOP);
//				BigDecimal accidentThreeYRePayRateOP = sumOP
//						.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRateOP(accidentThreeYRePayRateOP);
//				// 事故年近三年已报告赔付率（除大灾）
//				BigDecimal sumP = accidentThreeYSumChRepP.add(accidentThreeYNoChRepP);
//				BigDecimal accidentThreeYRePayRateP = sumP
//						.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRateP(accidentThreeYRePayRateP);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resp;
//	}
//
//	/**
//	 * 精算数据：工程险基本数据 根据标的地址信息 获取该标的点保费、费率等基本数据
//	 * 
//	 * @author 崔凤志
//	 * @param address
//	 */
//	@Override
//	public AjaxResult getGManagerActuarialDataByAdd(MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult result = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String address = "";
//		// 处理工程险特殊省份信息
//		address = handleSpecialAddress(mapRequestInfoVo);
//		// 省级地址不为空
//		if (StringUtils.isNotBlank(address)) {
//			// address = address.split("省")[0];
//			try {
//				String sql = "select " + "nvl(sum(calendarThisYPremium),0) as calendarThisYPremium,"
//						+ "nvl(sum(calendarThisYAmount),0) as calendarThisYAmount,"
//						+ "nvl(sum(policyThreeYSumChRep),0) as policyThreeYSumChRep,"
//						+ "nvl(sum(policyThreeYNoChRep),0) as policyThreeYNoChRep,"
//						+ "nvl(sum(policyThreeYEarnPremium),0) as policyThreeYEarnPremium,"
//						+ "nvl(sum(accidentThreeYEarnPremium),0) as accidentThreeYEarnPremium,"
//						+ "nvl(sum(accidentThreeYSumChRep),0) as accidentThreeYSumChRep,"
//						+ "nvl(sum(accidentThreeYNoChRep),0) as accidentThreeYNoChRep,"
//						+ "nvl(sum(accidentThreeYSumChRepC),0) as accidentThreeYSumChRepC,"
//						+ "nvl(sum(accidentThreeYNoChRepC),0) as accidentThreeYNoChRepC,"
//						+ "nvl(sum(accidentThreeYSumChRepOP),0) as accidentThreeYSumChRepOP,"
//						+ "nvl(sum(accidentThreeYNoChRepOP),0) as accidentThreeYNoChRepOP,"
//						+ "nvl(sum(accidentThreeYSumChRepP),0) as accidentThreeYSumChRepP, "
//						+ "nvl(sum(accidentThreeYNoChRepP),0) as accidentThreeYNoChRepP "
//						+ "from RiskMap_GFormManager where addressname like '" + address + "%'";
//				RiskMapRegionVo riskMapRegionVo = mapRequestInfoVo.getMapAddress();
//				if (riskMapRegionVo != null) {
//					String businessName = riskMapRegionVo.getBusinessName();
//					if (StringUtils.isNotBlank(businessName)) {
//						sql += " and enginecategory like '%" + businessName.trim() + "%'";
//					}
//				}
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				stat = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
//						ResultSet.CONCUR_READ_ONLY);
//				rs = stat.executeQuery();
//				rs.last(); // 移到最后一行
//				int currentRow = rs.getRow();
//				// 数据库数据转换为万元
//				if (currentRow > 0) {
//					// 如果还要用结果集，就把指针再移到初始化的位置
//					rs.beforeFirst();
//					RiskMapGFormManager resp = new RiskMapGFormManager();
//					for (; rs.next();) {
//						resp.setCalendarThisYPremium(
//								rs.getBigDecimal("calendarThisYPremium").divide(new BigDecimal("10000")));
//						resp.setCalendarThisYAmount(
//								rs.getBigDecimal("calendarThisYAmount").divide(new BigDecimal("10000")));
//						resp.setPolicyThreeYSumChRep(
//								rs.getBigDecimal("policyThreeYSumChRep").divide(new BigDecimal("10000")));
//						resp.setPolicyThreeYNoChRep(
//								rs.getBigDecimal("policyThreeYNoChRep").divide(new BigDecimal("10000")));
//
//						resp.setPolicyThreeYEarnPremium(
//								rs.getBigDecimal("policyThreeYEarnPremium").divide(new BigDecimal("10000")));
//						resp.setAccidentThreeYEarnPremium(
//								rs.getBigDecimal("accidentThreeYEarnPremium").divide(new BigDecimal("10000")));
//
//						resp.setAccidentThreeYSumChRep(
//								rs.getBigDecimal("accidentThreeYSumChRep").divide(new BigDecimal("10000")));
//						resp.setAccidentThreeYNoChRep(
//								rs.getBigDecimal("accidentThreeYNoChRep").divide(new BigDecimal("10000")));
//
//						resp.setAccidentThreeYSumChRepC(
//								rs.getBigDecimal("accidentThreeYSumChRepC").divide(new BigDecimal("10000")));
//						resp.setAccidentThreeYNoChRepC(
//								rs.getBigDecimal("accidentThreeYNoChRepC").divide(new BigDecimal("10000")));
//
//						resp.setAccidentThreeYSumChRepOP(
//								rs.getBigDecimal("accidentThreeYSumChRepOP").divide(new BigDecimal("10000")));
//						resp.setAccidentThreeYNoChRepOP(
//								rs.getBigDecimal("accidentThreeYNoChRepOP").divide(new BigDecimal("10000")));
//
//						resp.setAccidentThreeYSumChRepP(
//								rs.getBigDecimal("accidentThreeYSumChRepP").divide(new BigDecimal("10000")));
//						resp.setAccidentThreeYNoChRepP(
//								rs.getBigDecimal("accidentThreeYNoChRepP").divide(new BigDecimal("10000")));
//					}
//					// 保费
//					BigDecimal calendarThisYPremium = resp.getCalendarThisYPremium();
//					// 保额
//					BigDecimal calendarThisYAmount = resp.getCalendarThisYAmount();
//					// 计算费率
//					if (calendarThisYAmount.compareTo(BigDecimal.ZERO) == 0) {
//						resp.setCalendarThisYRate(BigDecimal.ZERO);
//					} else {
//						BigDecimal calendarThisYRate = calendarThisYPremium
//								.divide(calendarThisYAmount, 4, BigDecimal.ROUND_HALF_UP)
//								.multiply(new BigDecimal("100"));
//						resp.setCalendarThisYRate(calendarThisYRate);
//					}
//
//					// 保单年近三年已核赔款
//					BigDecimal policyThreeYSumChRep = resp.getPolicyThreeYSumChRep();
//					// 保单年近三年未核赔款
//					BigDecimal policyThreeYNoChRep = resp.getPolicyThreeYNoChRep();
//					// 保单年近三年已赚保费
//					BigDecimal policyThreeYEarnPremium = resp.getPolicyThreeYEarnPremium();
//					// 保单年近三年已报告赔付率
//					if (policyThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//						resp.setPolicyThreeYRePayRate(BigDecimal.ZERO);
//					} else {
//						BigDecimal sum = policyThreeYSumChRep.add(policyThreeYNoChRep);
//						BigDecimal policyThreeYRePayRate = sum
//								.divide(policyThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//								.multiply(new BigDecimal("100"));
//						resp.setPolicyThreeYRePayRate(policyThreeYRePayRate);
//					}
//
//					// 事故年近三年已赚保费
//					BigDecimal accidentThreeYEarnPremium = resp.getAccidentThreeYEarnPremium();
//					// 事故年近三年已核赔款（整体）
//					BigDecimal accidentThreeYSumChRep = resp.getAccidentThreeYSumChRep();
//					// 事故年近三年未核赔款（整体）
//					BigDecimal accidentThreeYNoChRep = resp.getAccidentThreeYNoChRep();
//					// 事故年近三年已核赔款（常规）
//					BigDecimal accidentThreeYSumChRepC = resp.getAccidentThreeYSumChRepC();
//					// 事故年近三年未核赔款（常规）
//					BigDecimal accidentThreeYNoChRepC = resp.getAccidentThreeYNoChRepC();
//					// 事故年近三年已核赔款（除大灾大赔案）
//					BigDecimal accidentThreeYSumChRepOP = resp.getAccidentThreeYSumChRepOP();
//					// 事故年近三年未核赔款（除大灾大赔案）
//					BigDecimal accidentThreeYNoChRepOP = resp.getAccidentThreeYNoChRepOP();
//					// 事故年近三年累计已核赔款(大灾)
//					BigDecimal accidentThreeYSumChRepP = resp.getAccidentThreeYSumChRepP();
//					// 事故年近三年累计未核赔款(大灾)
//					BigDecimal accidentThreeYNoChRepP = resp.getAccidentThreeYNoChRepP();
//					if (accidentThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//						resp.setAccidentThreeYRePayRate(BigDecimal.ZERO);
//						resp.setAccidentThreeYRePayRateC(BigDecimal.ZERO);
//						resp.setAccidentThreeYRePayRateOP(BigDecimal.ZERO);
//						resp.setAccidentThreeYRePayRateP(BigDecimal.ZERO);
//					} else {
//						// 事故年近三年已报告赔付率--整体
//						BigDecimal sum = accidentThreeYSumChRep.add(accidentThreeYNoChRep);
//						BigDecimal accidentThreeYRePayRate = sum
//								.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//								.multiply(new BigDecimal("100"));
//						resp.setAccidentThreeYRePayRate(accidentThreeYRePayRate);
//						// 事故年近三年已报告赔付率--常规
//						BigDecimal sum1 = accidentThreeYSumChRepC.add(accidentThreeYNoChRepC);
//						BigDecimal accidentThreeYRePayRateC = sum1
//								.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//								.multiply(new BigDecimal("100"));
//						resp.setAccidentThreeYRePayRateC(accidentThreeYRePayRateC);
//						// 事故年近三年已报告赔付率--除大灾大赔案
//						BigDecimal sum2 = accidentThreeYSumChRepOP.add(accidentThreeYNoChRepOP);
//						BigDecimal accidentThreeYRePayRateOP = sum2
//								.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//								.multiply(new BigDecimal("100"));
//						resp.setAccidentThreeYRePayRateOP(accidentThreeYRePayRateOP);
//						// 事故年近三年已报告赔付率--大灾
//						BigDecimal sum3 = accidentThreeYSumChRepP.add(accidentThreeYNoChRepP);
//						BigDecimal accidentThreeYRePayRateP = sum3
//								.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_DOWN)
//								.multiply(new BigDecimal("100"));
//						resp.setAccidentThreeYRePayRateP(accidentThreeYRePayRateP);
//					}
//					result.setData(resp);
//					result.setStatus(1);
//					result.setStatusText("查询工程险基本数据成功！");
//				} else {
//					result.setStatus(3);
//					result.setStatusText("查询工程险基本数据为空！");
//				}
//			} catch (Exception e) {
//				result.setStatus(2);
//				result.setStatusText("查询工程险基本数据失败！");
//				e.printStackTrace();
//			} finally {
//				releaseResources(stat, conn, rs);
//			}
//		} else {
//			result.setStatus(4);
//			result.setStatusText("组织省级地址为空！");
//		}
//		return result;
//	}
//
//	/**
//	 * 精算数据：工程险详细数据 根据标的地址信息 获取该标的点保费、费率等详细数据
//	 * 
//	 * @author 崔凤志
//	 * @param address
//	 * @param pageNo
//	 * @param pageSize
//	 */
//	@Override
//	public AjaxResult getGManagerActuarialDataInfoByAdd(MapRequestInfoVo mapRequestInfoVo) {
//		AjaxResult ajaxResult = new AjaxResult();
//
//		List<RiskMapGFormManager> resultList = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//
//		// 查询省级别数据
//		String proviceSql = this.handleSplitSql(mapRequestInfoVo, "G");
//		// 组织查询总数的sql语句
//		String totalSql = this.totalCountSql(mapRequestInfoVo, "G");
//
//		try {
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(proviceSql.toString());
//			rs = stat.executeQuery();
//			for (; rs.next();) {
//				RiskMapGFormManager resp = this.calculationActuarialDataInfoForG(rs);
//				resultList.add(resp);
//			}
//			Map<String, Object> mapObject = this.slaveJdbcTemplate.queryForMap(totalSql);
//			ajaxResult.setData(resultList);
//			ajaxResult.setDatas(mapObject);
//			ajaxResult.setStatus(1);
//			ajaxResult.setStatusText("标的查询工程险数据详情查询成功！");
//		} catch (Exception e) {
//			ajaxResult.setStatus(2);
//			ajaxResult.setStatusText("标的查询工程险数据详情查询失败！");
//			e.printStackTrace();
//		} finally {
//			releaseResources(stat, conn, rs);
//		}
//		return ajaxResult;
//	}
//
//	/**
//	 * 精算数据：工程数据处理
//	 * 
//	 * @author 崔凤志
//	 * @param ResultSet
//	 */
//	private RiskMapGFormManager calculationActuarialDataInfoForG(ResultSet rs) {
//		RiskMapGFormManager resp = new RiskMapGFormManager();
//		try {
//			resp.setEngineCategory(rs.getString("engineCategory"));
//			resp.setAddressName(rs.getString("addressName"));
//
//			resp.setCalendarThisYPremium(rs.getBigDecimal("calendarThisYPremium").divide(new BigDecimal("10000")));// 日历年当年签单保费
//			resp.setCalendarThisYAmount(rs.getBigDecimal("calendarThisYAmount").divide(new BigDecimal("10000")));// 日历年当年保额
//
//			resp.setCalendarThreeYPremium(rs.getBigDecimal("calendarThreeYPremium").divide(new BigDecimal("10000")));// 日历年近三年签单保费
//			resp.setCalendarYAmount(rs.getBigDecimal("calendarYAmount").divide(new BigDecimal("10000")));// 日历年近三年保额
//
//			resp.setPolicyThisYSumChRep(rs.getBigDecimal("policyThisYSumChRep").divide(new BigDecimal("10000")));// 保单年当年已核赔款
//			resp.setPolicyThisYNoChRep(rs.getBigDecimal("policyThisYNoChRep").divide(new BigDecimal("10000")));
//			resp.setPolicyThisYEarnPremium(rs.getBigDecimal("policyThisYEarnPremium").divide(new BigDecimal("10000")));// 保单年当年已赚保费
//
//			resp.setAccidentThisYSumChRep(rs.getBigDecimal("accidentThisYSumChRep").divide(new BigDecimal("10000")));// 事故年当年累计已核赔款(整体)
//			resp.setAccidentThisYNoChRep(rs.getBigDecimal("accidentThisYNoChRep").divide(new BigDecimal("10000")));
//			resp.setAccidentThisYSumChRepC(rs.getBigDecimal("accidentThisYSumChRepC").divide(new BigDecimal("10000")));// 事故年当年累计已核赔款(常规)
//			resp.setAccidentThisYNoChRepC(rs.getBigDecimal("accidentThisYNoChRepC").divide(new BigDecimal("10000")));
//			resp.setAccidentThisYSumChRepOP(
//					rs.getBigDecimal("accidentThisYSumChRepOP").divide(new BigDecimal("10000")));// 事故年当年累计已核赔款(除大灾大赔)
//			resp.setAccidentThisYNoChRepOP(rs.getBigDecimal("accidentThisYNoChRepOP").divide(new BigDecimal("10000")));
//			resp.setAccidentThisYSumChRepP(rs.getBigDecimal("accidentThisYSumChRepP").divide(new BigDecimal("10000")));// 事故年当年累计已核赔款(大灾)
//			resp.setAccidentThisYNoChRepP(rs.getBigDecimal("accidentThisYNoChRepP").divide(new BigDecimal("10000")));
//			resp.setAccidentThisYEarnPremium(
//					rs.getBigDecimal("accidentThisYEarnPremium").divide(new BigDecimal("10000")));// 事故年当年累计已赚保费
//
//			resp.setPolicyThreeYSumChRep(rs.getBigDecimal("policyThreeYSumChRep").divide(new BigDecimal("10000")));// 保单年近三年已核赔款
//			resp.setPolicyThreeYNoChRep(rs.getBigDecimal("policyThreeYNoChRep").divide(new BigDecimal("10000"))); // 保单年近三年未核估计赔款
//			resp.setPolicyThreeYEarnPremium(
//					rs.getBigDecimal("policyThreeYEarnPremium").divide(new BigDecimal("10000")));// 保单年近三年已赚保费
//
//			resp.setAccidentThreeYEarnPremium(
//					rs.getBigDecimal("accidentThreeYEarnPremium").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRep(rs.getBigDecimal("accidentThreeYSumChRep").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRep(rs.getBigDecimal("accidentThreeYNoChRep").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRepC(
//					rs.getBigDecimal("accidentThreeYSumChRepC").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRepC(rs.getBigDecimal("accidentThreeYNoChRepC").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRepOP(
//					rs.getBigDecimal("accidentThreeYSumChRepOP").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRepOP(
//					rs.getBigDecimal("accidentThreeYNoChRepOP").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYSumChRepP(
//					rs.getBigDecimal("accidentThreeYSumChRepP").divide(new BigDecimal("10000")));
//			resp.setAccidentThreeYNoChRepP(rs.getBigDecimal("accidentThreeYNoChRepP").divide(new BigDecimal("10000")));
//
//			// 日历年当年费率
//			BigDecimal calendarThisYPremium = resp.getCalendarThisYPremium();
//			BigDecimal calendarThisYAmount = resp.getCalendarThisYAmount();
//			if (calendarThisYAmount.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setCalendarThisYRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal calendarThisYRate = calendarThisYPremium
//						.divide(calendarThisYAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setCalendarThisYRate(calendarThisYRate);
//			}
//
//			// 日历年近三年费率
//			BigDecimal calendarThreeYPremium = resp.getCalendarThreeYPremium();
//			BigDecimal CalendarThreeYAmount = resp.getCalendarYAmount();
//			if (CalendarThreeYAmount.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setCalendarThreeYRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal calendarThreeYRate = calendarThreeYPremium
//						.divide(CalendarThreeYAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setCalendarThreeYRate(calendarThreeYRate);
//			}
//
//			// 保单年当年事故已报告赔付率
//			BigDecimal policyThisYSumChRep = resp.getPolicyThisYSumChRep();
//			BigDecimal policyThisYNoChRep = resp.getPolicyThisYNoChRep();
//			BigDecimal policyThisYEarnPremium = resp.getPolicyThisYEarnPremium();
//			if (policyThisYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setPolicyThisYRePayRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal sum = policyThisYSumChRep.add(policyThisYNoChRep);
//				BigDecimal policyThisYRePayRate = sum.divide(policyThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setPolicyThisYRePayRate(policyThisYRePayRate);
//			}
//
//			// 保单年近三年事故已报告赔付率
//			BigDecimal policyThreeYSumChRep = resp.getPolicyThreeYSumChRep();
//			BigDecimal policyThreeYNoChRep = resp.getPolicyThreeYNoChRep();
//			BigDecimal policyThreeYEarnPremium = resp.getPolicyThreeYEarnPremium();
//			if (policyThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setPolicyThreeYRePayRate(BigDecimal.ZERO);
//			} else {
//				BigDecimal sum = policyThreeYSumChRep.add(policyThreeYNoChRep);
//				BigDecimal policyThreeYRePayRate = sum.divide(policyThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setPolicyThreeYRePayRate(policyThreeYRePayRate);
//			}
//
//			// 事故年当年已报告赔付率
//			BigDecimal accidentThisYEarnPremium = resp.getAccidentThisYEarnPremium();
//			BigDecimal accidentThisYSumChRep = resp.getAccidentThisYSumChRep();// 整体
//			BigDecimal accidentThisYNoChRep = resp.getAccidentThisYNoChRep();// 整体 未核赔款
//			BigDecimal accidentThisYSumChRepC = resp.getAccidentThisYSumChRepC();// 常规
//			BigDecimal accidentThisYNoChRepC = resp.getAccidentThisYNoChRepC();// 常规 未核赔款
//			BigDecimal accidentThisYSumChRepOP = resp.getAccidentThisYSumChRepOP();// 除大灾大赔
//			BigDecimal accidentThisYNoChRepOP = resp.getAccidentThisYNoChRepOP();// 除大灾大赔 未核赔款
//			BigDecimal accidentThisYSumChRepP = resp.getAccidentThisYSumChRepP();// 大灾
//			BigDecimal accidentThisYNoChRepP = resp.getAccidentThisYNoChRepP();// 大灾 未核赔款
//			if (accidentThisYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setAccidentThisYRePayRate(BigDecimal.ZERO);
//				resp.setAccidentThisYRePayRateC(BigDecimal.ZERO);
//				resp.setAccidentThisYRePayRateOP(BigDecimal.ZERO);
//				resp.setAccidentThisYRePayRateP(BigDecimal.ZERO);
//			} else {
//				// 事故年当年已报告赔付率（整体）
//				BigDecimal sum = accidentThisYSumChRep.add(accidentThisYNoChRep);
//				BigDecimal accidentThisYRePayRate = sum.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRate(accidentThisYRePayRate);
//				// 事故年当年已报告赔付率（常规）
//				BigDecimal sum1 = accidentThisYSumChRepC.add(accidentThisYNoChRepC);
//				BigDecimal accidentThisYRePayRateC = sum1.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRateC(accidentThisYRePayRateC);
//				// 事故年当年已报告赔付率（除大灾大赔案）
//				BigDecimal sum2 = accidentThisYSumChRepOP.add(accidentThisYNoChRepOP);
//				BigDecimal accidentThisYRePayRateOP = sum2.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRateOP(accidentThisYRePayRateOP);
//				// 事故年当年已报告赔付率（大灾）
//				BigDecimal sum3 = accidentThisYSumChRepP.add(accidentThisYNoChRepP);
//				BigDecimal accidentThisYRePayRateP = sum3.divide(accidentThisYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThisYRePayRateP(accidentThisYRePayRateP);
//			}
//
//			// 事故年近三年已报告赔付率
//			BigDecimal accidentThreeYEarnPremium = resp.getAccidentThreeYEarnPremium();// 已赚保费
//			BigDecimal accidentThreeYSumChRep = resp.getAccidentThreeYSumChRep();// 已核赔款 整体
//			BigDecimal accidentThreeYNoChRep = resp.getAccidentThreeYNoChRep();// 未核赔款 整体
//			BigDecimal accidentThreeYSumChRepC = resp.getAccidentThreeYSumChRepC();// 已核赔款 常规
//			BigDecimal accidentThreeYNoChRepC = resp.getAccidentThreeYNoChRepC();// 未核赔款 常规
//			BigDecimal accidentThreeYSumChRepOP = resp.getAccidentThreeYSumChRepOP();// 已核赔款 除大灾大赔
//			BigDecimal accidentThreeYNoChRepOP = resp.getAccidentThreeYNoChRepOP();// 未核赔款 除大灾大赔
//			BigDecimal accidentThreeYSumChRepP = resp.getAccidentThreeYSumChRepP();// 已核赔款 大灾
//			BigDecimal accidentThreeYNoChRepP = resp.getAccidentThreeYNoChRepP();// 未核赔款 大灾
//			if (accidentThreeYEarnPremium.compareTo(BigDecimal.ZERO) == 0) {
//				resp.setAccidentThreeYRePayRate(BigDecimal.ZERO);
//				resp.setAccidentThreeYRePayRateC(BigDecimal.ZERO);
//				resp.setAccidentThreeYRePayRateOP(BigDecimal.ZERO);
//				resp.setAccidentThreeYRePayRateP(BigDecimal.ZERO);
//			} else {
//				// 事故年近三年已报告赔付率（整体）
//				BigDecimal sum = accidentThreeYSumChRep.add(accidentThreeYNoChRep);
//				BigDecimal accidentThreeYRePayRate = sum.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP)
//						.multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRate(accidentThreeYRePayRate);
//				// 事故年近三年已报告赔付率（常规）
//				BigDecimal sum1 = accidentThreeYSumChRepC.add(accidentThreeYNoChRepC);
//				BigDecimal accidentThreeYRePayRateC = sum1
//						.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRateC(accidentThreeYRePayRateC);
//				// 事故年近三年已报告赔付率（除大灾大赔）
//				BigDecimal sum2 = accidentThreeYSumChRepOP.add(accidentThreeYNoChRepOP);
//				BigDecimal accidentThreeYRePayRateOP = sum2
//						.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRateOP(accidentThreeYRePayRateOP);
//				// 事故年近三年已报告赔付率（除大灾）
//				BigDecimal sum3 = accidentThreeYSumChRepP.add(accidentThreeYNoChRepP);
//				BigDecimal accidentThreeYRePayRateP = sum3
//						.divide(accidentThreeYEarnPremium, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				resp.setAccidentThreeYRePayRateP(accidentThreeYRePayRateP);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resp;
//	}
//
//	/**
//	 * @功能：精算数据： 对特殊的省份进行处理,四个直辖市
//	 * @param String
//	 * @return Map<String, Object>
//	 * @author liqiankun @时间：20190709 @修改记录：
//	 */
//	public Map<String, Object> handleSpecialProvince(Map<String, String> map) {
//		Map<String, Object> newMap = new HashMap<String, Object>();
//		String proAdCode = map.get("proAdCode");
//		String addressCounty = "", addressCity = "";
//		if ("110000,120000,310000,500000".indexOf(proAdCode) > -1) {
//			addressCounty = map.get("provinceName") + map.get("countryName");
//			// 是否是特殊直辖市标志
//			newMap.put("isSpecialFlag", "01");
//		} else {
//			addressCounty = map.get("provinceName") + map.get("cityName") + map.get("countryName");
//			addressCity = map.get("provinceName") + map.get("cityName");
//			newMap.put("isSpecialFlag", "02");
//		}
//		newMap.put("addressCounty", addressCounty);
//		newMap.put("provinceName", map.get("provinceName"));
//		newMap.put("cityName", map.get("cityName"));
//		newMap.put("countryName", map.get("countryName"));
//		newMap.put("proAdCode", map.get("proAdCode"));
//		newMap.put("addressCity", addressCity);
//
//		return newMap;
//	}
//
//	/**
//	 * @功能：精算数据中对工程险特殊省份信息处理
//	 * @param MapRequestInfoVo
//	 * @return String
//	 * @author liqiankun @时间：20190709 @修改记录：
//	 */
//	public String handleSpecialAddress(MapRequestInfoVo mapRequestInfoVo) {
//		String address = "";
//		if (null != mapRequestInfoVo) {
//			RiskMapRegionVo riskMapRegionVo = mapRequestInfoVo.getMapAddress();
//			String proAdCode = riskMapRegionVo.getProAdCode();
//			if (riskMapRegionVo != null && StringUtils.isNotBlank(proAdCode)) {
//				String provinceName = riskMapRegionVo.getProvinceName();
//				// 对自治区和省市进行名称截取
//				if ("150000".equals(proAdCode)) {
//					// 内蒙古自治区
//					address = provinceName.substring(0, 3);
//				} else if ("450000,540000,640000,650000".indexOf(proAdCode) > -1) {
//					// 其他四个自治区
//					address = provinceName.substring(0, 2);
//				} else {
//					// 其余的省份的处理
//					address = provinceName.substring(0, provinceName.length() - 1);
//				}
//			}
//		}
//		return address;
//	}
//
//	/**
//	 * @功能：精算数据：拼接分页语句
//	 * @param String
//	 * @return String
//	 * @author liqiankun @时间：20190709 @修改记录：
//	 */
//	public String handleSplitSql(MapRequestInfoVo mapRequestInfoVo, String sqlFlag) {
//		// 分页的前缀和后缀
//		String preQSql = "select addressname,secondLevelProduct,businessName,conisBeforeAmountSection,stockScaleSection, "
//				+ "calendarThisYPremium,calendarThisYAmount,calendarThreeYPremium,calendarYAmount,policyThisYSumChRep,policyThisYNoChRep,"
//				+ "policyThisYEarnPremium,policyThreeYSumChRep,policyThreeYNoChRep,policyThreeYEarnPremium,accidentThisYSumChRep,accidentThisYNoChRep, accidentThisYSumChRepC,accidentThisYNoChRepC,"
//				+ " accidentThisYSumChRepOP,accidentThisYNoChRepOP, accidentThisYSumChRepP,accidentThisYNoChRepP, accidentThisYEarnPremium, accidentThreeYEarnPremium, accidentThreeYSumChRep,accidentThreeYNoChRep, "
//				+ "accidentThreeYSumChRepC,accidentThreeYNoChRepC, accidentThreeYSumChRepOP,accidentThreeYNoChRepOP, accidentThreeYSumChRepP,accidentThreeYNoChRepP from  (select a.*,ROWNUM rn from( ";
//
//		String preGSql = "select  addressName,engineCategory, calendarThisYPremium, calendarThisYAmount, calendarThreeYPremium,calendarYAmount,"
//				+ " policyThisYSumChRep,policyThisYNoChRep, policyThisYEarnPremium,policyThreeYSumChRep,policyThreeYNoChRep,  policyThreeYEarnPremium, accidentThisYSumChRep,accidentThisYNoChRep,  accidentThisYSumChRepC,accidentThisYNoChRepC,"
//				+ " accidentThisYSumChRepOP,accidentThisYNoChRepOP, accidentThisYSumChRepP,accidentThisYNoChRepP, accidentThisYEarnPremium,accidentThreeYEarnPremium, accidentThreeYSumChRep,accidentThreeYNoChRep, accidentThreeYSumChRepC,accidentThreeYNoChRepC,"
//				+ " accidentThreeYSumChRepOP,accidentThreeYNoChRepOP,  accidentThreeYSumChRepP,accidentThreeYNoChRepP from  (select a.*,ROWNUM rn from( ";
//		// 分页的中间SQL和最终SQL
//		String targetSql = "", amongSql = "", appendSql = "";
//		int pageNo = mapRequestInfoVo.getPageNo();
//		int pageSize = mapRequestInfoVo.getPageSize();
//		appendSql = ") a where ROWNUM<=(" + pageNo * pageSize + "))  where rn>" + (pageNo - 1) * pageSize;
//		RiskMapRegionVo riskMapRegionVo = mapRequestInfoVo.getMapAddress();
//		if (null != riskMapRegionVo && StringUtils.isNotBlank(riskMapRegionVo.getProAdCode())) {
//			String businessName = riskMapRegionVo.getBusinessName();
//			if ("Q".equals(sqlFlag)) {
//				// 获取通过union 连接的中间语句的sql
//				amongSql = this.amongSql(riskMapRegionVo, "");
//				targetSql = preQSql + amongSql + appendSql;
//			} else if ("G".equals(sqlFlag)) {
//				String addressname = this.handleSpecialAddress(mapRequestInfoVo);
//				amongSql = this.establishSql(addressname, sqlFlag, businessName);
//				targetSql = preGSql + amongSql + appendSql;
//			}
//		}
//		return targetSql;
//	}
//
//	/**
//	 * @功能：精算数据：拼接查询totalcount的语句
//	 * @param String
//	 * @return String
//	 * @author liqiankun @时间：20190709 @修改记录：
//	 */
//	public String totalCountSql(MapRequestInfoVo mapRequestInfoVo, String flag) {
//		StringBuffer stringBuffer = new StringBuffer();
//		String preSql = "select count(a.total) as num from ( ";
//		String appendSql = " ) a  ";
//		String amongSql = "";
//		RiskMapRegionVo riskMapRegionVo = mapRequestInfoVo.getMapAddress();
//		// 行业或 工程类别
//		String businessName = riskMapRegionVo.getBusinessName();
//		if (null != riskMapRegionVo && StringUtils.isNotBlank(riskMapRegionVo.getProAdCode())) {
//			// 获取通过union 连接的中间语句的sql
//			if ("Q".equals(flag)) {
//				amongSql = this.amongSql(riskMapRegionVo, "countFlag");
//				stringBuffer.append(preSql).append(amongSql).append(appendSql);
//			} else if ("G".equals(flag)) {
//				String addressname = this.handleSpecialAddress(mapRequestInfoVo);
//				amongSql = this.establishSql(addressname, "countFlagG", businessName);
//				stringBuffer.append(preSql).append(amongSql).append(appendSql);
//			}
//
//		}
//		return stringBuffer.toString();
//	}
//
//	/**
//	 * @功能：精算数据：通过union 连接的中间语句
//	 * @param String
//	 * @return String
//	 * @author liqiankun @时间：20190709 @修改记录：
//	 */
//	public String amongSql(RiskMapRegionVo riskMapRegionVo, String countFlag) {
//		String provinceSql = "", citySql = "", countySql = "", amongSql = "";
//		String provinceName = riskMapRegionVo.getProvinceName();
//		String cityName = riskMapRegionVo.getCityName();
//		String countryName = riskMapRegionVo.getCountryName();
//		// 行业或者工程
//		String businessName = riskMapRegionVo.getBusinessName();
//		// 四个直辖市特殊处理
//		if ("110000,120000,310000,500000".indexOf(riskMapRegionVo.getProAdCode().trim()) > -1) {
//			provinceSql = this.establishSql(provinceName, countFlag, businessName);
//			countySql = this.establishSql(provinceName + countryName, countFlag, businessName);
//			if ("province".equals(riskMapRegionVo.getAddressName())) {
//				amongSql = provinceSql + " union all " + countySql;
//			} else {
//				amongSql = countySql;
//			}
//
//		} else {
//			provinceSql = this.establishSql(provinceName, countFlag, businessName);
//			citySql = this.establishSql(provinceName + cityName, countFlag, businessName);
//			countySql = this.establishSql(provinceName + cityName + countryName, countFlag, businessName);
//			// 拼接中间的连接的sql语句
//			if ("province".equals(riskMapRegionVo.getAddressName())) {
//				amongSql = provinceSql + " union all " + citySql + " union all " + countySql;
//			} else if ("city".equals(riskMapRegionVo.getAddressName())) {
//				amongSql = citySql + " union all " + countySql;
//			} else {
//				amongSql = countySql;
//			}
//			// amongSql = provinceSql+" union all "+citySql+" union all "+countySql;
//		}
//		return amongSql;
//	}
//
//	/**
//	 * @功能：组织sql语句
//	 * @param String
//	 * @return String
//	 * @author liqiankun @时间：20190709 @修改记录：
//	 */
//	public String establishSql(String addressname, String countFlag, String businessName) {
//		StringBuffer stringBuffer = new StringBuffer();
//		String sql = "", appendQSql = "", appendQSqlGroup = "", appendGSql = "", appendGSqlGroup = "";
//		if (StringUtils.isNotBlank(addressname)) {
//			// 企财险后缀
//			appendQSql = " from riskmap_qformmanager where addressname like '" + addressname + "%'  ";
//			// "group by
//			// secondLevelProduct,businessName,conisBeforeAmountSection,stockScaleSection";
//			appendQSqlGroup = " group by secondLevelProduct,businessName,conisBeforeAmountSection,stockScaleSection";
//			// 工程险后缀
//			appendGSql = " from riskmap_gformmanager where addressname like '" + addressname + "%'";
//			appendGSqlGroup = " group by engineCategory";
//			// 增加“行业/工程类别”的查询条件
//			if (StringUtils.isNotBlank(businessName)) {
//				appendQSql += " and businessname like '%" + businessName.trim() + "%'";
//				appendGSql += " and enginecategory like '%" + businessName.trim() + "%'";
//			}
//			appendQSql += appendQSqlGroup;
//			appendGSql += appendGSqlGroup;
//			if ("countFlag".equals(countFlag)) {
//				// 企财险total
//				sql = "select  count(*) as total ";
//				stringBuffer.append(sql).append(appendQSql);
//			} else if ("countFlagG".equals(countFlag)) {
//				// 工程险total
//				sql = "select  count(*) as total ";
//				stringBuffer.append(sql).append(appendGSql);
//			} else if ("G".equals(countFlag)) {
//				// 工程险sql
//				sql = "select  '" + addressname + "'  as  addressName," + "engineCategory,"
//						+ "sum(calendarThisYPremium) as calendarThisYPremium,"
//						+ "sum(calendarThisYAmount) as calendarThisYAmount,"
//						+ "sum(calendarThreeYPremium) as calendarThreeYPremium,"
//						+ "sum(calendarYAmount) as calendarYAmount,"
//						+ "sum(policyThisYSumChRep) as policyThisYSumChRep,"
//						+ "sum(policyThisYNoChRep) as policyThisYNoChRep,"
//						+ "sum(policyThisYEarnPremium) as policyThisYEarnPremium,"
//						+ "sum(policyThreeYSumChRep) as policyThreeYSumChRep,"
//						+ "sum(policyThreeYNoChRep) as policyThreeYNoChRep,"
//						+ "sum(policyThreeYEarnPremium) as policyThreeYEarnPremium,"
//						+ "sum(accidentThisYSumChRep) as accidentThisYSumChRep,"
//						+ "sum(accidentThisYNoChRep) as accidentThisYNoChRep,"
//						+ "sum(accidentThisYSumChRepC) as accidentThisYSumChRepC,"
//						+ "sum(accidentThisYNoChRepC) as accidentThisYNoChRepC,"
//						+ "sum(accidentThisYSumChRepOP) as accidentThisYSumChRepOP,"
//						+ "sum(accidentThisYNoChRepOP) as accidentThisYNoChRepOP,"
//						+ "sum(accidentThisYSumChRepP) as accidentThisYSumChRepP,"
//						+ "sum(accidentThisYNoChRepP) as accidentThisYNoChRepP,"
//						+ "sum(accidentThisYEarnPremium) as accidentThisYEarnPremium,"
//						+ "sum(accidentThreeYEarnPremium) as accidentThreeYEarnPremium,"
//						+ "sum(accidentThreeYSumChRep) as accidentThreeYSumChRep,"
//						+ "sum(accidentThreeYNoChRep) as accidentThreeYNoChRep,"
//						+ "sum(accidentThreeYSumChRepC) as accidentThreeYSumChRepC,"
//						+ "sum(accidentThreeYNoChRepC) as accidentThreeYNoChRepC,"
//						+ "sum(accidentThreeYSumChRepOP) as accidentThreeYSumChRepOP,"
//						+ "sum(accidentThreeYNoChRepOP) as accidentThreeYNoChRepOP,"
//						+ "sum(accidentThreeYSumChRepP) as accidentThreeYSumChRepP,  "
//						+ "sum(accidentThreeYNoChRepP) as accidentThreeYNoChRepP  ";
//				stringBuffer.append(sql).append(appendGSql);
//			} else {
//				// 企财险
//				sql = "select '" + addressname + "'  as  addressname," + "secondLevelProduct," + "businessName,"
//						+ "conisBeforeAmountSection," + "stockScaleSection,"
//						+ "sum(calendarThisYPremium) as calendarThisYPremium,"
//						+ "sum(calendarThisYAmount) as calendarThisYAmount,"
//						+ "sum(calendarThreeYPremium) as calendarThreeYPremium,"
//						+ "sum(calendarYAmount) as calendarYAmount,"
//						+ "sum(policyThisYSumChRep) as policyThisYSumChRep,"
//						+ "sum(policyThisYNoChRep) as policyThisYNoChRep,"
//						+ "sum(policyThisYEarnPremium) as policyThisYEarnPremium,"
//						+ "sum(policyThreeYSumChRep) as policyThreeYSumChRep,"
//						+ "sum(policyThreeYNoChRep) as policyThreeYNoChRep,"
//						+ "sum(policyThreeYEarnPremium) as policyThreeYEarnPremium,"
//						+ "sum(accidentThisYSumChRep) as accidentThisYSumChRep,"
//						+ "sum(accidentThisYNoChRep) as accidentThisYNoChRep,"
//						+ "sum(accidentThisYSumChRepC) as accidentThisYSumChRepC,"
//						+ "sum(accidentThisYNoChRepC) as accidentThisYNoChRepC,"
//						+ "sum(accidentThisYSumChRepOP) as accidentThisYSumChRepOP,"
//						+ "sum(accidentThisYNoChRepOP) as accidentThisYNoChRepOP,"
//						+ "sum(accidentThisYSumChRepP) as accidentThisYSumChRepP,"
//						+ "sum(accidentThisYNoChRepP) as accidentThisYNoChRepP,"
//						+ "sum(accidentThisYEarnPremium) as accidentThisYEarnPremium,"
//						+ "sum(accidentThreeYEarnPremium) as accidentThreeYEarnPremium,"
//						+ "sum(accidentThreeYSumChRep) as accidentThreeYSumChRep,"
//						+ "sum(accidentThreeYNoChRep) as accidentThreeYNoChRep,"
//						+ "sum(accidentThreeYSumChRepC) as accidentThreeYSumChRepC,"
//						+ "sum(accidentThreeYNoChRepC) as accidentThreeYNoChRepC,"
//						+ "sum(accidentThreeYSumChRepOP) as accidentThreeYSumChRepOP,"
//						+ "sum(accidentThreeYNoChRepOP) as accidentThreeYNoChRepOP,"
//						+ "sum(accidentThreeYSumChRepP) as accidentThreeYSumChRepP,  "
//						+ "sum(accidentThreeYNoChRepP) as accidentThreeYNoChRepP  ";
//				stringBuffer.append(sql).append(appendQSql);
//			}
//
//		}
//		return stringBuffer.toString();
//	}
//	/**
//	 * @功能：根据台风编号查询降雨影响标的 及 预测降雨影响标的
//	 * @author liqiankun
//	 * @param tfbh
//	 * @throws Exception
//	 * @时间：20200324
//	 */
//	public AjaxResult queryRainTarget(String tfbh, String target) {
//		AjaxResult ajaxResult = new AjaxResult();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String tableName = "";
//		List<WzCorporeYjPVo> YjTarget = new ArrayList<WzCorporeYjPVo>();
//		List<WzCorporeLsPVo> LsTarget = new ArrayList<WzCorporeLsPVo>();
//		if (target.equals("RainYbTarget")) {
//			tableName = "WZ_CORPOREYJ_P";
//			try {
//				if (StringUtils.isNotBlank(tfbh)) {
//					String sql = "select * from " + tableName.trim() +" where tfbh = '" + tfbh.trim() + "'";
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					stat = conn.prepareStatement(sql);
//					rs = stat.executeQuery();
//					// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
//					Map<String,List<WzCorporeYjPVo>> targetMap = new HashMap<String, List<WzCorporeYjPVo>>();
//					List<WzCorporeYjPVo> proList = new ArrayList<WzCorporeYjPVo>();
//					List<WzCorporeYjPVo> cityList = new ArrayList<WzCorporeYjPVo>();
//				
//					for(;rs.next();) {
//						WzCorporeYjPVo itemp = new WzCorporeYjPVo();
//						itemp.setTfbh(rs.getString("tfbh"));
//						itemp.setCityCode(rs.getString("cityCode"));
//						itemp.setCityName(rs.getString("cityName"));
//						itemp.setTotalAmount(rs.getBigDecimal("totalAmount"));
//						itemp.setjAmount(rs.getBigDecimal("jAmount"));
//						itemp.setqAmount(rs.getBigDecimal("qAmount"));
//						itemp.setgAmount(rs.getBigDecimal("gAmount"));
//						itemp.setCenterX(rs.getBigDecimal("centerX"));
//						itemp.setCenterY(rs.getBigDecimal("centerY"));
//						itemp.setProCityFlag(rs.getString("proCityFlag"));
//						itemp.setCorporeSum(rs.getBigDecimal("corporeSum"));
//						YjTarget.add(itemp);
//						String cityCode= rs.getString("cityCode");
//						if(cityCode.endsWith("0000")){
//							proList.add(itemp);
//						}else {
//							cityList.add(itemp);
//						}
//					}
//					if (!(target.isEmpty())&&null!=YjTarget&&YjTarget.size()>0) {
//						// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
//						targetMap.put("1", proList);
//						targetMap.put("2", cityList);
////						Map<String,List<WzCorporeYjPVo>> targetMap = new HashMap<String, List<WzCorporeYjPVo>>();
////						for (WzCorporeYjPVo itemTarget : YjTarget) {
////							if (targetMap.containsKey(itemTarget.getProCityFlag())) {
////								targetMap.get(itemTarget.getProCityFlag()).add(itemTarget);
//////								targetMap.put(itemTarget.getProCityFlag(), itemList);
////							} else {
////								List<WzCorporeYjPVo> itemList = new ArrayList<WzCorporeYjPVo>();
////								itemList.add(itemTarget);
////								targetMap.put(itemTarget.getProCityFlag(), itemList);
////							}
////						}
//						ajaxResult.setData(targetMap);
//						ajaxResult.setStatus(0);
//					} else {
//						ajaxResult.setStatus(1);
//						ajaxResult.setStatusText("无降雨预测影响标的");
//					}
//				}
//			}catch (Exception e) {
//				log.info("查询降雨预测影响标的异常：" + e.getMessage(), e);
//				e.printStackTrace();
//				throw new RuntimeException("查询降雨预测影响标的异常:" + e);
//			}finally {
//				releaseResources(stat, conn, rs);
//			}
//		} else if (target.equals("RainLsTarget")) {
//			tableName = "WZ_CORPORELS_P";
//			try {
//				if (StringUtils.isNotBlank(tfbh)) {
//					String sql = "select * from " + tableName.trim() +" where tfbh = '" + tfbh.trim() + "'";
//					conn = slaveJdbcTemplate.getDataSource().getConnection();
//					stat = conn.prepareStatement(sql);
//					rs = stat.executeQuery();
//					// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
//					Map<String,List<WzCorporeLsPVo>> targetMap = new HashMap<String, List<WzCorporeLsPVo>>();
//					List<WzCorporeLsPVo> proList = new ArrayList<WzCorporeLsPVo>();
//					List<WzCorporeLsPVo> cityList = new ArrayList<WzCorporeLsPVo>();
//				
//					for(;rs.next();) {
//						WzCorporeLsPVo itemp = new WzCorporeLsPVo();
//						itemp.setTfbh(rs.getString("tfbh"));
//						itemp.setCityCode(rs.getString("cityCode"));
//						itemp.setCityName(rs.getString("cityName"));
//						itemp.setTotalAmount(rs.getBigDecimal("totalAmount"));
//						itemp.setjAmount(rs.getBigDecimal("jAmount"));
//						itemp.setqAmount(rs.getBigDecimal("qAmount"));
//						itemp.setgAmount(rs.getBigDecimal("gAmount"));
//						itemp.setCenterX(rs.getBigDecimal("centerX"));
//						itemp.setCenterY(rs.getBigDecimal("centerY"));
//						itemp.setProCityFlag(rs.getString("PROCITYFLAG"));
//						itemp.setCorporeSum(rs.getBigDecimal("CORPORESUM"));
//						LsTarget.add(itemp);
//						String cityCode= rs.getString("cityCode");
//						if(cityCode.endsWith("0000")){
//							proList.add(itemp);
//						}else {
//							cityList.add(itemp);
//						}
//					}
//					if (!(target.isEmpty())&&null!=LsTarget&&LsTarget.size()>0) {
//						// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
//						targetMap.put("1", proList);
//						targetMap.put("2", cityList);
//						// 更加行政区划（proCityFlag）分组台风影响标的(1:省级，2：市级)
////						Map<String,List<WzCorporeLsPVo>> targetMap = new HashMap<String, List<WzCorporeLsPVo>>();
////						
////						for (WzCorporeLsPVo itemTarget : LsTarget) {
////							if (targetMap.containsKey(itemTarget.getProCityFlag())) {
//////								itemList.add(itemTarget);
////								targetMap.get(itemTarget.getProCityFlag()).add(itemTarget);
//////								targetMap.put(itemTarget.getProCityFlag(), itemList);
////							} else {
////								List<WzCorporeLsPVo> itemList = new ArrayList<WzCorporeLsPVo>();
////								itemList.add(itemTarget);
////								targetMap.put(itemTarget.getProCityFlag(), itemList);
////							}
////						}
//						ajaxResult.setData(targetMap);
//						ajaxResult.setStatus(0);
//					} else {
//						ajaxResult.setStatus(1);
//						ajaxResult.setStatusText("无降雨影响标的");
//					}
//				}
//			}catch (Exception e) {
//				log.error("查询降雨影响标的异常：" + e.getMessage(), e);
//				e.printStackTrace();
//				throw new RuntimeException("查询降雨影响标的异常:" + e);
//			}finally {
//				releaseResources(stat, conn, rs);
//			}
//		}
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
//	public List<DangerManageMapInfo> queryTySingleInfo(String tfbh,String isHappen,
//			String comCode,String coordinateType,int pageNo,int pageSize){
//		List<DangerManageMapInfo> dangerManageMapInfoList =new ArrayList<DangerManageMapInfo>();
//		try {
//			if(StringUtils.isNotBlank(isHappen)){
//				if("0".equals(isHappen.trim())){
//					// 预计影响，查询WZ_CORPOREYJ表
//					dangerManageMapInfoList = this.queryCorporeYjInfo(tfbh,comCode,coordinateType,pageNo,pageSize);
//				}else if("1".equals(isHappen.trim())){
//					// 已影响，查询WZ_CORPORELS表
//					dangerManageMapInfoList = this.queryCorporeLsInfo(tfbh,comCode,coordinateType,pageNo,pageSize);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("获取台风影响单个标的坐标信息异常：" + e.getMessage(), e);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：查询台风预计影响信息
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public List<DangerManageMapInfo> queryCorporeYjInfo(String tfbh,String comCode,
//			String coordinateType,int pageNo,int pageSize){
//		QueryRule queryRule =QueryRule.getInstance();
//		List<DangerManageMapInfo> dangerManageMapInfoList = new ArrayList<DangerManageMapInfo>();
//		List<WzCorporeYj> wzCorporeYjList= new ArrayList<WzCorporeYj>(); 
//		try {
//			 Page page = new Page();
//			if(StringUtils.isNotBlank(comCode)){
//				//判断该编码 是何种编码类型,然后根据编码类型来进行查询，编码类型只有省市两种类型,且省级编码开头两位数，剩余是四个零。
//				if(comCode.trim().endsWith("0000")){
//					Map<String, List<String>> mapCode =  this.operateCityProCode();
//					queryRule.addIn("cityCode", mapCode.get(comCode.trim()));
//				}else {
//					queryRule.addEqual("cityCode", comCode.trim());
//				}
//			}
//			if(StringUtils.isNotBlank(tfbh)){
//				queryRule.addEqual("id.tfbh", tfbh.trim());
//			}
////			wzCorporeYjList =databaseDao.findAll(WzCorporeYj.class, queryRule);
//			page = databaseDao.findPage(WzCorporeYj.class, queryRule, pageNo, pageSize);
//			wzCorporeYjList = page.getResult();
//			/*没有信息直接返回*/
//			if(null==wzCorporeYjList||wzCorporeYjList.size()==0){
//				return dangerManageMapInfoList;
//			}
//			for(WzCorporeYj wzCorporeYj: wzCorporeYjList){
//				DangerManageMapInfo dangerManageMapInfo =new DangerManageMapInfo();
//				if(null!=wzCorporeYj.getId()){
//					dangerManageMapInfo.setTfbh(wzCorporeYj.getId().getTfbh());
//					dangerManageMapInfo.setAddressId(wzCorporeYj.getId().getMid());
//					List<String> InsuredNameList = this.queryInsuredName(wzCorporeYj.getId().getMid());
//					dangerManageMapInfo.setInsuredName(InsuredNameList);
//				}
//				if(StringUtils.isNotBlank(tfbh)){
//					if("02".equals(coordinateType.trim())){
//						dangerManageMapInfo.setLongitude(wzCorporeYj.getPointx_02());
//						dangerManageMapInfo.setLatitude(wzCorporeYj.getPointy_02());
//					}else if("2000".equals(coordinateType.trim())){
//						dangerManageMapInfo.setLongitude(wzCorporeYj.getPointx_2000());
//						dangerManageMapInfo.setLatitude(wzCorporeYj.getPointy_2000());
//					}
//				}
//				dangerManageMapInfo.setAddressName(wzCorporeYj.getAddressName());
//				dangerManageMapInfoList.add(dangerManageMapInfo);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("组织台风预计影响信息失败：" + e.getMessage(), e);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：查询台风已影响信息
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public List<DangerManageMapInfo> queryCorporeLsInfo(String tfbh,String comCode,
//			String coordinateType,int pageNo,int pageSize){
//		QueryRule queryRule =QueryRule.getInstance();
//		List<DangerManageMapInfo> dangerManageMapInfoList = new ArrayList<DangerManageMapInfo>();
//		List<WzCorporeLs> wzCorporeLsList= new ArrayList<WzCorporeLs>(); 
//		try {
//			 Page page = new Page();
//			if(StringUtils.isNotBlank(comCode)){
//				//判断该编码 是何种编码类型,然后根据编码类型来进行查询，编码类型只有省市两种类型,且省级编码开头两位数，剩余是四个零。
//				if(comCode.trim().endsWith("0000")){
//					Map<String, List<String>> mapCode =  this.operateCityProCode();
//					queryRule.addIn("cityCode", mapCode.get(comCode.trim()));
//				}else {
//					queryRule.addEqual("cityCode", comCode.trim());
//				}
//			}
//			if(StringUtils.isNotBlank(tfbh)){
//				queryRule.addEqual("id.tfbh", tfbh.trim());
//			}
////			wzCorporeLsList =databaseDao.findAll(WzCorporeLs.class, queryRule);
//			page = databaseDao.findPage(WzCorporeLs.class, queryRule, pageNo, pageSize);
//			wzCorporeLsList = page.getResult(); 
//			/*没有信息直接返回*/
//			if(null==wzCorporeLsList||wzCorporeLsList.size()==0){
//				return dangerManageMapInfoList;
//			}
//			for(WzCorporeLs wzCorporeLs: wzCorporeLsList){
//				DangerManageMapInfo dangerManageMapInfo =new DangerManageMapInfo();
//				if(null!=wzCorporeLs.getId()){
//					dangerManageMapInfo.setTfbh(wzCorporeLs.getId().getTfbh());
//					dangerManageMapInfo.setAddressId(wzCorporeLs.getId().getMid());
//					List<String> InsuredNameList = this.queryInsuredName(wzCorporeLs.getId().getMid());
//					dangerManageMapInfo.setInsuredName(InsuredNameList);
//				}
//				if(StringUtils.isNotBlank(tfbh)){
//					if("02".equals(coordinateType.trim())){
//						dangerManageMapInfo.setLongitude(wzCorporeLs.getPointx_02());
//						dangerManageMapInfo.setLatitude(wzCorporeLs.getPointy_02());
//					}else if("2000".equals(coordinateType.trim())){
//						dangerManageMapInfo.setLongitude(wzCorporeLs.getPointx_2000());
//						dangerManageMapInfo.setLatitude(wzCorporeLs.getPointy_2000());
//					}
//				}
//				dangerManageMapInfo.setAddressName(wzCorporeLs.getAddressName());
//				dangerManageMapInfoList.add(dangerManageMapInfo);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("组织台风已影响信息失败：" + e.getMessage(), e);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：根据china_city来进行组织省级编码信息
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public Map<String, List<String>> operateCityProCode(){
//		Map<String, List<String>> map = new HashMap<String, List<String>>();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			// 查询出表名
////			String tableName = MapUtils.getDataSetTableName("china_city");
////			String sql = "select PROADCODE,CITYADCODE from "+tableName.trim()+" group by PROADCODE,CITYADCODE";
//			String sql = "select UPPERCODE as PROADCODE, CODECODE as CITYADCODE from riskDcode where CODETYPE='MapCode'  group by UPPERCODE,CODECODE";
//			conn = slaveJdbcTemplate.getDataSource().getConnection();
//			stat = conn.prepareStatement(sql);
//			rs = stat.executeQuery();
//			for(;rs.next();) {
//				String proAdCode =rs.getString("PROADCODE");
//				String cityAdCode =rs.getString("CITYADCODE");
//				if(map.containsKey(proAdCode)){
//					List<String> strList= map.get(proAdCode);
//					strList.add(cityAdCode);
//					map.put(proAdCode, strList);
//				}else {
//					List<String> strList= new ArrayList<String>();
//					strList.add(cityAdCode);
//					map.put(proAdCode,strList);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			log.error("组织省市信息编码失败：" + e.getMessage(), e);
//		}finally{
//			releaseResources(stat, conn, rs);
//		}
//		return map;
//	}
//	/**
//	 * @功能：根据标的地址查询关系人名称
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public List<String> queryInsuredName(Integer addressId){
//		List<String> insuredNameList = new ArrayList<String>();
////		Connection conn = null;
////		PreparedStatement stat = null;
////		ResultSet rs = null;
//		try {
//			QueryRule queryRule = QueryRule.getInstance();
//			if(null!=addressId){
////				String sql = "select distinct INSUREDCODE,INSUREDNAME from  RISKMAP_INSURED where ADDRESSID ="+addressId;
////				conn = slaveJdbcTemplate.getDataSource().getConnection();
////				stat = conn.prepareStatement(sql);
////				rs = stat.executeQuery();
////				for(;rs.next();) {
////					String insuredName =rs.getString("INSUREDNAME");
////					insuredNameList.add(insuredName);
////				}
////				List rows = slaveJdbcTemplate.queryForList(sql);
////				Iterator iterator = rows.iterator();
////				for(;iterator.hasNext();) {
////					Map avMap = (Map)iterator.next();
////					String insuredName ="";
////					if(null!=avMap.get("INSUREDNAME")){
////						insuredName =avMap.get("INSUREDNAME").toString();
////					}
////					insuredNameList.add(insuredName);
////				}
//				queryRule.addEqual("id.addressID", addressId);
//				List<RiskMapInsured> riskMapInsuredList =databaseDao.findAll(RiskMapInsured.class, queryRule);
//				List<String> codeList = new ArrayList<String>();
//				for (RiskMapInsured riskMapInsured:riskMapInsuredList){
//					String insuredName= riskMapInsured.getInsuredName();
//					String insuredCode= riskMapInsured.getInsuredCode();
//					if(!codeList.contains(insuredCode)){
//						codeList.add(insuredCode);
//						insuredNameList.add(insuredName);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("查询关系人名称失败：" + e.getMessage(), e);
//		}finally{
////			releaseResources(stat, conn, rs);
//		}
//		return insuredNameList;
//	}
//	/**
//	 * @功能：根据标的地址查询关系人名称
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public List<RiskMapInsured> queryInsuredNameList(List<Integer> addressIdList){
//		List<RiskMapInsured> insuredNameList = new ArrayList<RiskMapInsured>();
//		try {
//			QueryRule queryRule = QueryRule.getInstance();
//			if(null!=addressIdList&&addressIdList.size()>0){
//				String sql = " ( ";
//				String appendSql = "";
//				int count = addressIdList.size()/500;
//				if(count==0){
//					appendSql+= "addressID in ( " +  StringUtils.join(addressIdList, ",") +")";
//				}
//				for (int i=1;i<=count;i++){
//					List<Integer> countList = null;
//					if(i<count){
//						countList = addressIdList.subList(0, i*500);
//						appendSql+=  "( addressID in ( " + StringUtils.join(countList, ",") +" ) ) or ";
//					}else {
//						countList = addressIdList.subList((i-1)*500, addressIdList.size());
//						appendSql+=  " ( addressID in ( " + StringUtils.join(countList, ",") +" ) ) ";
//					}
//				}
//				sql += appendSql+" )";
////				queryRule.addEqual("id.addressID", addressId);
////				queryRule.addIn("id.addressID", addressIdList);
//				queryRule.addSql(sql);
//				System.out.println(sql);
//				insuredNameList =databaseDao.findAll(RiskMapInsured.class, queryRule);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("查询关系人名称失败：" + e.getMessage(), e);
//		}
//		return insuredNameList;
//	}
//	/**
//	 * @功能：查询台风影响标的点数量
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200327
//	 * @修改记录：
//	 */
//	public Integer queryTySingleCount(String tfbh,String isHappen,String comCode){
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		String appendSql = " 1=1 ";
//		String tableName ="",count ="";
//		try {
//			if(StringUtils.isNotBlank(isHappen)){
//				if("0".equals(isHappen.trim())){
//					// 预计影响，查询WZ_CORPOREYJ表
//					tableName="WZ_CORPOREYJ"; 
//				}else if("1".equals(isHappen.trim())){
//					// 已影响，查询WZ_CORPORELS表
//					tableName="WZ_CORPORELS"; 
//				}
//				if(StringUtils.isNotBlank(comCode)){
//					//判断该编码 是何种编码类型,然后根据编码类型来进行查询，编码类型只有省市两种类型,且省级编码开头两位数，剩余是四个零。
//					if(comCode.trim().endsWith("0000")){
//						Map<String, List<String>> mapCode =  this.operateCityProCode();
//						String listStr= StringUtils.join(mapCode.get(comCode.trim()),",");
//						appendSql+=" and cityCode in ("+listStr+")";
//					}else {
//						appendSql+=" and cityCode  ='"+comCode.trim()+"'";
//					}
//				} 
//				if(StringUtils.isNotBlank(tfbh)){
//					appendSql+=" and tfbh  ='"+tfbh.trim()+"'";
//				}
//				
//				String sql = "select count(*) from  "+tableName+" where "+appendSql;
//				conn = slaveJdbcTemplate.getDataSource().getConnection();
//				stat = conn.prepareStatement(sql);
//				rs = stat.executeQuery();
//				for(;rs.next();) {
//					count =rs.getString(1);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			log.error("查询台风影响标的点数量失败：" + e.getMessage(), e);
//		}finally{
//			releaseResources(stat, conn, rs);
//		}
//		return Integer.parseInt(count);
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
//	public List<DangerManageMapInfo> queryTySubjectInfo(String tfbh,String isHappen,
//			String comCode,String coordinateType,int pageNo,int pageSize){
//		List<DangerManageMapInfo> dangerManageMapInfoList =new ArrayList<DangerManageMapInfo>();
//		try {
//			long startTime = System.currentTimeMillis();
//			if(StringUtils.isNotBlank(isHappen)){
//				if("0".equals(isHappen.trim())){
//					// 预计影响，查询WZ_CORPOREYJ表
//					dangerManageMapInfoList = this.querySubjectCorporeInfo(tfbh,comCode,coordinateType,"WZ_CORPOREYJ",pageNo,pageSize);
//				}else if("1".equals(isHappen.trim())){
//					// 已影响，查询WZ_CORPORELS表
//					dangerManageMapInfoList = this.querySubjectCorporeInfo(tfbh,comCode,coordinateType,"WZ_CORPORELS",pageNo,pageSize);
//				}
//			}
//			long endTime = System.currentTimeMillis();
//			System.out.println("===========totalTime================"+(endTime-startTime));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("获取台风影响单个标的坐标信息异常：" + e.getMessage(), e);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：查询台风预计影响信息
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public List<DangerManageMapInfo> querySubjectCorporeInfo(String tfbh,String comCode,
//			String coordinateType,String tableName,int pageNo,int pageSize){
//		long startTime = System.currentTimeMillis();
//		Connection connect = null;
//		PreparedStatement preState = null;
//        ResultSet resultSet = null;
//        int endCount = pageNo*pageSize;
//        int startCount =(pageNo-1)*pageSize;
//        List<DangerManageMapInfo> dangerManageMapInfoList = new ArrayList<DangerManageMapInfo>();
//		try {
//			String appendSql ="1=1 ";
//			String comCodeSql ="";
//			if(StringUtils.isNotBlank(comCode)){
//				//判断该编码 是何种编码类型,然后根据编码类型来进行查询，编码类型只有省市两种类型,且省级编码开头两位数，剩余是四个零。
//				if(comCode.trim().endsWith("0000")){
//					Map<String, List<String>> mapCode =  this.operateCityProCode();
//					long mapTime = System.currentTimeMillis();
//					System.out.println("============处理地图所用时间=============="+(mapTime-startTime));
//					List<String> mapList = mapCode.get(comCode.trim());
//					String[] mapArray = new String[mapList.size()];
//					mapList.toArray(mapArray);
//					String mapStr = StringUtils.join(mapArray , ",");
////					String mapStr ="";
////					for(int i=0;i< mapList.size(); i++){
////						if(i==(mapList.size()-1)){
////							mapStr +="'"+mapList.get(i)+"'";
////						}else {
////							mapStr +="'"+mapList.get(i)+"',";
////						}
////					}
////					comCodeSql += "and cor.cityCode in (" +mapStr+") ";
//					comCodeSql += "and cityCode in (" +mapStr+") ";
//				}else {
////					comCodeSql += "and cor.cityCode = '" +comCode.trim()+"' ";
//					comCodeSql += "and cityCode = '" +comCode.trim()+"' ";
//				}
//			}
//			if(StringUtils.isNotBlank(tfbh)){
//				appendSql +=  " and cor.tfbh='"+tfbh.trim()+"' ";
//			}
//			String dateSql=this.organzieDateSql();
//			String  searchSql = "select * from (select sum(main.sumamount) as sumamount ,cor.MID,cor.tfbh,cor.POINTX_2000,cor.POINTY_2000,cor.POINTX_02,cor.POINTY_02,cor.ADDRESSNAME,cor.cityName,cor.CITYECODE,cor.PRONAME,cor.PROVINCECODE,main.classCode,cor.cityCode from "+tableName+" cor , riskMap_main main "
//					+ "where "+appendSql+" and " + dateSql
//					+ " and cor.MID = main.ADDRESSID   group by cor.MID ,cor.tfbh,cor.POINTX_2000,cor.POINTY_2000,cor.POINTX_02,cor.POINTY_02,cor.ADDRESSNAME,cor.cityName,cor.CITYECODE,cor.PRONAME,cor.PROVINCECODE,main.classCode,cor.cityCode)  res where sumamount>100000000 "+comCodeSql;
//			String beginSql = "select * from(select a.*,ROWNUM rn from("+searchSql+
//					") a where ROWNUM<=(?)) where rn>?";
//			connect = slaveJdbcTemplate.getDataSource().getConnection();
//			preState = connect.prepareStatement(beginSql);
//            preState.setInt(1, endCount);
//            preState.setInt(2, startCount);
//            resultSet = preState.executeQuery();
//            long amongTime = System.currentTimeMillis();
//            System.out.println("===========查询时间among==========="+(amongTime-startTime));
//           //第五步：处理结果集
//            List<Integer> integers = new ArrayList<Integer>();
//            for(int i=1;resultSet.next();i++) {
//            	DangerManageMapInfo dangerManageMapInfo =new DangerManageMapInfo();
//            	BigDecimal sumAmount = resultSet.getBigDecimal(1);
//            	int mid = resultSet.getInt(2);
//            	String tfbhResult = resultSet.getString(3);
//            	String addressName = resultSet.getString(8);
//            	dangerManageMapInfo.setTotalAmount(sumAmount);
//				dangerManageMapInfo.setTfbh(tfbhResult);
//				dangerManageMapInfo.setAddressId(mid);
//				integers.add(mid);
////				List<String> InsuredNameList = this.queryInsuredName(mid);
////				dangerManageMapInfo.setInsuredName(InsuredNameList);
//				if(StringUtils.isNotBlank(tfbhResult)){
//					if("02".equals(coordinateType.trim())){
//						BigDecimal pointx_02 = resultSet.getBigDecimal(6);
//						BigDecimal pointy_02 = resultSet.getBigDecimal(7);
//						dangerManageMapInfo.setLongitude(pointx_02);
//						dangerManageMapInfo.setLatitude(pointy_02);
//					}else if("2000".equals(coordinateType.trim())){
//						BigDecimal pointx_2000 = resultSet.getBigDecimal(4);
//						BigDecimal pointy_2000 = resultSet.getBigDecimal(5);
//						dangerManageMapInfo.setLongitude(pointx_2000);
//						dangerManageMapInfo.setLatitude(pointy_2000);
//					}
//				}
//				dangerManageMapInfo.setAddressName(addressName);
//				dangerManageMapInfoList.add(dangerManageMapInfo);
//			}
//            List<RiskMapInsured> riskMapInsuredList =  this.queryInsuredNameList(integers);
//            for (DangerManageMapInfo dangerManageMapInfo: dangerManageMapInfoList){
//				String addressId = dangerManageMapInfo.getAddressId().toString();
//				List<String> codeList = new ArrayList<String>();
//				List<String> nameList = new ArrayList<String>();
//				for(RiskMapInsured riskMapInsured:riskMapInsuredList){
//					RiskMapInsuredId id = riskMapInsured.getId();
//					String  insuredCode = riskMapInsured.getInsuredCode();
//					String  insuredName = riskMapInsured.getInsuredName();
//					String addressIdInsured= id.getAddressID().toString();
//					if(addressId.equals(addressIdInsured)&&StringUtils.isNotBlank(insuredCode)&&!codeList.contains(insuredCode.trim())){
//						codeList.add(insuredCode.trim());
//						if(StringUtils.isNotBlank(insuredName)){
//							nameList.add(insuredName.trim());
//						}
//					}
//				}
//				dangerManageMapInfo.setInsuredName(nameList);
//			}
//            long endTime = System.currentTimeMillis();
//            System.out.println("===========处理时间after==========="+(endTime-amongTime));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("组织台风预计影响信息失败：" + e.getMessage(), e);
//		}finally{
//			releaseResources(preState, connect, resultSet);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：对日期sql进行组织
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200510
//	 * @修改记录：
//	 */
//	public String organzieDateSql (){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
//		Date date = new Date();
//		String tfdate = sdf.format(date);
//		String tftime = sdfHour.format(date);
//		//对日期sql进行组织
//		String sql =" (main.startdate<to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
//				+ " or (main.startdate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and main.starthour <='" +tftime+ "')) and (enddate>to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
//				+ " or (main.enddate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and main.endhour >='" +tftime+ "')) ";
//		return sql;
//	}
//	/**
//	 * @功能：获取台风影响标的信息,根据保额的范围（供大灾调度平台使用）
//	 * @param tfbh: 台风编号,  isHappen: 0（预计影响）1（已影响）,
//	 * comCode: 地区编码,coordinateType: 坐标类型(02坐标系/2000坐标系/84坐标系)
//	 * minCoverage:最小保额，maxCoverage: 最大保额
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200326
//	 * @修改记录：
//	 */
//	public List<DangerManageMapInfo> queryTyAffectInfo(String tfbh,String isHappen,
//			String comCode,String coordinateType,BigDecimal minCoverage,
//			BigDecimal maxCoverage,Integer pageNo,Integer pageSize){
//		long startTime  = System.currentTimeMillis();
//		List<DangerManageMapInfo> dangerManageMapInfoList =new ArrayList<DangerManageMapInfo>();
//		try {
//			if(StringUtils.isNotBlank(isHappen)){
//				if("0".equals(isHappen.trim())){
//					// 预计影响，查询WZ_CORPOREYJ表
//					dangerManageMapInfoList = this.queryAffectCorporeInfo(tfbh,comCode,coordinateType,"WZ_CORPOREYJ",minCoverage,maxCoverage,pageNo,pageSize);
//				}else if("1".equals(isHappen.trim())){
//					// 已影响，查询WZ_CORPORELS表
//					dangerManageMapInfoList = this.queryAffectCorporeInfo(tfbh,comCode,coordinateType,"WZ_CORPORELS",minCoverage,maxCoverage,pageNo,pageSize);
//				}
//			}
//			long endTime  = System.currentTimeMillis();
//			System.out.println("======queryTyAffectInfo==totalTime==============="+(endTime-startTime));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("获取台风影响标的信息异常：" + e.getMessage(), e);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：获取台风影响标的信息组织
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200510
//	 * @修改记录：
//	 */
//	public List<DangerManageMapInfo> queryAffectCorporeInfo(String tfbh,String comCode,
//			String coordinateType,String tableName,BigDecimal minCoverage,
//			BigDecimal maxCoverage,Integer pageNo,Integer pageSize){
//		Connection connect = null;
//		PreparedStatement preState = null;
//        ResultSet resultSet = null;
//        
//        List<DangerManageMapInfo> dangerManageMapInfoList = new ArrayList<DangerManageMapInfo>();
//		try {
//			String appendSql ="1=1 ";
//			if(StringUtils.isNotBlank(comCode)){
//				//判断该编码 是何种编码类型,然后根据编码类型来进行查询，编码类型只有省市两种类型,且省级编码开头两位数，剩余是四个零。
//				if(comCode.trim().endsWith("0000")){
//					Map<String, List<String>> mapCode =  this.operateCityProCode();
//					List<String> mapList = mapCode.get(comCode.trim());
//					String[] mapArray = new String[mapList.size()];
//					mapList.toArray(mapArray);
//					String mapStr = StringUtils.join(mapArray , ",");
//					appendSql += "and cor.cityCode in (" +mapStr+") ";
//				}else {
//					appendSql += "and cor.cityCode = '" +comCode.trim()+"' ";
//				}
//			}
//			if(StringUtils.isNotBlank(tfbh)){
//				appendSql +=  " and cor.tfbh='"+tfbh.trim()+"' ";
//			}
//			//根据保额范围来进行限定的sql过滤条件
//			String outSideSql = "";
//			if(minCoverage!=null&&maxCoverage!=null){
//				outSideSql = "where sumamount>="+minCoverage+" and sumamount<"+maxCoverage;
//			}else if(minCoverage!=null){
//				outSideSql = "where sumamount>="+minCoverage;
//			}else if(maxCoverage!=null){
//				outSideSql = "where sumamount<"+maxCoverage;
//			}
//			String dateSql=this.organzieDateSql();
//			String  searchSql = "select * from (select sum(main.sumamount) as sumamount ,cor.MID,cor.tfbh,cor.POINTX_2000,cor.POINTY_2000,cor.POINTX_02,cor.POINTY_02,cor.ADDRESSNAME,cor.cityName,cor.CITYECODE,cor.PRONAME,cor.PROVINCECODE from "+tableName+" cor , riskMap_main main "
//					+ "where "+appendSql+" and " + dateSql
//					+ " and cor.MID = main.ADDRESSID   group by cor.MID ,cor.tfbh,cor.POINTX_2000,cor.POINTY_2000,cor.POINTX_02,cor.POINTY_02,cor.ADDRESSNAME,cor.cityName,cor.CITYECODE,cor.PRONAME,cor.PROVINCECODE)  res "
////					+ "where sumamount>="+minCoverage+" and sumamount<"+maxCoverage;
//					+ outSideSql;
//			String beginSql = "select * from(select a.*,ROWNUM rn from("+searchSql+
//					") a where ROWNUM<=(?)) where rn>?";
//			connect = slaveJdbcTemplate.getDataSource().getConnection();
//			if(pageSize !=null&& pageNo !=null){
//				int endCount = pageNo*pageSize;
//		        int startCount =(pageNo-1)*pageSize;
//				//同时拥有页码和每页条数，则执行分页查询
//				preState = connect.prepareStatement(beginSql);
//				preState.setInt(1, endCount);
//	            preState.setInt(2, startCount);
//			}else {
//				preState = connect.prepareStatement(searchSql);
//			}
//            
//            resultSet = preState.executeQuery();
//            String isHappen="";
//            if("WZ_CORPOREYJ".equals(tableName)){
//            	isHappen = "0";
//			}else if("WZ_CORPORELS".equals(tableName)){
//				isHappen = "1";
//			}
//           //第五步：处理结果集
//            List<Integer> integers = new ArrayList<Integer>();
//            for(int i=1;resultSet.next();i++) {
//            	DangerManageMapInfo dangerManageMapInfo =new DangerManageMapInfo();
//            	BigDecimal sumAmount = resultSet.getBigDecimal(1);
//            	int mid = resultSet.getInt(2);
//            	String tfbhResult = resultSet.getString(3);
//            	String addressName = resultSet.getString(8);
//            	String cityName = resultSet.getString(9);
//            	String cityCode = resultSet.getString(10);
//            	String proName = resultSet.getString(11);
//            	String proCode = resultSet.getString(12);
//            	dangerManageMapInfo.setCityName(cityName);
//            	dangerManageMapInfo.setCityCode(cityCode);
//            	dangerManageMapInfo.setProName(proName);
//            	dangerManageMapInfo.setProCode(proCode);
//            	dangerManageMapInfo.setIsHappen(isHappen);
//            	dangerManageMapInfo.setTotalAmount(sumAmount);
//				dangerManageMapInfo.setTfbh(tfbhResult);
//				dangerManageMapInfo.setAddressId(mid);
//				integers.add(mid);
////				List<String> InsuredNameList = this.queryInsuredName(mid);
////				dangerManageMapInfo = this.searchGAndQAmount(tfbh,mid,dateSql,tableName,dangerManageMapInfo);
////				dangerManageMapInfo.setInsuredName(InsuredNameList);
//				if(StringUtils.isNotBlank(tfbhResult)){
//					if("02".equals(coordinateType.trim())){
//						BigDecimal pointx_02 = resultSet.getBigDecimal(6);
//						BigDecimal pointy_02 = resultSet.getBigDecimal(7);
//						dangerManageMapInfo.setLongitude(pointx_02);
//						dangerManageMapInfo.setLatitude(pointy_02);
//					}else if("2000".equals(coordinateType.trim())){
//						BigDecimal pointx_2000 = resultSet.getBigDecimal(4);
//						BigDecimal pointy_2000 = resultSet.getBigDecimal(5);
//						dangerManageMapInfo.setLongitude(pointx_2000);
//						dangerManageMapInfo.setLatitude(pointy_2000);
//					}
//				}
//				dangerManageMapInfo.setAddressName(addressName);
//				dangerManageMapInfoList.add(dangerManageMapInfo);
//			}
////            List<String> InsuredNameList = this.queryInsuredNameList(integers);
//            List<RiskMapInsured> riskMapInsuredList =  this.queryInsuredNameList(integers);
//			List<DangerManageMapInfo> dangerManageMapInfoAmountList = this.searchGAndQAmount(tfbh,integers,dateSql,tableName);
//            for (DangerManageMapInfo dangerManageMapInfo: dangerManageMapInfoList){
//				String addressId = dangerManageMapInfo.getAddressId().toString();
//				List<String> codeList = new ArrayList<String>();
//				List<String> nameList = new ArrayList<String>();
//				for(RiskMapInsured riskMapInsured:riskMapInsuredList){
//					RiskMapInsuredId id = riskMapInsured.getId();
//					String  insuredCode = riskMapInsured.getInsuredCode();
//					String  insuredName = riskMapInsured.getInsuredName();
//					String addressIdInsured= id.getAddressID().toString();
//					if(addressId.equals(addressIdInsured)&&StringUtils.isNotBlank(insuredCode)&&!codeList.contains(insuredCode.trim())){
//						codeList.add(insuredCode.trim());
//						if(StringUtils.isNotBlank(insuredName)){
//							nameList.add(insuredName.trim());
//						}
//					}
//				}
//				dangerManageMapInfo.setInsuredName(nameList);
//				for (DangerManageMapInfo dangerManageNew: dangerManageMapInfoAmountList){
//					String addressIdNew= dangerManageNew.getAddressId().toString();
//					if(addressId.equals(addressIdNew)){
////						nameList.add(insuredName.trim());
//						dangerManageMapInfo.setgAmount(dangerManageNew.getgAmount());
//						dangerManageMapInfo.setqAmount(dangerManageNew.getqAmount());
//					}
//				}
//			}
//            
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("组织台风预计影响信息失败：" + e.getMessage(), e);
//		}finally{
//			releaseResources(preState, connect, resultSet);
//		}
//		return dangerManageMapInfoList;
//	}
//	/**
//	 * @功能：重新组织返回信息
//	 * @return void
//	 * @author liqiankun
//	 * @时间：20200510
//	 * @修改记录：
//	 */
//	public List<DangerManageMapInfo> searchGAndQAmount(String tfbh,List<Integer> addressIdList,String dateSql,String tableName){
//		List<DangerManageMapInfo>  dangerManageMapInfoList = new ArrayList<DangerManageMapInfo>();
//		Connection connect = null;
//		PreparedStatement preState = null;
//        ResultSet resultSet = null;
//        try {
//        	if(addressIdList==null || addressIdList.size() == 0){
//        		return dangerManageMapInfoList;
//        	}
//        	String addressIdSql = " and ( ";
//			String appendSql = "";
//			int count = addressIdList.size()/500;
//			if(count==0){
//				appendSql+=  "main.ADDRESSID in ("+ StringUtils.join(addressIdList, ",")+")";
//			}
//			for (int i=1;i<=count;i++){
//				List<Integer> countList = null;
//				if(i<count){
//					countList = addressIdList.subList(0, i*500);
//					appendSql+=  "( main.ADDRESSID in (" + StringUtils.join(countList, ",") +") ) or ";
//				}else {
//					countList = addressIdList.subList((i-1)*500, addressIdList.size());
//					appendSql+=  " ( main.ADDRESSID in ( " + StringUtils.join(countList, ",") +" ))";
//				}
//			}
//			addressIdSql += appendSql+" )";
////        	String sql= "select sum(sumamount)  as sumamount,main.classCode, main.ADDRESSID from  "+
////					"riskMap_main main where "+dateSql+" and main.ADDRESSID="+mid+" group by main.ADDRESSID,main.classCode";
//        	String sql= "select sum(sumamount)  as sumamount,main.classCode, main.ADDRESSID as ADDRESSID from  "+
//					"riskMap_main main where "+dateSql+ addressIdSql +" group by main.ADDRESSID,main.classCode";
//			connect = slaveJdbcTemplate.getDataSource().getConnection();
//			preState = connect.prepareStatement(sql);
//			resultSet = preState.executeQuery();
//			
//			//第五步：处理结果集
//			for(int i=1;resultSet.next();i++) {
//				DangerManageMapInfo dangerManageMapInfo = new DangerManageMapInfo();
//				BigDecimal gAmount = BigDecimal.ZERO;
//				BigDecimal qAmount = BigDecimal.ZERO;
//				BigDecimal sumamount= resultSet.getBigDecimal(1);
//				String classCode=resultSet.getString(2);
//				Integer addressId=resultSet.getInt(3);
//				if("01".equals(classCode) && sumamount!=null){
//					qAmount = qAmount.add(sumamount);
//				}
//				if("03".equals(classCode) && sumamount!=null){
//					gAmount = gAmount.add(sumamount);
//				}
//				dangerManageMapInfo.setqAmount(qAmount);
//				dangerManageMapInfo.setgAmount(gAmount);
//				dangerManageMapInfo.setAddressId(addressId);
//				dangerManageMapInfoList.add(dangerManageMapInfo);
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			releaseResources(preState, connect, resultSet);
//		}
//        return dangerManageMapInfoList;
//	}
//	
//	
//	
//}