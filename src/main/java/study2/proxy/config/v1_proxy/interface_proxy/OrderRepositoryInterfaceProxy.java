package study2.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study2.proxy.app.v1.OrderRepositoryV1;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

    private final OrderRepositoryV1 orderRepository;
    private final LogTrace trace;

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepositoryV1");
            orderRepository.save(itemId);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
