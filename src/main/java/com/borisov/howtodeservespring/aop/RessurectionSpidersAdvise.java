package com.borisov.howtodeservespring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RessurectionSpidersAdvise implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object target = invocation.getThis();

        try {
            // Получаем метод getter для поля lives
            Method getLivesMethod = target.getClass().getMethod("getLives");
            int hp = (int) getLivesMethod.invoke(target);

            if (hp == 1) {
                // Получаем метод setter для поля lives
                Method setLivesMethod = target.getClass().getMethod("setLives", int.class);
                setLivesMethod.invoke(target, 100);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Логируем ошибку или обрабатываем по необходимости
            throw new RuntimeException("Ошибка при работе с методами getLives/setLives", e);
        }

        return invocation.proceed();
    }
}
