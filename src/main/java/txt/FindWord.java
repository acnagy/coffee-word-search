package txt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FindWord {

    public Integer countOccurances(InputStream inStrFile, String term) {

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStrFile));
            int count = 0;
            term = normalizeLine(term);
            String line = "";

            while ((line = fileReader.readLine()) != null){
                line = normalizeLine(line);
                count += hits(line, term);
            }

            return count;

        } catch (Exception e){
            return Integer.MAX_VALUE;
        }
    }

    public Integer countOccurances(String str, String term){
        str = normalizeLine(str);
        term = normalizeLine(term);
        return hits(str, term);
    }

    public String normalizeLine(String line) {
        line = cleanLine(line);
        line = line.toLowerCase();
        return line;
    }

    public String cleanLine(String line) {
        line = line.replaceAll("[^a-zA-Z0-9\\s+]", "");
        return line;
    }

    private int hits(String str, String term){
        if (str == null || str.equals("") || term.equals("")){
            return 0;
        }

        int count = 0;
        String [] words = str.split("\\s+");
        for (String word : words){
            if (word.equals(term)) {
                count++;
            }
        }

        return count;
    }
}
