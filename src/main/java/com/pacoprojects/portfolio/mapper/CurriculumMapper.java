package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.CurriculumDto;
import com.pacoprojects.portfolio.model.Curriculum;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurriculumMapper {
    Curriculum toEntity(CurriculumDto curriculumDto);

    CurriculumDto toDto(Curriculum curriculum);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Curriculum partialUpdate(CurriculumDto curriculumDto, @MappingTarget Curriculum curriculum);
}
