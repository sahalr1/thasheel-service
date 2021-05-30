package com.thasheel.service;

public class CustomerAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerAlreadyUsedException() {
        super("Customer Login name already used!");
    }

}
