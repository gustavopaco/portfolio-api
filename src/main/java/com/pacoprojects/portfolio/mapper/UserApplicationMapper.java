package com.pacoprojects.portfolio.mapper;

import com.pacoprojects.portfolio.dto.RegisterUserApplicationRequestDto;
import com.pacoprojects.portfolio.dto.UserApplicationDto;
import com.pacoprojects.portfolio.projection.UserApplicationProjection;
import com.pacoprojects.portfolio.model.Bio;
import com.pacoprojects.portfolio.model.Social;
import com.pacoprojects.portfolio.model.UserApplication;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {SkillMapper.class, ProjectMapper.class, BioMapper.class, SocialMapper.class})
public interface UserApplicationMapper {
    UserApplication toEntity(UserApplicationDto userApplicationDto);

    UserApplication toEntity(UserApplicationProjection userApplicationProjection);

    UserApplication toEntity(RegisterUserApplicationRequestDto registerUserApplicationRequestDto);

    @AfterMapping
    default void linkSkills(@MappingTarget UserApplication userApplication) {
        userApplication.getSkills().forEach(skill -> skill.setUserApplication(userApplication));
    }

    @AfterMapping
    default void linkProjects(@MappingTarget UserApplication userApplication) {
        userApplication.getProjects().forEach(project -> project.setUserApplication(userApplication));
    }

    @AfterMapping
    default void linkBio(@MappingTarget UserApplication userApplication) {
        Bio bio = userApplication.getBio();
        if (bio != null) {
            bio.setUserApplication(userApplication);
        }
    }

    @AfterMapping
    default void linkSocial(@MappingTarget UserApplication userApplication) {
        Social social = userApplication.getSocial();
        if (social != null) {
            social.setUserApplication(userApplication);
        }
    }

    UserApplicationDto toDto(UserApplication userApplication);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserApplication partialUpdate(UserApplicationDto userApplicationDto, @MappingTarget UserApplication userApplication);
}
