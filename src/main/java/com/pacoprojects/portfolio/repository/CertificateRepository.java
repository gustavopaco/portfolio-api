package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.projection.CertificateProjection;
import com.pacoprojects.portfolio.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<CertificateProjection> findAllByUserApplicationId(Long id);
}
