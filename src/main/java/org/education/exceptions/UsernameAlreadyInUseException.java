package org.education.exceptions;

public class UsernameAlreadyInUseException extends BaseException {
    public UsernameAlreadyInUseException(String username){
        super("Username " + username + " is already in use.", "USERNAME_ALREADY_IN_USE");
    }
}
