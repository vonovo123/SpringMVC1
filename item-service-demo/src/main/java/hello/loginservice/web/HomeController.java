package hello.loginservice.web;

import hello.loginservice.domain.member.Member;
import hello.loginservice.domain.member.MemberRepository;
import hello.loginservice.web.argumentResolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/home")
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    @GetMapping
    public String homeLogin(@Login Member loginMember, Model model ) {
        //Object loginMember = sessionManager.getSession(request);
//        HttpSession session = request.getSession(false);
//        if(session== null) return "login/home";
//        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if( loginMember == null) return "login/home";
        model.addAttribute("member", loginMember);
        return "login/loginHome";
    }
}
