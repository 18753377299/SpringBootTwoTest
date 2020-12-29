package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.example.dao.TestRepository;
import com.example.dao.TestTwoRepository;
import com.example.dao.UsersRepository;
import com.example.pojo.Roles;
import com.example.pojo.Test;
import com.example.pojo.TestTwo;
import com.example.pojo.Users;


/**
 *  addby  liqiankun  20201015 begin
 * Service中，被 @Transactional 注解的方法，将支持事务。如果注解在类上，则整个类的所有方法都默认支持事务。
 * 1、使用insertTest 方法事务进行试验当类上添加@Transactional事务注解的时候，insertTest方法进行保存，
  * 即使该方法上不使用事务注解，当遇到错误时，错误之前的保存方法和错误之后的保存方法依旧没有插入到库中。
  * 问题1： 在class上添加的事务，是什么类型的（Propagation）？？？
  * 
  * 2、当一个方法中进行保存的是两个不同的表的情况,： 两个不同的表进行保存的时候，事务同样起作用。
  *
 * */
@Service
@Transactional
public class TestService {
	
	
	@Autowired
	TestRepository testRepository;
	@Autowired
	TestTwoRepository testTwoRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	
	/*当程序中遇到异常时，不进行try{}catch()抛出，直接使用全局异常方法也能进行处理*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Test> findAll() {
		List<Test> testList = null;
//		try {
			testList =  testRepository.findAll();
//			System.out.println(1/0);
			for (Test test: testList) {
				System.out.println("id:"+test.getId()+",name:"+test.getName());
			}
//		} catch (Exception e) {
////			e.printStackTrace();
//			throw new RuntimeException(e.getMessage());
//		}
		return testList;
	}
	
	/**当两次保存的表是同一个表的情况*/
//	@Transactional(propagation = Propagation.REQUIRED)
	public void insertTest() {
		Test testA = new Test();
//		testA.setId("33");
		testA.setName("wangergou");
		testRepository.save(testA);

        System.out.print(1/0);
        Test testB = new Test();
//        testA.setId("34");
        testB.setName("mazi");
		testRepository.save(testB);
	}
	/**当两次保存的表不是同一个表的情况*/
	public void insertTwoDiff() {
			Test testA = new Test();
//		testA.setId("43");
			testA.setName("ergouzi");
			testRepository.save(testA);
			
			System.out.print(1/0);
			TestTwo test2B = new TestTwo();
			test2B.setId(44);
			test2B.setName("wangfugui");
			testTwoRepository.save(test2B);
	}
	
	public void deleteTest(String  id) {
//		testRepository.deleteById(id);
	}
	
	public Users queryUser(){
		Optional<Users> optional = this.usersRepository.findById(4);
		Users findOne = optional.isPresent()?optional.get():null;

		Users userNew= new Users();
		BeanUtils.copyProperties(findOne, userNew);
		if(findOne!=null&&findOne.getRoles()!=null) {
			Roles roleNew =new Roles();
			Roles roles =findOne.getRoles();
			BeanUtils.copyProperties(roles, roleNew);
			roleNew.setUsers(null);
			userNew.setRoles(roleNew);
		}
		System.out.println(findOne);
		return userNew;
	}
	
}
