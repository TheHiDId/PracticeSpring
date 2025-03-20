package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.spring.member.model.dto.MemberDTO;

public interface MemberService {
	// 로그인
	MemberDTO signIn(MemberDTO member);
	
	// 회원가입
	void signUp(MemberDTO member);
	
	// 회원정보수정
	void updateMember(MemberDTO member, HttpSession session);
	
	// 회원탈퇴
	int deleteMember(MemberDTO member, HttpSession session);
	
	// 아이디 중복 체크
	
	
	// 이메일 인증
}
