package to.marcus.rxtesting.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by marcus on 9/8/2015
 * For Caching
 */
public class WordStorage {

    protected static ArrayList<Word> sWordsArchive;
    private Context context;

    public WordStorage(Context context){
        this.context = context;
        sWordsArchive = new ArrayList<>();
    }

    public void saveWord(Word word){
        sWordsArchive.add(word);
    }

    public Word getWord(){
        return sWordsArchive.get(sWordsArchive.size()-1);
    }

    public String getLatestDate(){
        return sWordsArchive.get(sWordsArchive.size() -1).getDate();
    }

    public int wordCount(){
        return sWordsArchive.size();
    }
}
