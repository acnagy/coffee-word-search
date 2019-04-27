package txt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FindWord {

    public Integer countOccurances(InputStream inStrFile, String term) {

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStrFile));
            int count = 0;
            String line = "";

            while ((line = fileReader.readLine()) != null){
                count += hits(line, term);
            }

            return count;

        } catch (Exception e){
            return Integer.MAX_VALUE;
        }
    }

    public Integer countOccurances(String str, String term){
        return hits(str, term);
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
