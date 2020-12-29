package com.example.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.pojo.Users;

/**
 * Repository接口的方法： 名称命名查询
 *
 *
 */
public interface UsersRepositoryByName extends Repository<Users, Integer> {

	//方法的名称必须要遵循驼峰式命名规则。findBy(关键字)+属性名称(首字母要大写)+查询条件(首字母大写)
	List<Users> findByName(String name);
	// 通过用户的name和age来进行查询需要写*NameAndAge
	List<Users> findByNameAndAge(String name, Integer age);
	
//	List<Users> findByNameLike(String name);
}
