package com.rcs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rcs.server.domain.entity.Manager;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.mapper.ManagerMapper;
import com.rcs.server.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class RcsServerApplicationTests {

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    ManagerService managerService;

    @Test
    public void testQueryWrapper(){
        //构建查询条件
        QueryWrapper<Manager> qw = new QueryWrapper<Manager>()
                .select("id","user_id","user_name","isActive")
                        .like("user_name","er")
                                .ge("create_time","2025-06-15");

        //查询并打印
        managerMapper.selectList(qw).forEach(System.out::println);
    }

    @Test
    public void testUpdateByQueryWrapper(){
        //构建更新信息
        Manager manager = new Manager();
        manager.setUserName("Mr.he");

        //更新的条件（where）
        QueryWrapper<Manager> qw = new QueryWrapper<Manager>().eq("user_name","ermu0");

        //更新
        managerMapper.update(manager, qw);
    }

    @Test
    public void testUpdateWrapper(){
        List<Integer> ids = List.of(1,2,3);
        UpdateWrapper updateWrapper = new UpdateWrapper<Manager>()
                .setSql("user_name = Mr.he")
                    .in("id",ids);
        managerMapper.update(null, updateWrapper);
    }

//    @Test
//    public void testTryCatch(){
//        try {
//            try {
//                int i = 1 / 0;
//            }catch (Exception e){
//                log.error("t0:"+e.getCause(),e);
//                throw new RuntimeException(e);//它会向上层的try catch传递，直到最终层
//            }
//        }catch (Exception e){
//            log.error("t1:"+e.getCause());
//            throw new RuntimeException("算式有错");
//        }
//    }

}
