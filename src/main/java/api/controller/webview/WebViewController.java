package api.controller.webview;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/webview")
public class WebViewController {

    @RequestMapping("/hello")
    public String welcome(Map<String, Object> model) {
        model.put("message", "Hello WebView");
        return "webview/hello";
    }

}
