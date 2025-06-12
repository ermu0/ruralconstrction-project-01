package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.Vendor;

import java.util.List;

public interface VendorService {

    /**
     * 根据分页页码以及分页所展示的记录数进行供应商信息的分页查询操作
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean page(Integer page, Integer pageSize);


    /**
     * 添加供应商信息
     * @param vendor
     */
    public void insertVendor(Vendor vendor);

    /**
     * 更新供应商信息
     * @param vendor
     */
    public void updateVendor(Vendor vendor);

    /**
     * 根据ID删除批量或者单条供应商数据
     * @param vendors
     */
    public void deleteVendor(List<Integer> vendors);

    /**
     * 根据条件查询供应商信息
     * @param vendor
     * @return
     */
    public List<Vendor> selectVendor(Vendor vendor);
}
