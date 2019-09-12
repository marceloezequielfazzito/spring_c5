package com.eduit.spring.ws.aspect;

import com.eduit.spring.ws.model.Valid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Validation {


    private static final Logger LOGGER = LoggerFactory.getLogger(Validation.class);

    @Around("execution(public * com.eduit.spring.ws.repository.*.save(..))")
    public Object isNotEmpty(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object firstArg = proceedingJoinPoint.getArgs()[0];
        if(firstArg instanceof Valid){
            Valid valid = (Valid) firstArg;
            if(valid.validate()){
                Object retVal = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
                return retVal;
            }
            LOGGER.error(" method {} not executed with args {}", proceedingJoinPoint.getSignature().toShortString(),firstArg);
            return firstArg;
        }
        Object retVal = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        return retVal;
    }



}
