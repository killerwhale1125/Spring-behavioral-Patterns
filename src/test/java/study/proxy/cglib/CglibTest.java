package study.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import study.proxy.common.service.ConcreteService;

@Slf4j
public class CglibTest {

    @Test
    void cglib() throws Exception {
        ConcreteService target = new ConcreteService();

        // Enhancer 가 CGLIB를 생성함
        Enhancer enhancer = new Enhancer();
        // 부모 타입은 ConcreteService final 이라면 상속 불가
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        // 따라서 ConcreteService 타입이 가능
        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();
    }
}
