package com.example.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody // 붙이지 않으면 "Hello World"라는 이름의 template을 찾음
    public String hello() {
        return "Hello";
    }
}
