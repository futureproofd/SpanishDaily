package to.marcus.rxtesting.model.repository;

import rx.Observable;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.Words;

/**
 * Created by mplienegger on 9/8/2015.
 *
 */
public interface Repository {

    public void addWord(String word);
    //public Observable<String> getWord();
    public String getLatestWord();
    public Words getAllWords();
    public void deleteWord();
    public void deleteWords();
    public void open();
    public void close();
}
