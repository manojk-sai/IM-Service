package com.manoj.user.controller;

import com.manoj.user.dto.UserProfileDTO;
import com.manoj.user.model.UserProfile;
import com.manoj.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalController {

    private final UserService userService;

    public InternalController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createUser(@RequestBody UserProfileDTO dto){
        UserProfile user = userService.createUser(
                UserProfile.builder()
                        .userId(dto.getUserId())
                        .username(dto.getUsername())
                        .email(dto.getEmail())
                        .bio(dto.getBio())
                        .avatarUrl(dto.getAvatarUrl())
                        .build()
        );
        return ResponseEntity.ok(toDTO(user));
    }

    private UserProfileDTO toDTO(UserProfile user) {
        return new UserProfileDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getAvatarUrl()
        );
    }
}
