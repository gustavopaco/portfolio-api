package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.CurriculumDto;
import com.pacoprojects.portfolio.model.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    Optional<Curriculum> findCurriculumByUserApplicationUsername(String username);
}
