package to.marcus.rxtesting.model;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by marcus on 9/8/2015
 * Back end For Caching
 */
public class WordStorage{
    private static final String TAG = WordStorage.class.getSimpleName();
    private ArrayList<Word> mWords;
    private WordSerializer mSerializer;
    private static final String WORDS_DATASET = "words.json";

    public WordStorage(Context appContext){
        mSerializer = new WordSerializer(appContext, WORDS_DATASET);
        try{
            mWords = mSerializer.loadWords();
        }catch(Exception e){
            mWords = new ArrayList<>();
        }
    }

    public void saveWord(Word word){
        Log.i(TAG, "saving word to array");
        mWords.add(word);
    }

    public boolean saveWordsToJSON(){
        try{
            Log.i(TAG, "saving words to JSON");
            mSerializer.saveWords(mWords);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void deleteAllWords(){
        mWords.clear();
        saveWordsToJSON();
    }

    public void deleteWord(int position){
        mWords.remove(position);
        saveWordsToJSON();
    }

    public void addFavorite(int position){
        mWords.get(position).setFavorite(1);
        saveWordsToJSON();
    }

    public void setHidden(int position){
        mWords.get(position).setVisibility(0);
        saveWordsToJSON();
    }

    public Word getWord(int position){
        return mWords.get(position);
    }

    public ArrayList<Word> getWordsDataSet(){
        return mWords;
    }

    public String getLatestDate(){
        return mWords.get(mWords.size() -1).getDate();
    }

    public int wordCount(){
        return mWords.size();
    }

}
