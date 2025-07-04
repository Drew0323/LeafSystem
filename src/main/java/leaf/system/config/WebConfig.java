package leaf.system.config;

import leaf.common.DB;
import leaf.common.Log;
import leaf.common.object.Redis;
import leaf.system.annotate.EnableJDBC;
import leaf.system.annotate.EnableRedis;
import leaf.system.annotate.LeafConfigPath;
import leaf.system.interceptor.PageGlobalInterceptor;
import leaf.common.Config;
import leaf.system.interceptor.ApiGlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;

/**
 * web配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 扫描有 @ServerEndpoint 注解的 Bean
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 拦截器配置
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //接口全局拦截器
        registry.addInterceptor(new ApiGlobalInterceptor())
                .addPathPatterns("/api/**")//接口
                .addPathPatterns("/system/api/**");//系统接口
        //页面全局拦截器
        registry.addInterceptor(new PageGlobalInterceptor())
                .addPathPatterns("/backend/index**")
                .addPathPatterns("/backend/pages/**")//拦截后台页面
                .addPathPatterns("/log/**");//拦截日志页面检查登录
    }

    /**
     * Cross-Origin Resource Sharing跨源资源共享配置
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if("true".equals(Config.Properties.getProperty("system.global.cors"))) {
            registry.addMapping("/**")
                    .allowCredentials(true)//是否发送Cookie
                    .allowedOrigins("*")//放行哪些原始域
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*");
            System.out.println(Log.info("系统开启全局跨域"));
        } else {
            System.out.println(Log.info("系统未开启全局跨域"));
        }
    }

    /**
     * 程序启动后最先执行的方法
     */
    @PostConstruct
    public void init() {
        // 获取启动类
        String mainApplicationClassName = System.getProperty("sun.java.command");
        Class<?> mainClass;

        try {
            mainClass = Class.forName(mainApplicationClassName.split(" ")[0]);

            // 检查启动类是否有LeafConfigPath注解
            if (mainClass.isAnnotationPresent(LeafConfigPath.class)) {
                System.out.println(Log.info("设置LeafConfig配置文件路径"));
                Config.setConfigPath(mainClass.getAnnotation(LeafConfigPath.class).value());
            }

            // 检查启动类是否有EnableJDBC注解
            if (mainClass.isAnnotationPresent(EnableJDBC.class)) {
                DB.config();
                System.out.println(Log.info("JDBC配置"));
            }

            // 检查启动类是否有EnableRedis注解
            if (mainClass.isAnnotationPresent(EnableRedis.class)) {
                Redis.config();
                System.out.println(Log.info("Redis配置"));
            }
        } catch (ClassNotFoundException e) {
            Log.write("Error", "获取启动类失败");
        }
    }
}
