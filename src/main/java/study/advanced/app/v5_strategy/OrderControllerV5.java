package study.advanced.app.v5_strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.advanced.trace.logtrace.LogTrace;
import study.advanced.trace.strategy.ContextV1;
import study.advanced.trace.template.AbstractTemplate;

@RestController
@RequiredArgsConstructor
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final LogTrace trace;

    @GetMapping("/v5/request")
    public String request(@RequestParam("itemId") String itemId) {
        new ContextV1<Void>(trace)
                .execute(() -> orderService.orderItem(itemId), "OrderController.request()");
        return "ok";
    }

}
