package com.gsdc.server.repository;

import com.gsdc.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKey(String key);
    Optional<User> findByEmailAndUsername(String email, String username);

    void deleteByKey(String key);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
