package com.rcs.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rcs.server.domain.entity.Vendor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 供应商表操作的DAO层
 */
@Mapper
public interface VendorMapper extends BaseMapper<Vendor> {

    /**
     * 无条件的分页查询，主要返回供应商的名称、联系方式、联系人、地址、公司资质证书存储地址、产品信息（也就是公司介绍）
     * @param page
     * @return
     */
    @Select("select id, company_name, company_phone, company_charger_name, company_address, company_type, certificate, company_introduction from vendor")
    IPage<Vendor> selectVendorInfo(Page<?> page);

}
