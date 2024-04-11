package com.example.library.logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class LoggingAspect {

    private static ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.library.services.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Calling method: {}.{} with arguments: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.example.library.services.*.*(..))", returning = "result")
    public void logAfterReturningMethod(JoinPoint joinPoint, Object result) {
        long executionTime = System.currentTimeMillis() - LoggingAspect.startTime.get();
        logger.info("Method {} returned with result: {}. Execution time: {} ms",
                joinPoint.getSignature().getName(), result, executionTime);
    }

    @AfterThrowing(pointcut = "execution(* com.example.library.services.*.*(..))", throwing = "exception")
    public void logAfterThrowingMethod(JoinPoint joinPoint, Exception exception) {
        long executionTime = System.currentTimeMillis() - LoggingAspect.startTime.get();
        logger.error("Exception in method {}: {}. Execution time: {} ms",
                joinPoint.getSignature().getName(), exception.getMessage(), executionTime);
    }

    @Before("execution(* com.example.library.services.*.*(..))")
    public void before() {
        startTime.set(System.currentTimeMillis());
    }

}
