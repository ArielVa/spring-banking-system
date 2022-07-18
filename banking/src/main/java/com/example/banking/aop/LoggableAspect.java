package com.example.banking.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggableAspect {

	@Around("@annotation(Loggable)")
	private Object methodExecutor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Object response = new Object();
		if(method != null) {
			Loggable logMethodMessage = method.getAnnotation(Loggable.class);

			String msg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - [" +logMethodMessage.className() + "] - " ;
			try {
				response = proceedingJoinPoint.proceed();
				msg += logMethodMessage.success();
				System.out.println(msg);
				return response;
			} catch(Exception e) {
				msg += logMethodMessage.failed();
				System.out.println(msg);
				throw logMethodMessage.throwable().getDeclaredConstructor().newInstance();
			}
		}
		return response;
	}
	
}
