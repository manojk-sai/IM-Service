package com.manoj.chat.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateChatRequest {

    private String name;

    private String type;

    private List<String> members;

}