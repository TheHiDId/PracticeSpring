package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 의존성 주입 생성자를 생성해주는 애노테이션
public class MemberController {
	
	// @Autowired 1번
	private final MemberService memberService;
	
	/* 2번
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	/*
	@Autowired // 3번
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
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
	
	/*
	@PostMapping("signIn")
	public String signIn(MemberDTO member, HttpSession session, Model model) {
		
		MemberDTO signInMember = memberService.signIn(member);
		
		if(signInMember == null) {
			// 로그인 실패 시
			// Spring에서는 Model 객체를 이용해서 RequestScope에 값을 담음
			model.addAttribute("message", "로그인 실패!");
			
			return "include/error-page";
		}
		
		// 로그인 성공 시
		session.setAttribute("signInMember", signInMember);
		
		return "redirect:/";
	}
	*/
	
	// 반환 타입을 ModelAndView로 해보자
	@PostMapping("signIn")
	public ModelAndView signIn(MemberDTO member, HttpSession session, ModelAndView mv) {
		MemberDTO signInMember = memberService.signIn(member);
		
		if(signInMember == null) {
			mv.addObject("message", "로그인 실패!").setViewName("include/error-page");
			
			return mv;
		}
		
		session.setAttribute("signInMember", signInMember);
		
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	@GetMapping("signOut")
	public String signOut(HttpSession session) {
		session.removeAttribute("signInMember");
		
		return "redirect:/";
	}
	
	@GetMapping("toMain")
	public String toMain() {
		return "redirect:/";
	}
	
	@GetMapping("signUp-form")
	public String toSignUpForm() {
		return "member/signup-form";
	}
	
	@PostMapping("signUp")
	public String signUp(MemberDTO member, HttpServletRequest request) {
		memberService.signUp(member);
		
		return "main_page";
	}
	
	@GetMapping("mypage")
	public String toMyPage() {
		return "member/my_page";
	}
	
	@PostMapping("update-member")
	public String updateMember(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		// log.info("사용자가 입력한 값: {}", member);
		
		// 2. 이번에 실행할 SQL문을 고려하여 설계
		memberService.updateMember(member, session);
		
		return "redirect:mypage";
	}
	
	@PostMapping("delete-member")
	public String deleteMember(MemberDTO member, HttpSession session) {
		// log.info("사용자가 입력한 값: {}", member);
		memberService.deleteMember(member, session);
		
		return "redirect:/";
	}
	
}
