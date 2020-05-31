/*
package com.amit.bugtracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApplicationLoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.amit.bugtracker.controller.*.*(..))")
    public void forControllerPackage() {
    }

    @Pointcut("forControllerPackage()")
    public void loggingAspect() {
    }

    @Before("loggingAspect()")
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.debug("\n");
        logger.debug("====>> in @Before: calling method: " + method);
        Object[] args = joinPoint.getArgs();
        for (Object tempArg : args) {
            logger.debug("====>> argument: " + tempArg);
        }
    }

    @AfterReturning(pointcut = "loggingAspect()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.debug("\n");
        logger.debug("====>> in @AfterReturning: from method: " + method);
        logger.debug("====>> result: " + result);
    }

}
*/
