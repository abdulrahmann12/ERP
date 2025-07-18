package com.learn.erp.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Before("execution(* com.learn.erp.service.*.*(..)) && " +
	        "!within(com.learn.erp.service.AuthService) && " +
	        "!within(com.learn.erp.service.JwtService)")
	public void logBefore(JoinPoint joinPoint) {
        System.out.println("▶️ Start: " + joinPoint.getSignature().getName());
        System.out.println("📥 Args: " + Arrays.toString(joinPoint.getArgs()));
	}
	
    @AfterReturning(pointcut = "execution(* com.learn.erp.service.*.*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("✅ End: " + joinPoint.getSignature().getName());
        System.out.println("📤 Returned: " + result);
	}
    
    @AfterThrowing(pointcut = "execution(* com.learn.erp.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("❌ Exception in: " + joinPoint.getSignature().getName());
        System.out.println("💣 Message: " + ex.getMessage());
    }
}
