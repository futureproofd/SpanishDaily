package to.marcus.rxtesting.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by marcus on 9/8/2015
 * For Caching
 */
public class WordStorage {

    protected static ArrayList<String> sWordsArchive;
    private Context context;

    public WordStorage(Context context){
        this.context = context;
        sWordsArchive = new ArrayList<>();
    }

    public void saveWord(String word){
        sWordsArchive.add(word);
    }

    public String query(String param){
        return null;
        //sWordsArchive.get(0).getDate   --gets the word object date
    }
}
