package to.marcus.rxtesting.model.repository;

import java.util.ArrayList;

import javax.inject.Inject;
import to.marcus.rxtesting.model.WordStorage;
import to.marcus.rxtesting.model.Words;

/**
 * Created by marcuson 9/4/2015.
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
    public void addWord(String word) {
        mWordStorage.saveWord(word);
    }

    /*
    @Override
    public Observable<String> getWord(){
      //  mWordStorage.getWord();
        //if date of word != getdate then
        //return WebParser.parseWord();
        //else get from WordStorage
    }
    */

    @Override
    public String getLatestWord(){
        return null;
       // return mWordStorage.query(0);
    }

    @Override
    public Words getAllWords() {
        return null;
    }

    @Override
    public void deleteWord() {

    }

    @Override
    public void deleteWords() {

    }

    @Override
    public void open(){

    }

    @Override
    public void close() {

    }
}
