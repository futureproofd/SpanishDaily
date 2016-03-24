package to.marcus.rxtesting.presenter;

import java.util.ArrayList;
import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.Constants;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.factory.WordFactoryImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.view.HomeView;
import to.marcus.rxtesting.util.DateUtility;
import to.marcus.rxtesting.util.NetworkUtility;

/**
 * Created by marcus on 9/2/2015
 * presenter for main view
 */
public class HomePresenterImpl implements HomePresenter<HomeView>{
    private final WordInteractorImpl wordInteractor;
    private final String UNFILTERED_MODE = Constants.MAIN_MODE;
    private final String DISMISSED_MODE = Constants.DISMISSED_MODE;
    private final String FAVORITES_MODE = Constants.FAVORITES_MODE;
    private HomeView homeView;
    @Inject RepositoryImpl mRepository;
    @Inject public HomePresenterImpl(WordInteractorImpl interactor){
        wordInteractor = interactor;
    }

    @Override
    public void initPresenter(HomeView activity){
        this.homeView = activity;
    }

    @Override
    public void onStart(){}

    @Override
    public void onStop(){
        mRepository.saveWords();
    }

    @Override
    public void onRefresh(){
        pullLatestWord();
        if(homeView.isSwipeRefreshing())
            homeView.hideSwipeRefreshWidget();
    }

    @Override
    public Word onElementSelected(String itemId){return mRepository.getWord(itemId);}

    @Override
    public void initWordDataSet(){
       if(mRepository.getDatasetSize()!=0){
            pullLatestWord();
       }else{
            pullWordFromNetwork();
       }
    }

    public void selectDataset(String datasetMode){
        switch(datasetMode){
            case UNFILTERED_MODE:
                if(NetworkUtility.isWiFiEnabled(homeView.getContext())&& mRepository.getWirelessPref()){
                    initWordDataSet();
                }else if(!mRepository.getWirelessPref()){
                    initWordDataSet();
                }else{
                    showWordList();
                }break;
            case FAVORITES_MODE:showWordList();
                break;
            case DISMISSED_MODE:showWordList();
                break;
        }
    }

    @Override
    public void onDismissOptionSelected(String itemId){
        if(mRepository.getWord(itemId).getVisibility() == 0){
            mRepository.deleteWord(itemId);
            homeView.showNotification(homeView.getContext().getString(R.string.notify_word_deleted));
        }else{
            mRepository.removeWord(itemId);
            homeView.showNotification(homeView.getContext().getString(R.string.notify_word_dismissed));
        }
        homeView.refreshWordList();
    }

    @Override
    public void onFavOptionSelected(String itemId){
        homeView.showNotification(homeView.getContext().getString(R.string.notify_favorite_added));
        mRepository.addFavorite(itemId);
    }

    public void pullLatestWord(){
        if(isWordStale()){
            pullWordFromNetwork();
        }else{
            showWordList();
        }
    }

    public void pullWordFromNetwork(){
        homeView.showLoading();
        wordInteractor.execute()
            .subscribe(
                    new Action1<ArrayList<String>>() {
                        @Override
                        public void call(ArrayList<String> elements) {
                            onWordElementsReceived(elements);
                        }
                    },
                    new Action1<Throwable>() {
                        @Override
                        public void call(Throwable error) {
                            homeView.showNotification(homeView.getContext().getString(R.string.notify_network_error));
                        }
                    }
            );
    }

    private void onWordElementsReceived(ArrayList<String> wordElements){
        homeView.hideLoading();
        homeView.showNotification(homeView.getContext().getString(R.string.notify_new_word));
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
        showWordList();
    }

    private void showWordList(){
        homeView.showWordList(mRepository.getWordsDataset());
    }

    public boolean isWordStale(){
        boolean isStale = false;
        if(DateUtility.isWordStale(mRepository.getLatestWordDate()))
            isStale=true;
        return isStale;
    }

    public boolean getGridCount(String dataSet){
        boolean isMultiGrid = false;
        switch (dataSet){
            case UNFILTERED_MODE:
                isMultiGrid = mRepository.getGridCntHomePref();
                break;
            case FAVORITES_MODE:
                isMultiGrid = mRepository.getGridCntFavPref();
                break;
            case DISMISSED_MODE:
                isMultiGrid = mRepository.getGridCntRecyclePref();
                break;
        }
        return isMultiGrid;
    }

    public void saveGridCountPref(String dataSet, boolean columnSetting){
        switch(dataSet){
            case UNFILTERED_MODE:
                mRepository.saveGridCntHomePref(columnSetting);
                break;
            case FAVORITES_MODE:
                mRepository.saveGridCntFavPref(columnSetting);
                break;
            case DISMISSED_MODE:
                mRepository.saveGridCntRecyclePref(columnSetting);
                break;
        }
    }
}
