package com.fox.factory.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@Aspect
public class LoggingAspect {
    @Pointcut("within(com.fox.factory.service.*)")
    private void serviceMethodPointcur() {
    }

    @Around("serviceMethodPointcur()")
    public Object setviceExecTimer(ProceedingJoinPoint pjp) throws Throwable{
        long start = System.currentTimeMillis();
        Object obj;
        try {
            obj = pjp.proceed();
        }catch (ResponseStatusException e){
            throw e;
        }
        catch (Exception e){
            log.error("Error occured while timing and executing method "+pjp.toLongString()+"MSG: "+e.toString());
            obj = null;
        }
        long stop = System.currentTimeMillis();
        log.info("Execution time (milis) "+(stop-start)+" method "+pjp.toLongString());
        return obj;
    }
}