package com.tdsee.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tdsee.blog.dto.ReplySaveRequestDto;
import com.tdsee.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	@Transactional
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3,  now())", nativeQuery = true)
	int mSave(int userId, int boardId, String content);
}