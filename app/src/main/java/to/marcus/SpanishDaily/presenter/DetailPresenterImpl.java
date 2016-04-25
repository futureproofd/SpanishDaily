package to.marcus.SpanishDaily.presenter;

import javax.inject.Inject;

import rx.functions.Action1;
import to.marcus.SpanishDaily.data.interactor.SoundByteInteractorImpl;
import to.marcus.SpanishDaily.model.Word;
import to.marcus.SpanishDaily.model.repository.RepositoryImpl;
import to.marcus.SpanishDaily.presenter.view.DetailView;

/**
 * Created by marcus on 9/15/2015
 */
public class DetailPresenterImpl implements DetailPresenter<DetailView> {
    @Inject RepositoryImpl mRepository;
    private final SoundByteInteractorImpl soundByteInteractor;
    private DetailView detailView;

    @Inject public DetailPresenterImpl(SoundByteInteractorImpl interactor){
        soundByteInteractor = interactor;
    }

    @Override
    public void initPresenter(DetailView activity){
        this.detailView = activity;
    }

    @Override
    public void onElementSelected(String soundRef){
        //TODO: check if sound exists in cache, else:
        pullSoundByteFromNetwork(soundRef);
    }

    private void pullSoundByteFromNetwork(String soundRef){
        soundByteInteractor.getSoundByte(soundRef);
        soundByteInteractor.execute()
            .subscribe(
                new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        onSoundByteElementsReceived(bytes);
                    }
                },
                new Action1<Throwable>(){
                    @Override
                    public void call(Throwable error){
                        detailView.showNotification("Error fetching speech from network");
                    }
                }
            );
    }

    private void onSoundByteElementsReceived(byte[] bytes){
        detailView.onClickPlayback(bytes);
        //todo add to sound cache
    }

    @Override
    public void onFavoriteToggled(Word word) {
        mRepository.toggleFavorite(word);
    }

}