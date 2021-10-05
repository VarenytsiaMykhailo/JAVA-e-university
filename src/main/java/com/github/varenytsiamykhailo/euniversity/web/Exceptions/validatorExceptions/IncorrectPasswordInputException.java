package com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions;

public class IncorrectPasswordInputException extends Exception{
    public IncorrectPasswordInputException() {
        super("Incorrect password input.");
    }
}
