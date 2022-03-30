package com.wb.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 9:41
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public static final long DEFAULT_EXPIRE = 86400L;
    public static final long HOUR_ONE_EXPIRE = 3600L;
    public static final long HOUR_SIX_EXPIRE = 21600L;
    public static final long NOT_EXPIRE = -1L;

    public RedisUtils() {
    }

    public void set(String key, Object value, long expire) {
        this.redisTemplate.opsForValue().set(key, value);
        if (expire != -1L) {
            this.expire(key, expire);
        }

    }

    public void set(String key, Object value) {
        this.set(key, value, 86400L);
    }

    public Object get(String key, long expire) {
        Object value = this.redisTemplate.opsForValue().get(key);
        if (expire != -1L) {
            this.expire(key, expire);
        }

        return value;
    }

    public Object get(String key) {
        return this.get(key, -1L);
    }

    public Set<String> keys(String pattern) {
        return this.redisTemplate.keys(pattern);
    }

    public void deleteByPattern(String pattern) {
        this.redisTemplate.delete(this.keys(pattern));
    }

    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        this.redisTemplate.delete(keys);
    }

    public Object hGet(String key, String field) {
        return this.redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = this.redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public void hMSet(String key, Map<String, Object> map) {
        this.hMSet(key, map, 86400L);
    }

    public void hMSet(String key, Map<String, Object> map, long expire) {
        this.redisTemplate.opsForHash().putAll(key, map);
        if (expire != -1L) {
            this.expire(key, expire);
        }

    }

    public void hSet(String key, String field, Object value) {
        this.hSet(key, field, value, 86400L);
    }

    public void hSet(String key, String field, Object value, long expire) {
        this.redisTemplate.opsForHash().put(key, field, value);
        if (expire != -1L) {
            this.expire(key, expire);
        }

    }

    public void expire(String key, long expire) {
        this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void hDel(String key, Object... fields) {
        this.redisTemplate.opsForHash().delete(key, fields);
    }

    public void leftPush(String key, Object value) {
        this.leftPush(key, value, 86400L);
    }

    public void leftPush(String key, Object value, long expire) {
        this.redisTemplate.opsForList().leftPush(key, value);
        if (expire != -1L) {
            this.expire(key, expire);
        }

    }

    public Object rightPop(String key) {
        return this.redisTemplate.opsForList().rightPop(key);
    }
}
