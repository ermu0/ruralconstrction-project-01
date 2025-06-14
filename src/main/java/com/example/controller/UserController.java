package com.example.controller;

import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * web用户管理服务controller
 */

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //这里只是简单测试用（前端传递用户姓名以及手机号进行验证）
    //默认设置页码为1，分页展示记录数为10
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,
                       String phoneNumber){
        log.info("分页查询，参数：{},{}",page,pageSize,name,phoneNumber);
        PageBean pageBean = userService.page(page,pageSize,name,phoneNumber);
        return Result.success(pageBean);
    }


}
