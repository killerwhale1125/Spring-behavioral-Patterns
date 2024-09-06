package study.proxy.proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import study.proxy.common.advice.TimeAdvice;
import study.proxy.common.service.ConcreteService;
import study.proxy.common.service.ServiceImpl;
import study.proxy.common.service.ServiceInterface;

@Slf4j
public class ProxyFactoryTest {

    @Test
    void interfaceProxy() throws Exception {
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("tproxyClass={}", proxy.getClass());
    }

    @Test
    void cglibProxy() throws Exception {
        ConcreteService target = new ConcreteService();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("tproxyClass={}", proxy.getClass());

        proxy.call();

        // 이게 AOP 프록시인지?
        AopUtils.isAopProxy(proxy); // true
        AopUtils.isJdkDynamicProxy(proxy); // false
        AopUtils.isCglibProxy(proxy); // true
    }
}
