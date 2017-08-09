package com.sadabasi.diary.config;

/**
 * An i18n friendly exception. message will be i18n key.
 * 
 * @author erdal.bitik
 * */
public class DiaryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DiaryException(String message) {
		super(message);
	}

}
