package to.marcus.rxtesting.data.interactor;

import java.util.ArrayList;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import to.marcus.rxtesting.data.api.TestWebParser;
import to.marcus.rxtesting.data.api.WebParser;

/**
 * Created by marcus on 9/8/2015
 * Implementation for API access
 */
public class WordInteractorImpl implements Interactor<ArrayList<String>> {

    @Inject
    public WordInteractorImpl(){}

    //todo: Add a real test; currently testing with TestWebParser (subbed out WebParser)
    @Override
    public Observable<ArrayList<String>> execute(){
        return TestWebParser.parseWordElements()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
