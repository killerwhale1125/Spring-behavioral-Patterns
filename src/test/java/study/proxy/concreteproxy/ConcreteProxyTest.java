package study.proxy.concreteproxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.proxy.concreteproxy.code.ConcreteClient;
import study.proxy.concreteproxy.code.TimeProxy;

@Slf4j
public class ConcreteProxyTest {

    @Test
    void noProxy() throws Exception {
        ConcreteClient concreteClient = new ConcreteClient(new ConcreteLogic());
        concreteClient.execute();
    }

    @Test
    void addProxy() throws Exception {
        ConcreteClient concreteClient = new ConcreteClient(new TimeProxy(new ConcreteLogic()));
        concreteClient.execute();
    }
}
