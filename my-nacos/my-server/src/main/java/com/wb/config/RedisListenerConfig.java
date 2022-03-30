package com.wb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/28 15:22
 */
@Configuration
public class RedisListenerConfig {

    @Resource
    private LiveRedisKeysExpireListener liveRedisKeysExpireListener;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(liveRedisKeysExpireListener, new PatternTopic("__keyevent@*__:set"));
        return container;
    }

}
