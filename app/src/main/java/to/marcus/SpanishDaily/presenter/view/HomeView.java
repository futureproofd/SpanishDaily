package to.marcus.SpanishDaily.presenter.view;

import android.content.Context;

import java.util.ArrayList;

import to.marcus.SpanishDaily.model.Word;

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
