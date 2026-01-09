package hello.membeservice.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public Member save(Member member) throws SQLException {
        return repository.insertMember(member);
    }

    public Optional<Member> findByLoginId(String loginId) throws SQLException {
        return repository.selectAll().stream().filter(member -> member.getLoginId().equals(loginId)).findFirst();
    }

}
