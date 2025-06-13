package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

/**
 * 供应商类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    private Integer id; //主键ID
    private String companyName; //供应商名称
    private String companyPhone; //供应商联系电话
    private String companyChargerName; //供应商法人姓名
    private String companyEmail; // 公司邮箱
    private Integer companyType; // 供应商类型（材料、人工、材料+人工）
    private String companyAddress; //供应商公司地址
    private String companyIntroduction; //供应商简介
    private String certificate; // 供应商资质证书
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //更新时间
    private Integer userID; //对应的用户主键ID
}
