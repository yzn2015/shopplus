package com.yzn.sport.commons;


import redis.clients.jedis.Jedis;

/**
 * @Author: YangZaining
 * @Date: Created in 21:00$ 2018/8/4$
 */
public class JredisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.200.135",6388);
        System.out.println(jedis.ping());
        jedis.close();
    }
}
