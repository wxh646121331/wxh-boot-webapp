package com.wxh.boot.controller;

import com.hundsun.t2sdk.common.util.ClassUtils;
import com.hundsun.t2sdk.common.util.ResourceUtils;
import com.wxh.boot.t2.T2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @author wuxinhong
 * @date 2022/8/9 2:13 下午
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public void test() throws FileNotFoundException {
        String resourceLocation = "classpath:t2sdk-config.xml";
        URL url = ResourceUtils.getURL("classpath:t2sdk-config.xml");
        System.out.println("file:" + url.getFile());
        System.out.println("path" + url.getPath());
        String path = resourceLocation.substring("classpath:".length());
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        System.out.println(classLoader);
        URL url2 = classLoader.getResource(path);
        System.out.println("url2 file:" + url2.getFile());
        System.out.println("url2 path" + url2.getPath());
        T2Client.test();
    }
}
