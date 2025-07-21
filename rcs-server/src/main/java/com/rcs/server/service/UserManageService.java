package com.rcs.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.User;

public interface UserManageService extends IService<User> {


    /**
     * 用户信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean pageUserInfo(Integer page, Integer pageSize);

    /**
     * 根据手机号对用户信息进行查询
     * @param phoneNumber
     * @return
     */
    User queryUserByPhoneNumber(String phoneNumber);
}
