package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public final class RedisUtil {

    private static JedisPool jedisPool = null;

    //初始化redispool
    static {

        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

            jedisPool = new JedisPool(jedisPoolConfig, "172.24.10.161", 8715, 6000);
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {

        try {

            if(jedisPool != null) {

                Jedis resource = jedisPool.getResource();

                return resource;

            } else

                return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }
    /**
     * 释放jedis资源
     * @return
     */
    public static void freeSource(final Jedis jedis) {

        if(jedis != null)
            jedisPool.returnResource(jedis);

    }

    /**
     * @desc 提交订单至redis数据库 ./redis-cli -h 127.0.0.1 -p 8715
     */
    public static void submit(String xmlStr) {

        Jedis redis = new Jedis("172.24.10.161", 8715, 6000);
//        Jedis redis = getJedis();
        System.out.println("172.24.10.161" + ":" + 8715 + ":" + 6000);
        redis.rpush("dpps:queue:order", xmlStr);
        System.out.println("执行订单提交成功");
        redis.close();

    }
}
