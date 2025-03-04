package com.basigo.project.persistence.user.repositories;

import com.basigo.project.persistence.user.entities.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u  " +
            "WHERE " +
            "(:id IS NULL OR u.id = :id) AND " +
            "(:email IS NULL OR u.email = :email)"
    )
    Page<User> findAllByFilters(@Param("profileId") Long id,
                                @Param("email") String email,
                                Pageable pageable);
}
