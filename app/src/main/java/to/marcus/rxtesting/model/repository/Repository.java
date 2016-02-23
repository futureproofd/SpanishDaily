package to.marcus.rxtesting.model.repository;

import java.util.ArrayList;
import java.util.Map;

import rx.Observable;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.Words;

/**
 * Created by marcus on 9/8/2015
 *
 */
public interface Repository {

    void addWord(Word word);
    void addFavorite(String itemId);
    void toggleFavorite(Word word);
    void saveWords();
    void setHidden(int position);
    Word getWord(String position);
    String getLatestWordDate();
    ArrayList<Word> getWordsDataset();
    int getDatasetSize();
    void removeWord(String itemId);
    void deleteWord(String itemId);
    void deleteWords();
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
