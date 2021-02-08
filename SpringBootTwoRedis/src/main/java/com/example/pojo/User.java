package com.example.pojo;

import java.io.Serializable;

/**
 * @author  作者 E-mail: 
 * @date 创建时间：2017年12月18日 上午9:44:07
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

     private String id;
     private String info;
     private String name;

	public User(String id, String info, String name) {
		this.id = id;
		this.info = info;
		this.name = name;
	}

	public User() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", info='" + info + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
