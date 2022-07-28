package com.wxh;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxinhong
 * @date 2022/7/26 8:50 下午
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public String test(){
        return "Test, SpringBoot2!";
    }
}
