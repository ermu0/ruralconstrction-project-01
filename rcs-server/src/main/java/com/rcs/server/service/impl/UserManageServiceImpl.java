package com.rcs.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.User;
import com.rcs.server.mapper.UserManageMapper;
import com.rcs.server.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserManageServiceImpl extends ServiceImpl<UserManageMapper, User> implements UserManageService {

    @Autowired
    UserManageMapper userManageMapper;

    /**
     * 用户信息无条件分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageBean pageUserInfo(Integer pageNow, Integer pageSize) {
        // 准备分页条件
        Page<User> page =  Page.of(pageNow,pageSize);

        // 根据创建时间进行升序排序（不要硬编码）
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<User>()
                .orderByAsc(User::getCreateTime);

        //执行分页查询
        IPage<User> userIPage = userManageMapper.selectPage(page,queryWrapper);

        return new PageBean(userIPage.getTotal(),userIPage.getRecords());
    }

    @Override
    public User queryUserByPhoneNumber(String phoneNumber) {//这里需要做查询为空的异常处理
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getPhoneNumber, phoneNumber);
        return userManageMapper.selectOne(lambdaQueryWrapper);
    }


}
