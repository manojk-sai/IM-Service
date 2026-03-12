package com.manoj.user.service;

import com.manoj.user.dto.UpdateUserProfileDTO;
import com.manoj.user.model.UserProfile;
import com.manoj.user.repository.UserProfileRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserProfileRepository repository;

    public UserService(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "users", key = "#username")
    public UserProfile getUserProfile(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @CachePut(value = "users", key = "#username")
    public UserProfile updateUserProfile(String username, UpdateUserProfileDTO dto) {
        UserProfile user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBio(dto.getBio());
        user.setAvatarUrl(dto.getAvatarUrl());
        return repository.save(user);
    }

    public List<UserProfile> searchUsers(String keyword) {
        return repository.findByUsernameContainingIgnoreCase(keyword);
    }

    public UserProfile createUser(UserProfile userProfile) {
        return repository.save(userProfile);
    }
}