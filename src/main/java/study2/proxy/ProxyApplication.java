package study2.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import study2.proxy.config.ConfigV1;
import study2.proxy.config.ConfigV2;

/**
 * ConfigV1에서 빈을 수동으로 등록하면, study2.proxy.app.v1 패키지 내의 @RestController가 포함된 빈들도 Spring 컨텍스트에 등록됩니다.
 * 빈 등록에 사용된 @Import 어노테이션은 컨트롤러가 위치한 패키지에 관계없이 컨트롤러를 빈으로 등록합니다.
 */
@Import({ConfigV1.class, ConfigV2.class})
@SpringBootApplication(scanBasePackages = "study2.proxy.app.v3")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }
}
