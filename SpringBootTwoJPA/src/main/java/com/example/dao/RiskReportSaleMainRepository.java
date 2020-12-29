package com.example.dao;

import com.example.common.jpa.base.JpaBaseRepository;
import com.example.pojo.RiskReportSaleMain;

import io.swagger.annotations.ApiModel;

@ApiModel(value="照片档案主表  add by liiqiankun 20201018")
public interface RiskReportSaleMainRepository  extends  JpaBaseRepository<RiskReportSaleMain, String>{
	
}
