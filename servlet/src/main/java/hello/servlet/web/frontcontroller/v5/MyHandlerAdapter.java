package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface MyHandlerAdapter {
    /**
     * handler는 controller를 의미
     * 어댑터가 해당 contoller를 처리할 수 있는지 판단
     * */
    boolean supports(Object handler);
    /**
     * 어뎁터는 실제 handler를 호출하고, 그 결과로 modelAndView를 반환한다.
     * 실제 컨트롤러가 modelAndView를 반환하지못하면 어뎁터가 직접 생성해서라도 반환한다.
     * 이전 예제에서는 front Controller가 실제 컨트롤러를 호출했지만 이제느 어뎁터를 통해 실제 컨트롤러르 호출한다.
     * */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
