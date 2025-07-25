package com.rcs.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rcs.server.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户表操作的DAO层
 */
@Mapper
public interface UserManageMapper extends BaseMapper<User> {

    @Select("select id, name, username, phone_number, address from user")
    IPage<User> selectUserInfo(Page<?> page);
}
