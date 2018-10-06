package com.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;



    //String类型操作
    @SuppressWarnings("unchecked")
    public void stringSet(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }

    public Object stringGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //添加key,并设置过期时间（单位秒）
    @SuppressWarnings("unchecked")
    public void stringSetExpire(String key, String value,long time) {

        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    //更新过期时间
    @SuppressWarnings("unchecked")
    public void updateExpire(String key,long time) {

        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    //判断key是否存在
    @SuppressWarnings("unchecked")
    public Boolean hasKey(String key) {

        return redisTemplate.hasKey(key);
    }

    //删除key
    @SuppressWarnings("unchecked")
    public Boolean deleteKey(String key) {

        return redisTemplate.delete(key);
    }



    public void listSet() {

    }
    public void listGet() {

    }

    public void setSet() {

    }
    public void setGet() {

    }

    public void hashSet() {

    }
    public void hashGet() {

    }
//    public void test() {
//        // String读写
//        redisTemplate.delete("myStr");
//        redisTemplate.opsForValue().set("myStr", "skyLine");
//        System.out.println(redisTemplate.opsForValue().get("myStr"));
//        System.out.println("---------------");
//
//        // List读写
//        redisTemplate.delete("myList");
//        redisTemplate.opsForList().rightPush("myList", "T");
//        redisTemplate.opsForList().rightPush("myList", "L");
//        redisTemplate.opsForList().leftPush("myList", "A");
//        List<String> listCache = redisTemplate.opsForList().range(
//                "myList", 0, -1);
//        for (String s : listCache) {
//            System.out.println(s);
//        }
//        System.out.println("---------------");
//
//        // Set读写
//        redisTemplate.delete("mySet");
//        redisTemplate.opsForSet().add("mySet", "A");
//        redisTemplate.opsForSet().add("mySet", "B");
//        redisTemplate.opsForSet().add("mySet", "C");
//        Set<String> setCache = redisTemplate.opsForSet().members(
//                "mySet");
//        for (String s : setCache) {
//            System.out.println(s);
//        }
//        System.out.println("---------------");
//
//        // Hash读写
//        redisTemplate.delete("myHash");
//        redisTemplate.opsForHash().put("myHash", "BJ", "北京");
//        redisTemplate.opsForHash().put("myHash", "SH", "上海");
//        redisTemplate.opsForHash().put("myHash", "HN", "河南");
//        Map<String, String> hashCache = redisTemplate.opsForHash()
//                .entries("myHash");
//        for (Map.Entry entry : hashCache.entrySet()) {
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }
//        System.out.println("---------------");
//    }
}
