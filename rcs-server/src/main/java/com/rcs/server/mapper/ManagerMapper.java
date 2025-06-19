package com.rcs.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rcs.server.domain.entity.Manager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {
}
