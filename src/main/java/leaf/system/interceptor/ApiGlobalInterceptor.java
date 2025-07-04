package leaf.system.interceptor;

import leaf.common.Clazz;
import leaf.system.annotate.ApiInterceptor;
import leaf.system.common.Http;
import leaf.system.common.SysUser;
import leaf.common.DB;
import leaf.common.object.JSONList;
import leaf.common.object.JSONMap;
import leaf.common.util.Valid;
import leaf.system.annotate.LoginToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口全局拦截器
 */
public class ApiGlobalInterceptor implements HandlerInterceptor {
    //preHandle方法：此方法会在进入controller之前执行，返回Boolean值决定是否执行后续操作
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginToken loginTokenAnnotation = handlerMethod.getMethodAnnotation(LoginToken.class);//获取方法上面的 @LoginToken 对象
            ApiInterceptor apiInterceptor = handlerMethod.getMethodAnnotation(ApiInterceptor.class);//获取方法上面的 @ApiInterceptor 对象

            //如果该方法上面有 @LoginToken 注解
            if (loginTokenAnnotation != null) {
                String backendLoginId = SysUser.getBackendLoginId(),
                        frontendLoginId = SysUser.getFrontendLoginId();
                String permissionKey = loginTokenAnnotation.permissionKey();//获取权限键

                //如果没有前台用户或者后台用户登录
                if(backendLoginId == null) {
                    if(frontendLoginId == null) {
                        Http.write(403,JSONMap.error("接口执行失败，用户未登录"));
                        return false;
                    } else {
                        JSONMap user = DB.queryFirst("select is_disable from sys_user where user_id = '" + frontendLoginId + "'");

                        if(user == null) {
                            Http.write(403,JSONMap.error("接口执行失败，用户未登录"));
                            return false;
                        }

                        if("1".equals(user.getString("is_disable"))) {
                            SysUser.removeFrontendLoginId();
                            Http.write(403,JSONMap.error("接口执行失败，该用户已被禁用"));
                            return false;
                        }
                    }
                }

                //如果需要验证后台用户并且没有后台用户登录
                if(loginTokenAnnotation.validBackend()) {
                    if(backendLoginId == null) {
                        Http.write(403,JSONMap.error("接口执行失败，后台用户未登录"));
                        return false;
                    } else {
                        JSONMap user = DB.queryFirst("select is_disable from sys_user where user_id = '" + backendLoginId + "'");

                        if(user == null) {
                            Http.write(403,JSONMap.error("接口执行失败，用户未登录"));
                            return false;
                        }

                        if("1".equals(user.getString("is_disable"))) {
                            SysUser.removeBackendLoginId();
                            Http.write(403,JSONMap.error("接口执行失败，该用户已被禁用"));
                            return false;
                        }
                    }
                }

                //如果需要验证前台用户并且没有前台用户登录
                if(loginTokenAnnotation.validFrontend() && frontendLoginId == null) {
                    Http.write(403,JSONMap.error("接口执行失败，前台用户未登录"));
                    return false;
                }

                //接口权限验证
                if(!"".equals(permissionKey)) {
                    if(!permission(permissionKey,backendLoginId,frontendLoginId)) {
                        Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                        return false;
                    }
                }
            }

            //ApiInterceptor 注解
            if(apiInterceptor != null) {
                String[] methods = apiInterceptor.value();

                if(methods.length > 0) {
                    for(String method : methods) {
                        if(!(boolean)Clazz.invokeStaticByMethodPath(method)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    /**
     * 权限认证
     * @param key 权限键
     * @return 是否通过认证
     */
    public static boolean permission(String key) {
        String backendLoginId = SysUser.getBackendLoginId(),
                frontendLoginId = SysUser.getFrontendLoginId();
        return permission(key,backendLoginId,frontendLoginId);
    }
    /**
     * 权限认证
     * @param key 权限键
     * @param backendLoginId 前台用户代码
     * @param frontendLoginId 后台用户代码
     * @return 是否通过认证
     */
    public static boolean permission(String key,String backendLoginId,String frontendLoginId) {
        //获取用户角色
        String userRoleIdStr = "";
        JSONList userRoleList = DB.query("" +
                "select a.role_id from sys_user_role_rel a " +
                "   left join sys_role b on a.role_id = b.role_id " +
                "   left join sys_user c on a.user_id = c.user_id " +
                "where (a.user_id = '" + backendLoginId + "' or a.user_id = '" + frontendLoginId + "') and ifnull(b.is_disable,0) != 1 and ifnull(c.is_disable,0) != 1 "+
                "order by role_id");

        if(userRoleList == null) return false;

        if(userRoleList.size() > 0 && userRoleList.getMap(0).getString("role_id").equals("1")) return true;//角色代码为1的跳过权限验证

        for(int i = 0;i < userRoleList.size();i++) {
            if(i == userRoleList.size() - 1) {
                userRoleIdStr += userRoleList.getMap(i).getString("role_id");
            } else {
                userRoleIdStr += userRoleList.getMap(i).getString("role_id")+",";
            }
        }

        if(Valid.isEmpty(userRoleIdStr)) return false;

        //通过权限键和角色代码查询该用户是否有调用这个api的权限
        if(DB.queryFirst("" +
                "select a.menu_id " +
                "from sys_menu a " +
                "   left join sys_role_menu_rel b on a.menu_id = b.menu_id " +
                "where a.permission_key = '" + key + "' and b.role_id in (" + userRoleIdStr + ")") == null) {
            return false;
        }
        return true;
    }
}
