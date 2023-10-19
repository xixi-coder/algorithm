package com.xixi.controller;


import com.xixi.service.RlimitServie;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/limit")
@RestController
public class RlimitController {

    @Autowired
    RlimitServie rlimitServie;

    //port 11101
    @RequestMapping("/init")
    public boolean limit() {
        return rlimitServie.limit();
    }

    @RequestMapping("/acquire")
    public boolean acquire() {
        return rlimitServie.acquire();
    }

}
