package study2.proxy.config.v6_aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import study2.proxy.config.ConfigV1;
import study2.proxy.config.ConfigV2;
import study2.proxy.config.v6_aop.aspect.LogTraceAspect;
import study2.proxy.trace.logtrace.LogTrace;

@Slf4j
@Configuration
@Import({ConfigV1.class, ConfigV2.class})
public class AopConfig {

    // 해당 Bean이 어드바이저로 반환된다
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
