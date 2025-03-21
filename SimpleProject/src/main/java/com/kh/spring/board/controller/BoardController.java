package com.kh.spring.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		// 한 페이지에 몇 개 표시 == 5
		// 페이지 버튼 몇 개 표시 == 5
		// 총 게시글 개수 == SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'
		if(page < 1) throw new InvalidParameterException("유효하지 않은 페이지입니다.");
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		model.addAttribute("map", map);
		
		return "board/board_list";
	}
	
	@GetMapping("insert-board-form")
	public String toInsertBoard() {
		return "board/insert_board";
	}
	
	@PostMapping("insert-board")
	public ModelAndView insertBoard(BoardDTO board, ModelAndView mv, MultipartFile upfile, HttpSession session) {
		// log.info("{}, {}", board, upfile);
		
		// INSERT INTO TB_SPRING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER, CHANGE_NAME) VALUES (#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName})
		
		boardService.insertBoard(board, upfile, session);
		
		session.setAttribute("message", "게시글 등록에 성공했습니다.");
		
		mv.setViewName("redirect:boards");
		
		return mv;
	}
	
	@GetMapping("boards/{id}")
	public ModelAndView selectBoard(@PathVariable(name = "id") int boardNo, ModelAndView mv) {
		if(boardNo < 1) throw new InvalidParameterException("비정상적인 접근입니다.");
		
	 	BoardDTO board = boardService.selectBoard(boardNo);
		
	 	mv.addObject("board", board).setViewName("board/board_detail");
	 	
		return mv;
	}
}
