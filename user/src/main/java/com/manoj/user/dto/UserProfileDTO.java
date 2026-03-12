package com.manoj.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private String userId;

    private String username;

    private String email;

    private String bio;

    private String avatarUrl;
}