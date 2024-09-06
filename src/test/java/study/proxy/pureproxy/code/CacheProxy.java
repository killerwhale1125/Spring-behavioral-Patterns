package study.proxy.pureproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

    // 프록시 입장에서 호출해야하는 대상
    private Subject target;
    private String cacheVal;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("Proxy 호출");
        if(cacheVal == null) cacheVal = target.operation();
        return cacheVal;
    }
}
