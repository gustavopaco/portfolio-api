package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    Optional<UserApplication> findByUsername(String username);
}
