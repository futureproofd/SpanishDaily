package to.marcus.rxtesting.presenter;

import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;

/**
 * Created by marcus on 9/2/2015
 * test
 */
public class TestPresenterImpl {
    private final WordInteractorImpl mInteractor;
    private static final String TAG = TestPresenterImpl.class.getSimpleName();

    @Inject public TestPresenterImpl(WordInteractorImpl interactor){
        mInteractor = interactor;
    }

    @Inject RepositoryImpl mRepository;

    public void initPresenter(){
        //show loading indicator
        /*
        get the last date added to storage
        if it equals this date, get storage word
            else, pullWordFromNetwork
         */
        pullWordFromNetwork();
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

    private void onWordElementsReceived(ArrayList<String> wordElements){
        Log.i(TAG, wordElements.get(0));
        Log.i(TAG, wordElements.get(1));
        Log.i(TAG, wordElements.get(2));
        Log.i(TAG, wordElements.get(3));
        //mRepository.addWord(wordElements);

    }
}
