package com.rcs.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {


    /**
     * 用户部分信息以及部分订单信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean queryUserOrderInfo(Integer page, Integer pageSize);

    /**
     * 某个用户的部分信息以及部分订单信息分页查询
     * @param page
     * @param pageSize
     * @param phoneNumer
     * @return
     */
    PageBean queryUserOrderInfo(Integer page, Integer pageSize, String phoneNumer);

    /**
     * 用户订单信息具体查询
     * @param orderNumber
     * @return
     */
    Order queryOrderInfo(String orderNumber);

    /**
     * 用户订单信息删除
     * @param orderNumber
     */
    void removeOrder(String orderNumber);

    /**
     * 用户订单信息更新
     * @param order
     * @return
     */
    Map<Integer, String> updateOrderInfo(Order order);
}
