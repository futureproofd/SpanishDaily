package to.marcus.rxtesting.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Date;
import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.factory.WordFactoryImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.view.BaseView;
import to.marcus.rxtesting.ui.DetailActivity;
import to.marcus.rxtesting.ui.adapter.RecyclerViewItemClickListener;
import to.marcus.rxtesting.util.Utility;

/**
 * Created by marcus on 9/2/2015
 * presenter for main view
 */
public class HomePresenterImpl implements TestPresenter, RecyclerViewItemClickListener{
    private static final String TAG = HomePresenterImpl.class.getSimpleName();
    private final WordInteractorImpl mInteractor;
    private ArrayList<Word> mWordsArray;
    private BaseView baseView;
    private Context mContext;
    @Inject RepositoryImpl mRepository;

    @Inject public HomePresenterImpl(WordInteractorImpl interactor, Context activity){
        mContext = activity;
        mInteractor = interactor;
    }

    @Override
    public void initPresenter(BaseView activity){
        this.baseView = activity;
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
        baseView.showWordList(mRepository.getWordsDataset());
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
        this.baseView.showLoading();
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
        baseView.hideLoading();
        Log.i(TAG, "network word received: " + wordElements.get(0));
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
        baseView.updateWordList();
    }

    /*
    RecyclerView click listener
    Puts Parcelable as extra
     */
    @Override
    public void onObjectClick(View v, int position) {
        Log.i(TAG, "clicked " + position);
        Word detailWord = mRepository.getWord(position);
        Intent i = new Intent(mContext, DetailActivity.class);
        i.putExtra("Word_Object", detailWord);
        mContext.startActivity(i);
    }

}
