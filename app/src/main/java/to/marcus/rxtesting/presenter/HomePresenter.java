package to.marcus.rxtesting.presenter;

import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/28/2015
 */
public interface HomePresenter<V>{
    void onStart();
    void onStop();
    void onRefresh();
    void initPresenter(V view);
    void initWordDataSet();
    Word onElementSelected(String itemId);
    void onDismissOptionSelected(String itemId);
    void onFavOptionSelected(String itemId);
}
