package com.xixi.config;

import com.alibaba.fastjson2.support.spring.data.redis.FastJsonRedisSerializer;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisConfig {

//    @Autowired
//    RedisConfig redisConfig;


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.database}")
    private Integer redisDataBase;


    @Bean
    @ConditionalOnMissingBean( name = {"redisTemplate"} )
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)  {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setDefaultSerializer(new FastJsonRedisSerializer(Object.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)  {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setDefaultSerializer(new FastJsonRedisSerializer(Object.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("redissonClient")
    public RedissonClient redisson(LettuceConnectionFactory lettuceConnectionFactory) {
        Config config = new Config();
        (config.useSingleServer().setTimeout(1000000)).setAddress("redis://"+redisHost+":"+
                        redisPort).setDatabase(redisDataBase).setPassword(redisPassword);
        RedissonClient redissonClient = Redisson.create(config);

        return redissonClient;
    }
}
