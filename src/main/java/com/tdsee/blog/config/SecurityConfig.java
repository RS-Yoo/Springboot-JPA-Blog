package com.tdsee.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tdsee.blog.config.auth.PrincipalDetailService;


@Configuration // bean 등록: 스프링 컨테이너에서 객체를 관리할 수 있도록 하는 것(IoC관리)
@EnableWebSecurity // 시큐리티 필터 등록 
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근 시 권한 및 인증 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC 됨
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해줄 때, 해당 패스워드가 무엇으로 해쉬되었는지 알아야
	// 같은 해쉬로 암호화해서 DB의 해시와 비교할 수 있음  
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  // csrf 토큰 비활성화 (테스트 시 걸어두기)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/"); // 스프링 시큐리티가 해당 주소로 요청 오는 로그인 가로챔, 대신 로그인
	}
}
