package com.example;

import com.example.mapper.UserMapper;
import com.example.pojo.PageBean;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.service.VendorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest //在不需要spring环境的时候（比如测试非数据库相关方法的时候可以将其注释掉，以便加快编译运行速度）
class RuralconstrctionProject01ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    VendorService vendorService;

    //测试删除功能
//    @Test
//    public void testDelete(){
//        Integer id = 1; //后续测试直接修改数值即可
//        userMapper.deleteById(id);
//    }

    //测试条件功能
    @Test
    public void testInsert(){
        //构造一个用户对象
        User user = new User();
        user.setUsername("ermu0666");
        user.setName("ermu0");
        user.setPassword("123456");
        user.setAddress("天子殿");
        user.setPhoneNumber("1929889899");
        user.setEmail("1710221275@qq.com");
        user.setImage("www.baidu.com");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRoleType(1);

        //执行新增用户信息操作
        userMapper.insert(user);
    }

    //测试更新功能
    @Test
    public void testUpdate(){
        //重新构造用户信息
        User user = new User();
        user.setId(4);
        user.setUsername("ermu0666");
        user.setName("ermu0");
        user.setPassword("123456");
        user.setAddress("天子殿");
        user.setPhoneNumber("1929889899");
        user.setEmail("1710221275@qq.com");
        user.setImage("www.google.com");
        user.setUpdateTime(LocalDateTime.now());
        user.setRoleType(2);

        //执行更新用户信息功能
        userMapper.update(user);
    }

    //测试查询功能
//    @Test
//    public void testSelect(){
//       User user = userMapper.selectById(2);
//       System.out.println(user);
//    }

    //测试多条件查询功能（包括模糊匹配）
    @Test
    public void testSelectByCondition(){
        User user = new User();
        //测试下模糊查询有没有写错
        user.setName("ermu");
        user.setUsername("ermu0");
        user.setPhoneNumber("19980836547");

        List<User> list = userMapper.selectByCondition(user);
        System.out.println(list);
    }

    //测试JWT令牌构建
    @Test
    public void testBuildJwt(){
        //生成一个随机密钥
        //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //将该密钥打印出来查看
        //String base64Key = Encoders.BASE64.encode(key.getEncoded());
        //System.out.println(base64Key);

        //固定密钥
        String base64Key = "vliQsTzi8aKxoDDtK4zYVwAhcUCWcQ3fwB8G6EursIQ=";
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Key));

        //创建payload
        Map<String,Object> map = new HashMap<>();
        map.put("id",1);
        map.put("name","ermu0");

        //开始构建jwt令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,key)//设置JWT的签名算法部分：一个算法名称，一个算法密钥值
                .setClaims(map)//设置JWT的载荷payload部分
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000 ))//设置JWT令牌有效期为1个小时（由于这里单位是毫秒所以乘了1000）
                .compact();
        System.out.println(jwt);
    }

    //测试JWT令牌解析
    @Test
    public void testParseJwt(){
        //这是上一步中生成的jwt令牌
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiZXJtdTAiLCJpZCI6MSwiZXhwIjoxNzQ5MjgyMjgxfQ.ebz1QC6xOf1hS4mLt3Wi97QA3HS-t-wN_w88_Ld58eA";

        //固定密钥
        String base64Key = "vliQsTzi8aKxoDDtK4zYVwAhcUCWcQ3fwB8G6EursIQ=";
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Key));

        //构建解析器
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key) //设置用于验证签名的密钥
                .build() //构建解析器
                .parseClaimsJws(jwt); //解析并验证JWS（JWS即带签名的JWT）

        //解析成功后，获取Claims
        Claims claims = jws.getBody();
        System.out.println(claims);
    }

    //测试根据ID删除单个或者多个员工信息
    //测试事务管理的回滚操作
    @Test
    public void testDeleteUsers(){
        List<Integer> list = new ArrayList<>();
        list.add(22);
        userService.deleteUsers(list);
    }

    @Test
    public void testVendorPage(){//测试成功 说明我的表以及对应的对象都是没问题的
        PageBean pageBean = vendorService.page(1,3);
        System.out.println(pageBean);
    }
}
