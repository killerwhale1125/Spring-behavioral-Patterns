package study.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 공통 로직
 * 타겟은 이미 프록시 팩토리에서 만들 때 넣어서 필요없음
 */
@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();
        
        // target 호출 후 결과를 받음
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = {}", resultTime);
        return result;
    }
}
