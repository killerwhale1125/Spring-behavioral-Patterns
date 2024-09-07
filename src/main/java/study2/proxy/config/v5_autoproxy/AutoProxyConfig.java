package study2.proxy.config.v5_autoproxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import study2.proxy.config.ConfigV1;
import study2.proxy.config.ConfigV2;
import study2.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@Configuration
@Import({ConfigV1.class, ConfigV2.class})
public class AutoProxyConfig {
    // 빈 교체용 어드바이저
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 이 위치에 있어야 프록시 적용 대상이 된다
        pointcut.setExpression("execution(* study2.proxy.app..*(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 이 위치에 있어야 프록시 적용 대상이 된다
        pointcut.setExpression("execution(* study2.proxy.app..*(..)) && !execution(* study2.proxy.app..noLog(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
