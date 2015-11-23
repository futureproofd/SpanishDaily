package to.marcus.rxtesting.model.repository;

import java.util.ArrayList;
import javax.inject.Inject;
import to.marcus.rxtesting.model.AppPreferences;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.WordStorage;

/**
 * Created by marcus on 9/4/2015.
 * Inject our model (database, json list) - WordStorage
 * Inject preferences to access application-wide preferences
 * use a repository interface (observable get methods)
 *      these will require an observable to obtain data from any source (database OR network pull)

 */
public class RepositoryImpl implements Repository{

    private final WordStorage mWordStorage;
    private final AppPreferences mAppPreferences;
    private static final String TAG = RepositoryImpl.class.getSimpleName();

    @Inject
    public RepositoryImpl(WordStorage wordStorage, AppPreferences appPreferences){
        mWordStorage = wordStorage;
        mAppPreferences = appPreferences;
    }

    @Override
    public void saveWords(){
        mWordStorage.saveWordsToJSON();
    }

    @Override
    public void addWord(Word word){
        mWordStorage.saveWord(word);
    }

    @Override
    public Word getWord(String position){
        return mWordStorage.getWord(position);
    }


    @Override
    public String getLatestWordDate(){
        return mWordStorage.getLatestDate();
    }

    @Override
    public Word getLatestWord(){
        return mWordStorage.getLatestWord();
    }

    @Override
    public ArrayList<Word> getWordsDataset(){
        return mWordStorage.getWordsDataSet();
    }

    @Override
    public int getDatasetSize(){
        return mWordStorage.wordCount();
    }

    @Override
    public void deleteWord(String itemId) {
        mWordStorage.deleteWord(itemId);
    }

    @Override
    public void deleteWords() {
        mWordStorage.deleteAllWords();
    }

    @Override
    public void deleteFavorites(){
        mWordStorage.deleteFavorites();
    }

    @Override
    public void addFavorite(String itemId){
        mWordStorage.addFavorite(itemId);
    }

    @Override
    public void toggleFavorite(Word word){
        mWordStorage.toggleFavorite(word);
    }

    @Override
    public void setHidden(int position){
        mWordStorage.setHidden(position);
    }

    /*
    Preferences
    */
    @Override
    public void saveWirelessPref(String key, boolean value){
        mAppPreferences.setWirelessPref(key, value);
    }

    @Override
    public void saveNotifyPref(String key, boolean value){
        mAppPreferences.setNotifyPref(key, value);
    }

    @Override
    public void savePullPref(String key, boolean value){
        mAppPreferences.setPullPref(key, value);
    }

    @Override
    public boolean getNotifyPref(){return mAppPreferences.getNotifyPref();}

    @Override
    public boolean getPullPref(){return mAppPreferences.getPullPref();}

    @Override
    public boolean getWirelessPref(){return mAppPreferences.getWirelessPref();}
}
