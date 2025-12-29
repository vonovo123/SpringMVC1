package hello.loginservice.web;

import hello.loginservice.domain.login.LoginForm;
import hello.loginservice.domain.login.LoginService;
import hello.loginservice.domain.member.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private  final LoginService loginService;
    private final SessionManager sessionManager;
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";

    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, @RequestParam(defaultValue="/home") String redirectURL, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", loginMember );
        if(loginMember==null){
            bindingResult.reject("loginFail", "로그인 ID 또는 Password가 맞지 않습니다,");
            return "login/loginForm";
        }
        //response.addCookie(new Cookie("memberId", String.valueOf(loginMember.getId())));
        //sessionManager.createSession(loginMember, response);
        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        log.info("redirect to {}", redirectURL);
        return "redirect:" + redirectURL;
    }
    @PostMapping("/logout")
    public String logout( HttpServletRequest request) {
        //sessionManager.expireSession(request);
        HttpSession session = request.getSession(false);
        if(session!=null) session.invalidate();
        return "redirect:/home";
    }

    private void expireCookie(HttpServletResponse response,  String cookieName) {
        Cookie cookie = new Cookie(cookieName,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
