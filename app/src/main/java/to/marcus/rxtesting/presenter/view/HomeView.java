package to.marcus.rxtesting.presenter.view;

import android.content.Context;

import java.util.ArrayList;

import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/14/2015
 */
public interface HomeView {
    void showWordList(ArrayList<Word> words);
    void refreshWordList();
    void hideSwipeRefreshWidget();
    boolean isSwipeRefreshing();
    void showNotification(String notification);
    Context getContext();
}
