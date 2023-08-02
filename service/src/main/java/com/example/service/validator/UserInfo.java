package com.example.service.validator;

import com.example.service.util.ConstContainer;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UserInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfo {
    String message() default ConstContainer.INVALID_USER_INFO;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
