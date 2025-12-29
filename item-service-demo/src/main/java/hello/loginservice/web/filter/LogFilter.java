package hello.loginservice.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init LogFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        log.info("REQUEST [{}][{}]",uuid,requestURI);
        chain.doFilter(req,response);
    }

    @Override
    public void destroy() {
        log.info("destroy LogFilter");
    }
}
