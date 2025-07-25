package com.rcs.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rcs.server.domain.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {

    @Select("select id, user_name, user_id, password, phone, email, role_type from manager")
    IPage<Manager> selectManagerInfo(Page<?> page);

    @Select("select id, user_name, user_id, password, phone, email, role_type from manager where user_id = #{userId}")
    Manager selectManagerInfoByUserId(String userId);
}
