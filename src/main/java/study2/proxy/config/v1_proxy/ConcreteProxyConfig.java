package study2.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study2.proxy.app.v2.OrderControllerV2;
import study2.proxy.app.v2.OrderRepositoryV2;
import study2.proxy.app.v2.OrderServiceV2;
import study2.proxy.config.v1_proxy.concreteproxy.OrderControllerConcreteProxy;
import study2.proxy.config.v1_proxy.concreteproxy.OrderRepositoryConcreteProxy;
import study2.proxy.config.v1_proxy.concreteproxy.OrderServiceConcreteProxy;
import study2.proxy.trace.logtrace.LogTrace;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderControllerV2 orderController(LogTrace logTrace) {
        return new OrderControllerConcreteProxy(new OrderControllerV2(orderService(logTrace)), logTrace);
    }

    @Bean
    public OrderServiceV2 orderService(LogTrace logTrace) {
        return new OrderServiceConcreteProxy(new OrderServiceV2(orderRepository(logTrace)), logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepository(LogTrace logTrace) {
        return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(), logTrace);
    }
}
