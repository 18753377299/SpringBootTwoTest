package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.jpa.vo.Criteria;
import com.example.dao.TestRepository;
import com.example.pojo.Test;

import io.swagger.annotations.ApiModel;

@Service
@Transactional
@ApiModel(value="进行事务的测试：事务的传播行为："
		+ "所谓事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。")
public class TransactionServiceOne {
	
	@Autowired
	TestRepository testRepository;
	
	@Autowired 
	TransactionServiceOne transactionServiceOne;
	
	@Autowired 
	TransactionServiceTwo transactionServiceTwo;
	
	/** https://www.cnblogs.com/panchanggui/p/10882749.html
	 * 事务的传播行为:
	* REQUIRED ：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
	* SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
	* MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
	* REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
	* NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
	* NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。
	* NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED 。
	*  指定方法：通过使用 propagation 属性设置，例如：
	* @Transactional(propagation = Propagation.REQUIRED)
	*/
	
	
	/**事务场景描述： 当保存(保存的时候执行自增操作)的时候对本serviceOne中的进行查询，同时，对serviceTwo进行查询，比较两次的查询出的ID之间的区别
	 * 1. 当queryTestOne为REQUIRED ，同时queryTestTwo 为 REQUIRED，两次查询出来的都是同一种新增的ID。
	 *    注： REQUIRED ：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。--->所以两个方法执行合并到一个事务中了。
	 * 2. 当queryTestOne为REQUIRED ，同时queryTestTwo 为 REQUIRES_NEW，第一次REQUIRED查询出来的是新增的ID，第二次REQUIRES_NEW查询出来的ID是库中的ID。
	 *  	注： REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。  --->
	 *  3、如果事务中发生了一个异常，如果这个异常被try{}catch了，那么事务依旧可以往下进行，否则事务将被终止。
	 * 问题1： 如果捕获异常，事务可以往下进行，这是不是说明事务没有进行回滚，是不是一个问题？？
	 * 答： Spring事务异常回滚，捕获异常不抛出就不会回滚。--->默认spring事务只在发生未被捕获的 runtimeexcetpion时才回滚。
	 * spring aop  异常捕获原理：被拦截的方法需显式抛出异常，并不能经任何处理，这样aop代理才能捕获到方法的异常，才能进行回滚，
	 *  默认情况下aop只捕获runtimeexception的异常，但可以通过  。
	 * */
	@Transactional(propagation=Propagation.REQUIRED)
	public String  saveTest() {
		Integer idOne =0;
		
		Test testA = new Test();
//		testA.setId("33");
		testA.setName("wangerxiao");
		testRepository.save(testA);
		
		idOne = this.queryTestOne().getId();
		System.out.println("=====idOne==========="+idOne);
		
		try {
			int result = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(); 
		}
		Integer idTwo =0;
		idTwo = transactionServiceTwo.queryTestTwo().getId();
		System.out.println("=====idTwo==========="+idTwo);
		return idOne+":"+idTwo;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Test queryTestOne() {
		Criteria<Test> criteria = new Criteria<>();
	    Sort sort = new Sort(Sort.Direction.DESC, "id");
		List<Test> testList = testRepository.findAll(criteria,sort);
		return testList.get(0);
	}
	
	
	
	
	
	
	
	
	
}
