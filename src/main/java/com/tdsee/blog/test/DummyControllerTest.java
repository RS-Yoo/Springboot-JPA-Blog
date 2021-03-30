package com.tdsee.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tdsee.blog.model.RoleType;
import com.tdsee.blog.model.User;
import com.tdsee.blog.repository.UserRepository;

// html이 아니라 data를 리턴해주는 컨트롤러
@RestController
public class DummyControllerTest {

	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	// save함수는 id를 전달하지 않으면 insert
	// 전달하면 해당 id에 대한 데이터가 있을 때 update, 없을 때 insert
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 아이디는 존재하지 않습니다.";
		}
		
		return "삭제되었습니다. id: " + id;
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		// json 데이터를 요청 -> Java Object(MessageConverter의 Jackson라이브러리가)로 변환
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(() ->  {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		// userRepository.save(user);
		return user;
	}
	
	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건의 데이터를 리턴
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	// {id} 주소로 파라미터를 전달받을 수 있음 
	// http://localhost:8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4를 찾는데 데이터베이스에서 찾지 못하면 user가 null이 됨
		// return null -> 문제
		// Optional로 User객체를 감싸서 가져옴
		// null 인지 아닌지 판단해서 return
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id: " + id);
			}
		});
		// 요청: 웹브라우저
		// user 객체 = 자바 오브젝트
		// 웹브라우저가 이해할 수 있는 데이터로 변환 -> json
		// 스프링부트는 MessageConverter가 응답시에 자동으로 작동
		// Jackson 라이브러리 호출, 오브젝트를 json으로 변환
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username:" + user.getUsername());
		System.out.println("password:" + user.getPassword());
		System.out.println("email:" + user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
