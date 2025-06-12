package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * web登陆服务controller
 */

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){ //将json格式数据封装到实体类中
        log.info("员工登录：{}",user);
        User u = userService.login(user);

        //下面这块内容后期需要调动到service层来进行处理
        //登录成功返回令牌
        if(u != null){
            //这里只封装了当前用户的基本信息，后续可以根据要求再更改
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",u.getId());
            claims.put("username",u.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        //登陆失败告知原因
        return Result.error("用户名或密码错误");
        //return u != null ? Result.success() : Result.error("用户名或密码错误");
    }
}
