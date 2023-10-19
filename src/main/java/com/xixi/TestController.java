package com.xixi;


import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    RedissonClient redissonClient;

    //port 11101
    @RequestMapping("limit/init")
    public boolean limit(){
        RRateLimiter test = redissonClient.getRateLimiter("test");
        return test.trySetRate(RateType.PER_CLIENT, 1, 1, RateIntervalUnit.SECONDS);
    }

    @RequestMapping("limit/acquire")
    public boolean acquire(){
        RRateLimiter test = redissonClient.getRateLimiter("test");
        return test.tryAcquire();
    }

}
