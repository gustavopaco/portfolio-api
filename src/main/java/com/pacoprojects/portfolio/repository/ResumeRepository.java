package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findResumeByUserApplicationUsername(String username);
}
