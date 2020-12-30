package vip.wangjc.permission.cache.service;

/**
 * 权限缓存的接口
 * @author wangjc
 * @title: PermissionCacheService
 * @projectName wangjc-vip
 * @date 2020/12/29 - 19:15
 */
public interface PermissionCacheService {

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 设置缓存
     * @param key
     * @param value
     * @param expire：过期时间,精确到秒
     */
    void set(String key, Object value, long expire);

    /**
     * 刷新过期
     * @param key
     * @param expire
     */
    void expire(String key, long expire);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取缓存
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> clazz);

}
