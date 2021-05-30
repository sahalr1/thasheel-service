package com.thasheel.service;

public class PhoneAlreadyUsedException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

    public PhoneAlreadyUsedException() {
        super("phones is already in use!");
    }

}
