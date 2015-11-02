package to.marcus.rxtesting.presenter;


/**
 * Created by marcus on 24/10/15.
 */
public interface BasePresenter<V> {
    void initPresenter(V view);
    void onPrefSelected(String prefKey, boolean value);
    boolean isNotification();
}
