package hello.loginservice.web.interceptor;

import hello.loginservice.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 시작 {}", requestURI);
            log.info("인증 체크 대상 URL {}", requestURI);
            HttpSession session = request.getSession();
            if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                log.info("미인증 사용자 요청 {}", requestURI);
                response.sendRedirect("/login?redirectURL=" + requestURI);
                return false;
            }
        return true;
    }

}
