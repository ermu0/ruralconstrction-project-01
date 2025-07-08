package com.rcs.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rcs.server.domain.dto.UserOrderDto;
import com.rcs.server.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据订单中关联的用户主键ID，来查询订单需求以及订单对应的用户信息
     * @return
     */
    @Select("select u.id,u.name,u.address,o.customer_notes, o.order_number " +
            "from user as u left join `order`as o on u.id = o.user_id")
    public IPage<UserOrderDto> selectUserOrderInfo(Page<?> page);


    @Select("select u.id,u.name,u.address,o.customer_notes, o.order_number " +
            "from user as u left join `order`as o on u.id = o.user_id where u.phone_number = #{phoneNumber}")
    public IPage<UserOrderDto> selectUserOrderInfo(Page<?> page, String phoneNumber);
}
