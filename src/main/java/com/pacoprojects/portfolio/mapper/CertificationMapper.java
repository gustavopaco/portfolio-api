package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.CertificationDto;
import com.pacoprojects.portfolio.model.Certification;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificationMapper {
    Certification toEntity(CertificationDto certificationDto);

    CertificationDto toDto(Certification certification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Certification partialUpdate(CertificationDto certificationDto, @MappingTarget Certification certification);
}
