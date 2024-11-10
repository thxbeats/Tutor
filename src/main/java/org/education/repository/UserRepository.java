package org.education.repository;

import org.education.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM auth.user_roles WHERE user_id IN :ids", nativeQuery = true)
    void deleteUserRolesByUserIds(@Param("ids") List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM auth.users WHERE id IN :ids", nativeQuery = true)
    void deleteUsersByIds(@Param("ids") List<Long> ids);
}
