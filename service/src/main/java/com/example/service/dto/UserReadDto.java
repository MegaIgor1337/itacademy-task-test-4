package com.example.service.dto;


public record UserReadDto(PersonalInfo personalInfo,
                          String email,
                          String role) {
}

