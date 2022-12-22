package com.wxh.boot.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wuxinhong
 * @date 2022/8/1 8:58 上午
 */
@ConfigurationProperties(prefix = "person")
@Component
@Data
@ToString
public class Person {
    private String name;

    private Pet pet;

    private Map<String, Double> score;

    private String[] interests;

    private int age;
}
