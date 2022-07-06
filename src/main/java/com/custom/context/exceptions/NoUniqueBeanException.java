package com.custom.context.exceptions;

public class NoUniqueBeanException extends Exception {

    private static final String MESSAGE_TEMPLATE = "More than one bean of type: %s found";

    public NoUniqueBeanException(String message) {
        super(String.format(MESSAGE_TEMPLATE, message));
    }
}
