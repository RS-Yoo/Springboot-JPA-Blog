package com.tdsee.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdsee.blog.model.User;
import com.tdsee.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록 (IoC)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public int join(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: join(): " + e.getMessage());
			return -1;
		}
	}
}
