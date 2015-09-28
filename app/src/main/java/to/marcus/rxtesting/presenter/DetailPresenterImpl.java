package to.marcus.rxtesting.presenter;

import javax.inject.Inject;
import rx.functions.Action1;
import to.marcus.rxtesting.data.interactor.SoundByteInteractorImpl;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.view.BaseView;
import to.marcus.rxtesting.ui.DetailActivity;

/**
 * Created by marcus on 9/15/2015
 */
public class DetailPresenterImpl implements BasePresenter<BaseView> {
    private final String TAG = DetailPresenterImpl.class.getSimpleName();
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Inject RepositoryImpl mRepository;
    private final SoundByteInteractorImpl soundByteInteractor;
    private BaseView baseView;

    @Inject public DetailPresenterImpl(SoundByteInteractorImpl interactor){
        soundByteInteractor = interactor;
    }

    @Override
    public void initPresenter(BaseView activity){
        this.baseView = activity;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    // FIXME: 9/25/2015 presenter needs an onclick
    public void onElementSelected(String soundRef){
       pullSoundByteFromNetwork(soundRef);
    }

    private void pullSoundByteFromNetwork(String soundRef){
        soundByteInteractor.getSoundByte(soundRef);
        soundByteInteractor.execute()
                .subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        onSoundByteElementsReceived(bytes);
                    }
                });
    }

    private void onSoundByteElementsReceived(byte[] bytes){
        // FIXME: 9/25/2015 need to redefine BaseView to use a view implementation
        //for testing
        DetailActivity.playSound(bytes);
    }

}
