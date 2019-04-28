package txt;

import org.junit.Test;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestFindWord {

    @Test
    @SuppressWarnings("illegal-access")
    public void test_normalizeLine(){
        FindWord wordFinder = new FindWord();
        String orig = "this string has punctuation. number5 and Caps!";
        String expected = "this string has punctuation number5 and caps";

        String out = wordFinder.normalizeLine(orig);
        Assert.assertTrue(out.equals(expected));
    }

    @Test
    public void test_cleanLine(){
        FindWord wordFinder = new FindWord();
        String orig = "this string has punctuation. number5 and Caps!";
        String expected = "this string has punctuation number5 and Caps";

        String out = wordFinder.cleanLine(orig);
        Assert.assertTrue(out.equals(expected));
    }

    @Test
    public void test_countOccurances_string(){
        FindWord wordFinder = new FindWord();
        String orig = "spider-man spiderman does whatever a spider can!";
        String term = "spiderman";
        Integer expected = 2;
        Integer out = wordFinder.countOccurances(orig, term);

        Assert.assertEquals(expected, out);

    }

    @Test
    public void test_countOccurances_file() throws FileNotFoundException {
        FindWord wordFinder = new FindWord();
        InputStream fileSt = new FileInputStream("test-assets/test-file1.txt");
        String term = "42";
        Integer expected = 5;

        Integer out = wordFinder.countOccurances(fileSt, term);
        Assert.assertEquals(expected, out);
    }

}
