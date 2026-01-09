package hello.membeservice.web;

import hello.membeservice.domain.BankCode;
import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member){
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("member") Member member,  BindingResult bindingResult) throws SQLException {
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";
        }
        service.save(member);
        return "redirect:/home";
    }
    @ModelAttribute("bankCodes")
    public BankCode[] deliveryCodes(){return BankCode.values();}
}
