package com.wxh.boot.zk;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author wuxinhong
 * @date 2023/3/6 11:14 PM
 */
@Component
public class ZkClient {
    @PostConstruct
    public void init(){

    }

    @PreDestroy
    public void destroy(){

    }
}
