package com.tdsee.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tdsee.blog.model.User;
import com.tdsee.blog.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// username이 DB에 있는지 확인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("User not found.");
		});
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보 저장
	}
}
