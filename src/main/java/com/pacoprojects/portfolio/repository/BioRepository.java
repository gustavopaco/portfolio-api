package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.projection.BioProjection;
import com.pacoprojects.portfolio.model.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BioRepository extends JpaRepository<Bio, Long> {

    Optional<BioProjection> findBioById(Long id);
    Optional<BioProjection> findBioByUserApplicationUsername(String username);
}
