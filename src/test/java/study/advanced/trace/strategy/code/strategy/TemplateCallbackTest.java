package study.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced.trace.strategy.code.template.TimeLogTemplate;

@Slf4j
public class TemplateCallbackTest {

    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직 1 실행"));
    }
}
