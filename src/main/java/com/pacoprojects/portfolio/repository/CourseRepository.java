package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.projection.CourseProjection;
import com.pacoprojects.portfolio.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<CourseProjection> findAllByUserApplicationIdOrderByEndDateDesc(Long id);

    @Query("""
        SELECT c from Course c
        JOIN FETCH c.userApplication u
        WHERE u.nickname = ?1
        ORDER BY c.endDate DESC
    """)
    Set<Course> findAllByUserApplicationNickname(String nickname);

    Optional<CourseProjection> findCourseById(Long id);
}
