package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户表操作的DAO层
 */
@Mapper
public interface UserMapper {

    /**
     * 查询所有数据
     * @return List<User> 用户列表
     */
    @Select("select * from user")
    public List<User> selectAll();


//    /**
//     * 根据ID删除用户信息（单条）
//     * @param id 主键值
//     * @return void
//     */
//    @Delete("delete from user where id = #{id}") //这种形式能防止sql注入
//    public void deleteById(Integer id);


    /**
     * 添加用户信息（单条）（未做空格值以及null校验）
     * @param user 用户类
     * @return void
     */
    @Options(keyProperty = "id", useGeneratedKeys = true) //返回主键值，并将主键值赋值给user对象的ID
    @Insert("insert into user(username, password, name, phone_number, email, address, image, create_time, update_time, role_type) " +
            "values (#{username},#{password},#{name},#{phoneNumber},#{email},#{address},#{image},#{createTime},#{updateTime},#{roleType})")
    public void insert(User user);


    /**
     * 根据ID更新用户信息（单条）
     * @param user 用户类
     * @return void
     */
    public void update(User user);

//    /**
//     * 根据ID查询用户信息（单条）
//     * @param id 主键值
//     * @return User 一个User对象
//     */
//    @Select("select * from user where id=#{id}")
//    public User selectById(Integer id);

    /**
     * 根据条件查询用户信息（单/多条）
     * @param user 用户类
     * @return List<User> 用户列表
     */
    public List<User> selectByCondition(User user);

    /**
     * 根据ID批量删除用户信息（单/多条）
     * @param ids 批量的主键值
     * @return void 无返回值
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 统计当前用户表中的用户数量
     * @return Long 一个长整型对象
     */
    @Select("select count(*) from user")
    public Long countUsers();

//    /**
//     * 根据起始索引以及分页展示记录数进行分页查询
//     * @param start 起始索引，从0开始
//     * @param pageSize 每页想要展示的记录数
//     * @return List<User> 用户列表
//     */
//    @Select("select * from user_accounts limit #{start},#{pageSize}")
//    public List<User> pageUsers(Integer start, Integer pageSize);

    /**
     * 根据用户名以及对应密码进行查询
     * @param username
     * @param password
     * @return User 一个用户类对象
     */
    @Select("select * from user where username=#{username} and password=#{password}")
    public User selectByUsernameAndPassword(String username, String password);
}
