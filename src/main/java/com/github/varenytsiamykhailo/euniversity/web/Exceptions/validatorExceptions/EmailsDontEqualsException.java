package com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions;

public class EmailsDontEqualsException extends Exception {
    public EmailsDontEqualsException() {
        super("Email is not equal email repeat.");
    }
}
