package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.SkillDto;
import com.pacoprojects.portfolio.dto.SkillProjection;
import com.pacoprojects.portfolio.dto.UserApplicationProjectsSkillsProjection;
import com.pacoprojects.portfolio.service.UserService;
import jakarta.validation.Valid;
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

    @GetMapping("owner")
    public ResponseEntity<UserApplicationProjectsSkillsProjection> getOwnerData() {
        return ResponseEntity.ok(userService.getOwnerData());
    }

    @PostMapping("owner/skill")
    public ResponseEntity<Void> createSkill(@RequestBody @Valid @NotNull SkillDto skillDto) {
        final String LOCATION = "/user/skill/";
        return ResponseEntity.created(URI.create(LOCATION + userService.createSkill(skillDto).id())).build();
    }

    @PutMapping("owner/skill")
    public ResponseEntity<Void> updateSkill(@RequestBody @Valid @NotNull SkillDto skillDto) {
        userService.updateSkill(skillDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("owner/skill/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable @NotNull Long id) {
        userService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
