package to.marcus.rxtesting.presenter;

import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/28/2015.
 */
public interface HomePresenter<V>{
    void onStart();
    void onStop();
    void initPresenter(V view);
    void initWordDataSet();
    Word onElementSelected(int position);
    void onDismissOptionSelected(int position);
    void onFavOptionSelected(int position);
}
