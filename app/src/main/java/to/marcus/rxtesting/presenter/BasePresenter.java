package to.marcus.rxtesting.presenter;

import to.marcus.rxtesting.presenter.view.BaseView;

/**
 * Created by marcus on 9/8/2015
 */
public interface BasePresenter {
    void onStart();
    void onStop();
    void initPresenter(BaseView view) ;
}
