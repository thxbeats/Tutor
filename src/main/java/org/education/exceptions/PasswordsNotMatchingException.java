package org.education.exceptions;

public class PasswordsNotMatchingException extends BaseException {
    public PasswordsNotMatchingException(){
        super("Passwords do not match. Please try again","PASSWORD_MUST_MATCH");
    }
}
