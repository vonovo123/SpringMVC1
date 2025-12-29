package hello.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/error-page")
@Slf4j
public class ErrorPageController {
    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION =
            "jakarta.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE =
            "jakarta.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";
    public static final String ERROR_REQUEST_URI =
            "jakarta.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME =
            "jakarta.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE =
            "jakarta.servlet.error.status_code";

    @GetMapping("/ex")
    public String ex(HttpServletRequest request) {
        throw new RuntimeException("RuntimeException");
    }

    @GetMapping("/404")
    public String ErrorPage404(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.error("Page Not Found");
        printErrorLog(req);
        return "/error-page/404";
    }

    @GetMapping("/500Ex")
    public void error(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(500);
    }

    @GetMapping("/500")
    public String ErrorPage500(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.error("500 Internal Server Error");
        printErrorLog(req);
        return "/error-page/500";
    }

    private void printErrorLog(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex=", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}",
                request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}",
                request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }

}
