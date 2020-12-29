package com.example.test;

import com.example.SpringBootTwoJpaApplication;
import com.example.dao.RiskReportSaleMainRepository;
import com.example.pojo.RiskReportSaleImaType;
import com.example.pojo.RiskReportSaleImaTypeId;
import com.example.pojo.RiskReportSaleMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class RiskOneToManyTest {
	
	@Autowired
	private RiskReportSaleMainRepository riskReportSaleMainRepository;
	
	/**
	 * 一对多关联关系的添加: https://blog.csdn.net/yingziisme/article/details/81436355
	 * 之前保存RiskReportSaleMain报错，是因为实体类中有@Cache配置，但是在文件中没有进行配置。
	 */
	@Test
	public void testSave(){
		//创建一个用户
		RiskReportSaleMain riskReportSaleMain = new RiskReportSaleMain();
		riskReportSaleMain.setArchivesNo("11111");
		riskReportSaleMain.setExploreComcode("0000");
		riskReportSaleMain.setExplorer("2222");
		riskReportSaleMain.setCheckUpFlag("1");
		riskReportSaleMain.setMobileFlag("2");
		
		RiskReportSaleImaType riskReportSaleImaType = new RiskReportSaleImaType();
		RiskReportSaleImaTypeId id=new RiskReportSaleImaTypeId();
		id.setArchivesNo("11111");
		id.setImageType("2-1");
		riskReportSaleImaType.setId(id);
		riskReportSaleImaType.setImageSum(2);
		riskReportSaleImaType.setImageRepulseSum(3);
		riskReportSaleMain.getRiskReportSaleImaTypeList().add(riskReportSaleImaType);
		
		try {
			riskReportSaleMainRepository.save(riskReportSaleMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
			
}
