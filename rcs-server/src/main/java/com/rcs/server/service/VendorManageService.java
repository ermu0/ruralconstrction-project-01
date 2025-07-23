package com.rcs.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.Vendor;

public interface VendorManageService extends IService<Vendor> {

    /**
     * 分页查询所有服务商信息
     * @param page
     * @param pageSize
     * @return
     */
    PageBean queryVendorInfo(Integer page, Integer pageSize);

    /**
     * 根据手机号查询服务商信息
     * @param companyPhone
     * @return
     */
    Vendor queryVendorInfo(String companyPhone);

    /**
     * 根据键值删除对应服务商信息
     * @param id
     */
    void removeVendor(Integer id);


    /**
     * 服务商信息更新
     * @param vendor
     */
    void updateVendorInfo(Vendor vendor);



}
