package search.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {

    @GetMapping("/docs")
    public String docs() {
        return "docs";
    }

    @GetMapping("/resources")
    public String resources(){
        return "resources";
    }

    @GetMapping("/")
    public String index() { return "index"; }
}
