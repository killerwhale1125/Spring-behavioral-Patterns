package study.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    /**
     * 변하지 않는 부분
     */
    public void execute() {
        long startTime = System.currentTimeMillis();
        call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    /**
     * protected -> 상속 관계에서 특정 멤버를 외부에 노출하지 않으면서도 하위 클래스에서 사용할 수 있도록 하기 위해 사용됩니다.
     */
    protected abstract void call();
}
