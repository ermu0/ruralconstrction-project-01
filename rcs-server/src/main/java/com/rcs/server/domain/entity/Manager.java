package com.rcs.server.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理员类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

//    @TableId(type = IdType.AUTO) //使用mybatis-plus框架，id需要标注类型：自增长
    //如果在全局配置里面标注了ID的分配方法，那么这里就不需要在另外标注了
    private Integer id; //主键ID
    private String userId; //员工工号/登录账号（唯一且非空）
    private String password; //登录密码（前端只展示几位就行了吧，避免泄露隐私）
    private String userName; //员工姓名
    private String email; //邮箱
    private String phone; //员工手机号
    private String avatarUrl; //员工头像地址
    private String roleType; //员工类型（管理员、财务）
    private Integer isActive; //是否启用账号（1或0）
    private LocalDateTime lastLoginTime; //上次登陆时时间
    private String createUser; //账号创建人
    private LocalDateTime createTime; //员工信息创建时间
    private LocalDateTime updateTime; //员工信息更新时间
}
