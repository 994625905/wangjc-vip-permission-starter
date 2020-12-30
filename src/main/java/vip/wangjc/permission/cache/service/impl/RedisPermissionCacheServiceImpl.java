package vip.wangjc.permission.cache.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import vip.wangjc.permission.cache.service.PermissionCacheService;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjc
 * @title: RedisPermissionCacheServiceImpl
 * @projectName wangjc-vip
 * @date 2020/12/29 - 19:20
 */
public class RedisPermissionCacheServiceImpl implements PermissionCacheService {

    private final RedisTemplate redisTemplate;

    public RedisPermissionCacheServiceImpl(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, Object value, long expire) {
        this.redisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
    }

    @Override
    public void expire(String key, long expire) {
        this.redisTemplate.expire(key,expire,TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) this.redisTemplate.opsForValue().get(key);
    }

}
