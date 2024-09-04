package study2.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/proxy/v1")
@RestController
public interface OrderControllerV1 {

    @GetMapping("/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/no-log")
    String noLog();
}
