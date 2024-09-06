package study.proxy.puredecorator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.proxy.puredecorator.code.*;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() throws Exception {
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }
    
    @Test
    void decorator1() throws Exception {
        Component messageDecorator = new MessageDecorator(new RealComponent());
        String result = messageDecorator.operation();
        log.info("result = {}", result);
    }

    @Test
    void decorator2() throws Exception {
        Component messageDecorator = new TimeDecorator(new MessageDecorator(new RealComponent()));
        String result = messageDecorator.operation();
        log.info("result = {}", result);
    }
}
