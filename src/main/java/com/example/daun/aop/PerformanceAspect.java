package com.example.daun.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // aop 클래스 선언 - 부가 기능을 주입하는 클래스
@Component // ioc 컨테이너가 해당 객체를 생성 및 관리
@Slf4j
public class PerformanceAspect {

    @Pointcut("@annotation(com.example.daun.annotation.RunningTime)") // 특정 어노테이션 지정
    private void enableRunningTime(){

    }
    
    @Pointcut("execution(* com.example.daun..*.*(..))") // 기본 패키지의 모든 메소드
    private void cut(){

    }

    @Around("cut() && enableRunningTime()") // 실행 시점 설정 - 두 조건을 모두 만족하는 대상을 전후로 부가 기능 삽입
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object returningObj = joinPoint.proceed();
        stopWatch.stop();
        String methodName = joinPoint.getSignature()
                .getName();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }
}
