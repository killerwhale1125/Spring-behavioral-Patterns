package study2.proxy.config.v6_aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace trace;

    public LogTraceAspect(LogTrace logTrace) {
        this.trace = logTrace;
    }

    /**
     * Advisor
     */
    @Around("execution(* study2.proxy.app..*(..))") // -> Pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // -> Advice
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = trace.begin(message);

            Object result = joinPoint.proceed();
            trace.end(status);
            return result;
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
