package hello.typeConverter.controller;

import hello.typeConverter.type.IpPort;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/converter")
@Slf4j
public class ConverterController {

    @GetMapping
    @ResponseBody
    public String typeconvertQueryParam(@RequestParam Integer data) {
      log.info("data:{}", data);
      return "ok";
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public String typeconvertPathVariable(@PathVariable Integer userId) {
        log.info("userId:{}", userId);
        return "ok";
    }

    @GetMapping("/ip-port")
    @ResponseBody
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("ipPort PORT = " + ipPort.getPort());
        return "ok";
    }

    @GetMapping("/view")
    public String converterView(Model model) {
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
        return "typeconvert/converter-view";
    }

    @GetMapping("/edit")
    public String convertFrom(Model model) {
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        Form form = new Form(ipPort);
        model.addAttribute("form", form);
        return "typeconvert/converter-form";
    }

    @Data
    static class Form {
        private IpPort ipPort;
        public Form(IpPort ipPort) {
            this.ipPort = ipPort;
        } }
}
