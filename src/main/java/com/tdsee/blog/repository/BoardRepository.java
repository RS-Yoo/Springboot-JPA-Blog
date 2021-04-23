package com.tdsee.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdsee.blog.model.Board;
import com.tdsee.blog.model.User;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
