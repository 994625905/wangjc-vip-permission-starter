package vip.wangjc.permission.cache.expire.rewrite;

import vip.wangjc.permission.cache.expire.AbstractCacheExpireBuilder;

/**
 * 默认的缓存有效时间的构建器
 * @author wangjc
 * @title: DefaultCacheExpireBuilder
 * @projectName wangjc-vip
 * @date 2020/12/29 - 20:09
 */
public class DefaultCacheExpireBuilder extends AbstractCacheExpireBuilder {

    private static final long min = 12 * 60 * 60; // 默认权限的有效时间：12小时

    @Override
    public long expire() {
        return min;
    }
}
