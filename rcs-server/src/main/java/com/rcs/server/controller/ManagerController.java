package com.rcs.server.controller;

import com.rcs.server.domain.entity.Manager;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理人员管理的后端接口
 */
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {//（当前controller下的所有接口我都基本测试了一遍，重新做了简单的异常判断；另外增、删、改还要添加做事务管理）

    @Autowired
    ManagerService managerService;

    /**
     * 分页查询所有员工信息
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result pageAccount(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("查询第{}页员工信息",page);
        PageBean pageBean = managerService.pageManagerInfo(page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 根据工号查询目标员工
     * @param userId
     * @return
     */
    @GetMapping("/{userId}") //根据userId查询具体某位员工（这里需要做异常控制，假设没有查询到员工应该怎么做）
    public Result getAccount(@PathVariable String userId){
        log.info("待查询员工工号为：{}",userId);
        Manager manager = managerService.queryByManagerUserId(userId);
        return Result.success(manager);
    }

//    /**
//     * 管理人员登录检验
//     * @param manager
//     * @return
//     */
//    //TODO 后续权限管理时，登陆界面会重新修改
//    @PostMapping("/login") //这里稍微有点小bug：每次登录都会重新发放新的令牌（登录还要记录最后一次登录时间）
//    public Result login(@RequestBody Manager manager){
//        log.info("管理人员登录：{}",manager);
//
//        Map<Integer,String> r = managerService.login(manager);
//        return r.containsKey(1) ? Result.success(r.get(1)) : Result.error(r.get(0));
//    }

    /**
     * 根据ID对管理人员信息进行编辑
     * @param manager
     * @return
     */
    @PutMapping
    public Result updateAccount(@RequestBody Manager manager){
        log.info("待更改人员id为：{}",manager.getId());
        managerService.updateManagerInfo(manager);
        return Result.success("员工信息保存成功");
    }

//    /**
//     * 管理人员信息添加
//     * @param manager
//     * @return
//     */
//    @PostMapping("/account") //添加员工信息（由管理员添加工号即可，密码默认，其余值都可以为空，然后后续由员工自行修改额外信息）
//    public Result createAccount(@RequestBody Manager manager){
//        log.info("待添加员工工号为：{}",manager.getUserId());
//
//        Map<Integer,String> r = managerService.insertManager(manager);
//        return r.containsKey(1) ? Result.success(r.get(1)) : Result.error(r.get(0));
//    }

    /**
     * 管理人员信息删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}") //删除员工信息
    public Result removeAccount(@PathVariable Integer id){
        log.info("待删除员工id为：{}", id);
        managerService.removeManagerById(id);
        return Result.success("员工信息删除成功");
    }

}
