package com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions;

public class IncorrectLoginInputException extends Exception {
    public IncorrectLoginInputException() {
        super("Incorrect login input.");
    }
}
