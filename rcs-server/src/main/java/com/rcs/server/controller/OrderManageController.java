package com.rcs.server.controller;


import com.rcs.server.domain.entity.Order;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.OrderManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户订单管理的后端接口
 */
@Slf4j
@RestController
@RequestMapping("/order/management")
public class OrderManageController {

    @Autowired
    OrderManageService orderManageService;

    /**
     * 所有用户的部分信息以及部分订单信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/a") //这里是无条件查询订单对应的用户姓名、手机号+订单对应需求描述、订单号
    public Result pageUserOrderInfo(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("查询第{}页订单信息",page);
        PageBean pageBean = orderManageService.queryUserOrderInfo(page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 某个用户的部分信息以及部分订单信息分页查询
     * @param page
     * @param pageSize
     * @param phoneNumber
     * @return
     */
    //之所以要分页，是因为一个用户可能会创建多个订单，所以需要将返回的订单信息进行分页
    @GetMapping("/a/{phoneNumber}")
    public Result pageUserOrderInfo(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @PathVariable String phoneNumber){
        log.info("查询手机号为{}的用户订单",phoneNumber);
        PageBean pageBean = orderManageService.queryUserOrderInfo(page,pageSize,phoneNumber);
        return Result.success(pageBean);
    }


    /**
     * 用户订单信息具体查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getOrderInfo(@PathVariable Integer id){
        log.info("正在查询键值为：{}的订单信息",id);
        Order order = orderManageService.queryOrderInfo(id);
        return Result.success(order);
    }

    /**
     * 用户订单信息删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result removeUserOrderInfo(@PathVariable Integer id){
        log.info("正在删除键值为：{}的订单信息", id);
        orderManageService.removeOrder(id);
        return Result.success("订单删除成功");
    }


    /**
     * 用户订单信息更新
     * @param order
     * @return
     */
    @PutMapping
    public Result updateOrderInfo(@RequestBody Order order){
        log.info("正在修改订单信息：{}",order);
        orderManageService.updateOrderInfo(order);
        return Result.success("保存成功");
    }

}
