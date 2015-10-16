package to.marcus.rxtesting.model.repository;

import java.util.ArrayList;
import javax.inject.Inject;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.WordStorage;

/**
 * Created by marcus on 9/4/2015.
 * Inject our model (database, json list) - WordStorage
 * use a repository interface (observable get methods)
 *      these will require an observable to obtain data from any source (database OR network pull)

 */
public class RepositoryImpl implements Repository{

    private final WordStorage mWordStorage;

    @Inject
    public RepositoryImpl(WordStorage wordStorage){
        mWordStorage = wordStorage;
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
    public Word getWord(int position){
        return mWordStorage.getWord(position);
    }


    @Override
    public String getLatestWordDate(){
        return mWordStorage.getLatestDate();
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
    public void deleteWord(int position) {
        mWordStorage.deleteWord(position);
    }

    @Override
    public void deleteWords() {
        mWordStorage.deleteAllWords();
    }

    @Override
    public void addFavorite(int position){
        mWordStorage.addFavorite(position);
    }

    @Override
    public void setHidden(int position){
        mWordStorage.setHidden(position);
    }

    @Override
    public void open(){

    }

    @Override
    public void close() {

    }
}
