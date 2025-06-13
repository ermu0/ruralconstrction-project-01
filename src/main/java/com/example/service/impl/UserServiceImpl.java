package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.pojo.PageBean;
import com.example.pojo.User;
import com.example.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    //分页条件查询的详细操作（有pageHelper插件版）
    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String phoneNumber){

        //自动设置分页参数
        PageHelper.startPage(page,pageSize);

        //先将搜索条件封装到User对象对象中
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);

        //执行查询
        List<User> users = userMapper.selectByCondition(user);
        Page<User> p = (Page<User>) users;

        //将返回的用户信息封装到PageBean对象中
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    //管理员登录查询的详细操作
    @Override
    public User login(User user) {
        User u = userMapper.selectByUsernameAndPassword(user.getUsername(),user.getPassword());
        return u;
    }

    //根据ID批量删除员工的详细操作
    @Transactional(rollbackFor = Exception.class)//所有异常类型都进行回滚
    @Override
    public void deleteUser(List<Integer> ids) {
        userMapper.deleteByIds(ids);
    }

    //添加用户的具体操作
    @Override
    public void insertUser(User user) {
        //处理创建时间以及更新时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    //更新用户的具体操作
    @Override
    public void updateUser(User user) {
        //处理更新时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public List<User> selectUser(User user) {
        return userMapper.selectByCondition(user);
    }

}
