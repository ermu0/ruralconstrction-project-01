package com.rcs.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rcs.server.domain.entity.Manager;
import com.rcs.server.domain.pojo.PageBean;

import java.util.Map;

public interface ManagerService extends IService<Manager> {

    /**
     * 登录校验
     * @param manager
     * @return
     */
    Map<Integer,String> login(Manager manager);

    /**
     * 管理人员信息查询
     * @param userId
     * @return
     */
    Manager queryByManagerId(String userId);

    /**
     * 管理人员信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean pageManagerInfo(Integer page, Integer pageSize);

    /**
     * 管理人员信息更新
     * @param manager
     */
    Map<Integer,String> updateManagerInfo(Manager manager);

    /**
     * 管理人员信息添加
     * @param manager
     */
    Map<Integer,String> insertManager(Manager manager);
}
