package com.example.service.validator;

import com.example.database.repository.UserRepository;
import com.example.service.dto.UserCreateDto;
import com.example.service.util.ConstContainer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateDto> {
    private final UserRepository userRepository;


    @Override
    public boolean isValid(UserCreateDto userCreateDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        List<Boolean> validations = new ArrayList<>();
        if (userRepository.findAllByEmail(userCreateDto.getEmail()).isPresent()) {
            validations.add(false);
        }
        var pattern = Pattern.compile(ConstContainer.REGEX);
        var nameMatcher = pattern.matcher(userCreateDto.getName());
        if (nameMatcher.find()) {
            validations.add(false);
        }
        var surnameMatcher = pattern.matcher(userCreateDto.getSurname());
        if (surnameMatcher.find()) {
            validations.add(false);
        }
        var patronymic = pattern.matcher(userCreateDto.getPatronymic());
        if (patronymic.find()) {
            validations.add(false);
        }
        return !validations.contains(false);
    }
}
