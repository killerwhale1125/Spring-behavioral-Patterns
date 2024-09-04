package study2.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study2.proxy.app.v1.OrderControllerV1;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 orderController;
    private final LogTrace trace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV1");
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
        return null;
    }
}
