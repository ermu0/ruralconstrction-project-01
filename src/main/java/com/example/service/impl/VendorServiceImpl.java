package com.example.service.impl;

import com.example.mapper.VendorMapper;
import com.example.pojo.PageBean;
import com.example.pojo.Vendor;
import com.example.service.VendorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorMapper vendorMapper;

    //分页查询的具体实现
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //执行查询（后续可以修改为条件查询）
        List<Vendor> vendors = vendorMapper.selectAll();
        Page<Vendor> p = (Page<Vendor>) vendors;

        //将返回的用户信息封装到PageBean对象中
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }


    //添加供应商信息的具体实现
    @Override
    public void insertVendor(Vendor vendor) {
        //设置创建以及更新时间
        vendor.setCreateTime(LocalDateTime.now());
        vendor.setUpdateTime(LocalDateTime.now());
        vendorMapper.insert(vendor);
    }

    //更新供应商信息的具体实现
    @Override
    public void updateVendor(Vendor vendor) {
        //设置更新时间
        vendor.setUpdateTime(LocalDateTime.now());
        vendorMapper.update(vendor);
    }

    //删除供应商信息的具体实现
    @Override
    public void deleteVendor(List<Integer> vendors) {
        vendorMapper.deleteByIds(vendors);
    }

    //条件查询的具体实现
    @Override
    public List<Vendor> selectVendor(Vendor vendor) {
        return vendorMapper.selectByContion(vendor);
    }


}
