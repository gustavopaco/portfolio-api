package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.dto.*;
import com.pacoprojects.portfolio.email.EmailMessage;
import com.pacoprojects.portfolio.email.EmailObject;
import com.pacoprojects.portfolio.email.EmailService;
import com.pacoprojects.portfolio.exception.RecordNotFoundException;
import com.pacoprojects.portfolio.mapper.*;
import com.pacoprojects.portfolio.model.*;
import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import com.pacoprojects.portfolio.projection.*;
import com.pacoprojects.portfolio.repository.*;
import com.pacoprojects.portfolio.security.jwt.JwtUtilService;
import com.pacoprojects.portfolio.util.RandomPasswordGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserApplicationRepository userApplicationRepository;
    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;
    private final BioRepository bioRepository;
    private final SocialRepository socialRepository;
    private final CertificateRepository certificateRepository;
    private final CurriculumRepository curriculumRepository;
    private final SkillMapper skillMapper;
    private final ProjectMapper projectMapper;
    private final BioMapper bioMapper;
    private final SocialMapper socialMapper;
    private final CourseMapper courseMapper;
    private final CertificateMapper certificateMapper;
    private final CurriculumMapper curriculumMapper;
    private final JwtUtilService jwtUtilService;
    private final CourseRepository courseRepository;
    private final UserApplicationMapper userApplicationMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public List<SkillProjection> listSkillsByUserNickname(@NotBlank String nickname) {
        return userApplicationRepository.findByNickname(nickname)
                .map(user -> skillRepository.findAllByUserApplicationId(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public List<ProjectStatus> listProjectsStatus() {
        return Stream.of(ProjectStatus.values()).toList();
    }

    public List<ProjectBasic> listProjectsByUserNickname(@NotBlank String nickname) {
        return userApplicationRepository.findByNickname(nickname)
                .map(user -> projectRepository.findAllByUserApplicationId(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public List<CourseProjection> listCoursesByUserNickname(@NotBlank String nickname) {
        return userApplicationRepository.findByNickname(nickname)
                .map(user -> courseRepository.findAllByUserApplicationIdOrderByEndDateDesc(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public List<CertificateProjection> listCertificatesByUserNickname(@NotBlank String nickname) {
        return userApplicationRepository.findByNickname(nickname)
                .map(user -> certificateRepository.findAllByUserApplicationId(user.getId()))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public List<UserApplicationBasicSearch> listUsersDataBasicSearch(@NotBlank String nickOrName) {
        return userApplicationRepository.findAllByContainingNicknameOrFullName(nickOrName);
    }

    public ProjectProjection findProjectById(@NotNull Long id) {
        return projectRepository.findProjectById(id)
                .orElseThrow(() -> new RecordNotFoundException(Messages.PROJECT_NOT_FOUND + id));
    }

    public CourseProjection findCourseById(@NotNull Long id) {
        return courseRepository.findCourseById(id)
                .orElseThrow(() -> new RecordNotFoundException(Messages.COURSE_NOT_FOUND + id));
    }

    public BioProjection findBioByUsername(@NotBlank String token) {
        return bioRepository.findBioByUserApplicationUsername(validateUser(token).getUsername())
                .orElse(null);
    }

    public UserApplicationDto getUserData(@NotBlank String nickname) {
        UserApplication userApplication = userApplicationRepository.findUserApplicationByNickname(nickname)
                .map(userApplicationMapper::toEntity)
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));

        userApplication.setSkills(userApplication.getSkills().stream()
                .sorted(Comparator.comparing(Skill::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        userApplication.setCourses(userApplication.getCourses().stream()
                .sorted(Comparator.comparing(Course::getEndDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        return userApplicationMapper.toDto(userApplication);
    }

    public UserApplicationBioSocialProjection getUserDataBioSocial(@NotBlank String nickname) {
        return userApplicationRepository.findUserApplicationBioSocialProjectionByNickname(nickname)
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    public CurriculumDto findCurriculumByUsername(@NotBlank String token) {
        return curriculumRepository.findCurriculumByUserApplicationUsername(validateUser(token).getUsername())
                .map(curriculumMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException(Messages.CURRICULUM_NOT_FOUND));
    }

    public UserApplication registerUser(@NotNull RegisterUserApplicationRequestDto registerUserApplicationRequestDto) {
        UserApplication userApplication = userApplicationMapper.toEntity(registerUserApplicationRequestDto);
        userApplication.setPassword(passwordEncoder.encode(RandomPasswordGenerator.generatePassword()));
        userApplication = userApplicationRepository.save(userApplication);
        emailService.sendMailWithAttachment(EmailObject.builder()
                .recipient(userApplication.getUsername())
                .subject(Messages.THANK_YOU_FOR_REGISTERING)
                .message(EmailMessage.buildRegistrationMessage(userApplication.getUsername(), userApplication.getPassword()))
                .build());
        return userApplication;
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

    public BioDto createBio(@NotNull BioDto bioDto, @NotBlank String token) {
        Bio entity = bioMapper.toEntity(bioDto);
        entity.setUserApplication(validateUser(token));
        return bioMapper.toDto(bioRepository.save(entity));
    }

    public SocialDto createSocial(@NotNull SocialDto socialDto, @NotBlank String token) {
        Social entity = socialMapper.toEntity(socialDto);
        entity.setUserApplication(validateUser(token));
        return socialMapper.toDto(socialRepository.save(entity));
    }

    public UserApplication createBioSocial(BioSocialDto bioSocialDto, String token) {
        if (bioSocialDto.bio().id() != null) {
            updateBio(bioSocialDto.bio().id(), bioSocialDto.bio());
        } else {
            createBio(bioSocialDto.bio(), token);
        }
        if (bioSocialDto.social().id() != null) {
            updateSocial(bioSocialDto.social().id(), bioSocialDto.social());
        } else if (validateSocialValuesNotBlank(bioSocialDto.social())) {
            createSocial(bioSocialDto.social(), token);
        }
        return validateUser(token);
    }

    public CourseDto createCourse(@NotNull CourseDto courseDto, @NotBlank String token) {
        Course entity = courseMapper.toEntity(courseDto);
        entity.setUserApplication(validateUser(token));
        return courseMapper.toDto(courseRepository.save(entity));
    }

    public void createCertificates(@NotNull Set<CertificateDto> certificateDtoSet, @NotBlank String token) {
        UserApplication userApplication = validateUser(token);
        Set<Certificate> certificates = new HashSet<>();
        certificateDtoSet.stream()
                .map(certificateMapper::toEntity)
                .forEach(certificate -> {
                    certificate.setUserApplication(userApplication);
                    certificates.add(certificate);
                });
        certificateRepository.saveAll(certificates);
    }

    public CurriculumDto createCurriculum(@NotNull CurriculumDto curriculumDto, @NotBlank String token) {
        Curriculum curriculum = curriculumMapper.toEntity(curriculumDto);
        curriculum.setUserApplication(validateUser(token));
        return curriculumMapper.toDto(curriculumRepository.save(curriculum));
    }

    public void updateSkill(@NotNull Long id, @NotNull SkillDto skillDto) {
        skillRepository.findById(id)
                .ifPresentOrElse(skill -> skillRepository.save(skillMapper.partialUpdate(skillDto, skill)),
                        () -> {throw new RecordNotFoundException(Messages.SKILL_NOT_FOUND);}
                );
    }

    public void updateProject(@NotNull Long id, @NotNull ProjectDto projectDto) {
        projectRepository.findById(projectDto.id())
                .ifPresentOrElse(project -> projectRepository.save(projectMapper.partialUpdate(projectDto, project)),
                        () -> {throw new RecordNotFoundException(Messages.PROJECT_NOT_FOUND + id);}
                );
    }

    public void updateBio(@NotNull Long id, @NotNull BioDto bioDto) {
        bioRepository.findById(id)
                .ifPresentOrElse(bio -> bioRepository.save(bioMapper.partialUpdate(bioDto, bio)),
                        () -> {throw new RecordNotFoundException(Messages.BIO_NOT_FOUND + id);}
                );
    }

    public void updateSocial(@NotNull Long id, @NotNull SocialDto socialDto) {
        socialRepository.findById(id)
                .ifPresentOrElse(social -> socialRepository.save(socialMapper.partialUpdate(socialDto, social)),
                        () -> {throw new RecordNotFoundException(Messages.SOCIAL_NOT_FOUND + id);}
                );
    }

    public void saveCourses(@NotNull Set<CourseDto> coursesDtos, @NotBlank String token) {
        UserApplication userApplication = validateUser(token);
        Set<Course> courses = new HashSet<>();
        coursesDtos.stream()
                .map(courseMapper::toEntity)
                .forEach(course -> {
                    course.setUserApplication(userApplication);
                    courses.add(course);
                });
        courseRepository.saveAll(courses);
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

    public void deleteBio(@NotNull Long id) {
        bioRepository.findById(id)
                .ifPresentOrElse(bioRepository::delete, () -> {
                    throw new RecordNotFoundException(Messages.BIO_NOT_FOUND + id);
                });
    }

    public void deleteCourse(@NotNull Long id) {
        courseRepository.findById(id)
                .ifPresentOrElse(courseRepository::delete,
                        () -> {throw new RecordNotFoundException(Messages.COURSE_NOT_FOUND + id);}
                );
    }

    public void deleteCertificate(@NotNull Long id) {
        certificateRepository.findById(id)
                .ifPresentOrElse(certificateRepository::delete,
                        () -> {throw new RecordNotFoundException(Messages.CERTIFICATE_NOT_FOUND + id);}
                );
    }

    public void deleteCurriculum(@NotNull Long id) {
        curriculumRepository.findById(id)
                .ifPresentOrElse(curriculumRepository::delete,
                        () -> {throw new RecordNotFoundException(Messages.CURRICULUM_NOT_FOUND + id);}
                );
    }

    private UserApplication validateUser(@NotNull String token) {
        return userApplicationRepository.findByUsername(jwtUtilService.extractUsername(jwtUtilService.getBasicToken(token)))
                .orElseThrow(() -> new RecordNotFoundException(Messages.USER_NOT_FOUND));
    }

    private boolean validateSocialValuesNotBlank(SocialDto socialDto) {
        return (socialDto.github() != null && !socialDto.github().isBlank()) ||
                (socialDto.facebook() != null && !socialDto.facebook().isBlank()) ||
                (socialDto.linkedin() != null && !socialDto.linkedin().isBlank()) ||
                (socialDto.twitter() != null && !socialDto.twitter().isBlank()) ||
                (socialDto.youtube() != null && !socialDto.youtube().isBlank()) ||
                (socialDto.instagram() != null && !socialDto.instagram().isBlank());
    }
}
