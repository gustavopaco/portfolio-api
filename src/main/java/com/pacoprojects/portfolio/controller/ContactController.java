package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.ContactRequest;
import com.pacoprojects.portfolio.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public void sendEmail(@RequestBody @Valid @NotNull ContactRequest contactRequest) {
        contactService.sendEmail(contactRequest);
    }
}
