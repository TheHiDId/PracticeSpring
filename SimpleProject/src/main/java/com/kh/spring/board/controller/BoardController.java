package com.kh.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("boards")
	public String toBoardList() {
		return "board/board_list";
	}
	
	@GetMapping("insert-board-form")
	public String toInsertBoard() {
		return "board/insert_board";
	}
	
	@PostMapping("insert-board")
	public String insertBoard(BoardDTO board) {
		return null;
	}
}
