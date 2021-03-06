package com.revature.aspects;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	@Around("everything()")
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		// get a logger for the class of the method being called
		Logger log = LogManager.getLogger(pjp.getTarget().getClass());
		log.trace("Method with signature: "+pjp.getSignature());
		log.trace("With arguments: "+Arrays.toString(pjp.getArgs()));
		try {
			result = pjp.proceed();
		} catch (Throwable throwable) {
			logError(log, throwable);
			throw throwable;
		}
		log.trace("Method returning with: "+result);
		return result;
	}
	
	private void logError(Logger log, Throwable throwable) {
		log.error("Method threw exception: "+throwable);
		for(StackTraceElement stack : throwable.getStackTrace()) {
			log.warn(stack);
		}
		if(throwable.getCause() != null) {
			logError(log, throwable.getCause());
		}
	}
	
	@Pointcut("execution( * com.revature..*(..) )")
	private void everything() {/*empty method for hook*/}

}

