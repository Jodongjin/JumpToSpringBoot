package com.example.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "Hello";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
        /**
         * redirect:<URL> -> URL로 리다이렉트
         * forward:<URL> -> URL로 포워드 (기존 요청 값들이 유지된 상태로 URL 전환) -> 검색하여 detail에서 이전으로 갈 때 사용하면 좋을 듯
         */
    }
}
