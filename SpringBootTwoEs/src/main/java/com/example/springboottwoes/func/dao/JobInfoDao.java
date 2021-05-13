package com.example.springboottwoes.func.dao;

import com.example.springboottwoes.func.po.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobInfoDao extends JpaRepository<JobInfo, Long> {
}

