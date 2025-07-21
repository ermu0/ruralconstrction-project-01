package com.rcs.server.controller;

import com.rcs.server.domain.entity.User;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/management")
public class UserManageController {

    @Autowired
    UserManageService userManageService;

    @GetMapping //无条件分页查询
    public Result pageUser(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询第{}页用户信息", page);
        PageBean pageBean = userManageService.pageUserInfo(page,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/{phoneNumber}") //需要做查询为空的异常处理
    public Result getAccount(@PathVariable String phoneNumber) {
        log.info("带查询用户手机号为{}", phoneNumber);
        User user = userManageService.queryUserByPhoneNumber(phoneNumber);
        return Result.success(user);
    }

    @DeleteMapping("/{ids}") //这里应该是做批量删除才对，前面应该有选项框（这里应该要做异常控制吧）
    public Result deleteUser(@PathVariable List<Integer> ids) {
        log.info("待删除员工id有：{}",ids);
        userManageService.removeByIds(ids);
        return Result.success();
    }
}
