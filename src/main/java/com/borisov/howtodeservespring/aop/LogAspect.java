package com.borisov.howtodeservespring.aop;

import com.borisov.howtodeservespring.infra.FieldPrinterUtils;
import com.borisov.howtodeservespring.infra.Log;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LogAspect {


    @Around("@annotation(log)")
    @SneakyThrows
    public Object aroundLogMethods(ProceedingJoinPoint pjp, Log log) {
        FieldPrinterUtils.printFieldsWithValues(log.value(), pjp.getTarget());
        Object retVal = pjp.proceed();

        return retVal;
    }
}


