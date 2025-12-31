package hello.exception.resolver;

import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public @Nullable ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

        if(ex instanceof UserException) {
            log.info("UserException resolver to 400");
            String acceptHeader = request.getHeader("accept");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            if("application/json".equals(acceptHeader)){
                try {
                    HashMap<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return new ModelAndView("error/400");
            }
        }
        return null;
    }
}
