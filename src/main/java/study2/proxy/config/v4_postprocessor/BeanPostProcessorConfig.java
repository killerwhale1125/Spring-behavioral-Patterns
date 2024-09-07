package study2.proxy.config.v4_postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import study2.proxy.config.ConfigV1;
import study2.proxy.config.ConfigV2;
import study2.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import study2.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@Configuration
@Import({ConfigV1.class, ConfigV2.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTracePostProcessor packageLogTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("study2.proxy.app.v3", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
