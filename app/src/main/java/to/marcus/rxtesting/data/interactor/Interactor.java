package to.marcus.rxtesting.data.interactor;

import rx.Observable;

/**
 * Created by marcus on 9/8/2015
 */
public interface Interactor<T> {

    Observable<T> execute();

}
