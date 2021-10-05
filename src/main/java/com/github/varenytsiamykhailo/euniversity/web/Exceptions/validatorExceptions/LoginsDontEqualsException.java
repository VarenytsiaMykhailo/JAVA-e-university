package com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions;

public class LoginsDontEqualsException extends Exception {
    public LoginsDontEqualsException() {
        super("Login is not equal login repeat.");
    }
}
