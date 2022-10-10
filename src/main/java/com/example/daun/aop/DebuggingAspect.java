package com.example.daun.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // aop 클래스 선언 - 부가 기능을 주입하는 클래스
@Component // ioc 컨테이너가 해당 객체를 생성 및 관리
@Slf4j
public class DebuggingAspect {

    @Pointcut("execution(* com.example.daun.api.*.*(..))")// 대상 메소스 선택
    private void cut() {
    }

    @Before("cut()")  // 실행 시점 설정 - cut의 대상이 수행되기 이전
    public void loggingArgs(JoinPoint joinPoint) { // cut()의 대상 메소드
        Object[] args = joinPoint.getArgs(); // 입력값 가져오기
        String className = joinPoint.getTarget()
            .getClass()
            .getSimpleName();  // 클래스명 가져오기
        String methodName = joinPoint.getSignature()
            .getName(); // 메소드명 가져오기
        for (Object obj : args) {
            log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj") // 실행 시점 설정 - cut의 대상 호출 성공 후 / 리터닝값과 메소드 반환값이 될
    public void loggingReturnValue(JoinPoint joinPoint, Object returnObj) { // 오브젝트형식의 값의 이름이 같아야함
        String className = joinPoint.getTarget()
            .getClass()
            .getSimpleName();
        String methodName = joinPoint.getSignature()
            .getName();
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);
    }
}
