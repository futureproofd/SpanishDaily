package to.marcus.SpanishDaily.data.interactor;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import to.marcus.SpanishDaily.data.api.WebParser;

/**
 * Created by marcus on 9/16/2015
 */
public class SoundByteInteractorImpl implements Interactor<byte[]> {
    private String mSoundRef;

    @Inject
    public SoundByteInteractorImpl(){}

    public void getSoundByte(String soundRef){
        this.mSoundRef = soundRef;
        execute();
    }

    @Override
    public Observable<byte[]> execute() {
        return WebParser.parseSoundByte(mSoundRef)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
