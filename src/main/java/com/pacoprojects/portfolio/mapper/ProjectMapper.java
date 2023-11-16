package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.ProjectDto;
import com.pacoprojects.portfolio.model.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);

    ProjectDto toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(ProjectDto projectDto, @MappingTarget Project project);
}
