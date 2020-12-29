package com.example.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_two_key")
public class TestTwoKey implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 对应字段：id */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "testId", column = @Column(name = "testid")),
			@AttributeOverride(name = "testType", column = @Column(name = "testtype")) 
			})
	private TestTwoKeyId id;
	/** 对应字段：name */
	@Column(name = "name")
	private String name;
	
	@ApiModelProperty(value = "插入时间")
	@CreatedDate
	@Column(name = "inserttime",updatable=false)
	private Date insertTime;
	
	@ApiModelProperty(value = "更新时间")
	@LastModifiedDate
//	@Column(name = "OPERATETIMEFORHIS",insertable=false)
	@Column(name = "updatetime")
	private Date updateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
	private TestTwo testTwo;
	
}
