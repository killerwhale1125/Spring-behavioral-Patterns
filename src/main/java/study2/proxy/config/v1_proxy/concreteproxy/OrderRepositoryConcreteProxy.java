package study2.proxy.config.v1_proxy.concreteproxy;

import study2.proxy.app.v2.OrderRepositoryV2;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

    private final OrderRepositoryV2 orderRepository;
    private final LogTrace trace;

    public OrderRepositoryConcreteProxy(OrderRepositoryV2 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.trace = trace;
    }

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository");
            orderRepository.save(itemId);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
