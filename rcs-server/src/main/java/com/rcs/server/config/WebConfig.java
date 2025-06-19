package com.rcs.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    /**
     * mybatis-plus的插件注册
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //1. 初始化核心插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //2. 添加分页插件
        PaginationInnerInterceptor pii = new PaginationInnerInterceptor(DbType.MYSQL);
        pii.setMaxLimit(1000L); //设置分页上限（最多查询1000条数据）
        //3. 将插件添加进interceptor中
        interceptor.addInnerInterceptor(pii);
        //后续想要添加新的插件就可以仿照2、3步骤进行
        return interceptor;
    }
}
