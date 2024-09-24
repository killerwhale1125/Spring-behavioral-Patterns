<p align="center">스프링 AOP Study</p>


**Proxy란?**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/06ae7d5d-218b-4fd2-8642-27c57f46daef/a2aadfea-418b-4253-a261-cf1efe43e63f/image.png)

- '대리인'으로 실제 객체에 대한 접근 이전에 필요한 행동을 취할수 있게 만들고 이 점을 이용해 미리 할당하지 않아도 상관 없는 것들을 실제로 이용할 대 할당하게 하여 메모리 용량을 아낄 수 있음
- 실제 객체가 드러나지 않게 하여 정보 은닉의 역할을 수행
- 접근 조절, 비용 절감, 복잡도 감소를 위해 접근이 힘든 객체에 대한 대역을 제공함

쉽게 말해 중간에 껴서 대리인의 역할을 수행하여 핵심 기능에 부가 기능을 추가하거나 어떠한 일을 수행한다. 

**그냥 부가 기능을 직접 추가 하면 되지 굳이 Proxy를 사용 해야 할까?**

로그 추적 기능을 개발하여 요청마다 로그를 남겨야 하는 상황을 생각해볼 수 있다.

로그 추적이란 사용자의 IP가 무엇인지, 중요한 정보가 무엇인지, 어느 시점에 예외가 발생하는지 등 전체적인 비즈니스 로직에 공통적인 기능으로서 직접 코드를 추가한다면 개발자는 핵심 비즈니스 로직을 작성하는 것 외에도 로그 추적에 신경 써야 하며 로그 추적의 구조가 변경된다면 모든 비즈니스 로직을 수정해야하는 단점이 존재한다. 

따라서  **Request → Proxy→ 핵심 비즈니스** 이와 같은 구조로 Proxy를 사용하여 **핵심 비즈니스에 부가 기능을 쉽게 추가하고 유지 보수하기 위하여 사용한다.**

**Spring AOP가 생성하는 프록시 종류**

- JDK 동적 프록시 → 인터페이스를 기반으로 Proxy를 생성

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/06ae7d5d-218b-4fd2-8642-27c57f46daef/a4a9a9b4-791b-415a-9f36-aa242d1902e3/image.png)

MVC 패턴을 사용하면 대부분 Controller → Service → Repository를 사용하는데 상황에 따라 Interface를 사용하여 ServiceImpl 와 같은 서비스 계층 클래스를 활용하여 비즈니스 로직을 처리하는게 대부분인데 이 Interface의 구현체를 기반으로 생성하는 Proxy가 JDK 동적 프록시이다.

**JDK 동적 프록시 생성과 활용 코드**

```java

@Configuration
public class DynamicProxyBasicConfig {
	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
	    LogTraceBasicHandler handler
	            = new LogTraceBasicHandler(logTrace, new OrderServiceImplV1(orderRepositoryV1(logTrace)));
	
	    return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
	            new Class[]{OrderServiceV1.class}, handler);
	}
}

public class LogTraceBasicHandler implements InvocationHandler {

    private final LogTrace trace;
    private final Object target;

    public LogTraceBasicHandler(LogTrace trace, Object target) {
        this.trace = trace;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {
            // 클래스 메타 정보 포맷
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = trace.begin(message);
            Object result = method.invoke(target, args);
            trace.end(status);
            return result;
        } catch(Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
```

1. JDK 동적 프록시 객체가 수행해야 할 로직을 InvocationHandler 인터페이스의 구현체로 정의하여 공통로직을 작성한다.
2. @Configuration 설정에 Proxy.newProxyInstance 를 통하여 인터페이스 기반 Proxy를 생성하여 Bean등록한다.
3. 애플리케이션 실행 시 실제 사용할 ServiceImpl의 Bean이 등록되는 것이 아닌 Proxy 객체가 Bean으로 등록되어 호출 시점에 Proxy가 ServiceImpl의 기능을 호출한다.

대략적인 구조

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/06ae7d5d-218b-4fd2-8642-27c57f46daef/be4b1fe7-b7fd-43fe-9843-1b936cbfc6b1/image.png)

- CGLIB → 클래스 상속을 통해 프록시를 생성

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/06ae7d5d-218b-4fd2-8642-27c57f46daef/0b9b2176-a584-4833-84a0-dc0265dac503/image.png)

**CGLIB**

- CGLIB는 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리이다.
- CGLIB를 사용하면 인터페이스가 없어도 구체 클래스만 가지고 동적 프록시를 만들어낼 수 있다.
- CGLIB는 원래는 외부 라이브러리인데, 스프링 프레임워크가 스프링 내부 소스 코드에 포함했다. 따라서 스프링을 사용한다면 별도의 외부 라이브러리를 추가하지 않아도 사용할 수 있다.

JDK 동적 프록시와 같이 ServiceImpl의 구현체로 비즈니스 로직을 수행하는 것이 아니라면 스프링 AOP는 상속을 통하여 프록시를 생성하는데 이것이 CGLIB이다.

**CGLIB** **생성과 활용 코드**

```java
@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = proxy.invoke(target, args);

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = {}", resultTime);
        return result;
    }
}
```

위 예제 코드도 마찬가지로 Java Reflection을 사용한다`org.springframework.cglib.proxy.MethodInterceptor` 패키지의 MethodInterceptor 의 구현체로 Proxy 객체가 수행할 일을 맡을 수 있으며 `Enhancer` 클래스를 통하여 target 클래스를 부모로 지정하고 CGLIB를 생성할 수 있다.

**Proxy 사용의 단점**

앞서 두 종류의 Proxy를 통하여 공통 로직을 쉽게 처리하였지만 Interface 기반인지 아닌지에 따라 Proxy를 구분 해야 하는 번거로움이 존재한다.

스프링 AOP는 동적 Proxy를 통합해서 편리하게 만들어주는 프록시 팩토리( ProxyFactory ) 라는 기능을 제공한다.

ProxyFactory는 Interface가 있으면 JDK 동적 프록시를 사용하고, 구체 클래스만 있다면 CGLIB를 사용한다

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/06ae7d5d-218b-4fd2-8642-27c57f46daef/b5f6c63f-05fa-41e6-8840-90da083f7888/image.png)

**두 기술을 함께 사용할 때 부가 기능을 적용하기 위해 JDK 동적 프록시가 제공하는 InvocationHandler와
CGLIB가 제공하는 MethodInterceptor를 각각 중복으로 따로 만들어야 할까?**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/06ae7d5d-218b-4fd2-8642-27c57f46daef/e2589976-3711-49c6-9a47-4026e42cc639/image.png)

스프링은 이 문제를 해결하기 위해 부가 기능을 적용할 때 **Advice**라는 새로운 개념을 도입했다. 

개발자는 InvocationHandler 나  MethodInterceptor를 신경 쓰지 않고, Advice만 만들어주면 되며 **결과적으로 InvocationHandler나 MethodInterceptor는 Advice만 호출한다.**

따라서 Advice라는 클래스로 공통 로직을 분리하여 Proxy의 종류에 따라 유연하게 처리가 가능하다.
