package com.rcs.server.controller;


import com.rcs.server.domain.entity.Order;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 用户订单管理的后端接口
 */
@Slf4j
@RestController
@RequestMapping("/order/management")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 所有用户的部分信息以及部分订单信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/a") //这里是无条件查询订单对应的用户姓名、手机号+订单对应需求描述、订单号
    public Result PageUserOrderInfo(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("查询第{}页订单信息",page);
        PageBean pageBean = orderService.queryUserOrderInfo(page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 某个用户的部分信息以及部分订单信息分页查询
     * @param page
     * @param pageSize
     * @param phoneNumber
     * @return
     */
    @GetMapping("/{phoneNumber}")
    public Result PageUserOrderInfo(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @PathVariable String phoneNumber){
        log.info("查询手机号为{}的用户订单",phoneNumber);
        PageBean pageBean = orderService.queryUserOrderInfo(page,pageSize,phoneNumber);
        return Result.success(pageBean);
    }


    /**
     * 用户订单信息具体查询
     * @param orderNumber
     * @return
     */
    @GetMapping("/{orderNumber}")
    public Result getOrderInfo(@PathVariable String orderNumber){
        log.info("正在查询订单:{}的信息",orderNumber);
        Order order = orderService.queryOrderInfo(orderNumber);
        return Result.success(order);
    }

    /**
     * 用户订单信息删除
     * @param orderNumber
     * @return
     */
    @DeleteMapping("/{orderNumber}")
    public Result removeUserOrder(@PathVariable String orderNumber){
        log.info("正在删除订单:{}",orderNumber);
        //这里后续我统一更改，否则不管删除失败还是成功都会返回成功
        orderService.removeOrder(orderNumber);
        return Result.success();
    }


    /**
     * 用户订单信息更新
     * @param order
     * @return
     */
    @PutMapping
    public Result updateOrderInfo(@RequestBody Order order){
        log.info("正在修改订单信息：{}",order);
        Map<Integer, String> r =  orderService.updateOrderInfo(order);
        return r.containsKey(1) ? Result.success(r.get(1)) : Result.error(r.get(0));
    }


}
