package to.marcus.rxtesting.presenter;

import android.util.Log;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.factory.WordFactoryImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;

/**
 * Created by marcus on 9/2/2015
 * test
 */
public class TestPresenterImpl implements TestPresenter{
    private final WordInteractorImpl mInteractor;
    private static final String TAG = TestPresenterImpl.class.getSimpleName();

    @Inject public TestPresenterImpl(WordInteractorImpl interactor){
        mInteractor = interactor;
    }

    @Inject RepositoryImpl mRepository;

    public void initPresenter(){
        pullLatestWord();
    }

    @Override
    public void onStart(){
    }

    private void pullWordFromNetwork(){
        mInteractor.execute()
            .subscribe(new Action1<ArrayList<String>>() {
                @Override
                public void call(ArrayList<String> elements) {
                    onWordElementsReceived(elements);
                }
            });
    }

    private Word pullWordFromCache(){
        Log.i(TAG, "from cache");
        return mRepository.getWord();
    }

    private void pullLatestWord(){
        if(mRepository.getDatasetSize() == 0){
            pullWordFromNetwork();
        }else{ //additional check needed - if getLatestDate == today
            pullWordFromCache();
        }
    }

    private void onWordElementsReceived(ArrayList<String> wordElements){
        Log.i(TAG, wordElements.get(0));
        Log.i(TAG, wordElements.get(1));
        Log.i(TAG, wordElements.get(2));
        Log.i(TAG, wordElements.get(3));
        Log.i(TAG, wordElements.get(4));
        Log.i(TAG, wordElements.get(5));
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
    }
}
