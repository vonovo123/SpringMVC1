package hello.loginservice.domain.member;


import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberRepository;
import hello.membeservice.domain.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
public class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
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
    }
}
