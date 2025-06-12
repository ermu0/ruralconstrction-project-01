package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.User;

import java.util.List;

public interface UserService {


    /**
     * 根据分页页码以及分页所展示的记录数进行用户信息的分页查询操作
     * @param page 分页页码
     * @param pageSize 分页展示的记录数
     * @return PageBean 一个自定义的封装对象
     */
    public PageBean page(Integer page, Integer pageSize);

    /**
     * 根据封装的User对象在数据库中进行查询
     * @param user
     * @return User 一个用户类对象
     */
    public User login(User user);

    /**
     * 根据ID批量删除员工信息（单/多条）
     * @param ids
     */
    public void deleteUsers(List<Integer> ids);
}
