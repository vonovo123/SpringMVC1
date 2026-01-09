package hello.loginservice.web;

import hello.membeservice.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;

class SessionManagerTest {

    @Test
    void sessionTest(){
        //create Session
        //Member 객체를 담은 Session을 만들어 세션저장소에 저장한다.
        //저장된 세션의 id를 클라이언트 응답에 cooike value로 심어준다.
        SessionManager sessionManager = new SessionManager();
        Member member = new Member();
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member,response);

        //set SessionCookie

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 요청쿠키에 담긴 sesionId로 세션 저장소를 확인해 member객체가 동일한지 확인한다.
        //inq Session
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);


        //expire Session
        //요청쿠기에 담긴 sessionId로 저장소의 세션을 제거한다.
        sessionManager.expireSession(request);
        Object expiredSession = sessionManager.getSession(request);
        assertThat(expiredSession).isNull();

    }

}