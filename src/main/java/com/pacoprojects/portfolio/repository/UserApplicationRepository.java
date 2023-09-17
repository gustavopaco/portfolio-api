package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    Optional<UserApplication> findByUsername(String username);
}
