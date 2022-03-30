package com.wb.util.redis.lock;

import com.wb.config.RedisProperies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.locks.Lock;

/**
 * redis锁工具类
 *
 * @author zhongwm
 * @version 1.0
 * @date 2021/4/17 14:32
 */
@Component
public class RedisLockUtils {

    @Autowired
    private RedisProperies redisProperies;

    private volatile static JedisPool pool;

    /**
     * 获取嗨场成员更新锁
     *
     * @param id
     * @return
     */
    public Lock getHiMemberUpdateLock(Long id) {
        return getLock("lock:hi:member:" + id);
    }

    /**
     * 获取嗨场管理员成员更新锁
     *
     * @param id
     * @return
     */
    public Lock getHiAuthManagerUpdateLock(Long id) {
        return getLock("lock:hi:auth:manage:" + id);
    }

    /**
     * 抽奖相关的锁
     *
     * @return
     */
    public Lock getPrizeLock(Long modelId) {
        return getLock("prize:" + modelId);
    }

    /**
     * 孔明灯开奖锁
     *
     * @return
     */
    public Lock getLotteryLock(Long activityId) {
        return getLock("lottery:" + activityId);
    }

    /**
     * 孔明灯领取红包奖励锁
     *
     * @return
     */
    public Lock getLotteryRedPacketLock(Long activityId) {
        return getLock("LotteryRedPacket:" + activityId);
    }

    /**
     * 初始化锁
     *
     * @param key
     * @return
     */
    private Lock getLock(String key) {
        if (pool == null) {
            synchronized (RedisLockUtils.class) {
                pool = new JedisPool(redisProperies, redisProperies.getHost(),
                        redisProperies.getPort(), redisProperies.getConnectionTimeout(),
                        redisProperies.getSoTimeout(), redisProperies.getPassword(),
                        redisProperies.getDatabase(), redisProperies.getClientName(),
                        redisProperies.isSsl(), redisProperies.getSslSocketFactory(),
                        redisProperies.getSslParameters(), redisProperies.getHostnameVerifier());
            }
        }
        return new JedisDistributedLock(pool, key);
    }

}
