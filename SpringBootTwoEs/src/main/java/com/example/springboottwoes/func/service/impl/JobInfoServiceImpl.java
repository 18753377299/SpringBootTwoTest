package com.example.springboottwoes.func.service.impl;

import com.example.springboottwoes.func.dao.JobInfoDao;
import com.example.springboottwoes.func.po.JobInfo;
import com.example.springboottwoes.func.service.facade.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao jobInfoDao;

    @Override
    @Transactional
    public void save(JobInfo jobInfo) {
        //先从数据库查询数据,根据发布日期查询和url查询
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        List<JobInfo> list = this.findJobInfo(param);

        if (list.size() == 0) {
            //没有查询到数据则新增或者修改数据
            this.jobInfoDao.saveAndFlush(jobInfo);
        }
    }

    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {

        Example example = Example.of(jobInfo);

        List<JobInfo> list = this.jobInfoDao.findAll(example);

        return list;
    }

}
