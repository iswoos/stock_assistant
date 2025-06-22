package com.stock.stock_assistant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class aaa {

    @GetMapping("test")
    public String test() {
        return "성공했어!~";
    }
}
