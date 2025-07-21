package com.rcs;

import com.rcs.common.properties.JwtProperties;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class RcsCommonApplicationTests {

//    @Autowired
//    JwtUtils jwtUtils;

    @Test
    public void testLombok(){
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSignKey("123456");
        jwtProperties.setExpire(430000L);
        System.out.println(jwtProperties.getSignKey());
        System.out.println(jwtProperties.getExpire());
    }

}
