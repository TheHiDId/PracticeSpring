package com.kh.spring.member.model.service;

import java.security.InvalidParameterException;

import org.springframework.stereotype.Component;

import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordMissMatchException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.UpdateOneRowFailException;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {
	private PasswordEncoder passwordEncoder;
	
	private void validatedLength(MemberDTO member) {
		if(member.getMemberId().length() > 10) {
			throw new TooLargeValueException("아이디가 너무 깁니다. 10자 이내로 작성해주세요.");
		}
	}
	
	private void validatedValue(MemberDTO member) {
		if(member == null ||
		   member.getMemberId() == null ||
		   member.getMemberId().trim().isEmpty() ||
		   member.getMemberPw() == null ||
		   member.getMemberPw().trim().isEmpty()) {
			throw new InvalidParameterException("유효하지 않은 입력값입니다.");
		}
	}
	
	public void validatedMember(MemberDTO member) {
		validatedLength(member);
		validatedValue(member);
	}
	
	public void idNotFound(MemberDTO member) {
		if(member == null) throw new MemberNotFoundException("존재하지 않는 아이디입니다.");
	}
	
	public void pwNotFound(MemberDTO member, MemberDTO signInMember) {
		if(!passwordEncoder.matches(member.getMemberPw(), signInMember.getMemberPw())) throw new PasswordMissMatchException("비밀번호가 일치하지 않습니다.");
	}
	
	public void signUpSuccessCheck(int signUpResult) {
		if(signUpResult != 1) throw new UpdateOneRowFailException("알 수 없는 이유로 회원가입에 실패했습니다.");
	}
}
