package to.marcus.SpanishDaily.presenter;

import to.marcus.SpanishDaily.model.Word;

/**
 * Created by marcus on 9/8/2015
 */
public interface DetailPresenter<V>{
    void initPresenter(V view);
    void onElementSelected(String soundRef);
    void onFavoriteToggled(Word word);
}
