package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.CourseDto;
import com.pacoprojects.portfolio.model.Course;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    Course toEntity(CourseDto courseDto);

    CourseDto toDto(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course partialUpdate(CourseDto courseDto, @MappingTarget Course course);
}
