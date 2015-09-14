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
import to.marcus.rxtesting.presenter.view.HomeView;
import to.marcus.rxtesting.util.Utility;

/**
 * Created by marcus on 9/2/2015
 * presenter for main view
 */
public class TestPresenterImpl implements TestPresenter{
    private static final String TAG = TestPresenterImpl.class.getSimpleName();
    private final WordInteractorImpl mInteractor;
    private ArrayList<Word> mWordsArray;
    private HomeView homeView;
    @Inject RepositoryImpl mRepository;

    @Inject public TestPresenterImpl(WordInteractorImpl interactor){
        mInteractor = interactor;
    }

    public void initPresenter(HomeView activity){
        this.homeView = activity;
        initWordDataset();
    }

    @Override
    public void onStart(){}

    @Override
    public void onStop(){mRepository.saveWords();}

    private void initWordDataset(){
        if(mRepository.getDatasetSize()!=0){
            Log.i(TAG, "dataset exists!");
            mWordsArray = mRepository.getWordsDataset();
            pullLatestWord();
        }else{
            pullWordFromNetwork();
        }
        homeView.showWordList(mRepository.getWordsDataset());
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

    private void pullWordFromNetwork(){
        this.homeView.showLoading();
        Log.i(TAG, "pull from network");
        mInteractor.execute()
            .subscribe(new Action1<ArrayList<String>>() {
                @Override
                public void call(ArrayList<String> elements) {
                    onWordElementsReceived(elements);
                }
            });
    }

    private void onWordElementsReceived(ArrayList<String> wordElements){
        homeView.hideLoading();
        Log.i(TAG, "network word received: " + wordElements.get(0));
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
        homeView.updateWordList();
    }

}
