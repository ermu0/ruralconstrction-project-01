package com.rcs.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rcs.common.utils.MinioUtils;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.Vendor;
import com.rcs.server.mapper.VendorMapper;
import com.rcs.server.service.VendorManageService;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
public class VendorManageServiceImpl extends ServiceImpl<VendorMapper, Vendor> implements VendorManageService {

    @Autowired
    VendorMapper vendorMapper;

    /**
     * 供应商信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageBean queryVendorInfo(Integer pageNow, Integer pageSize) {
        Page<Vendor> page = Page.of(pageNow,pageSize);
        IPage<Vendor> vendorIPage = vendorMapper.selectVendorInfo(page);
        if (vendorIPage.getTotal() <= 0){
            throw new RuntimeException("数据库连接异常，请联系管理员");
        }
        return new PageBean(vendorIPage.getTotal(),vendorIPage.getRecords());
    }

    @Override
    public Vendor queryVendorInfo(String companyPhone) {
        LambdaQueryWrapper<Vendor> queryWrapper = new LambdaQueryWrapper<Vendor>()
                .eq(Vendor::getCompanyPhone, companyPhone);
        Vendor vendor = vendorMapper.selectOne(queryWrapper);
        if (vendor==null){
            throw new RuntimeException("未查询到服务商");
        }
        return vendor;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeVendor(Integer id) {
        int row = vendorMapper.deleteById(id);
        if ( row <= 0){
            throw new RuntimeException("服务商信息删除失败");
        }
    }

    /**
     * 根据服务商的键值进行更新修改
     * @param vendor
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateVendorInfo(Vendor vendor) {
        vendor.setUpdateTime(LocalDateTime.now());
        int row = vendorMapper.updateById(vendor);//更新的行数
        if (row <= 0){
            throw new RuntimeException("服务商信息保存失败");
        }
    }


}
