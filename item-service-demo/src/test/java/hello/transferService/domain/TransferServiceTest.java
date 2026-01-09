package hello.transferService.domain;

import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberRepository;
import hello.membeservice.domain.MemberService;
import hello.accountService.domain.TransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TransferServiceTest {


    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    TransferService transferService;

    @TestConfiguration
    static class TestConfig {
        private final DataSource dataSource;;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
        @Bean
        MemberRepository memberRepository() {
            return new MemberRepository(dataSource);
        }
        @Bean
        MemberService memberService() {
            return new MemberService(memberRepository());
        }
        @Bean
        TransferService transferService() {return  new TransferService(memberRepository());}
    }

    @Test
    @DisplayName("정상이체")
    void transferSuccess() throws SQLException {

        Member savedFromMember = memberService.save(new Member("test1","tester1","test1!", 10000));
        Member savedToMember = memberService.save(new Member("test2","test2","test2!", 10000));
        transferService.accountTransfer(savedFromMember.getId(),savedToMember.getId(), 1000);
        Member updatedFromMember = memberService.findByLoginId(savedFromMember.getLoginId()).get();
        Member updatedToMember = memberService.findByLoginId(savedToMember.getLoginId()).get();
        assertThat(updatedFromMember.getMoney()).isEqualTo(9000);
        assertThat(updatedToMember.getMoney()).isEqualTo(11000);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void transferFail() throws SQLException {
        Member savedFromMember = memberService.save(new Member("test1","tester1","test1!", 10000));
        Member savedToMember = memberService.save(new Member("test2","test2","test2!", 10000));
        assertThatThrownBy(() -> transferService.accountTransfer(savedFromMember.getId(),savedToMember.getId(), 1000));
        Member updatedFromMember = memberService.findByLoginId(savedFromMember.getLoginId()).get();
        Member updatedToMember = memberService.findByLoginId(savedToMember.getLoginId()).get();
        assertThat(updatedFromMember.getMoney()).isEqualTo(9000);
        assertThat(updatedToMember.getMoney()).isEqualTo(11000);
    }
}
