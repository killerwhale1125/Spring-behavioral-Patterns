package study2.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study2.proxy.app.v1.OrderServiceV1;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1 {

    private final OrderServiceV1 orderService;
    private final LogTrace trace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderServiceV1");
            orderService.orderItem(itemId);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
