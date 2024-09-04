package study2.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study2.proxy.app.v1.OrderControllerV1;
import study2.proxy.app.v1.OrderRepositoryV1;
import study2.proxy.app.v1.impl.OrderControllerImplV1;
import study2.proxy.app.v1.impl.OrderRepositoryImplV1;
import study2.proxy.app.v1.impl.OrderServiceImplV1;
import study2.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import study2.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import study2.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import study2.proxy.trace.logtrace.LogTrace;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        // service 프록시를 알고있는 controller 구현체
        OrderControllerImplV1 controllerImpl = new OrderControllerImplV1(orderService(logTrace));
        
        // 그 controller를 프록시 객체에 저장 후 초기화
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceInterfaceProxy orderService(LogTrace logTrace) {
        OrderServiceImplV1 serviceImpl = new OrderServiceImplV1(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryInterfaceProxy orderRepository(LogTrace logTrace) {
        return new OrderRepositoryInterfaceProxy(new OrderRepositoryImplV1(), logTrace);
    }
}
