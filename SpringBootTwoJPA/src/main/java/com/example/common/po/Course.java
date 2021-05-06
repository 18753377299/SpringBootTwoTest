package com.example.common.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
create table Course (
    courseId int,
    name varchar(255),
    primary key (courseId)
    );
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "courseId")
    private Integer  courseId;

    @Column(name = "name")
    private String name;

//    @OneToOne(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "course")
    @OneToOne(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "course")
    private Klass klass;



}


