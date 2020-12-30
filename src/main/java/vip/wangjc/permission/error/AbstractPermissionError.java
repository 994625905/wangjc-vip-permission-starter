package vip.wangjc.permission.error;

import vip.wangjc.permission.annotation.Permission;

/**
 * 权限的错误处理：抽象类
 * @author wangjc
 * @title: AbstractPermissionError
 * @projectName wangjc-vip
 * @date 2020/12/30 - 17:55
 */
public abstract class AbstractPermissionError {

    /**
     * 错误内容：数据
     * @return
     */
    public abstract Object data();

    /**
     * 错误内容：页面
     * @return
     */
    public abstract Object view();

    /**
     * 判断返回值类型
     * @param permission
     * @return
     */
    public Object error(Permission permission){
        switch (permission.request()){
            case DATA:
                return this.data();
            case VIEW:
                return this.view();
            default:
                return PermissionErrorResult.error("AbstractPermissionError decide error!please check it");
        }
    }

}
