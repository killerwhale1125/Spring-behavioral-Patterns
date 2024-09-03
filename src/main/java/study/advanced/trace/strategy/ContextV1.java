package study.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import study.advanced.trace.TraceStatus;
import study.advanced.trace.logtrace.LogTrace;

@Slf4j
public class ContextV1<T> {
    private final LogTrace trace;

    public ContextV1(LogTrace trace) {
        this.trace = trace;
    }

    public void execute(Strategy strategy, String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            strategy.call();

            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
