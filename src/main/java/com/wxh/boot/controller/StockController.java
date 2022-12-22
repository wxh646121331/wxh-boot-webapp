package com.wxh.boot.controller;

import com.wxh.boot.bean.LocalStock;
import com.wxh.boot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxinhong
 * @date 2022/9/16 9:04 上午
 */
@RestController
@RequestMapping("stock")
public class StockController {
    @Autowired
    private StockService stockService;

    LocalStock localStock1 = new LocalStock();

    LocalStock localStock2 = new LocalStock();

    LocalStock localStock3 = new LocalStock();

    @RequestMapping("/deduct1")
    public String deduct1(){
        localStock1.deduct1();
        return "success";
    }

    @RequestMapping("/deduct2")
    public String deduct2(){
        localStock2.deduct2();
        return "success";
    }

    @RequestMapping("/deduct3")
    public String deduct3(){
        localStock3.deduct3();
        return "success";
    }

    @RequestMapping("/deduct4")
    public String deduct4(){
        stockService.deduct();
        return "success";
    }

    @RequestMapping("/deduct5")
    public String deduct5(){
        stockService.deduct1();
        return "success";
    }

    @RequestMapping("/deduct6")
    public String deduct6(){
        stockService.deduct2();
        return "success";
    }

    @RequestMapping("/deduct7")
    public String deduct7(){
        boolean res = stockService.deduct3();
        return res ? "success" : "fail";
    }

    @RequestMapping("/deduct8")
    public String deduct8(){
        boolean res = stockService.deduct4();
        return res ? "success" : "fail";
    }

    @RequestMapping("/deduct9")
    public String deduct9(){
        boolean res = stockService.deduct5();
        return res ? "success" : "fail";
    }
}
