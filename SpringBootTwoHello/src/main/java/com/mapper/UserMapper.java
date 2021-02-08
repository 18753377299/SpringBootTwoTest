package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.po.Users;

@Mapper
public interface UserMapper {
	public void addUser(Users users);
	public List<Users> selectByAll();
}
