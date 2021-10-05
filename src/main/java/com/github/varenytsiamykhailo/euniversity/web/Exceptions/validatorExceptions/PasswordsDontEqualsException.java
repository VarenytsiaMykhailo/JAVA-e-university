package com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions;

public class PasswordsDontEqualsException extends Exception {
    public PasswordsDontEqualsException() {
        super("Password is not equal password repeat.");
    }
}
