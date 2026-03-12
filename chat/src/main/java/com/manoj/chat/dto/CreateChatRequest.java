package com.manoj.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateChatRequest {
    @NotBlank(message = "Chat name is required")
    private String name;
    @NotBlank(message = "Chat type is required")
    @Pattern(regexp = "PRIVATE|GROUP", message = "Chat type must be either PRIVATE or GROUP")
    private String type;
    @NotNull(message = "Members list is required")
    @Size(min = 1, message = "At least one member is required")
    private List<String> members;
}