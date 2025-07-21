package com.rcs.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rcs.server.domain.entity.Manager;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.mapper.ManagerMapper;
import com.rcs.server.service.ManagerService;
import com.rcs.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    JwtUtils jwtUtils;


    /**
     * 登录校验具体操作
     * @param manager
     * @return
     */
    @Override
    public Map<Integer, String> login(Manager manager) {
        //构建查询条件（用户名与密码都能从数据库中找到对应的值）
        LambdaQueryWrapper<Manager> queryWrapper = new LambdaQueryWrapper<Manager>()
                .eq(Manager::getUserId, manager.getUserId())
                .eq(Manager::getPassword, manager.getPassword());
        //创建返回键值对
        Map<Integer,String> result = new HashMap<Integer,String>();
        if (managerMapper.exists(queryWrapper)) {
            //创建jwt令牌信息
            Map<String,Object> claims = new HashMap<>();
            //后续要继续改进这里的硬编码
            claims.put("user_id",manager.getUserId());
            claims.put("login_time",System.currentTimeMillis());
            String jwt = jwtUtils.generateJwt(claims);
            result.put(1,jwt);

        }else {
            result.put(0, "账号或密码错误");
        }
        return result;
    }


    /**
     * 管理人员信息查询具体操作
     * @param userId
     * @return
     */
    @Override
    public Manager queryByManagerId(String userId) {
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<Manager>()
                .eq(Manager::getUserId, userId);
        //TODO 查询要做null判断
        return managerMapper.selectOne(wrapper);
    }

    /**
     * 管理人员信息分页查询具体操作（无额外条件）
     * @param pageNow
     * @param pageSize
     * @return
     */

    //TODO 这里我做了排序处理（后续跟前端商量一下看如何排序）
    @Override
    public PageBean pageManagerInfo(Integer pageNow, Integer pageSize) {
        // 准备分页条件
        Page<Manager> page =  Page.of(pageNow,pageSize);
        // 根据创建时间进行升序排序（不要硬编码）
        LambdaQueryWrapper<Manager> queryWrapper = new LambdaQueryWrapper<Manager>()
                .orderByAsc(Manager::getCreateTime);
        // 执行分页查询
        IPage<Manager> managerIPage = managerMapper.selectPage(page, queryWrapper);
        return new PageBean(managerIPage.getTotal(),managerIPage.getRecords());
    }

    /**
     * 管理人员信息更新
     * @param manager
     */
    @Override
    public Map<Integer,String> updateManagerInfo(Manager manager) {
        Map<Integer,String> result = new HashMap<Integer,String>();
        manager.setUpdateTime(LocalDateTime.now());

        if (managerMapper.updateById(manager) == 1){
            result.put(1,"信息更新成功");
        }else {
            result.put(0,"信息更新失败，请重新检查信息是否有误");
        }
        return result;
    }

    /**
     * 管理人员信息添加
     * @param manager
     */
    @Override
    public Map<Integer,String> insertManager(Manager manager) {
        Map<Integer,String> result = new HashMap<Integer,String>();
        //设置创建与更新时间
        manager.setCreateTime(LocalDateTime.now());
        manager.setUpdateTime(LocalDateTime.now());
        //设置密码（这里后续要进行去除硬编码操作）
        manager.setPassword(manager.getUserId()+"123456");
        if (managerMapper.insert(manager) == 1){
            result.put(1,"信息添加成功");
        }else {
            result.put(0,"信息添加失败，请重新检查信息是否有误");
        }
        return result;
    }
}
