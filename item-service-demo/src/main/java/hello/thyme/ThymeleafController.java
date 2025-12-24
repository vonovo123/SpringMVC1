package hello.thyme;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/thyme")
public class ThymeleafController {
    @GetMapping
    public String thyme(){
        return "/thyme/thyme";
    }

    @GetMapping("text-basic")
    public String textBasic(Model model){
        log.info("thyme/textBasic");
        model.addAttribute("data", "<b>Hello Spring</b>");
        return  "thyme/text-basic";
    }
    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data", "Hello <b>Spring</b>!!!");
        return  "thyme/text-unescaped";
    }

    @GetMapping("variable")
    public String variable(Model model){
        User userA = new User("userA", 10);
        User userB = new User("userB", 10);
        ArrayList<User> users = new ArrayList();
        users.add(userA);
        users.add(userB);
        HashMap<String, User> userMap = new HashMap<>();
        userMap.put("userA", userA);
        userMap.put("userB", userB);
        model.addAttribute("user", userA);
        model.addAttribute("users", users);
        model.addAttribute("userMap", userMap);
        System.out.println("variable");
        return "thyme/variable";
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }

    @GetMapping("basic-objects")
    public String basicObjects(HttpSession session, Model model, HttpServletResponse response, HttpServletRequest request){
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request",request);
        model.addAttribute("response",response);
        model.addAttribute("servletContext",request.getServletContext());
        return "thyme/basic-objects";
    }

    @Component("HelloBean")
    public class HelloBean {
        public String hello(String data){
            return "hello " + data;
        }
    }

    @GetMapping("date")
    public String date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "thyme/date";
    }

    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return  "thyme/link";
    }
    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data", "spring");
        return "thyme/literal";
    }
    @GetMapping("/attribute")
    public String attribute(Model model){
        return "thyme/attribute";
    }
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "thyme/each";
    }

    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "thyme/condition";
    }
    @GetMapping("/block")
    public String block(Model model) {
        addUsers(model);
        return "thyme/block";
    }

    @GetMapping("/javascript")
    public String javascript(Model model) {
        model.addAttribute("user", new User("userA", 10));
        addUsers(model);
        return "thyme/javascript";
    }

    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);
    }



}


