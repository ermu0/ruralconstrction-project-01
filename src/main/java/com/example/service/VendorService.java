package com.example.service;

import com.example.pojo.PageBean;

public interface VendorService {

    /**
     * 根据分页页码以及分页所展示的记录数进行供应商信息的分页查询操作
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean page(Integer page, Integer pageSize);
}
