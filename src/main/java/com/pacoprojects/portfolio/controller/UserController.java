package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.*;
import com.pacoprojects.portfolio.projection.*;
import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import com.pacoprojects.portfolio.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("skill")
    public ResponseEntity<List<SkillProjection>> listSkillsByUserNickname(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.listSkillsByUserNickname(nickname));
    }

    @GetMapping("project")
    public ResponseEntity<List<ProjectBasic>> listProjectsByUserNickname(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.listProjectsByUserNickname(nickname));
    }

    @GetMapping("course")
    public ResponseEntity<List<CourseProjection>> listCoursesByUserNickname(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.listCoursesByUserNickname(nickname));
    }

    @GetMapping("certificate")
    public ResponseEntity<List<CertificateProjection>> listCertificatesByUserNickname(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.listCertificatesByUserNickname(nickname));
    }

    @GetMapping("project/status")
    public ResponseEntity<List<ProjectStatus>> listProjectsStatus() {
        return ResponseEntity.ok(userService.listProjectsStatus());
    }

    @GetMapping("user")
    public ResponseEntity<List<UserApplicationBasicSearch>> listUsersDataBasicSearch(@RequestParam @NotBlank String nickOrName) {
        return ResponseEntity.ok(userService.listUsersDataBasicSearch(nickOrName));
    }

    @GetMapping("project/{id}")
    public ResponseEntity<ProjectProjection> findProjectById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.findProjectById(id));
    }

    @GetMapping("course/{id}")
    public ResponseEntity<CourseProjection> findCourseById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.findCourseById(id));
    }

    @GetMapping("bio")
    public ResponseEntity<BioProjection> findBioByUsername(@RequestHeader("Authorization") @NotBlank String token) {
        return ResponseEntity.ok(userService.findBioByUsername(token));
    }

    @GetMapping("resume")
    public ResponseEntity<ResumeDto> findResumeByUsername(@RequestHeader("Authorization") @NotBlank String token) {
        return ResponseEntity.ok(userService.findResumeByUsername(token));
    }

    @GetMapping()
    public ResponseEntity<UserApplicationDto> getUserData(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.getUserData(nickname));
    }

    @GetMapping("bio-social")
    public ResponseEntity<UserApplicationBioSocialProjection> getUserDataBioSocial(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.getUserDataBioSocial(nickname));
    }

    @PostMapping("register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid @NotNull RegisterUserApplicationRequestDto registerUserApplicationRequestDto) {
        final String LOCATION = "/user/register/";
        return ResponseEntity.created(URI.create(LOCATION + userService.registerUser(registerUserApplicationRequestDto).getId())).build();
    }

    @PostMapping("skill")
    public ResponseEntity<Void> createSkill(@RequestBody @Valid @NotNull SkillDto skillDto,
                                            @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/skill/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createSkill(skillDto, token).id())).build();
    }

    @PostMapping("project")
    public ResponseEntity<Void> createProject(@RequestBody @Valid @NotNull ProjectDto projectDto,
                                              @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/project/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createProject(projectDto, token).id())).build();
    }

    @PostMapping("bio")
    public ResponseEntity<Void> createBio(@RequestBody @Valid @NotNull BioDto bioDto,
                                          @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/bio/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createBio(bioDto, token).id())).build();
    }

    @PostMapping("social")
    public ResponseEntity<Void> createSocial(@RequestBody @Valid @NotNull SocialDto socialDto,
                                             @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/social/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createSocial(socialDto, token).id())).build();
    }

    @PostMapping("course")
    public ResponseEntity<Void> createCourse(@RequestBody @Valid @NotNull CourseDto courseDto,
                                             @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/course/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createCourse(courseDto, token).id())).build();
    }

    @PostMapping("bio-social")
    public ResponseEntity<Void> createBioSocial(@RequestBody @Valid @NotNull BioSocialDto bioSocialDto,
                                                @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/bio-social?nickname=";
        return ResponseEntity.created(URI.create(LOCATION + userService.createBioSocial(bioSocialDto, token).getNickname())).build();
    }

    @PostMapping("certificate")
    public ResponseEntity<Void> createCertificates(@RequestBody @Valid @NotNull Set<CertificateDto> certificateDtoSet,
                                                    @RequestHeader("Authorization") @NotBlank String token) {
        userService.createCertificates(certificateDtoSet, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("resume")
    public ResponseEntity<Void> createResume(@RequestBody @Valid @NotNull MultipartFile file,
                                             @RequestParam String path,
                                             @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/resume/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createResume(file, path, token).id())).build();
    }

    @PutMapping("skill/{id}")
    public ResponseEntity<Void> updateSkill(@PathVariable @NotNull Long id,
                                            @RequestBody @Valid @NotNull SkillDto skillDto) {
        userService.updateSkill(id, skillDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("project/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable @NotNull Long id,
                                              @RequestBody @Valid @NotNull ProjectDto projectDto) {
        userService.updateProject(id, projectDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("bio/{id}")
    public ResponseEntity<Void> updateBio(@PathVariable @NotNull Long id,
                                          @RequestBody @Valid @NotNull BioDto bioDto) {
        userService.updateBio(id, bioDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("social/{id}")
    public ResponseEntity<Void> updateSocial(@PathVariable @NotNull Long id,
                                             @RequestBody @Valid @NotNull SocialDto socialDto) {
        userService.updateSocial(id, socialDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("course")
    public ResponseEntity<Void> saveCourses(@RequestBody @Valid @NotNull Set<CourseDto> coursesDtos,
                                             @RequestHeader("Authorization") @NotBlank String token) {
        userService.saveCourses(coursesDtos, token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("skill/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable @NotNull Long id) {
        userService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("project/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable @NotNull Long id) {
        userService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("bio/{id}")
    public ResponseEntity<Void> deleteBio(@PathVariable @NotNull Long id) {
        userService.deleteBio(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("course/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable @NotNull Long id) {
        userService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("certificate/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable @NotNull Long id) {
        userService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("resume/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable @NotNull Long id) {
        userService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }
}
