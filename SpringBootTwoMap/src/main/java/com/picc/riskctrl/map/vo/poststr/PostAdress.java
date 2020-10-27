/**
 * 
 */
package com.picc.riskctrl.map.vo.poststr;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:PostAdress</p>
 * <p>Description: </p> 
 * @author weishituan
 * @date 2018年1月24日
 */
public class PostAdress {
private String landmarkcount;
private String searchversion;
private String count;
private String engineversion;
private String resultType;
private List<Pois> pois=new ArrayList<>();
private String dataversion;
private List<Prompt> prompt=new ArrayList<>();
private String mclayer;
private String keyWord;
public String getLandmarkcount() {
	return landmarkcount;
}
public void setLandmarkcount(String landmarkcount) {
	this.landmarkcount = landmarkcount;
}
public String getSearchversion() {
	return searchversion;
}
public void setSearchversion(String searchversion) {
	this.searchversion = searchversion;
}
public String getCount() {
	return count;
}
public void setCount(String count) {
	this.count = count;
}
public String getEngineversion() {
	return engineversion;
}
public void setEngineversion(String engineversion) {
	this.engineversion = engineversion;
}
public String getResultType() {
	return resultType;
}
public void setResultType(String resultType) {
	this.resultType = resultType;
}
public List<Pois> getPois() {
	return pois;
}
public void setPois(List<Pois> pois) {
	this.pois = pois;
}
public String getDataversion() {
	return dataversion;
}
public void setDataversion(String dataversion) {
	this.dataversion = dataversion;
}
public List<Prompt> getPrompt() {
	return prompt;
}
public void setPrompt(List<Prompt> prompt) {
	this.prompt = prompt;
}
public String getMclayer() {
	return mclayer;
}
public void setMclayer(String mclayer) {
	this.mclayer = mclayer;
}
public String getKeyWord() {
	return keyWord;
}
public void setKeyWord(String keyWord) {
	this.keyWord = keyWord;
}

}
