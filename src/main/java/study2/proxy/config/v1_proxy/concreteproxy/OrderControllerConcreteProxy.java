package study2.proxy.config.v1_proxy.concreteproxy;

import study2.proxy.app.v2.OrderControllerV2;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {
    private OrderControllerV2 orderController;
    private LogTrace trace;

    public OrderControllerConcreteProxy(OrderControllerV2 orderController, LogTrace trace) {
        super(null);
        this.orderController = orderController;
        this.trace = trace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService");
            String result = orderController.request(itemId);
            trace.end(status);
            return result;
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return super.noLog();
    }
}
