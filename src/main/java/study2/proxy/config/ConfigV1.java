package study2.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study2.proxy.app.v1.OrderControllerV1;
import study2.proxy.app.v1.OrderRepositoryV1;
import study2.proxy.app.v1.OrderServiceV1;
import study2.proxy.app.v1.impl.OrderControllerImplV1;
import study2.proxy.app.v1.impl.OrderRepositoryImplV1;
import study2.proxy.app.v1.impl.OrderServiceImplV1;

@Configuration
public class ConfigV1 {

    @Bean
    public OrderControllerV1 orderControllerV1() {
        return new OrderControllerImplV1(orderServiceV1());
    }

    @Bean
    public OrderServiceV1 orderServiceV1() {
        return new OrderServiceImplV1(orderRepositoryV1());
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        return new OrderRepositoryImplV1();
    }
}
