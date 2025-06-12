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

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    //分页查询的详细操作（无插件版）
//    @Override
//    public PageBean page(Integer page, Integer pageSize){
//
//        //统计用户总数量
//        Long countusers = userMapper.countUsers();
//
//        //计算分页起始索引
//        Integer start = (page - 1) * pageSize;
//        //进行用户信息分页查询
//        List<User> listusers = userMapper.pageUsers(start,pageSize);
//
//        //将返回的用户信息封装到PageBean对象中
//        PageBean pageBean = new PageBean(countusers,listusers);
//
//        return pageBean;
//    }

    //分页查询的详细操作（有pageHelper插件版）
    @Override
    public PageBean page(Integer page, Integer pageSize){

        //自动设置分页参数
        PageHelper.startPage(page,pageSize);

        //执行查询
        List<User> users = userMapper.selectAll();
        Page<User> p = (Page<User>) users;

        //将返回的用户信息封装到PageBean对象中
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    //登录查询的详细操作
    @Override
    public User login(User user) {
        User u = userMapper.selectByUsernameAndPassword(user.getUsername(),user.getPassword());
        return u;
    }

    //根据ID批量删除员工的详细操作
    @Transactional(rollbackFor = Exception.class)//所有异常类型都进行回滚
    @Override
    public void deleteUsers(List<Integer> ids) {
        userMapper.deleteByIds(ids);
    }

}
