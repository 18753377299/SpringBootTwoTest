package com.picc.riskctrl.map.dao;

import org.springframework.data.jpa.repository.Query;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.map.po.TyphoonHistoryPath;
import com.picc.riskctrl.map.po.TyphoonHistoryPathId;

public interface TyphoonHistoryPathRepository extends JpaBaseRepository<TyphoonHistoryPath,TyphoonHistoryPathId> {
	
//	@Query(
//	        value = "select * from RiskReport_SaleImage where tfbh=?1 and rqsj =to_date('"')",
//	        nativeQuery = true)
//	public TyphoonHistoryPath queryHistoryByTfbhAndRqsj(String tfbh,String rqsj);
}

