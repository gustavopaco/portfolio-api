package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.model.TokenConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long> {
}
