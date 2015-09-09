package to.marcus.rxtesting.data.interactor;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by marcus on 9/8/2015
 */
public interface Interactor<T> {

    Observable<ArrayList<String>> execute();

}
