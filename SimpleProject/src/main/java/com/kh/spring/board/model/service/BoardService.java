package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

@Service
public interface BoardService {
	// 게시글 작성 (파일 첨부)
	void insertBoard(BoardDTO board, MultipartFile file);
	
	// 게시글 목록 조회
	List<BoardDTO> selectBoardList(int currentPage);
	
	// 게시글 상세보기 (댓글 조회)
	BoardDTO selectBoard(int boardNo);
	
	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	
	// 게시글 삭제 (STATUS 업데이트 방식)
	void deleteBoard(int boardNo);
	
	// 게시글 검색
	// 댓글 작성
	// 댓글 수정
	// 댓글 삭제
}
