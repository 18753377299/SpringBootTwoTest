package com.dao;


import com.po.Users;

import java.util.List;


public interface UserDao {
	
	public List<Users> selectByAll();
	
	public void addUser(Users users);
	
	public Users selectById();
	
	
}
