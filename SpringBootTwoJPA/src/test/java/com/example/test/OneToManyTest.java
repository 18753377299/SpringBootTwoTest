package com.example.test;

import com.example.SpringBootTwoJpaApplication;
import com.example.dao.RiskInfoDiscussRepository;
import com.example.pojo.RiskInfoDiscuss;
import com.example.pojo.Roles;
import com.example.pojo.Users;
import com.example.riskfunc.test.dao.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * 一对多关联关系测试
 *
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class OneToManyTest {
	
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private RiskInfoDiscussRepository riskInfoDiscussRepository;
	
	/**
	 * 一对多关联关系的添加,使用@Data不能成功，自己添加get、set方法就可以成功
	 * 以前会失败，是因为创建表的时候外键关系创建错误。
	 */
	
	@Test
	public void testSave(){
		//创建一个用户
		Users users = new Users();
		users.setAddress("天津");
		users.setAge(32);
		users.setName("小刚");
		
		//创建一个角色
		Roles roles = new Roles();
		roles.setRolename("管理员");
//		//关联
//		roles.getUsers().add(users);
		users.setRoles(roles);
		
		//保存
		this.usersRepository.save(users);
		System.out.println(users);
	}
	@Test
	public void testRiskInfoDiscussSave(){
		//创建一个用户
		RiskInfoDiscuss riskInfoDiscuss = new RiskInfoDiscuss();
		riskInfoDiscuss.setExpertNo(11);
		riskInfoDiscuss.setScore(new BigDecimal(3.1));
		riskInfoDiscussRepository.save(riskInfoDiscuss);
//		riskInfoDiscussRepository.saveAndFlush(riskInfoDiscuss);
		Integer discussSequence = riskInfoDiscussRepository.queryDiscussSequence();
		riskInfoDiscuss.setSerialNo(discussSequence-1);
		System.out.println(riskInfoDiscuss);
	}
	
	/**
	 * 一对多关联关系的查询
	 */
	@Test
	public void testFind(){
		try {
			//		Users findOne = this.usersRepository.findOne(4);
			Optional<Users> optional = this.usersRepository.findById(4);
			Users findOne =  optional.isPresent()?optional.get():null;
			Users userNew= new Users();
			BeanUtils.copyProperties(findOne, userNew);
//			if(findOne!=null&&findOne.getRoles()!=null) {
//				Roles roleNew =new Roles();
//				Roles roles =findOne.getRoles();
//				BeanUtils.copyProperties(roles, roleNew);
//				roleNew.setUsers(null);
//				userNew.setRoles(roleNew);
//			}
//			if(null!=userNew) {
//				String json= JSON.toJSONString(userNew);
//				System.out.println(json);
//			}
			System.out.println(findOne);
//					usersRepository.delete(1);
			System.out.println(UUID.randomUUID());
			System.out.println(System.currentTimeMillis());
	//		Roles roles = findOne.getRoles();
	//		System.out.println(roles.getRolename());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
