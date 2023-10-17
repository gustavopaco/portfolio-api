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

    @GetMapping("owner/skill")
    public ResponseEntity<List<SkillProjection>> listOwnerSkills() {
        return ResponseEntity.ok(userService.listOwnerSkills());
    }

    @GetMapping("owner/project")
    public ResponseEntity<List<ProjectProjection>> listOwnerProjects() {
        return ResponseEntity.ok(userService.listOwnerProjects());
    }

    @GetMapping("owner/project/{id}")
    public ResponseEntity<ProjectProjection> findProjectById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.findProjectById(id));
    }

    @GetMapping("owner/bio")
    public ResponseEntity<BioProjection> findBioByUsername(@RequestHeader("Authorization") @NotBlank String token) {
        return ResponseEntity.ok(userService.findBioByUsername(token));
    }

    @GetMapping("owner/project/status")
    public ResponseEntity<List<ProjectStatus>> listOwnerProjectsStatus() {
        return ResponseEntity.ok(userService.listOwnerProjectsStatus());
    }

    @GetMapping("owner")
    public ResponseEntity<UserApplicationProjectsSkillsProjection> getOwnerData() {
        return ResponseEntity.ok(userService.getOwnerData());
    }

    @PostMapping("owner/skill")
    public ResponseEntity<Void> createSkill(@RequestBody @Valid @NotNull SkillDto skillDto,
                                            @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/skill/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createSkill(skillDto, token).id())).build();
    }

    @PostMapping("owner/project")
    public ResponseEntity<Void> createProject(@RequestBody @Valid @NotNull ProjectDto projectDto,
                                              @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/project/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createProject(projectDto, token).id())).build();
    }

    @PostMapping("owner/bio")
    public ResponseEntity<Void> createBio(@RequestBody @Valid @NotNull BioDto bioDto,
                                          @RequestHeader("Authorization") @NotBlank String token) {
        final String LOCATION = "/user/bio/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createBio(bioDto, token).id())).build();
    }

    @PutMapping("owner/skill/{id}")
    public ResponseEntity<Void> updateSkill(@PathVariable @NotNull Long id,
                                            @RequestBody @Valid @NotNull SkillDto skillDto) {
        userService.updateSkill(id, skillDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("owner/project/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable @NotNull Long id,
                                              @RequestBody @Valid @NotNull ProjectDto projectDto) {
        userService.updateProject(id, projectDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("owner/bio/{id}")
    public ResponseEntity<Void> updateBio(@PathVariable @NotNull Long id,
                                          @RequestBody @Valid @NotNull BioDto bioDto) {
        userService.updateBio(id, bioDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("owner/skill/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable @NotNull Long id) {
        userService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("owner/project/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable @NotNull Long id) {
        userService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("owner/bio/{id}")
    public ResponseEntity<Void> deleteBio(@PathVariable @NotNull Long id) {
        userService.deleteBio(id);
        return ResponseEntity.noContent().build();
    }
}
