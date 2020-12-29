package com.example.api;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.exception.customException.ApplicationException;
import com.example.common.valid.group.GroupA;
import com.example.common.valid.group.GroupB;
import com.example.vo.RiskRequestVo;

import io.swagger.annotations.ApiOperation;

/*该类用于进行字段校验测试*/

@RestController
//@Validated
public class RiskValidateApi {
	@Autowired
    private Validator validator;
	
	@GetMapping("/getUserTest")
	@ApiOperation(value="使用get方式对字段进行校验",notes="addby liqiankun20200611 begin")
    public String getUserTest(String name,int age) {
		Assert.notNull(name,  "name_null 不能为空");
		Assert.hasText(name, "name 不能为空");
		if(name==""||name=="null"||name==null) {
			throw new ApplicationException("name 不能为空");
		}
		Assert.hasLength(name, "name 不能为''");
        return "name: " + name + " ,age:" + age;
    }
	
	@GetMapping("/getUser")
	@ApiOperation(value="使用get方式对字段进行校验",notes="addby liqiankun20200611 begin")
    public String getUserStr(@NotBlank(message = "name 不能为空") String name,
   		 @Max(value = 99, message = "不能大于99岁") int age) {
		Assert.isNull(name, "name 不能为空");
       return "name: " + name + " ,age:" + age;
    }
	
	@PostMapping(value = "/getUser")
	@ApiOperation(value="使用Post方式对字段进行校验",notes="addby liqiankun20200611 begin")
    public String getUserStr(@RequestBody @Validated({GroupA.class, Default.class}) RiskRequestVo riskRequestVo, 
    		BindingResult bindingResult){
		 validData(bindingResult);
	    return "name: " + riskRequestVo.getName() + ", age:" + riskRequestVo.getAge();
    }
	@PostMapping("/setUser")
    public String setUser(@RequestBody @Validated RiskRequestVo riskRequestVo, BindingResult bindingResult) {
        validData(bindingResult);
//        Parent parent = riskRequestVo.getParent();
//	    validObject(parent, validator, GroupB.class, Default.class);
        return "name: " + riskRequestVo.getName() + ", age:" + riskRequestVo.getAge();
    }
	
	@PostMapping("/setUserTest")
    public String setUserTest(@RequestBody RiskRequestVo riskRequestVo) {
//        Parent parent = riskRequestVo.getParent();
//		throw new ApplicationException("这是系统自定义的异常");
	    validObject(riskRequestVo, validator, GroupB.class, Default.class);
        return "name: " + riskRequestVo.getName() + ", age:" + riskRequestVo.getAge();
    }
	
	 private void validData(BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) {
	            StringBuffer sb = new StringBuffer();
	            for (ObjectError error : bindingResult.getAllErrors()) {
	                sb.append(error.getDefaultMessage());
	            }
	            throw new ValidationException(sb.toString());
	        }
	  }
	 /**
     * 实体类参数有效性验证
     * @param bean 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；验证失败：将错误信息添加到message中
     */
    public void validObject(Object bean, Validator validator, Class<?> ...groups) {
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean, groups);
        if (!constraintViolationSet.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation violation: constraintViolationSet) {
                sb.append(violation.getMessage());
            }

            throw new ValidationException(sb.toString());
        }
    }
	
	
	
}
