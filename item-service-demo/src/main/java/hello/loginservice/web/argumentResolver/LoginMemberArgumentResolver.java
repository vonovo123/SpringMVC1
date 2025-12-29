package hello.loginservice.web.argumentResolver;

import hello.loginservice.domain.member.Member;
import hello.loginservice.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter : {}", parameter);
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean assignableFrom = Member.class.isAssignableFrom(parameter.getParameterType());
        return hasParameterAnnotation && assignableFrom;
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument : {}", parameter);
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if(session== null) return null;
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
