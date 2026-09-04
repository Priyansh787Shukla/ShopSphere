package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
