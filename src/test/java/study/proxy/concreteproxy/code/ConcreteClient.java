package study.proxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;
import study.proxy.concreteproxy.ConcreteLogic;

@Slf4j
public class ConcreteClient {

    private ConcreteLogic concreteLogic;

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    public void execute() {
        concreteLogic.operation();
    }
}
