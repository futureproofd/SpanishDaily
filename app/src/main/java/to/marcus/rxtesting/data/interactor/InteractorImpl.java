package to.marcus.rxtesting.data.interactor;

import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.model.repository.Repository;

/**
 * Created by marcus on 9/8/2015
 */
public class InteractorImpl implements Interactor<Word> {

    private final Repository mRepository;

    @Inject
    public InteractorImpl(Repository repository){
        mRepository = repository;
    }

    @Override
    public Observable<String> execute(){
        return mRepository.getWord()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()
                );
    }
}
