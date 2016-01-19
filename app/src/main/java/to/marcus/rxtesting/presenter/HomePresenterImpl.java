package to.marcus.rxtesting.presenter;

import java.util.ArrayList;
import javax.inject.Inject;
import rx.functions.Action1;
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
    private static final String TAG = HomePresenterImpl.class.getSimpleName();
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
    public void onStop(){mRepository.saveWords();}

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
            case "unfiltered":
                if(NetworkUtility.isWiFiEnabled(homeView.getContext())&& mRepository.getWirelessPref()){
                    initWordDataSet();
                }else if(!mRepository.getWirelessPref()){
                    initWordDataSet();
                }else{
                    showWordList();
                }break;
            case "favorites":showWordList();
                break;
            case "dismissed":showWordList();
                break;
        }
    }

    @Override
    public void onDismissOptionSelected(String itemId){
        if(mRepository.getWord(itemId).getVisibility() == 0){
            mRepository.deleteWord(itemId);
            homeView.showNotification("Word permanently deleted");
        }else{
            mRepository.removeWord(itemId);
            homeView.showNotification("Word dismissed");
        }
        homeView.refreshWordList();
    }

    @Override
    public void onFavOptionSelected(String itemId){
        homeView.showNotification("Favorite added");
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
                            homeView.showNotification("Error fetching Word from network");
                        }
                    }
            );
    }

    private void onWordElementsReceived(ArrayList<String> wordElements){
        homeView.hideLoading();
        Word word = WordFactoryImpl.Word.newWordInstance(wordElements);
        mRepository.addWord(word);
        showWordList();
    }

    public boolean isWordStale(){
        boolean isStale = false;
        if(DateUtility.isWordStale(DateUtility.formatDateString(mRepository.getLatestWordDate())))
            isStale=true;
        return isStale;
    }

    private void showWordList(){
        homeView.showWordList(mRepository.getWordsDataset());
    }
}
