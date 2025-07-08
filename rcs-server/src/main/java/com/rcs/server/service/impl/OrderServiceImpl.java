package com.rcs.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rcs.server.domain.dto.UserOrderDto;
import com.rcs.server.domain.entity.Order;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.mapper.OrderMapper;
import com.rcs.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    /**
     * 用户部分信息以及部分订单信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageBean queryUserOrderInfo(Integer pageNow, Integer pageSize) {
        Page<Order> page = Page.of(pageNow,pageSize);

        //执行分页查询（暂未做排序，看后续需求）
        IPage<UserOrderDto> orderIPage = orderMapper.selectUserOrderInfo(page);

        return new PageBean(orderIPage.getTotal(),orderIPage.getRecords());
    }

    /**
     * 某个用户的部分信息以及部分订单信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @param phoneNumer
     * @return
     */
    @Override
    public PageBean queryUserOrderInfo(Integer pageNow, Integer pageSize, String phoneNumer) {
        Page<Order> page = Page.of(pageNow,pageSize);

        //执行分页查询（暂未做排序）
        IPage<UserOrderDto> orderIPage = orderMapper.selectUserOrderInfo(page, phoneNumer);

        return new PageBean(orderIPage.getTotal(),orderIPage.getRecords());
    }

    /**
     * 用户订单信息具体查询具体操作
     * @param orderNumber
     * @return
     */
    @Override
    public Order queryOrderInfo(String orderNumber) {
        //构造查询条件
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNumber,orderNumber);

        return orderMapper.selectOne(queryWrapper);
    }

    /**
     * 用户订单信息删除具体操作
     * @param orderNumber
     */
    @Override
    public void removeOrder(String orderNumber) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNumber,orderNumber);

        orderMapper.delete(queryWrapper);
    }

    /**
     * 用户订单信息更新具体操作
     * @param order
     * @return
     */
    @Override
    public Map<Integer, String> updateOrderInfo(Order order) {
        Map<Integer,String> result = new HashMap<Integer,String>();
        order.setUpdateTime(LocalDateTime.now());

        if (orderMapper.updateById(order) == 1){
            result.put(1,"信息更新成功");
        }else {
            result.put(0,"信息更新失败，请重新检查信息是否有误");
        }
        return result;
    }


}
