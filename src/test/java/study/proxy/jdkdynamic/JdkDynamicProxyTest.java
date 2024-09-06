package study.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.proxy.jdkdynamic.code.*;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() throws Exception {
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        /**
         * Java에서 제공하는 것
         * AInterface.class.getClassLoader() -> 어디에 생성될지?
         * new Class[]{AInterface.class} -> 어떤 인터페이스 기반 프록시 만들지? ( 여러개라서 배열 )
         * handler -> 적용할 동적 프록시 ( 프록시가 사용할 로직은 뭐야? )
         */
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(),
                new Class[]{AInterface.class}, handler);

        // handler의 메소드 호출
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB() throws Exception {
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        /**
         * Java에서 제공하는 것
         * BInterface.class.getClassLoader() -> 어디에 생성될지?
         * new Class[]{AInterface.class} -> 어떤 인터페이스 기반 프록시 만들지? ( 여러개라서 배열 )
         * handler -> 적용할 동적 프록시 ( 프록시가 사용할 로직은 뭐야? )
         */
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(),
                new Class[]{BInterface.class}, handler);

        // handler의 메소드 호출
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
