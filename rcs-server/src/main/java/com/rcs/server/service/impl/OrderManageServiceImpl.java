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

    @Autowired
    MinioUtils minioUtils;

    @Autowired
    MinioClient minioClient;

    /**
     * 用户部分信息以及部分订单信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageBean queryUserOrderInfo(Integer pageNow, Integer pageSize) {
        Page<Order> page = Page.of(pageNow,pageSize);

        //TODO 执行分页查询（暂未做排序，看后续需求）
        IPage<UserOrderDto> orderIPage = orderManageMapper.selectUserOrderInfoA(page);

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

        //TODO 执行分页查询（暂未做排序）
        IPage<UserOrderDto> orderIPage = orderManageMapper.selectUserOrderInfoB(page, phoneNumer);

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

        return orderManageMapper.selectOne(queryWrapper);
    }

    /**
     * 用户订单信息删除具体操作
     * @param orderNumber
     */
    @Override
    public void removeOrder(String orderNumber) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNumber,orderNumber);

        orderManageMapper.delete(queryWrapper);
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
        //TODO 后续的异常判断应该是直接throw交给全局处理器
        if (orderManageMapper.updateById(order) == 1){
            result.put(1,"信息更新成功");
        }else {
            result.put(0,"信息更新失败，请重新检查信息是否有误");
        }
        return result;
    }

    //上传单个文件
    @Override
    public String uploadOrderFile(Integer id, String filePath){
        String fileUrl = "";
        try {
            //首先要判断该文件类型，然后通过类型匹配对应的bucket
            String bucketName = minioUtils.getBucketNameFromFilePath(filePath);
            //利用客户端判断并创建bucket
            minioUtils.createMinioBucket(minioClient,bucketName);
            //构建对应的文件存储名
            String objectName = minioUtils.getFileObjectName(filePath);
            //上传文件返回文件存储url
            fileUrl = minioUtils.uploadFile(minioClient, bucketName, filePath, objectName);
            //将url存储到数据库中（更新订单表中id为#{id}的订单的orderContract字段以及updataTime字段）
            LambdaUpdateWrapper<Order> lambdaUpdateWrapper = new LambdaUpdateWrapper<Order>()
                    .set(Order::getUpdateTime,LocalDateTime.now())
                    .set(Order::getOrderContract,fileUrl)
                    .eq(Order::getId,id);
            //TODO 这里应该做一个异常判断
            orderManageMapper.update(lambdaUpdateWrapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回url
        return fileUrl;
    }


}
