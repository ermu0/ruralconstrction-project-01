package com.rcs.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto {
    private Integer id; //用户的ID主键值
    private String name; //用户姓名
    private String address; //用户住址
    private String customer_notes; //用户订单需求
    private String order_number; //用户订单号
}
