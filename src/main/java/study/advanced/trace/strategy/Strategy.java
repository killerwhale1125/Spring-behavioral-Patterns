package study.advanced.trace.strategy;

@FunctionalInterface
public interface Strategy<T> {
    T call();
}
