package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 用户类
 */
@Data //自动创建toString方法、属性的get方法、属性的set方法
@NoArgsConstructor //无参构造
@AllArgsConstructor //有参构造
public class User {
    private Integer id; //ID
    private String username; //用户名
    private String password; //密码
    private String name;//真实姓名（看到时候是否需要实名认证）
    private String phoneNumber; //手机号码
    private String email; //邮箱
    private String address; //地址（家庭地址或者公司地址）
    private String image; //图像url
    private LocalDateTime createTime;//用户信息创建时间
    private LocalDateTime updateTime;//用户信息修改时间
    private Integer roleType; //用户类别 (1.普通用户、2.管理员、3.供应商)
}
