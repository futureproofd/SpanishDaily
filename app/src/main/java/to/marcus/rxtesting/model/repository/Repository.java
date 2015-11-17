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

    public void addWord(Word word);
    public void addFavorite(int position);
    public void toggleFavorite(Word word);
    public void saveWords();
    public void setHidden(int position);
    public Word getWord(int position);
    public String getLatestWordDate();
    public ArrayList<Word> getWordsDataset(int filterCondition);
    public int getDatasetSize();
    public void deleteWord(int position);
    public void deleteWords();
    //Preference values
    public void saveWirelessPref(String key, boolean value);
    public void saveNotifyPref(String key, boolean value);
    public void savePullPref(String key, boolean value);
    public boolean getWirelessPref();
    public boolean getNotifyPref();
    public boolean getPullPref();
    public Word getLatestWord();

}
