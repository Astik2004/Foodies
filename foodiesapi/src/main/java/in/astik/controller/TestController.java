package in.astik.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {
    public String test() {
        return "Hello, World!";
    }
}
