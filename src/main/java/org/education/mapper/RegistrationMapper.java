package org.education.mapper;

import org.education.dto.RegistrationDTO;
import org.education.dto.UserDTO;

public class RegistrationMapper {

    public static UserDTO toUserDto(RegistrationDTO reg){
        return new UserDTO(
         reg.getUsername(),
         reg.getPassword(),
         reg.getEmail(),
         reg.getFirstName(),
         reg.getLastName(),
         reg.getRole());
    }
}
