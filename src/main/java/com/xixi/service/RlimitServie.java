package com.xixi.service;


import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class RlimitServie {

    @Autowired
    RedissonClient redissonClient;
    //port 11101

    public boolean limit(){
        RRateLimiter test = redissonClient.getRateLimiter("test1");
        return test.trySetRate(RateType.OVERALL, 10000, 1, RateIntervalUnit.HOURS);
    }


    public boolean acquire(){
        RRateLimiter test = redissonClient.getRateLimiter("test1");
        return test.tryAcquire();
    }

}
