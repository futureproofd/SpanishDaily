package to.marcus.rxtesting.model;

import java.util.ArrayList;

/**
 * Created by marcus on 9/4/2015
 */

public class Words {

    private ArrayList<Word> words = new ArrayList<Word>();

    public void setWords(ArrayList<Word> words){
        this.words = words;
    }

    public ArrayList<Word> getWordsArray(){
        return words;
    }

    public void addWord(Word word){words.add(word);}
}
