package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.ResumeDto;
import com.pacoprojects.portfolio.model.Resume;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResumeMapper {
    Resume toEntity(ResumeDto resumeDto);

    ResumeDto toDto(Resume resume);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Resume partialUpdate(ResumeDto resumeDto, @MappingTarget Resume resume);
}
