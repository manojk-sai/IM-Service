package com.manoj.user.controller;

import com.manoj.user.dto.UpdateUserProfileDTO;
import com.manoj.user.dto.UserProfileDTO;
import com.manoj.user.model.UserProfile;
import com.manoj.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserProfileDTO> getUser(@RequestHeader("X-User-Username") String username) {
        UserProfile user = userService.getUserProfile(username);
        return ResponseEntity.ok(toDTO(user));
    }

    @PutMapping
    public ResponseEntity<UserProfileDTO> updateUser(
            @RequestHeader("X-User-Username") String username,
            @RequestBody UpdateUserProfileDTO dto) {
        UserProfile user = userService.updateUserProfile(username, dto);
        return ResponseEntity.ok(toDTO(user));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDTO>> searchUsers(@RequestParam String keyword) {
        List<UserProfile> users = userService.searchUsers(keyword);
        List<UserProfileDTO> dtos = users.stream().map(this::toDTO).toList();
        return ResponseEntity.ok(dtos);
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