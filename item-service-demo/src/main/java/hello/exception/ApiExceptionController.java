package hello.exception;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import hello.loginservice.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class ApiExceptionController {


    @GetMapping("/api/members/{id}")
    public MemberDTO getMember(@PathVariable String id){
        if("ex".equals(id)){
            throw new RuntimeException("wrong user");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if(id.equals("user-ex")){
            throw new UserException("User error");
        }
        if(id.equals("view")){
            throw new ViewException("View Error");
        }
        return new MemberDTO(id, "hello " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1(){
        log.info("response-status-ex1");
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2(){
        log.info("response-status-ex2");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new
                IllegalArgumentException());
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }

    @Data
    @AllArgsConstructor
    private class MemberDTO {
        private String id;
        private String name;
    }
}
