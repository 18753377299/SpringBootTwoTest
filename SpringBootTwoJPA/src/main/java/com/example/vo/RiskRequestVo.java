package com.example.vo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.example.common.valid.group.GroupA;
import com.example.common.valid.group.GroupB;
import com.example.pojo.Parent;

import lombok.Data;

/*进行校验的时候，最好用到那个组就给每一个字段进行分组，否则容易出错，
有的进行校验之后不分组，有可能是对所有分组进行校验*/

@Data
public class RiskRequestVo {
	 /*设置组就只能校验改组*/
	 @NotBlank(message="姓名不能为空",groups= {GroupA.class})
	 private String name;
	 
	 @Max(value=99,message="年龄不能超过99")
	 private Integer age;
	 
	 @NotBlank(message = "sex cannot be null")
	 private String sex;
	 
	 private Date insertTime;
	 
	 @Valid
	 @NotNull(message="parent子级不能为null",groups= {GroupA.class,GroupB.class})
	 private Parent parent;
	 
	 /*parents参数，@NotEmpty只能保证list不为空，但是list中的元素是否为空、
	 Parent对象中的属性是否合格，还需要进一步的校验*/
//	 /*级联参数校验,对含有子级的进行校验*/
	 @Valid
	 @NotEmpty(message="parents子级集合不能为空",groups= {GroupA.class,GroupB.class})
	 @NotNull(groups= {GroupB.class}) 
	 private List<Parent> parents;
	 
}
