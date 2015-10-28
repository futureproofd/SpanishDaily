package to.marcus.rxtesting.presenter;

import android.content.Context;
import android.util.Log;
import javax.inject.Inject;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.view.BaseView;

/**
 * Created by marcus on 24/10/15
 * Interaction with Preference selections
 */
public class BasePresenterImpl implements BasePresenter<BaseView> {
    private static final String TAG = BasePresenterImpl.class.getSimpleName();
    private static final String KEY_WIRELESS = "key_wireless";
    private static final String KEY_PULL = "key_pull";
    private static final String KEY_NOTIFY = "key_notify";
    private BaseView baseView;
    private static Context context;
    private final WordInteractorImpl wordInteractor; //// TODO: 24/10/15 need another type of interactor?search
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
        }
    }
}
