package com.manoj.user.repository;

import com.manoj.user.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByUsername(String username);
    List<UserProfile> findByUsernameContainingIgnoreCase(String keyword);
}
