package com.kh.spring.exception;

public class UpdateOneRowFailException extends RuntimeException {
	public UpdateOneRowFailException(String msg) {
		super(msg);
	}
}
