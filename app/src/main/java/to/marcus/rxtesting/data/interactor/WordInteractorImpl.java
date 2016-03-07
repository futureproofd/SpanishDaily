package to.marcus.rxtesting.data.interactor;

import java.util.ArrayList;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import to.marcus.rxtesting.data.api.WebParser;

/**
 * Created by marcus on 9/8/2015
 */
public class WordInteractorImpl implements Interactor<ArrayList<String>> {

    @Inject
    public WordInteractorImpl(){}

    @Override
    public Observable<ArrayList<String>> execute(){
        return WebParser.parseWordElements()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
