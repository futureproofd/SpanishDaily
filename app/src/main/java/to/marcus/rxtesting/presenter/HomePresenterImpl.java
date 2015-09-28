package to.marcus.rxtesting.presenter;

import java.util.ArrayList;
import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.factory.WordFactoryImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.view.BaseView;
import to.marcus.rxtesting.util.Utility;

/**
 * Created by marcus on 9/2/2015
 * presenter for main view
 */
public class HomePresenterImpl implements BasePresenter<BaseView>{
    private static final String TAG = HomePresenterImpl.class.getSimpleName();
    private final WordInteractorImpl wordInteractor;
    private BaseView baseView;
    @Inject RepositoryImpl mRepository;

    @Inject public HomePresenterImpl(WordInteractorImpl interactor){
        wordInteractor = interactor;
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
            pullLatestWord();
        }else{
            pullWordFromNetwork();
        }
        baseView.showWordList(mRepository.getWordsDataset());
    }

    public Word onElementSelected(int position){
        return mRepository.getWord(position);
    }

    private void pullLatestWord(){
        if(Utility.isWordStale(Utility.formatDateString(mRepository.getLatestWordDate())))
            pullWordFromNetwork();
    }

    private void pullWordFromNetwork(){
        this.baseView.showLoading();
        wordInteractor.execute()
            .subscribe(new Action1<ArrayList<String>>() {
                @Override
                public void call(ArrayList<String> elements) {
                    onWordElementsReceived(elements);
                }
            });
    }

    private void onWordElementsReceived(ArrayList<String> wordElements){
        baseView.hideLoading();
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
        baseView.updateWordList();
    }

}
