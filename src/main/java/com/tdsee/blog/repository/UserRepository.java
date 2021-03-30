package com.tdsee.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdsee.blog.model.User;

// DAO
// 자동으로 bean등록됨
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
