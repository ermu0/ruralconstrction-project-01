package com.example.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {
    //这里的两个字段可以自定义但是要和前端沟通形成对应的接口文档
    private Long total;//总记录数（指的查询出来的所有记录数）
    private List rows;//数据列表
}
