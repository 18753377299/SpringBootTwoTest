package com.example.riskfunc.test.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.common.service.facade.TransactionServiceOne;
import com.example.common.service.facade.TransactionServiceTwo;
import com.example.pojo.User;
import com.example.riskfunc.test.dao.UsersRepository;
import com.example.riskfunc.test.service.facade.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.example.pojo.Test;
import com.example.pojo.Users;
import com.example.vo.AjaxResult;
import com.example.vo.RiskRequestVo;

@RestController
@RequestMapping("/test")
@Validated
//@EnableWebMvc
public class TestAPi {
	
	@Autowired
	TestService testService;

	@Autowired
	UsersRepository usersRepository;
	/**测试,相同的url，不同的参数和请求类型会不会导致冲突--begin
	 * 结果: 如果请求类型不同,可以有相同的url，
	 *  如果请求类型相同，url相同，
	 * */
	// 查询
	 @PostMapping(value = "/hello")
     public AjaxResult<Test> say(@RequestBody User user){
		 Test  test = null;
		 System.out.println("hello everyBody");
		 List<Test> testList= testService.findAll();
		 if(null!=testList&&testList.size()>0) {
			 test =  testList.get(0);
		 }
         return AjaxResult.ok(test);
     }
	/*@PostMapping(value = "/hello")
	public AjaxResult<Test> sayParam(String riskFileNo){
		Test  test = null;
		System.out.println("===>"+riskFileNo);
		return AjaxResult.ok(test);
	}*/

	@GetMapping(value = "/hello")
	public AjaxResult<Test> sayOne(@RequestParam String riskFileNo){
		Test  test = null;
		System.out.println("===>"+riskFileNo);
	 	return AjaxResult.ok(test);
	}
	/**测试,相同的url，不同的参数和请求类型会不会导致冲突--end*/

	// 查询
	 @RequestMapping(value = "/helloTwo",method = RequestMethod.GET)
     public String sayTwo(){
		 Test  test = null;
		 System.out.println("hello everyBody");
		 List<Test> testList= testService.findAll();
		 if(null!=testList&&testList.size()>0) {
			 test =  testList.get(0);
		 }
         return "sayTwo";
     }
	 // 增加
	 @RequestMapping(value = "/insert",method = RequestMethod.GET)
     public String insert(){
		 testService.insertTest();
         return "insertTest";
     }
	// 增加两个不同的表
	 @RequestMapping(value = "/insertTwoDiff",method = RequestMethod.GET)
     public String insertTwoDiff(){
		 testService.insertTwoDiff();
         return "insertTwoDiff";
     }
	 // 删除
	 @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
     public String delete(@PathVariable("id") String  id){
		 testService.deleteTest(id);
         return "deleteTest";
     }
	 //修改
	 @RequestMapping(value = "/update",method = RequestMethod.GET)
     public String update(){
//		 testService.updateTest();
         return "updateTest";
     }
	 //测试
	 @RequestMapping(value = "/test",method = RequestMethod.POST)
     public AjaxResult test(@RequestBody RiskRequestVo riskRequestVo,HttpServletRequest request){
		 AjaxResult ajaxResult =new AjaxResult();
//		 String name = riskRequestVo.getName();
//		 String time= riskRequestVo.getInsertTime().toString();
//		 String contextPath = request.getContextPath();
//		 testService.updateTest();
		 Users users = new Users();
		 users.setAddress("qqqq");
		 users.setInsertTimeForHis(riskRequestVo.getInsertTime());
		 usersRepository.save(users);
//		 insertTimeForHis
		 ajaxResult.setData("/home/middle/file/abc_ccc.doc");
         return ajaxResult;
     }
	// 查询
	 @RequestMapping(value = "/queryUser",method = RequestMethod.GET)
     public Users queryUser(){
		 Users users = testService.queryUser();
		 if(null!=users) {
			String json= JSON.toJSONString(users);
			System.out.println(json);
		 }
         return users;
     }
	 
	 @Autowired
	 TransactionServiceOne transactionServiceOne;
	 @Autowired
	 TransactionServiceTwo transactionServiceTwo;
	 
	 @GetMapping(value = "/testTransaction")
	 public String testTransaction() {
		 
		String result =  transactionServiceOne.saveTest();
		
		return result;
	 }
	 
	 @RequestMapping(value = "/queryOutlineByRiskFileNo", method = RequestMethod.POST)
	 @ResponseBody
	 public String queryByRiskFileNo(String riskControlRequest) {
		 System.out.println(riskControlRequest);
		 return  null;
	 }
	 
}
