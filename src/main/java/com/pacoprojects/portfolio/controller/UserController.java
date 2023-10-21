package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.*;
import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import com.pacoprojects.portfolio.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<List<ProjectProjection>> listProjectsByUserNickname(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.listProjectsByUserNickname(nickname));
    }

    @GetMapping("project/{id}")
    public ResponseEntity<ProjectProjection> findProjectById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.findProjectById(id));
    }

    @GetMapping("bio")
    public ResponseEntity<BioProjection> findBioByUsername(@RequestHeader("Authorization") @NotBlank String token) {
        return ResponseEntity.ok(userService.findBioByUsername(token));
    }

    @GetMapping("project/status")
    public ResponseEntity<List<ProjectStatus>> listProjectsStatus() {
        return ResponseEntity.ok(userService.listProjectsStatus());
    }

    @GetMapping()
    public ResponseEntity<UserApplicationProjection> getUserData(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.getUserData(nickname));
    }

    @GetMapping("bio-social")
    public ResponseEntity<UserApplicationBioSocialProjection> getUserDataBioSocial(@RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok(userService.getUserDataBioSocial(nickname));
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

    @PostMapping("bio-social")
    public ResponseEntity<Void> createBioSocial(@RequestBody @Valid @NotNull BioSocialDto bioSocialDto,
                                                @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/bio-social?nickname=";
        return ResponseEntity.created(URI.create(LOCATION + userService.createBioSocial(bioSocialDto, token).getNickname())).build();
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
}
