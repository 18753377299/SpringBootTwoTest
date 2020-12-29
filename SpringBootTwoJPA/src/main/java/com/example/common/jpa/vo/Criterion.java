package com.example.common.jpa.vo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Criterion {  
    
    public enum Operator {  
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR ,IS_MEMBER,IS_NOT_MEMBER, INTERCEPT, NINTERCEPT,ISNOTNULL,ISNULL
//        ,equal,notEqual,like,greaterThan,lessThan,greaterThanOrEqualTo,lessThanOrEqualTo,
    }  
    
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder);
    
}
