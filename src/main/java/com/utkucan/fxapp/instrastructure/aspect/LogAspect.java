package com.utkucan.fxapp.instrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @AfterThrowing(pointcut = "within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Service *)",
            throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error(" [{}#{}] threw {}: {}", className, methodName, ex.getClass().getSimpleName(), ex.getMessage(), ex);
    }
}
