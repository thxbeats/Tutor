
package org.education.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Getter
@Setter
public class RegistrationDTO {
    @NotBlank(message = "{field.required}")
    @Email(message = "{email.invalid}")
    private String email;

    @NotBlank(message = "{field.required}")
    @Size(min = 8, max = 20, message = "{password.size}")
    private String password;

    @NotBlank(message = "{field.required}")
    private String confirmPassword;

    @NotBlank(message = "{field.required}")
    private String firstName;

    @NotBlank(message = "{field.required}")
    private String lastName;

    //private String middleName;

    @NotNull(message = "{field.required}")
    private String role;

   //@Min(value = 18, message = "{age.min}")
   // @Max(value = 99, message = "{age.max}")
   // private int age;

    private String username;

}
