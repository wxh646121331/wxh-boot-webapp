package com.wxh.boot.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuxinhong
 * @date 2022/7/28 10:43 下午
 * @ConfigurationProperties(prefix = "my-car") 需要与@Component或者配置类上的@EnableConfigurationProperties(Car.class)组合使用才能生效
 */
//@Component
@ConfigurationProperties(prefix = "my-car")
@Data
@ToString
public class Car {
    private String brand;

    private int price;
}
