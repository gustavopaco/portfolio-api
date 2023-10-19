package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.SocialDto;
import com.pacoprojects.portfolio.model.Social;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SocialMapper {
    Social toEntity(SocialDto socialDto);

    SocialDto toDto(Social social);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Social partialUpdate(SocialDto socialDto, @MappingTarget Social social);
}
