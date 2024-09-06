package study.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() throws Exception {
        Hello target = new Hello();

        // 공통 로직 1 start
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);

        // 공통 로직 2 start
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
    }

    @Test
    void reflection1() throws Exception {
        Class hello = Class.forName("study.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = hello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result={}", result1);

        Method methodCallB = hello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        Class hello = Class.forName("study.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = hello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = hello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
