package study.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// JDK 동적 프록시에 적용 할 공통 로직
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {
    
    // 동적 프록시가 호출 할 대상
    private final Object target;

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 프록시가 호출 할 로직
     * method -> 공통 로직 코드 틀
     * 
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        /**
         *  target 인스턴스 메서드 호출
         *  method는 target인 AInterface의 구현체 call
         */
        Object result = method.invoke(target, args);

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = {}", resultTime);
        return result;
    }
}
