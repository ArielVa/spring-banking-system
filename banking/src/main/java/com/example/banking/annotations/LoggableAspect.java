package com.example.banking.annotations;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggableAspect {

	@Around("@annotation(Loggable)")
	private String methodExecutor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if(method != null) {
			try {
				proceedingJoinPoint.proceed();
				Loggable loggable = method.getAnnotation(Loggable.class);
				System.out.println("[" + loggable.getClass().getSimpleName().toString() + "] " + loggable.successMessage());
			} catch(Exception e) {
				return null;
			}
		}
		
		
		return null;
		
	}
	
}
