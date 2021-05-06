package com.example.common.po;

import lombok.*;

import javax.persistence.*;

/*
create table Klass (
        klassId int,
        name varchar(255),
        primary key (klassId)
);
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Klass")
@ToString
public class Klass {

    @Id
    @Column(name = "klassId")
    private Integer  klassId;

    @Column(name = "name")
    private String name;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "klassId", referencedColumnName = "klassId", nullable = false, insertable = false, updatable = false)
    private Course course;

}


