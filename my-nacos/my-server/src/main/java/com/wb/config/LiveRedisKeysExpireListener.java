package com.wb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/26 15:36
 */
@Service
@RequiredArgsConstructor
public class LiveRedisKeysExpireListener implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println(message.toString());
    }
}
