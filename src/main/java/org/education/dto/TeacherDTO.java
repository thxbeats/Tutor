package org.education.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherDTO {
    private Long id;

    @Setter
    private String firstName;

    @Setter
    private String secondName;

    @Setter
    private String email;
}
