package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.CertificateDto;
import com.pacoprojects.portfolio.model.Certificate;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateMapper {
    Certificate toEntity(CertificateDto certificateDto);

    CertificateDto toDto(Certificate certificate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Certificate partialUpdate(CertificateDto certificateDto, @MappingTarget Certificate certificate);
}
