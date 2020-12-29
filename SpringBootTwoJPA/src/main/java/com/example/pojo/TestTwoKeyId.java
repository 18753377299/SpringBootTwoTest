package com.example.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TestTwoKeyId implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "testid", nullable = false)
	private String testId;
	
	@Column(name = "testtype", nullable = false)
	private String testType;
}
