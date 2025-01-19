package com.Shop.repo;

import com.Shop.entity.AppUser;
import com.Shop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser>  findByEmail(String email);

    AppUser findByRole(Role role);
}
