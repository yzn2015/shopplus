package com.yzn.sport.commons;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @Author: YangZaining
 * @Date: Created in 21:34$ 2018/8/4$
 */
public class RedisCacheTransfer {
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
