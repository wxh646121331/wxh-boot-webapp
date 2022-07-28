package com.wxh.boot.controller;

import com.wxh.boot.bean.Pet;
import com.wxh.boot.config.MyConfig;
import com.wxh.boot.util.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxinhong
 * @date 2022/7/27 10:26 下午
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @RequestMapping("/test")
    public boolean testConfig(){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        MyConfig myConfig = applicationContext.getBean("myConfig", MyConfig.class);
        Pet pet1 = myConfig.pet01();
        Pet pet2 = myConfig.pet01();
        // @Configuration(proxyBeanMethods = false)，返回false
        // @Configuration(proxyBeanMethods = true)，则返回true
        return pet1 == pet2;
    }
}
