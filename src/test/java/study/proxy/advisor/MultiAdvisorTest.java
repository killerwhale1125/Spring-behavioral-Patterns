package study.proxy.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import study.proxy.common.advice.TimeAdvice;
import study.proxy.common.service.ServiceImpl;
import study.proxy.common.service.ServiceInterface;

@Slf4j
public class MultiAdvisorTest {

    @Test
    void multiAdvisorTest1() throws Exception {

        // client -> proxy2(advisor2) -> proxy1(advisor1) -> target

        // 프록시 1 생성
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory1 = new ProxyFactory(target);
        DefaultPointcutAdvisor addAdvisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory1.addAdvisor(addAdvisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        // 프록시 2 생성 target -> proxy1 입력
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor addAdvisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(addAdvisor2);
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        proxy2.save();
    }

    // 하나의 프록시 1 : N
    @Test
    void multiAdvisorTest2() throws Exception {

        // client -> advisor2 -> advisor1 -> target

        // 프록시 1 생성
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory1 = new ProxyFactory(target);

        DefaultPointcutAdvisor addAdvisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor addAdvisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        proxyFactory1.addAdvisor(addAdvisor2);
        proxyFactory1.addAdvisor(addAdvisor1);

        ServiceInterface proxy = (ServiceInterface) proxyFactory1.getProxy();

        proxy.save();
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 호출");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 호출");
            return invocation.proceed();
        }
    }
}
