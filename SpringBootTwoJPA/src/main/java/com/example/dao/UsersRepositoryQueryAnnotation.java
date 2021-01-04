package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.example.pojo.Users;

/**
 * Repository   @Query
 *
 *
 */
public interface UsersRepositoryQueryAnnotation extends Repository<Users, Integer> {

	@Query("from Users where name = ?1")
	List<Users> queryByNameUseHQL(String name);
	
	/*本地查询： 所谓本地查询，就是使用原生的sql语句（根据数据库的不同，在sql的语法或结构方面可能有所区别）进行查询数据库的操作。*/
	@Query(value="select * from t_users where name = ?1",nativeQuery=true)
	List<Users> queryByNameUseSQL(String name);
	
//	@Query("update Users set name  = ?1 where id  = ?2")
//	@Modifying //需要执行一个更新操作
//	void updateUsersNameById(String name, Integer id);

}
