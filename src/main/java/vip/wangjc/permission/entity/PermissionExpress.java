package vip.wangjc.permission.entity;

import java.io.Serializable;

/**
 * 权限表达式
 * @author wangjc
 * @title: PermissionExpress
 * @projectName wangjc-vip
 * @date 2020/12/29 - 17:39
 */
public class PermissionExpress implements Serializable {

    private static final long serialVersionUID = -6457056720328413568L;

    /**
     * 表达式
     */
    private String express;

    /**
     * 对应的请求链接
     */
    private String requestUrl;

    public PermissionExpress(String express, String requestUrl){
        this.express = express;
        this.requestUrl = requestUrl;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
