package com.gestion.eventos.api.repository;

import com.gestion.eventos.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // find , by , Username  lo interpreta JPA, no se puede poner cualquier nombre
    Optional<User> findByUsername (String username);
}
