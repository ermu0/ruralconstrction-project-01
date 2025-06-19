package com.rcs;

import com.rcs.common.properties.JwtUtilsProperties;
import com.rcs.common.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class RcsCommonApplicationTests {

//    @Autowired
//    JwtUtils jwtUtils;

    @Test
    public void testLombok(){
        JwtUtilsProperties jwtUtilsProperties = new JwtUtilsProperties();
        jwtUtilsProperties.setSignKey("123456");
        jwtUtilsProperties.setExpire(430000L);
        System.out.println(jwtUtilsProperties.getSignKey());
        System.out.println(jwtUtilsProperties.getExpire());
    }

}
