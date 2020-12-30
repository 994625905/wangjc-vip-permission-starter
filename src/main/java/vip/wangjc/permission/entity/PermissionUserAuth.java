package vip.wangjc.permission.entity;

import java.io.Serializable;

/**
 * 权限对应的用户身份认证实体：单利模式
 * @author wangjc
 * @title: PermissionEntity
 * @projectName wangjc-vip
 * @date 2020/12/29 - 16:43
 */
public class PermissionUserAuth implements Serializable {

    private static final long serialVersionUID = -1150286529077795174L;

    public PermissionUserAuth(Object userId,Object userName){
        this.userId = userId;
        this.userName = userName;
    }

    private Object userId;

    private Object userName;

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }
}
