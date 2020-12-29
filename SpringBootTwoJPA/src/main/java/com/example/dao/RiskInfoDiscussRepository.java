package com.example.dao;

import org.springframework.data.jpa.repository.Query;

import com.example.common.jpa.base.JpaBaseRepository;
import com.example.pojo.RiskInfoDiscuss;
import com.example.pojo.RiskInfoDiscussId;

public interface RiskInfoDiscussRepository extends JpaBaseRepository<RiskInfoDiscuss, RiskInfoDiscussId>{

	// 不能获取当前的序列号的值
	@Query(value="select nextval ('riskinfo_discuss_sequence')",nativeQuery=true)
	Integer queryDiscussSequence();
}
