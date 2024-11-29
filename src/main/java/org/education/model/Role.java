package org.education.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "roles", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Добавим метод для удобного отображения имени роли
    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
    public String formatRoles(Set<Role> roles, MessageSource messageSource, Locale locale) {
        return roles.stream()
                .map(role -> messageSource.getMessage("role." + role.getName(), null, locale)) // Перевод роли
                .collect(Collectors.joining(", ")); // Соединяем роли через запятую
    }
}
