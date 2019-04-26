package txt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FindWord {

    public String countOccurances(InputStream inStrFile, String term) {

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStrFile));
            int count = 0;
            String line = "";

            while ((line = fileReader.readLine()) != null){
                count += hits(line, term);
            }

            return term + ": " + String.valueOf(count) + "\n";

        } catch (IOException e){
            return "There was a file read/write issue counting the occurances of " + term + ".\n";

        } catch (Exception e) {
            return "There was an exception counting the occurances of " + term + ".\n";
        }
    }

    public String countOccurances(String str, String term){
        int count = hits(str, term);
        return term + ": " + String.valueOf(count) + "\n";
    }

    public String normalizeLine(String line) {
        line = line.replaceAll("[^a-zA-Z0-9\\s+]", "").toLowerCase();
        return line;
    }

    private int hits(String str, String term){
        if (str == null || str.equals("") || term.equals("")){
            return 0;
        }

        int count = 0;
        str = normalizeLine(str);
        term = term.replaceAll("\\s+","").toLowerCase();

        String [] words = str.split("\\s+");
        for (String word : words){
            if (word.equals(term)) {
                count++;
            }
        }

        return count;
    }
}
