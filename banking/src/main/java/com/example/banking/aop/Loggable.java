package com.example.banking.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {
	String className () default "";
	String success() default "";
	String failed() default "";
	Class<? extends Exception> throwable() default Exception.class;
}
