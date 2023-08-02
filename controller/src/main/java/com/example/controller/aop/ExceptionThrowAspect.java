package com.example.controller.aop;

import com.example.service.dto.UserCreateDto;
import com.example.service.validator.UserInfoInvalidException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ExceptionThrowAspect {
    @Before("execution(* com.example.controller.rest.UserRestController.addUser(..))" +
            "&& args(userCreateDto, bindingResult)")
    public void checkBindingResultAndThrowException(JoinPoint joinPoint, UserCreateDto userCreateDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserInfoInvalidException(bindingResult);
        }
    }
}
