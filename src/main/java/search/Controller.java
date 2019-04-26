package search;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import txt.FindWord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@RestController
public class Controller {

    @RequestMapping("/")
    public String index() {
        return "Hi! 42 :)\n";
    }

    @RequestMapping(path = "/file", method = RequestMethod.POST)
    public String file(@ModelAttribute("file") MultipartFile file, @RequestParam String term) {
        FindWord finder = new FindWord();
        try {
            InputStream fileInStr = file.getInputStream();
            return finder.countOccurances(fileInStr, term);

        } catch (IOException e){
            return "There was a read/write issue creating the file input stream\n";

        } catch (Exception e) {
            return "There was a strange exception\n";
        }
    }

    @RequestMapping(path = "/string", method = RequestMethod.POST)
    public String string(@RequestParam String string, @RequestParam String term) {
        FindWord finder = new FindWord();
        return finder.countOccurances(string, term);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return args -> {
            System.out.println("some boot beans: ");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

}