package com.lasertagnation.yachtmodule.controller;

import com.lasertagnation.model.User;
import com.lasertagnation.yachtmodule.service.YachtModuleUserQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Raw {@link User} payloads for debugging: expect {@code LazyInitializationException}, {@code StackOverflowError}
 * from JSON cycles ({@code user ↔ bookings ↔ user}), and very noisy SQL from lazy loading + {@code EAGER} roles.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/yacht-module/users")
public class YachtModuleUserLabController {

    private final YachtModuleUserQueryService yachtModuleUserQueryService;

    public YachtModuleUserLabController(YachtModuleUserQueryService yachtModuleUserQueryService) {
        this.yachtModuleUserQueryService = yachtModuleUserQueryService;
    }

    @GetMapping
    public List<User> getAllUsersRaw() {
        return yachtModuleUserQueryService.findAllUsersAsEntities();
    }

    @GetMapping("/paged")
    public Page<User> getAllUsersPaged(Pageable pageable) {
        return yachtModuleUserQueryService.findAllUsersPaged(pageable);
    }
}
