package com.crawler.activator;

import com.crawler.entity.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class RedisActivator {

    @Value("${redis.url.key.name}")
    private String redisUrlKeyName;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisActivator(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean exists(@Payload Request request) {
        redisTemplate.opsForZSet().removeRange(redisUrlKeyName, 0 , -1);
        return redisTemplate.opsForZSet().rank(redisUrlKeyName, request.getLink()) == null;
    }

    public void push(@Payload Request request) {
        if (exists(request))
            redisTemplate.opsForZSet().add(redisUrlKeyName, request.getLink(), request.getScore());
    }

    public Request oldest() {
        Set<Object> urls = redisTemplate.opsForZSet().range(redisUrlKeyName, 0, 0);
        return urls.isEmpty() ? null : (Request) urls.iterator().next();
    }
}
