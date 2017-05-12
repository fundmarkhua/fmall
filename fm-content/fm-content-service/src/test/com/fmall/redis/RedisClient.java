package com.fmall.redis;

import com.fmall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/7 9:59
 */

public class RedisClient {

    @Test
    public void testJedisClient() throws Exception {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*.xml");

        //从容器中获取jedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("first", "熊孩子丽胖胖");
        String result = jedisClient.get("first");
        System.out.println(result);
        System.in.read();

        //
    }

    @Test
    public void testRedis() throws Exception {
        // 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。
        Jedis jedis = new Jedis("192.168.25.128", 6379);
        // 第二步：使用Jedis对象操作数据库，每个redis命令对应一个方法。
        jedis.set("test123", "臭屁胖");
        String string = jedis.get("test123");
        System.out.println(string);
        // 第四步：关闭Jedis
        jedis.close();
    }

    @Test
    public void testRedisPool() throws Exception {
        //创建JedisPool 连接池对象
        JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
        // 从连接池获得jedis对象
        Jedis jedis = jedisPool.getResource();
        System.out.println(System.currentTimeMillis());
        //使用jedis操作redis服务器
        jedis.set("jedis_key", "连接池测试");
        String jedisKey = jedis.get("jedis_key");
        System.out.println(System.currentTimeMillis());
        System.out.println(jedisKey);
        //操作完毕后关闭jedis对象  连接池回收资源
        jedis.close();
        //关闭连接池对象
        jedisPool.close();

    }

}
