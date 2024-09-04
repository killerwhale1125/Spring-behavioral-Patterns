package study2.proxy.app.v1.impl;

import lombok.extern.slf4j.Slf4j;
import study2.proxy.app.v1.OrderControllerV1;
import study2.proxy.app.v1.OrderServiceV1;

@Slf4j
public class OrderControllerImplV1 implements OrderControllerV1 {

    private final OrderServiceV1 orderService;

    public OrderControllerImplV1(OrderServiceV1 orderService) {
        this.orderService = orderService;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "ok";
    }
}
