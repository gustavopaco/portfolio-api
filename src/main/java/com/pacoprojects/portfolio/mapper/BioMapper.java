package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.BioDto;
import com.pacoprojects.portfolio.model.Bio;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BioMapper {
    Bio toEntity(BioDto bioDto);

    BioDto toDto(Bio bio);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Bio partialUpdate(BioDto bioDto, @MappingTarget Bio bio);
}
