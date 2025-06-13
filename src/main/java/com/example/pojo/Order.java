package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id; //主键ID
    private String orderNumber; //订单号
    private Integer totalAmount; //订单总金额
    private String paymentMethod; //支付方式（线上、线下）
    private String orderStatus; //订单状态（待处理、已确认、处理中、已发货、已送达、已取消、已退货）
    private String paymentStatus; //支付状态（未支付、支付成功、支付失败、已退款）
    private String orderAddress; //下单装修地址
    private String customerNotes; //用户备注
    private String adminNotes; //后台管理人员备注
    private LocalDateTime orderTime; //支付时间
    private LocalDateTime createTime; //订单创建时间
    private LocalDateTime updateTime;//订单更新时间
    private Integer userId;
}
