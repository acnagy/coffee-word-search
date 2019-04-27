package search;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import txt.FindWord;

import java.io.InputStream;

@RestController
public class Search {

    @GetMapping("/")
    @ResponseBody
    public Results index() {
        return new Results(null, 1, "Hi! Welcome to the app :)");
    }

    @PostMapping("/file")
    @RequestMapping(path = "/file", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> file(@ModelAttribute("file") MultipartFile file, @RequestParam String term) {
        try {
            InputStream fileInStr = file.getInputStream();
            FindWord wordFinder = new FindWord();
            Integer count = wordFinder.countOccurances(fileInStr, term);

            if (count == Integer.MAX_VALUE){
                return new ResponseEntity<String>(
                        "there was a read/write issue with the file",
                        HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<Results>(
                    new Results(term, count, file.getOriginalFilename()),
                    HttpStatus.OK);

        } catch (Exception e){
                return new ResponseEntity<String>(
                        "there was an issue with the file input stream",
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/string")
    @ResponseBody
    public ResponseEntity<?> string(@RequestParam String string, @RequestParam String term) {
        FindWord wordFinder = new FindWord();
        Integer count = wordFinder.countOccurances(string, term);
        return new ResponseEntity<Results>(new Results(term, count, string), HttpStatus.OK);
    }

}