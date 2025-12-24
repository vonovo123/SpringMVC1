package hello.login;

import hello.itemservicedemo.domain.item.Item;
import hello.itemservicedemo.domain.item.ItemRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final ItemRepository itemRepository;
    private final MemberRepository  memberRepository;
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");
        memberRepository.save(member);
    }
}
