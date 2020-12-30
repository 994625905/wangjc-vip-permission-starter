package vip.wangjc.permission.auth;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vip.wangjc.permission.cache.service.PermissionCacheService;
import vip.wangjc.permission.entity.PermissionExpress;
import vip.wangjc.permission.entity.PermissionUserAuth;
import vip.wangjc.permission.register.PermissionRegister;
import vip.wangjc.permission.util.PermissionConstant;
import vip.wangjc.permission.util.PermissionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangjc
 * @title: PermissionCheckService
 * @projectName wangjc-vip
 * @date 2020/12/29 - 21:05
 */
public class PermissionCheckService {

    private final PermissionCacheService cacheService;

    public PermissionCheckService(PermissionCacheService cacheService){
        this.cacheService = cacheService;
    }


    /**
     * 获取验证的用户
     * @return
     */
    public PermissionUserAuth getPermissionUserAuth(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userSign = PermissionUtil.getCookieValue(request, PermissionConstant.cookieUserSignKey());

        /** 刷新缓存 */
        this.cacheService.expire( PermissionConstant.userSignKey(PermissionUtil.getUserId(userSign,String.class)), PermissionRegister.getExpire());

        return this.cacheService.get( PermissionConstant.userSignKey(PermissionUtil.getUserId(userSign,String.class)), PermissionUserAuth.class);
    }

    /**
     * 获取验证用户的权限列表
     * @return
     */
    public List<PermissionExpress> getPermissionExpressList(){

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userSign = PermissionUtil.getCookieValue(request, PermissionConstant.cookieUserSignKey());

        /** 刷新缓存 */
        this.cacheService.expire( PermissionConstant.userSignKey(PermissionUtil.getUserId(userSign,String.class)), PermissionRegister.getExpire());
        this.cacheService.expire( PermissionConstant.expressListKey(PermissionUtil.getUserId(userSign,String.class)), PermissionRegister.getExpire());

        String json = this.cacheService.get(PermissionConstant.expressListKey(PermissionUtil.getUserId(userSign, String.class)), String.class);
        return JSONArray.parseArray(json,PermissionExpress.class);
    }

}
