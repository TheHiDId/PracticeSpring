package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.HTMLManipulationException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.NotSignInException;
import com.kh.spring.exception.PasswordMissMatchException;
import com.kh.spring.exception.UpdateOneRowFailException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	// private final MemberDAO memberDao;
	// private final SqlSessionTemplate sqlSession;
	private final PasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	private final MemberMapper memberMapper;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	
	@Override
	public MemberDTO signIn(MemberDTO member) {
		validator.validatedMember(member);
		
		MemberDTO signInMember = memberMapper.signIn(member);
		
		validator.idNotFound(signInMember);
		
		if(!passwordEncoder.matches(member.getMemberPw(), signInMember.getMemberPw())) throw new PasswordMissMatchException("비밀번호가 일치하지 않습니다.");
		
		return signInMember;
	}

	@Override
	public void signUp(MemberDTO member) {
		validator.validatedMember(member);
		
		memberMapper.signIn(member);
		
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		
		int signUpResult = memberMapper.signUp(member);
		
		validator.signUpSuccessCheck(signUpResult);
	}

	@Override
	public void updateMember(MemberDTO member, HttpSession session) {
		MemberDTO signInMember = (MemberDTO)session.getAttribute("signInMember");
		
		if(signInMember == null) throw new NotSignInException("로그인된 상태가 아닙니다. 먼저 로그인해주세요.");
		
		if(!member.getMemberId().equals(signInMember.getMemberId())) throw new HTMLManipulationException("경고! 웹 페이지가 조작되었습니다.");
		
		MemberDTO signInResult = memberMapper.signIn(member);
		
		if(signInResult == null) throw new MemberNotFoundException("존재하지 않는 아이디입니다.");
		
		int updateResult = memberMapper.updateMember(signInResult);
		
		if(updateResult != 1) throw new UpdateOneRowFailException("알 수 없는 이유로 회원정보 수정에 실패했습니다. 다시 시도해주세요.");
		
		signInMember.setMemberName(member.getMemberName());
		signInMember.setEmail(member.getEmail());
	}

	@Override
	public int deleteMember(MemberDTO member, HttpSession session) {
		MemberDTO signInMember = (MemberDTO)session.getAttribute("signInMember");
		
		return 0;
	}
}
