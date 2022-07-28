package com.wxh.boot.config;

import com.wxh.boot.bean.Pet;
import com.wxh.boot.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxinhong
 * @date 2022/7/27 10:18 下午
 */
@Configuration(proxyBeanMethods = true)
public class MyConfig {
    @Bean
    public User user01(){
        return new User();
    }

    @Bean("tom")
    public Pet pet01(){
        return new Pet();
    }
}
