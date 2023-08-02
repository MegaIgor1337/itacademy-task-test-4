package com.example.controller.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class LoggingAspectRestController {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void anyRestController() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void anyExceptionHandlerMethod() {
    }

    @Pointcut("anyRestController() " +
              "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    @Before("anyRestController()")
    public void beforeAddLoggingInRestController(JoinPoint joinPoint) {
        Object controller = joinPoint.getThis();
        log.info("User entered in method - {}, class - {}",
                joinPoint.getSignature().getName(), controller.getClass().getSimpleName());
    }

    @After("anyRestController()")
    public void afterAddLoggingInRestController(JoinPoint joinPoint) {
        Object controller = joinPoint.getThis();
        log.info("User ended method - {}, class - {}",
                joinPoint.getSignature().getName(), controller.getClass().getSimpleName());
    }

    @AfterThrowing(pointcut = "anyRestController()", throwing = "ex")
    public void logExceptionInRestController(JoinPoint joinPoint, Exception ex) {
        Object controller = joinPoint.getThis();
        log.error("Exception occurred in method - {} of class - {}. Exception message: {}",
                joinPoint.getSignature().getName(), controller.getClass().getSimpleName(), ex.getMessage(), ex);
    }

    @After("anyExceptionHandlerMethod()")
    public void logExceptionHandlerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("ExceptionHandler method '{}' in class '{}' has been invoked.", methodName, className);
    }

}
