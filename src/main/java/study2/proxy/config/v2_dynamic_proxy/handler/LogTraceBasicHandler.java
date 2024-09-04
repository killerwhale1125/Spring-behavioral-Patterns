package study2.proxy.config.v2_dynamic_proxy.handler;

import study2.proxy.trace.TraceStatus;
import study2.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceBasicHandler implements InvocationHandler {

    private final LogTrace trace;
    private final Object target;

    public LogTraceBasicHandler(LogTrace trace, Object target) {
        this.trace = trace;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {
            // 클래스 메타 정보 포맷
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = trace.begin(message);
            Object result = method.invoke(target, args);
            trace.end(status);
            return result;
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
