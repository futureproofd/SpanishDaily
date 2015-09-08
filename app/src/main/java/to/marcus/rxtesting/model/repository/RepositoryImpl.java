package to.marcus.rxtesting.model.repository;

import javax.inject.Inject;
import rx.Observable;
import to.marcus.rxtesting.data.api.WebParser;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.Words;

/**
 * Created by mplienegger on 9/4/2015.
 * Inject our model (database, json list) - WordStorage
 * use a repository interface (observable get methods)
 *      these will require an observable to obtain data from any source (database OR network pull)

 */
public class RepositoryImpl implements Repository {

   //get wordStorage injection

    @Inject
    public RepositoryImpl(){
    }

    @Override
    public Word createWord() {
        return null;
    }

    @Override
    public Observable<String> getWord(){
        //if date of word != getdate then
        return WebParser.parseWord();
        //else get from WordStorage
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
    public void open() {

    }

    @Override
    public void close() {

    }
}
