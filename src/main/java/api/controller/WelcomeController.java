package api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class WelcomeController {

    @RequestMapping("/hello")
    public String welcome(Map<String, Object> model) {
        model.put("message", "Hello");
        return "/WEB-INF/jsp/welcome.jsp";
    }

}
