package study.advanced.app.v5_strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.advanced.trace.logtrace.LogTrace;
import study.advanced.trace.strategy.ContextV1;
import study.advanced.trace.template.AbstractTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV5 {

    private final LogTrace trace;

    public void save(String itemId) {
        new ContextV1<Void>(trace).execute(() -> {
            if(itemId.equals("ex")) throw new IllegalStateException("예외 발생!");
            sleep(1000);
        },"OrderRepository.save()");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
