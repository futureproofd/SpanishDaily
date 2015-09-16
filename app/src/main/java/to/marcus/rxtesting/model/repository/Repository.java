package to.marcus.rxtesting.model.repository;

import java.util.ArrayList;

import rx.Observable;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.Words;

/**
 * Created by mplienegger on 9/8/2015.
 *
 */
public interface Repository {

    public void addWord(Word word);
    public void saveWords();
    public Word getWord(int position);
    public String getLatestWordDate();
    public ArrayList<Word> getWordsDataset();
    public int getDatasetSize();
    public void deleteWord();
    public void deleteWords();
    public void open();
    public void close();
}
