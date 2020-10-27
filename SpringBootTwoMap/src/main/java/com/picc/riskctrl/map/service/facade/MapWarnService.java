package com.picc.riskctrl.map.service.facade;

import java.math.BigDecimal;
import java.util.List;

import com.picc.riskctrl.common.response.RiskResponseVo;
import com.picc.riskctrl.map.po.WzCorporeLsP;
import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonBaseInfo;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年5月24日 上午9:06:22
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
//@Rpc
public interface MapWarnService {
	/**
	 * @功能：iobjectjava  组织面数据和提取标的信息
	 * @param 
	 * @return void
	 * @author liqiankun
	 * @时间：20190515
	 * @修改记录：
	 */	
	public void  createRegionDataSet();
	/**
	 * @功能：根据气象局提供的台风信息来更新台风基本信息、实时路径和预报路径
	 * @author liqiankun
	 * @param 
	 * @return
	 * @throws Exception
	 * @时间：20190903
	 * @修改记录:
	 */
	public RiskResponseVo updateTyphoonInfo(TyphoonBaseInfo typhoonBaseInfo);
	
//	public RiskResponseVo sumAmount(Map<String,ArrayList<WzCorporeLs>> mapList);
	
	public BigDecimal[] doworkSumAmount(List<String> countList,String tfdate,String tftime);
	
	public void saveWzCorporeLsP(List<WzCorporeLsP>  corporePGradeList);
	/*获取台风影响单个标的详细信息（供大灾调度平台使用）*/
	public RiskResponseVo queryTyDetailInfo(String tfbh,Integer addressId);
		
}
