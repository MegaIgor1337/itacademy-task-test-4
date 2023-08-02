package com.example.service.aop;


import com.example.service.dto.UserCreateDto;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;



@Log4j2
@Aspect
@Component
public class LoggingAspectService {
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void anyServiceMethod() {}

    @Before("anyServiceMethod()")
    public void beforeLogUser(JoinPoint joinPoint) {
        Object service = joinPoint.getTarget().getClass().getSimpleName();
        log.info("started method {} in class {}",
                joinPoint.getSignature().getName(), service);
    }

    @AfterReturning("anyServiceMethod()")
    public void AfterLogUser(JoinPoint joinPoint) {
        Object service = joinPoint.getTarget().getClass().getSimpleName();
        log.info("ended method {} in class {}",
                joinPoint.getSignature().getName(), service);
    }

    @Before("anyServiceMethod() " +
            "&& @annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionalServiceMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Executing method {} in service class {} with annotation @Transaction",
                methodName, className);
    }

    @Before("execution(public * com.example.service.services.UserService.create(..))" +
            "&& args(userCreateDto)")
    public void logUserCreateDtoIsValid(UserCreateDto userCreateDto) {
        log.info("userCreateDto {} is valid", userCreateDto);
    }

    @AfterReturning(pointcut = "execution(public * com.example.service.services.UserService.create(..))",
            returning = "result")
    public void logAfterServiceMethodExecution(Object result) {
        log.info("User created: - {}", result);
    }
}
