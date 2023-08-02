package com.example.service.dto;


import com.example.database.entity.Role;
import com.example.service.validator.UserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@UserInfo
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private  String patronymic;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private Role role;
}
