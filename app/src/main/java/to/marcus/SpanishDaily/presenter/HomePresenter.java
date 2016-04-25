package to.marcus.SpanishDaily.presenter;

import to.marcus.SpanishDaily.model.Word;

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
    void onDismissOptionSelected(String itemId, String dataSetMode);
    void onModifyPropertySelected(String itemId, String dataSetMode);
}
