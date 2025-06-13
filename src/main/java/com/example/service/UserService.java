package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.User;

import java.util.List;

public interface UserService {


    /**
     * 根据分页页码、分页记录数、用户姓名、用户手机号进行用户信息的分页条件查询操作
     * @param page 第几页
     * @param pageSize 每一页展示的记录数
     * @param name 用户姓名
     * @param phoneNumber 用户手机号
     * @return PageBean 自定义的封装对象
     */
    public PageBean page(Integer page, Integer pageSize, String name, String phoneNumber);

    /**
     * 管理员登陆查询
     * @param user
     * @return User 一个用户类对象
     */
    public User login(User user);

    /**
     * 根据ID批量删除员工信息（单/多条）
     * @param ids
     */
    public void deleteUser(List<Integer> ids);


    /**
     * 添加用户信息（单条）
     * @param user
     */
    public void insertUser(User user);

    /**
     * 更新用户信息（单条）
     * @param user
     */
    public void updateUser(User user);

    /**
     * 根据条件查询供应商信息（单/多条）
     * @param user
     * @return List<User>
     */
    public List<User> selectUser(User user);
}
