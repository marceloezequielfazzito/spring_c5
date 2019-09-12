package com.eduit.spring.ws.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Aspect
@Component
public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    @Before("execution(public * com.eduit.spring.ws.repository.*.save(..))")
    public void loggingBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        LOGGER.info(" executed at date : {} on method {} ",
                ZonedDateTime.now().toString(),methodName);
    }

    @AfterReturning(pointcut = "execution(public * com.eduit.spring.ws.repository.*.save(..))" , returning = "returnValue")
    public void loggingAfter(JoinPoint joinPoint,Object returnValue){
        String methodName = joinPoint.getSignature().toShortString();
        LOGGER.info(" returning at date : {} on method {} return value {} ",
                ZonedDateTime.now().toString(),methodName,returnValue);
    }

    @AfterThrowing(pointcut = "execution(public * com.eduit.spring.ws.repository.*.save(..))" , throwing = "exception")
    public void loggingThrowing(JoinPoint joinPoint,Throwable exception){
        String methodName = joinPoint.getSignature().toShortString();
        LOGGER.info(" failing at date : {} on method {} throwing {} ",
                ZonedDateTime.now().toString(),methodName,exception);
    }




}
