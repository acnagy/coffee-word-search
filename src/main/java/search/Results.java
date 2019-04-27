package search;

public class Results {
    private final String term;
    private final Integer count;
    private final String input;

    public Results(){
        term = input = null;
        count = null;
    }
    public Results(String term, Integer count, String input){
        this.term = term;
        this.count = count;
        this.input = input;
    }

    public String getTerm(){
        return term;
    }

    public Integer getCount(){
        return count;
    }

    public String getInput(){
        return input;
    }
}
