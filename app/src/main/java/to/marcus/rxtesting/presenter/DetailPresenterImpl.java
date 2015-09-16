package to.marcus.rxtesting.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import javax.inject.Inject;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.view.BaseView;

/**
 * Created by marcus on 9/15/2015
 */
public class DetailPresenterImpl implements TestPresenter{
    private final String TAG = DetailPresenterImpl.class.getSimpleName();
    @Inject RepositoryImpl mRepository;
    private Context mContext;
    private final WordInteractorImpl mInteractor;
    private BaseView baseView;
    private Intent mIntent;

    @Inject public DetailPresenterImpl(WordInteractorImpl interactor, Context activity){
        mContext = activity;
        mInteractor = interactor;
    }

    @Override
    public void initPresenter(BaseView activity){
        Log.i(TAG, "presenter initialized");
        this.baseView = activity;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    //to get HomeActivity intent
    public void getWordParcel(Intent intent){
        this.mIntent = intent;
        Word word = (Word) mIntent.getParcelableExtra("Word_Object");
    }

}
