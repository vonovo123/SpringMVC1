package hello.accountService.web;

import hello.accountService.domain.AccountService;
import hello.accountService.domain.MasterAccountInfoConst;
import hello.loginservice.web.SessionConst;
import hello.loginservice.web.argumentResolver.Login;
import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberService;
import hello.accountService.domain.Transfer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

import static hello.accountService.domain.MasterAccountInfoConst.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final MemberService memberService;
    @GetMapping("/transfer")
    public String transferForm(@Login Member loginMember, @ModelAttribute("transfer") Transfer transfer, Model model){
        model.addAttribute("sendMember", loginMember);
        return "account/transferForm";
    }

    @PostMapping("/transfer")
    public String transfer(@Login Member sendMember, @ModelAttribute("transfer") Transfer transfer, BindingResult bindingResult, HttpServletRequest request) throws SQLException {
        if(bindingResult.hasErrors()){
            return  "account/transferForm";
        }
        log.info("sendAcctNo = {}, receiveAcctNo = {}", sendMember.getAcctNo(),transfer.getReceiveAcctNo());
        accountService.accountTransferByAcctNo(sendMember.getAcctNo(), transfer.getReceiveAcctNo(),transfer.getAmount());
        Member loginMember = memberService.findByLoginId(sendMember.getLoginId()).get();
        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/home";
    }

    @GetMapping("/deposit")
    public String depositForm(@Login Member loginMember, @ModelAttribute("transfer") Transfer transfer, Model model){
        transfer.setReceiveMemberName(MASTER_NAME);
        transfer.setReceiveAcctNo(MASTER_ACCT_NO);
        transfer.setReceiveAcctBankCD(MASTER_BANK_CD);
        model.addAttribute("sendMember", loginMember);
        return "account/depositForm";
    }

    @PostMapping("/deposit")
    public String deposit(@Login Member sendMember, @ModelAttribute("transfer") Transfer transfer, BindingResult bindingResult, HttpServletRequest request) throws SQLException {
        if(bindingResult.hasErrors()){
            return  "account/depositForm";
        }
        accountService.deposit(sendMember.getId(),transfer.getAmount());
        Member loginMember = memberService.findByLoginId(sendMember.getLoginId()).get();
        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/home";
    }
}
