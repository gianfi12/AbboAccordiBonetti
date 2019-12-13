package com.SafeStreets;

public class RegistrationError extends Exception {
    private String errorMsg;

    public RegistrationError(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }
}
