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

        //TODO 执行分页查询（暂未做排序）
        IPage<User> userIPage = userManageMapper.selectUserInfo(page);

        if (userIPage.getTotal() <= 0){
            throw new RuntimeException("数据库连接异常，请联系管理员");
        }

        return new PageBean(userIPage.getTotal(),userIPage.getRecords());
    }

    /**
     * 根据手机号查询目标用户
     * @param phoneNumber
     * @return
     */
    @Override
    public User queryUserByPhoneNumber(String phoneNumber) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getPhoneNumber, phoneNumber);
        User user = userManageMapper.selectOne(lambdaQueryWrapper);
        if (user == null){
            throw new RuntimeException("未查询到用户信息");
        }
        return user;
    }

    /**
     * 根据ID删除目标用户
     * @param id
     */
    @Override
    public void removeUserById(Integer id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getId, id);
        int row = userManageMapper.delete(queryWrapper);
        if (row <= 0){
            throw new RuntimeException("用户信息删除失败");
        }
    }


}
