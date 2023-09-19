package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.SkillDto;
import com.pacoprojects.portfolio.model.Skill;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkillMapper {
    Skill toEntity(SkillDto skillDto);

    SkillDto toDto(Skill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Skill partialUpdate(SkillDto skillDto, @MappingTarget Skill skill);
}
