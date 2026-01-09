package hello.accountService.domain;

import hello.membeservice.domain.Member;
import hello.membeservice.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final MemberRepository repository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int point) throws SQLException {
        Member fromMember = repository.selectById(fromId);
        Member toMember = repository.selectById(toId);
        fromMember.setPoint(fromMember.getPoint() - point);
        toMember.setPoint(toMember.getPoint() + point);
        repository.updatePoint(fromMember);
        repository.updatePoint(toMember);
    }

    @Transactional
    public void accountTransferByAcctNo(String fromAcctNo, String receiveAcctNo, int amount) throws SQLException {
        Member fromMember = repository.selectByAcctNo(fromAcctNo);
        Member toMember = repository.selectByAcctNo(receiveAcctNo);
        log.info("fromMember.acctNo = {}, fromMember.point={}, amount={}",fromMember.getAcctNo(), fromMember.getPoint(), amount);
        fromMember.setPoint(fromMember.getPoint() - amount);
        log.info("fromMember.acctNo = {}, fromMember.point={}, amount={}",fromMember.getAcctNo(), fromMember.getPoint(), amount);
        toMember.setPoint(toMember.getPoint() + amount);
        repository.updatePoint(fromMember);
        repository.updatePoint(toMember);
    }

    public void deposit(String id, int amount) throws SQLException {
        Member findMember = repository.selectById(id);
        findMember.setPoint(findMember.getPoint() + amount);
        repository.updatePoint(findMember);
    }




}
