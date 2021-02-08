package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import io.swagger.annotations.Api;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Api(value="请假流程排他网关")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class LeaveExclusiveTest {

}
