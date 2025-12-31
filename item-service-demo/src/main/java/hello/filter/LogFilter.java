package hello.filter;

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
        try{
            log.info("REQUEST [{}][{}][{}]",uuid,requestURI,request.getDispatcherType());
            chain.doFilter(req,response);
        }catch(Exception e){
            throw e;
        }finally {
            log.info("REQUEST [{}][{}]",uuid,request.getDispatcherType());
        }
    }

    @Override
    public void destroy() {
        log.info("destroy LogFilter");
    }
}
