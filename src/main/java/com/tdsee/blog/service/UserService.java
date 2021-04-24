package com.tdsee.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdsee.blog.model.RoleType;
import com.tdsee.blog.model.User;
import com.tdsee.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록 (IoC)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public User searchUser(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()-> {
			return new User();
		});
		return user;
	}
	
	@Transactional
	public void join(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void updateUser(User user) {
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("Could not find the user.");
		});
		if(persistance.getOauth() == null || persistance.getOauth().equals(""))
		{
			if (user.getPassword() != "") {
				String rawPassword = user.getPassword();
				String encPassword = encoder.encode(rawPassword);
				persistance.setPassword(encPassword);
			}
			persistance.setEmail(user.getEmail());
		}
	}
}
