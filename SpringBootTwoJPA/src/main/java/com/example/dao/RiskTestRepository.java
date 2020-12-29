package com.example.dao;

import org.springframework.data.jpa.repository.Query;

import com.example.common.jpa.base.JpaBaseRepository;
import com.example.pojo.TestTwo;

public interface RiskTestRepository extends JpaBaseRepository<TestTwo,Integer>{
	
//	@Query("from TestTwo where id = ?1")
	TestTwo queryTestTwoById(Integer id);
}
