package com.kh.spring.exception.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.HTMLManipulationException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.NotSignInException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.UpdateOneRowFailException;
import com.kh.spring.exception.PasswordMissMatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("message", e.getMessage()).setViewName("include/error-page");
		
		log.info("발생한 예외: {}", errorMsg, e);
		
		return mv;
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalidParameterError(InvalidParameterException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	protected ModelAndView MemberNotFoundError(MemberNotFoundException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	
	@ExceptionHandler(PasswordMissMatchException.class)
	protected ModelAndView PasswordMissMatchError(PasswordMissMatchException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(UpdateOneRowFailException.class)
	protected ModelAndView UpdateOneRowFailError(UpdateOneRowFailException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(HTMLManipulationException.class)
	protected ModelAndView HTMLManipulationError(HTMLManipulationException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(NotSignInException.class)
	protected ModelAndView NotSignInError(NotSignInException e) {
		return createErrorResponse(e.getMessage(), e);
	}
}
