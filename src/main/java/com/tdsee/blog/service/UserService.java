package com.tdsee.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdsee.blog.model.User;
import com.tdsee.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록 (IoC)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void join(User user) {
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료(그때까지 정합성 유지)
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
}
