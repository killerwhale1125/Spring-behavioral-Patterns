package study2.proxy.config.v2_dynamic_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study2.proxy.app.v1.OrderControllerV1;
import study2.proxy.app.v1.OrderRepositoryV1;
import study2.proxy.app.v1.OrderServiceV1;
import study2.proxy.app.v1.impl.OrderControllerImplV1;
import study2.proxy.app.v1.impl.OrderRepositoryImplV1;
import study2.proxy.app.v1.impl.OrderServiceImplV1;
import study2.proxy.config.v2_dynamic_proxy.handler.LogTraceBasicHandler;
import study2.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        LogTraceBasicHandler handler
                = new LogTraceBasicHandler(logTrace, new OrderControllerImplV1(orderServiceV1(logTrace)));

        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class}, handler);
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        LogTraceBasicHandler handler
                = new LogTraceBasicHandler(logTrace, new OrderServiceImplV1(orderRepositoryV1(logTrace)));

        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class}, handler);
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        LogTraceBasicHandler handler
                = new LogTraceBasicHandler(logTrace, new OrderRepositoryImplV1());

        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class}, handler);
    }
}
