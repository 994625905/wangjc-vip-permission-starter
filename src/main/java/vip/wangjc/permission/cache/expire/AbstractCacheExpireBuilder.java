package vip.wangjc.permission.cache.expire;

/**
 * 缓存有效时间的构建器抽象类
 * @author wangjc
 * @title: AbstractCacheExpireBuilder
 * @projectName wangjc-vip
 * @date 2020/12/29 - 20:06
 */
public abstract class AbstractCacheExpireBuilder {

    /**
     * 生成有效时间
     * @return
     */
    public abstract long expire();
}
