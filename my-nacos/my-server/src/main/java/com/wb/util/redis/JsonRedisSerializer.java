package com.wb.util.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 9:52
 */
public class JsonRedisSerializer<T> implements RedisSerializer<T> {
    private static ParserConfig defaultRedisConfig = new ParserConfig();
    private Class<T> type;

    public JsonRedisSerializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return t == null ? new byte[0] : JSON.toJSONString(t, new SerializerFeature[]{SerializerFeature.WriteClassName}).getBytes(IOUtils.UTF8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length > 0) {
            String str = new String(bytes, IOUtils.UTF8);
            return JSON.parseObject(str, this.type, defaultRedisConfig, new Feature[0]);
        } else {
            return null;
        }
    }

    static {
        defaultRedisConfig.setAutoTypeSupport(true);
    }
}
