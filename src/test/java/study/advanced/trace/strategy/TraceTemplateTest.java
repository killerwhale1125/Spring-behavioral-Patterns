package study.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced.trace.strategy.code.strategy.ContextV1;
import study.advanced.trace.strategy.code.strategy.Strategy;
import study.advanced.trace.strategy.code.strategy.StrategyLogic1;

@Slf4j
public class TraceTemplateTest {
    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();
    }

    @Test
    void strategyV2() {
        ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        contextV1.execute();
    }
}
