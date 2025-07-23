package com.rcs.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rcs.common.utils.MinioUtils;
import com.rcs.server.domain.dto.UserOrderDto;
import com.rcs.server.domain.entity.Order;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.mapper.OrderManageMapper;
import com.rcs.server.service.OrderManageService;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class OrderManageServiceImpl extends ServiceImpl<OrderManageMapper, Order> implements OrderManageService {

    @Autowired
    OrderManageMapper orderManageMapper;

    /**
     * 用户部分信息以及部分订单信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageBean queryUserOrderInfo(Integer pageNow, Integer pageSize) {
        Page<Order> page = Page.of(pageNow,pageSize);

        //TODO 执行分页查询（暂未做排序）
        IPage<UserOrderDto> orderIPage = orderManageMapper.selectUserOrderInfoA(page);
        if (orderIPage.getTotal() <= 0){
            throw new RuntimeException("数据库连接出现问题，请联系管理员");
        }
        return new PageBean(orderIPage.getTotal(),orderIPage.getRecords());
    }

    /**
     * 某个用户的部分信息以及部分订单信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @param phoneNumber
     * @return
     */
    @Override
    public PageBean queryUserOrderInfo(Integer pageNow, Integer pageSize, String phoneNumber) {
        Page<Order> page = Page.of(pageNow,pageSize);

        //TODO 执行分页查询（暂未做排序）
        IPage<UserOrderDto> orderIPage = orderManageMapper.selectUserOrderInfoB(page, phoneNumber);
        if(orderIPage.getTotal() <= 0){
            throw new RuntimeException("未查询到订单信息");
        }

        return new PageBean(orderIPage.getTotal(),orderIPage.getRecords());
    }

    /**
     * 根据订单的键值查询订单信息
     * @param id
     * @return
     */
    @Override
    public Order queryOrderInfo(Integer id) {
        //构造查询条件
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getId,id);
        Order order = orderManageMapper.selectOne(queryWrapper);
        if(order == null){
            throw new RuntimeException("未查询到订单信息");
        }
        return order;
    }

    /**
     * 用户订单信息删除具体操作
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeOrder(Integer id) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getId,id);

        int row = orderManageMapper.delete(queryWrapper); //删除的行数
        if(row == 0){
            throw new RuntimeException("订单删除失败");
        }
    }

    /**
     * 用户订单信息更新具体操作
     * @param order
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderInfo(Order order) {
        order.setUpdateTime(LocalDateTime.now());
        int row = orderManageMapper.updateById(order); //更新的行数
        if(row <= 0){
            throw new RuntimeException("订单信息保存失败");
        }
    }

}
