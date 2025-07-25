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

    /**
     * 分页查询所有用户信息
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result pageUserInfo(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询第{}页用户信息", page);
        PageBean pageBean = userManageService.pageUserInfo(page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 根据手机号查询目标用户
     * @param phoneNumber
     * @return
     */
    @GetMapping("/{phoneNumber}")
    public Result getAccount(@PathVariable String phoneNumber) {
        log.info("带查询用户手机号为{}", phoneNumber);
        User user = userManageService.queryUserByPhoneNumber(phoneNumber);
        return Result.success(user);
    }

    /**
     * 根据ID删除目标用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}") //这里应该是做批量删除才对，前面应该有选项框
    public Result removeUser(@PathVariable Integer id) {
        log.info("待删除员工id为：{}",id);
        userManageService.removeUserById(id);
        return Result.success("删除成功");
    }
}
