package com.example.riskfunc.test.service.facade;


import com.example.pojo.Test;
import com.example.pojo.Users;

import java.util.List;

public interface TestService {
    public List<Test> findAll();

    public void insertTest();

    public void insertTwoDiff();

    public void deleteTest(String  id);

    public Users queryUser();
}
