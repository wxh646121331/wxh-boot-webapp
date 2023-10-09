package com.wxh.boot;

import com.wxh.boot.bean.Pet;
import com.wxh.boot.bean.User;
import com.wxh.boot.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxinhong
 * @date 2022/7/26 4:03 下午
 */
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "com.wxh")
@EnableJpaRepositories
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MyApplication.class, args);
        SpringUtil.setApplicationContext(run);
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String i: beanDefinitionNames){
            System.out.println(i);
        }
        Pet tom = run.getBean("tom", Pet.class);
        User user = run.getBean("user01", User.class);
        System.out.println(tom);
        System.out.println(user);
    }
}
