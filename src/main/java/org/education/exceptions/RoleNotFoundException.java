package org.education.exceptions;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(String role){
        super("Role " + role + " is empty!", "ROLE_NOT_FOUND");
    }
}
