package study2.proxy.config.v1_proxy.concreteproxy;

import study2.proxy.app.v2.OrderServiceV2;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {
    private final OrderServiceV2 orderService;
    private final LogTrace trace;

    public OrderServiceConcreteProxy(OrderServiceV2 orderService, LogTrace trace) {
        // 프록시 역할이기 때문에 부모 생성자 호출은 null;
        super(null);
        this.orderService = orderService;
        this.trace = trace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService");
            orderService.orderItem(itemId);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
