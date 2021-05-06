package com.example.test;


import com.example.SpringBootTwoJpaApplication;
import com.example.common.jpa.condition.Restrictions;
import com.example.common.jpa.vo.Criteria;
import com.example.common.service.facade.SaaAPIService;
import com.example.dao.RiskReportSaleMainRepository;
import com.example.dao.RiskTestRepository;
import com.example.dao.TestTwoKeyRepository;
import com.example.pojo.TestTwo;
import com.example.pojo.TestTwoKey;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class RiskTest {
	@Autowired 
    private RiskTestRepository riskTestRepository;
	
	@Autowired 
    private TestTwoKeyRepository testTwoKeyRepository;
	
	@Autowired
	private RiskReportSaleMainRepository riskReportSaleMainRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
    SaaAPIService  saaAPIService;
	
	@Test
	public void testJdbcTemplate() {
//		String sql = "select ";
//		jdbcTemplate.query
		Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<String[]> countListC = new ArrayList<String[]>();
        try {
            conn = dataSource.getConnection();
            String sqlC = "select count(age) from  t_users";
            stat = conn.prepareStatement(sqlC);
            rs = stat.executeQuery();
            for (; rs.next(); ) {
                String[] str = new String[2];
                str[0] = rs.getString(1);
//                str[1] = rs.getString(2);
                countListC.add(str);
            }
        }catch (Exception e) {
        	e.printStackTrace();
		}
	}
	 @ApiOperation(value="对jpa的封装方法的各种类型进行测试（包括分页查询和添加各种查询的条件）,"
	    		+ "只剩下添加sql语句作为条件没有测试成功")
    @Test
    public void test(){
       Criteria<TestTwo> criteria = new Criteria<>();
       String name = "wan";
       Integer id = null;
//       if(StringUtils.isNotBlank(name)) {
//    	   criteria.add(Restrictions.like("name","%wan%"));
//       }
       if(id!=null) {
    	   criteria.add(Restrictions.eq("id",1));
       }
       // 或查询
//       criteria.add(Restrictions.or(Restrictions.like("name", name),Restrictions.like("password", "%11%")));
//       使用in进行查询
       //criteria.add(Restrictions.in("explorer",Arrays.asList(explorer.trim(), explorerCode.trim()),true));
       //postGre进行and查询
//       criteria.add(Restrictions.and(Restrictions.eq("name", "w")));
//       进行字段截取查询
       criteria.add(Restrictions.intercept("name", "w", 1, 1));
       String  password = null;
       criteria.add(Restrictions.eq("password",password));
       Specification specification  = saaAPIService.addPower("41270000");
       if(null !=specification){
         criteria.and(specification);
       }

//       Sort sort = null;
//       Sort sort = new Sort(new Order(Direction.DESC,"id"));
//       criteria.add(Restrictions.eq("id",1)).add(Restrictions.like("name","wan"));
       // 多条件进行排序
       List< Order> orders=new ArrayList< Order>();
       orders.add( new Order(Direction. DESC, "id"));
       orders.add( new Order(Direction.ASC, "password"));
//       sort = new Sort(orders);
//       List<TestTwo> users = riskTestRepository.findAll(criteria,sort);
       /*springboot1.0用法*/
       Pageable pageable = null;
       Sort sort = new Sort(Direction.ASC, "id");
       pageable = new PageRequest(0, 2, sort);

       Page<TestTwo> pageTestTwo =  riskTestRepository.findAll(criteria, pageable);
       List<TestTwo> users = pageTestTwo.getContent();

       if(!users.isEmpty()) {
    	   System.out.println(users.get(0).getName());
       }
       
       /*springboot2.0用法*/
//       Page<TestTwo> users =  riskTestRepository.findAll(criteria, 
//    		   PageRequest.of(0, 1,Sort.by(Sort.Direction.DESC, "age")));
       for(TestTwo testTwo:  users) {
    	   System.out.println(testTwo);
       }
    }
    
    @Test
    @ApiOperation(value="对findOne单个查询进行测试")
    public void testSearch() {
    	Criteria<TestTwo> criteria = new Criteria<>();
        String name = "wan";
        Integer id = 1;
//        if(StringUtils.isNotBlank(name)) {
//     	   criteria.add(Restrictions.like("name","%wan%"));
//        }
        if(id!=null) {
     	   criteria.add(Restrictions.eq("id",1));
        }
        try {
//			TestTwo testTwo = riskTestRepository.findOne(criteria);
//        TestTwo testTwo = riskTestRepository.queryTestTwoById(2);
//			System.out.println(testTwo);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    @ApiOperation(value="通过联合主键的其中一个键值进行查询,如果criteria中没有传值那就是查询所有的信息")
    public void testSearchTwoKey() {
    	/*第一种方式*/
//        TestTwoKey testTwokey = testTwoKeyRepository.queryTestTwoKeyById("1");
//        System.out.println(testTwokey==null?null:testTwokey.getName());
        /*第二种方式*/
        Criteria<TestTwoKey> criteria = new Criteria<>();
        String name = "wan";
        Integer id = 1;
//        if(StringUtils.isNotBlank(name)) {
//     	   criteria.add(Restrictions.eq("id.testId","2"));
//        }
//        TestTwoKey testTwokey2 = testTwoKeyRepository.findOne(criteria);
        
//         if(StringUtils.isNotBlank(name)) {
//      	   criteria.add(Restrictions.eq("id.testId","2"));
//         }
        List<TestTwoKey> testTwokeyList = testTwoKeyRepository.findAll(criteria);
        if(!testTwokeyList.isEmpty()) {
     	   System.out.println(testTwokeyList.get(0).getName());
        }
        
        for(TestTwoKey testTwoKey:  testTwokeyList) {
     	   System.out.println(testTwoKey);
        }

    }


   
    
}
