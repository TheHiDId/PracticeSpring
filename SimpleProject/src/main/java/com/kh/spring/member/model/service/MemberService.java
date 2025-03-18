package com.kh.spring.member.model.service;

import com.kh.spring.member.model.dto.MemberDTO;

public interface MemberService {
	// 로그인
	MemberDTO signIn(MemberDTO member);
	
	// 회원가입
	MemberDTO signUp(MemberDTO member);
	
	// 회원정보수정
	MemberDTO updateMember(MemberDTO member);
	
	// 회원탈퇴
	int deleteMember(MemberDTO member);
	
	// 아이디 중복 체크
	
	
	// 이메일 인증
}
