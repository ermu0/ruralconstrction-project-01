package com.example.service;


import com.example.pojo.Order;
import com.example.pojo.PageBean;

import java.util.List;

public interface OrderService {

    /**
     * 根据分页页码、分页记录进行订单信息的分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean page(Integer page, Integer pageSize);


    /**
     * 添加订单信息
     * @param order
     */
    public void insertOrder(Order order);

    /**
     * 更新订单信息
     * @param order
     */
    public void updateOrder(Order order);


    /**
     * 根据ID删除批量或者单条订单数据
     * @param orders
     */
    public void deleteOrder(List<Integer> orders);


    /**
     * 根据条件查询订单信息
     * @param order
     * @return
     */
    public List<Order> selectOrder(Order order);
}
