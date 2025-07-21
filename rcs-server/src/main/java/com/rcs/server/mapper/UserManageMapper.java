package com.rcs.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rcs.server.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表操作的DAO层
 */
@Mapper
public interface UserManageMapper extends BaseMapper<User> {

}
