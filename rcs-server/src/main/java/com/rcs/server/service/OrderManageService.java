package com.rcs.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.Order;

public interface OrderManageService extends IService<Order> {


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
     * @param phoneNumber
     * @return
     */
    PageBean queryUserOrderInfo(Integer page, Integer pageSize, String phoneNumber);

    /**
     * 用户订单信息具体查询
     * @param id
     * @return
     */
    Order queryOrderInfo(Integer id);

    /**
     * 用户订单信息删除
     * @param id
     */
    void removeOrder(Integer id);

    /**
     * 用户订单信息更新
     * @param order
     * @return
     */
    void updateOrderInfo(Order order);

}
