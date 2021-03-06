package to.marcus.SpanishDaily.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import to.marcus.SpanishDaily.data.interactor.WordInteractorImpl;
import to.marcus.SpanishDaily.model.Word;
import to.marcus.SpanishDaily.model.repository.RepositoryImpl;
import to.marcus.SpanishDaily.presenter.view.BaseView;

/**
 * Created by marcus on 24/10/15
 * Interaction with Preference selections
 */
public class BasePresenterImpl implements BasePresenter<BaseView> {
    private static final String KEY_WIRELESS = "key_wireless";
    private static final String KEY_PULL = "key_pull";
    private static final String KEY_NOTIFY = "key_notify";
    private static final String KEY_DEL_WORDS = "key_delete_words";
    private static final String KEY_DEL_FAVS = "key_delete_favs";
    private static final String KEY_DEL_BIN = "key_delete_bin";
    private BaseView baseView;
    private final WordInteractorImpl wordInteractor;
    @Inject RepositoryImpl mRepository;

    @Inject public BasePresenterImpl(WordInteractorImpl interactor){
        wordInteractor = interactor;
    }

    @Override
    public void initPresenter(BaseView activity){
        this.baseView = activity;
    }

    @Override
    public void onPrefSelected(String prefKey, boolean value){
        switch(prefKey){
            case KEY_WIRELESS:
                mRepository.saveWirelessPref(prefKey, value);
                break;
            case KEY_NOTIFY:
                mRepository.saveNotifyPref(prefKey, value);
                break;
            case KEY_PULL:
                mRepository.savePullPref(prefKey, value);
                break;
            case KEY_DEL_WORDS:
                mRepository.deleteWords();
                break;
            case KEY_DEL_FAVS:
                mRepository.deleteFavorites();
                break;
            case KEY_DEL_BIN:
                mRepository.deleteRecycled();
                break;
        }
    }

    @Override
    public boolean isNotification(){return mRepository.getNotifyPref();}

    @Override
    public ArrayList<Word> getWordList(){
        return mRepository.getWordsDataset();
    }

    public void setSearched(String itemId){
        if(mRepository.getSearched(itemId) == 0)
            mRepository.setSearched(itemId);
    }
}
