package study.proxy.advisor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import study.proxy.common.advice.TimeAdvice;
import study.proxy.common.service.ServiceImpl;
import study.proxy.common.service.ServiceInterface;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1() throws Exception {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 항상 Ture 모든 곳에 공통로직 적용 ( true이기 때문 )
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }

    // 직접 만든 Pointcut
    @Test
    void advisorTest2() throws Exception {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 항상 Ture 모든 곳에 공통로직 적용 ( true이기 때문 )
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }

    // 스프링이 제공하는 포인트컷
    @Test
    void advisorTest3() throws Exception {
        ServiceInterface target1 = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target1);
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");
        // 항상 Ture 모든 곳에 공통로직 적용 ( true이기 때문 )
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }


    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {
        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.getName().equals(matchName);
        }

        @Override
        public boolean isRuntime() {
            // false일 경우 정적인 정보만 사용해서 캐싱 가능
            // true이면 아래 matches 실행
            return false;
        }

        // 캐싱 불가
        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
