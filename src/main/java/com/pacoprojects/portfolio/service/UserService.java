package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.dto.*;
import com.pacoprojects.portfolio.exception.RecordNotFoundException;
import com.pacoprojects.portfolio.mapper.ProjectMapper;
import com.pacoprojects.portfolio.mapper.SkillMapper;
import com.pacoprojects.portfolio.model.Project;
import com.pacoprojects.portfolio.model.Skill;
import com.pacoprojects.portfolio.model.UserApplication;
import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import com.pacoprojects.portfolio.repository.ProjectRepository;
import com.pacoprojects.portfolio.repository.SkillRepository;
import com.pacoprojects.portfolio.repository.UserApplicationRepository;
import com.pacoprojects.portfolio.security.jwt.JwtUtilService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserApplicationRepository userApplicationRepository;
    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;
    private final SkillMapper skillMapper;
    private final ProjectMapper projectMapper;
    private final JwtUtilService jwtUtilService;
    @Value("${spring.mail.personal.username}")
    private String ownerUsername;

    public List<SkillProjection> listOwnerSkills() {
        return userApplicationRepository.findByUsername(ownerUsername)
                .map(user -> skillRepository.findAllByUserApplicationId(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public List<ProjectStatus> listOwnerProjectsStatus() {
        return Stream.of(ProjectStatus.values()).toList();
    }

    public List<ProjectProjection> listOwnerProjects() {
        return userApplicationRepository.findByUsername(ownerUsername)
                .map(user -> projectRepository.findAllByUserApplicationId(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public ProjectProjection findProjectById(@NotNull Long id) {
        return projectRepository.findProjectById(id)
                .orElseThrow(() -> new RecordNotFoundException(Messages.PROJECT_NOT_FOUND + id));
    }

    public UserApplicationProjectsSkillsProjection getOwnerData() {
        return userApplicationRepository.findUserApplicationByUsername(ownerUsername)
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public SkillDto createSkill(@NotNull SkillDto skillDto, @NotBlank String token) {
        Skill entity = skillMapper.toEntity(skillDto);
        entity.setUserApplication(validateUser(token));
        return skillMapper.toDto(skillRepository.save(entity));
    }

    public ProjectDto createProject(@NotNull ProjectDto projectDto, @NotBlank String token) {
        Project entity = projectMapper.toEntity(projectDto);
        entity.setUserApplication(validateUser(token));
        return projectMapper.toDto(projectRepository.save(entity));
    }

    private UserApplication validateUser(@NotNull String token) {
        return userApplicationRepository.findByUsername(jwtUtilService.extractUsername(jwtUtilService.getBasicToken(token)))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public void updateSkill(@NotNull Long id, @NotNull SkillDto skillDto) {
        skillRepository.findById(id)
                .ifPresentOrElse(skill -> skillRepository.save(skillMapper.partialUpdate(skillDto, skill)),
                        () -> {throw new RecordNotFoundException(Messages.SKILL_NOT_FOUND);}
                );
    }

    public void updateProject(@NotNull Long id,@NotNull ProjectDto projectDto) {
        projectRepository.findById(projectDto.id())
                .ifPresentOrElse(project -> projectRepository.save(projectMapper.partialUpdate(projectDto, project)),
                        () -> {throw new RecordNotFoundException(Messages.PROJECT_NOT_FOUND + id);}
                );
    }

    public void deleteSkill(@NotNull Long id) {
        skillRepository.findById(id)
                .ifPresentOrElse(skillRepository::delete, () -> {
                    throw new RecordNotFoundException(Messages.SKILL_NOT_FOUND + id);
                });
    }

    public void deleteProject(@NotNull Long id) {
        projectRepository.findById(id)
                .ifPresentOrElse(projectRepository::delete, () -> {
                    throw new RecordNotFoundException(Messages.PROJECT_NOT_FOUND + id);
                });
    }
}
