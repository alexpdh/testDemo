package com.pdh.test.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * java使用jedis操作redis
 *
 * @auther:pengdh
 * @create:2017-04-18 14:54
 */
public class TestRedis {
    private Jedis jedis;

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setup() {
        // 连接 redis 服务器
        jedis = new Jedis("127.0.0.1", 6379);
    }

    /**
     * redis 操作字符串
     */
    public void testString() {
        // 添加数据
        jedis.set("name", "zhangsan");
        System.out.println(jedis.get("name"));

        // 拼接数据
        jedis.append("name", " hello!");
        System.out.println(jedis.get("name"));

        // 删除指定的键
        jedis.del("name");
        System.out.println(jedis.get("name"));

        jedis.mset("name", "lisi", "age", "23", "gender", "girl");
        jedis.incr("age");
        System.out.println(jedis.get("name") + ":" + jedis.get("age") + ":" + jedis.get("gender"));
    }

    /**
     * redis 操作 map
     */
    public void testMap() {
        // 添加数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "wangwu");
        map.put("age", "21");
        map.put("gender", "female");
        jedis.hmset("user", map);

        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name", "age", "gender");
        System.out.println(rsmap);

        // 删除map中指定的键值
        jedis.hdel("user", "gender");
        // 因为删除了，所以返回的是null
        System.out.println(jedis.hmget("user", "gender"));
        // 返回key为user的键中存放的值的个数2
        System.out.println(jedis.hlen("user"));
        //是否存在key为user的记录 返回true
        System.out.println(jedis.hexists("user", "name"));
        //返回map对象中的所有key
        System.out.println(jedis.hkeys("user"));
        //返回map对象中的所有value
        System.out.println(jedis.hvals("user"));

        Iterator<String> t = jedis.hkeys("user").iterator();
        while (t.hasNext()) {
            String key = t.next();
            System.out.println(key + ":" + jedis.hget("user", key));
        }
    }
    /**
     *
     * FunName: testList
     * @Function: redis 操作 list
     */
    public void testList() {
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        // 添加数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "spingMVC");
        jedis.lpush("java framework", "mybatis");
        // 再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "spingMVC");
        jedis.rpush("java framework", "mybatis");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * redis 操作 set
     */
    public void testSet() {
        // 添加
        jedis.sadd("person", "zhangsan");
        jedis.sadd("person", "lisi");
        jedis.sadd("person", "wangwu");
        jedis.sadd("person", "who");

        // 移除集合中的一个或多个成员元素，不存在的成员元素会被忽略
        jedis.srem("person", "who");
        // 返回集合中元素的数量
        System.out.println(jedis.scard("person"));
        // 判断成员元素是否是集合的成员
        System.out.println(jedis.sismember("person", "zhaoliu"));
        // 返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
        System.out.println(jedis.smembers("person"));
        // 返回集合中的一个随机元素。
        System.out.println(jedis.srandmember("person"));
    }

    /**
     * redis 操作 sorted set
     */
    public void testZSet() {
        jedis.del("user");
        jedis.zadd("user", 1, "zhangsan");
        jedis.zadd("user", 2, "lisi");
        jedis.zadd("user", 3, "wangwu");
        jedis.zadd("user", 4, "zhaoliu");

        // 计算集合中元素的数量
        System.out.println(jedis.zcard("user"));
        // 返回有序集中，指定区间内的成员。
        System.out.println(jedis.zrange("user", 0, -1));
        // 返回有序集中，成员的分数值
        System.out.println(jedis.zscore("user", "wangwu"));
    }

    // 客户端测试
    public static void main(String[] args) {
        TestRedis redis = new TestRedis();
        redis.setup();

        // redis 操作字符串
//        redis.testString();

        // redis 操作 map
//        redis.testMap();

        // redis 操作 list
//        redis.testList();

        // redis 操作 set
//        redis.testSet();

        // redis 操作 sorted set
        redis.testZSet();
    }
}
