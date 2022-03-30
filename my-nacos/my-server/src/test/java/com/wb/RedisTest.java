package com.wb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wb.entity.UserEntity;
import com.wb.service.UserService;
import com.wb.util.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 9:59
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserService userServiceImpl;

    @Test
    public void save(){
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        System.out.println(format.format(new Date()));

        UserEntity user = userServiceImpl.getById(1L);
        userServiceImpl.saveOrUpdate(user);
        redisUtils.set("mymsg",JSON.toJSONString(user));
        UserEntity r = JSON.parseObject(String.valueOf(redisUtils.get("mymsg")),UserEntity.class);
        System.out.println(r);
        //redisUtils.delete("mymsg");
    }

}
