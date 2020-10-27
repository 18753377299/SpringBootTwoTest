//package com.picc.riskctrl.map.service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.picc.riskctrl.common.schema.PrpDcompanyFk;
//import com.sinosoft.dmsdriver.model.PrpDcompany;
//import com.sinosoft.dmsdriver.service.common.DictPage;
//import com.sinosoft.dmsdriver.service.server.PageService;
//
//import ins.framework.dao.database.DatabaseDao;
//import ins.framework.lang.Datas;
//import ins.framework.rpc.annotation.Rpc;
//@Component
//public class JobForAutoMapServiceSpringImpl{
//	@Autowired
//	DatabaseDao databaseDao;
//	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
//
//	public void autoMap() {
//		DictPage page = new DictPage();	   	  
//	   	   try {
//	   		     int pageNo=1;
//	   		     int pageSize=100;
//		    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		    	 Calendar cal = Calendar.getInstance();
//		    	 cal.add(Calendar.DATE, -1);
//		    	 String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
//		    	 String yesterdayBegin = yesterday + " 00:00:00";
//		    	 String yesterdayEnd = yesterday + " 23:59:59";
////		    	 System.out.println("获取前一天起始时间和结束时间 ：" + yesterdayBegin + "    " + yesterdayEnd);
//		    	 
//		    	 Date startDate=df.parse(yesterdayBegin);
//		    	 Date endDate=df.parse(yesterdayEnd);
//		    	 
////		    	 Date startDate=df.parse("2017-11-22 00:00:00");
////		    	 Date endDate=df.parse("2017-12-02 00:00:00");
//		    	 List<PrpDcompany> prpDcList=new ArrayList<PrpDcompany>();
//		    	 int total=0;
//		    	 do {
//		    		 page=PageService.getCompanyByDate("riskcontrol", startDate, endDate, pageNo, pageSize);
//		    		 prpDcList=page.getData();
//		    		 List<PrpDcompanyFk> prpDcompanyFks=new ArrayList<PrpDcompanyFk>();	
//		    		 if(prpDcList.size()>0){
//		    			 for(int i=0;i<prpDcList.size();i++){
//		    				 PrpDcompanyFk prpDcompanyFk=new PrpDcompanyFk();
//		    				    Datas.copySimpleObjectToTargetFromSource(prpDcompanyFk, prpDcList.get(i));
//				    			java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
//				    			Date updateDateFormat;	
//				    			if(null!=prpDcList.get(i).getUpdateDate()){
//				    				updateDateFormat=formatter.parse(prpDcList.get(i).getUpdateDate());
//				    			}else{
//				    				updateDateFormat=null;
//				    			}	
//				    			prpDcompanyFk.setUpdateDate(updateDateFormat);
//				    			prpDcompanyFks.add(prpDcompanyFk);
//				    			total++;
//				    	}
//		    			 try {
//		    				 databaseDao.saveAll(PrpDcompanyFk.class, prpDcompanyFks);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}				    	
//				    	System.out.println("=====================================================total:"+total);
//		    		 }
//		    		 pageNo++;
//				} while (prpDcList.size()>=pageSize);
//			} catch (Exception e) {
//				LOGGER.info("更新数据字典信息异常：" + e.getMessage() ,e);
//				e.printStackTrace();
//				throw new RuntimeException("更新数据字典信息异常:"+e);
//			}
//		
//	}
//	
//}
