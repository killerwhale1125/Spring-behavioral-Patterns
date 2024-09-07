package study2.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {
    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param beanName = {}, beanName = {}", beanName, bean.getClass());

        String packageName = bean.getClass().getPackageName();
        
        // 원본이라면 그대로 반환
        if(!packageName.startsWith(basePackage)) {
            return bean;
        }
        
        // 프록시 대상이라면 프록시로 변경하여 반환
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("create proxy : target = {} proxy = {}", bean.getClass(), proxy.getClass());
        return proxy;
    }
}
