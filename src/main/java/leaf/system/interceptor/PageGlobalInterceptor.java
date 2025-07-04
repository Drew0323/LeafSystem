package leaf.system.interceptor;

import leaf.system.common.SysUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 页面全局拦截器
 */
public class PageGlobalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
//        response.setCharacterEncoding("GBK");
        String uri = request.getRequestURI();
        String backendLoginId = SysUser.getBackendLoginId();

        if(uri.startsWith("/backend")) {
            if(backendLoginId == null) {
                response.sendRedirect("/backend/login");
                return false;
            }
        } else if(uri.startsWith("/log")) {
            //查看该用户的角色是有包含获取日志的权限
            if(!ApiGlobalInterceptor.permission("lyfsysPermissionKey-system_log_getLog")) {
                request.getRequestDispatcher("/error/403.html").forward(request,response);
                return false;
            }
        }
        return true;
    }
}
