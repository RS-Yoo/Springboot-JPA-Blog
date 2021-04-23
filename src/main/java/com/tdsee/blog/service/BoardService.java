package com.tdsee.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdsee.blog.model.Board;
import com.tdsee.blog.model.RoleType;
import com.tdsee.blog.model.User;
import com.tdsee.blog.repository.BoardRepository;
import com.tdsee.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void Post(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> PostList(Pageable pageable)  {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board viewContents(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("Failed: Cannot find ID.");
				});
	}
	
	@Transactional
	public void deletePost(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void updatePost(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("Failed: Cannot find ID.");
				});  // 영속화 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료 시(Service 종료될 때) 트랜젝션 종료 (더티체킹) - 자동 업데이트
	}
}
