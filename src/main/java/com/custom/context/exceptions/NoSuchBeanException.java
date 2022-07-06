package com.custom.context.exceptions;

public class NoSuchBeanException extends Exception {
    private static final String MESSAGE_TEMPLATE = "Bean with type: %s doesn't exist";

    public NoSuchBeanException(String message) {
        super(String.format(MESSAGE_TEMPLATE, message));
    }
}
