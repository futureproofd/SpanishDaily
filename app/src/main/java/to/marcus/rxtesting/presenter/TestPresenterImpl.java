package to.marcus.rxtesting.presenter;

import android.util.Log;
import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.InteractorImpl;

/**
 * Created by marcus on 9/2/2015
 * test
 */
public class TestPresenterImpl {
    private final InteractorImpl mInteractor;
    private static final String TAG = TestPresenterImpl.class.getSimpleName();


    @Inject public TestPresenterImpl(InteractorImpl interactor){
        mInteractor = interactor;
    }

    public void initPresenter(){
        //show loading indicator
        pullWordFromNetwork();
    }

    private void pullWordFromNetwork(){
        mInteractor.execute()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });
    }

}
