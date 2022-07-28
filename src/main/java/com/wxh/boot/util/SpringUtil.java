package com.wxh.boot.util;

import org.springframework.context.ApplicationContext;

/**
 * @author wuxinhong
 * @date 2022/7/28 9:03 上午
 */
public class SpringUtil {
    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext ac){
        applicationContext = ac;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
