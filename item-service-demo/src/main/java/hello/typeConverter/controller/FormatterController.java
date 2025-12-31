package hello.typeConverter.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/formatter")
public class FormatterController {
    @GetMapping("/edit")
    public String edit(Model model) {
        Form form = new Form();
        form.setNumber(100000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);
        return "formatter/form";
    }
    @PostMapping("/edit")
    public String view(@ModelAttribute Form form) {
            return "formatter/view";
    }

    @Data
    static class Form {
        @NumberFormat(pattern = "###,###")
        private Integer number;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }

}
