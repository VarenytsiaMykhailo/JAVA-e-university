package com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions;

public class IncorrectEmailInputException extends Exception {
    public IncorrectEmailInputException() {
        super("Incorrect email input.");
    }
}
