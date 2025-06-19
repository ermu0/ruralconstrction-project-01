package com.rcs.server.controller;

import com.rcs.server.domain.entity.Manager;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理人员后端接口
 */
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {//（当前controller下的所有接口我都基本测试了一遍，重新做了简单的异常判断；另外增、删、改还要添加做事务管理）

    @Autowired
    ManagerService managerService;

    /**
     * 管理人员登录检验
     * @param manager
     * @return
     */
    @PostMapping("/login") //这里稍微有点小bug：每次登录都会重新发放新的令牌（登录还要记录最后一次登录时间）
    public Result login(@RequestBody Manager manager){
        log.info("管理人员登录：{}",manager);

        Map<Integer,String> r = managerService.login(manager);
        return r.containsKey(1) ? Result.success(r.get(1)) : Result.error(r.get(0));
    }

    /**
     * 管理人员信息编辑
     * @param manager
     * @return
     */
    @PutMapping("/account") //根据ID对管理人员信息进行更改
    public Result updateAccount(@RequestBody Manager manager){
        log.info("待更改人员id为：{}",manager.getId());

        Map<Integer,String> r = managerService.updateManagerInfo(manager);
        return r.containsKey(1) ? Result.success(r.get(1)) : Result.error(r.get(0));
    }

    /**
     * 管理人员信息添加
     * @param manager
     * @return
     */
    @PostMapping("/account") //添加员工信息（由管理员添加工号即可，密码默认，其余值都可以为空，然后后续由员工自行修改额外信息）
    public Result createAccount(@RequestBody Manager manager){
        log.info("待添加员工工号为：{}",manager.getUserId());

        Map<Integer,String> r = managerService.insertAccount(manager);
        return r.containsKey(1) ? Result.success(r.get(1)) : Result.error(r.get(0));
    }

    /**
     * 管理人员信息删除
     * @param ids
     * @return
     */
    @DeleteMapping("/account/{ids}") //删除员工信息
    public Result deleteAccountByIds(@PathVariable List<Integer> ids){
        log.info("待删除员工id为：{}", ids);
        managerService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 管理人员信息查询
     * @param userId
     * @return
     */
    @GetMapping("/account/{userId}") //根据userId查询具体某位员工
    public Result getAccountByUserId(@PathVariable String userId){
        log.info("待查询员工工号为：{}",userId);
        Manager manager = managerService.selectByUserId(userId);
        return Result.success(manager);
    }


    /**
     * 管理人员分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/account") //无条件分页查询
    public Result pageAccount(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("查询第{}页，共计{}位员工信息",page,pageSize);
        PageBean pageBean = managerService.getAccountByPage(page,pageSize);
        return Result.success(pageBean);
    }
}
