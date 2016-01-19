package to.marcus.rxtesting.model;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by marcus on 9/8/2015
 * Back-end For Caching
 */
public class WordStorage{
    private static final String TAG = WordStorage.class.getSimpleName();
    private ArrayList<Word> mWords;
    private ObjectSerializer mSerializer;
    private static final String WORDS_DATASET = "words.json";

    public WordStorage(Context appContext){
        mSerializer = new ObjectSerializer(appContext, WORDS_DATASET);
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
            mSerializer.saveObjects(mWords);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void deleteAllWords(){
        mWords.clear();
        saveWordsToJSON();
    }

    public void deleteWord(String itemId){
        mWords.remove(getWord(itemId));
        saveWordsToJSON();
    }

    public void hideWord(String itemId){
        getWord(itemId).setVisibility(0);
        saveWordsToJSON();
    }

    public void deleteFavorites(){
        for(Word w : mWords){
            if(w.getFavorite() == 1)
                w.setFavorite(0);
        }
    }

    public void addFavorite(String itemId){
        getWord(itemId).setFavorite(1);
        saveWordsToJSON();
    }

    public void toggleFavorite(Word word){
        for(Word dataSetWord : mWords){
            if(word.getImgUrl().equals(dataSetWord.getImgUrl())){
                if(word.getFavorite() == 0){
                    dataSetWord.setFavorite(0);
                }else{
                    dataSetWord.setFavorite(1);
                }
                saveWordsToJSON();
            }
        }
    }

    public void setHidden(int position){
        mWords.get(position).setVisibility(0);
        saveWordsToJSON();
    }

    public void setSearched(String itemId){
        getWord(itemId).setSearched(1);
    }

    public int getSearched(String itemId){
        return getWord(itemId).getSearched();
    }

    public Word getWord(String itemId){
        Word matchedWord = new Word();
        for(Word word : mWords) {
            if (word.getImgUrl().equals(itemId)) {
                matchedWord = word;
            }
        }
        return matchedWord;
    }

    public ArrayList<Word> getWordsDataSet(){
        return mWords;
    }

    public String getLatestDate(){
        if(wordCount() > 0){
            return mWords.get(mWords.size() -1).getDate();
        }else{
            return "January 1, 1970";
        }
    }

    public Word getLatestWord(){
        return mWords.get(mWords.size() -1);
    }

    public int wordCount(){
        return mWords.size();
    }

}
