package com.example.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.pojo.Users;

/**
 * CrudRepository接口
 *
 *
 */
public interface UsersRepositoryCrudRepository extends CrudRepository<Users, Integer> {

}
