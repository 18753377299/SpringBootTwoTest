package com.service;

import java.util.List;

import com.dao.UserDao;
import com.po.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService  {
	
//	@Autowired
//	UserMapper userMapper;
//	
//	public void addUser(Users user) {	
//		userMapper.addUser(user);
//	}
//	
//	public List<Users> selectByAll(){
//		System.out.println("====service=========");
//		return userMapper.selectByAll();
//	}

	@Autowired
	private UserDao userDao;

	public String getUserName() {
		System.out.println("================successs");
		List<Users> list = userDao.selectByAll();
		return "you are success";
	}
	//@CacheEvict(value="users",allEntries=true) 清除缓存中以 users 缓存策略缓存的对象
	@CacheEvict(value="users",allEntries= true)
	public void addUser(Users user) {
		userDao.addUser(user);
	}

	//	@Cacheable(value="users")
	public List<Users> selectByAll(){
		System.out.println("====service=========");
		return userDao.selectByAll();
	}
	/**
	 * 实现缓存，这里的value值和配置文件中想要使用的那个cache中name相同，
	 * 要缓存某个对象，被缓存的对象要实现可序列化接口，是磁盘存储
	 * **/
	@Cacheable(value="users")
	public Users selectById(){
		System.out.println("====service=========");
		return userDao.selectById();
	}

	public String getUserCode() {
		System.out.println("====1111111111111=========");
		return "you are right!";
	}


}
