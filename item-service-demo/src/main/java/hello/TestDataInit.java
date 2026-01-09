package hello;

import hello.itemservice.domain.ItemService;
import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

//@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final ItemService itemService;
    private final MemberService memberService;
    @PostConstruct
    public void init() throws SQLException {
        //itemService.saveItem(new Item("itemA", 10000, 10));
        //itemService.saveItem(new Item("itemB", 20000, 20));
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");
        memberService.save(member);
    }
}
