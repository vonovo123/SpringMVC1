package hello.loginservice.domain.member;

import com.zaxxer.hikari.HikariDataSource;
import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {
    MemberRepository memberRepository;
    @BeforeEach
    void setUp() throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        memberRepository = new MemberRepository(dataSource);
    }
    @Test
    void crud() throws SQLException {
        memberRepository.removeAll();
        //insert
        Member member = new Member("test4","tester4","test4!", 10000);
        Member saveMember = memberRepository.save(member);
        assertThat(saveMember.getId()).isEqualTo(member.getId());

        //findById
        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);

        //updatePassword
        member.setPassword("test4@");
        memberRepository.updatePassword(member);
        findMember = memberRepository.findById(member.getId());
        assertThat(findMember.getPassword()).isEqualTo(member.getPassword());

        //removeMember
        memberRepository.removeMember(member.getId());
        assertThatThrownBy(() -> memberRepository.findById(member.getId())).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void selectAll() throws SQLException {
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(6);
    }
}