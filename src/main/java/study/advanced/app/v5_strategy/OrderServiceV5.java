package study.advanced.app.v5_strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.advanced.trace.logtrace.LogTrace;
import study.advanced.trace.strategy.ContextV1;
import study.advanced.trace.template.AbstractTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        new ContextV1<Void>(trace)
                .execute(() -> orderRepository.save(itemId),"OrderService.orderItem()");
    }
}
