package com.repository;

import com.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findUserAppByUsername(String username);

    Optional<UserApp> findUserAppByEmail(String email);

    Optional<UserApp> findUserAppById(Long id);
}
