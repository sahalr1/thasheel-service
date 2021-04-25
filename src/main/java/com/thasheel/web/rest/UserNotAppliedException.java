package com.thasheel.web.rest;

public class UserNotAppliedException extends RuntimeException {

	
	 private static final long serialVersionUID = 1L;

	    public UserNotAppliedException() {
	        super("Customer is Not Applied");
	    }
}
