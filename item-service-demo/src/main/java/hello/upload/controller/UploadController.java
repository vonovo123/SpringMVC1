package hello.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping("/upload")
@Slf4j
public class UploadController {
    @GetMapping
    public String upload() {
        return "/upload/index";
    }
    @GetMapping("/servlet/v1")
    public String newFile() {
        return "/upload/form";
    }
    @PostMapping("/servlet/v1")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);
        Collection<Part> parts = request.getParts();
        log.info("parts={}", parts);
        return "/upload/form";
    }


}
