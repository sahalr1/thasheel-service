package com.thasheel.service;

public class MobileNumberAlreadyusedException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

    public MobileNumberAlreadyusedException() {
        super("Mobile phones is already in use!");
    }

}
