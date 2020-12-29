package com.example.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.pojo.Users;
/**
 * 
 *PagingAndSortingRepository接口
 *
 */
public interface UsersRepositoryPagingAndSorting extends PagingAndSortingRepository<Users,Integer> {

}
