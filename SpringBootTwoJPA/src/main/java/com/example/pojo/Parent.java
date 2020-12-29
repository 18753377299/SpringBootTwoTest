package com.example.pojo;



import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.example.common.valid.group.GroupA;
import com.example.common.valid.group.GroupB;

import lombok.Data;


@Data
public class Parent {
	
	@NotEmpty(message = "parent name cannot be empty", groups = {GroupB.class})
    private String name;

    @Email(message = "should be email format", groups = {GroupA.class})
    private String email;
    
    private List<Parent> childParents;
    
}
