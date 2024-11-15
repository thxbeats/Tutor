package org.education.exceptions;

public class EmailAlreadyInUseException extends BaseException {
    public EmailAlreadyInUseException(String email){
        super("Email " + email + " is already in use.", "EMAIL_ALREADY_IN_USE");
    }
}
