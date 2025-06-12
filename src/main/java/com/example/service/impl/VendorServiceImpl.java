package com.example.service.impl;

import com.example.mapper.VendorMapper;
import com.example.pojo.PageBean;
import com.example.pojo.Vendor;
import com.example.service.VendorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorMapper vendorMapper;

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
}
