package to.marcus.rxtesting.presenter;


import java.util.ArrayList;

import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 24/10/15
 */
public interface BasePresenter<V> {
    void initPresenter(V view);
    void onPrefSelected(String prefKey, boolean value);
    boolean isNotification();
    ArrayList<Word> getWordList();
}
