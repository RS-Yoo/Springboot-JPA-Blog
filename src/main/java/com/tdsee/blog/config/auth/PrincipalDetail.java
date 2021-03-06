package com.tdsee.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tdsee.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링 시큐리티가 로그인 진행, 완료되면 UserDetails타입 오브젝트를
// 스프링 시큐리티의 고유한 세션에 저장
@Data
public class PrincipalDetail implements UserDetails {
	private User user;  // Composition 
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 (true: 만료 안 됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨 있는지 (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되었는지 (true: 만료 안 됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화가 되었는지 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정이 가지고 있는 권한 목록 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		
		collectors.add(()  -> {return "ROLE_"+user.getRole();});
		
		return collectors;
	}
	
	
	
}
