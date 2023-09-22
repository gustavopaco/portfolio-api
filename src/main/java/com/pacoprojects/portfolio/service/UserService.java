package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.dto.SkillDto;
import com.pacoprojects.portfolio.dto.SkillProjection;
import com.pacoprojects.portfolio.dto.UserApplicationProjectsSkillsProjection;
import com.pacoprojects.portfolio.exception.RecordNotFoundException;
import com.pacoprojects.portfolio.mapper.SkillMapper;
import com.pacoprojects.portfolio.repository.SkillRepository;
import com.pacoprojects.portfolio.repository.UserApplicationRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserApplicationRepository userApplicationRepository;
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    @Value("${spring.mail.personal.username}")
    private static String ownerUsername;

    public List<SkillProjection> listSkillsByUser(@NotNull Long id) {
        return userApplicationRepository.findById(id)
                .map(user -> skillRepository.findAllByUserApplicationId(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public UserApplicationProjectsSkillsProjection getOwnerData() {
        return userApplicationRepository.findUserApplicationByUsername(ownerUsername)
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public SkillDto createSkill(@NotNull SkillDto skillDto) {
        validateUser(skillDto.userApplication().id());
        return skillMapper.toDto(skillRepository.save(skillMapper.toEntity(skillDto)));
    }

    private void validateUser(@NotNull Long id) {
        userApplicationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public void updateSkill(@NotNull SkillDto skillDto) {
        skillRepository.findById(skillDto.id())
                .ifPresentOrElse(skill -> skillRepository.save(skillMapper.partialUpdate(skillDto, skill)),
                        () -> {throw new RecordNotFoundException(Messages.SKILL_NOT_FOUND);}
                );
    }

    public void deleteSkill(@NotNull Long id) {
        skillRepository.findById(id)
                .ifPresentOrElse(skillRepository::delete, () -> {
                    throw new RecordNotFoundException(Messages.SKILL_NOT_FOUND);
                });
    }
}
