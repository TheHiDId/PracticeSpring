package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.HTMLManipulationException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;
import com.kh.spring.util.model.dto.PageInfo;
import com.kh.spring.util.template.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;

	@Override
	public void insertBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		MemberDTO signInMember = (MemberDTO)session.getAttribute("signInMember");
		
		if(signInMember == null || !signInMember.getMemberId().equals(board.getBoardWriter())) throw new HTMLManipulationException("경고! 웹 페이지가 조작되었습니다.");
		
		if(board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() ||
		   board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
		   board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			throw new InvalidParameterException("유효하지 않은 요청입니다.");
		}
		
		if(!file.getOriginalFilename().isEmpty()) {
			/*
			if(!file.getOriginalFilename().contains(".")) {
				
			}
			*/
			
			StringBuilder sb = new StringBuilder();
			
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			int random = (int)(Math.random() * 900) + 100;
			
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			sb.append("KH_");
			sb.append(currentTime);
			sb.append("_");
			sb.append(random);
			sb.append(ext);
			
			ServletContext application = session.getServletContext();
			
			String savePath = application.getRealPath("/resources/upload_files/");
			
			// 왜 경로가 여기로 잡힘? 
			// C:\workspace\PracticeSpring\LegacyProject\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SimpleProject\
			
			try {
				file.transferTo(new File(savePath + sb.toString()));
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			board.setChangeName("/spring/resources/upload_files/" + sb.toString());
		}
		
		boardMapper.insertBoard(board);
	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		int count = boardMapper.selectTotalCount();
		
		List<BoardDTO> boards = new ArrayList<BoardDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		PageInfo pi = Pagination.getPageInfo(count, currentPage, 5, 5);
		
		if(count != 0) {
			RowBounds rb = new RowBounds((currentPage -1) * 5, 5);

			boards = boardMapper.selectBoardList(rb);
		}
		
		map.put("boards", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

	@Override
	public BoardDTO selectBoard(int boardNo) {
		BoardDTO board = boardMapper.selectBoardAndReply(boardNo);
		
		if(board == null) throw new InvalidParameterException("존재하지 않는 게시글입니다.");
		
		return board;
	}

	@Override
	public BoardDTO updateBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		return null;
	}

	@Override
	public void deleteBoard(int boardNo, HttpSession session) {
		
	}
	
}
