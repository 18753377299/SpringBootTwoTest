package com.example.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@IdClass(RiskInfoDiscussId.class)
@Table(name = "RISKINFO_DISCUSS_2")
@EntityListeners(AuditingEntityListener.class)
public class RiskInfoDiscuss  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**专家编号*/
	private Integer expertNo;
	/**序号*/
	private Integer serialNo;
	/**评分*/
	private BigDecimal score;
	/**评论*/
	private String discuss;
	/**评论人代码*/
	private String userCode;
	/**评论人名称*/
	private String userName;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;
	
	@Id
	@Column(name = "EXPERTNO", nullable = false)
	public Integer getExpertNo() {
		return expertNo;
	}
	public void setExpertNo(Integer expertNo) {
		this.expertNo = expertNo;
	}
	
	@Id
	@SequenceGenerator(name="generator",allocationSize=1,sequenceName="riskinfo_discuss_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="generator") 
	@Column(name = "SERIALNO", nullable = false)
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "SCORE", nullable = true)
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
	@Column(name = "DISCUSS", nullable = true)
	public String getDiscuss() {
		return discuss;
	}
	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}
	
	@Column(name = "USERCODE", nullable = true)
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@Column(name = "USERNAME", nullable = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@CreatedDate
	@Column(name = "INSERTTIMEFORHIS",insertable=false,updatable=false)
//	@Column(name = "INSERTTIMEFORHIS",updatable=false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	
	@LastModifiedDate
	@Column(name = "OPERATETIMEFORHIS",insertable=false)
//	@Column(name = "OPERATETIMEFORHIS")
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	
	
}
