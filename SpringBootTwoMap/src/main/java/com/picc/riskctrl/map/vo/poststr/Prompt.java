/**
 * 
 */
package com.picc.riskctrl.map.vo.poststr;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:Prompt</p>
 * <p>Description: </p> 
 * @author weishituan
 * @date 2018年1月24日
 */
public class Prompt {
private String type;
private List<Admins> admins=new ArrayList<>();
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public List<Admins> getAdmins() {
	return admins;
}
public void setAdmins(List<Admins> admins) {
	this.admins = admins;
}

}
