package vip.wangjc.permission.cache;

import org.springframework.data.redis.core.RedisTemplate;
import vip.wangjc.permission.cache.service.PermissionCacheService;
import vip.wangjc.permission.cache.service.impl.RedisPermissionCacheServiceImpl;
import vip.wangjc.permission.entity.PermissionCacheType;

/**
 * 缓存类型的初始化
 * @author wangjc
 * @title: PermissionCacheInit
 * @projectName wangjc-vip
 * @date 2020/12/29 - 19:05
 */
public class PermissionCacheServiceBuilder {

    private final RedisTemplate redisTemplate;

    public PermissionCacheServiceBuilder(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取缓存服务
     * @return
     */
    public PermissionCacheService getCacheService(PermissionCacheType type){
        switch (type){
            case REDIS:
                return new RedisPermissionCacheServiceImpl(this.redisTemplate);
            default:
                throw new IllegalArgumentException("error cache type argument");
        }
    }

}
