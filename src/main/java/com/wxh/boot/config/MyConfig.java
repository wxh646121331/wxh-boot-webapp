package com.wxh.boot.config;

import ch.qos.logback.core.db.DBHelper;
import com.wxh.boot.bean.Car;
import com.wxh.boot.bean.Pet;
import com.wxh.boot.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author wuxinhong
 * @date 2022/7/27 10:18 下午
 * @Configuration(proxyBeanMethods = false)
 *      Full(proxyBeanMethods = true) 保证每个@Bean方法被调用多少次返回的都是同一个实例
 *      Lite(proxyBeanMethods = false) 每个@Bean方法被调用多少次都是新创建的
 * @Import({User.class, DBHelper.class})
 *  给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名
 */
@Import({User.class, DBHelper.class})
@Configuration(proxyBeanMethods = true)
@ImportResource({"classpath:bean.xml"})
@EnableConfigurationProperties(Car.class)
public class MyConfig {
    @Bean
    public User user01(){
        return new User();
    }

    @ConditionalOnBean(name = "pet02")
    @Bean("user02")
    public User user02(){
        User user = new User();
        user.setPet(pet02());
        return new User();
    }

    @Bean("tom")
    public Pet pet01(){
        return new Pet();
    }

    @Bean
    public Pet pet02(){
        return new Pet();
    }
}
