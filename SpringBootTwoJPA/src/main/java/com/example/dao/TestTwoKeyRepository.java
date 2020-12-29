package com.example.dao;

import org.springframework.data.jpa.repository.Query;

import com.example.common.jpa.base.JpaBaseRepository;
import com.example.pojo.TestTwoKey;
import com.example.pojo.TestTwoKeyId;

// 通过联合主键的其中一个键值进行查询
public interface TestTwoKeyRepository  extends JpaBaseRepository<TestTwoKey,TestTwoKeyId>{
	
//	@Query("from TestTwoKey where id.testId = ?1")
//	TestTwoKey queryTestTwoKeyById(String testid);
}
