package to.marcus.rxtesting.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.factory.WordFactoryImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.util.Utility;

/**
 * Created by marcus on 9/2/2015
 * this will be the detail view for a clicked word
 * needs an intent passed in
 */
public class TestPresenterImpl implements TestPresenter{
    private static final String TAG = TestPresenterImpl.class.getSimpleName();
    private final WordInteractorImpl mInteractor;
    private ArrayList<Word> mWordsArray;
    @Inject RepositoryImpl mRepository;

    @Inject public TestPresenterImpl(WordInteractorImpl interactor){
        mInteractor = interactor;
    }

    public void initPresenter(){
        initWordDataset();
    }

    @Override
    public void onStart(){

    }

    @Override
    public void onStop(){
        mRepository.saveWords();
    }

    private void initWordDataset(){
        if(mRepository.getDatasetSize()!=0){
            Log.i(TAG, "dataset exists!");
            mWordsArray = mRepository.getWordsDataset();
            pullLatestWord();
        }else{
            pullWordFromNetwork();
        }
    }

    private void pullWordFromNetwork(){
        Log.i(TAG, "pull from network");
        mInteractor.execute()
            .subscribe(new Action1<ArrayList<String>>() {
                @Override
                public void call(ArrayList<String> elements) {
                    onWordElementsReceived(elements);
                }
            });
    }

    private void pullLatestWord(){
        Date testDate = Utility.formatDateString(mRepository.getLatestWordDate());
        if(Utility.isWordStale(testDate)){
            Log.i(TAG, "pull latest word, IS STALE!");
            pullWordFromNetwork();
        }else{
            Log.i(TAG, "Word exists");
        }
    }

    private void onWordElementsReceived(ArrayList<String> wordElements){
        Log.i(TAG, "network word received!");
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
        //notifydatasetchanged or something
    }
}
