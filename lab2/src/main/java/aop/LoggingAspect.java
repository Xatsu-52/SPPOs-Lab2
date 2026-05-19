package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before(
            "execution(* creational.*.*(..)) || " +
                    "execution(* behavioral.*.*(..))"
    )
    public void before(JoinPoint jp) {

        System.out.println(
                "[LOG] Вызов метода: "
                        + jp.getSignature().getName()
        );
    }

    @AfterReturning(
            pointcut =
                    "execution(* creational.*.*(..)) || " +
                            "execution(* behavioral.*.*(..))"
    )
    public void after(JoinPoint jp) {

        System.out.println(
                "[LOG] Метод завершён: "
                        + jp.getSignature().getName()
        );
    }
}
