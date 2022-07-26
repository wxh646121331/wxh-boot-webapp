package com.wxh.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxinhong
 * @date 2022/7/26 4:03 下午
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(){
        return "Hello, SpringBoot2!";
    }

}
