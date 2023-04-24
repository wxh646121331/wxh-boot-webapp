package com.wxh.boot.controller;

import com.wxh.boot.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuxinhong
 * @date 2023/4/23 3:29 PM
 */
@RestController
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("新增订单")
    @RequestMapping(value = "/order/add", method = RequestMethod.POST)
    public void addOrder(){
        orderService.addOrder();
    }

    @ApiOperation("查询订单")
    @RequestMapping(value = "/order/{keyId}", method = RequestMethod.GET)
    public String getOrder(@PathVariable Integer keyId){
        return orderService.getOrderById(keyId);
    }
}
