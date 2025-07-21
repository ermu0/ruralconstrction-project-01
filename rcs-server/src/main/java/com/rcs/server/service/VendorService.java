package com.rcs.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.Vendor;

import java.util.List;

public interface VendorService extends IService<Vendor> {

    /**
     * 供应商基础信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean queryVendorInfo(Integer page, Integer pageSize);


    /**
     * 供应商资质证书上传
     * @param id
     * @param filePath
     * @return
     */
    String uploadCertificateFile(Integer id, String filePath);
}
