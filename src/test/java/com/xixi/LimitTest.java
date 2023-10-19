package com.xixi;

import com.xixi.service.RlimitServie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LimitTest {

    @Autowired
    RlimitServie rlimitServie;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void limit() {
        rlimitServie.limit();
    }

    @Test
    public void acquire() {
        rlimitServie.acquire();
    }

    @Test
    public void get() {
//      zset  {test}:permits  随机字符串 score 请求时间
        //
        Set<String> members = redisTemplate.opsForZSet().range("{test}:permits", 0, -1);
        System.out.println(members);
    }


}
