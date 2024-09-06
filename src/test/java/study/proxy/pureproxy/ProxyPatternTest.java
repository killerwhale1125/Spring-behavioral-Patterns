package study.proxy.pureproxy;

import org.junit.jupiter.api.Test;
import study.proxy.pureproxy.code.CacheProxy;
import study.proxy.pureproxy.code.ProxyPatternClient;
import study.proxy.pureproxy.code.RealSubject;

public class ProxyPatternTest {

    @Test
    void noProxyTest() throws Exception {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient proxyPatternClient = new ProxyPatternClient(realSubject);

        proxyPatternClient.execute();
        proxyPatternClient.execute();
        proxyPatternClient.execute();
    }

    @Test
    void cacheProxyTest() throws Exception {
        ProxyPatternClient client = new ProxyPatternClient(new CacheProxy(new RealSubject()));
        client.execute();
        client.execute();
        client.execute();
    }
}
