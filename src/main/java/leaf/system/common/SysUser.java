package leaf.system.common;

import leaf.common.DB;
import leaf.common.net.Servlet;
import leaf.common.object.JSONList;
import leaf.common.util.DateTime;
import leaf.common.util.Lock;

import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户
 */
public class SysUser {
    /**
     * 设置前台登录
     * @param userId 用户代码
     */
    public static void setFrontendLoginId(String userId,int duration) {
        HttpServletResponse response = Http.response();
        //lyfsys_frontend_用户代码_当前时间（202311210955）_过期时间的时间戳（秒级）
        if(duration == -1) {
            Servlet.setCookie(response,"UserToken",
                    Lock.aesEncrypt("lyfsys_frontend_"+userId+"_"+DateTime.now("yyyyMMddHHmmss")+"_"+
                            (System.currentTimeMillis()/1000 + 86400)),duration);
        } else {
            Servlet.setCookie(response,"UserToken",
                    Lock.aesEncrypt("lyfsys_frontend_"+userId+"_"+DateTime.now("yyyyMMddHHmmss")+"_"+
                            (System.currentTimeMillis()/1000 + duration)),duration);
        }
    }
    /**
     * 获取前台登录
     * @return 登录用户代码
     */
    public static String getFrontendLoginId() {
        String userToken = Servlet.getCookie(Http.request(), "UserToken");

        if(userToken == null) {
            return null;
        }

        userToken = Lock.aesDncrypt(userToken);

        if(userToken == null) {
            return null;
        }

        String[] split = userToken.split("_");

        if(split.length != 5 || !"lyfsys".equals(split[0]) || !"frontend".equals(split[1])) {
            return null;
        }

        try {
            if(System.currentTimeMillis()/1000 > Long.parseLong(split[4])) return null;
        } catch(Exception e) {
            return null;
        }
        return split[2];
    }
    /**
     * 移除前台登录
     */
    public static void removeFrontendLoginId() {
        Servlet.clearCookie(Http.response(),"UserToken");
    }
    /**
     * 设置后台登录
     * @param userId 用户代码
     * @param duration 有效时间
     */
    public static void setBackendLoginId(String userId,int duration) {
        HttpServletResponse response = Http.response();
        //lyfsys_backend_用户代码_当前时间（202311210955）_过期时间的时间戳（秒级）

        if(duration == -1) {
            Servlet.setCookie(response,"AdminToken",
                    Lock.aesEncrypt("lyfsys_backend_"+userId+"_"+DateTime.now("yyyyMMddHHmmss")+
                            "_"+(System.currentTimeMillis()/1000 + 86400)),duration);
        } else {
            Servlet.setCookie(response,"AdminToken",
                    Lock.aesEncrypt("lyfsys_backend_"+userId+"_"+DateTime.now("yyyyMMddHHmmss")+
                            "_"+(System.currentTimeMillis()/1000 + duration)),duration);
        }
    }
    /**
     * 获取后台登录
     * @return 登录用户代码
     */
    public static String getBackendLoginId() {
        String adminToken = Servlet.getCookie(Http.request(), "AdminToken");

        if(adminToken == null) {
            return null;
        }

        adminToken = Lock.aesDncrypt(adminToken);

        if(adminToken == null) {
            return null;
        }

        String[] split = adminToken.split("_");

        if(split.length != 5 || !"lyfsys".equals(split[0]) || !"backend".equals(split[1])) {
            return null;
        }

        try {
            if(System.currentTimeMillis()/1000 > Long.parseLong(split[4])) {
                return null;
            }
        } catch(Exception e) {
            return null;
        }
        return split[2];
    }
    /**
     * 移除后台登录
     */
    public static void removeBackendLoginId() {
        Servlet.clearCookie(Http.response(),"AdminToken");
    }
    /**
     * 获取用户角色代码字符串
     */
    public static String getUserRoleIdStr() {
        String userRoleIdStr = "",frontendLoginId = SysUser.getFrontendLoginId(),backendLoginId = SysUser.getBackendLoginId();
        JSONList userRoleList = DB.query("" +
                "select a.role_id from sys_user_role_rel a " +
                "left join sys_role b on a.role_id = b.role_id " +
                "where a.user_id = '" + backendLoginId + "' or a.user_id = '" + frontendLoginId + "' and b.is_disable != 1 "+
                "order by role_id");

        if(userRoleList == null) return "";

        for(int i = 0;i < userRoleList.size();i++) {
            if(i == userRoleList.size() - 1) {
                userRoleIdStr += userRoleList.getMap(i).getString("role_id");
            } else {
                userRoleIdStr += userRoleList.getMap(i).getString("role_id")+",";
            }
        }
        return userRoleIdStr;
    }
}
