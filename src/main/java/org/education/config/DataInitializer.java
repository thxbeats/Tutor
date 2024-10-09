package org.education.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.education.model.Role;
import org.education.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role(null, "ADMIN", null));
        }
        if (roleRepository.findByName("TEACHER").isEmpty()) {
            roleRepository.save(new Role(null, "TEACHER", null));
        }
        if (roleRepository.findByName("STUDENT").isEmpty()) {
            roleRepository.save(new Role(null, "STUDENT", null));
        }
    }
}
