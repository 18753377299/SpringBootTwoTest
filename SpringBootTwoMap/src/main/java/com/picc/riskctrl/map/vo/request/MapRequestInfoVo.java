package com.picc.riskctrl.map.vo.request;

import java.util.List;

import com.picc.riskctrl.map.vo.WzTFLsljVo;
	/**
 * @author  作者 E-mail:liqiankun 
 * @date 创建时间：2017年10月30日 下午6:57:46
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class MapRequestInfoVo {
	 private String branchCompanyZH;//分公司中文表示
	 private String DangerAdderss;
	 private String branchCompany;
     private String scope;
     private List<LongAndLatitude>  lngXLatYList;
     private String searchType;
     
     private MapBound mapBound;
     
     /**台风历史数据--addby liqiankun 20190515*/
     private WzTFLsljVo wzTFLslj;
          
	 private MapMoreQuery mapMoreQuery;
	 /**存储省市县的地址信息 addby liqiankun 20190709*/
	 
	 private RiskMapRegionVo mapAddress;
	 
	 private int pageNo;
	 private int pageSize;	
     
     
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<LongAndLatitude> getLngXLatYList() {
		return lngXLatYList;
	}
	public void setLngXLatYList(List<LongAndLatitude> lngXLatYList) {
		this.lngXLatYList = lngXLatYList;
	}
	public MapMoreQuery getMapMoreQuery() {
		return mapMoreQuery;
	}
	public void setMapMoreQuery(MapMoreQuery mapMoreQuery) {
		this.mapMoreQuery = mapMoreQuery;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public MapBound getMapBound() {
		return mapBound;
	}
	public void setMapBound(MapBound mapBound) {
		this.mapBound = mapBound;
	}
	public WzTFLsljVo getWzTFLslj() {
		return wzTFLslj;
	}
	public void setWzTFLslj(WzTFLsljVo wzTFLslj) {
		this.wzTFLslj = wzTFLslj;
	}
	public String getBranchCompany() {
		return branchCompany;
	}
	public String getBranchCompanyZH() {
		return branchCompanyZH;
	}
	public void setBranchCompanyZH(String branchCompanyZH) {
		this.branchCompanyZH = branchCompanyZH;
	}
	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}
	public String getDangerAdderss() {
		return DangerAdderss;
	}
	public void setDangerAdderss(String dangerAdderss) {
		DangerAdderss = dangerAdderss;
	}
	public RiskMapRegionVo getMapAddress() {
		return mapAddress;
	}
	public void setMapAddress(RiskMapRegionVo mapAddress) {
		this.mapAddress = mapAddress;
	}
	   
}
