package study2.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study2.proxy.app.v2.OrderControllerV2;
import study2.proxy.app.v2.OrderRepositoryV2;
import study2.proxy.app.v2.OrderServiceV2;


@Configuration
public class ConfigV2 {

    @Bean
    public OrderControllerV2 orderControllerV2() {
        return new OrderControllerV2(orderServiceV2());
    }

    @Bean
    public OrderServiceV2 orderServiceV2() {
        return new OrderServiceV2(orderRepositoryV2());
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2() {
        return new OrderRepositoryV2();
    }
}
