package com.kh.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.spring.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	/*
	@RequestMapping("signIn")
	public String signIn(@RequestParam(value = "id", defaultValue = "abcd1234") String id,
						 @RequestParam(value = "pw", defaultValue = "abcd1234") String pw) {
		log.info("id: {}, pw: {}", id, pw);
		
		return "main_page";
	}
	*/
	
	/*
	@PostMapping("signIn")
	public String signIn(String id, String pw) {
		MemberDTO member = new MemberDTO();
		
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		return "main_page";
	}
	*/
	
	/* 커맨드 객체 방식
	 * 
	 * 전제 조건
	 * 1. 매개변수 자료형에 반드시 기본 생성자가 존재할 것
	 * 2. 전달되는 키 값과 객체의 필드명이 동일할 것
	 * 3. setter 메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본 생성자를 통해서 생성한 후 내부적으로 setter 메서드를 찾아서 요청 시 전달값을 해당 필드에 대입해 줌
	 * (Setter Injection)
	 */
	@PostMapping("signIn")
	public String signIn(MemberDTO member) {
		log.info("{}", member);
		
		return "main_page";
	}
}
