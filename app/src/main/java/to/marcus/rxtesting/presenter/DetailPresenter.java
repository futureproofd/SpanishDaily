package to.marcus.rxtesting.presenter;

/**
 * Created by marcus on 9/8/2015
 */
public interface DetailPresenter<V>{
    void onStart();
    void onStop();
    void initPresenter(V view);
    void onElementSelected(String soundRef);
}
