package hello.loginservice.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURL().toString();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("hm: {}", hm.getBean().getClass().getName());
        }
        log.info("REQUEST [{}][{}][{}]", requestUrl, uuid, request, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("modelAndView {}", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        String requestUrl = request.getRequestURL().toString();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("response [{}][{}]", logId, requestUrl);
        if(ex!=null) log.error("afterCompletion!!!", ex);
    }
}
