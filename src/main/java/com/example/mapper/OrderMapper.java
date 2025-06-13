package com.example.mapper;

import com.example.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 查询所有订单
     * @return
     */
    @Select("select * from order")
    public List<Order> selectAll();

    /**
     * 添加订单信息
     * @param order
     */
    @Insert("insert into order(order_number, total_amount, payment_method, order_status, payment_status, order_address, customer_notes, admin_notes, order_time, create_time, update_time, user_id) values " +
            "(#{orderNumber},#{totalAmount},#{paymentMethod},#{orderStatus},#{paymentStatus},#{orderAddress},#{customerNotes},#{adminNotes},#{orderTime},#{createTime},#{updateTime},#{userId})")
    public void insert(Order order);

    /**
     * 更新订单信息
     * @param order
     */
    public void update(Order order);

    /**
     * 根据ID删除pi
     * @param ids
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 条件查询订单信息
     * @param order
     * @return
     */
    public List<Order> selectByCondition(Order order);
}
