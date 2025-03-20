package com.kh.spring.exception;

public class NotSignInException extends RuntimeException {
	public NotSignInException(String msg) {
		super(msg);
	}
}
