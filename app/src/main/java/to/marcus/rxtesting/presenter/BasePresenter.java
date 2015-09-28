package to.marcus.rxtesting.presenter;

/**
 * Created by marcus on 9/8/2015
 */
public interface BasePresenter<V>{
    void onStart();
    void onStop();
    void initPresenter(V view) ;
}
