package com.picc.riskctrl.map.service.spring;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.picc.riskctrl.common.jpa.condition.Restrictions;
import com.picc.riskctrl.common.jpa.vo.Criteria;
import com.picc.riskctrl.common.response.RiskResponseVo;
import com.picc.riskctrl.common.util.BeanUtils;
import com.picc.riskctrl.common.util.MapUtils;
import com.picc.riskctrl.map.dao.TyphoonLatestInfoRepository;
import com.picc.riskctrl.map.dao.WzCorporeLsPRepository;
import com.picc.riskctrl.map.dao.WzCorporeLsRepository;
import com.picc.riskctrl.map.dao.WzCorporeYjPRepository;
import com.picc.riskctrl.map.dao.WzCorporeYjRepository;
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
import com.picc.riskctrl.map.vo.RiskMapAddressField;
import com.picc.riskctrl.map.vo.WzTFLsljVo;
import com.picc.riskctrl.map.vo.WzTFYbljVo;
import com.picc.riskctrl.map.vo.request.WZ_tfbh;
import com.picc.riskctrl.map.vo.response.Wz_Corporels_byMid;
import com.picc.riskctrl.map.vo.response.dangerManage.DangerManageMapInfo;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonBaseInfo;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonPath;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.GeoRegion;
import com.supermap.data.Geometry;
import com.supermap.data.LinkItem;
import com.supermap.data.LinkItems;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.SpatialQueryMode;
import com.supermap.data.Workspace;

import lombok.extern.slf4j.Slf4j;


//@Rpc
@Slf4j
@Service(value = "mapWarnService")
public class MapWarnServiceSpringImpl implements  MapWarnService{
	
//	public static final Logger log = LoggerFactory.getLogger("mapWarnLog");
	
	@Autowired
	JdbcTemplate slaveJdbcTemplate;
	
//	@Autowired
//	DatabaseDao databaseDao;
	
//	@Autowired
//	MapService mapService;
	
	@Autowired
	private WzCorporeLsPRepository wzCorporeLsPRepository;
	
	@Autowired
	private WzCorporeYjPRepository wzCorporeYjPRepository;
	
	@Autowired
	private TyphoonLatestInfoRepository typhoonLatestInfoRepository;
	
	@Autowired
	private WzCorporeLsRepository wzCorporeLsRepository;
	
	@Autowired
	private WzCorporeYjRepository wzCorporeYjRepository;
	
	// 台风基本信息，台风的状态是未结束的
	// ISUSE第一位是 可以是1,2第二位可以是3,4
	private static final String sql_TFBH ="select * from WZ_TFBH where 1=1 and (substr(ISUSE,2,1) ='3' or substr(ISUSE,2,1) ='5')";
	//查询历史台风信息
	private static final String sql_TFLSLJ ="select *  from WZ_TFLSLJ where substr(ISUSE,2,1) ='3' and TFBH='";
	// 更新信息
	private static final String update_TFLSLJ ="update WZ_TFLSLJ set ISUSE=replace(ISUSE,substr(ISUSE,2,1),'4')  where substr(ISUSE,2,1) ='3'";
	
	private static final String update_TFBH ="update WZ_TFBH set ISUSE=replace(ISUSE,substr(ISUSE,2,1),'4')  where  1=1 and (substr(ISUSE,2,1) ='3' or substr(ISUSE,2,1) ='5') ";
	private static final String update_TFYJLJ ="update WZ_TFYBLJ set ISUSE=replace(ISUSE,substr(ISUSE,2,1),'4')  where substr(ISUSE,2,1) ='3'";
	//查询预警台风信息
	private static final String sql_TFYBLJ ="select *  from WZ_TFYBLJ where substr(ISUSE,2,1) ='3' and TFBH= '";
//		and YBSJ = to_date(?,'yyyy-mm-dd hh24:mi:ss')
	 // 面数据集名称
	private static final String dataSetUnioin_7 = "TFUNION_7M",dataSetUnion_10="TFUNION_10M",dataSet_new="TFNew",dataSetUnion_12="TFUNION_12M";
	private static final String dataSetYJUnioin_7="TFYJUNION_7M",dataSetYJUnioin_10="TFYJUNION_10M",dataSetYJ_new="TFYJ_New",dataSetYJUnioin_12="TFYJUNION_12M";
	private static final String radiusName_7="radius7",radiusName_10="radius10",radiusName_12="radius12";
	// 点数据集名称
	private static final String riskmap_address_point="RISKMAP_ADDRESS_POINT";
	
	//查询该台风标的表中是否有该信息
//	private static final String query_CORPORELS_count ="select count(*) from WZ_CORPORELS where TFBH=? and MID=?";
	
	/**
	 * @功能：iobjectjava 
	 * @param void
	 * @return RiskResponseVo
	 * @author liqiankun
	 * @时间：20190515
	 * @修改记录：
	 */	
	public void  createRegionDataSet(){
		try {
			log.info("==========createRegionDataSet==begin=========");
			System.out.println("==========createRegionDataSet==begin=========");
			/**查询台风编号基本信息*/
			List<WZ_tfbh>  wzTfbhList =this.slaveJdbcTemplate.query(sql_TFBH, new Object[]{}, new BeanPropertyRowMapper<WZ_tfbh>(WZ_tfbh.class));
			 // 更新标志为Isuse 第二位为5 ，防止重复执行定时任务，导致用时过长
			 this.updateWzTfbhFlag("5","3");
			 System.out.println("==========查询wzTfbhList条数==========="+wzTfbhList.size());
			 System.out.println("==========查询sql语句==========="+sql_TFBH);
			// 若有信息则进行更新
			if(wzTfbhList!=null&&wzTfbhList.size()>0){
				
				Workspace workspace = new Workspace();
				// 定义数据源连接信息，假设以下所有数据源设置都存在
			    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
				//进行数据源的连接
				Datasource datasource =MapUtils.connectDataSource(workspace,datasourceconnection);
				System.out.println("==========执行WZ_tfbh循环开始===========");
				for (WZ_tfbh wz_tfbh:wzTfbhList){
					// 台风编号
					String tfbh  = wz_tfbh.getTfbh();
					// 与气象局对接标志，如果是非空，并且是为"weather",则为气象局数据，用于对处理面数据来进行区分
					String weatherFlag = wz_tfbh.getId();
					
					String sql = sql_TFLSLJ+ tfbh + "' order by RQSJ asc ";
//					查询历史台风信息
					List<WzTFLsljVo>  wzTFLsljList =this.slaveJdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<WzTFLsljVo>(WzTFLsljVo.class));
					System.out.println("==========查询WzTFLslj条数==========="+wzTFLsljList.size());
					log.info("==========查询WzTFLslj条数==========="+wzTFLsljList.size());
					if(wzTFLsljList!=null&&wzTFLsljList.size()>0){
						// 取最后一条新增的历史数据，来求响应的的预警台风数据
						
						WzTFLsljVo wzTFLslj = wzTFLsljList.get(wzTFLsljList.size()-1);
						
						// 组织省的map数据
						Map<String,String> mapProCenterPoint = getMapProCenterPoint(datasource);
						// 创建历史台风面数据
						this.createOrUpdateRegionDataSetLs(wzTFLsljList,workspace,
								datasourceconnection,datasource,mapProCenterPoint,weatherFlag);
						
						List<WzTFYbljVo>  wzTFYbljList = new ArrayList<WzTFYbljVo>();
						//预警台风数据处理
						if(wzTFLslj.getRqsj()!=null){
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
						    String rqsj = sdf.format(wzTFLslj.getRqsj());
							String sql_YJ = sql_TFYBLJ + tfbh + "' and YBSJ = to_date('"+rqsj +"','yyyy-mm-dd hh24:mi:ss') and TM='中国'";
//							String sql_YJ = sql_TFYBLJ + tfbh + " and YBSJ = to_date('2018-09-16 23:00:00','yyyy-mm-dd hh24:mi:ss') and TM='中国'";
							wzTFYbljList=this.slaveJdbcTemplate.query(sql_YJ, new Object[]{}, new BeanPropertyRowMapper<WzTFYbljVo>(WzTFYbljVo.class));
							System.out.println("========success:"+wzTFYbljList.size()+":"+sql_YJ);
							log.info("========success:"+wzTFYbljList.size()+":"+sql_YJ);
							if(wzTFYbljList!=null&&wzTFYbljList.size()>0){
								this.createOrUpdateRegionDataSetYJ(wzTFYbljList,workspace,
										datasourceconnection,datasource,mapProCenterPoint,weatherFlag);
							}
						}
					}
				}
				// 关闭地图资源
				MapUtils.closeMapResource(datasource,datasourceconnection,workspace);
//			    MapUtils.closeMapResource(null,null,null,null,null,null,
//			    		null,datasource,datasourceconnection,workspace);
				// 执行完之后，修改表中标志位
				this.slaveJdbcTemplate.update(update_TFLSLJ);
				this.slaveJdbcTemplate.update(update_TFYJLJ);
				this.slaveJdbcTemplate.update(update_TFBH);
				System.out.println("==========createRegionDataSet==end=========");
				log.info("==========createRegionDataSet==end=========");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 更新标志为Isuse 第二位为5 ，防止重复执行定时任务，导致用时过长
			 this.updateWzTfbhFlag("3","5");
			 log.info("createRegionDataSet",e.getMessage());
		}
		
	}
	
	/**
	 * @功能：iobjectjava 对预警台风数据进行更新或增加,并整理面数据和提取标的信息
	 * @param void
	 * @return RiskResponseVo
	 * @author liqiankun
	 * @时间：20190515
	 * @修改记录：
	 */
	public void  createOrUpdateRegionDataSetYJ(List<WzTFYbljVo>  wzTFYbljList,Workspace workspace,
			DatasourceConnectionInfo datasourceconnection,Datasource datasource,
			Map<String,String> mapProCenterPoint,String weatherFlag){
//		Workspace workspace = new Workspace();
//		// 定义数据源连接信息，假设以下所有数据源设置都存在
//	    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
//		//进行数据源的连接
//		Datasource datasource =MapUtils.connectDataSource(workspace,datasourceconnection);
		System.out.println("=====createOrUpdateRegionDataSetYJ===begin=========");
		log.info("=====createOrUpdateRegionDataSetYJ===begin=========");
		/**数据源不能为空*/
		try {
			if(datasource!=null){
				// 创建预警的面数据集
				WzTFYbljVo wzTFYblj =new WzTFYbljVo();
				DatasetVector datasetVectorYJ_7 = MapUtils.createDataSet(dataSetYJUnioin_7,datasource,wzTFYblj);
				DatasetVector datasetVectorYJ_10 = MapUtils.createDataSet(dataSetYJUnioin_10,datasource,wzTFYblj);
				
				DatasetVector datasetVectorYJ_12 = MapUtils.createDataSet(dataSetYJUnioin_12,datasource,wzTFYblj);
				// 新建一个面数据集，用于存储新的整合的面数据信息
				DatasetVector datasetVector_new = MapUtils.createDataSet(dataSetYJ_new,datasource,wzTFYblj);
				
				/*通过面数据提取标的信息*/
				this.getCorporeYJInfo(datasourceconnection,datasource,datasetVectorYJ_7,datasetVectorYJ_10,datasetVectorYJ_12,
						datasetVector_new,wzTFYbljList,radiusName_7,radiusName_10,radiusName_12,mapProCenterPoint,weatherFlag);
				
				/*给面数据集增加融合数据，七级面数据*/
				Recordset recordsetYJ_7= MapUtils.addUnionDataToDatasetVector(datasetVectorYJ_7,wzTFYbljList,radiusName_7,"YJ",weatherFlag);
				/*给面数据集增加融合数据，十级面数据*/
				Recordset recordsetYJ_10= MapUtils.addUnionDataToDatasetVector(datasetVectorYJ_10,wzTFYbljList,radiusName_10,"YJ",weatherFlag);
				
				Recordset recordsetYJ_12=null;
				if(StringUtils.isNotBlank(weatherFlag)&&"weather".equals(weatherFlag.trim())){
					/*给面数据集增加融合数据，十二级面数据*/
					recordsetYJ_12= MapUtils.addUnionDataToDatasetVector(datasetVectorYJ_12,wzTFYbljList,radiusName_12,"YJ",weatherFlag);
				}
				
				// 关闭地图资源
			    MapUtils.closeMapResource(recordsetYJ_10,recordsetYJ_7,recordsetYJ_12,datasetVectorYJ_12,datasetVector_new,datasetVectorYJ_10,datasetVectorYJ_7,
			    		null,null,null);
			    System.out.println("=====createOrUpdateRegionDataSetYJ===end=========");
			    log.info("=====createOrUpdateRegionDataSetYJ===end=========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @功能：iobjectjava 对台风数据进行更新或增加,并整理面数据和提取标的信息
	 * @param void
	 * @return RiskResponseVo
	 * @author liqiankun
	 * @时间：20190515
	 * @修改记录：
	 */
	public void createOrUpdateRegionDataSetLs(List<WzTFLsljVo>  wzTFLsljList,Workspace workspace,
			DatasourceConnectionInfo datasourceconnection,Datasource datasource,
			Map<String,String> mapProCenterPoint,String weatherFlag){
//		Workspace workspace = new Workspace();
//		// 定义数据源连接信息，假设以下所有数据源设置都存在
//	    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
//		//进行数据源的连接
//		Datasource datasource =MapUtils.connectDataSource(workspace,datasourceconnection);
		System.out.println("======createOrUpdateRegionDataSetLs==begin=========");
		log.info("======createOrUpdateRegionDataSetLs==begin=========");
		/**数据源不能为空*/
		try {
			if(datasource!=null){
				// 创建预警和历史的面数据集
				System.out.println("======createVector==begin=========");
				log.info("======createVector==begin=========");
				WzTFLsljVo wzTFLslj =new WzTFLsljVo();
				DatasetVector datasetVector_7 = MapUtils.createDataSet(dataSetUnioin_7,datasource,wzTFLslj);
				System.out.println("======createVector_7==begin=========");
				DatasetVector datasetVector_10 = MapUtils.createDataSet(dataSetUnion_10,datasource,wzTFLslj);
				System.out.println("======createVector_10==begin=========");
				// 十二级台风面数据
				DatasetVector datasetVector_12 = MapUtils.createDataSet(dataSetUnion_12,datasource,wzTFLslj);
				System.out.println("======createVector_12==begin=========");
				// 新建一个面数据集，用于存储新的整合的面数据信息
				DatasetVector datasetVector_new = MapUtils.createDataSet(dataSet_new,datasource,wzTFLslj);
				System.out.println("======createVector==end=========");
				log.info("======createVector==end=========");
				/*通过面数据提取标的信息*/
				this.getCorporeLsInfo(datasourceconnection,datasource,datasetVector_7,datasetVector_10,datasetVector_12,
						datasetVector_new,wzTFLsljList,radiusName_7,radiusName_10,radiusName_12,mapProCenterPoint,weatherFlag);
				System.out.println("======getCorporeLsInfo==after=========");
				log.info("======getCorporeLsInfo==after=========");
				/*给面数据集增加融合数据，七级面数据*/
				Recordset recordset_7= MapUtils.addUnionDataToDatasetVector(datasetVector_7,wzTFLsljList,radiusName_7,"LS",weatherFlag);
				/*给面数据集增加融合数据，十级面数据*/
				Recordset recordset_10= MapUtils.addUnionDataToDatasetVector(datasetVector_10,wzTFLsljList,radiusName_10,"LS",weatherFlag);
				
				Recordset recordset_12=null;
				if(StringUtils.isNotBlank(weatherFlag)&&"weather".equals(weatherFlag.trim())){
					/*给面数据集增加融合数据，十二级面数据*/
					recordset_12= MapUtils.addUnionDataToDatasetVector(datasetVector_12,wzTFLsljList,radiusName_12,"LS",weatherFlag);

				}
								
//				MapUtils.closeMapRecordResource(recordset_10,recordset_7,recordset_12,null);
				// 关闭资源
			    MapUtils.closeMapResource(recordset_10,recordset_7,recordset_12,datasetVector_12,datasetVector_new,datasetVector_10,datasetVector_7,
			    		null,null,null);
			    System.out.println("======createOrUpdateRegionDataSetLs==end=========");
			    log.info("======createOrUpdateRegionDataSetLs==end=========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
	}
	
	/**
	 * @功能：iobjectjava 提取标的信息向标的表中
	 *  将新增七级和十级台风面数据进行融合，然后和标的数据进行相交 ，然后在将融合的数据和面进行相交，
	 *	 将相交得出的不同的省或者市的数据和求出的标的数据相交 求出 不同的省的标的信息，插入库中的时候判断是否有该点的信息，没有的时候插入
	 * @param RiskMapInsuredModify
	 * @return RiskResponseVo
	 * @author liqiankun
	 * @时间：20190515
	 * @修改记录：
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public  void  getCorporeLsInfo(DatasourceConnectionInfo datasourceconnection,Datasource datasource,
			DatasetVector datasetVector_7,DatasetVector datasetVector_10,DatasetVector datasetVector_12,
			DatasetVector datasetVector_new,Object objectList,String radiusName_7,String radiusName_10,
			String radiusName_12, Map<String,String> mapProCenterPoint,String weatherFlag){
		try {
			System.out.println("=========getCorporeLsInfo===begin=========");
			log.info("=========getCorporeLsInfo===begin=========");
			/*转换成list集合*/
			List<Object> list = (List)objectList;
			/*获取编号信息*/
			String tfbh = MapUtils.getMethod(list.get(0),"tfbh").toString();
			String filter = "tfbh ="+tfbh;
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("tfbh", tfbh);
			// 点矢量集
			DatasetVector datasetVector_address = (DatasetVector)datasource.getDatasets().get("RISKMAP_ADDRESS");
			// 该台风编号的记录集
			Recordset recordset_7 = datasetVector_7.query(filter,CursorType.DYNAMIC );
			Recordset recordset_10 = datasetVector_10.query(filter,CursorType.DYNAMIC );
			/*add by liqiankun 20191012*/ 
			Recordset recordset_12 = datasetVector_12.query(filter,CursorType.DYNAMIC );
			// 新增的数据集
			System.out.println("=========datasetVector_new.truncate===begin=========");
//			datasetVector_new.truncate();
			Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
			
//			Recordset recordset_new =  null;
//			boolean riskmapFlag = false,appendFlag = false;
//			if(datasetVector_new.getRecordCount()>0){
//				System.out.println("======getin  truncate==getCorporeLsInfo====");
//				log.info("======getin  truncate===getCorporeLsInfo===");
//				recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
//				if(recordset_new!=null){
//					riskmapFlag = recordset_new.deleteAll();
//				}
//			}else {
//				riskmapFlag = true;
//			}
			
			List<Geometry> geometryList= new ArrayList<Geometry>();
			List<Geometry> geoList_7=new ArrayList<Geometry>();
			System.out.println("======getin  truncate==getCorporeLsInfo====");
			log.info("======getin  truncate===getCorporeLsInfo===");
			// 判断是否是气象局数据
			if(StringUtils.isNotBlank(weatherFlag)&&"weather".equals(weatherFlag.trim())){
				// 通过半径计算
				geoList_7= MapUtils.getPieGeometryList(list,radiusName_7,"");
				List<Geometry> geoList_10=MapUtils.getPieGeometryList(list,radiusName_10,"");
				/*add by liqiankun 20191012*/ 
				List<Geometry> geoList_12=MapUtils.getPieGeometryList(list,radiusName_12,"");
				
				geometryList.addAll(geoList_7);
				geometryList.addAll(geoList_10);
				geometryList.addAll(geoList_12);
			}else {
//				// 通过半径计算
				geoList_7= MapUtils.getGeometryList(list,radiusName_7,"");
				List<Geometry> geoList_10=MapUtils.getGeometryList(list,radiusName_10,"");
//				/*add by liqiankun 20191012*/ 
//				List<Geometry> geoList_12=MapUtils.getGeometryList(list,radiusName_12,"");
				geometryList.addAll(geoList_7);
				geometryList.addAll(geoList_10);
			}
			System.out.println("=========MapUtils.unionGeometryList===begin=========");
			// 将新增的数据集进行整合成一个面数据
			MapUtils.unionGeometryList(geometryList,recordset_new,map);
			System.out.println("=========datasetVector_new.getRecordCount()=begin========");
			// 获取需要插入标的表中的省市县的信息集合
			if(datasetVector_new.getRecordCount()>0){
				// 获取省的面数据信息
				DatasetVector datasetVector_p = (DatasetVector)datasource.getDatasets().get("china_province");
				// 获取市的面数据信息
				DatasetVector datasetVector_c = (DatasetVector)datasource.getDatasets().get("china_city");
				List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
				// 查询出所有的需要新增的省市的标的信息
				long startDate = System.currentTimeMillis();
				// 创建新的点数据集
				RiskMapAddressField riskMapAddressField =new RiskMapAddressField();
				DatasetVector riskmap_address_new = MapUtils.createDataSet(riskmap_address_point,datasource,riskMapAddressField);
				// 获取整理各个省/市的标的信息 集合
//				List<WzCorporeLs> wzCorporeLsLis_p =  getWzCorporeLsList(riskmap_address_new,datasetVector_address,datasetVector_p,datasetVector_new,tfbh,"1","WZ_CORPORELS");
				long amongDate = System.currentTimeMillis();
				System.out.println("========第一次时间："+(amongDate-startDate));
				Map<String, ArrayList<WzCorporeLs>>  mapLs=  getWzCorporeLsList(datasourceconnection,riskmap_address_new,datasetVector_address,datasetVector_c,datasetVector_new,tfbh,"2","WZ_CORPORELS",mapProCenterPoint);
				long endDate = System.currentTimeMillis();
				System.out.println("========第二次时间："+(endDate-startDate));
				// 获取台风七级风圈的标的信息 WZ_CORPORELS_FQ
				MapUtils.unionGeometryList(geoList_7,recordset_new,map);
				//风圈融合影响范围和七级风圈影像范围是一样的，所以影响标的应该也是一样的
//				ArrayList<WzCorporeLs> wzCorporeLsFqList = getWzCorporeLsFqList(datasourceconnection,riskmap_address_new,
//						datasetVector_address,datasetVector_new,tfbh,"3","WZ_CORPORELS_FQ");
				ArrayList<WzCorporeLs> wzCorporeLsFqList =mapLs.get("corporeLSC");
				long endDateFq = System.currentTimeMillis();
				System.out.println("========第三次时间："+(endDateFq-startDate));
				mapLs.put("corporeLSF", wzCorporeLsFqList);
				List<WzCorporeLs> wzCorporeLsLis_c = mapLs.get("corporeLSC");
//				wzCorporeLsList.addAll(wzCorporeLsLis_p);
				wzCorporeLsList.addAll(wzCorporeLsLis_c);
				// 生成标的组织信息mapService.
				log.info("======需要处理sumAmount的个数是======fq"+wzCorporeLsFqList.size()+"Ls:"+wzCorporeLsLis_c.size());
				this.sumAmount(mapLs);
				
				// 插入风圈历史标的的sql
				String sql_insert ="insert into WZ_CORPORELS(TFBH,MID,CITYCODE,ADDRESSNAME,VALIDSTATUS,CENTERX,CENTERY,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02,CITYNAME,PRONAME,COUNTYCODE,COUNTYNAME,CITYECODE,PROVINCECODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				// 插入风圈标的的sql
				String sql_insert_fq ="insert into WZ_CORPORELS_FQ(TFBH,MID,CITYCODE,ADDRESSNAME,VALIDSTATUS,CENTERX,CENTERY,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02) values (?,?,?,?,?,?,?,?,?,?,?)";
				
				String sql_delete = "delete from WZ_CORPORELS_FQ where TFBH= '"+tfbh+"'";
				List<Object[]> insertList = new ArrayList<>();
				
				if( wzCorporeLsList!=null&& wzCorporeLsList.size()>0){
					for (WzCorporeLs wzCorporeLs : wzCorporeLsList) {
			            insertList.add(new Object[]{
			            		wzCorporeLs.getId().getTfbh(), wzCorporeLs.getId().getMid(), wzCorporeLs.getCityCode(),
			            		wzCorporeLs.getAddressName(), wzCorporeLs.getValidStatus(),wzCorporeLs.getCenterX(),
			            		wzCorporeLs.getCenterY(),wzCorporeLs.getPointx_2000(),wzCorporeLs.getPointy_2000(),
			            		wzCorporeLs.getPointx_02(),wzCorporeLs.getPointy_02(),wzCorporeLs.getCityName(),
			            		wzCorporeLs.getProName(),wzCorporeLs.getCountyCode(),wzCorporeLs.getCountyName(),
			            		wzCorporeLs.getCityEcode(),wzCorporeLs.getProvinceCode()});
			        }
					// 执行批量插入标的数据
					this.slaveJdbcTemplate.batchUpdate(sql_insert,insertList);
				}
				// 从列表中移除所有元素
				insertList.clear();
				if( wzCorporeLsFqList!=null&& wzCorporeLsFqList.size()>0){
					for (WzCorporeLs wzCorporeLsFq : wzCorporeLsFqList) {
			            insertList.add(new Object[]{
			            		wzCorporeLsFq.getId().getTfbh(), wzCorporeLsFq.getId().getMid(), wzCorporeLsFq.getCityCode(),
			            		wzCorporeLsFq.getAddressName(), wzCorporeLsFq.getValidStatus(),
			            		wzCorporeLsFq.getCenterX(), wzCorporeLsFq.getCenterY(),wzCorporeLsFq.getPointx_2000(),wzCorporeLsFq.getPointy_2000(),
			            		wzCorporeLsFq.getPointx_02(),wzCorporeLsFq.getPointy_02()});
			        }
					/*历史风圈数据是直接添加的，根据表中是否存在添加的，预警的则直接删掉之后重新添加*/
					//先删除我们的台风编号对应的风圈标的信息
					this.slaveJdbcTemplate.update(sql_delete);
					// 执行批量插入标的数据
					this.slaveJdbcTemplate.batchUpdate(sql_insert_fq,insertList);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("=====获取预警标的信息====="+datasetVector_new.getRecordCount());
		log.info("=====获取预警标的信息====="+datasetVector_new.getRecordCount());
		
	}
	/**
	 * @功能：iobjectjava 提取标的信息向标的表中， 预警信息
	 *  将新增七级和十级台风面数据进行融合，然后和标的数据进行相交 ，然后在将融合的数据和面进行相交，
	 *	 将相交得出的不同的省或者市的数据和求出的标的数据相交 求出 不同的省的标的信息，插入库中的时候判断是否有该点的信息，没有的时候插入
	 * @param void
	 * @return RiskResponseVo
	 * @author liqiankun
	 * @时间：20190515
	 * @修改记录：
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public  void  getCorporeYJInfo(DatasourceConnectionInfo datasourceconnection,Datasource datasource,
			DatasetVector datasetVectorYJ_7,DatasetVector datasetVectorYJ_10,DatasetVector datasetVectorYJ_12,
			 DatasetVector datasetVector_new,Object objectList,String radiusName_7,String radiusName_10,
			 String radiusName_12,Map<String,String> mapProCenterPoint,String weatherFlag){
		try {
			/*转换成list集合*/
			List<Object> list = (List)objectList;
			// 点矢量集
			DatasetVector datasetVector_address = (DatasetVector)datasource.getDatasets().get("RISKMAP_ADDRESS");
			// 获取省的面数据信息
			DatasetVector datasetVector_p = (DatasetVector)datasource.getDatasets().get("china_province");
			// 获取市的面数据信息
			DatasetVector datasetVector_c = (DatasetVector)datasource.getDatasets().get("china_city");
			
			// 新增的数据集
			Recordset recordset_new = datasetVector_new.getRecordset(false, CursorType.DYNAMIC);
			/*进行map数据组织*/
			Map<String,List<Object>> mapObject = MapUtils.getObjectjMap(list);
			for(Map.Entry<String, List<Object>>  mList : mapObject.entrySet()){
				List<Object> objList= mList.getValue();
				if(objList!=null&&objList.size()>0){
					/*获取编号信息*/
					String tfbh = MapUtils.getMethod(list.get(0),"tfbh").toString();
					String filter = "TFBH ="+tfbh+" and TM='"+mList.getKey()+"'";
					Map<String , Object> map = new HashMap<String, Object>();
					map.put("tfbh", tfbh);
					map.put("TM", mList.getKey());
					// 该台风编号的记录集
					Recordset recordset_7 = datasetVectorYJ_7.query(filter,CursorType.DYNAMIC );
					Recordset recordset_10 = datasetVectorYJ_10.query(filter,CursorType.DYNAMIC );
					
					Recordset recordset_12 = datasetVectorYJ_12.query(filter,CursorType.DYNAMIC );
					
					List<Geometry> geometryList= new ArrayList<Geometry>();
					List<Geometry> geoList_7= new ArrayList<Geometry>();
					if(StringUtils.isNotBlank(weatherFlag)&&"weather".equals(weatherFlag.trim())){
						// 通过半径计算
						geoList_7= MapUtils.getPieGeometryList(objList,radiusName_7,"");
						List<Geometry> geoList_10=MapUtils.getPieGeometryList(objList,radiusName_10,"");
						List<Geometry> geoList_12=MapUtils.getPieGeometryList(objList,radiusName_12,"");
						geometryList.addAll(geoList_7);
						geometryList.addAll(geoList_10);
						geometryList.addAll(geoList_12);
					}else {
//						// 通过半径计算
						geoList_7= MapUtils.getGeometryList(objList,radiusName_7,"");
						List<Geometry> geoList_10=MapUtils.getGeometryList(objList,radiusName_10,"");
//						List<Geometry> geoList_12=MapUtils.getGeometryList(objList,radiusName_12,"");
						geometryList.addAll(geoList_7);
						geometryList.addAll(geoList_10);
//						geometryList.addAll(geoList_12);
					}
					
					// 将新增的数据集进行整合成一个面数据
					MapUtils.unionGeometryList(geometryList,recordset_new,map);
					// 获取需要插入标的表中的省市县的信息集合
					if(datasetVector_new.getRecordCount()>0){
						// 历史标的的字段和预警标的的字段一致，现在使用历史标的存储预警标的的数据
						List<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
						// 查询出所有的需要新增的省市的标的信息
						long startDate = System.currentTimeMillis();
						// 创建新的点数据集
						RiskMapAddressField riskMapAddressField =new RiskMapAddressField();
						DatasetVector riskmap_address_new = MapUtils.createDataSet(riskmap_address_point,datasource,riskMapAddressField);
						// 获取整理各个省/市的标的信息 集合
//						List<WzCorporeLs> wzCorporeLsLis_p =  getWzCorporeLsList(riskmap_address_new,datasetVector_address,datasetVector_p,datasetVector_new,tfbh,"1","WZ_CORPOREYJ");
						long amongDate = System.currentTimeMillis();
						System.out.println("========第一次时间："+(amongDate-startDate));
						Map<String, ArrayList<WzCorporeLs>>  mapLs =  getWzCorporeLsList(datasourceconnection,riskmap_address_new,datasetVector_address,datasetVector_c,datasetVector_new,tfbh,"2","WZ_CORPOREYJ",mapProCenterPoint);
						long endDate = System.currentTimeMillis();
						System.out.println("========第二次时间："+(endDate-startDate));
						// 获取台风七级风圈的标的信息 WZ_CORPORELS_FQ
						MapUtils.unionGeometryList(geoList_7,recordset_new,map);
//						ArrayList<WzCorporeLs> wzCorporeLsFqList = getWzCorporeLsFqList(datasourceconnection,riskmap_address_new,datasetVector_address,datasetVector_new,tfbh,"3","WZ_CORPOREYJ_FQ");
						ArrayList<WzCorporeLs> wzCorporeLsFqList =mapLs.get("corporeYJC");
						long endDateFq = System.currentTimeMillis();
						System.out.println("========第三次时间："+(endDateFq-startDate));
						List<WzCorporeLs> wzCorporeLsLis_c = mapLs.get("corporeYJC");
						mapLs.put("corporeYJF", wzCorporeLsFqList);
						
//						wzCorporeLsList.addAll(wzCorporeLsLis_p);
						wzCorporeLsList.addAll(wzCorporeLsLis_c);
						// mapService.
						this.sumAmount(mapLs);
						
						// 插入风圈历史标的的sql
						String sql_insert ="insert into WZ_CORPOREYJ(TFBH,MID,CITYCODE,ADDRESSNAME,VALIDSTATUS,CENTERX,CENTERY,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02,CITYNAME,PRONAME,COUNTYCODE,COUNTYNAME,CITYECODE,PROVINCECODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						// 插入风圈标的的sql
						String sql_insert_fq ="insert into WZ_CORPOREYJ_FQ(TFBH,MID,CITYCODE,ADDRESSNAME,VALIDSTATUS,CENTERX,CENTERY,POINTX_2000,POINTY_2000,POINTX_02,POINTY_02) values(?,?,?,?,?,?,?,?,?,?,?)";
						
						String sql_delete = "delete from WZ_CORPOREYJ_FQ where TFBH= '"+tfbh+"'";
						
						String sql_delete_YJ = "delete from WZ_CORPOREYJ where TFBH= '"+tfbh+"'";
						
						List<Object[]> insertList = new ArrayList<>();
						
						if( wzCorporeLsList!=null&& wzCorporeLsList.size()>0){
							for (WzCorporeLs wzCorporeLs : wzCorporeLsList) {
					            insertList.add(new Object[]{
					            		wzCorporeLs.getId().getTfbh(), wzCorporeLs.getId().getMid(), wzCorporeLs.getCityCode(),
					            		wzCorporeLs.getAddressName(), wzCorporeLs.getValidStatus(),wzCorporeLs.getCenterX(),
					            		wzCorporeLs.getCenterY(),wzCorporeLs.getPointx_2000(),wzCorporeLs.getPointy_2000(),
					            		wzCorporeLs.getPointx_02(),wzCorporeLs.getPointy_02(),wzCorporeLs.getCityName(),
					            		wzCorporeLs.getProName(),wzCorporeLs.getCountyCode(),wzCorporeLs.getCountyName(),
					            		wzCorporeLs.getCityEcode(),wzCorporeLs.getProvinceCode()});
					        }
							//先删除我们的台风编号对应的标的信息
							this.slaveJdbcTemplate.update(sql_delete_YJ);
							// 执行批量插入标的数据
							this.slaveJdbcTemplate.batchUpdate(sql_insert,insertList);
						}
						// 从列表中移除所有元素
						insertList.clear();
						if( wzCorporeLsFqList!=null&& wzCorporeLsFqList.size()>0){
							for (WzCorporeLs wzCorporeLsFq : wzCorporeLsFqList) {
					            insertList.add(new Object[]{
					            		wzCorporeLsFq.getId().getTfbh(), wzCorporeLsFq.getId().getMid(), wzCorporeLsFq.getCityCode(),
					            		wzCorporeLsFq.getAddressName(), wzCorporeLsFq.getValidStatus(),
					            		wzCorporeLsFq.getCenterX(), wzCorporeLsFq.getCenterY(),wzCorporeLsFq.getPointx_2000(),wzCorporeLsFq.getPointy_2000(),
					            		wzCorporeLsFq.getPointx_02(),wzCorporeLsFq.getPointy_02()});
					        }
							//先删除我们的台风编号对应的风圈标的信息
							this.slaveJdbcTemplate.update(sql_delete);
							// 执行批量插入标的数据
							this.slaveJdbcTemplate.batchUpdate(sql_insert_fq,insertList);
						}
						
					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("=====获取预警标的信息====="+datasetVector_new.getRecordCount());
		log.info("=====获取预警标的信息====="+datasetVector_new.getRecordCount());
	}
	
	/**
	 * @功能：提取七级风圈的台风影响的标的信息
	 * @param 新建点数据集 用于存储新增点、点数据集、新增的面数据信息、台风编号、省市标志位
	 * @return 
	 * @author liqiankun
	 * @时间：20190520
	 * @修改记录：
	 */
	public ArrayList<WzCorporeLs>  getWzCorporeLsFqList(DatasourceConnectionInfo datasourceconnection,DatasetVector riskmap_address_new,DatasetVector datasetVector_address,
			DatasetVector datasetVector_new,String  tfbh,String proCityFlag,String tableName){
		ArrayList<WzCorporeLs> wzCorporeLsFqList = new ArrayList<WzCorporeLs>();
		
		QueryParameter parameter_p = new QueryParameter();
		parameter_p.setSpatialQueryObject(datasetVector_new);
		parameter_p.setAttributeFilter("VALIDSTATUS=1");
		parameter_p.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
		// 进行关联查询，过滤掉无效的标的点(当前时间已终保的单子 视为无效)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tfdate = sdf.format(new Date());
		String filter = " RISKMAP_MAIN.ENDDATE>to_date('" +tfdate+ "','yyyy-MM-dd hh24:mi:ss')";
		LinkItem linkItem = new LinkItem();
		linkItem.setConnectionInfo(datasourceconnection);
		linkItem.setForeignKeys(new String[] {"ADDRESSID"});//主数据集的外键
		linkItem.setPrimaryKeys(new String[] {"ADDRESSID"});//关联数据集的主键
		linkItem.setForeignTable("RISKMAP_MAIN");//关联数据集
		linkItem.setLinkFields(new String[] {"RISKMAP_MAIN.ADDRESSID","RISKMAP_MAIN.PROPOSALNO","RISKMAP_MAIN.ENDDATE"});//关联数据集的字段
//			linkItem.setLinkFilter("SMID < 10");//关联数据集的过滤条件 
		linkItem.setLinkFilter(filter);
		linkItem.setName("connect");
//		System.out.println("关联信息为：" + linkItem.toString());
		// 构造一个 LinkItems 对象并将 linkItem 添加到其中
		LinkItems linkItems = new LinkItems();
		linkItems.add(linkItem);
		parameter_p.setLinkItems(linkItems);
		
		Recordset queryRecordset_address = datasetVector_address.query(parameter_p);
		System.out.println("======查出LsFq的相交的标的的个数======"+proCityFlag+":"+queryRecordset_address.getRecordCount());
		log.info("======查出LsFq的相交的标的的个数======"+proCityFlag+":"+queryRecordset_address.getRecordCount());
		/*对七级台风影响的标的进行处理*/
		int j=0;
		while (!queryRecordset_address.isEOF()){
			String proposalNo = queryRecordset_address.getFieldValue("PROPOSALNO")+"";
			// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
			if(queryRecordset_address!=null&&StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){
				j++;
				// 应该循环添加
//				Integer  smid = Integer.parseInt(queryRecordset_address.getFieldValue("SmID")+"");
				Integer  smid = Integer.parseInt(queryRecordset_address.getFieldValue("ADDRESSID")+"");
				String addressName = queryRecordset_address.getFieldValue("ADDRESSNAME")+"";
				BigDecimal pointx_2000 = new BigDecimal(queryRecordset_address.getString("POINTX_2000"));
				BigDecimal pointy_2000 = new BigDecimal(queryRecordset_address.getString("POINTY_2000"));
				BigDecimal pointx_02 = new BigDecimal(queryRecordset_address.getString("POINTX_02"));
				BigDecimal pointy_02 = new BigDecimal(queryRecordset_address.getString("POINTY_02"));
				long totalCount =  getCorporeTotalCount(tfbh ,smid,proCityFlag,tableName);
				// 如果是预警则进行全部添加
				if(tableName.indexOf("YJ")>0){
					totalCount= 0;
				}
				if(totalCount==0){
					WzCorporeLs  wzCorporeLsFq =new WzCorporeLs();
					WzCorporeLsId id= new WzCorporeLsId();
					id.setTfbh(tfbh);
					id.setMid(smid);
					wzCorporeLsFq.setId(id);
//					wzCorporeLsFq.setProcityFlag(proCityFlag);
					wzCorporeLsFq.setPointx_2000(pointx_2000);
					wzCorporeLsFq.setPointy_2000(pointy_2000);
					wzCorporeLsFq.setPointx_02(pointx_02);
					wzCorporeLsFq.setPointy_02(pointy_02);
					wzCorporeLsFq.setAddressName(addressName);
					// 状态标志位为2 ，则是未生成标的保额信息
					wzCorporeLsFq.setValidStatus("2");
					wzCorporeLsFqList.add(wzCorporeLsFq);
				}
			}
			
//			System.out.println(tfbh+":"+proCityFlag+"=================="+j);
			queryRecordset_address.moveNext();
		}
		System.out.println("=====七级风圈影像有效的标的个数======="+j);
		log.info("=====七级风圈影像有效的标的个数======="+j);
		
		return wzCorporeLsFqList;
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
//			stat = conn.prepareStatement(query_CORPORELS_count);
//			stat.setString(1, tfbh);
//			stat.setInt(2, mid);
//			stat.setString(3, proCityFlag);
			rs = stat.executeQuery();
			for(;rs.next();) {
				totalCount = rs.getLong(1);
			}
		} catch (SQLException e) {
			log.info("查询异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("查询异常:"+e);
		}finally {
			releaseResources(stat, conn, rs);
		}
		
		return totalCount;
	}
	
	/**
	 * @功能：iobjectjava 整理各个省/市的标的信息 集合
	 * @param 新建点数据集 用于存储新增点,点数据集、省/市的面数据信息、新增的面数据信息、台风编号、省市标志位
	 * @return List<WzCorporeLs>
	 * @author liqiankun
	 * @时间：20190520
	 * @修改记录：
	 */
	public  Map<String, ArrayList<WzCorporeLs>>  getWzCorporeLsList(DatasourceConnectionInfo datasourceconnection,DatasetVector riskmap_address_new,DatasetVector datasetVector_address,DatasetVector datasetVector,
			DatasetVector datasetVector_new,String  tfbh,String proCityFlag,String tableName,Map<String,String> mapProCenterPoint){
		
		Map<String, ArrayList<WzCorporeLs>> map = new HashMap<String, ArrayList<WzCorporeLs>>();
		
		ArrayList<WzCorporeLs> wzCorporeLsList = new ArrayList<WzCorporeLs>();
		ArrayList<WzCorporeLs> wzCorporeLsPList = new ArrayList<WzCorporeLs>();
		
		ArrayList<WzCorporeLs> wzCorporeLsListNew = new ArrayList<WzCorporeLs>();
		ArrayList<WzCorporeLs> wzCorporeLsPListNew = new ArrayList<WzCorporeLs>();
		
		Set<WzCorporeLs> wzCorporeLsSet = new HashSet<WzCorporeLs>();
		Set<WzCorporeLs> wzCorporeLsPSet = new HashSet<WzCorporeLs>();
		
		QueryParameter parameter_p = new QueryParameter();
		parameter_p.setSpatialQueryObject(datasetVector_new);
		parameter_p.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
		
		//查询省市面信息和新增的面数据相交的信息
		Recordset queryRecordset = datasetVector.query(parameter_p);
		System.out.println(tfbh+"======查出Ls的省份的个数======"+proCityFlag+":"+queryRecordset.getRecordCount());
		log.info(tfbh+"======查出Ls的省份的个数======"+proCityFlag+":"+queryRecordset.getRecordCount());
		//通过点数据集和新增面数据集的交集的有效的点的个数
		parameter_p.setAttributeFilter("VALIDSTATUS=1");
		System.out.println("======总标的个数count========"+datasetVector_address.getRecordCount()); 
		Recordset queryRecordset_address_intersect = datasetVector_address.query(parameter_p);
		//清除矢量数据集中的所有记录,清空成功之后，添加新的查询出来的记录
//		boolean riskmapFlag =riskmap_address_new.truncate();
//		boolean appendFlag  =false;
//		if(riskmapFlag){
//			appendFlag = riskmap_address_new.append(queryRecordset_address_intersect);
//		}
		boolean riskmapFlag = false,appendFlag = false;
		if(riskmap_address_new.getRecordCount()>0){
			System.out.println("======getin  truncate======");
			log.info("======getin  truncate======");
//			riskmapFlag =riskmap_address_new.truncate();
			Recordset recordset = riskmap_address_new.getRecordset(false, CursorType.DYNAMIC);
			if(recordset!=null){
				riskmapFlag = recordset.deleteAll();
			}
		}else {
			riskmapFlag = true;
		}
		if(riskmapFlag){
			appendFlag = riskmap_address_new.append(queryRecordset_address_intersect);
		}
		if(!(riskmapFlag&&appendFlag)){
			// 如果不能够清除数据或增加数据集成功，则进行重新执行定时任务
			this.updateWzTfbhFlag("3","5");
			return map;
		}
		System.out.println("======查出Ls的相交的标的的个数======"+proCityFlag+":"+queryRecordset_address_intersect.getRecordCount());
		log.info("======查出Ls的相交的标的的个数======"+proCityFlag+":"+queryRecordset_address_intersect.getRecordCount());
		try {
			// 省份或者市进行循环
			int count =0; // 用于记录有效标的点的个数
			while (!queryRecordset.isEOF()){
				String adminCode ="",proName="";
				if(proCityFlag.equals("1")){
					adminCode = queryRecordset.getString("AdminCode");
				}else if(proCityFlag.equals("2")){
					adminCode = queryRecordset.getString("CityAdCode");
					proName = queryRecordset.getString("PRONAME");
				}
				// 中心点
				BigDecimal centerX = new BigDecimal(queryRecordset.getString("centerX"));
				BigDecimal centerY = new BigDecimal(queryRecordset.getString("centerY"));
				// 提取省市的中心点
				String adminCodeP = adminCode.substring(0, 2)+"0000";
				String  mapProCenterPointValue = mapProCenterPoint.get(adminCodeP);
				String [] mapProCenterArray = mapProCenterPointValue.split(",");
				
				String  cityName = queryRecordset.getString("Name");
				// 单个省或者市的面矢量数据集
//			DatasetVector datasetVectorP_single = queryRecordset.getDataset();
				GeoRegion geoRegion=(GeoRegion)queryRecordset.getGeometry();
//			System.out.println(adminCode+"面数据是否为空："+geoRegion.isEmpty());
//			DatasetVector datasetVector_address_intersect = queryRecordset_address_intersect.getDataset();
				
				QueryParameter parameterP_address = new QueryParameter();
				parameterP_address.setSpatialQueryObject(geoRegion);
				parameterP_address.setAttributeFilter("VALIDSTATUS=1");
				parameterP_address.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
				// 进行关联查询，过滤掉无效的标的点
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			    String tfdate = sdf.format(new Date());
			    String filter = " RISKMAP_MAIN.ENDDATE>to_date('" +tfdate+ "','yyyy-MM-dd hh24:mi:ss')";
				LinkItem linkItem = new LinkItem();
			    linkItem.setConnectionInfo(datasourceconnection);
			    linkItem.setForeignKeys(new String[] {"ADDRESSID"});//主数据集的外键
			    linkItem.setPrimaryKeys(new String[] {"ADDRESSID"});//关联数据集的主键
			    linkItem.setForeignTable("RISKMAP_MAIN");//关联数据集
			    linkItem.setLinkFields(new String[] {"RISKMAP_MAIN.ADDRESSID","RISKMAP_MAIN.PROPOSALNO","RISKMAP_MAIN.ENDDATE"});//关联数据集的字段
//	        linkItem.setLinkFilter("SMID < 10");//关联数据集的过滤条件 
			    linkItem.setLinkFilter(filter);
			    linkItem.setName("connect");
//	        System.out.println("关联信息为：" + linkItem.toString());
			    // 构造一个 LinkItems 对象并将 linkItem 添加到其中
			    LinkItems linkItems = new LinkItems();
			    linkItems.add(linkItem);
			    parameterP_address.setLinkItems(linkItems);
				//查询出各省或者市的标的信息     queryRecordset_address_intersect
//			Recordset queryRecordset_address = datasetVector_address.query(parameterP_address);
				
				Recordset queryRecordset_address = riskmap_address_new.query(parameterP_address);
				
				System.out.println(adminCode+"=====省的标的个数======="+queryRecordset_address.getRecordCount());
				log.info(adminCode+"=====省的标的个数======="+queryRecordset_address.getRecordCount());
				// 对标的点进行循环
				int j=0;
				while (!queryRecordset_address.isEOF()){
					String  proposalNo = queryRecordset_address.getFieldValue("PROPOSALNO")+"";
					// 用于判断单子是否有效（是否已经終保）StringUtils.isNotBlank(proposalNo)
					if(queryRecordset_address!=null&&queryRecordset_address.getFieldValue("ADDRESSID")!=null&&
							StringUtils.isNotBlank(proposalNo)&&!"null".equals(proposalNo.trim())){  
						j++;
						if(queryRecordset_address.getFieldValue("ADDRESSID")==null){
							System.out.println("null的时候"+queryRecordset_address.getRecordCount()); 
						}
						// 应该循环添加
//					Integer  smid = Integer.parseInt(queryRecordset_address.getFieldValue("SmID")+"");
						Integer  smid = Integer.parseInt(queryRecordset_address.getFieldValue("ADDRESSID")+"");
						String addressName = queryRecordset_address.getFieldValue("ADDRESSNAME")+"";
						BigDecimal pointx_2000 = new BigDecimal(queryRecordset_address.getString("POINTX_2000"));
						BigDecimal pointy_2000 = new BigDecimal(queryRecordset_address.getString("POINTY_2000"));
						BigDecimal pointx_02 = new BigDecimal(queryRecordset_address.getString("POINTX_02"));
						BigDecimal pointy_02 = new BigDecimal(queryRecordset_address.getString("POINTY_02"));
						
						String countyName = queryRecordset_address.getString("COUNTYNAME");
						String countyCode = queryRecordset_address.getString("COUNTYCODE");
						String cityEcode = queryRecordset_address.getString("CITYECODE");
						String cityEname = queryRecordset_address.getString("CITYENAME");
						String provinceCode = queryRecordset_address.getString("PROVINCECODE");
						String provinceName = queryRecordset_address.getString("PROVINCENAME");
						
						
//					String validStatus = queryRecordset_address.getFieldValue("VALIDSTATUS")+"";
						// 求标的表中是否存在该信息,若不存在则进行添加
						long totalCount =  getCorporeTotalCount(tfbh ,smid,proCityFlag,tableName);
						if(totalCount==0){
							WzCorporeLs wzCorporeLs =new WzCorporeLs();
							WzCorporeLsId  wzCorporeLsId = new WzCorporeLsId();
							wzCorporeLsId.setTfbh(tfbh);
							wzCorporeLsId.setMid(smid);
							wzCorporeLs.setId(wzCorporeLsId);
							
							wzCorporeLs.setCenterX(centerX);
							wzCorporeLs.setCenterY(centerY);
							wzCorporeLs.setCityCode(adminCode);
							wzCorporeLs.setAddressName(addressName);
							// 状态标志位为2 ，则是未生成标的保额信息
							wzCorporeLs.setValidStatus("2");
							
							wzCorporeLs.setPointx_2000(pointx_2000);
							wzCorporeLs.setPointy_2000(pointy_2000);
							wzCorporeLs.setPointx_02(pointx_02);
							wzCorporeLs.setPointy_02(pointy_02);
							wzCorporeLs.setCityName(cityName);
							wzCorporeLs.setProName(proName);
							
							wzCorporeLs.setCountyCode(countyCode);
							wzCorporeLs.setCountyName(countyName);
							wzCorporeLs.setCityEcode(cityEcode);
							wzCorporeLs.setProvinceCode(provinceCode);
							
							wzCorporeLsList.add(wzCorporeLs);
							WzCorporeLs wzCorporeLsNew =new WzCorporeLs();
							try {
								BeanUtils.copyProperties(wzCorporeLsNew, wzCorporeLs);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
							if(mapProCenterArray.length>0){
								BigDecimal  centerPX =  new BigDecimal(mapProCenterArray[0]);
								BigDecimal  centerPY =  new BigDecimal(mapProCenterArray[1]);
								wzCorporeLsNew.setCenterX(centerPX);
								wzCorporeLsNew.setCenterY(centerPY);
								wzCorporeLsNew.setCityCode(adminCodeP);
								wzCorporeLsNew.setCityName(proName);
								wzCorporeLsPList.add(wzCorporeLsNew);
							}
						}
					}
					
//				System.out.println(tfbh+":"+proCityFlag+"=================="+j);
					queryRecordset_address.moveNext();
				}
				System.out.println(adminCode+"=====有效的标的个数======="+j);
				log.info(adminCode+"=====有效的标的个数======="+j);
				count+=j;
				queryRecordset.moveNext();
			}
			wzCorporeLsSet.addAll(wzCorporeLsList);
			wzCorporeLsPSet.addAll(wzCorporeLsPList);
			wzCorporeLsListNew.addAll(wzCorporeLsSet);
			wzCorporeLsPListNew.addAll(wzCorporeLsPSet);
			System.out.println("=========统计有效标的点的总数============"+count);
			log.info("=========统计有效标的点的总数============"+count);
			if(tableName.indexOf("YJ")!=-1){
//				map.put("corporeYJP", wzCorporeLsPList);
//				map.put("corporeYJC", wzCorporeLsList);
				map.put("corporeYJP", wzCorporeLsPListNew);
				map.put("corporeYJC", wzCorporeLsListNew);
			}else {
//				map.put("corporeLSP", wzCorporeLsPList);
//				map.put("corporeLSC", wzCorporeLsList);
				map.put("corporeLSP", wzCorporeLsPListNew);
				map.put("corporeLSC", wzCorporeLsListNew);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 释放数据库资源，包括数据库连接和PrepareStatement对象*/
	
	private void releaseResources(Statement stat, Connection conn, ResultSet rs) {
		try {
			if(rs!=null){
			rs.close();
			}
		} catch (SQLException e) {
			log.info("关闭异常：" + e.getMessage() ,e);
			throw new RuntimeException("关闭异常:"+e);
		}
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (SQLException e) {
			log.info("关闭异常：" + e.getMessage() ,e);
			throw new RuntimeException("关闭异常:"+e);
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			log.info("关闭异常：" + e.getMessage() ,e);
			throw new RuntimeException("关闭异常:"+e);
		}
	}
	/**
	 * @功能：iobjectjava 获取省级的中心点的经纬度
	 * @param 
	 * @return 
	 * @author liqiankun
	 * @时间：20190523
	 * @修改记录：
	 */
	public Map<String,String>  getMapProCenterPoint(Datasource datasource){
		Map<String,String> map = new HashMap<String, String>();
		try {
			// 获取省的面数据信息
			DatasetVector datasetVector_p = (DatasetVector)datasource.getDatasets().get("china_province");
			// 新增的数据集
			Recordset recordset_p = datasetVector_p.getRecordset(false, CursorType.DYNAMIC);
			while (!recordset_p.isEOF()){
				String lonAndLat = "";
				String adminCode = recordset_p.getFieldValue("ADMINCODE")+""; 
				String centerX = recordset_p.getFieldValue("CENTERX")+"";
				String centerY = recordset_p.getFieldValue("CENTERY")+"";
				lonAndLat = centerX+","+centerY;
				map.put(adminCode, lonAndLat);
				
				recordset_p.moveNext();
			}
			
			// 关闭资源
			MapUtils.closeMapResource(recordset_p,null,null,datasetVector_p,null,null,null,null,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**@功能：统计台风影响省/城市的保额统计
	 * @return RiskResponseVo
	 * @author 王坤龙
	 * @时间：20190523
	 * @修改记录：*/
	@SuppressWarnings("unchecked")
	public RiskResponseVo sumAmount(Map<String,ArrayList<WzCorporeLs>> mapList) {
		RiskResponseVo ajax = new RiskResponseVo();
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<WzCorporeLs> WzCorporeLsList;
		Iterator<String> iteratorAll = mapList.keySet().iterator();
		String procityFlag = "";
		String tableName = "";
	try {
		while (iteratorAll.hasNext()) {
			String keyFlag = iteratorAll.next();
			WzCorporeLsList = mapList.get(keyFlag);
			Map<String, Timestamp> resultTfdate = new HashMap<String, Timestamp>();
			switch(keyFlag){
			case "corporeLSP":
				tableName = "LS";
				procityFlag = "1";
				break;
			case "corporeLSC":
				tableName = "LS";
				procityFlag = "2";
				break;
			case "corporeLSF":
				tableName = "LS";
				procityFlag = "3";
				break;
			case "corporeYJP":
				tableName = "YJ";
				procityFlag = "1";
				break;
			case "corporeYJC":
				tableName = "YJ";
				procityFlag = "2";
				break;
			case "corporeYJF":
				tableName = "YJ";
				procityFlag = "3";
				break;
			}
			String tfbh;
			String mid;
			String citycode;
			String cityname;
			String centerx;
			String centery;
			Map<Map<String,String>,Wz_Corporels_byMid> resultMap = new HashMap<Map<String,String>,Wz_Corporels_byMid>();
			for(WzCorporeLs wzLs:WzCorporeLsList){
				tfbh = wzLs.getId().getTfbh();
				mid = wzLs.getId().getMid().toString();
				if(procityFlag.equals("3")){
					citycode = "-1";
					cityname = "";
					centerx = "0";
					centery = "0";
				} else{
					citycode = wzLs.getCityCode();
					cityname = wzLs.getCityName();
					centerx = wzLs.getPointx_2000().toString();
					centery = wzLs.getPointy_2000().toString();	
				}
				// 按照台风编号查询台风发生时间
				if(!resultTfdate.containsKey(tfbh) && tfbh != null && !("".equals(tfbh))){
					String sqlSearch="select tfdate from wz_tfbh where tfbh = '" + tfbh.trim()+"'";
					System.out.println(sqlSearch);
//					List<Map<String, Object>> resulttemp = null;
//					resulttemp = slaveJdbcTemplate.queryForList(sqlSearch);
//					if(resulttemp!=null && resulttemp.size()==1){
//						resultTfdate.put(tfbh, Timestamp.class.cast(resulttemp.get(0).get("TFDATE")));
//					}
					Object resulttemp = this.searchSqlInfo(sqlSearch,"TFDATE");
					if(null!=resulttemp&&resulttemp!=""){
						resultTfdate.put(tfbh, Timestamp.valueOf(resulttemp.toString()));
					}
				}
				Map<String,String> datakey = new HashMap<String,String>();
				datakey.put(tfbh,citycode);
				if(!resultMap.containsKey(datakey)){
					Wz_Corporels_byMid wcbm = new Wz_Corporels_byMid(new ArrayList(Arrays.asList(mid)),cityname,centerx,centery,procityFlag);
					resultMap.put(datakey, wcbm);
				} else{
					resultMap.get(datakey).getCountList().add(mid);
					Double x = (Double.parseDouble(resultMap.get(datakey).getCenterX())) + (Double.parseDouble(centerx));
					Double y = (Double.parseDouble(resultMap.get(datakey).getCenterY())) + (Double.parseDouble(centery));
					resultMap.get(datakey).setCenterX(x+"");
					resultMap.get(datakey).setCenterY(y+"");
				}

			}
			System.out.println("++++++++++++++++分割线+++++++++++++++++++++++++++++++++++++++"); // 目前获得台风编号，台风发生时间，台风影响Mid，影响地的citycode
			for(Map<String,String> resultKey:resultMap.keySet()){
				tfbh = "";
				mid = "";
				citycode = "";
				cityname = "";
				centerx = "";
				centery = "";
				procityFlag = "";
				for(String key:resultKey.keySet()){
					tfbh = key;
					citycode = resultKey.get(key);
				}
				Timestamp date = resultTfdate.get(tfbh);
			    SimpleDateFormat dateformatter=new SimpleDateFormat("yyyy-MM-dd");
			    SimpleDateFormat hourformatter=new SimpleDateFormat("HH");
			    String tfdate = dateformatter.format(date);
			    String tftime = hourformatter.format(date);
			    Wz_Corporels_byMid wz = resultMap.get(resultKey);
			    List<String> countList = wz.getCountList();
				cityname = wz.getCityName();
				procityFlag = wz.getProcityFlag();
				centerx = (Double.parseDouble(wz.getCenterX())/countList.size()) + "";
				centery = (Double.parseDouble(wz.getCenterY())/countList.size()) + "";
				wz.setCorporeSum(countList.size()+"");
			    BigDecimal[] bigdecimals;
			    if(countList.size()>500){
			    	System.out.println("长度过大情况：");
			    	// 截取list
			    	bigdecimals = new BigDecimal[3];
			    	bigdecimals[0] = BigDecimal.ZERO;
			    	bigdecimals[1] = BigDecimal.ZERO;
			    	bigdecimals[2] = BigDecimal.ZERO;
			    	int max = countList.size()/500;
			    	for(int i=0; i<=max; i++){
			    		System.out.println("进入第" + i + "次计算");
			    		List<String> bigdecimalsTemp;
			    		if(i<max){
			    			bigdecimalsTemp = countList.subList(i*500, (i+1)*500);
			    		} else if(i*500 != countList.size()){
			    			bigdecimalsTemp = countList.subList(i*500, countList.size());
			    		} else{
			    			break;
			    		}
			    		BigDecimal[] bigdecimalTemp = doworkSumAmount(bigdecimalsTemp,tfdate,tftime);
			    		bigdecimals[0] = bigdecimals[0].add(bigdecimalTemp[0]);
			    		bigdecimals[1] = bigdecimals[1].add(bigdecimalTemp[1]);
			    		bigdecimals[2] = bigdecimals[2].add(bigdecimalTemp[2]);
			    		System.out.println("此时 amount = " + bigdecimals[0] + " Q = "  + bigdecimals[1] + " G = "  + bigdecimals[2]);
			    	}

			    }else{
			    	// 直接调用程序
			    	bigdecimals = doworkSumAmount(countList,tfdate,tftime);
			    }
			    wz.setAmount(bigdecimals[0]);
			    wz.setAmountQ(bigdecimals[1]);
			    wz.setAmountG(bigdecimals[2]);
			    System.out.println("最终计算出 台风编号为" + tfbh+ citycode + cityname + " 省/市 = "+ procityFlag + " 影响地点共有" + wz.getCorporeSum() + " amount = " + bigdecimals[0] + " Q = "  + bigdecimals[1] + " G = "  + bigdecimals[2]);
			    // 测试查询
//			    QueryRule queryRule = QueryRule.getInstance();
			    Criteria<WzCorporeLsP> criteriaWzCorporeLsP = new Criteria<>();
			    
			    WzCorporeLsPId id = new WzCorporeLsPId();
			    id.setTfbh(tfbh);
			    id.setProcityFlag(procityFlag);
			    id.setCityCode(citycode);
//			    queryRule.addEqual("id", id);
			    criteriaWzCorporeLsP.add(Restrictions.eq("id", id));
			    if(tableName.equals("LS")){
			    	List<WzCorporeLsP> resultSelect = new ArrayList<WzCorporeLsP>();
//					resultSelect = databaseDao.findAll(WzCorporeLsP.class, queryRule);
					resultSelect = wzCorporeLsPRepository.findAll(criteriaWzCorporeLsP);
					
				    if(resultSelect == null || resultSelect.size()==0){
				    	// 插入
					    WzCorporeLsP wzLsPIns = new WzCorporeLsP();
					    wzLsPIns.setId(id);
					    wzLsPIns.setCenterX(new BigDecimal(centerx));
					    wzLsPIns.setCenterY(new BigDecimal(centery));
					    wzLsPIns.setCityName(cityname);
					    wzLsPIns.setCorporeSum(countList.size());
					    wzLsPIns.setGAmount(bigdecimals[2]);
					    wzLsPIns.setJAmount(BigDecimal.ZERO);
					    wzLsPIns.setQAmount(bigdecimals[1]);
					    wzLsPIns.setTotalAmount(bigdecimals[0]);
//					    databaseDao.save(WzCorporeLsP.class, wzLsPIns);
					    wzCorporeLsPRepository.save(wzLsPIns);
				    } else{
				    	// 修改，将四个数据与已经存在的数据累加
				    	WzCorporeLsP wzLsPTemp = resultSelect.get(0);
						wzLsPTemp.setTotalAmount(wzLsPTemp.getTotalAmount().add(bigdecimals[0]));
						wzLsPTemp.setQAmount(wzLsPTemp.getQAmount().add(bigdecimals[1]));
						wzLsPTemp.setGAmount(wzLsPTemp.getGAmount().add(bigdecimals[2]));
						wzLsPTemp.setCorporeSum(wzLsPTemp.getCorporeSum()+countList.size());
				    }
			    }else if(tableName.equals("YJ")){
			    	 Criteria<WzCorporeYjP> criteriaWzCorporeYjP = new Criteria<>();
			    	 criteriaWzCorporeYjP.add(Restrictions.eq("id", id));
			    	 
			    	List<WzCorporeYjP> resultSelect = new ArrayList<WzCorporeYjP>();
//					resultSelect = databaseDao.findAll(WzCorporeYjP.class, queryRule);
					
					resultSelect = wzCorporeYjPRepository.findAll(criteriaWzCorporeYjP);
				    if(resultSelect == null || resultSelect.size()==0){
				    	// 插入
				    	WzCorporeYjP wzYjPIns = new WzCorporeYjP();
				    	wzYjPIns.setId(id);
				    	wzYjPIns.setCenterX(new BigDecimal(centerx));
				    	wzYjPIns.setCenterY(new BigDecimal(centery));
				    	wzYjPIns.setCityName(cityname);
				    	wzYjPIns.setCorporeSum(countList.size());
				    	wzYjPIns.setGAmount(bigdecimals[2]);
				    	wzYjPIns.setJAmount(BigDecimal.ZERO);
				    	wzYjPIns.setQAmount(bigdecimals[1]);
				    	wzYjPIns.setTotalAmount(bigdecimals[0]);
//					    databaseDao.save(WzCorporeYjP.class, wzYjPIns);
					    wzCorporeYjPRepository.save(wzYjPIns);
				    } else{
				    	// 修改，将四个数据与已经存在的数据累加
				    	WzCorporeYjP wzYjPTemp = resultSelect.get(0);
				    	wzYjPTemp.setTotalAmount(wzYjPTemp.getTotalAmount().add(bigdecimals[0]));
				    	wzYjPTemp.setQAmount(wzYjPTemp.getQAmount().add(bigdecimals[1]));
				    	wzYjPTemp.setGAmount(wzYjPTemp.getGAmount().add(bigdecimals[2]));
				    	wzYjPTemp.setCorporeSum(wzYjPTemp.getCorporeSum()+countList.size());
				    }
			    }
			}
			ajax.setStatus(1);
			ajax.setStatusText("插入成功");
		}

	} catch (Exception e) {
		ajax.setStatus(0);
		ajax.setStatusText("查询异常：" + e.getMessage());
		log.info("查询异常：" + e.getMessage(), e);
		e.printStackTrace();
		throw new RuntimeException("查询异常:" + e);
	} finally {
		releaseResources(stat, conn, rs);
	}
	return ajax;
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
//		List<Map<String, Object>> resultQ = null;
//		resultQ = slaveJdbcTemplate.queryForList(sql);
//		if(resultQ!=null && resultQ.size() == 1 && resultQ.get(0)!=null && resultQ.get(0).get("AMOUNTQ")!=null){
//			BigDecimal amountQ = new BigDecimal(resultQ.get(0).get("AMOUNTQ").toString()); 
////			amountQ=amountQ.setScale(2, BigDecimal.ROUND_DOWN); //小数位 直接舍去
//			bigdecimals[1]=amountQ.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
//		}
		Object resultQ = this.searchSqlInfo(sql,"amountQ");
		if(null!=resultQ&&StringUtils.isNotBlank(resultQ.toString())){
			System.out.println("=========resultQ==========="+resultQ);
			BigDecimal amountQ = new BigDecimal(resultQ.toString()); 
			bigdecimals[1]=amountQ.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
		}
		
		String sql2 = "select sum(sumamount) as amountG from riskmap_main where classcode = '03' and addressid in ('" +list_str+ "') and (startdate<to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
				+ " or (startdate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and starthour <='" +tftime+ "')) and (enddate>to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss')"
				+ " or (enddate=to_date('" +tfdate+ "','yyyy-MM-dd HH:mi:ss') and endhour >='" +tftime+ "')) group by classcode";
//		List<Map<String, Object>> resultG = null;
//		resultG = slaveJdbcTemplate.queryForList(sql2);
//		if(resultG!=null && resultG.size() == 1 && resultG.get(0)!=null && resultG.get(0).get("AMOUNTG")!=null){
//			BigDecimal amountG = new BigDecimal(resultG.get(0).get("AMOUNTG").toString()); 
//	//		amountG=amountG.setScale(2, BigDecimal.ROUND_DOWN); //小数位 直接舍去
//			bigdecimals[2]=amountG.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
//		}
		
		Object resultG = this.searchSqlInfo(sql2,"amountG");
//		if(StringUtils.isNotBlank(resultG)){
		if(null!=resultG&&StringUtils.isNotBlank(resultG.toString())){
			System.out.println("=========resultG==========="+resultG);
			BigDecimal amountG = new BigDecimal(resultG.toString()); 
			bigdecimals[2]=amountG.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
		}
		
		bigdecimals[0] = bigdecimals[1].add(bigdecimals[2]).setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println("计算中临时 amount = " + bigdecimals[0] + " Q = "  + bigdecimals[1] + " G = "  + bigdecimals[2]);
		return bigdecimals;
		
	}
	/**
	 * @功能：更改标志位,用来进行占位
	 * @author liqiankun
	 * @param 
	 * @return
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public RiskResponseVo updateWzTfbhFlag(String targetFlag,String sourceFlag){
		System.out.println("=====updateWzTfbhFlag===begin=====target:"+targetFlag+"source:"+sourceFlag);
		RiskResponseVo ajaxResult =new RiskResponseVo();
		Connection conn = null;
	    PreparedStatement stat = null;
	    try {
	    	String sql ="update WZ_TFBH set ISUSE=replace(ISUSE,substr(ISUSE,2,1),'"+targetFlag+"')  where substr(ISUSE,2,1) ='"+sourceFlag+"'";
//		    	String sql ="update WZ_TFBH set ISACTIVE='"+targetFlag+"' where ISACTIVE='"+sourceFlag+"'";
			conn = slaveJdbcTemplate.getDataSource().getConnection();
			stat = conn.prepareStatement(sql);
			stat.executeUpdate();
			System.out.println("=====updateWzTfbhFlag===end=====");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			releaseResources(stat, conn, null);
		}  
		return ajaxResult;
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
			// 获取多个台风的信息
			List<TyphoonLatestInfo> typhoonLatestInfoList = this.organizeTyphoonInfo(typhoonPathList);
			
//			databaseDao.saveAll(TyphoonLatestInfo.class, typhoonLatestInfoList);
			typhoonLatestInfoRepository.saveAll(typhoonLatestInfoList);
			// WZ_TFBH表
//			TyphoonLatestInfo typhoonLatestInfo = new TyphoonLatestInfo();
//			typhoonLatestInfo.setTfbh("201923");
//			typhoonLatestInfo.setIsActive("0");
//			databaseDao.save(TyphoonLatestInfo.class, typhoonLatestInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
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
			boolean flag =  MapUtils.compareDateSize(typhoonPath);
			if(flag){
				// 如果是历史，则需要进行比较基本信息中的发生时间是否是该点发生之前
				typhoonLatestInfo = MapUtils.compareObjectDateSize(typhoonPath, typhoonLatestInfo);
				//组织台风历史信息
				TyphoonHistoryPath typhoonHistoryPath = this.organizeTyphoonHistoryPath(typhoonPath);
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
			}else{
				// 台风预报信息
				WzTFYblj  wzTFYblj =this.organizeWzTFYblj(typhoonPath);
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
			mapTarget.put(typhoonPath.getNum_Nati(), typhoonLatestInfo);
			
		}else {
			// map中不存在该编号信息
			try {
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				Map<String, Object> map =new HashMap<String, Object>();
				// 组织台风基本信息
				TyphoonLatestInfo typhoonLatestInfo = this.organizeTyphoonLatestInfo(typhoonPath);
//				TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
//				typhoonLatestInfo.setTfbh(typhoonPath.getNum_Nati());
				//判断是预报还是历史信息,true为历史，false为预报
				boolean flag =  MapUtils.compareDateSize(typhoonPath);
				
				if(flag){
					// 台风发生时间
					Date datetime = MapUtils.getTyphoonDate(typhoonPath,"YB");
					if(datetime!=null){
						typhoonLatestInfo.setTfDate(datetime);
					}
//					typhoonLatestInfo.setTfDate(sdf.parse(typhoonPath.getDatetime()));
					// 台风历史信息
					List<TyphoonHistoryPath> typhoonHistoryPathList = new ArrayList<TyphoonHistoryPath>();
//					TyphoonHistoryPath typhoonHistoryPath = new TyphoonHistoryPath();
					TyphoonHistoryPath typhoonHistoryPath = this.organizeTyphoonHistoryPath(typhoonPath);
					typhoonHistoryPathList.add(typhoonHistoryPath);
					typhoonLatestInfo.setTyphoonHistoryPathList(typhoonHistoryPathList);
				}else {
					// 台风预报信息
					List<WzTFYblj> wzTFYbljList = new ArrayList<WzTFYblj>();
					WzTFYblj wzTFYblj = organizeWzTFYblj(typhoonPath);
//					WzTFYblj wzTFYblj =new WzTFYblj();
//					WzTFYbljId  id =new WzTFYbljId();
//					wzTFYblj.setId(id);
					wzTFYbljList.add(wzTFYblj);
					typhoonLatestInfo.setWzTFYbljList(wzTFYbljList);
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
		TyphoonLatestInfo typhoonLatestInfo =new TyphoonLatestInfo();
		//编号
		typhoonLatestInfo.setTfbh(typhoonPath.getNum_Nati());
		typhoonLatestInfo.setIsActive(typhoonPath.getISACTIVE());
		return typhoonLatestInfo;
	}
	/**
	 * @功能：组织台风历史数据信息
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public  TyphoonHistoryPath organizeTyphoonHistoryPath(TyphoonPath typhoonPath){
		TyphoonHistoryPath typhoonHistoryPath =new  TyphoonHistoryPath();
		TyphoonHistoryPathId  id =new TyphoonHistoryPathId();
		id.setTfbh(typhoonPath.getNum_Nati());
		
//		typhoonHistoryPath.setTfbh(typhoonPath.getNum_Nati());
		Date rqsj = MapUtils.getTyphoonDate(typhoonPath,"YB");
		if(rqsj!=null){
//			typhoonHistoryPath.setRqsj(rqsj);
			id.setRqsj(rqsj);
		}
		typhoonHistoryPath.setId(id);
		typhoonHistoryPath.setJd(typhoonPath.getLon());
		typhoonHistoryPath.setWd(typhoonPath.getLat());
		typhoonHistoryPath.setIsuse("13");
		return typhoonHistoryPath;
	}
	/**
	 * @功能：组织台风预警数据信息
	 * @author liqiankun
	 * @param  
	 * @return 
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public  WzTFYblj organizeWzTFYblj(TyphoonPath typhoonPath){
		WzTFYblj wzTFYblj =new WzTFYblj();
		WzTFYbljId  id =new WzTFYbljId();
		id.setTfbh(typhoonPath.getNum_Nati());
		id.setTm("中国");
		Date ybsj = MapUtils.getTyphoonDate(typhoonPath,"SH");
		Date rqsj = MapUtils.getTyphoonDate(typhoonPath,"YB");
		if(ybsj!=null){
			id.setYbsj((Timestamp)ybsj);
		}
		if(rqsj!=null){
			id.setRqsj((Timestamp)rqsj);
		}
		wzTFYblj.setId(id); 
		wzTFYblj.setJd(typhoonPath.getLon());
		wzTFYblj.setWd(typhoonPath.getLat());
		wzTFYblj.setIsUse("13");
		return wzTFYblj;
	}
	
	public void saveWzCorporeLsP(List<WzCorporeLsP>  corporePGradeList){
		if(corporePGradeList!=null&&corporePGradeList.size()>0){
//			databaseDao.saveAll(WzCorporeLsP.class, corporePGradeList);
			wzCorporeLsPRepository.saveAll(corporePGradeList);
		}
	}
	/**
	 * @功能：获取台风影响单个标的详细信息（供大灾调度平台使用）
	 * @return void
	 * @author liqiankun
	 * @时间：20200326
	 * @修改记录：
	 */
	public RiskResponseVo queryTyDetailInfo(String tfbh,Integer addressId){
		RiskResponseVo ajaxResult =new RiskResponseVo();
		try {
			DangerManageMapInfo dangerManageMapInfo = new DangerManageMapInfo();
			//查询台风预警标的和实况标的信息，来判断标的信息
			WzCorporeLs wzCorporeLs=this.queryWzCorporeLs(tfbh,addressId);
			WzCorporeYj wzCorporeYj=this.queryWzCorporeYj(tfbh,addressId);
			if(null!=wzCorporeLs&&null!=wzCorporeYj){
				dangerManageMapInfo.setIsHappen("2");
			}else if(null!=wzCorporeLs){
				dangerManageMapInfo.setIsHappen("1");
			}else if(null!=wzCorporeYj){
				dangerManageMapInfo.setIsHappen("0");
			}else {
				ajaxResult.setStatus(0);
				ajaxResult.setStatusText("此台风不存在标的信息");
				return ajaxResult;
			}
			TyphoonLatestInfo typhoonLatestInfo = this.queryWzTfbh(tfbh);
			Date tfDate = typhoonLatestInfo.getTfDate();
			if(tfDate!=null){
				SimpleDateFormat dateformatter=new SimpleDateFormat("yyyy-MM-dd");
			    SimpleDateFormat hourformatter=new SimpleDateFormat("HH");
			    String tfdate = dateformatter.format(tfDate);
			    String tftime = hourformatter.format(tfDate);
			    List<String> countList = new ArrayList<String>();
			    countList.add(addressId+"");
			    // 计算保额信息
			    BigDecimal[]  bigDecimalAmount =this.doworkSumAmount(countList, tfdate, tftime);
			    dangerManageMapInfo.setTotalAmount(bigDecimalAmount[0]);
			    dangerManageMapInfo.setqAmount(bigDecimalAmount[1]);
			    dangerManageMapInfo.setgAmount(bigDecimalAmount[2]);
			    dangerManageMapInfo.setTfbh(tfbh);
			    //组织详细信息
			    dangerManageMapInfo = this.organWzCorporeInfo(dangerManageMapInfo,wzCorporeLs,wzCorporeYj);
			    ajaxResult.setData(dangerManageMapInfo);
			    ajaxResult.setStatus(1);
				ajaxResult.setStatusText("组织台风影响标的详细信息成功");
			}else{
				ajaxResult.setStatus(0);
				ajaxResult.setStatusText("组织台风影响标的详细信息失败");
			}
		} catch (Exception e) {
			ajaxResult.setStatus(0);
			ajaxResult.setStatusText("组织台风影响标的详细信息异常");
			e.printStackTrace();
			log.error("组织台风影响标的详细信息异常：" + e.getMessage(), e);
		}
		return ajaxResult;
	}
	/**
	 * @功能：组织查询的标的的详细信息
	 * @return void
	 * @author liqiankun
	 * @时间：20200326
	 * @修改记录：
	 */
	public DangerManageMapInfo organWzCorporeInfo(DangerManageMapInfo dangerManageMapInfo,
					WzCorporeLs wzCorporeLs,WzCorporeYj wzCorporeYj){
		if("1,2".indexOf(dangerManageMapInfo.getIsHappen())>-1){
			dangerManageMapInfo.setAddressName(wzCorporeLs.getAddressName());
			dangerManageMapInfo.setCityName(wzCorporeLs.getCityName());
			dangerManageMapInfo.setCityCode(wzCorporeLs.getCityCode());
			dangerManageMapInfo.setProName(wzCorporeLs.getProName());
			dangerManageMapInfo.setProCode(wzCorporeLs.getCityCode().substring(0,2)+"0000");
			if(wzCorporeLs.getId()!=null){
				List<String> insuredNameList=this.queryInsuredName(wzCorporeLs.getId().getMid());
				dangerManageMapInfo.setInsuredName(insuredNameList);
			}
		}else if("0".indexOf(dangerManageMapInfo.getIsHappen())>-1){
			dangerManageMapInfo.setAddressName(wzCorporeYj.getAddressName());
			dangerManageMapInfo.setCityName(wzCorporeYj.getCityName());
			dangerManageMapInfo.setCityCode(wzCorporeYj.getCityCode());
			dangerManageMapInfo.setProName(wzCorporeYj.getProName());
			dangerManageMapInfo.setProCode(wzCorporeYj.getCityCode().substring(0,2)+"0000");
			if(wzCorporeYj.getId()!=null){
				List<String> insuredNameList=this.queryInsuredName(wzCorporeYj.getId().getMid());
				dangerManageMapInfo.setInsuredName(insuredNameList);
			}
		}
		return dangerManageMapInfo;
	}
	/**
	 * @功能：查询台风基本信息
	 * @return void
	 * @author liqiankun
	 * @时间：20200326
	 * @修改记录：
	 */
	public TyphoonLatestInfo queryWzTfbh(String tfbh){
		TyphoonLatestInfo typhoonLatestInfo =null;
		try {
//			QueryRule queryRule = QueryRule.getInstance();
			Criteria<TyphoonLatestInfo> criteria = new Criteria<>();
			if(StringUtils.isNotBlank(tfbh)){
//				queryRule.addEqual("tfbh", tfbh.trim());
//				typhoonLatestInfo = databaseDao.findUnique(TyphoonLatestInfo.class, queryRule);
				
				criteria.add(Restrictions.eq("tfbh", tfbh.trim()));
				Optional<TyphoonLatestInfo> optional= typhoonLatestInfoRepository.findOne(criteria);
				typhoonLatestInfo = optional.isPresent()?optional.get():null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询台风基本信息失败：" + e.getMessage(), e);
		}
		return typhoonLatestInfo;
	}
	/**
	 * @功能：根据台风编号和标的信息查询WzCorporeLs表信息
	 * @return void
	 * @author liqiankun
	 * @时间：20200326
	 * @修改记录：
	 */
	public WzCorporeLs queryWzCorporeLs(String tfbh,Integer addressId){
		WzCorporeLs wzCorporeLs =null;
		try {
//			QueryRule queryRule = QueryRule.getInstance();
			
			Criteria<WzCorporeLs> criteria = new Criteria<>();
			if(null!=addressId){
//				queryRule.addEqual("id.tfbh", tfbh);
//				queryRule.addEqual("id.mid", addressId);
//				wzCorporeLs = databaseDao.findUnique(WzCorporeLs.class, queryRule);
				
				criteria.add(Restrictions.eq("id.tfbh", tfbh));
				criteria.add(Restrictions.eq("id.mid", addressId));
				Optional<WzCorporeLs> optional=wzCorporeLsRepository.findOne(criteria);
				wzCorporeLs = optional.isPresent()?optional.get(): null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询台风基本信息失败：" + e.getMessage(), e);
		}
		return wzCorporeLs;
	}
	/**
	 * @功能：根据台风编号和标的信息查询WzCorporeYj表信息
	 * @return void
	 * @author liqiankun
	 * @时间：20200326
	 * @修改记录：
	 */
	public WzCorporeYj queryWzCorporeYj(String tfbh,Integer addressId){
		WzCorporeYj wzCorporeYj =null;
		try {
//			QueryRule queryRule = QueryRule.getInstance();
			
			Criteria<WzCorporeYj> criteria = new Criteria<>();
			if(null!=addressId){
//				queryRule.addEqual("id.tfbh", tfbh);
//				queryRule.addEqual("id.mid", addressId);
//				wzCorporeYj = databaseDao.findUnique(WzCorporeYj.class, queryRule);
				
				criteria.add(Restrictions.eq("id.tfbh", tfbh));
				criteria.add(Restrictions.eq("id.mid", addressId));
				Optional<WzCorporeYj> optional= wzCorporeYjRepository.findOne(criteria);
				wzCorporeYj= optional.isPresent()?optional.get():null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询台风基本信息失败：" + e.getMessage(), e);
		}
		return wzCorporeYj;
	}
	/**
	 * @功能：根据标的地址查询关系人名称
	 * @return void
	 * @author liqiankun
	 * @时间：20200326
	 * @修改记录：
	 */
	public List<String> queryInsuredName(Integer addressId){
		List<String> insuredNameList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			if(null!=addressId){
				String sql = "select distinct INSUREDCODE,INSUREDNAME from  RISKMAP_INSURED where ADDRESSID ="+addressId;
				conn = slaveJdbcTemplate.getDataSource().getConnection();
				stat = conn.prepareStatement(sql);
				rs = stat.executeQuery();
				for(;rs.next();) {
					String insuredName =rs.getString("INSUREDNAME");
					insuredNameList.add(insuredName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("查询关系人名称失败：" + e.getMessage(), e);
		}finally{
			releaseResources(stat, conn, rs);
		}
		return insuredNameList;
	}
	/**
	 * @功能：对台风基本信息中发生日期进行查询
	 * @author liqiankun
	 * @throws Exception
	 * @时间：20200403
	 */
	public Object searchSqlInfo(String sqlSearch,String key){
		System.out.println("=====searchTfDate===start=====");
		Connection conn = null;
	    PreparedStatement stat = null;
	    ResultSet rs = null;
	    Object tfDate = null;
	    try {
			conn = slaveJdbcTemplate.getDataSource().getConnection();
			stat = conn.prepareStatement(sqlSearch);
			rs = stat.executeQuery();
			for(;rs.next();) {
				tfDate =rs.getString(key);
			}
			System.out.println("=====searchTfDate===end=====");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			releaseResources(stat, conn, rs);
		}  
		return tfDate;
	}

}
