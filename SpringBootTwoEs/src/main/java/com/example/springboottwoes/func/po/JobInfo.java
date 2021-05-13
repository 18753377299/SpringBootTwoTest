package com.example.springboottwoes.func.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyAddr;
    private String companyInfo;
    private String jobName;
    private String jobAddr;
    private String jobInfo;
    private Integer salaryMin;
    private Integer salaryMax;
    private String url;
    private String time;

}


