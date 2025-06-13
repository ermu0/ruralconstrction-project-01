package com.example.mapper;

import com.example.pojo.Vendor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 供应商表操作的DAO层
 */
@Mapper
public interface VendorMapper {

    /**
     * 添加供应商信息（单条）(未做null以及空格值校验)
     * @param vendor
     */
    @Insert("insert into vendor(company_name, company_phone, company_charger_name, company_email, company_type, company_address, company_introduction, certificate, create_time, update_time, user_id) " +
            "values(#{companyName},#{companyPhone},#{companyChargerName},#{companyEmail},#{companyType},#{companyAddress},#{companyIntroduction},#{certificate},#{createTime},#{updateTime},#{userID}) ")
    public void insert(Vendor vendor);


    /**
     * 根据ID更新供应商信息（单条）
     * @param vendor
     */
    public void update(Vendor vendor);


    /**
     * 根据ID批量删除供应商信息（单/多条）
     * @param ids
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 查询所有数据
     * @return List<Vendor> 供应商列表
     */
    @Select("select * from vendor")
    public List<Vendor> selectAll();

    /**
     * 根据条件查询供应商信息（单/多条）
     * @param vendor
     * @return
     */
    public List<Vendor> selectByContion(Vendor vendor);
}
