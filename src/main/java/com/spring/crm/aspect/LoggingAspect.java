package com.spring.crm.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private static Logger LOGGER = Logger.getLogger(LoggingAspect.class.getName());
	
	@AfterReturning(pointcut="execution(* com.spring.mvc.dao.*.*(..))", returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		LOGGER.info("Returning from " + method);
		LOGGER.info("Result: " + result);
	}
}
