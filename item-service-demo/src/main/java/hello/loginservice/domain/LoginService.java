package hello.loginservice.domain;

import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberService memberRepository;
    public Member login(String loginId, String password) throws SQLException {
        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password)).orElse(null);

    }
}
