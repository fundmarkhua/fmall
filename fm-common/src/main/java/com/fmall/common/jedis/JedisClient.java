package com.fmall.common.jedis;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/10 16:58
 */
public interface JedisClient {

    String set(String key, String value);

    String get(String key);

    Boolean exists(String key);

    Long expire(String key, int second);

    Long ttl(String key);

    Long incr(String key);

    Long decr(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String... field);

    Boolean hexists(String key, String field);

    List<String> hvals(String key);

    Long del(String key);

}
