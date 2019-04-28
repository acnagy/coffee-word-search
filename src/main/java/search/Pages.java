package search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {

    @GetMapping("/docs")
    public String docs() {
        return "docs";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
