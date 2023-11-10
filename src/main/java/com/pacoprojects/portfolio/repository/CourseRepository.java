package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.CourseProjection;
import com.pacoprojects.portfolio.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<CourseProjection> findAllByUserApplicationId(Long id);

    Optional<CourseProjection> findCourseById(Long id);
}
