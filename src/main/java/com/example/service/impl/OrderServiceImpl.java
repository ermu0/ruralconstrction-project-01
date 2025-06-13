package com.example.service.impl;

import com.example.mapper.OrderMapper;
import com.example.pojo.Order;
import com.example.pojo.PageBean;
import com.example.pojo.Vendor;
import com.example.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    //分页查询订单的具体操作
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //执行查询（后续可以修改为条件查询）
        List<Order> orders = orderMapper.selectAll();
        Page<Order> p = (Page<Order>) orders;

        //将返回的用户信息封装到PageBean对象中
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    //添加订单信息的具体操作
    @Override
    public void insertOrder(Order order) {
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.insert(order);
    }

    //更新订单信息的具体操作
    @Override
    public void updateOrder(Order order) {
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    //根据ID删除批量或单条订单数据的具体操作
    @Override
    public void deleteOrder(List<Integer> ids) {
        orderMapper.deleteByIds(ids);
    }

    //条件查询订单信息的具体操作
    @Override
    public List<Order> selectOrder(Order order) {
        return orderMapper.selectByCondition(order);
    }
}
