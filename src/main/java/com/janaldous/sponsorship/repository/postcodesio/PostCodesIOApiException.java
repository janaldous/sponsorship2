package com.janaldous.sponsorship.repository.postcodesio;

public class PostCodesIOApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostCodesIOApiException(Throwable t) {
		super(t);
	}
	
	public PostCodesIOApiException(String message) {
		super(message);
	}
}
