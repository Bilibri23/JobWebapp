package com.brian.jobapp1.aop;


//We are using AOP which compliments OOP , it deals with cut crossing concerns,
// such that all aspects of the business logic are up to standard
//handles exception, logging, security, validation and also performance.
// u will create a separate class for that and u will not call it yourself it will be automatic

/*
refer to spring documentation section of AOP
1-- Aspect, joinPoint, advice, Pointcut, targetObject
 */


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    //any method that is being called I want to maintain a log for it
        private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

        //  return type, class name, method_name(args)

    //before is the advice the parameters(expression) if it is the point cut

//        @Before("execution(* com.brian.jobapp1.service.JobService.*(..)) ") //pointcut
//        public  void logMethodCall(){
//            logger.info("method called"); //prints method called for each method in the service
//
//    }
   @Before("execution(* com.brian.jobapp1.service.JobService.getJob(..)) ") //pointcut
    public  void logMethodCall(JoinPoint joinPoint){
        logger.info(joinPoint.getSignature().getName()); //prints method called for each method in the service

    }

}
