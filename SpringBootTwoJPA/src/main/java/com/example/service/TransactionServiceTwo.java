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


@Service
@Transactional
public class TransactionServiceTwo {
	
	@Autowired
	TestRepository testRepository;
	
	@Transactional(propagation=Propagation.REQUIRED)
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Test queryTestTwo() {
		Criteria<Test> criteria = new Criteria<>();
	    Sort sort = new Sort(Sort.Direction.DESC, "id");
		List<Test> testList = testRepository.findAll(criteria,sort);
		return testList.get(0);
	}
	
	
	
}
