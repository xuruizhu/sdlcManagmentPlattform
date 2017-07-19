package com.cx.demo.nonsecure.commons;

public class CxDemoException extends Exception {

	private static final long serialVersionUID = 7890L;

	public CxDemoException() {
		super();
	}

	public CxDemoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CxDemoException(String message) {
		super(message);
	}

	public CxDemoException(Throwable cause) {
		super(cause);
	}
	
}
