package study.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import study.advanced.trace.TraceStatus;
import study.advanced.trace.logtrace.LogTrace;

@Slf4j
public class TraceTemplate {
    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    /**
     * Strategy가 넘어온 시점에 메서드 T 타입이 결정된다
     *
     */
    public <T> T execute(String message, Strategy<T> strategy) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            T result = strategy.call();

            trace.end(status);
            return result;
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
