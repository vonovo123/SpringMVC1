package hello.loginservice.web;

import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberRepository;
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
        if( loginMember == null) return "login/home";
        model.addAttribute("member", loginMember);
        return "login/loginHome";
    }
}
