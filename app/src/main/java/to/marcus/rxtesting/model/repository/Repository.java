package to.marcus.rxtesting.model.repository;

import java.util.ArrayList;

import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/8/2015
 */
public interface Repository {

    void addWord(Word word);
    void addFavorite(String itemId);
    void toggleFavorite(Word word);
    void saveWords();
    void toggleHidden(String itemId);
    Word getWord(String position);
    String getLatestWordDate();
    ArrayList<Word> getWordsDataset();
    int getDatasetSize();
    void deleteWord(String itemId);
    void deleteWords();
    void removeFavorite(String itemId);
    void deleteFavorites();
    void deleteRecycled();
    void setSearched(String itemId);
    int getSearched(String itemId);
    //Preference values
    void saveWirelessPref(String key, boolean value);
    void saveNotifyPref(String key, boolean value);
    void savePullPref(String key, boolean value);
    void saveGridCntHomePref(boolean value);
    void saveGridCntFavPref(boolean value);
    void saveGridCntRecyclePref(boolean value);
    boolean getWirelessPref();
    boolean getNotifyPref();
    boolean getPullPref();
    boolean getGridCntFavPref();
    boolean getGridCntHomePref();
    boolean getGridCntRecyclePref();
    Word getLatestWord();

}
