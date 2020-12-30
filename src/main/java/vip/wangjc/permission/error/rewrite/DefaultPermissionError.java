package vip.wangjc.permission.error.rewrite;

import vip.wangjc.permission.error.AbstractPermissionError;
import vip.wangjc.permission.error.PermissionErrorResult;

/**
 * 默认的权限错误处理
 * @author wangjc
 * @title: DefaultPermissionError
 * @projectName wangjc-vip
 * @date 2020/12/30 - 18:16
 */
public class DefaultPermissionError extends AbstractPermissionError {

    @Override
    public Object data() {
        return PermissionErrorResult.error();
    }

    @Override
    public Object view() {
        return null;
    }
}
