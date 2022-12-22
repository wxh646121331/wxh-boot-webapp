package com.wxh.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/TEST.jpeg")
    public String hello1(){
        return "Hello!";
    }

    @RequestMapping(value = "mv", method = RequestMethod.GET)
    public ModelAndView shortcutRoute() {
        return null;
    }

}
