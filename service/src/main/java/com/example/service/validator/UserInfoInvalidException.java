package com.example.service.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class UserInfoInvalidException extends RuntimeException {
    private BindingResult bindingResult;
}
