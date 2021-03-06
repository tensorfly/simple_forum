package chinaso.com.demo.springboot.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author fangqian
 * @date 2018/7/2 17:43
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

    public final static String SESSION_ADMIN_KEY = "admin";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/toLogin");
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/loginOut");
        addInterceptor.excludePathPatterns("/toRegister");
        addInterceptor.excludePathPatterns("/register");
        addInterceptor.excludePathPatterns("/activation/**");
        addInterceptor.excludePathPatterns("/topic/list");
        addInterceptor.excludePathPatterns("/topic/detail");
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/css/**");
        addInterceptor.excludePathPatterns("/js/**");
        addInterceptor.excludePathPatterns("/image/**");
        addInterceptor.excludePathPatterns("/code/**");
        addInterceptor.excludePathPatterns("/admin/toLogin");
        addInterceptor.excludePathPatterns("/admin/login");
        addInterceptor.excludePathPatterns("/commodity/**");
        addInterceptor.excludePathPatterns("/toForgetPasswordPage");
        addInterceptor.excludePathPatterns("/forgetPassword");
        addInterceptor.excludePathPatterns("/resetPassword/**");
        addInterceptor.excludePathPatterns("/modify/password");
        addInterceptor.excludePathPatterns("/common/**");




        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String referer = request.getHeader("referer");
            if (referer != null) {
                HttpSession session = request.getSession();
                if (session.getAttribute(SESSION_KEY) != null)
                    return true;
            }

            String requestURI = request.getRequestURI();
            if(requestURI.contains("admin") ){
                if(null != request.getSession().getAttribute(SESSION_ADMIN_KEY)){
                    return true;
                }
                    response.sendRedirect(request.getContextPath()+"/admin/toLogin");
                    return false;
                }

            // 跳转登录
            String url = "/toLogin";
            response.sendRedirect(url);
            return false;
        }
    }
}
