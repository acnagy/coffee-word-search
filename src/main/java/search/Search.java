package search;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;


import txt.FindWord;
import exceptions.FileContentException;
import exceptions.FileStreamException;


@RestController
public class Search {

    @PostMapping("/file")
    @RequestMapping(path = "/file", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> file(@ModelAttribute("file") MultipartFile file, @RequestParam String term) {
        try {
            InputStream fileInStr = file.getInputStream();
            FindWord wordFinder = new FindWord();
            Integer count = wordFinder.countOccurances(fileInStr, term);

            if (count == Integer.MAX_VALUE) throw new FileContentException();

            return new ResponseEntity<Results>(
                    new Results(term, count, file.getOriginalFilename()),
                    HttpStatus.OK);

        } catch (Exception e){
                throw new FileStreamException();
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